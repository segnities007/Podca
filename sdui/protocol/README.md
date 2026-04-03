# Protocol

`sdui/protocol` は Compose の `ui` / `foundation` / `material3` のうち、SDUI の共有契約として意味がある公開 API を `proto3` に写像するモジュールです。

## 実装方針

- 対象:
  - `commonMain` の公開 API
  - `public` / `experimental public`
  - SDUI の契約として必要な `expect` / `actual` の共有面
- 除外:
  - `androidMain` 専用実装
  - `samples`
  - `internal` / private 実装
  - primitive / helper / utility のみのファイル

## 追加実装

今回、監査結果から共有 API として必要だった以下を追加しました。

- `foundation/layout`
  - `ExperimentalFlexBoxApi.proto`
  - `ExperimentalGridApi.proto`
  - `FlexBox.proto`
  - `Grid.proto`

## レイヤー別対応表

### material3

| 区分 | 対応 |
|---|---|
| 実装済み | `AlertDialog`, `AppBar`, `AppBarColumn`, `AppBarDsl`, `AppBarRow`, `Badge`, `BottomSheetScaffold`, `Button`, `CalendarLocale`, `Card`, `Checkbox`, `Chip`, `ColorScheme`, `ContentColor`, `DateInput`, `DatePicker`, `DatePickerDialog`, `DateRangeInput`, `DateRangePicker`, `Divider`, `DragHandle`, `ExperimentalMaterial3Api`, `ExperimentalMaterial3ExpressiveApi`, `ExposedDropdownMenu`, `FloatingActionButton`, `HorizontalCenterOptically`, `Icon`, `IconButton`, `IconButtonDefaults`, `InteractiveComponentSize`, `Label`, `ListItem`, `MaterialTheme`, `Menu`, `ModalBottomSheet`, `MotionScheme`, `NavigationBar`, `NavigationDrawer`, `NavigationDrawerState`, `NavigationItem`, `NavigationRail`, `OutlinedTextField`, `ProgressIndicator`, `RadioButton`, `Ripple`, `Scaffold`, `SearchBar`, `SecureTextField`, `SegmentedButton`, `Shapes`, `SheetDefaults`, `ShortNavigationBar`, `Slider`, `Snackbar`, `SnackbarHost`, `Surface`, `SwipeToDismissBox`, `Switch`, `Tab`, `TabRow`, `Text`, `TextField`, `TextFieldDefaults`, `TimeFormat`, `TimePicker`, `TimePickerDialog`, `TonalPalette`, `Tooltip`, `Typography`, `WideNavigationRail`, `WideNavigationRailState` |
| 互換プレースホルダ | `Styles`, `Theme`, `Values` |
| 除外 | Android 専用実装, samples, `internal`/private, helper/utility |

### foundation

| 区分 | 対応 |
|---|---|
| 実装済み | `layout/AlignmentLine`, `layout/Arrangement`, `layout/AspectRatio`, `layout/Box`, `layout/BoxWithConstraints`, `layout/Column`, `layout/ComposeFoundationLayoutFlags`, `layout/ContextualFlowLayout`, `layout/ExperimentalFlexBoxApi`, `layout/ExperimentalGridApi`, `layout/ExperimentalLayoutApi`, `layout/FlexBox`, `layout/FlowLayout`, `layout/FlowLayoutBuildingBlocks`, `layout/FlowLayoutOverflow`, `layout/Grid`, `layout/Intrinsic`, `layout/Layout`, `layout/LayoutScopeMarker`, `layout/Offset`, `layout/Padding`, `layout/Row`, `layout/RowColumnImpl`, `layout/RowColumnMeasurePolicy`, `layout/RulerAlignment`, `layout/Size`, `layout/Spacer`, `layout/WindowInsets`, `layout/WindowInsetsPadding`, `layout/WindowInsetsSize` |
| 監査対象外 | `androidMain`, `samples`, `internal` 実装, helper/utility |
| 現時点の扱い | `WindowInsetsConnection` は source jar 上で shared/public の実体を確認できていないため未採用 |

### ui

| 区分 | 対応 |
|---|---|
| 実装済み | root: `Alignment`, `AtomicReference`, `ComposeUiFlags`, `ComposedModifier`, `Expect`, `FrameRate`, `FrameRateCategory`, `KeepScreenOn`, `Modifier`, `MotionDurationScale`, `SensitiveContent`, `SessionMutex`, `UiComposable`, `ZIndexModifier` |
| 実装済み | `alignment`: `Alignment` |
| 実装済み | `geometry`: `CornerRadius`, `Offset`, `Rect`, `RoundRect`, `Size` |
| 実装済み | `graphics`: `Bezier`, `BlendMode`, `Brush`, `Canvas`, `ClipOp`, `Color`, `ColorFilter`, `ColorMatrix`, `Degrees`, `ExperimentalGraphicsApi`, `FilterQuality`, `Float16`, `GraphicsContext`, `ImageBitmap`, `ImageBitmapConfig`, `InlineClassHelper`, `Interpolatable`, `IntervalTree`, `LayerOutsets`, `Matrix`, `Outline`, `Paint`, `PaintingStyle`, `Path`, `PathEffect`, `PathFillType`, `PathGeometry`, `PathHitTester`, `PathIterator`, `PathMeasure`, `PathOperation`, `PathSegment`, `PathSvg`, `PixelMap`, `PointMode`, `RectangleShape`, `RenderEffect`, `Shader`, `Shadow`, `Shape`, `StrokeCap`, `StrokeJoin`, `TileMode`, `VertexMode`, `Vertices` |
| 実装済み | `graphics/colorspace`: `Adaptation`, `ColorModel`, `ColorSpace`, `NamedColorSpace`, `RenderIntent`, `TransferParameters`, `WhitePoint` |
| 実装済み | `graphics/drawscope`: `DrawStyle` |
| 実装済み | `modifier`: `Modifier` |
| 実装済み | `text`: `AnnotatedString`, `AtomicReference`, `Bullet`, `CharHelpers`, `ExperimentalTextApi`, `InternalTextApi`, `LinkAnnotation`, `LinkInteractionListener`, `MultiParagraph`, `MultiParagraphIntrinsics`, `Paragraph`, `ParagraphIntrinsics`, `ParagraphStyle`, `Placeholder`, `PlaceholderVerticalAlign`, `PlatformTextStyle`, `Savers`, `SpanStyle`, `String`, `StringAnnotation`, `Text`, `TextGranularity`, `TextInclusionStrategy`, `TextLayoutResult`, `TextLinkStyles`, `TextMeasurer`, `TextOverflow`, `TextPainter`, `TextRange`, `TextStyle`, `TtsAnnotation`, `UrlAnnotation` |
| 実装済み | `text/font`: `DelegatingFontLoaderForDeprecatedUsage`, `Font`, `FontFamily`, `FontFamilyResolver`, `FontFamilyTypefaceAdapter`, `FontListFontFamilyTypefaceAdapter`, `FontLoadingStrategy`, `FontMatcher`, `FontStyle`, `FontSynthesis`, `FontVariation`, `FontWeight`, `Typeface` |
| 実装済み | `text/intl`: `Locale` |
| 実装済み | `unit`: `Constraints`, `Density`, `Dp`, `FontScaling`, `IntOffset`, `IntRect`, `IntSize`, `LayoutDirection`, `TextUnit`, `Velocity` |
| 空で許容する型 | `NativeCanvasProto`, `ClosePathProto`, `FillProto`, `FontFamilyDefaultProto` |
| 除外 | Android 専用実装, samples, `internal`/private, helper/utility |

## 明示的に除外したもの

以下は source 監査には現れることがありますが、protocol 対象から外しています。

- Android 専用実装
  - 例: `Android*`, `Platform*` の Android バリアント
- samples
  - `*-samples-sources.jar` に由来するもの
- `internal` / private 実装
  - 例: helper, tracker, node, delegate, resolver の内部補助
- primitive / helper / utility
  - 例: `StringHelpers`, `ClassHelpers`, `AtomicInt`

## 残件の扱い

`audit_proto_parity.sh` は差分監査の起点です。ただし機械的な名前比較だけでは `public/commonMain` と `internal/androidMain` を完全には分離できないため、監査結果はこの README の方針で解釈します。
