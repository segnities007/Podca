# Podca

Podca (Pod + Cast) は、Compose を用いたサーバー主導型 UI (Server Driven UI) ライブラリです。
「テレビスタジオから番組を送り、テレビで再生する」というメタファーに基づき、UI 構成をパッキング（Pod化）して配信し、クライアント側で動的に再生します。

## 実装方針

Podca は **Hybrid SDUI** として設計します。

- UI 構造、表示条件、アクション配線は `Podca*` DSL で宣言する
- 実際のビジネスロジック、HTTP 通信、DB、端末機能は基本的にローカル実装に寄せる
- `NodeProto` は `ByteArray` として Studio から Player に送る
- `NodeProto.key` / `NodeProto.actions` で UI とローカル実装を接続する
- `ClientEventProto` と `ActionResultProto` で Player と Server/Studio を往復する
- `PodcaPlayer` 側は decode -> render -> dispatch -> state patch 反映 を担当する
- `PodcaPlayer(runtime)` は runtime の document を監視する入口で、`PodcaPlayer(document, runtime)` は decoded document を直接描画する薄いヘルパー
- 未対応ノードは落とさず、children を再帰描画する fallback を採る

このため、Podca は「関数本体を配る」仕組みではなく、「UI とロジックの接点を宣言的に配る」仕組みです。

## モジュール構成（Gradle パス）

リポジトリルートの `settings.gradle.kts` の `include` と対応します。

| Gradle モジュール | 役割 |
|-------------------|------|
| `:sdui:protocol` | Wire / Protobuf。`NodeProto` および `ui` / `foundation` / `material3` 相当の共有契約。 |
| `:sdui:remote:remote-core` | **Podca Remote** の Wire 契約。**SSoT**: `RemoteCanvasProgramProto`（`remote.CanvasProgram` の op 列）。**糖衣**: `RemoteNodeProto`（`remote.Node`）。 |
| `:sdui:remote:remote-player-compose` | **op インタプリタ**（Canvas 等）と `remote.Node` の Compose 再生。AndroidX `remote-player-compose` に相当する役割。 |
| `:sdui:remote:remote-creation` | `PodcaRemoteCanvasProgram` / `remoteCanvasProgram` と `PodcaRemoteNode` 等 — スタジオから Remote ペイロードを載せる API。 |
| `:sdui:studio:core` | `PodcaNode` / `PodcaTree`、エンコードの基盤。 |
| `:sdui:studio:ui-core` | Modifier・Alignment・Color 等（Compose UI 寄りの DSL）。 |
| `:sdui:studio:ui-foundation` | Row / Column / Flow / Grid 等のレイアウト DSL。 |
| `:sdui:studio:ui-material3` | Material 3 コンポーネントの DSL。 |
| `:sdui:studio:studio` | 公開 API（`PodcaStudio` など）。アプリ・サーバーが依存するなら主にこれ。 |
| `:sdui:marketing` | マーケ／紹介用のスタジオツリー定義とエンコード（`composeApp` デモと Ktor サンプルが利用）。 |
| `:sdui:player:engine` | デコード、アクション dispatch、状態ストア、ドキュメント制御。 |
| `:sdui:player:ui-core` | `ui.*` 系ノードのレンダリング。 |
| `:sdui:player:ui-foundation` | `foundation.*` 系ノードのレンダリング。 |
| `:sdui:player:ui-material3` | `material3.*` 系ノードのレンダリング。 |
| `:sdui:player:player` | 公開 API（`PodcaPlayer` / `PodcaRuntime`）。 |

### Action / State の扱い

- `PodcaActionBinding` は UI イベントと `actionId` を結ぶ
- `PodcaActionDispatcher` は `actionId -> Local ViewModel / UseCase` を解決する
- `PodcaStateStore` はローカル state と `statePatch` を保持する
- `PodcaNode.toProto()` の回帰テストで `key` / `actions` の伝播を固定している
- `PodcaDocumentController` は `NodeProto` の decode と action dispatch をまとめる
- `PodcaStudio.encode(...)` は UI を `ByteArray` に変換する
- `PodcaPlayer` 側は受信した `ByteArray` を decode して再生し、必要なら event を dispatch する

### 実装の境界

- `studio` は `key` と `actions` を持つ `NodeProto` を生成する
- `player` は `NodeProto` を Compose に復元する renderer を持つ
- `player` の renderer は現時点で `Root` / 基礎レイアウト / 基本 Material 3 コンポーネントをカバーし、それ以外は安全な fallback で流す
- `player:engine` は decode / dispatch / state patch 反映 の実行基盤を持つ
- ビジネスロジックは原則ローカル実装に置き、SDUI はその配線を宣言する

---

## 開発の進め方

1. Protocol の定義: `:sdui:protocol` の `src/commonMain/proto/` に `.proto` を追加し、Wire 生成物を確認する（広い Material / Foundation 契約）。
2. **Podca Remote** の定義: `:sdui:remote:remote-core` の `.proto` に **まず canvas op**（`RemoteCanvasProgram.proto`）を追加し、`remote-player-compose` のインタプリタ → `remote-creation` のヘルパの順で更新する。`RemoteNodeProto` への追加は、意図した糖衣化のときのみ。埋め込みバイナリ（例: `DRAW_IMAGE` の PNG）は **`RemoteCanvasWireFixtures`** に **1 か所**に集約し、他モジュールでバイト列を複製しない（検証は `remote-creation` の `commonTest` と `remote-player-compose` の `jvmTest`；ルートで **`./gradlew remoteVerifyJvm`** または [remote/README.md](./remote/README.md) の *ローカル検証* を参照。）
3. Studio の実装: 対応する `Podca*` DSL を `sdui/studio/*` に追加する。
4. Player の実装: 対応するレンダラを `sdui/player/ui-*` に追加する（`remote.Node` は `remote-player-compose`）。

（例外やデモ専用の回避は `AGENTS.md` の方針に従わない。）

---

## 進化の方向（メモ）

**Remote サブシステム**では、表現の中心を **Remote Compose 粒度の op stream（`remote.CanvasProgram`）** に置く（SSoT）。`material3.*` / `foundation.*`（`sdui/protocol`）は当面の生産性のため維持し、**Remote に足す新機能は opcode から** とする。

Remote の規約と拡張順は [remote/README.md](./remote/README.md)。AndroidX との **対応調査・ギャップ一覧**は [remote/ANDROIDX_REMOTE_MAP.md](./remote/ANDROIDX_REMOTE_MAP.md)。ディレクトリ全体は [ARCHITECTURE.md](./ARCHITECTURE.md)。`player` の振り分けは [player/README.md](./player/README.md)。ルートの起動・API は [README.md](../README.md)。
