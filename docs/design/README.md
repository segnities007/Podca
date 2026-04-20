# 設計書（v1）

Podca の **基本設計**・**詳細設計**・**完成計画**をこのディレクトリに集約する。

| 文書 | 内容 |
|------|------|
| **[PRODUCT_COMPLETION_PLAN_v1.md](./PRODUCT_COMPLETION_PLAN_v1.md)** | **プロダクト完成の実行計画書（v1.3.1 文書完成）** — §0.1 宣言、Track R/S、フェーズ、CI + PR + CONTRIBUTING、付録 A–F。 |
| [BASIC_DESIGN_v1.md](./BASIC_DESIGN_v1.md) | アーキテクチャ境界、v1 スコープ（SDUI 実用レベル ＋ Remote の CMP/KMP 完走）、受入条件。 |
| [DETAILED_DESIGN_v1.md](./DETAILED_DESIGN_v1.md) | **v1.1 完成**: シーケンス、Remote 前処理順、`ClientEvent`/`ActionResult`/`StatePatch` のフィールド表、`PodcaRenderDocumentNode` 振分け、`PodcaRuntime`、モジュール図（Mermaid）、検証コマンド、v1 完了範囲と E1 注記。 |
| [WBS_PRODUCT_COMPLETE_v1.md](./WBS_PRODUCT_COMPLETE_v1.md) | **プロダクト完成向け WBS**（ガバナンス、広い SDUI、Remote ギャップ、composeApp/server、QA、セキュリティ、Doc、リリース、負債）。 |
| [PRODUCT_STATUS_INCOMPLETE_v1.md](./PRODUCT_STATUS_INCOMPLETE_v1.md) | **未完の整理**: 完成していない領域の一覧と次アクション（ステータス）。 |
| [SMOKE_TRACK_R.md](./SMOKE_TRACK_R.md) | Track R **R6** 用スモーク表（計画書付録 E と連動）。 |
| [AI_ONE_SHOT_MASTER_PROMPT.md](./AI_ONE_SHOT_MASTER_PROMPT.md) | **AI 一括実行用**マスタープロンプト（Track R 想定・コピペ用）。 |

上位の索引: [docs/README.md](../README.md)。実装規約: ルート [AGENTS.md](../../AGENTS.md)。
