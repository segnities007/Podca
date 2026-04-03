package com.podca.sdui.studio.ui.core

import com.podca.sdui.protocol.ui.geometry.OffsetProto
import com.podca.sdui.protocol.ui.geometry.RectProto
import com.podca.sdui.protocol.ui.geometry.RoundRectProto
import com.podca.sdui.protocol.ui.geometry.SizeProto
import com.podca.sdui.protocol.ui.graphics.BlendModeColorFilterProto
import com.podca.sdui.protocol.ui.graphics.BlendModeProto
import com.podca.sdui.protocol.ui.graphics.BrushProto
import com.podca.sdui.protocol.ui.graphics.ColorFilterProto
import com.podca.sdui.protocol.ui.graphics.ColorMatrixColorFilterProto
import com.podca.sdui.protocol.ui.graphics.ColorMatrixProto
import com.podca.sdui.protocol.ui.graphics.ColorProto
import com.podca.sdui.protocol.ui.graphics.CompositeBrushProto
import com.podca.sdui.protocol.ui.graphics.CornerPathEffectProto
import com.podca.sdui.protocol.ui.graphics.DashPathEffectProto
import com.podca.sdui.protocol.ui.graphics.GenericRoundedCornerShapeProto
import com.podca.sdui.protocol.ui.graphics.ChainPathEffectProto
import com.podca.sdui.protocol.ui.graphics.LightingColorFilterProto
import com.podca.sdui.protocol.ui.graphics.LinearGradientProto
import com.podca.sdui.protocol.ui.graphics.OutlineProto
import com.podca.sdui.protocol.ui.graphics.PathEffectProto
import com.podca.sdui.protocol.ui.graphics.PathProto
import com.podca.sdui.protocol.ui.graphics.RadialGradientProto
import com.podca.sdui.protocol.ui.graphics.RectangleShapeProto
import com.podca.sdui.protocol.ui.graphics.ShadowProto
import com.podca.sdui.protocol.ui.graphics.ShapeProto
import com.podca.sdui.protocol.ui.graphics.SolidColorProto
import com.podca.sdui.protocol.ui.graphics.StampedPathEffectProto
import com.podca.sdui.protocol.ui.graphics.StampedPathEffectStyleProto
import com.podca.sdui.protocol.ui.graphics.StrokeCapProto
import com.podca.sdui.protocol.ui.graphics.StrokeJoinProto
import com.podca.sdui.protocol.ui.graphics.SweepGradientProto
import com.podca.sdui.protocol.ui.graphics.TileModeProto
import com.podca.sdui.protocol.ui.graphics.drawscope.DrawStyleProto
import com.podca.sdui.protocol.ui.graphics.drawscope.FillProto
import com.podca.sdui.protocol.ui.graphics.drawscope.StrokeProto
import com.podca.sdui.protocol.ui.unit.DensityProto
import com.podca.sdui.protocol.ui.unit.DpProto
import com.podca.sdui.protocol.ui.unit.LayoutDirectionProto

public fun PodcaSolidColorBrush(
    color: ColorProto? = null,
): BrushProto =
    BrushProto(
        solid_color = SolidColorProto(
            value_ = color,
        ),
    )

public fun PodcaLinearGradientBrush(
    colors: List<ColorProto> = emptyList(),
    stops: List<Float> = emptyList(),
    start: OffsetProto? = null,
    end: OffsetProto? = null,
    tileMode: TileModeProto = TileModeProto.TILE_MODE_CLAMP,
): BrushProto =
    BrushProto(
        linear_gradient = LinearGradientProto(
            colors = colors,
            stops = stops,
            start = start,
            end = end,
            tile_mode = tileMode,
        ),
    )

public fun PodcaRadialGradientBrush(
    colors: List<ColorProto> = emptyList(),
    stops: List<Float> = emptyList(),
    center: OffsetProto? = null,
    radius: Float = 0f,
    tileMode: TileModeProto = TileModeProto.TILE_MODE_CLAMP,
): BrushProto =
    BrushProto(
        radial_gradient = RadialGradientProto(
            colors = colors,
            stops = stops,
            center = center,
            radius = radius,
            tile_mode = tileMode,
        ),
    )

public fun PodcaSweepGradientBrush(
    colors: List<ColorProto> = emptyList(),
    stops: List<Float> = emptyList(),
    center: OffsetProto? = null,
): BrushProto =
    BrushProto(
        sweep_gradient = SweepGradientProto(
            colors = colors,
            stops = stops,
            center = center,
        ),
    )

public fun PodcaCompositeBrush(
    dstBrush: BrushProto? = null,
    srcBrush: BrushProto? = null,
    blendMode: BlendModeProto = BlendModeProto.BLEND_MODE_SRC_OVER,
): BrushProto =
    BrushProto(
        composite = CompositeBrushProto(
            dst_brush = dstBrush,
            src_brush = srcBrush,
            blend_mode = blendMode,
        ),
    )

public fun PodcaShadow(
    color: ColorProto? = null,
    offset: OffsetProto? = null,
    blurRadius: Float = 0f,
): ShadowProto =
    ShadowProto(
        color = color,
        offset = offset,
        blur_radius = blurRadius,
    )

public fun PodcaRectangleShape(): ShapeProto =
    ShapeProto(
        rectangle = true,
    )

public fun PodcaCircleShape(): ShapeProto =
    ShapeProto(
        circle = true,
    )

public fun PodcaRoundedCornerShape(
    corner: DpProto? = null,
): ShapeProto =
    ShapeProto(
        rounded_corner = corner,
    )

public fun PodcaCutCornerShape(
    corner: DpProto? = null,
): ShapeProto =
    ShapeProto(
        cut_corner = corner,
    )

public fun PodcaCustomRoundedCornerShape(
    topStart: DpProto? = null,
    topEnd: DpProto? = null,
    bottomEnd: DpProto? = null,
    bottomStart: DpProto? = null,
): ShapeProto =
    ShapeProto(
        custom_rounded_corner = GenericRoundedCornerShapeProto(
            top_start = topStart,
            top_end = topEnd,
            bottom_end = bottomEnd,
            bottom_start = bottomStart,
        ),
    )

public fun PodcaRectangleShapeInfo(
    size: SizeProto? = null,
    layoutDirection: LayoutDirectionProto = LayoutDirectionProto.LAYOUT_DIRECTION_LTR,
    density: DensityProto? = null,
    isDefault: Boolean = false,
    debugName: String = "",
): RectangleShapeProto =
    RectangleShapeProto(
        size = size,
        layout_direction = layoutDirection,
        density = density,
        is_default = isDefault,
        debug_name = debugName,
    )

public fun PodcaOutlineRectangle(
    rectangle: RectProto? = null,
): OutlineProto =
    OutlineProto(
        rectangle = rectangle,
    )

public fun PodcaOutlineRounded(
    rounded: RoundRectProto? = null,
): OutlineProto =
    OutlineProto(
        rounded = rounded,
    )

public fun PodcaOutlineGeneric(
    generic: PathProto? = null,
): OutlineProto =
    OutlineProto(
        generic = generic,
    )

public fun PodcaColorMatrix(
    values: List<Float> = emptyList(),
): ColorMatrixProto =
    ColorMatrixProto(
        values = values,
    )

public fun PodcaTintColorFilter(
    color: ColorProto? = null,
    blendMode: BlendModeProto = BlendModeProto.BLEND_MODE_SRC_IN,
): ColorFilterProto =
    ColorFilterProto(
        tint = BlendModeColorFilterProto(
            color = color,
            blend_mode = blendMode,
        ),
    )

public fun PodcaColorMatrixColorFilter(
    colorMatrix: ColorMatrixProto? = null,
): ColorFilterProto =
    ColorFilterProto(
        color_matrix = ColorMatrixColorFilterProto(
            color_matrix = colorMatrix,
        ),
    )

public fun PodcaLightingColorFilter(
    multiply: ColorProto? = null,
    add: ColorProto? = null,
): ColorFilterProto =
    ColorFilterProto(
        lighting = LightingColorFilterProto(
            multiply = multiply,
            add = add,
        ),
    )

public fun PodcaDensity(
    density: Float,
    fontScale: Float = 1f,
): DensityProto =
    DensityProto(
        density = density,
        font_scale = fontScale,
    )

public fun PodcaCornerPathEffect(
    radius: Float,
): PathEffectProto =
    PathEffectProto(
        corner = CornerPathEffectProto(
            radius = radius,
        ),
    )

public fun PodcaDashPathEffect(
    intervals: List<Float> = emptyList(),
    phase: Float = 0f,
): PathEffectProto =
    PathEffectProto(
        dash = DashPathEffectProto(
            intervals = intervals,
            phase = phase,
        ),
    )

public fun PodcaChainPathEffect(
    outer: PathEffectProto? = null,
    inner: PathEffectProto? = null,
): PathEffectProto =
    PathEffectProto(
        chain = ChainPathEffectProto(
            outer = outer,
            inner_ = inner,
        ),
    )

public fun PodcaStampedPathEffect(
    shape: PathProto? = null,
    advance: Float = 0f,
    phase: Float = 0f,
    style: StampedPathEffectStyleProto =
        StampedPathEffectStyleProto.STAMPED_PATH_EFFECT_STYLE_TRANSLATE,
): PathEffectProto =
    PathEffectProto(
        stamped = StampedPathEffectProto(
            shape = shape,
            advance = advance,
            phase = phase,
            style = style,
        ),
    )

public fun PodcaFillDrawStyle(): DrawStyleProto =
    DrawStyleProto(
        fill = FillProto(),
    )

public fun PodcaStrokeDrawStyle(
    width: Float = 0f,
    miter: Float = 4f,
    cap: StrokeCapProto = StrokeCapProto.STROKE_CAP_BUTT,
    join: StrokeJoinProto = StrokeJoinProto.STROKE_JOIN_MITER,
    pathEffect: PathEffectProto? = null,
): DrawStyleProto =
    DrawStyleProto(
        stroke = StrokeProto(
            width = width,
            miter = miter,
            cap = cap,
            join = join,
            path_effect = pathEffect,
        ),
    )
