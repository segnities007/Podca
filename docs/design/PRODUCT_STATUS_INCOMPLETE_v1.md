# プロダクト未完整理（ステータス）v1.11

**結論: プロダクトは未完成。** 本書は「まだできていない／曖昧なままのもの」を **領域別に整理**する（WBS の実行状況トラッキング用）。

**実行の順序・ゲート・リリース完了の定義**は **[PRODUCT_COMPLETION_PLAN_v1.md](./PRODUCT_COMPLETION_PLAN_v1.md)**（計画書 **v1.3.1**・**文書完成**・§0.1、付録 A〜F）を正とする。

| 参照 | 用途 |
|------|------|
| [PRODUCT_COMPLETION_PLAN_v1.md](./PRODUCT_COMPLETION_PLAN_v1.md) | **完成に向けた実行計画**（Track R、Phase 0–4） |
| [WBS_PRODUCT_COMPLETE_v1.md](./WBS_PRODUCT_COMPLETE_v1.md) | やることの **全分解** |
| [BASIC_DESIGN_v1.md](./BASIC_DESIGN_v1.md) §2 | **完成の定義**（柱 A / B） |
| [ANDROIDX_REMOTE_MAP.md](../../sdui/remote/ANDROIDX_REMOTE_MAP.md) | Remote の **✓ / △** 正 |

---

## 1. ガバナンス・工程（未着手が多い）

| 項目 | 状態 | メモ |
|------|------|------|
| 「完成」の P0 範囲の合意 | **未** | 基本設計 §2 と WBS 1.1.1 |
| PR チェックリストの文書化 | **済** | [`.github/pull_request_template.md`](../../.github/pull_request_template.md) |
| **GitHub Actions**（`main` の PR ゲート） | **一部** | [`.github/workflows/track-r-core.yml`](../../.github/workflows/track-r-core.yml) = R2 相当＋`assembleDebug`＋iOS/JVM/JS/Wasm compile を実行。PR テンプレ・[CONTRIBUTING](../../CONTRIBUTING.md) **同梱済**。**実ラン緑（run #1 / id: `24651110048`）は確認済み、default branch の必須チェック設定は未** |
| マルチターゲット smoke CI | **一部** | [`.github/workflows/track-r-smoke.yml`](../../.github/workflows/track-r-smoke.yml) を追加。週次/手動トリガーと matrix 定義は済、実ラン緑確認は未 |
| マップの **△ ごと Issue 化** | **一部** | [`.github/ISSUE_TEMPLATE/remote-gap.yml`](../../.github/ISSUE_TEMPLATE/remote-gap.yml) を追加。Issue 起票の運用面は残 |

---

## 2. Podca Remote（AndroidX 対照で **△ が残る**＝狭義では未完成）

[ANDROIDX_REMOTE_MAP.md](../../sdui/remote/ANDROIDX_REMOTE_MAP.md) 上、**完全 ✓ ではない行**（要対応または「意図的差分」の明文化）:

| API 行 | 未完内容（要約） |
|--------|------------------|
| `drawBitmap` | 汎用 RemotePaint バンドル |
| `drawText` / run | 非 Android の `includeFontPadding`、ピクセル一致方針 |
| `drawTweenPath` | AndroidX Paint バンドル相当 |
| `drawRoundedPolygon` | 非凸・真円 fillet・完全 morph のギャップ |
| `drawTextOnPath` 系 | flags 全文、Alignment/Placement、曲線 path、タイポメトリ完全一致 |
| `drawConditionally` | RemoteFloat/NaN 識別子の upstream 完全一致 |
| `drawToOffscreenBitmap` | RemoteBitmap プール規模・GPU 解放 API の完全一致（`CLEAR_OFFSCREEN_BITMAP_POOL` は実装済み） |
| `loop` | 運用ガード（CONDITIONAL 内のコスト）のツール化は任意 |
| `startStateLayout` | RecordingModifier バイト列は未ワイヤ |
| `usePaint` / RemotePaint | 設計差分として継続か、bundle 化の判断 |

**柱 B（基本設計）** の「— ゼロ」「△ を必要分 ✓」を **厳密に満たす**には、上表の **製品判断＋実装** がまだ必要。

---

## 3. 広い SDUI（実用レベル＝柱 A の「完了」判定は未クローズ）

| 項目 | 状態 | メモ |
|------|------|------|
| 全 `material3.*` / `foundation.*` の **網羅表と Player 実装の一致** | **未** | 未対応は `else` フォールスルー |
| `decodePodcaDocument` の **例外安全**をホストで統一 | **一部** | `composeApp` のマーケ導線（`PodcaIntro`）は失敗時フォールバック導線あり。全ホスト経路の統一は未 |
| 部分 `state_patch` | **未検討** | 現状ルート全差し替え |
| マーケ以外の **本番画面**の存在 | **サンプルのみ** | プロダクト次第 |

---

## 4. composeApp / server（本番品質は未）

| 項目 | 状態 | メモ |
|------|------|------|
| 全ターゲットの **自動ビルド** | **済** | `track-r-core` で `assembleDebug` + iOS arm64/simulator + JVM/JS/Wasm compile を常設 |
| server の **本番 CORS** | **済** | `anyHost()` を撤廃し、`PODCA_CORS_ALLOWED_ORIGINS`（CSV）で許可オリジンを明示可能 |
| **レスポンスサイズ上限**の実装 | **済** | `PODCA_DOCUMENT_MAX_BYTES`（既定 16 MiB）を超える `GET /api/podca/marketing-document` / `/api/podca/welcome-document` は 413 を返す |
| **Docker / 本番 TLS** | **未** | WBS 4.2 |

---

## 5. 設計書・WBS 自体

| 文書 | 状態 |
|------|------|
| 基本／詳細設計 v1 | **文書としての v1 スコープは揃え済**（実装と100%同期ではない） |
| WBS | **一覧のみ**（完了チェックは別途 Issue／本ファイルの更新） |

---

## 6. 次にやると進む順

**[PRODUCT_COMPLETION_PLAN_v1.md](./PRODUCT_COMPLETION_PLAN_v1.md) §3** の Phase 0 → 1 → … に従う。以下はサマリのみ。

1. Phase 0: Track R 承認、Track S の扱い、P0 Issue 化。  
2. Phase 1: CI（`remoteVerifyJvm` + `engine:jvmTest`）＋ PR 規約。  
3. Phase 2: `loadDocument` try/catch。  
4. Phase 3: server CORS・サイズ上限。  
5. Phase 4: スモーク・CHANGELOG・STATUS 更新。

---

## 改訂履歴

| 版 | 日付 | 変更 |
|----|------|------|
| v1.0 | 2026-04-19 | 初版。未完の整理一覧。 |
| v1.1 | 2026-04-19 | [PRODUCT_COMPLETION_PLAN_v1.md](./PRODUCT_COMPLETION_PLAN_v1.md) を実行マスタとして参照。§6 を計画書に合わせて更新。 |
| v1.2 | 2026-04-19 | `track-r-core` CI 同梱を反映。§1 ガバナンス表を更新。計画書 v1.2。 |
| v1.3 | 2026-04-19 | 計画書 v1.3（PR テンプレ・CONTRIBUTING）。本文・§1 を追随。 |
| v1.4 | 2026-04-19 | 計画書 **v1.3.1**（§0.1 文書完成宣言・付録 F）。 |
| v1.5 | 2026-04-20 | §4 を更新（server CORS とレスポンスサイズ上限を「済」に変更、JS/Wasm 手動コンパイル状況を反映）。 |
| v1.6 | 2026-04-20 | `track-r-core` に `compileKotlinJs` / `compileKotlinWasmJs` を追加し、§1/§4 を更新。 |
| v1.7 | 2026-04-20 | `track-r-core` に `assembleDebug` と iOS compile を追加し、§1 の CI 状態を更新。 |
| v1.8 | 2026-04-20 | `track-r-smoke` ワークフローを追加し、マルチターゲット smoke CI を「一部」に更新。 |
| v1.9 | 2026-04-20 | Remote ギャップ追跡用の Issue テンプレートを追加し、§1 の「△ ごと Issue 化」を更新。 |
| v1.10 | 2026-04-20 | Remote offscreen 明示解放 op と path tint color filter を追加し、§2 の未完要約を更新。 |
| v1.11 | 2026-04-20 | `track-r-core` の GitHub 実ラン成功（run #1）を反映し、§1/§4 を更新。 |
