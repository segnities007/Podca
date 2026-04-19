# Agent instructions (Podca)

## 日本語（要約）

**デモアプリ（`composeApp` の紹介用コード）は必ず SDUI で開発する。** 見える UI（ヘッダー・ナビ・フッター・本文）は `Podca*` DSL で定義し、**本流は Ktor が配るバイト列をクライアントが取得して** `PodcaRuntime.loadDocument` → `PodcaPlayer` とする（マーケ用ツリーは `sdui/marketing` に一本化）。クライアント内だけで `encode` して済ませるのを前提にしたデモにしない。サーバーに繋がらないときの **ローカル encode はフォールバック専用**。ホスト側は `PodcaPlayer` / `runtime.register` / テーマの色 / 取得失敗時フォールバック / Web の URL 同期などインフラに限定する。

**足りない表現力が出たら、まず Podca UI（Protocol / Studio / Player）の新規実装・拡張を検討する。** アプリ側にだけ `PodcaNode` 直書きやローカルラッパーを増やして回避しない（サーバー側の UI 定義も同様に、基本は Studio の `Podca*` DSL を拡張する方向で揃える）。

---

## Prefer extending Podca UI first (Protocol → Studio → Player)

When something cannot be expressed cleanly with existing `Podca*` APIs:

1. **Default path**: add or extend the **wire model** (`sdui/protocol`), expose it in **Studio** (`Podca…` composables in `sdui/studio/*`), and implement rendering in **Player** (`sdui/player/*`).
2. **Do not** paper over gaps with **one-off helpers in apps or servers** (e.g. hand-rolled `PodcaNode` wrappers) unless the user explicitly wants a temporary exception — those patterns rot and duplicate the real API surface.

Same idea for **Ktor / server-side** authoring: define UI with Studio DSL and evolve **Podca UI**, not ad-hoc parallel stacks.

---

## Demo app (`composeApp`): SDUI-first

When changing the **intro / marketing demo** (source under `composeApp/src/podcaIntroMain/` and related wiring):

1. **Server-sourced bytes are the default story, not “client-only encode.”** The Ktor sample serves `GET /api/podca/marketing-document?tab=0..3`; the tree is authored once in **`sdui/marketing`** and encoded on the server. The demo client uses **Ktor Client** to fetch, then `loadDocument` / `PodcaPlayer`. **`encodePodcaMarketingDocument` on the client is fallback only** when the API is down. Do not write throwaway demo code that implies SDUI means skipping the server unless the user explicitly asks.
2. **Implement visible UI with SDUI only** — use Studio DSL in **`sdui/marketing`** (`PodcaScaffold`, `PodcaText`, `PodcaTabRow`, `PodcaCard`, `PodcaColumn`, `PodcaSurface`, `material3.*` / `foundation.*` / `ui.*` nodes, etc.). Do not duplicate large marketing trees inside `composeApp` to save time.
3. **Do not add local Compose screens** for demo chrome (no extra `Scaffold`, marketing nav bars, footers, or page bodies built with `androidx.compose.material3.*` composables) unless the user explicitly asks for an exception.
4. **Allowed host / infrastructure outside SDUI** (keep minimal):
   - `PodcaPlayer`, `PodcaRuntime`, and action registration (`runtime.register`) for wiring to navigation or other local behavior.
   - `MaterialTheme` / color scheme for global palette only — not for duplicating layout that belongs in the SDUI tree.
   - Top-level **offline / error fallback** UI when bytes cannot be loaded or decoded (e.g. `CallbackIntroScreen` after fetch + local encode both fail).
   - **Ktor Client**, base URL resolution (`MarketingHttp` expect/actual), and related networking — transport only; documents still come from the protocol.
   - Web URL sync helpers (`WebMarketingHistoryEffect` and similar) that only bridge the browser to `NavController`; they must not replace SDUI layout.

If a requested change cannot be expressed with current Podca node types, **extend Protocol + Studio + Player** for that component, or ask the user — do not bypass SDUI with ad-hoc local UI in the demo.

This is the same as **“extend Podca UI first”** above: protocol and Studio are the source of truth for what clients can author.

## Libraries (`sdui/*`)

Follow normal project conventions: protocol changes drive Studio DSL and Player renderers together.

---

## ドキュメントの住所（人間・エージェント共通）

| ファイル | 用途 |
|----------|------|
| ルート `README.md` | リポジトリ構成、`composeApp` / `server` の起動、Web ビルド、Ktor の `GET /api/podca/*`。 |
| `sdui/README.md` | Gradle モジュール名（`:sdui:protocol` 等）と開発フロー。**古い `podca-*` という別名は使わない。** |
| `sdui/ARCHITECTURE.md` | ランタイム責務と `sdui/` ディレクトリツリー。 |
| `sdui/protocol/README.md` | `.proto` の監査方針と Compose API 対応表。 |
| `docs/README.md` | プロダクトの目的と技術スタック要約への入口。 |

## 進化の方向（薄いクライアント）

長期的には **クライアントを薄くし、表現を低レベル命令で足す**形を検討する（Remote Compose 寄り）。新規 UI のたびに Player の `when (node.type)` を増やし続けないため、**少数の命令セット＋インタプリタ**の層を別途設計する余地がある。それまでは既存の Protocol / Studio / Player の三層拡張がデフォルトパス。

詳細な作業順は上記「Prefer extending Podca UI first」に従う。
