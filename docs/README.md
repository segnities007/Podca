# What is this product

- このリポジトリは、**Podca** と呼ぶ SDUI（Server-Driven UI）スタックと、そのサンプル（`composeApp`）、サンプル Ktor サーバー（`server`）を含みます。
- 目的の例:
  - SDUI を構築するために必要な知識の整理と実装
  - **Remote Compose** に近い思想（クライアントは薄く、表現は低レベル命令で足す）への寄せ方の検討

## ドキュメントの地図

| ドキュメント | 内容 |
|--------------|------|
| ルート [README.md](../README.md) | リポジトリ構成、アプリ／サーバーの起動、Web 配信、Ktor API。 |
| [AGENTS.md](../AGENTS.md) | AI／開発者向けの厳格な方針（SDUI ファースト、拡張の順序など）。 |
| [sdui/README.md](../sdui/README.md) | SDUI ライブラリのモジュール一覧と開発フロー。 |
| [sdui/ARCHITECTURE.md](../sdui/ARCHITECTURE.md) | ランタイムモデルと `sdui/` 以下のディレクトリ構造。 |
| [sdui/protocol/README.md](../sdui/protocol/README.md) | Protocol の監査方針と Compose API との対応表。 |

## 技術スタック（要約）

- **共有契約・配信バイト列**: [Wire](https://square.github.io/wire/)（`sdui:protocol`）。クライアント／サーバーで同じ生成型を参照する。
- **クライアント**: Kotlin、Kotlin Multiplatform、Compose Multiplatform、`sdui:player:*`、`composeApp`。
- **サーバー（サンプル）**: Kotlin JVM、Ktor（`server`）。マーケドキュメントのエンコードは Studio 依存で **kotlinx.serialization は SDUI のワイヤ形式では主役ではない**。
