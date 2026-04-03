# ui Protocol

`sdui/ui` は Compose UI の shared/public API を SDUI 用 `proto3` に写像したレイヤーです。

## 1:1 対応表

### root

| Source / Proto | 状態 |
|---|---|
| `Alignment` | 実装 |
| `AtomicReference` | 実装 |
| `ComposeUiFlags` | 実装 |
| `ComposedModifier` | 実装 |
| `Expect` | 実装 |
| `FrameRate` | 実装 |
| `FrameRateCategory` | 実装 |
| `KeepScreenOn` | 実装 |
| `Modifier` | 実装 |
| `MotionDurationScale` | 実装 |
| `SensitiveContent` | 実装 |
| `SessionMutex` | 実装 |
| `UiComposable` | 実装 |
| `ZIndexModifier` | 実装 |

### alignment

| Source / Proto | 状態 |
|---|---|
| `alignment/Alignment` | 実装 |

### geometry

| Source / Proto | 状態 |
|---|---|
| `geometry/CornerRadius` | 実装 |
| `geometry/Offset` | 実装 |
| `geometry/Rect` | 実装 |
| `geometry/RoundRect` | 実装 |
| `geometry/Size` | 実装 |

### graphics

| Source / Proto | 状態 |
|---|---|
| `graphics/Bezier` | 実装 |
| `graphics/BlendMode` | 実装 |
| `graphics/Brush` | 実装 |
| `graphics/Canvas` | 実装 |
| `graphics/ClipOp` | 実装 |
| `graphics/Color` | 実装 |
| `graphics/ColorFilter` | 実装 |
| `graphics/ColorMatrix` | 実装 |
| `graphics/Degrees` | 実装 |
| `graphics/ExperimentalGraphicsApi` | 実装 |
| `graphics/FilterQuality` | 実装 |
| `graphics/Float16` | 実装 |
| `graphics/GraphicsContext` | 実装 |
| `graphics/ImageBitmap` | 実装 |
| `graphics/ImageBitmapConfig` | 実装 |
| `graphics/InlineClassHelper` | 実装 |
| `graphics/Interpolatable` | 実装 |
| `graphics/IntervalTree` | 実装 |
| `graphics/LayerOutsets` | 実装 |
| `graphics/Matrix` | 実装 |
| `graphics/Outline` | 実装 |
| `graphics/Paint` | 実装 |
| `graphics/PaintingStyle` | 実装 |
| `graphics/Path` | 実装 |
| `graphics/PathEffect` | 実装 |
| `graphics/PathFillType` | 実装 |
| `graphics/PathGeometry` | 実装 |
| `graphics/PathHitTester` | 実装 |
| `graphics/PathIterator` | 実装 |
| `graphics/PathMeasure` | 実装 |
| `graphics/PathOperation` | 実装 |
| `graphics/PathSegment` | 実装 |
| `graphics/PathSvg` | 実装 |
| `graphics/PixelMap` | 実装 |
| `graphics/PointMode` | 実装 |
| `graphics/RectangleShape` | 実装 |
| `graphics/RenderEffect` | 実装 |
| `graphics/Shader` | 実装 |
| `graphics/Shadow` | 実装 |
| `graphics/Shape` | 実装 |
| `graphics/StrokeCap` | 実装 |
| `graphics/StrokeJoin` | 実装 |
| `graphics/TileMode` | 実装 |
| `graphics/VertexMode` | 実装 |
| `graphics/Vertices` | 実装 |

### graphics/colorspace

| Source / Proto | 状態 |
|---|---|
| `graphics/colorspace/Adaptation` | 実装 |
| `graphics/colorspace/ColorModel` | 実装 |
| `graphics/colorspace/ColorSpace` | 実装 |
| `graphics/colorspace/NamedColorSpace` | 実装 |
| `graphics/colorspace/RenderIntent` | 実装 |
| `graphics/colorspace/TransferParameters` | 実装 |
| `graphics/colorspace/WhitePoint` | 実装 |

### graphics/drawscope

| Source / Proto | 状態 |
|---|---|
| `graphics/drawscope/DrawStyle` | 実装 |

### modifier

| Source / Proto | 状態 |
|---|---|
| `modifier/Modifier` | 実装 |

### text

| Source / Proto | 状態 |
|---|---|
| `text/AnnotatedString` | 実装 |
| `text/AtomicReference` | 実装 |
| `text/Bullet` | 実装 |
| `text/CharHelpers` | 実装 |
| `text/ExperimentalTextApi` | 実装 |
| `text/InternalTextApi` | 実装 |
| `text/LinkAnnotation` | 実装 |
| `text/LinkInteractionListener` | 実装 |
| `text/MultiParagraph` | 実装 |
| `text/MultiParagraphIntrinsics` | 実装 |
| `text/Paragraph` | 実装 |
| `text/ParagraphIntrinsics` | 実装 |
| `text/ParagraphStyle` | 実装 |
| `text/Placeholder` | 実装 |
| `text/PlaceholderVerticalAlign` | 実装 |
| `text/PlatformTextStyle` | 実装 |
| `text/Savers` | 実装 |
| `text/SpanStyle` | 実装 |
| `text/String` | 実装 |
| `text/StringAnnotation` | 実装 |
| `text/Text` | 実装 |
| `text/TextGranularity` | 実装 |
| `text/TextInclusionStrategy` | 実装 |
| `text/TextLayoutResult` | 実装 |
| `text/TextLinkStyles` | 実装 |
| `text/TextMeasurer` | 実装 |
| `text/TextOverflow` | 実装 |
| `text/TextPainter` | 実装 |
| `text/TextRange` | 実装 |
| `text/TextStyle` | 実装 |
| `text/TtsAnnotation` | 実装 |
| `text/UrlAnnotation` | 実装 |

### text/font

| Source / Proto | 状態 |
|---|---|
| `text/font/DelegatingFontLoaderForDeprecatedUsage` | 実装 |
| `text/font/Font` | 実装 |
| `text/font/FontFamily` | 実装 |
| `text/font/FontFamilyResolver` | 実装 |
| `text/font/FontFamilyTypefaceAdapter` | 実装 |
| `text/font/FontListFontFamilyTypefaceAdapter` | 実装 |
| `text/font/FontLoadingStrategy` | 実装 |
| `text/font/FontMatcher` | 実装 |
| `text/font/FontStyle` | 実装 |
| `text/font/FontSynthesis` | 実装 |
| `text/font/FontVariation` | 実装 |
| `text/font/FontWeight` | 実装 |
| `text/font/Typeface` | 実装 |

### text/intl

| Source / Proto | 状態 |
|---|---|
| `text/intl/Locale` | 実装 |

### unit

| Source / Proto | 状態 |
|---|---|
| `unit/Constraints` | 実装 |
| `unit/Density` | 実装 |
| `unit/Dp` | 実装 |
| `unit/FontScaling` | 実装 |
| `unit/IntOffset` | 実装 |
| `unit/IntRect` | 実装 |
| `unit/IntSize` | 実装 |
| `unit/LayoutDirection` | 実装 |
| `unit/TextUnit` | 実装 |
| `unit/Velocity` | 実装 |

## メモ

- `ui` は範囲が広いため、表はパッケージ単位で整理しています
- 実装の健全性は Wire 生成と Kotlin コンパイルで確認済みです
- 空で許容: `NativeCanvasProto`, `ClosePathProto`, `FillProto`, `FontFamilyDefaultProto`
- 除外: Android 専用実装, samples, `internal`/private, helper/utility
