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

## モジュール構成

Podca は以下の 3 つの主要レイヤーで構成されています。

### 1. Podca Protocol (`podca-protocol`)
全ての基盤となる通信規約です。
- 役割: Wire (Protobuf) を用いた UI コンポーネントのシリアライズ定義。
- 内容: `sdui/ui/` (Modifier, Color等), `sdui/foundation/`, `sdui/material3/` 等。

### 2. Podca Studio (`podca-studio-`)
番組（UI）を制作するサーバーサイド SDK です。
- `podca-studio-core`: サーバー側での Composition や Applier の基盤。
- `podca-studio-ui-core`: `androidx.compose.ui` 相当。Modifier, Alignment, Color 等の DSL。
- `podca-studio-ui-foundation`: Row, Column, Text 等の基本 DSL。
- `podca-studio-ui-material3`: Material 3 デザインシステムに基づく DSL。

### 3. Podca Player (`podca-player-`)
番組を受信し、画面に再生するクライアントサイド SDK です。
- `podca-player`: 利用者が Compose App で使用する最上位のエントリーポイント。
- `podca-player-engine`: バイナリのデコード、action dispatch、state store、document controller を行うコアエンジン。
- `podca-player-ui-core`: `androidx.compose.ui` 相当。ModifierApplier 等、Modifier を実機 UI に適用する核。
- `podca-player-ui-foundation`: Foundation コンポーネントの描画実装。
- `podca-player-ui-material3`: Material 3 コンポーネントの描画実装。

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
- `player-engine` は decode / dispatch / state patch 反映 の実行基盤を持つ
- ビジネスロジックは原則ローカル実装に置き、SDUI はその配線を宣言する

---

## 開発の進め方

1. Protocol の定義: `podca-protocol` に `.proto` ファイルを追加。
2. Studio の実装: サーバー側でそのコンポーネントを表現する DSL を作成。
3. Player の実装: クライアント側でその信号を Compose UI に変換する Renderer を作成。
