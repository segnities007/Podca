# Podca Player（`sdui/player/*`）

`NodeProto` をデコードしたツリーを **Compose Multiplatform** で描画し、`actionId` をローカルへ dispatch するクライアント側 SDKです。

## モジュール

| Gradle | 役割 |
|--------|------|
| `:sdui:player:engine` | `PodcaRuntime` / `PodcaDocumentController` / `PodcaActionDispatcher` / 状態パッチ。 |
| `:sdui:player:ui-core` | `ui.*` ノード、`PodcaRenderChildren` など共有描画ヘルパ。 |
| `:sdui:player:ui-foundation` | `foundation.*` レイアウト・修飾のレンダリング。 |
| `:sdui:player:ui-material3` | `material3.*` コンポーネントのレンダリング（`PodcaRuntime` が必要な箇所あり）。 |
| `:sdui:player:player` | **`PodcaPlayer` / `PodcaRenderDocumentNode`**。ルートから名前空間へ振り分け。 |

## 描画パイプライン（要約）

1. アプリは `PodcaRuntime` にドキュメントバイト列を載せる（`loadDocument` など）。
2. `PodcaPlayer` が `runtime.document` を監視し、ルート `PodcaDocumentNode` を `PodcaRenderDocumentNode` に渡す。
3. `PodcaRenderDocumentNode` は `node.type` に応じて次へ振り分ける:
   - `Root` → 子を縦積み（`Column`）で再帰
   - `foundation.*` → `PodcaRenderFoundationNode`
   - `material3.*` → `PodcaRenderMaterial3Node`（`runtime` を渡す）
   - `ui.*` → `PodcaRenderUiNode`
   - その他 → 子だけ再帰（未知タイプを落とさない）

詳細な責務分けとツリーは [../ARCHITECTURE.md](../ARCHITECTURE.md) の *Player Renderer Position* を参照。

## 改良の方向（プロダクト方針）

長期的には **レンダラの分岐面積を抑え**、低レベル命令に寄せたインタプリタ層の検討余地あり（ルート `AGENTS.md` の「進化の方向」）。現状は上記の名前空間別 `when` が拡張の主パス。
