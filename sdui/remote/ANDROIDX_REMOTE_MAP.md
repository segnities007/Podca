# AndroidX Compose Remote ↔ Podca Remote（対応表・設計差分）

調査方針: **マクロ（リポジトリ／責務分割）→ ミクロ（`RemoteCanvas` 公開 API 単位）**。挙動の正（SSoT）は [AndroidX `compose/remote`](https://github.com/androidx/androidx/tree/androidx-main/compose/remote) の実装。Podca は **Podca 専用ワイヤ**（バイト互換ではない）。

---

## 1. マクロ: Gradle / 責務の対応

| AndroidX（`compose/remote/`） | Podca | 備考 |
|-------------------------------|--------|------|
| `remote-core`（演算・バッファ） | `:sdui:remote:remote-core`（`.proto` + Wire + `RemoteCanvasWireFixtures`） | 表現は **protobuf op 列** に集約。AndroidX のバイナリ演算子と 1:1 ではない。 |
| `remote-creation-core` | **（統合）** `:sdui:remote:remote-creation` 内 | Podca は **KMP / メンテ負荷**のため creation を **1 モジュール**に畳む。API 面では「creation-core 相当の Path 等」は `PodcaRemoteCanvas` に同居。 |
| `remote-creation-compose`（`RemoteCanvas` 等） | `:sdui:remote:remote-creation` | `RemoteCanvas` の **エンコード側**相当のヘルパ群。 |
| `remote-player-core` | **（統合）** `:sdui:remote:remote-player-compose` に依存する解釈ロジック | 再生の **コアと Compose** を同一モジュールに寄せている。将来 `remote-player-core` 相当を切り出す場合は **解釈のみ**を分離可能。 |
| `remote-player-compose` | `:sdui:remote:remote-player-compose` | `Canvas` / `remote.Node` 再生。 |
| `remote-player-view` | **なし**（`PodcaPlayer` / `material3.*` 等がホスト） | 宣言 UI は広い SDUI とホストアプリ側。 |
| `remote-core-testutils` / `remote-player-compose-testutils` / `integration-tests` | `remote-core` / `remote-creation` の `commonTest`、`remote-player-compose` の `jvmTest` + `RemoteCanvasWireFixtures` | 公式フィクスチャと `remoteVerifyJvm`（`remote-core` のループ展開テストを含む）。 |

**設計差分（マクロ）**: AndroidX は **細かいモジュール分割**（core / view / testutils）。Podca は **3 モジュール**で Remote サブシステムを閉じ、**拡張順は proto → player → creation**（`sdui/remote/README.md`）を維持する。

---

## 2. ミクロ: `RemoteCanvas`（creation-compose）API と Podca

`RemoteCanvas` 公開 API（抜粋）と Podca の対応。**✓** 実装済み相当、**△** 部分対応、**—** 未実装（ワイヤ／プレイヤー）。

| AndroidX API | Podca | 状態 |
|----------------|--------|------|
| `save()` / `restore()` | `PUSH_CANVAS_SAVE` / `POP_CANVAS_RESTORE` | ✓ |
| `translate(dx, dy)` | `canvasPushTranslateDp` → `PUSH_TRANSLATE_DP` | ✓ |
| `scale(sx, sy)`（ピボット省略） | `canvasPushScaleAtOriginDp` | ✓（ヘルパ: 原点ピボット） |
| `scale(sx, sy, pivot)` | `PUSH_SCALE_DP` | ✓ |
| `rotate(degrees)`（ピボット省略） | `canvasPushRotateDegAroundOriginDp` | ✓（ヘルパ: 原点） |
| `rotate(degrees, pivot)` / `rotate(deg, cx, cy)` | `PUSH_ROTATE_DEG` | ✓（1 op で pivot+角度） |
| `transform(matrix)` | `PUSH_TRANSFORM_MATRIX` | ✓ |
| `clipRect` / `clipPath` | `PUSH_CLIP_*` + `POP_CLIP` | ✓（`clip_op` Intersect/Difference） |
| `drawRect` / `drawRoundRect` / `drawOval` / `drawCircle` | 各 `FILL_*` / `STROKE_*` | ✓ |
| `drawArc` / sector | `DRAW_ARC` + `arc_use_center` | ✓ |
| `drawLine` | `DRAW_LINE` | ✓ |
| `drawPath` | `FILL_PATH` / `STROKE_PATH` + 任意 **`path_draw_blend_mode_*`**（0..28＝`BlendModeProto`）+ 任意 **`path_draw_alpha_*`**（0..1；`DRAW_TWEEN_PATH` のストロークにも同一フィールド）+ 任意 **`path_draw_color_filter_tint_*`**（Compose `ColorFilter.tint`） | ✓ |
| `drawBitmap` / `drawScaledBitmap` | `DRAW_IMAGE` + 任意 `image_src_*` + 任意 `draw_image_alpha_*` + 任意 `draw_image_blend_mode_*` + 任意 `draw_image_filter_quality` + 任意 **`draw_image_color_filter_tint_*`**（Compose `ColorFilter.tint`；任意 **`draw_image_color_filter_tint_blend_mode_*`** で SrcIn 以外のブレンド）+ 任意 **`draw_image_color_filter_lighting_*`**（Compose `ColorFilter.lighting`＝AndroidX **LightingColorFilter** サブセット；tint 同時は v1 で **lighting 優先**）+ 任意 **`draw_image_color_filter_color_matrix_*`**（**20** float row-major 4×5、`ColorFilter.colorMatrix`；**lighting** 同時は lighting 優先、**tint** のみ同時は matrix 優先）+ 任意 **`draw_image_scale_type_*`**（Fit / Crop / FillBounds のサブセット） + 任意 **`draw_image_scale_factor_*`** + 任意 **`draw_image_content_description`** | ✓ / △（Path 側は **`path_draw_color_filter_tint_*`** と **`stroke_dash_*`** を追加済み。汎用 **`RemotePaint`** バンドルは未ワイヤ） |
| `drawText` / `drawTextRun` / … | `DRAW_TEXT` / `DRAW_TEXT_AT` + 任意 **`text_line_height_sp`** + 任意 **`draw_text_line_height_style`**（`text_line_height_sp` > 0 のとき Compose `LineHeightStyle`：0=省略 / 1=Default / 2=Top+Both / 3=Bottom+Both / 4=Center+Both / 5=Proportional+None）+ 任意 **`draw_text_rect_top_is_first_baseline`** + 任意 **`draw_text_rect_bottom_is_last_baseline`**（先頭フラグが true のとき末行フラグは無視）+ 任意 **`draw_text_disable_font_padding`**（**Android** のみ `PlatformParagraphStyle(includeFontPadding=false)`；JVM/JS/Wasm/iOS は v1 無視）／**`DRAW_TEXT_RUN`**（[text_body] の UTF-16 範囲 + **`draw_text_run_context_*`**（**end ≤ 0** = 文末）でレイアウト窓を正規化し、プレイヤーは窓全体を `BasicText` しつつ実行外を透明マスク + 任意 **`draw_text_run_is_rtl`**、`DRAW_TEXT_AT` と同じ [rect_*] 箱） | △（**非 Android** での `includeFontPadding` 制御・Canvas ピクセル一致は未ワイヤ） |
| `drawTweenPath` | `DRAW_TWEEN_PATH` + [path] from + [tween_path_to] to + [tween_path_fraction]（0..1）+ 任意 **`tween_path_fraction_remote_float_id`**（非空時は `PodcaRuntime.remoteCanvasConditionalFloats` から **t**、未知 id / 非有限 → 0 へクランプ前扱い）+ 任意 **`tween_path_incompatible_fallback`**（lerp 不能時 0=skip / 1=from / 2=to）+ stroke 用 `color_argb` / `stroke_width_dp` / join/cap；**同一 verb 列・同一 coords 長**のとき **dp 座標 lerp**（`lerpRemoteCanvasPathProtosOrNull`） | △（AndroidX **`Paint`** バンドルは未ワイヤ） |
| `drawRoundedPolygon` / `drawRoundedPolygonMorph` | **`FILL_ROUNDED_POLYGON`** / **`STROKE_ROUNDED_POLYGON`** + 閉じた [polyline.xy_dp] + [corner_radius_dp] + 任意 [rounded_polygon_morph_polyline] / [rounded_polygon_morph_t]（頂点 dp lerp） | △（**凸**のみ角丸パス；非凸はシャープ閉 polyline へフォールバック。**二次ベジェ**角＝ graphics-shapes の真円 fillet／完全 morph ではない） |
| `drawTextOnPath` / `drawTextOnCircle` / `drawAnchoredText` | **`DRAW_TEXT_ON_PATH`**（MOVE+LINE baseline + 沿線 [text_align] + [text_on_path_start_offset_dp]）／**`DRAW_TEXT_ON_CIRCLE`**（中心 [rect_l/t] + [circle_radius_dp] + [arc_start_deg]/[arc_sweep_deg]（0=360°）+ [text_on_circle_warp_radius_offset_dp] + 弧長 [text_on_path_start_offset_dp] + [text_align]、`TextMeasurer`）／**`DRAW_TEXT_ANCHORED`**（錨 [rect_l/t] + [text_anchor_pan_*] + [draw_anchored_text_flags] 0..3 + max [rect_r/b] + `TextMeasurer`） | △（AndroidX **`flags` ビットマップ全文**・**Alignment/Placement** enum・曲線 path・完全 Canvas タイポメトリは未ワイヤ） |
| `usePaint` / `RemotePaint` | 各 op の `color_argb` / stroke フィールド | **設計差分**: AndroidX は **Paint バンドル**でまとめる。Podca は **直列フィールド**（ワイヤ単純化）。必要なら将来 `PaintBundle` 相当の submessage を検討。 |
| `drawConditionally` | `CONDITIONAL_BEGIN` / `CONDITIONAL_END` + `conditional_cmp` / `conditional_literal_*` + 任意 **`conditional_literal_nan_eq`** + 任意 **`conditional_remote_float_id_a` / `conditional_remote_float_id_b`**（ホスト `PodcaRuntime` の名前付き float マップ；欠損 → **NaN**）+ 任意 **`conditional_remote_float_fallback_to_literal`** + 任意 **`conditional_operand_a_is_wire_nan` / `conditional_operand_b_is_wire_nan`**（その側を **明示 NaN** に固定＝id／リテラルより優先） | △（AndroidX の **完全な NaN 識別子 / RemoteFloat トークン**表現そのものとは未一致。比較は **`resolveRemoteCanvasConditionalOperands`** + **`evalRemoteCanvasConditionalBranch`**（`remote-core`）） |
| `drawToOffscreenBitmap` | `PUSH_OFFSCREEN_RENDER` / `POP_OFFSCREEN_RENDER` + `rect_*` + 任意 `offscreen_skip_clear_before_draw` / `offscreen_clear_color_argb`（AndroidX の clear あり／なしに相当） + 任意 `offscreen_bitmap_id`（同一サイズのビットマップ再利用のサブセット） + 任意 **`offscreen_discard_pooled_bitmap_after_pop`** + **`CLEAR_OFFSCREEN_BITMAP_POOL`**（明示的に名前付きプールを全解放）+ 任意プログラム **`offscreen_bitmap_pool_max_entries`**（**0**＝既定 **16** 名前付き LRU／**1..512** で上限制御、`OffscreenBitmapPoolJvmTest` で再利用・LRU 退避/clear を固定） | △（AndroidX 側のフル **`RemoteBitmap`** プール規模・GPU API の完全一致までは未到達だが、明示解放のサブセットは実装） |
| `loop` | `LOOP_BEGIN` / `LOOP_END` + `loop_repeat_count`（リテラル回数）+ 任意 **`RemoteCanvasProgramProto.loop_expand_max_repeat_per_block`**（**0**＝既定 **512**／`LOOP_BEGIN` ごとに [loop_repeat_count] をクランプ） | △（paint 前に **`expandRemoteCanvasLoopBlocks`**（`remote-core` の `RemoteCanvasLoopExpand.kt`）でフラット化。オフスクリーン内も **同一ルール**。`CONDITIONAL` より先に展開 — 下記 **評価順**参照。`RemoteCanvasLoopExpandTest`（`remote-core` commonTest）で往復・ルート二重ループ・オフスクリーン内ループを固定） |
| `startStateLayout` / `endStateLayout` | **`STATE_LAYOUT_BEGIN`**（[state_layout_id] + 任意 **`state_layout_semantics_merge_descendants`** / **`state_layout_semantics_test_tag`** / **`state_layout_semantics_content_description`** → アクティブ枝の **プログラム根** `Modifier.semantics`）/ **`STATE_LAYOUT_END`** + 再生前 **`remoteCanvasStateLayoutSemanticsHintsForPlayback`** + **`filterRemoteCanvasStateLayoutBlocksForPlayback`**（`remote-core`）+ ホスト **`PodcaRuntime.remoteCanvasStateLayoutActiveId`**（未設定時は BEGIN に **0** があれば 0、なければ最小 id） | △（AndroidX **`RecordingModifier` 全文（任意 modifier バイト列）** は未ワイヤ；Semantics サブセットのみ） |
| `RemoteFloat` / `RemoteString` ID モデル | **リテラル dp / 文字列を op に直埋め** | **設計差分**: 可変状態の追跡は AndroidX 本体の責務。Podca デモは **静的バイト列**が主。 |

**評価順（`LOOP` と `CONDITIONAL`）**: `LOOP_BEGIN` / `LOOP_END` は **描画より前**に `expandRemoteCanvasLoopBlocks` でフラット化される（`CONDITIONAL` の真偽はこの段階では見ない）。各 `LOOP_BEGIN` の実効繰り返しは **`loop_expand_max_repeat_per_block`**（既定 **512**）で上限制御される。展開後の op 列に対してインタプリタが `CONDITIONAL_*` を積み、偽の分岐では **描画**はスキップされる。とはいえ **ワイヤ上のループ展開は常に実行**されるため、上限を上げた場合は `CONDITIONAL` の内側に閉じ込めても **デコード後の op 数は減らない** — 重い繰り返しは条件の外に出すか、そもそも `CONDITIONAL` 内に `LOOP` を置かないことを推奨する（AndroidX 側の「ネスト禁止」と同趣旨の運用ガード）。

---

## 3. 「すべて揃える」ための推奨順（ロードマップ）

1. **ワイヤ拡張**（`remote-core`）: 条件付き描画、オフスクリーン、ループ等は **opcode 設計**が先。AndroidX の `ConditionalOperations` 等を読み、**意味**を落とし込んでから proto 化する。  
2. **`remote-player-compose`**: 新 op の `Canvas` 実装。  
3. **`remote-creation`**: DSL ヘルパ。  
4. **`wire_opset_version`**: 破壊的変更時のみインクリメント（`SupportedWireOpsetVersion` と同期）。

未実装 API は上表 **—** を参照し、Issue / 設計メモに **P0/P1** を振ってから着手する（一括で巨大 diff にしない）。

---

## 4. ローカル検証

```bash
./gradlew remoteVerifyJvm
```

詳細は [README.md](./README.md) の *ローカル検証*。
