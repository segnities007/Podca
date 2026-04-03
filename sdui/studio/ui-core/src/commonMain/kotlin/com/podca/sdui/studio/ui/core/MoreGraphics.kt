package com.podca.sdui.studio.ui.core

import com.podca.sdui.protocol.ui.geometry.OffsetProto
import com.podca.sdui.protocol.ui.geometry.RectProto
import com.podca.sdui.protocol.ui.geometry.RoundRectProto
import com.podca.sdui.protocol.ui.graphics.BlendModeProto
import com.podca.sdui.protocol.ui.graphics.CanvasClipProto
import com.podca.sdui.protocol.ui.graphics.CanvasDrawCommandProto
import com.podca.sdui.protocol.ui.graphics.CanvasProto
import com.podca.sdui.protocol.ui.graphics.CanvasSaveLayerProto
import com.podca.sdui.protocol.ui.graphics.CanvasTransformProto
import com.podca.sdui.protocol.ui.graphics.ClipOpProto
import com.podca.sdui.protocol.ui.graphics.ColorProto
import com.podca.sdui.protocol.ui.graphics.DrawArcProto
import com.podca.sdui.protocol.ui.graphics.DrawCircleProto
import com.podca.sdui.protocol.ui.graphics.DrawImageProto
import com.podca.sdui.protocol.ui.graphics.DrawLineProto
import com.podca.sdui.protocol.ui.graphics.DrawOvalProto
import com.podca.sdui.protocol.ui.graphics.DrawPathProto
import com.podca.sdui.protocol.ui.graphics.DrawPointsProto
import com.podca.sdui.protocol.ui.graphics.DrawRectProto
import com.podca.sdui.protocol.ui.graphics.DrawRoundRectProto
import com.podca.sdui.protocol.ui.graphics.DrawVerticesProto
import com.podca.sdui.protocol.ui.graphics.Float16Proto
import com.podca.sdui.protocol.ui.graphics.GraphicsContextProto
import com.podca.sdui.protocol.ui.graphics.ImageBitmapConfigProto
import com.podca.sdui.protocol.ui.graphics.ImageBitmapProto
import com.podca.sdui.protocol.ui.graphics.IntervalProto
import com.podca.sdui.protocol.ui.graphics.IntervalQueryProto
import com.podca.sdui.protocol.ui.graphics.IntervalTreeProto
import com.podca.sdui.protocol.ui.graphics.LayerOutsetsProto
import com.podca.sdui.protocol.ui.graphics.MatrixProto
import com.podca.sdui.protocol.ui.graphics.PaintProto
import com.podca.sdui.protocol.ui.graphics.PathDirectionProto
import com.podca.sdui.protocol.ui.graphics.PathElementProto
import com.podca.sdui.protocol.ui.graphics.PathFillTypeProto
import com.podca.sdui.protocol.ui.graphics.PathGeometryProto
import com.podca.sdui.protocol.ui.graphics.PathHitTesterProto
import com.podca.sdui.protocol.ui.graphics.PathMeasureProto
import com.podca.sdui.protocol.ui.graphics.PathOperationProto
import com.podca.sdui.protocol.ui.graphics.PathProto
import com.podca.sdui.protocol.ui.graphics.PathSegmentProto
import com.podca.sdui.protocol.ui.graphics.PathSegmentTypeProto
import com.podca.sdui.protocol.ui.graphics.PaintingStyleProto
import com.podca.sdui.protocol.ui.graphics.PointModeProto
import com.podca.sdui.protocol.ui.graphics.RotateTransformProto
import com.podca.sdui.protocol.ui.graphics.ScaleTransformProto
import com.podca.sdui.protocol.ui.graphics.SkewTransformProto
import com.podca.sdui.protocol.ui.graphics.BlurEffectProto
import com.podca.sdui.protocol.ui.graphics.OffsetEffectProto
import com.podca.sdui.protocol.ui.graphics.ShaderProto
import com.podca.sdui.protocol.ui.graphics.TransformShaderProto
import com.podca.sdui.protocol.ui.graphics.LinearGradientShaderProto
import com.podca.sdui.protocol.ui.graphics.RadialGradientShaderProto
import com.podca.sdui.protocol.ui.graphics.SweepGradientShaderProto
import com.podca.sdui.protocol.ui.graphics.ImageShaderProto
import com.podca.sdui.protocol.ui.graphics.CompositeShaderProto
import com.podca.sdui.protocol.ui.graphics.VerticesProto
import com.podca.sdui.protocol.ui.graphics.VertexModeProto
import com.podca.sdui.protocol.ui.graphics.colorspace.ColorModelProto
import com.podca.sdui.protocol.ui.graphics.colorspace.ColorSpaceProto
import com.podca.sdui.protocol.ui.graphics.colorspace.CustomRgbColorSpaceProto
import com.podca.sdui.protocol.ui.graphics.colorspace.NamedColorSpaceProto
import com.podca.sdui.protocol.ui.graphics.colorspace.RenderIntentProto
import com.podca.sdui.protocol.ui.graphics.colorspace.TransferParametersProto
import com.podca.sdui.protocol.ui.graphics.colorspace.WhitePointProto
import com.podca.sdui.protocol.ui.graphics.DegreesProto
import com.podca.sdui.protocol.ui.graphics.FilterQualityProto
import com.podca.sdui.protocol.ui.graphics.PathSegmentBoundsProto
import com.podca.sdui.protocol.ui.graphics.TileModeProto
import okio.ByteString

public fun PodcaDegrees(
    value: Float,
): DegreesProto =
    DegreesProto(
        value_ = value,
    )

public fun PodcaFloat16(
    halfValue: Int,
): Float16Proto =
    Float16Proto(
        half_value = halfValue,
    )

public fun PodcaLayerOutsets(
    left: com.podca.sdui.protocol.ui.unit.DpProto? = null,
    top: com.podca.sdui.protocol.ui.unit.DpProto? = null,
    right: com.podca.sdui.protocol.ui.unit.DpProto? = null,
    bottom: com.podca.sdui.protocol.ui.unit.DpProto? = null,
): LayerOutsetsProto =
    LayerOutsetsProto(
        left = left,
        top = top,
        right = right,
        bottom = bottom,
    )

public fun PodcaMatrix(
    values: List<Float> = emptyList(),
): MatrixProto =
    MatrixProto(
        values = values,
    )

public fun PodcaFilterQuality(
    filterQuality: FilterQualityProto = FilterQualityProto.FILTER_QUALITY_NONE,
): FilterQualityProto = filterQuality

public fun PodcaPaintingStyle(
    paintingStyle: PaintingStyleProto = PaintingStyleProto.PAINTING_STYLE_FILL,
): PaintingStyleProto = paintingStyle

public fun PodcaClipOp(
    clipOp: ClipOpProto = ClipOpProto.CLIP_OP_INTERSECT,
): ClipOpProto = clipOp

public fun PodcaPointMode(
    pointMode: PointModeProto = PointModeProto.POINT_MODE_POINTS,
): PointModeProto = pointMode

public fun PodcaVertexMode(
    vertexMode: VertexModeProto = VertexModeProto.VERTEX_MODE_TRIANGLES,
): VertexModeProto = vertexMode

public fun PodcaPathFillType(
    pathFillType: PathFillTypeProto = PathFillTypeProto.PATH_FILL_TYPE_NON_ZERO,
): PathFillTypeProto = pathFillType

public fun PodcaPathOperation(
    pathOperation: PathOperationProto = PathOperationProto.PATH_OPERATION_UNION,
): PathOperationProto = pathOperation

public fun PodcaPathDirection(
    pathDirection: PathDirectionProto = PathDirectionProto.PATH_DIRECTION_CLOCKWISE,
): PathDirectionProto = pathDirection

public fun PodcaImageBitmapConfig(
    config: ImageBitmapConfigProto = ImageBitmapConfigProto.IMAGE_BITMAP_CONFIG_ARGB_8888,
): ImageBitmapConfigProto = config

public fun PodcaBlendMode(
    blendMode: BlendModeProto = BlendModeProto.BLEND_MODE_SRC_OVER,
): BlendModeProto = blendMode

public fun PodcaPathGeometry(
    direction: PathDirectionProto = PathDirectionProto.PATH_DIRECTION_CLOCKWISE,
    contourCount: Int = 0,
): PathGeometryProto =
    PathGeometryProto(
        direction = direction,
        contour_count = contourCount,
    )

public fun PodcaPathHitTester(
    isEmpty: Boolean = false,
): PathHitTesterProto =
    PathHitTesterProto(
        is_empty = isEmpty,
    )

public fun PodcaPathMeasure(
    length: Float = 0f,
    isClosed: Boolean = false,
): PathMeasureProto =
    PathMeasureProto(
        length = length,
        is_closed = isClosed,
    )

public fun PodcaPathSegment(
    type: PathSegmentTypeProto = PathSegmentTypeProto.PATH_SEGMENT_TYPE_DONE,
    points: List<OffsetProto> = emptyList(),
    weight: Float = 0f,
): PathSegmentProto =
    PathSegmentProto(
        type = type,
        points = points,
        weight = weight,
    )

public fun PodcaPathSegmentBounds(
    bounds: RectProto? = null,
): PathSegmentBoundsProto =
    PathSegmentBoundsProto(
        bounds = bounds,
    )

public fun PodcaCubicBezierEvaluation(
    p1: Float = 0f,
    p2: Float = 0f,
    t: Float = 0f,
    value: Float = 0f,
): com.podca.sdui.protocol.ui.graphics.CubicBezierEvaluationProto =
    com.podca.sdui.protocol.ui.graphics.CubicBezierEvaluationProto(
        p1 = p1,
        p2 = p2,
        t = t,
        value_ = value,
    )

public fun PodcaInterval(
    start: Float,
    end: Float,
    dataTag: String = "",
): IntervalProto =
    IntervalProto(
        start = start,
        end = end,
        data_tag = dataTag,
    )

public fun PodcaIntervalQuery(
    start: Float,
    end: Float,
): IntervalQueryProto =
    IntervalQueryProto(
        start = start,
        end = end,
    )

public fun PodcaIntervalTree(
    intervals: List<IntervalProto> = emptyList(),
): IntervalTreeProto =
    IntervalTreeProto(
        intervals = intervals,
    )

public fun PodcaRenderEffectCapability(
    isSupported: Boolean = false,
): com.podca.sdui.protocol.ui.graphics.RenderEffectCapabilityProto =
    com.podca.sdui.protocol.ui.graphics.RenderEffectCapabilityProto(
        is_supported = isSupported,
    )

public fun PodcaBlurEffect(
    renderEffect: com.podca.sdui.protocol.ui.graphics.RenderEffectProto? = null,
    radiusX: Float = 0f,
    radiusY: Float = 0f,
    edgeTreatment: TileModeProto = TileModeProto.TILE_MODE_CLAMP,
): BlurEffectProto =
    BlurEffectProto(
        render_effect = renderEffect,
        radius_x = radiusX,
        radius_y = radiusY,
        edge_treatment = edgeTreatment,
    )

public fun PodcaOffsetEffect(
    renderEffect: com.podca.sdui.protocol.ui.graphics.RenderEffectProto? = null,
    offset: OffsetProto? = null,
): OffsetEffectProto =
    OffsetEffectProto(
        render_effect = renderEffect,
        offset = offset,
    )

public fun PodcaRenderEffect(
    capability: com.podca.sdui.protocol.ui.graphics.RenderEffectCapabilityProto? = null,
    blur: BlurEffectProto? = null,
    offset: OffsetEffectProto? = null,
): com.podca.sdui.protocol.ui.graphics.RenderEffectProto =
    com.podca.sdui.protocol.ui.graphics.RenderEffectProto(
        capability = capability,
        blur = blur,
        offset = offset,
    )

public fun PodcaGraphicsContext(
    canvas: CanvasProto? = null,
    renderEffectCapability: com.podca.sdui.protocol.ui.graphics.RenderEffectCapabilityProto? = null,
    supportsLayers: Boolean = false,
    supportsSoftwareFallback: Boolean = false,
): GraphicsContextProto =
    GraphicsContextProto(
        canvas = canvas,
        render_effect_capability = renderEffectCapability,
        supports_layers = supportsLayers,
        supports_software_fallback = supportsSoftwareFallback,
    )

public fun PodcaCanvasSaveLayer(
    bounds: RectProto? = null,
    paint: PaintProto? = null,
): CanvasSaveLayerProto =
    CanvasSaveLayerProto(
        bounds = bounds,
        paint = paint,
    )

public fun PodcaCanvasTransformTranslate(
    translate: OffsetProto? = null,
): CanvasTransformProto =
    CanvasTransformProto(
        translate = translate,
    )

public fun PodcaCanvasTransformScale(
    scaleX: Float = 1f,
    scaleY: Float = 1f,
): CanvasTransformProto =
    CanvasTransformProto(
        scale = ScaleTransformProto(
            scale_x = scaleX,
            scale_y = scaleY,
        ),
    )

public fun PodcaCanvasTransformRotate(
    degrees: Float = 0f,
): CanvasTransformProto =
    CanvasTransformProto(
        rotate = RotateTransformProto(
            degrees = degrees,
        ),
    )

public fun PodcaCanvasTransformSkew(
    skewX: Float = 0f,
    skewY: Float = 0f,
): CanvasTransformProto =
    CanvasTransformProto(
        skew = SkewTransformProto(
            skew_x = skewX,
            skew_y = skewY,
        ),
    )

public fun PodcaCanvasTransformMatrix(
    matrix: MatrixProto? = null,
): CanvasTransformProto =
    CanvasTransformProto(
        matrix = matrix,
    )

public fun PodcaCanvasClipRect(
    rect: RectProto? = null,
    clipOp: ClipOpProto = ClipOpProto.CLIP_OP_INTERSECT,
): CanvasClipProto =
    CanvasClipProto(
        rect = rect,
        clip_op = clipOp,
    )

public fun PodcaCanvasClipRoundRect(
    roundRect: RoundRectProto? = null,
    clipOp: ClipOpProto = ClipOpProto.CLIP_OP_INTERSECT,
): CanvasClipProto =
    CanvasClipProto(
        round_rect = roundRect,
        clip_op = clipOp,
    )

public fun PodcaCanvasClipPath(
    path: PathProto? = null,
    clipOp: ClipOpProto = ClipOpProto.CLIP_OP_INTERSECT,
): CanvasClipProto =
    CanvasClipProto(
        path = path,
        clip_op = clipOp,
    )

public fun PodcaDrawLine(
    p1: OffsetProto? = null,
    p2: OffsetProto? = null,
    paint: PaintProto? = null,
): DrawLineProto =
    DrawLineProto(
        p1 = p1,
        p2 = p2,
        paint = paint,
    )

public fun PodcaDrawRect(
    rect: RectProto? = null,
    paint: PaintProto? = null,
): DrawRectProto =
    DrawRectProto(
        rect = rect,
        paint = paint,
    )

public fun PodcaDrawRoundRect(
    roundRect: RoundRectProto? = null,
    paint: PaintProto? = null,
): DrawRoundRectProto =
    DrawRoundRectProto(
        round_rect = roundRect,
        paint = paint,
    )

public fun PodcaDrawCircle(
    center: OffsetProto? = null,
    radius: Float = 0f,
    paint: PaintProto? = null,
): DrawCircleProto =
    DrawCircleProto(
        center = center,
        radius = radius,
        paint = paint,
    )

public fun PodcaDrawOval(
    bounds: RectProto? = null,
    paint: PaintProto? = null,
): DrawOvalProto =
    DrawOvalProto(
        bounds = bounds,
        paint = paint,
    )

public fun PodcaDrawArc(
    bounds: RectProto? = null,
    startAngle: Float = 0f,
    sweepAngle: Float = 0f,
    useCenter: Boolean = false,
    paint: PaintProto? = null,
): DrawArcProto =
    DrawArcProto(
        bounds = bounds,
        start_angle = startAngle,
        sweep_angle = sweepAngle,
        use_center = useCenter,
        paint = paint,
    )

public fun PodcaDrawPath(
    path: PathProto? = null,
    paint: PaintProto? = null,
): DrawPathProto =
    DrawPathProto(
        path = path,
        paint = paint,
    )

public fun PodcaDrawImage(
    image: ImageBitmapProto? = null,
    srcOffset: com.podca.sdui.protocol.ui.unit.IntOffsetProto? = null,
    srcSize: com.podca.sdui.protocol.ui.unit.IntSizeProto? = null,
    dstOffset: com.podca.sdui.protocol.ui.unit.IntOffsetProto? = null,
    dstSize: com.podca.sdui.protocol.ui.unit.IntSizeProto? = null,
    paint: PaintProto? = null,
): DrawImageProto =
    DrawImageProto(
        image = image,
        src_offset = srcOffset,
        src_size = srcSize,
        dst_offset = dstOffset,
        dst_size = dstSize,
        paint = paint,
    )

public fun PodcaDrawPoints(
    mode: PointModeProto = PointModeProto.POINT_MODE_POINTS,
    points: List<OffsetProto> = emptyList(),
    paint: PaintProto? = null,
): DrawPointsProto =
    DrawPointsProto(
        mode = mode,
        points = points,
        paint = paint,
    )

public fun PodcaDrawVertices(
    vertices: VerticesProto? = null,
    blendMode: BlendModeProto = BlendModeProto.BLEND_MODE_SRC_OVER,
    paint: PaintProto? = null,
): DrawVerticesProto =
    DrawVerticesProto(
        vertices = vertices,
        blend_mode = blendMode,
        paint = paint,
    )

public fun PodcaCanvasDrawCommandLine(
    line: DrawLineProto? = null,
): CanvasDrawCommandProto =
    CanvasDrawCommandProto(
        line = line,
    )

public fun PodcaCanvasDrawCommandRect(
    rect: DrawRectProto? = null,
): CanvasDrawCommandProto =
    CanvasDrawCommandProto(
        rect = rect,
    )

public fun PodcaCanvasDrawCommandRoundRect(
    roundRect: DrawRoundRectProto? = null,
): CanvasDrawCommandProto =
    CanvasDrawCommandProto(
        round_rect = roundRect,
    )

public fun PodcaCanvasDrawCommandCircle(
    circle: DrawCircleProto? = null,
): CanvasDrawCommandProto =
    CanvasDrawCommandProto(
        circle = circle,
    )

public fun PodcaCanvasDrawCommandOval(
    oval: DrawOvalProto? = null,
): CanvasDrawCommandProto =
    CanvasDrawCommandProto(
        oval = oval,
    )

public fun PodcaCanvasDrawCommandArc(
    arc: DrawArcProto? = null,
): CanvasDrawCommandProto =
    CanvasDrawCommandProto(
        arc = arc,
    )

public fun PodcaCanvasDrawCommandPath(
    path: DrawPathProto? = null,
): CanvasDrawCommandProto =
    CanvasDrawCommandProto(
        path = path,
    )

public fun PodcaCanvasDrawCommandImage(
    image: DrawImageProto? = null,
): CanvasDrawCommandProto =
    CanvasDrawCommandProto(
        image = image,
    )

public fun PodcaCanvasDrawCommandPoints(
    points: DrawPointsProto? = null,
): CanvasDrawCommandProto =
    CanvasDrawCommandProto(
        points = points,
    )

public fun PodcaCanvasDrawCommandVertices(
    vertices: DrawVerticesProto? = null,
): CanvasDrawCommandProto =
    CanvasDrawCommandProto(
        vertices = vertices,
    )

public fun PodcaCanvas(
    transforms: List<CanvasTransformProto> = emptyList(),
    clips: List<CanvasClipProto> = emptyList(),
    saveLayers: List<CanvasSaveLayerProto> = emptyList(),
    commands: List<CanvasDrawCommandProto> = emptyList(),
): CanvasProto =
    CanvasProto(
        transforms = transforms,
        clips = clips,
        save_layers = saveLayers,
        commands = commands,
    )

public fun PodcaNativeCanvas(): com.podca.sdui.protocol.ui.graphics.NativeCanvasProto =
    com.podca.sdui.protocol.ui.graphics.NativeCanvasProto()

public fun PodcaShader(
    linearGradient: LinearGradientShaderProto? = null,
    radialGradient: RadialGradientShaderProto? = null,
    sweepGradient: SweepGradientShaderProto? = null,
    imageShader: ImageShaderProto? = null,
    compositeShader: CompositeShaderProto? = null,
    localMatrix: MatrixProto? = null,
    isSupported: Boolean = false,
): ShaderProto =
    ShaderProto(
        linear_gradient = linearGradient,
        radial_gradient = radialGradient,
        sweep_gradient = sweepGradient,
        image_shader = imageShader,
        composite_shader = compositeShader,
        local_matrix = localMatrix,
        is_supported = isSupported,
    )

public fun PodcaLinearGradientShader(
    from: OffsetProto? = null,
    to: OffsetProto? = null,
    colors: List<ColorProto> = emptyList(),
    colorStops: List<Float> = emptyList(),
    tileMode: TileModeProto = TileModeProto.TILE_MODE_CLAMP,
): LinearGradientShaderProto =
    LinearGradientShaderProto(
        from = from,
        to = to,
        colors = colors,
        color_stops = colorStops,
        tile_mode = tileMode,
    )

public fun PodcaRadialGradientShader(
    center: OffsetProto? = null,
    radius: Float = 0f,
    colors: List<ColorProto> = emptyList(),
    colorStops: List<Float> = emptyList(),
    tileMode: TileModeProto = TileModeProto.TILE_MODE_CLAMP,
): RadialGradientShaderProto =
    RadialGradientShaderProto(
        center = center,
        radius = radius,
        colors = colors,
        color_stops = colorStops,
        tile_mode = tileMode,
    )

public fun PodcaSweepGradientShader(
    center: OffsetProto? = null,
    colors: List<ColorProto> = emptyList(),
    colorStops: List<Float> = emptyList(),
): SweepGradientShaderProto =
    SweepGradientShaderProto(
        center = center,
        colors = colors,
        color_stops = colorStops,
    )

public fun PodcaImageShader(
    image: ImageBitmapProto? = null,
    tileModeX: TileModeProto = TileModeProto.TILE_MODE_CLAMP,
    tileModeY: TileModeProto = TileModeProto.TILE_MODE_CLAMP,
): ImageShaderProto =
    ImageShaderProto(
        image = image,
        tile_mode_x = tileModeX,
        tile_mode_y = tileModeY,
    )

public fun PodcaCompositeShader(
    dst: ShaderProto? = null,
    src: ShaderProto? = null,
    blendMode: BlendModeProto = BlendModeProto.BLEND_MODE_SRC_OVER,
): CompositeShaderProto =
    CompositeShaderProto(
        dst = dst,
        src = src,
        blend_mode = blendMode,
    )

public fun PodcaTransformShader(
    shader: ShaderProto? = null,
    matrix: MatrixProto? = null,
): TransformShaderProto =
    TransformShaderProto(
        shader = shader,
        matrix = matrix,
    )

public fun PodcaImageBitmap(
    width: Int,
    height: Int,
    config: ImageBitmapConfigProto = ImageBitmapConfigProto.IMAGE_BITMAP_CONFIG_ARGB_8888,
    hasAlpha: Boolean = true,
    colorSpace: ColorSpaceProto? = null,
    encodedBytes: ByteString = ByteString.EMPTY,
    pixelBytes: ByteString = ByteString.EMPTY,
    rowBytes: Int = 0,
): ImageBitmapProto =
    ImageBitmapProto(
        width = width,
        height = height,
        config = config,
        has_alpha = hasAlpha,
        color_space = colorSpace,
        encoded_bytes = encodedBytes,
        pixel_bytes = pixelBytes,
        row_bytes = rowBytes,
    )

public fun PodcaColorModel(
    colorModel: ColorModelProto = ColorModelProto.COLOR_MODEL_RGB,
): ColorModelProto = colorModel

public fun PodcaNamedColorSpace(
    namedColorSpace: NamedColorSpaceProto = NamedColorSpaceProto.NAMED_COLOR_SPACE_SRGB,
): NamedColorSpaceProto = namedColorSpace

public fun PodcaRenderIntent(
    renderIntent: RenderIntentProto = RenderIntentProto.RENDER_INTENT_PERCEPTUAL,
): RenderIntentProto = renderIntent

public fun PodcaWhitePoint(
    x: Float,
    y: Float,
): WhitePointProto =
    WhitePointProto(
        x = x,
        y = y,
    )

public fun PodcaTransferParameters(
    gamma: Double = 0.0,
    a: Double = 0.0,
    b: Double = 0.0,
    c: Double = 0.0,
    d: Double = 0.0,
    e: Double = 0.0,
    f: Double = 0.0,
): TransferParametersProto =
    TransferParametersProto(
        gamma = gamma,
        a = a,
        b = b,
        c = c,
        d = d,
        e = e,
        f = f,
    )

public fun PodcaCustomRgbColorSpace(
    name: String,
    primaries: List<Float> = emptyList(),
    whitePoint: WhitePointProto? = null,
    transferParameters: TransferParametersProto? = null,
): CustomRgbColorSpaceProto =
    CustomRgbColorSpaceProto(
        name = name,
        primaries = primaries,
        white_point = whitePoint,
        transfer_parameters = transferParameters,
    )

public fun PodcaColorSpace(
    named: NamedColorSpaceProto? = null,
    customRgb: CustomRgbColorSpaceProto? = null,
): ColorSpaceProto =
    ColorSpaceProto(
        named = named,
        custom_rgb = customRgb,
    )
