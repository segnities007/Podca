# Podca Remote

**Podca** ブランドのまま、[AndroidX Compose Remote](https://github.com/androidx/androidx/tree/androidx-main/compose/remote) のモジュール分割に倣ったレイヤです（バイナリ互換ではありません）。AndroidX は `remote-creation-core` / `remote-player-core` 等 **より細かい分割**、Podca は **3 Gradle モジュールに集約**（KMP・運用のため）。**マクロ→ミクロの対応表・API ギャップ**は [ANDROIDX_REMOTE_MAP.md](./ANDROIDX_REMOTE_MAP.md)。

## Single source of truth（Remote Compose 実装）

**挙動・API の正（SSoT）** は [AndroidX Compose Remote](https://github.com/androidx/androidx/tree/androidx-main/compose/remote) の **実装**（例: [`RemoteCanvas.kt`](https://github.com/androidx/androidx/blob/androidx-main/compose/remote/remote-creation-compose/src/main/java/androidx/compose/remote/creation/compose/layout/RemoteCanvas.kt) の `translate` / `scale` / `rotate` / `clipRect`、対応する player 側の再生）です。Podca の `.proto` と `remote-player-compose` は **その意味への Podca 専用ワイヤ射影** であり、バイト列そのものが AndroidX と互換ではありません。

| 概念 | Podca のワイヤ | 備考 |
|------|------------------|------|
| **Op stream（Podca 上の命令列）** | `RemoteCanvasProgramProto` を `NodeProto.type == "remote.CanvasProgram"` の payload で載せる | 塗り（矩形・角丸 **rx/ry**（`corner_radius_y_dp`）・**path**（`RemoteCanvasPathProto` 動詞列 = `drawPath`/`clipPath`；**`FILL_PATH` / `STROKE_PATH`** は任意 **`path_draw_blend_mode_*` / `path_draw_alpha_*` / `path_draw_color_filter_tint_*` / `stroke_dash_*`**、`DRAW_TWEEN_PATH` ストロークも同フィールド）・楕円・**円**・**弧・扇** `DRAW_ARC`・ポリライン・グラデ）・ストローク・線・**`FILL_ROUNDED_POLYGON` / `STROKE_ROUNDED_POLYGON`**（閉 polyline + 角丸 dp + 任意頂点 morph）・**`DRAW_TEXT_ON_PATH`**（MOVE+LINE baseline + `TextMeasurer`）・**`DRAW_TEXT_ON_CIRCLE`**（円弧 + warp + `TextMeasurer`）・**`DRAW_TEXT_ANCHORED`**（錨 + pan + flags + `TextMeasurer`）・**`DRAW_TWEEN_PATH`**（二 path + リテラル t + 任意 **`tween_path_fraction_remote_float_id`** + 任意 **`tween_path_incompatible_fallback`** のサブセット）・**`DRAW_IMAGE`**（`image_png_bytes` = PNG、dst `rect_*_dp`；任意で **src 矩形（px）**＝AndroidX `drawScaledBitmap` の src/dst；未指定時は全画素→dst＝`drawBitmap`＋スケール相当；任意 **アルファ**、任意 **ブレンドモード**（`BlendModeProto` と同じ数値 id）、任意 **`draw_image_filter_quality`**、任意 **`draw_image_color_filter_tint_*`**（`ColorFilter.tint`、任意 **`draw_image_color_filter_tint_blend_mode_*`**）、任意 **`draw_image_color_filter_lighting_*`**（`ColorFilter.lighting`）、任意 **`draw_image_color_filter_color_matrix`**（20 floats、`ColorFilter.colorMatrix`；precedence: lighting > matrix > tint）、任意 **`draw_image_scale_type_*`**（Fit/Crop/FillBounds）、任意 **`draw_image_scale_factor_*`**、任意 **`draw_image_content_description`**・**`CONDITIONAL_BEGIN` / `CONDITIONAL_END`**（リテラル float 比較または任意 **`conditional_remote_float_id_*`** と `PodcaRuntime` のマップ、任意 **`conditional_literal_nan_eq`**、任意 **`conditional_remote_float_fallback_to_literal`**、任意 **`conditional_operand_*_is_wire_nan`**）、**`STATE_LAYOUT_BEGIN` / `STATE_LAYOUT_END`**（[state_layout_id]・任意 **`state_layout_semantics_*`**（アクティブ枝の根 `Modifier.semantics`）・**`remoteCanvasStateLayoutSemanticsHintsForPlayback`** + 描画前フィルタ + `PodcaRuntime`）・**`LOOP_BEGIN` / `LOOP_END`**（リテラル回数・任意プログラム **`loop_expand_max_repeat_per_block`**（0＝既定 512／`LOOP_BEGIN` ごと）・描画前に op 列を展開）、**`PUSH_OFFSCREEN_RENDER` / `POP_OFFSCREEN_RENDER` / `CLEAR_OFFSCREEN_BITMAP_POOL`**（AndroidX `drawToOffscreenBitmap` 相当のサブセット：内側をビットマップにラスタライズして親の `rect_*` に描画、任意 **`offscreen_bitmap_id`**、任意 **`offscreen_discard_pooled_bitmap_after_pop`**、必要時に named pool 明示解放、プログラム任意 **`offscreen_bitmap_pool_max_entries`**）・テキスト（矩形 `DRAW_TEXT` / 座標 `DRAW_TEXT_AT` / UTF-16 部分 **`DRAW_TEXT_RUN`** + **`draw_text_run_context_*`** による shaping 窓と透明マスク、任意 **`text_line_height_sp`** + 任意 **`draw_text_line_height_style`**、任意 **`draw_text_rect_top_is_first_baseline`**、任意 **`draw_text_rect_bottom_is_last_baseline`**、任意 **`draw_text_disable_font_padding`**（Android のみ反映）・クリップ（**軸矩形** / **角丸** `PUSH_CLIP_ROUND_RECT` / **`PUSH_CLIP_POLYLINE`**、`clip_op` = Intersect / Difference）・**Canvas ベクタ用 transform スタック**（`PUSH_TRANSLATE_DP` / `PUSH_SCALE_DP` / `PUSH_ROTATE_DEG` / **`PUSH_TRANSFORM_MATRIX`**（16 float = Compose `Matrix.values`）+ `POP_TRANSFORM`）・**save/restore**（`PUSH_CANVAS_SAVE` / `POP_CANVAS_RESTORE` — clip+transform のスナップショット、AndroidX `save`/`restore` 相当）、ヒット等。新 op は **まず upstream `RemoteCanvas` に相当するか** を確認してから追加する。`wire_opset_version` は解釈器世代（下記）。インタプリタは `remote-player-compose`。ヘルパは `remote-creation` の `canvasDrawImagePngInRectDp` 等。 |
| **宣言的ツリー（糖衣）** | `RemoteNodeProto` を `NodeProto.type == "remote.Node"` で載せる | 読み書きしやすい **高レベル sugar**。新しい表現力を **ここだけ** に増やして `RemoteCanvasProgram` と二重定義しない。必要なら後から **糖 → op の機械的変換** を検討。 |
| **広い SDUI** | `sdui/protocol` の `material3.*` / `foundation.*` | Remote とは別軌道の **生産性・互換** 用。Remote 内の機能追加の逃げ先にしない。 |

**拡張順（固定）**: upstream 相当の意味を決める → `remote-core`（`.proto` / opcode）→ `remote-player-compose`（インタプリタ）→ `remote-creation`（DSL・ヘルパ）。レビューでは「AndroidX Remote のどの API に対応するか？」を最初に問う。

## `wire_opset_version` の運用

| 状況 | `wire_opset_version` |
|------|----------------------|
| 新 **opcode** を足し、既存 op の意味は不変 | 原則 **据え置き**（`0` はプレイヤー側で 1 とみなす）。 |
| 既存 op の **意味変更**、省略不能な新フィールド、インタプリタの **破壊的** 変更 | **インクリメント**し、`remote-player-compose` の `SupportedWireOpsetVersion` を同じ変更で更新。超過フレームは描画拒否。 |
| エンコーダだけの任意メタ | protobuf の別メッセージやコメントに寄せ、`wire_opset_version` を消費しない。 |

## Gradle モジュール対応表

| Gradle モジュール | Remote Compose の近い役割 | 内容 |
|-------------------|---------------------------|------|
| `:sdui:remote:remote-core` | `remote-core` | Wire / `.proto`。**`RemoteCanvasProgram.proto`**（op 列）と **`RemoteNode.proto`**（糖衣 IR）。`DRAW_IMAGE` 用の共有 PNG バイト列は **`RemoteCanvasWireFixtures`**（`minimalPng1x1Rgba`・`demoBadgePng8x8Teal26A69A` など）。ループの paint 前フラット化は **`expandRemoteCanvasLoopBlocks`**（`RemoteCanvasLoopExpand.kt`；**`RemoteCanvasProgramProto.loop_expand_max_repeat_per_block`** で `LOOP_BEGIN` ごとの実効上限、0＝512）。オフスクリーンの名前付き LRU は **`RemoteCanvasProgramProto.offscreen_bitmap_pool_max_entries`**（0＝16、**`remoteCanvasOffscreenPoolMaxEntriesFromWire`**）。**`RemoteCanvasLoopExpandTest`**（commonTest）でオフスクリーン内／ルートの二重ループ展開を固定。 |
| `:sdui:remote:remote-player-compose` | `remote-player-compose` | **`remote.CanvasProgram`** の op インタプリタ（`Canvas` 等）と **`remote.Node`** の Compose 再生。`jvmTest` で **`RemoteCanvasWireFixtures`** の 1×1 / 8×8 PNG を **`decodeToImageBitmap`**（Skiko 同梱）し JVM 上で固定。 |
| `:sdui:remote:remote-creation` | `remote-creation` | `remoteCanvasProgram` / `PodcaRemoteCanvasProgram`、`PodcaRemoteNode` など。`commonTest` で canvas op の **Wire 往復**（例: `DRAW_IMAGE` + `RemoteCanvasProgramProto` encode/decode）を固定。 |

`:sdui/protocol` は従来どおり **Material / Foundation / UI の広いワイヤ**用。Remote サブシステムは上表の三モジュールで閉じ、拡張は **op stream を先に** 伸ばす。

## ローカル検証（`DRAW_IMAGE` 含む）

Wire 往復（`remote-creation`）と JVM 上の PNG デコード（`remote-player-compose` / Skiko）をまとめて実行する例:

```bash
./gradlew remoteVerifyJvm
```

（サブプロジェクトだけ実行したいときは `:sdui:remote:remote-core:jvmTest`、`:sdui:remote:remote-creation:jvmTest`、`:sdui:remote:remote-player-compose:jvmTest` を個別指定してもよい。）
