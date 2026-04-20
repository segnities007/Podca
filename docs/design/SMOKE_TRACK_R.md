# Track R スモーク（テンプレ）

[PRODUCT_COMPLETION_PLAN_v1.md](./PRODUCT_COMPLETION_PLAN_v1.md) §1.1 **R6** 用。行を実態に合わせて増やす。

| # | ターゲット | タブ | 操作 | 期待 | Pass |
|---|------------|------|------|------|------|
| 1 | JVM | 0 | `:composeApp:compileKotlinJvm` + `remoteVerifyJvm` | Remote/Player の JVM 経路が通る | [x] |
| 2 | JVM | 1〜3 | `:server:test`（`testPodcaMarketingDocumentTabs`） | `marketing-document?tab=0..3` が全て 200 + bytes 非空 | [x] |
| 3 | Wasm（browser） | 0 | `:composeApp:compileKotlinWasmJs` | Wasm ターゲットがコンパイル通過 | [x] |
| 4 | Android | 0 | `:composeApp:assembleDebug` | Android デバッグ APK の組み立てが通る | [x] |
| 5 | JVM | 0 | `PodcaIntro` の `loadDocument` 失敗時分岐（コードレビュー） | 失敗時 `CallbackIntroScreen` へフォールバック可能 | [x] |
| 6 | JVM（server） | - | `:server:test`（max bytes=1） | `/api/podca/marketing-document` と `/api/podca/welcome-document` が 413 | [x] |

**実施者**: Copilot CLI **日付**: 2026-04-20
