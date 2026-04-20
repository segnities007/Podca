# Agent instructions (Podca)

## 日本語（要約）

**デモアプリ（`composeApp` の紹介用コード）は必ず SDUI で開発する。** 見える UI（ヘッダー・ナビ・フッター・本文）は `Podca*` DSL で定義し、**本流は Ktor が配るバイト列をクライアントが取得して** `PodcaRuntime.loadDocument` → `PodcaPlayer` とする（マーケ用ツリーは `sdui/marketing` に一本化）。クライアント内だけで `encode` して済ませるのを前提にしたデモにしない。サーバーに繋がらないときの **ローカル encode はフォールバック専用**。ホスト側は `PodcaPlayer` / `runtime.register` / テーマの色 / 取得失敗時フォールバック / Web の URL 同期などインフラに限定する。

**足りない表現力が出たら、まず Podca UI を拡張する。** 広い Material/Foundation 画面は **Protocol → Studio → Player**。**Podca Remote** では **挙動の SSoT は AndroidX Compose Remote の実装**（例: `RemoteCanvas` の translate / scale / rotate / clip / drawBitmap）。Podca 側ではそれを **`remote.CanvasProgram` / `RemoteCanvasProgramProto` の op 列 + `remote-player-compose` インタプリタ** に写す。**まず upstream 相当の意味を決めてから** `remote-core` の opcode → player → `remote-creation` を伸ばす。宣言的 **`remote.Node`** は糖衣。アプリだけに `PodcaNode` 直書きやローカルラッパーを増やして回避しない。

---

## Prefer extending Podca UI first (Protocol → Studio → Player)

When something cannot be expressed cleanly with existing `Podca*` APIs:

1. **Default path**: extend the **wire model** — **`sdui/protocol`** for broad `material3.*` / `foundation.*` UI; **`sdui/remote/remote-core`** for **Podca Remote** where **behavior tracks AndroidX Compose Remote source** (e.g. `RemoteCanvas`), encoded as the **canvas op program** (`RemoteCanvasProgramProto`, `NodeProto.type == "remote.CanvasProgram"`). Add opcodes and payloads there first, then **`sdui/remote/remote-creation`** helpers and **`sdui/remote/remote-player-compose`** interpreter. Only introduce or extend **`remote.Node` / `RemoteNodeProto`** when it is deliberate sugar over the same semantics (not a parallel spec).
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

This matches **“extend Podca UI first”** above: for broad SDUI, protocol and Studio define what clients author; for **Remote**, **AndroidX Remote Compose implementation** is the behavioral source of truth, and **`remote-core`’s op contract** is how Podca serializes it on the wire.

## Libraries (`sdui/*`)

Follow normal project conventions: **`sdui/protocol`** changes drive Studio DSL and Player renderers together. **`sdui/remote/remote-core`** canvas op changes drive **`remote-player-compose`** then **`remote-creation`** (see [sdui/remote/README.md](sdui/remote/README.md)).

---

## ドキュメントの住所（人間・エージェント共通）

| ファイル | 用途 |
|----------|------|
| ルート `README.md` | リポジトリ構成、`composeApp` / `server` の起動、Web ビルド、Ktor の `GET /api/podca/*`。 |
| `CONTRIBUTING.md` | コントリビューション入口・よく使う Gradle コマンド・完成計画書へのリンク。 |
| `sdui/README.md` | Gradle モジュール名（`:sdui:protocol` 等）と開発フロー。**古い `podca-*` という別名は使わない。** |
| `sdui/ARCHITECTURE.md` | ランタイム責務と `sdui/` ディレクトリツリー。 |
| `sdui/remote/README.md` | **Podca Remote**：挙動の SSoT は AndroidX Remote Compose **実装**；Podca 上の **`remote.CanvasProgram` / op 列** と `remote.Node` の位置づけ。 |
| `sdui/remote/ANDROIDX_REMOTE_MAP.md` | AndroidX `compose/remote` との **モジュール／`RemoteCanvas` API 対応表**・意図的差分・未実装ギャップのロードマップ。 |
| `sdui/protocol/README.md` | `.proto` の監査方針と Compose API 対応表。 |
| `docs/README.md` | プロダクトの目的と技術スタック要約への入口。 |
| `docs/design/AI_ONE_SHOT_MASTER_PROMPT.md` | **AI 一括実行用プロンプト**（Track R を 1 セッションで進めるコピペ本文）。 |
| `docs/design/README.md` | **設計・計画（v1）**の索引: **[PRODUCT_COMPLETION_PLAN_v1.md](docs/design/PRODUCT_COMPLETION_PLAN_v1.md)**（実行計画 **v1.3.1**・文書完成・CI + PR + [CONTRIBUTING.md](CONTRIBUTING.md)）、[BASIC_DESIGN_v1.md](docs/design/BASIC_DESIGN_v1.md)、[DETAILED_DESIGN_v1.md](docs/design/DETAILED_DESIGN_v1.md)、[WBS_PRODUCT_COMPLETE_v1.md](docs/design/WBS_PRODUCT_COMPLETE_v1.md)、[PRODUCT_STATUS_INCOMPLETE_v1.md](docs/design/PRODUCT_STATUS_INCOMPLETE_v1.md)。 |

---

## プロダクトを完璧に近づけるための指示（エージェントへの命令集合）

本節は **「何をすべきか」** を `AGENTS.md` に集約したものである。表・数値の **正** はリンク先の文書に従い、ここでは **従うべき行動規範**を書く。

### 1. 正とする文書と読む順（着手前）

1. **[PRODUCT_COMPLETION_PLAN_v1.md](docs/design/PRODUCT_COMPLETION_PLAN_v1.md)** … **Track R**（リリース完成の定義）、フェーズ、**付録 A 受入チェックリスト**、CI・PR 規約。
2. **[BASIC_DESIGN_v1.md](docs/design/BASIC_DESIGN_v1.md)** … 柱 A（広い SDUI 実用）／柱 B（Remote・KMP）、受入条件。
3. **[DETAILED_DESIGN_v1.md](docs/design/DETAILED_DESIGN_v1.md)** … シーケンス、Remote 前処理順、`ClientEvent` / `ActionResult` / `StatePatch`、**`loadDocument` と例外**（§14）、検証コマンド。
4. **[PRODUCT_STATUS_INCOMPLETE_v1.md](docs/design/PRODUCT_STATUS_INCOMPLETE_v1.md)** … **未充足の一覧**（次に潰すギャップ）。
5. **[WBS_PRODUCT_COMPLETE_v1.md](docs/design/WBS_PRODUCT_COMPLETE_v1.md)** … 作業の網羅分解。

**エージェントは** タスク着手前に **少なくとも 1 と 2** を読み、変更がネットワーク・ランタイム・Remote に触れる場合は **3〜5** を読む。

### 2. Track R（リリース品質バー）— 実装・PR で満たす命令

- **R1-min**: [`.github/workflows/track-r-core.yml`](.github/workflows/track-r-core.yml) を **壊さない**。変更後も CI が緑であること。
- **R1-full**: `composeApp` の **android / iosArm64 / iosSimulatorArm64 / jvm / js / wasmJs** について、計画書 **付録 A** のとおり **コンパイルを CI または手動で充足**する。未充足のターゲットがある場合は **[STATUS](docs/design/PRODUCT_STATUS_INCOMPLETE_v1.md)** に理由と Issue を残し、**黙って未検証のままにしない**。
- **R2**: **`./gradlew remoteVerifyJvm`** と **`:sdui:player:engine:jvmTest`** を **main に合流させる前に通す**（ローカルでも同コマンドを実行してから PR する）。
- **R3**: マーケ／紹介 UI は **`sdui/marketing`** のみをソースとし、**HTTP 取得が本流**、クライアント `encode` は **フォールバック専用**（上記「Demo app」節と同一。違反 PR にしない）。
- **R4**: `PodcaRuntime.loadDocument` または `decodePodcaDocument` を呼ぶ経路は **try/catch 等で例外を握りつぶさずホストで捕捉**し、**プロセスを落とさない**（詳細設計 §14）。フォールバック UI へ遷移できること。
- **R5**: `server` を変更するときは **許可オリジンを明示した CORS**（`anyHost` のまま本番相当の変更をしない）と、**マーケ／ウェルカム文書のレスポンスサイズ上限**（詳細設計 §8・計画書）を **検討し、可能なら実装・テストする**。
- **R6**: リリース判断に使う **[SMOKE_TRACK_R.md](docs/design/SMOKE_TRACK_R.md)** を、ターゲットとシナリオが現実と一致するよう **更新し、全 Pass を取る**。

### 3. 実装の作法（手抜き禁止・品質）

- **依頼・Issue の範囲外を変更しない**（無関係リファクタ、別機能の同梱をしない）。
- **恒久回避を禁止**: アプリ／サーバーだけのラッパーで **Protocol / Studio / Player / remote-core** を迂回しない（例外はユーザーが明示した一時措置のみ）。
- **既存コードのスタイル・抽象・命名に合わせる**。不要なコメント・過剰防御を増やさない。
- **二重定義を増やさない**: Remote は **op 先行**、広い SDUI は **protocol 先行**（上記「Prefer extending」）。

### 4. テストと CI（必須コマンド）

| 変更領域 | 最低限通す Gradle |
|----------|-------------------|
| `sdui/remote/*` | `./gradlew remoteVerifyJvm` |
| `sdui/player/engine` またはドキュメント読込・アクション | `./gradlew :sdui:player:engine:jvmTest` |
| `server` | `./gradlew :server:build` |
| `composeApp`（JVM 経路） | `./gradlew :composeApp:compileKotlinJvm` |

PR では **[`.github/pull_request_template.md`](.github/pull_request_template.md)** のチェックを埋め、**嘘チェックをしない**。

### 5. Remote と AndroidX（SSoT）

- 新規 canvas 表現は **AndroidX `compose/remote` 実装の意味を先に確定**し、**`remote-core`（.proto）→ `remote-player-compose` → `remote-creation` → テスト**の順で実装する。
- 同一 PR（または直後の PR）で **[ANDROIDX_REMOTE_MAP.md](sdui/remote/ANDROIDX_REMOTE_MAP.md)** を更新する（**どの API 行に対応するか**をレビュー可能にする）。
- **`RemoteCanvasWireFixtures`** 以外に同一 PNG バイト列を複製しない。

### 6. セキュリティ・配信

- 配信バイト列・HTTP 応答に **サイズ上限**を設ける方針を無視しない（リバプロまたは Ktor）。
- **`PODCA_SITE_ROOT`** 等の静的配信は **読み取り専用ディレクトリ**に限定する。

### 7. ドキュメント同期

- 起動手順・ポート・API・CI ジョブを変えたら **ルート `README.md` または `docs/README.md`** を更新する。
- ワイヤの破壊的変更・公開面の変更には **CHANGELOG**（またはリリースノート）を検討する。

### 8. 「完璧」と Track S（スコープの取り違え禁止）

- **Track R**（計画書 §1.1）を満たすこと＝**リリース品質のゲート**。
- **基本設計 §2 柱 B を字面どおり完全充足**（マップの **△ をすべて ✓** 等）は **Track S / v2** とし、計画書 **§1.2** に従う。**Track R と混同してスコープを無限に広げない**。

### 9. 禁止事項（再掲・追加）

- マーケ本文の **大規模な `composeApp` 内の Material3 レイアウト**（SDUI で表現すべきものをローカル Compose で組む）。
- **サーバー取得なしを前提としたデモ設計**（encode 本流化の暗示）。
- **`wire_opset_version` ポリシー違反**（既存 op の意味変更なのに版を上げない、等）。
- **`remote-core` テスト用 PNG の複製**。

---

## 進化の方向（薄いクライアント）— Remote 粒度（AndroidX 実装に追従）

**方針（固定）**: 薄いクライアント寄りの拡張は **AndroidX Remote Compose の実装が示す粒度・意味**（フレーム内の **描画・ヒット・（将来）レイアウト／式**）に揃える。Podca ワイヤ上の中心は **`RemoteCanvasProgramProto`**（`remote.CanvasProgram`）の **op 列** とその **インタプリタ**（`remote-player-compose`）。宣言的な **`RemoteNodeProto`（`remote.Node`）** は、当面の DX のための **糖衣**；新しい表現力は **まず upstream 相当を確認し、op とコードポイントを増やす**。

既存の広い **`material3.*` / `foundation.*`（`sdui/protocol`）** は、密度の高い画面や移行期のため **維持**するが、**Remote サブシステム内**で二重定義を増やさない（Remote に足すなら canvas ops が先）。**埋め込み PNG 等のテスト／デモ用バイト列**は **`remote-core` の `RemoteCanvasWireFixtures`** に **1 か所**に集約し、他モジュールで同じファイルを複製しない。JVM 上の Remote 検証はリポジトリルートで **`./gradlew remoteVerifyJvm`**（`remote-creation` + `remote-player-compose` の `jvmTest`）。

詳細なモジュール地図と拡張順は [sdui/remote/README.md](sdui/remote/README.md) と [sdui/ARCHITECTURE.md](sdui/ARCHITECTURE.md)。AndroidX `compose/remote` との **対応表・ギャップ**は [sdui/remote/ANDROIDX_REMOTE_MAP.md](sdui/remote/ANDROIDX_REMOTE_MAP.md)。一般の Protocol 拡張は従来どおり上記「Prefer extending Podca UI first」に従う。

### `wire_opset_version`（canvas program）

- **`RemoteCanvasProgramProto.wire_opset_version`** はプログラム単位の **解釈器世代**（単調増）。`remote-player-compose` の **`SupportedWireOpsetVersion`** を超える値のとき、**そのフレーム全体を描画しない**（空 `Box`）— 互換に破壊が入ったとき用。
- **新 opcode の追加だけ** で既存 op の意味が変わらない場合は原則 **`wire_opset_version` を上げない**（古いバイナリは新 enum を無視するだけ、再ビルド後のクライアントは全 op を解釈）。
- **既存 op の意味変更・必須フィールドの追加** など解釈が変わるときは **`wire_opset_version` を上げ、`SupportedWireOpsetVersion` を同じリリースで合わせる**。
