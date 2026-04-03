# foundation Protocol

`sdui/foundation` は Compose Foundation の shared/public API を SDUI 用 `proto3` に写像したレイヤーです。

## 1:1 対応表

| Source / Proto | 状態 |
|---|---|
| `layout/AlignmentLine` | 実装 |
| `layout/Arrangement` | 実装 |
| `layout/AspectRatio` | 実装 |
| `layout/Box` | 実装 |
| `layout/BoxWithConstraints` | 実装 |
| `layout/Column` | 実装 |
| `layout/ComposeFoundationLayoutFlags` | 実装 |
| `layout/ContextualFlowLayout` | 実装 |
| `layout/ExperimentalFlexBoxApi` | 実装 |
| `layout/ExperimentalGridApi` | 実装 |
| `layout/ExperimentalLayoutApi` | 実装 |
| `layout/FlexBox` | 実装 |
| `layout/FlowLayout` | 実装 |
| `layout/FlowLayoutBuildingBlocks` | 実装 |
| `layout/FlowLayoutOverflow` | 実装 |
| `layout/Grid` | 実装 |
| `layout/Intrinsic` | 実装 |
| `layout/Layout` | 実装 |
| `layout/LayoutScopeMarker` | 実装 |
| `layout/Offset` | 実装 |
| `layout/Padding` | 実装 |
| `layout/Row` | 実装 |
| `layout/RowColumnImpl` | 実装 |
| `layout/RowColumnMeasurePolicy` | 実装 |
| `layout/RulerAlignment` | 実装 |
| `layout/Size` | 実装 |
| `layout/Spacer` | 実装 |
| `layout/WindowInsets` | 実装 |
| `layout/WindowInsetsPadding` | 実装 |
| `layout/WindowInsetsSize` | 実装 |
| `WindowInsetsConnection` | 未採用 |

## メモ

- `WindowInsetsConnection` は、現時点の監査対象 source jar で shared/public 実体を確認できていません
- `FlexBox` と `Grid` は experimental public API として追加済みです
- 除外: Android 専用実装, samples, `internal`/private, helper/utility
