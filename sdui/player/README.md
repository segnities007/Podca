# Podca Player（`sdui/player/*`）

`NodeProto` をデコードしたツリーを **Compose Multiplatform** で描画し、`actionId` をローカルへ dispatch するクライアント側 SDKです。

## モジュール

| Gradle | 役割 |
|--------|------|
| `:sdui:player:engine` | `PodcaRuntime` / `PodcaDocumentController` / `PodcaActionDispatcher` / 状態パッチ。 |
| `:sdui:player:ui-core` | `ui.*` ノード、`PodcaRenderChildren` など共有描画ヘルパ。 |
| `:sdui:remote:remote-player-compose` | **`remote.CanvasProgram`**（`RemoteCanvasProgramProto`）の **op インタプリタ**と、糖衣としての **`remote.Node`**（`RemoteNodeProto`）の Compose 再生。AndroidX `remote-player-compose` に相当。 |
| `:sdui:player:ui-foundation` | `foundation.*` レイアウト・修飾のレンダリング。 |
| `:sdui:player:ui-material3` | `material3.*` コンポーネントのレンダリング（`PodcaRuntime` が必要な箇所あり）。 |
| `:sdui:player:player` | **`PodcaPlayer` / `PodcaRenderDocumentNode`**。ルートから名前空間へ振り分け。 |

## 描画パイプライン（要約）

1. アプリは `PodcaRuntime` にドキュメントバイト列を載せる（`loadDocument` など）。
2. `PodcaPlayer` が `runtime.document` を監視し、ルート `PodcaDocumentNode` を `PodcaRenderDocumentNode` に渡す。
3. `PodcaRenderDocumentNode` は `node.type` に応じて次へ振り分ける:
   - `Root` → 子を縦積み（`Column`）で再帰
   - `remote.CanvasProgram` → `PodcaRenderRemoteCanvasProgramNode`（**op stream**：塗り／ストローク／線／`DRAW_IMAGE`（PNG）／`DRAW_TEXT` オーバーレイ／クリップ／ポインタ；SSoT は [remote/README.md](../remote/README.md)）
   - `remote.Node` → `PodcaRenderRemoteDocumentNode`（宣言的糖衣）
   - `foundation.*` → `PodcaRenderFoundationNode`
   - `material3.*` → `PodcaRenderMaterial3Node`（`runtime` を渡す）
   - `ui.*` → `PodcaRenderUiNode`
   - その他 → 子だけ再帰（未知タイプを落とさない）

詳細な責務分けとツリーは [../ARCHITECTURE.md](../ARCHITECTURE.md) の *Player Renderer Position* を参照。

## 改良の方向（プロダクト方針）

**Podca Remote** では **`remote.CanvasProgram` の op 列**を正史とし、インタプリタを `remote-player-compose` に集約する（ルート `AGENTS.md` / `sdui/remote/README.md`）。リポジトリルートで **`./gradlew remoteVerifyJvm`** すると `remote-creation` の Wire 往復と本モジュール経由の JVM PNG デコード検証がまとめて走る。広い `material3.*` / `foundation.*` は従来どおり名前空間別レンダラが拡張の主パス。
