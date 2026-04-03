# Podca Directory Structure

## Runtime Model

Podca は **Hybrid SDUI** を採用します。

- UI 構造、表示条件、アクションの配線は SDUI 側で宣言的に決める
- 実際の業務処理、HTTP 通信、DB、再生制御などは基本的にローカル実装に寄せる
- そのため `Podca` は「関数本体を配る仕組み」ではなく、「UI と Action/State の契約を配る仕組み」として扱う
- `NodeProto` は `ByteArray` で配信し、`key` / `actions` でローカル実装へ接続する

責務は次のように分けます。

- `Studio/Server`
  - 画面構造の生成
  - 表示条件や分岐の決定
  - `actionId` と `stateKey` の付与
  - `NodeProto.key` / `NodeProto.actions` の生成
  - `Button` / `Card` / `Surface` / `TextField` / `Snackbar` / `Tab` などの interactive component に識別子を埋める
  - 必要ならサーバ側で event を受けて UI を再生成
- `Player`
  - `NodeProto` の binary decode
  - Compose UI へのレンダリング
  - `actionId` をローカルの `ViewModel` / `UseCase` に dispatch
  - `statePatch` を受けてローカル state を更新
  - 未対応ノードは子要素だけ再帰描画して落とさない
- `Local ViewModel / UseCase`
  - HTTP、DB、端末機能などの実処理
  - `actionId -> 実処理` の解決
  - `stateKey -> 現在値` の管理
- `Binary transport`
  - `Studio -> Player` は `ByteArray` で `NodeProto` を送る
  - `Player -> Studio/Server` は `ClientEventProto` を送る
  - `ActionResultProto` の `state_patch` で再描画を反映する
- `Player runtime`
  - `PodcaDocumentController` が `NodeProto` を decode して document を保持する
  - `PodcaActionDispatcher` が local handler を解決する
  - `PodcaStateStore` が `statePatch` を受けて document/revision を更新する
  - `PodcaRuntime` が application 側の入口になる

この前提では、Podca DSL は次の境界で使うのが基本です。

- `Podca*` UI DSL: 画面構造と配線を表現する
- `PodcaActionBinding` / `ClientEventProto`: 何が起きたかを宣言する
- `PodcaStateStore`: ローカル状態を保持する
- `PodcaActionDispatcher`: `actionId` をローカル実装に振り分ける

```text
sdui/
├── protocol/               # 【全基盤】通信規格・Protobuf 定義
│   └── src/commonMain/proto/ # .proto ファイル (Wire 生成元)
│
├── studio/                 # 【制作側】Server-side SDK
│   ├── core/               # Composition / Applier 基盤
│   ├── ui-core/            # Modifier / Alignment DSL (androidx.compose.ui 相当)
│   ├── ui-foundation/      # Row / Column DSL (androidx.compose.foundation 相当)
│   ├── ui-material3/       # Button / Card DSL (androidx.compose.material3 相当)
│   └── studio/             # 公開 API (Entry Point: `PodcaStudio`)
│
└── player/                 # 【再生側】Client-side SDK
    ├── engine/             # Binary Decoder / Registry (再生エンジン)
    ├── ui-core/            # Modifier Applier (androidx.compose.ui 相当)
    ├── ui-foundation/      # Foundation レンダラー実装
    ├── ui-material3/       # Material 3 レンダラー実装
    └── player/             # 公開 API (Entry Point: `PodcaPlayer`)
```

## Recommended Flow

1. Studio が `Podca*` DSL を使って UI を組み立てる
2. `PodcaStudio.encode(...)` が `NodeProto` を `ByteArray` に変換する
3. Player が `NodeProto.ADAPTER.decode(...)` で受け取る
4. Player は `type` と `payload` と `actions` を元に Compose へ再生する
5. ユーザー操作が起きたら `ClientEventProto` を生成して dispatch する
6. ローカルの `ViewModel / UseCase` が処理し、必要なら `ActionResultProto.state_patch` を返す
7. state 更新後に UI を再描画する

## Player Renderer Position

`player/ui-core`, `player/ui-foundation`, `player/ui-material3` は、`NodeProto` を Compose に戻すレンダラー層です。

- `player/ui-core`
  - `Modifier` や基本的な UI 型の適用
  - ルートノードから child tree を描画へ落とす土台
- `player/ui-foundation`
  - `Box` / `Row` / `Column` / `Flow` などの基礎レイアウト
  - `key` を持つノードや action を持つノードをそのまま描画に接続
- `player/ui-material3`
  - `Button` / `Card` / `TextField` / `Surface` などの Material 3 コンポーネント
  - `actions` を UI イベントに結び、`PodcaRuntime.dispatch(...)` へ流す
  - `key` を持つ node を event 発火点として扱う

### Current Renderer Coverage

- `player/ui-core`
  - `Root` と child traversal
- `player/ui-foundation`
  - `Box`
  - `BoxWithConstraints`
  - `FlowRow`
  - `FlowColumn`
  - `ContextualFlowRow`
  - `ContextualFlowColumn`
  - `FlexBox`
  - `Grid`
  - `Row`
  - `Column`
  - `Spacer`
- `player/ui-material3`
  - `Text`
  - `Button`
  - `ElevatedButton`
  - `FilledTonalButton`
  - `OutlinedButton`
  - `TextButton`
  - `Card`
  - `ElevatedCard`
  - `OutlinedCard`
  - `Surface`

Fallback 方針は単純です。

- 未対応 type はクラッシュさせず、children を再帰描画する
- children も無いなら no-op
- つまり「未知ノードは無視して tree を進める」

この層は「ビジネスロジックを書く場所」ではなく、`NodeProto` の意味を Compose に復元する場所です。

---

### Module Internal Structure (Common)

```text
[module]/
├── build.gradle.kts        # モジュール個別のビルド設定
└── src/
    ├── commonMain/
    │   └── kotlin/         # 実装コード (Production)
    └── commonTest/
        └── kotlin/         # ユニットテストコード (Test)
```
