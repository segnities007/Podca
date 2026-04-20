package com.podca.sdui.remote.creation

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.ui.graphics.BlendModeProto
import com.podca.sdui.remote.core.RemoteCanvasOpCodeProto
import com.podca.sdui.remote.core.RemoteCanvasOpProto
import com.podca.sdui.remote.core.RemoteCanvasPathProto
import com.podca.sdui.remote.core.RemoteCanvasPathVerbProto
import com.podca.sdui.remote.core.RemoteCanvasPolylineProto
import com.podca.sdui.remote.core.RemoteCanvasProgramProto
import com.podca.sdui.studio.core.PodcaNode
import okio.ByteString
import okio.ByteString.Companion.toByteString

/** Wire ids for [RemoteCanvasOpProto.draw_image_scale_type] when [RemoteCanvasOpProto.draw_image_scale_type_enabled]. */
public object RemoteCanvasDrawImageScaleType {
    public const val FIT: Int = 0
    public const val CROP: Int = 1
    public const val FILL_BOUNDS: Int = 2
}

private fun remoteCanvasOp(
    code: RemoteCanvasOpCodeProto,
    rectL: Float,
    rectT: Float,
    rectR: Float,
    rectB: Float,
    colorArgb: Int = 0,
    pointerActionId: String = "",
    cornerRadiusDp: Float = 0f,
    cornerRadiusYDp: Float = 0f,
    strokeWidthDp: Float = 0f,
    textBody: String = "",
    fontSizeSp: Float = 0f,
    fontWeight: Int = 0,
    gradientEndColorArgb: Int = 0,
    textAlign: Int = 0,
    gradientAxis: Int = 0,
    textAlignVertical: Int = 0,
    polyline: RemoteCanvasPolylineProto? = null,
    transformRotateDeg: Float = 0f,
    strokeJoin: Int = 0,
    strokeCap: Int = 0,
    transformScaleX: Float = 0f,
    transformScaleY: Float = 0f,
    circleRadiusDp: Float = 0f,
    arcStartDeg: Float = 0f,
    arcSweepDeg: Float = 0f,
    arcUseCenter: Int = 0,
    clipOp: Int = 0,
    transformMatrixValues: List<Float> = emptyList(),
    path: RemoteCanvasPathProto? = null,
    imagePngBytes: ByteString = ByteString.EMPTY,
    imageSrcRectEnabled: Boolean = false,
    imageSrcLPx: Int = 0,
    imageSrcTPx: Int = 0,
    imageSrcRPx: Int = 0,
    imageSrcBPx: Int = 0,
    drawImageAlphaEnabled: Boolean = false,
    drawImageAlpha: Float = 1f,
    drawImageBlendModeEnabled: Boolean = false,
    drawImageBlendMode: Int = BlendModeProto.BLEND_MODE_SRC_OVER.value,
    conditionalCmp: Int = 0,
    conditionalLiteralA: Float = 0f,
    conditionalLiteralB: Float = 0f,
    loopRepeatCount: Int = 0,
    offscreenSkipClearBeforeDraw: Boolean = false,
    offscreenClearColorArgb: Int = 0,
    offscreenDiscardPooledBitmapAfterPop: Boolean = false,
    drawImageFilterQuality: Int = 0,
    textLineHeightSp: Float = 0f,
    conditionalLiteralNanEq: Boolean = false,
    offscreenBitmapId: String = "",
    drawImageScaleTypeEnabled: Boolean = false,
    drawImageScaleType: Int = RemoteCanvasDrawImageScaleType.FILL_BOUNDS,
    conditionalRemoteFloatIdA: String = "",
    conditionalRemoteFloatIdB: String = "",
    conditionalRemoteFloatFallbackToLiteral: Boolean = false,
    drawTextRectTopIsFirstBaseline: Boolean = false,
    drawTextRectBottomIsLastBaseline: Boolean = false,
    drawTextDisableFontPadding: Boolean = false,
    drawImageScaleFactorEnabled: Boolean = false,
    drawImageScaleFactor: Float = 1f,
    drawImageContentDescription: String = "",
    drawImageColorFilterTintEnabled: Boolean = false,
    drawImageColorFilterTintArgb: Int = 0,
    drawImageColorFilterLightingEnabled: Boolean = false,
    drawImageColorFilterLightingMulArgb: Int = 0,
    drawImageColorFilterLightingAddArgb: Int = 0,
    drawImageColorFilterColorMatrixEnabled: Boolean = false,
    drawImageColorFilterColorMatrix: List<Float> = emptyList(),
    drawImageColorFilterTintBlendModeEnabled: Boolean = false,
    drawImageColorFilterTintBlendMode: Int = 5,
    tweenPathTo: RemoteCanvasPathProto? = null,
    tweenPathFraction: Float = 0f,
    tweenPathFractionRemoteFloatId: String = "",
    tweenPathIncompatibleFallback: Int = 0,
    roundedPolygonMorphPolyline: RemoteCanvasPolylineProto? = null,
    roundedPolygonMorphT: Float = 0f,
    textOnPathStartOffsetDp: Float = 0f,
    textOnCircleWarpRadiusOffsetDp: Float = 0f,
    stateLayoutId: Int = 0,
    textAnchorPanXDp: Float = 0f,
    textAnchorPanYDp: Float = 0f,
    drawAnchoredTextFlags: Int = 0,
    drawTextRunStart: Int = 0,
    drawTextRunEnd: Int = -1,
    drawTextRunContextStart: Int = 0,
    drawTextRunContextEnd: Int = 0,
    drawTextRunIsRtl: Boolean = false,
    drawTextLineHeightStyle: Int = 0,
    stateLayoutSemanticsMergeDescendants: Boolean = false,
    stateLayoutSemanticsTestTag: String = "",
    stateLayoutSemanticsContentDescription: String = "",
    conditionalOperandAIsWireNan: Boolean = false,
    conditionalOperandBIsWireNan: Boolean = false,
    pathDrawBlendModeEnabled: Boolean = false,
    pathDrawBlendMode: Int = BlendModeProto.BLEND_MODE_SRC_OVER.value,
    pathDrawAlphaEnabled: Boolean = false,
    pathDrawAlpha: Float = 1f,
    pathDrawColorFilterTintEnabled: Boolean = false,
    pathDrawColorFilterTintArgb: Int = 0,
    pathDrawColorFilterTintBlendModeEnabled: Boolean = false,
    pathDrawColorFilterTintBlendMode: Int = 5,
    strokeDashEnabled: Boolean = false,
    strokeDashOnDp: Float = 0f,
    strokeDashOffDp: Float = 0f,
    strokeDashPhaseDp: Float = 0f,
): RemoteCanvasOpProto =
    RemoteCanvasOpProto(
        code = code,
        rect_l_dp = rectL,
        rect_t_dp = rectT,
        rect_r_dp = rectR,
        rect_b_dp = rectB,
        color_argb = colorArgb,
        pointer_action_id = pointerActionId,
        corner_radius_dp = cornerRadiusDp,
        corner_radius_y_dp = cornerRadiusYDp,
        stroke_width_dp = strokeWidthDp,
        text_body = textBody,
        font_size_sp = fontSizeSp,
        font_weight = fontWeight,
        gradient_end_color_argb = gradientEndColorArgb,
        text_align = textAlign,
        gradient_axis = gradientAxis,
        text_align_vertical = textAlignVertical,
        polyline = polyline,
        transform_rotate_deg = transformRotateDeg,
        stroke_join = strokeJoin,
        stroke_cap = strokeCap,
        transform_scale_x = transformScaleX,
        transform_scale_y = transformScaleY,
        circle_radius_dp = circleRadiusDp,
        arc_start_deg = arcStartDeg,
        arc_sweep_deg = arcSweepDeg,
        arc_use_center = arcUseCenter,
        clip_op = clipOp,
        transform_matrix_values = transformMatrixValues,
        path = path,
        image_png_bytes = imagePngBytes,
        image_src_rect_enabled = imageSrcRectEnabled,
        image_src_l_px = imageSrcLPx,
        image_src_t_px = imageSrcTPx,
        image_src_r_px = imageSrcRPx,
        image_src_b_px = imageSrcBPx,
        draw_image_alpha_enabled = drawImageAlphaEnabled,
        draw_image_alpha = drawImageAlpha,
        draw_image_blend_mode_enabled = drawImageBlendModeEnabled,
        draw_image_blend_mode = drawImageBlendMode,
        conditional_cmp = conditionalCmp,
        conditional_literal_a = conditionalLiteralA,
        conditional_literal_b = conditionalLiteralB,
        loop_repeat_count = loopRepeatCount,
        offscreen_skip_clear_before_draw = offscreenSkipClearBeforeDraw,
        offscreen_clear_color_argb = offscreenClearColorArgb,
        offscreen_discard_pooled_bitmap_after_pop = offscreenDiscardPooledBitmapAfterPop,
        draw_image_filter_quality = drawImageFilterQuality,
        text_line_height_sp = textLineHeightSp,
        conditional_literal_nan_eq = conditionalLiteralNanEq,
        offscreen_bitmap_id = offscreenBitmapId,
        draw_image_scale_type_enabled = drawImageScaleTypeEnabled,
        draw_image_scale_type = drawImageScaleType,
        conditional_remote_float_id_a = conditionalRemoteFloatIdA,
        conditional_remote_float_id_b = conditionalRemoteFloatIdB,
        conditional_remote_float_fallback_to_literal = conditionalRemoteFloatFallbackToLiteral,
        draw_text_rect_top_is_first_baseline = drawTextRectTopIsFirstBaseline,
        draw_text_rect_bottom_is_last_baseline = drawTextRectBottomIsLastBaseline,
        draw_text_disable_font_padding = drawTextDisableFontPadding,
        draw_image_scale_factor_enabled = drawImageScaleFactorEnabled,
        draw_image_scale_factor = drawImageScaleFactor,
        draw_image_content_description = drawImageContentDescription,
        draw_image_color_filter_tint_enabled = drawImageColorFilterTintEnabled,
        draw_image_color_filter_tint_argb = drawImageColorFilterTintArgb,
        draw_image_color_filter_lighting_enabled = drawImageColorFilterLightingEnabled,
        draw_image_color_filter_lighting_mul_argb = drawImageColorFilterLightingMulArgb,
        draw_image_color_filter_lighting_add_argb = drawImageColorFilterLightingAddArgb,
        draw_image_color_filter_color_matrix_enabled = drawImageColorFilterColorMatrixEnabled,
        draw_image_color_filter_color_matrix = drawImageColorFilterColorMatrix,
        draw_image_color_filter_tint_blend_mode_enabled = drawImageColorFilterTintBlendModeEnabled,
        draw_image_color_filter_tint_blend_mode = drawImageColorFilterTintBlendMode,
        path_draw_blend_mode_enabled = pathDrawBlendModeEnabled,
        path_draw_blend_mode = pathDrawBlendMode,
        path_draw_alpha_enabled = pathDrawAlphaEnabled,
        path_draw_alpha = pathDrawAlpha,
        path_draw_color_filter_tint_enabled = pathDrawColorFilterTintEnabled,
        path_draw_color_filter_tint_argb = pathDrawColorFilterTintArgb,
        path_draw_color_filter_tint_blend_mode_enabled = pathDrawColorFilterTintBlendModeEnabled,
        path_draw_color_filter_tint_blend_mode = pathDrawColorFilterTintBlendMode,
        stroke_dash_enabled = strokeDashEnabled,
        stroke_dash_on_dp = strokeDashOnDp,
        stroke_dash_off_dp = strokeDashOffDp,
        stroke_dash_phase_dp = strokeDashPhaseDp,
        tween_path_to = tweenPathTo ?: RemoteCanvasPathProto(),
        tween_path_fraction = tweenPathFraction,
        tween_path_fraction_remote_float_id = tweenPathFractionRemoteFloatId,
        tween_path_incompatible_fallback = tweenPathIncompatibleFallback,
        rounded_polygon_morph_polyline = roundedPolygonMorphPolyline ?: RemoteCanvasPolylineProto(),
        rounded_polygon_morph_t = roundedPolygonMorphT,
        text_on_path_start_offset_dp = textOnPathStartOffsetDp,
        text_on_circle_warp_radius_offset_dp = textOnCircleWarpRadiusOffsetDp,
        state_layout_id = stateLayoutId,
        text_anchor_pan_x_dp = textAnchorPanXDp,
        text_anchor_pan_y_dp = textAnchorPanYDp,
        draw_anchored_text_flags = drawAnchoredTextFlags,
        draw_text_run_start = drawTextRunStart,
        draw_text_run_end = drawTextRunEnd,
        draw_text_run_context_start = drawTextRunContextStart,
        draw_text_run_context_end = drawTextRunContextEnd,
        draw_text_run_is_rtl = drawTextRunIsRtl,
        draw_text_line_height_style = drawTextLineHeightStyle,
        state_layout_semantics_merge_descendants = stateLayoutSemanticsMergeDescendants,
        state_layout_semantics_test_tag = stateLayoutSemanticsTestTag,
        state_layout_semantics_content_description = stateLayoutSemanticsContentDescription,
        conditional_operand_a_is_wire_nan = conditionalOperandAIsWireNan,
        conditional_operand_b_is_wire_nan = conditionalOperandBIsWireNan,
    )

public fun canvasFillRectDp(
    leftDp: Float,
    topDp: Float,
    rightDp: Float,
    bottomDp: Float,
    colorArgb: Int,
): RemoteCanvasOpProto =
    remoteCanvasOp(
        RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_FILL_RECT,
        leftDp,
        topDp,
        rightDp,
        bottomDp,
        colorArgb = colorArgb,
    )

/**
 * Draw [pngBytes] (PNG) scaled into dst rect (dp). Matches AndroidX `drawScaledBitmap` with full source
 * (same as `drawBitmap` then scale-to-rect). For a **source crop** in bitmap pixel space, use [canvasDrawImagePngSubrectInRectDp].
 * Optional [alpha] (0..1) maps to Compose `drawImage` alpha when set (subset of bitmap + paint opacity on AndroidX).
 * Optional [blendMode] maps to Compose `drawImage` blendMode; wire uses the same numeric ids as [BlendModeProto].
 * Optional [filterQuality] maps to Compose `FilterQuality`: 0 = default (Medium), 1 = Low, 2 = Medium, 3 = High (clamped by the player).
 * Optional [scaleType] sets uniform scaling inside the dst rect ([RemoteCanvasDrawImageScaleType] — AndroidX `ScaleType` subset).
 * Optional [drawImageScaleFactorEnabled] / [drawImageScaleFactor]: uniform scale of the **post scale-type** dst inside `rect_*` (re-centered).
 * Optional non-blank [drawImageContentDescription]: semantics overlay on `rect_*` (pointer clip stack; not pixel-identical to Fit/Crop inner bounds).
 * Optional [colorFilterTintArgb]: when non-null, enables Compose `ColorFilter.tint` (default SrcIn; see [colorFilterTintBlendMode]).
 * Optional [colorFilterLightingMulArgb]: when non-null, enables Compose `ColorFilter.lighting` with [colorFilterLightingAddArgb] (AndroidX `LightingColorFilter` subset; when combined with tint, **lighting wins** in v1).
 * Optional [colorFilterColorMatrix]: when non-null and **exactly 20** finite floats (row-major 4×5), enables Compose `ColorFilter.colorMatrix` (when combined with tint and no lighting, **matrix wins**; lighting still wins over matrix).
 * Optional [colorFilterTintBlendMode]: when non-null and [colorFilterTintArgb] is set, selects the `ColorFilter.tint` blend mode (same numeric ids as [BlendModeProto]); legacy tint without this uses SrcIn.
 * Canonical sample payloads live in `remote-core` as [com.podca.sdui.remote.core.RemoteCanvasWireFixtures].
 */
public fun canvasDrawImagePngInRectDp(
    leftDp: Float,
    topDp: Float,
    rightDp: Float,
    bottomDp: Float,
    pngBytes: ByteArray,
    alpha: Float? = null,
    blendMode: BlendModeProto? = null,
    filterQuality: Int = 0,
    scaleType: Int? = null,
    drawImageScaleFactorEnabled: Boolean = false,
    drawImageScaleFactor: Float = 1f,
    drawImageContentDescription: String = "",
    colorFilterTintArgb: Int? = null,
    colorFilterLightingMulArgb: Int? = null,
    colorFilterLightingAddArgb: Int = 0,
    colorFilterColorMatrix: List<Float>? = null,
    colorFilterTintBlendMode: BlendModeProto? = null,
): RemoteCanvasOpProto {
    val clamped = alpha?.coerceIn(0f, 1f)
    val bm = blendMode
    val st = scaleType?.coerceIn(RemoteCanvasDrawImageScaleType.FIT, RemoteCanvasDrawImageScaleType.FILL_BOUNDS)
    val lightingOn = colorFilterLightingMulArgb != null
    val matrixList =
        colorFilterColorMatrix?.takeIf { m ->
            m.size == 20 && m.all { it.isFinite() }
        }
    val matrixOn = matrixList != null
    val tintOn = colorFilterTintArgb != null
    val tintBlendOn = tintOn && colorFilterTintBlendMode != null
    return remoteCanvasOp(
        RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_DRAW_IMAGE,
        leftDp,
        topDp,
        rightDp,
        bottomDp,
        imagePngBytes = pngBytes.toByteString(),
        drawImageAlphaEnabled = clamped != null,
        drawImageAlpha = clamped ?: 1f,
        drawImageBlendModeEnabled = bm != null,
        drawImageBlendMode = (bm?.value ?: BlendModeProto.BLEND_MODE_SRC_OVER.value).coerceIn(0, 28),
        drawImageFilterQuality = filterQuality.coerceIn(0, 3),
        drawImageScaleTypeEnabled = st != null,
        drawImageScaleType = st ?: RemoteCanvasDrawImageScaleType.FILL_BOUNDS,
        drawImageScaleFactorEnabled = drawImageScaleFactorEnabled,
        drawImageScaleFactor = drawImageScaleFactor,
        drawImageContentDescription = drawImageContentDescription,
        drawImageColorFilterTintEnabled = tintOn,
        drawImageColorFilterTintArgb = colorFilterTintArgb ?: 0,
        drawImageColorFilterLightingEnabled = lightingOn,
        drawImageColorFilterLightingMulArgb = colorFilterLightingMulArgb ?: 0,
        drawImageColorFilterLightingAddArgb = if (lightingOn) colorFilterLightingAddArgb else 0,
        drawImageColorFilterColorMatrixEnabled = matrixOn,
        drawImageColorFilterColorMatrix = matrixList ?: emptyList(),
        drawImageColorFilterTintBlendModeEnabled = tintBlendOn,
        drawImageColorFilterTintBlendMode =
            (colorFilterTintBlendMode?.value ?: BlendModeProto.BLEND_MODE_SRC_IN.value).coerceIn(0, 28),
    )
}

/**
 * Draw a **sub-rectangle of the decoded PNG** (pixel bounds: [srcLeftPx], [srcTopPx] inclusive,
 * [srcRightPx], [srcBottomPx] exclusive) scaled into dst rect (dp). Matches AndroidX `drawScaledBitmap` src/dst rects
 * Optional [scaleType] and [filterQuality] match [canvasDrawImagePngInRectDp].
 * Optional [alpha] (0..1) and [blendMode] are the same as [canvasDrawImagePngInRectDp].
 * Optional [drawImageScaleFactorEnabled] / [drawImageScaleFactor] and [drawImageContentDescription] match [canvasDrawImagePngInRectDp].
 * Optional [colorFilterTintArgb] matches [canvasDrawImagePngInRectDp].
 * Optional [colorFilterLightingMulArgb] / [colorFilterLightingAddArgb] match [canvasDrawImagePngInRectDp].
 * Optional [colorFilterColorMatrix] matches [canvasDrawImagePngInRectDp].
 * Optional [colorFilterTintBlendMode] matches [canvasDrawImagePngInRectDp].
 */
public fun canvasDrawImagePngSubrectInRectDp(
    dstLeftDp: Float,
    dstTopDp: Float,
    dstRightDp: Float,
    dstBottomDp: Float,
    pngBytes: ByteArray,
    srcLeftPx: Int,
    srcTopPx: Int,
    srcRightPx: Int,
    srcBottomPx: Int,
    alpha: Float? = null,
    blendMode: BlendModeProto? = null,
    filterQuality: Int = 0,
    scaleType: Int? = null,
    drawImageScaleFactorEnabled: Boolean = false,
    drawImageScaleFactor: Float = 1f,
    drawImageContentDescription: String = "",
    colorFilterTintArgb: Int? = null,
    colorFilterLightingMulArgb: Int? = null,
    colorFilterLightingAddArgb: Int = 0,
    colorFilterColorMatrix: List<Float>? = null,
    colorFilterTintBlendMode: BlendModeProto? = null,
): RemoteCanvasOpProto {
    val clamped = alpha?.coerceIn(0f, 1f)
    val bm = blendMode
    val st = scaleType?.coerceIn(RemoteCanvasDrawImageScaleType.FIT, RemoteCanvasDrawImageScaleType.FILL_BOUNDS)
    val lightingOn = colorFilterLightingMulArgb != null
    val matrixList =
        colorFilterColorMatrix?.takeIf { m ->
            m.size == 20 && m.all { it.isFinite() }
        }
    val matrixOn = matrixList != null
    val tintOn = colorFilterTintArgb != null
    val tintBlendOn = tintOn && colorFilterTintBlendMode != null
    return remoteCanvasOp(
        RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_DRAW_IMAGE,
        dstLeftDp,
        dstTopDp,
        dstRightDp,
        dstBottomDp,
        imagePngBytes = pngBytes.toByteString(),
        imageSrcRectEnabled = true,
        imageSrcLPx = srcLeftPx,
        imageSrcTPx = srcTopPx,
        imageSrcRPx = srcRightPx,
        imageSrcBPx = srcBottomPx,
        drawImageAlphaEnabled = clamped != null,
        drawImageAlpha = clamped ?: 1f,
        drawImageBlendModeEnabled = bm != null,
        drawImageBlendMode = (bm?.value ?: BlendModeProto.BLEND_MODE_SRC_OVER.value).coerceIn(0, 28),
        drawImageFilterQuality = filterQuality.coerceIn(0, 3),
        drawImageScaleTypeEnabled = st != null,
        drawImageScaleType = st ?: RemoteCanvasDrawImageScaleType.FILL_BOUNDS,
        drawImageScaleFactorEnabled = drawImageScaleFactorEnabled,
        drawImageScaleFactor = drawImageScaleFactor,
        drawImageContentDescription = drawImageContentDescription,
        drawImageColorFilterTintEnabled = tintOn,
        drawImageColorFilterTintArgb = colorFilterTintArgb ?: 0,
        drawImageColorFilterLightingEnabled = lightingOn,
        drawImageColorFilterLightingMulArgb = colorFilterLightingMulArgb ?: 0,
        drawImageColorFilterLightingAddArgb = if (lightingOn) colorFilterLightingAddArgb else 0,
        drawImageColorFilterColorMatrixEnabled = matrixOn,
        drawImageColorFilterColorMatrix = matrixList ?: emptyList(),
        drawImageColorFilterTintBlendModeEnabled = tintBlendOn,
        drawImageColorFilterTintBlendMode =
            (colorFilterTintBlendMode?.value ?: BlendModeProto.BLEND_MODE_SRC_IN.value).coerceIn(0, 28),
    )
}

/**
 * Comparison kinds for [canvasConditionalBegin]; values match AndroidX
 * [androidx.compose.remote.core.operations.ConditionalOperations] `TYPE_*` (byte 0..5).
 */
public object RemoteCanvasConditionalCmp {
    public const val EQ: Int = 0
    public const val NEQ: Int = 1
    public const val LT: Int = 2
    public const val LTE: Int = 3
    public const val GT: Int = 4
    public const val GTE: Int = 5
}

/**
 * Opens a conditional block: subsequent ops run only if [comparisonType] holds on the resolved operands
 * **and** every enclosing `CONDITIONAL_BEGIN` branch is active. Operands default to [literalA] / [literalB];
 * non-empty [remoteFloatIdA] / [remoteFloatIdB] select values from the host **remote canvas float map** at paint time
 * (`remote-player-compose` + `PodcaRuntime`; unknown id → NaN unless [remoteFloatFallbackToLiteral]). [literalNanEq]: when true, EQ treats two NaNs as equal (see [evalRemoteCanvasConditionalBranch]).
 * [operandWireNanA] / [operandWireNanB]: wire-explicit **NaN** on that operand side (see [resolveRemoteCanvasConditionalOperands]).
 * Pair with [canvasConditionalEnd].
 */
public fun canvasConditionalBegin(
    comparisonType: Int,
    literalA: Float,
    literalB: Float,
    literalNanEq: Boolean = false,
    remoteFloatIdA: String = "",
    remoteFloatIdB: String = "",
    remoteFloatFallbackToLiteral: Boolean = false,
    operandWireNanA: Boolean = false,
    operandWireNanB: Boolean = false,
): RemoteCanvasOpProto =
    remoteCanvasOp(
        RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_CONDITIONAL_BEGIN,
        rectL = 0f,
        rectT = 0f,
        rectR = 0f,
        rectB = 0f,
        conditionalCmp = comparisonType.coerceIn(RemoteCanvasConditionalCmp.EQ, RemoteCanvasConditionalCmp.GTE),
        conditionalLiteralA = literalA,
        conditionalLiteralB = literalB,
        conditionalLiteralNanEq = literalNanEq,
        conditionalRemoteFloatIdA = remoteFloatIdA,
        conditionalRemoteFloatIdB = remoteFloatIdB,
        conditionalRemoteFloatFallbackToLiteral = remoteFloatFallbackToLiteral,
        conditionalOperandAIsWireNan = operandWireNanA,
        conditionalOperandBIsWireNan = operandWireNanB,
    )

/** Closes the innermost [canvasConditionalBegin] block. Extra ENDs are ignored (lenient). */
public fun canvasConditionalEnd(): RemoteCanvasOpProto =
    remoteCanvasOp(
        RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_CONDITIONAL_END,
        rectL = 0f,
        rectT = 0f,
        rectR = 0f,
        rectB = 0f,
    )

/**
 * Opens a loop block executed [repeatCount] times (0..512). Pair with [canvasLoopEnd].
 * The player expands loops to a flat op list before painting; **do not nest LOOP inside CONDITIONAL** (undefined).
 */
public fun canvasLoopBegin(repeatCount: Int): RemoteCanvasOpProto =
    remoteCanvasOp(
        RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_LOOP_BEGIN,
        rectL = 0f,
        rectT = 0f,
        rectR = 0f,
        rectB = 0f,
        loopRepeatCount = repeatCount.coerceIn(0, 512),
    )

/** Closes the innermost [canvasLoopBegin]. Extra ENDs are passed through on expand (lenient). */
public fun canvasLoopEnd(): RemoteCanvasOpProto =
    remoteCanvasOp(
        RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_LOOP_END,
        rectL = 0f,
        rectT = 0f,
        rectR = 0f,
        rectB = 0f,
    )

/**
 * Opens a **state layout** span (AndroidX `startStateLayout` subset): following ops belong to [stateLayoutId] until [canvasStateLayoutEnd].
 * The player keeps only the branch matching [PodcaRuntime.remoteCanvasStateLayoutActiveId] (or default id resolution when unset — see [com.podca.sdui.remote.core.filterRemoteCanvasStateLayoutBlocksForPlayback]).
 * Optional [semanticsMergeDescendants] / non-blank [semanticsTestTag] / [semanticsContentDescription]: root `Modifier.semantics` subset when this branch is active (see [com.podca.sdui.remote.core.remoteCanvasStateLayoutSemanticsHintsForPlayback]).
 */
public fun canvasStateLayoutBegin(
    stateLayoutId: Int,
    semanticsMergeDescendants: Boolean = false,
    semanticsTestTag: String = "",
    semanticsContentDescription: String = "",
): RemoteCanvasOpProto =
    remoteCanvasOp(
        RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_STATE_LAYOUT_BEGIN,
        rectL = 0f,
        rectT = 0f,
        rectR = 0f,
        rectB = 0f,
        stateLayoutId = stateLayoutId,
        stateLayoutSemanticsMergeDescendants = semanticsMergeDescendants,
        stateLayoutSemanticsTestTag = semanticsTestTag,
        stateLayoutSemanticsContentDescription = semanticsContentDescription,
    )

/** Closes the innermost [canvasStateLayoutBegin]. */
public fun canvasStateLayoutEnd(): RemoteCanvasOpProto =
    remoteCanvasOp(
        RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_STATE_LAYOUT_END,
        rectL = 0f,
        rectT = 0f,
        rectR = 0f,
        rectB = 0f,
    )

/**
 * Opens an offscreen block: ops until [canvasPopOffscreenRender] render into a bitmap sized to the
 * rect width×height (dp), then the bitmap is drawn into that rect on the parent (after clip/transform).
 * Inner coordinates use (0,0) as the top-left of that rect in **local** dp space. Pair with [canvasPopOffscreenRender].
 *
 * @param skipClearBeforeDraw When false (default), the player clears the offscreen bitmap with [clearColorArgb]
 *   (0xAARRGGBB, default transparent) before inner ops — AndroidX `drawToOffscreenBitmap(bitmap, clearColor, …)`.
 *   When true, no clear step — AndroidX overload without clear (buffer may retain prior pixels if reused).
 * @param discardPooledBitmapAfterPop When true with non-blank [bitmapId], the player evicts that pool entry after compositing this block (explicit release subset).
 */
public fun canvasPushOffscreenRenderDp(
    leftDp: Float,
    topDp: Float,
    rightDp: Float,
    bottomDp: Float,
    skipClearBeforeDraw: Boolean = false,
    clearColorArgb: Int = 0,
    bitmapId: String = "",
    discardPooledBitmapAfterPop: Boolean = false,
): RemoteCanvasOpProto =
    remoteCanvasOp(
        RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_PUSH_OFFSCREEN_RENDER,
        leftDp,
        topDp,
        rightDp,
        bottomDp,
        offscreenSkipClearBeforeDraw = skipClearBeforeDraw,
        offscreenClearColorArgb = clearColorArgb,
        offscreenBitmapId = bitmapId,
        offscreenDiscardPooledBitmapAfterPop = discardPooledBitmapAfterPop,
    )

/** Closes the innermost [canvasPushOffscreenRenderDp]. Extra POPs are ignored when unmatched (lenient). */
public fun canvasPopOffscreenRender(): RemoteCanvasOpProto =
    remoteCanvasOp(
        RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_POP_OFFSCREEN_RENDER,
        rectL = 0f,
        rectT = 0f,
        rectR = 0f,
        rectB = 0f,
    )

/**
 * Clears all named offscreen bitmaps currently pooled by the player.
 * Use this when a scene transition should not reuse prior [canvasPushOffscreenRenderDp] named buffers.
 */
public fun canvasClearOffscreenBitmapPool(): RemoteCanvasOpProto =
    remoteCanvasOp(
        RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_CLEAR_OFFSCREEN_BITMAP_POOL,
        rectL = 0f,
        rectT = 0f,
        rectR = 0f,
        rectB = 0f,
    )

public fun canvasFillRoundRectDp(
    leftDp: Float,
    topDp: Float,
    rightDp: Float,
    bottomDp: Float,
    cornerRadiusDp: Float,
    colorArgb: Int,
    cornerRadiusYDp: Float = 0f,
): RemoteCanvasOpProto =
    remoteCanvasOp(
        RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_FILL_ROUND_RECT,
        leftDp,
        topDp,
        rightDp,
        bottomDp,
        colorArgb = colorArgb,
        cornerRadiusDp = cornerRadiusDp,
        cornerRadiusYDp = cornerRadiusYDp,
    )

public fun canvasStrokeRectDp(
    leftDp: Float,
    topDp: Float,
    rightDp: Float,
    bottomDp: Float,
    strokeWidthDp: Float,
    colorArgb: Int,
    strokeJoin: Int = 0,
    strokeCap: Int = 0,
): RemoteCanvasOpProto =
    remoteCanvasOp(
        RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_STROKE_RECT,
        leftDp,
        topDp,
        rightDp,
        bottomDp,
        colorArgb = colorArgb,
        strokeWidthDp = strokeWidthDp,
        strokeJoin = strokeJoin,
        strokeCap = strokeCap,
    )

public fun canvasFillOvalDp(
    leftDp: Float,
    topDp: Float,
    rightDp: Float,
    bottomDp: Float,
    colorArgb: Int,
): RemoteCanvasOpProto =
    remoteCanvasOp(
        RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_FILL_OVAL,
        leftDp,
        topDp,
        rightDp,
        bottomDp,
        colorArgb = colorArgb,
    )

public fun canvasStrokeOvalDp(
    leftDp: Float,
    topDp: Float,
    rightDp: Float,
    bottomDp: Float,
    strokeWidthDp: Float,
    colorArgb: Int,
    strokeJoin: Int = 0,
    strokeCap: Int = 0,
): RemoteCanvasOpProto =
    remoteCanvasOp(
        RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_STROKE_OVAL,
        leftDp,
        topDp,
        rightDp,
        bottomDp,
        colorArgb = colorArgb,
        strokeWidthDp = strokeWidthDp,
        strokeJoin = strokeJoin,
        strokeCap = strokeCap,
    )

/** Center ([centerXDp], [centerYDp]); [radiusDp] > 0. Aligns with AndroidX `RemoteCanvas.drawCircle` (fill). */
public fun canvasFillCircleDp(
    centerXDp: Float,
    centerYDp: Float,
    radiusDp: Float,
    colorArgb: Int,
): RemoteCanvasOpProto =
    remoteCanvasOp(
        RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_FILL_CIRCLE,
        centerXDp,
        centerYDp,
        0f,
        0f,
        colorArgb = colorArgb,
        circleRadiusDp = radiusDp,
    )

public fun canvasStrokeCircleDp(
    centerXDp: Float,
    centerYDp: Float,
    radiusDp: Float,
    strokeWidthDp: Float,
    colorArgb: Int,
    strokeJoin: Int = 0,
    strokeCap: Int = 0,
): RemoteCanvasOpProto =
    remoteCanvasOp(
        RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_STROKE_CIRCLE,
        centerXDp,
        centerYDp,
        0f,
        0f,
        colorArgb = colorArgb,
        strokeWidthDp = strokeWidthDp,
        strokeJoin = strokeJoin,
        strokeCap = strokeCap,
        circleRadiusDp = radiusDp,
    )

/** Oval [leftDp]..[rightDp], [topDp]..[bottomDp]; angles in degrees (Compose `drawArc`, `useCenter` false). Matches RemoteCanvas `drawArc` with stroke. */
public fun canvasDrawArcDp(
    leftDp: Float,
    topDp: Float,
    rightDp: Float,
    bottomDp: Float,
    startAngleDeg: Float,
    sweepAngleDeg: Float,
    strokeWidthDp: Float,
    colorArgb: Int,
    strokeJoin: Int = 0,
    strokeCap: Int = 0,
): RemoteCanvasOpProto =
    remoteCanvasOp(
        RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_DRAW_ARC,
        leftDp,
        topDp,
        rightDp,
        bottomDp,
        colorArgb = colorArgb,
        strokeWidthDp = strokeWidthDp,
        strokeJoin = strokeJoin,
        strokeCap = strokeCap,
        arcStartDeg = startAngleDeg,
        arcSweepDeg = sweepAngleDeg,
        arcUseCenter = 0,
    )

/** Same bounds/angles; filled pie sector (`useCenter` true). Matches RemoteCanvas `drawArc` with useCenter / `drawSector`. */
public fun canvasFillSectorDp(
    leftDp: Float,
    topDp: Float,
    rightDp: Float,
    bottomDp: Float,
    startAngleDeg: Float,
    sweepAngleDeg: Float,
    colorArgb: Int,
): RemoteCanvasOpProto =
    remoteCanvasOp(
        RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_DRAW_ARC,
        leftDp,
        topDp,
        rightDp,
        bottomDp,
        colorArgb = colorArgb,
        arcStartDeg = startAngleDeg,
        arcSweepDeg = sweepAngleDeg,
        arcUseCenter = 1,
    )

/**
 * Linear gradient in [rect]: [startColorArgb] → [endColorArgb].
 * [gradientAxis] 0 = horizontal (start left), 1 = vertical (start top).
 */
public fun canvasFillLinearGradientRectDp(
    leftDp: Float,
    topDp: Float,
    rightDp: Float,
    bottomDp: Float,
    startColorArgb: Int,
    endColorArgb: Int,
    gradientAxis: Int = 0,
): RemoteCanvasOpProto =
    remoteCanvasOp(
        RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_FILL_LINEAR_GRADIENT_RECT,
        leftDp,
        topDp,
        rightDp,
        bottomDp,
        colorArgb = startColorArgb,
        gradientEndColorArgb = endColorArgb,
        gradientAxis = gradientAxis,
    )

/** Radial gradient: [innerColorArgb] at center → [outerColorArgb] toward rect edges (outer 0 = solid inner). */
public fun canvasFillRadialGradientRectDp(
    leftDp: Float,
    topDp: Float,
    rightDp: Float,
    bottomDp: Float,
    innerColorArgb: Int,
    outerColorArgb: Int,
): RemoteCanvasOpProto =
    remoteCanvasOp(
        RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_FILL_RADIAL_GRADIENT_RECT,
        leftDp,
        topDp,
        rightDp,
        bottomDp,
        colorArgb = innerColorArgb,
        gradientEndColorArgb = outerColorArgb,
    )

/** Closed polygon fill; [xyDp] pairs in dp, even length ≥ 6 (≥ 3 vertices). */
public fun canvasFillPolylineDp(
    colorArgb: Int,
    xyDp: List<Float>,
): RemoteCanvasOpProto {
    require(xyDp.size >= 6 && xyDp.size % 2 == 0) {
        "canvasFillPolylineDp: need an even number of floats ≥ 6 (x,y pairs, closed polygon)"
    }
    return remoteCanvasOp(
        code = RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_FILL_POLYLINE,
        rectL = 0f,
        rectT = 0f,
        rectR = 0f,
        rectB = 0f,
        colorArgb = colorArgb,
        polyline = RemoteCanvasPolylineProto(xy_dp = xyDp),
    )
}

public fun canvasFillPolylineDp(
    colorArgb: Int,
    vararg xyDp: Float,
): RemoteCanvasOpProto = canvasFillPolylineDp(colorArgb, xyDp.toList())

/** Open polyline stroke; [xyDp] pairs in dp, even length ≥ 4. */
public fun canvasStrokePolylineDp(
    colorArgb: Int,
    strokeWidthDp: Float,
    xyDp: List<Float>,
    strokeJoin: Int = 0,
    strokeCap: Int = 0,
): RemoteCanvasOpProto {
    require(xyDp.size >= 4 && xyDp.size % 2 == 0) {
        "canvasStrokePolylineDp: need an even number of floats ≥ 4 (x,y pairs)"
    }
    return remoteCanvasOp(
        code = RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_STROKE_POLYLINE,
        rectL = 0f,
        rectT = 0f,
        rectR = 0f,
        rectB = 0f,
        colorArgb = colorArgb,
        strokeWidthDp = strokeWidthDp,
        polyline = RemoteCanvasPolylineProto(xy_dp = xyDp),
        strokeJoin = strokeJoin,
        strokeCap = strokeCap,
    )
}

public fun canvasStrokePolylineDp(
    colorArgb: Int,
    strokeWidthDp: Float,
    vararg xyDp: Float,
): RemoteCanvasOpProto = canvasStrokePolylineDp(colorArgb, strokeWidthDp, xyDp.toList())

/** Builds [RemoteCanvasPathProto] (dp) for [canvasFillPathDp] / [canvasStrokePathDp] / [canvasPushClipPathDp]; first command must be [moveTo]. */
public class RemoteCanvasPathBuilder {
    private val verbs = mutableListOf<RemoteCanvasPathVerbProto>()
    private val coords = mutableListOf<Float>()

    public fun moveTo(xDp: Float, yDp: Float) {
        verbs.add(RemoteCanvasPathVerbProto.REMOTE_PATH_VERB_MOVE_TO)
        coords.add(xDp)
        coords.add(yDp)
    }

    public fun lineTo(xDp: Float, yDp: Float) {
        verbs.add(RemoteCanvasPathVerbProto.REMOTE_PATH_VERB_LINE_TO)
        coords.add(xDp)
        coords.add(yDp)
    }

    public fun quadraticTo(x1Dp: Float, y1Dp: Float, x2Dp: Float, y2Dp: Float) {
        verbs.add(RemoteCanvasPathVerbProto.REMOTE_PATH_VERB_QUADRATIC_TO)
        coords.add(x1Dp)
        coords.add(y1Dp)
        coords.add(x2Dp)
        coords.add(y2Dp)
    }

    public fun cubicTo(
        x1Dp: Float,
        y1Dp: Float,
        x2Dp: Float,
        y2Dp: Float,
        x3Dp: Float,
        y3Dp: Float,
    ) {
        verbs.add(RemoteCanvasPathVerbProto.REMOTE_PATH_VERB_CUBIC_TO)
        coords.add(x1Dp)
        coords.add(y1Dp)
        coords.add(x2Dp)
        coords.add(y2Dp)
        coords.add(x3Dp)
        coords.add(y3Dp)
    }

    public fun close() {
        verbs.add(RemoteCanvasPathVerbProto.REMOTE_PATH_VERB_CLOSE)
    }

    public fun build(): RemoteCanvasPathProto =
        RemoteCanvasPathProto(verbs = verbs.toList(), coords_dp = coords.toList())
}

public inline fun buildRemoteCanvasPath(block: RemoteCanvasPathBuilder.() -> Unit): RemoteCanvasPathProto =
    RemoteCanvasPathBuilder().apply(block).build()

/**
 * Fill path built with [RemoteCanvasPathBuilder] / [buildRemoteCanvasPath]. Matches AndroidX `RemoteCanvas.drawPath` (fill).
 * Optional [pathBlendMode] / [pathAlpha] map to Compose `DrawScope.drawPath` blendMode / alpha (see [RemoteCanvasOpProto.path_draw_*]).
 * Optional [pathColorFilterTintArgb] applies Compose `ColorFilter.tint` (subset of AndroidX paint color filter).
 */
public fun canvasFillPathDp(
    path: RemoteCanvasPathProto,
    colorArgb: Int,
    pathBlendMode: BlendModeProto? = null,
    pathAlpha: Float? = null,
    pathColorFilterTintArgb: Int? = null,
    pathColorFilterTintBlendMode: BlendModeProto? = null,
): RemoteCanvasOpProto {
    val blendOn = pathBlendMode != null
    val alphaOn = pathAlpha != null
    val tintOn = pathColorFilterTintArgb != null
    val a = pathAlpha?.coerceIn(0f, 1f)
    return remoteCanvasOp(
        code = RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_FILL_PATH,
        rectL = 0f,
        rectT = 0f,
        rectR = 0f,
        rectB = 0f,
        colorArgb = colorArgb,
        path = path,
        pathDrawBlendModeEnabled = blendOn,
        pathDrawBlendMode = (pathBlendMode?.value ?: BlendModeProto.BLEND_MODE_SRC_OVER.value).coerceIn(0, 28),
        pathDrawAlphaEnabled = alphaOn,
        pathDrawAlpha = if (alphaOn) (a?.takeIf { it.isFinite() } ?: 1f) else 1f,
        pathDrawColorFilterTintEnabled = tintOn,
        pathDrawColorFilterTintArgb = pathColorFilterTintArgb ?: 0,
        pathDrawColorFilterTintBlendModeEnabled = tintOn && pathColorFilterTintBlendMode != null,
        pathDrawColorFilterTintBlendMode = (pathColorFilterTintBlendMode?.value ?: 5).coerceIn(0, 28),
    )
}

/**
 * Stroke of a path **morphed** between [pathFrom] and [pathTo] at [fraction] in `[0, 1]` (AndroidX `drawTweenPath` subset:
 * literal fraction only; **dp** coordinate lerp when verb streams and coord lengths match — see `RemoteCanvasProgram.proto`).
 * When they do not match, [incompatibleFallback] selects **skip** / stroke **from** / stroke **to** (see [com.podca.sdui.remote.core.RemoteCanvasTweenPathIncompatibleFallbackWire]).
 * Optional [fractionRemoteFloatId]: when non-blank, the player reads blend **t** from the host named-float map (same as `CONDITIONAL_BEGIN`); otherwise [fraction] is used.
 */
public fun canvasDrawTweenPathStrokeDp(
    pathFrom: RemoteCanvasPathProto,
    pathTo: RemoteCanvasPathProto,
    fraction: Float,
    strokeWidthDp: Float,
    colorArgb: Int,
    strokeJoin: Int = 0,
    strokeCap: Int = 0,
    incompatibleFallback: Int = 0,
    fractionRemoteFloatId: String = "",
    pathBlendMode: BlendModeProto? = null,
    pathAlpha: Float? = null,
    pathColorFilterTintArgb: Int? = null,
    pathColorFilterTintBlendMode: BlendModeProto? = null,
    strokeDashOnDp: Float? = null,
    strokeDashOffDp: Float? = null,
    strokeDashPhaseDp: Float = 0f,
): RemoteCanvasOpProto {
    val blendOn = pathBlendMode != null
    val alphaOn = pathAlpha != null
    val tintOn = pathColorFilterTintArgb != null
    val dashOn = strokeDashOnDp
    val dashOff = strokeDashOffDp
    val dashEnabled =
        dashOn != null &&
            dashOff != null &&
            dashOn.isFinite() &&
            dashOff.isFinite() &&
            dashOn > 0f &&
            dashOff > 0f
    val a = pathAlpha?.coerceIn(0f, 1f)
    return remoteCanvasOp(
        code = RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_DRAW_TWEEN_PATH,
        rectL = 0f,
        rectT = 0f,
        rectR = 0f,
        rectB = 0f,
        colorArgb = colorArgb,
        strokeWidthDp = strokeWidthDp,
        strokeJoin = strokeJoin,
        strokeCap = strokeCap,
        path = pathFrom,
        tweenPathTo = pathTo,
        tweenPathFraction = fraction,
        tweenPathFractionRemoteFloatId = fractionRemoteFloatId,
        tweenPathIncompatibleFallback = incompatibleFallback,
        pathDrawBlendModeEnabled = blendOn,
        pathDrawBlendMode = (pathBlendMode?.value ?: BlendModeProto.BLEND_MODE_SRC_OVER.value).coerceIn(0, 28),
        pathDrawAlphaEnabled = alphaOn,
        pathDrawAlpha = if (alphaOn) (a?.takeIf { it.isFinite() } ?: 1f) else 1f,
        pathDrawColorFilterTintEnabled = tintOn,
        pathDrawColorFilterTintArgb = pathColorFilterTintArgb ?: 0,
        pathDrawColorFilterTintBlendModeEnabled = tintOn && pathColorFilterTintBlendMode != null,
        pathDrawColorFilterTintBlendMode = (pathColorFilterTintBlendMode?.value ?: 5).coerceIn(0, 28),
        strokeDashEnabled = dashEnabled,
        strokeDashOnDp = if (dashEnabled) dashOn!! else 0f,
        strokeDashOffDp = if (dashEnabled) dashOff!! else 0f,
        strokeDashPhaseDp = if (dashEnabled && strokeDashPhaseDp.isFinite()) strokeDashPhaseDp else 0f,
    )
}

/** Closed convex polygon fill with uniform [cornerRadiusDp] rounding (quadratic corners); optional vertex morph. */
public fun canvasFillRoundedPolygonDp(
    polyline: RemoteCanvasPolylineProto,
    cornerRadiusDp: Float,
    colorArgb: Int,
    morphPolyline: RemoteCanvasPolylineProto? = null,
    morphT: Float = 0f,
): RemoteCanvasOpProto =
    remoteCanvasOp(
        code = RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_FILL_ROUNDED_POLYGON,
        rectL = 0f,
        rectT = 0f,
        rectR = 0f,
        rectB = 0f,
        colorArgb = colorArgb,
        cornerRadiusDp = cornerRadiusDp,
        polyline = polyline,
        roundedPolygonMorphPolyline = morphPolyline,
        roundedPolygonMorphT = morphT,
    )

/** Same as [canvasFillRoundedPolygonDp] but stroked (stroke fields like [canvasStrokePathDp]). */
public fun canvasStrokeRoundedPolygonDp(
    polyline: RemoteCanvasPolylineProto,
    cornerRadiusDp: Float,
    strokeWidthDp: Float,
    colorArgb: Int,
    strokeJoin: Int = 0,
    strokeCap: Int = 0,
    morphPolyline: RemoteCanvasPolylineProto? = null,
    morphT: Float = 0f,
): RemoteCanvasOpProto =
    remoteCanvasOp(
        code = RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_STROKE_ROUNDED_POLYGON,
        rectL = 0f,
        rectT = 0f,
        rectR = 0f,
        rectB = 0f,
        colorArgb = colorArgb,
        cornerRadiusDp = cornerRadiusDp,
        strokeWidthDp = strokeWidthDp,
        strokeJoin = strokeJoin,
        strokeCap = strokeCap,
        polyline = polyline,
        roundedPolygonMorphPolyline = morphPolyline,
        roundedPolygonMorphT = morphT,
    )

/**
 * [text] along an **open** baseline [path] using **MOVE_TO + LINE_TO** verbs only (dp, same grammar as [canvasStrokePathDp] paths).
 * [textAlignAlongPath]: 0 = start at [startOffsetAlongPathDp], 1 = center string on path length, 2 = end toward path end.
 * Subset of AndroidX `drawTextOnPath` (`drawTextOnCircle` / `drawAnchoredText` are separate ops — see `ANDROIDX_REMOTE_MAP.md`).
 */
public fun canvasDrawTextOnPathMoveLinePathDp(
    path: RemoteCanvasPathProto,
    text: String,
    colorArgb: Int,
    fontSizeSp: Float = 12f,
    fontWeight: Int = 0,
    textAlignAlongPath: Int = 0,
    startOffsetAlongPathDp: Float = 0f,
    textLineHeightSp: Float = 0f,
): RemoteCanvasOpProto =
    remoteCanvasOp(
        code = RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_DRAW_TEXT_ON_PATH,
        rectL = 0f,
        rectT = 0f,
        rectR = 0f,
        rectB = 0f,
        colorArgb = colorArgb,
        textBody = text,
        fontSizeSp = fontSizeSp,
        fontWeight = fontWeight,
        textAlign = textAlignAlongPath,
        path = path,
        textLineHeightSp = textLineHeightSp,
        textOnPathStartOffsetDp = startOffsetAlongPathDp,
    )

/**
 * [text] on a circle: center ([centerXDp], [centerYDp]), [radiusDp], [arcStartDeg] / [arcSweepDeg] (0 sweep = full 360° layout, same angle sense as `DRAW_ARC`).
 * [warpRadiusOffsetDp] shifts the baseline radially (AndroidX `warpRadiusOffset` subset). [arcLengthStartOffsetDp] is consumed as [text_on_path_start_offset_dp] (arc length from [arcStartDeg]).
 * [textAlignAlongArc]: 0 = start, 1 = center on arc, 2 = end toward sweep end.
 */
public fun canvasDrawTextOnCircleDp(
    centerXDp: Float,
    centerYDp: Float,
    radiusDp: Float,
    text: String,
    colorArgb: Int,
    fontSizeSp: Float = 12f,
    fontWeight: Int = 0,
    arcStartDeg: Float = 0f,
    arcSweepDeg: Float = 0f,
    warpRadiusOffsetDp: Float = 0f,
    arcLengthStartOffsetDp: Float = 0f,
    textAlignAlongArc: Int = 0,
    textLineHeightSp: Float = 0f,
): RemoteCanvasOpProto =
    remoteCanvasOp(
        code = RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_DRAW_TEXT_ON_CIRCLE,
        rectL = centerXDp,
        rectT = centerYDp,
        rectR = 0f,
        rectB = 0f,
        colorArgb = colorArgb,
        textBody = text,
        fontSizeSp = fontSizeSp,
        fontWeight = fontWeight,
        textAlign = textAlignAlongArc,
        circleRadiusDp = radiusDp,
        arcStartDeg = arcStartDeg,
        arcSweepDeg = arcSweepDeg,
        textLineHeightSp = textLineHeightSp,
        textOnPathStartOffsetDp = arcLengthStartOffsetDp,
        textOnCircleWarpRadiusOffsetDp = warpRadiusOffsetDp,
    )

/**
 * [text] with anchor ([anchorXDp],[anchorYDp]) in dp, optional [panXDp]/[panYDp], and [anchoredTextFlags]:
 * **0** = anchor at measured layout **top-left**; **1** = **center** of measured box; **2** = **left** of first line **baseline**;
 * **3** = **horizontal center** of first line at first line **baseline**. Max layout box matches [canvasDrawTextAtDp] via [maxWidthDp]/[maxHeightDp] (wired as [rect_r_dp]/[rect_b_dp]; 0 = same defaults as DRAW_TEXT_AT).
 * Subset of AndroidX `drawAnchoredText` (wire does not carry the full upstream `flags` bitmap).
 */
public fun canvasDrawTextAnchoredDp(
    anchorXDp: Float,
    anchorYDp: Float,
    text: String,
    colorArgb: Int,
    fontSizeSp: Float = 12f,
    fontWeight: Int = 0,
    maxWidthDp: Float = 0f,
    maxHeightDp: Float = 0f,
    panXDp: Float = 0f,
    panYDp: Float = 0f,
    anchoredTextFlags: Int = 0,
    textLineHeightSp: Float = 0f,
): RemoteCanvasOpProto =
    remoteCanvasOp(
        code = RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_DRAW_TEXT_ANCHORED,
        rectL = anchorXDp,
        rectT = anchorYDp,
        rectR = maxWidthDp,
        rectB = maxHeightDp,
        colorArgb = colorArgb,
        textBody = text,
        fontSizeSp = fontSizeSp,
        fontWeight = fontWeight,
        textAlign = 0,
        textAlignVertical = 0,
        textLineHeightSp = textLineHeightSp,
        textAnchorPanXDp = panXDp,
        textAnchorPanYDp = panYDp,
        drawAnchoredTextFlags = anchoredTextFlags,
    )

/**
 * Stroke path; matches AndroidX `RemoteCanvas.drawPath` (stroke).
 * Optional [pathBlendMode] / [pathAlpha] / [pathColorFilterTintArgb]: see [canvasFillPathDp].
 */
public fun canvasStrokePathDp(
    path: RemoteCanvasPathProto,
    strokeWidthDp: Float,
    colorArgb: Int,
    strokeJoin: Int = 0,
    strokeCap: Int = 0,
    pathBlendMode: BlendModeProto? = null,
    pathAlpha: Float? = null,
    pathColorFilterTintArgb: Int? = null,
    pathColorFilterTintBlendMode: BlendModeProto? = null,
    strokeDashOnDp: Float? = null,
    strokeDashOffDp: Float? = null,
    strokeDashPhaseDp: Float = 0f,
): RemoteCanvasOpProto {
    val blendOn = pathBlendMode != null
    val alphaOn = pathAlpha != null
    val tintOn = pathColorFilterTintArgb != null
    val dashOn = strokeDashOnDp
    val dashOff = strokeDashOffDp
    val dashEnabled =
        dashOn != null &&
            dashOff != null &&
            dashOn.isFinite() &&
            dashOff.isFinite() &&
            dashOn > 0f &&
            dashOff > 0f
    val a = pathAlpha?.coerceIn(0f, 1f)
    return remoteCanvasOp(
        code = RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_STROKE_PATH,
        rectL = 0f,
        rectT = 0f,
        rectR = 0f,
        rectB = 0f,
        colorArgb = colorArgb,
        strokeWidthDp = strokeWidthDp,
        strokeJoin = strokeJoin,
        strokeCap = strokeCap,
        path = path,
        pathDrawBlendModeEnabled = blendOn,
        pathDrawBlendMode = (pathBlendMode?.value ?: BlendModeProto.BLEND_MODE_SRC_OVER.value).coerceIn(0, 28),
        pathDrawAlphaEnabled = alphaOn,
        pathDrawAlpha = if (alphaOn) (a?.takeIf { it.isFinite() } ?: 1f) else 1f,
        pathDrawColorFilterTintEnabled = tintOn,
        pathDrawColorFilterTintArgb = pathColorFilterTintArgb ?: 0,
        pathDrawColorFilterTintBlendModeEnabled = tintOn && pathColorFilterTintBlendMode != null,
        pathDrawColorFilterTintBlendMode = (pathColorFilterTintBlendMode?.value ?: 5).coerceIn(0, 28),
        strokeDashEnabled = dashEnabled,
        strokeDashOnDp = if (dashEnabled) dashOn!! else 0f,
        strokeDashOffDp = if (dashEnabled) dashOff!! else 0f,
        strokeDashPhaseDp = if (dashEnabled && strokeDashPhaseDp.isFinite()) strokeDashPhaseDp else 0f,
    )
}

public fun canvasStrokeRoundRectDp(
    leftDp: Float,
    topDp: Float,
    rightDp: Float,
    bottomDp: Float,
    cornerRadiusDp: Float,
    strokeWidthDp: Float,
    colorArgb: Int,
    strokeJoin: Int = 0,
    strokeCap: Int = 0,
    cornerRadiusYDp: Float = 0f,
): RemoteCanvasOpProto =
    remoteCanvasOp(
        RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_STROKE_ROUND_RECT,
        leftDp,
        topDp,
        rightDp,
        bottomDp,
        colorArgb = colorArgb,
        cornerRadiusDp = cornerRadiusDp,
        cornerRadiusYDp = cornerRadiusYDp,
        strokeWidthDp = strokeWidthDp,
        strokeJoin = strokeJoin,
        strokeCap = strokeCap,
    )

/** Line from ([x1Dp], [y1Dp]) to ([x2Dp], [y2Dp]). */
public fun canvasDrawLineDp(
    x1Dp: Float,
    y1Dp: Float,
    x2Dp: Float,
    y2Dp: Float,
    strokeWidthDp: Float,
    colorArgb: Int,
    strokeCap: Int = 0,
): RemoteCanvasOpProto =
    remoteCanvasOp(
        RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_DRAW_LINE,
        x1Dp,
        y1Dp,
        x2Dp,
        y2Dp,
        colorArgb = colorArgb,
        strokeWidthDp = strokeWidthDp,
        strokeCap = strokeCap,
    )

/**
 * UTF-8 [text] laid out in the given rect; [fontSizeSp] defaults to 12 in the player if <= 0.
 * [fontWeight] 0 → w400, else clamped 100..1000.
 * [textAlign] 0 = Start, 1 = Center, 2 = End (horizontal).
 * [textAlignVertical] 0 = Top, 1 = Center, 2 = Bottom within the rect.
 * Optional [lineHeightSp] (> 0): Compose [TextStyle.lineHeight] and tighter max-line hints in the player.
 * Optional [lineHeightStyleWire]: see [RemoteCanvasOpProto.draw_text_line_height_style] (ignored when [lineHeightSp] ≤ 0).
 * Optional [rectTopIsFirstBaseline]: when true, [topDp] is the first line baseline (see [RemoteCanvasOpProto.draw_text_rect_top_is_first_baseline]).
 * Optional [rectBottomIsLastBaseline]: when true and [rectTopIsFirstBaseline] is false, [bottomDp] is the last line baseline (see [RemoteCanvasOpProto.draw_text_rect_bottom_is_last_baseline]).
 * Optional [disableFontPadding]: when true, Android player disables Compose font padding (see [RemoteCanvasOpProto.draw_text_disable_font_padding]).
 */
public fun canvasDrawTextDp(
    leftDp: Float,
    topDp: Float,
    rightDp: Float,
    bottomDp: Float,
    text: String,
    fontSizeSp: Float,
    fontWeight: Int,
    colorArgb: Int,
    textAlign: Int = 0,
    textAlignVertical: Int = 0,
    lineHeightSp: Float = 0f,
    rectTopIsFirstBaseline: Boolean = false,
    rectBottomIsLastBaseline: Boolean = false,
    disableFontPadding: Boolean = false,
    lineHeightStyleWire: Int = 0,
): RemoteCanvasOpProto =
    remoteCanvasOp(
        RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_DRAW_TEXT,
        leftDp,
        topDp,
        rightDp,
        bottomDp,
        colorArgb = colorArgb,
        textBody = text,
        fontSizeSp = fontSizeSp,
        fontWeight = fontWeight,
        textAlign = textAlign,
        textAlignVertical = textAlignVertical,
        textLineHeightSp = lineHeightSp.coerceAtLeast(0f),
        drawTextRectTopIsFirstBaseline = rectTopIsFirstBaseline,
        drawTextRectBottomIsLastBaseline = rectBottomIsLastBaseline,
        drawTextDisableFontPadding = disableFontPadding,
        drawTextLineHeightStyle = lineHeightStyleWire,
    )

/**
 * Text at layout origin ([xDp],[yDp]) in dp (default: top-left of [BasicText] box; optional baseline mode as [canvasDrawTextDp]).
 * [maxWidthDp] ≤ 0 → use canvas width − x; [maxHeightDp] ≤ 0 → about five lines from font size.
 * Other params as [canvasDrawTextDp]. Maps a subset of AndroidX `RemoteCanvas.drawText` to Compose overlay.
 */
public fun canvasDrawTextAtDp(
    xDp: Float,
    yDp: Float,
    text: String,
    fontSizeSp: Float,
    fontWeight: Int,
    colorArgb: Int,
    maxWidthDp: Float = 0f,
    maxHeightDp: Float = 0f,
    textAlign: Int = 0,
    textAlignVertical: Int = 0,
    lineHeightSp: Float = 0f,
    rectTopIsFirstBaseline: Boolean = false,
    rectBottomIsLastBaseline: Boolean = false,
    disableFontPadding: Boolean = false,
    lineHeightStyleWire: Int = 0,
): RemoteCanvasOpProto =
    remoteCanvasOp(
        RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_DRAW_TEXT_AT,
        rectL = xDp,
        rectT = yDp,
        rectR = maxWidthDp,
        rectB = maxHeightDp,
        colorArgb = colorArgb,
        textBody = text,
        fontSizeSp = fontSizeSp,
        fontWeight = fontWeight,
        textAlign = textAlign,
        textAlignVertical = textAlignVertical,
        textLineHeightSp = lineHeightSp.coerceAtLeast(0f),
        drawTextRectTopIsFirstBaseline = rectTopIsFirstBaseline,
        drawTextRectBottomIsLastBaseline = rectBottomIsLastBaseline,
        drawTextDisableFontPadding = disableFontPadding,
        drawTextLineHeightStyle = lineHeightStyleWire,
    )

/**
 * Like [canvasDrawTextAtDp] but draws only the UTF-16 slice [[runStart],[runEnd])) of [fullText] (`runEnd` **−1** = end of string).
 * [contextStart]/[contextEndWire] select the shaping window on the same buffer (**[contextEndWire] ≤ 0** = end of string, mirroring run end −1).
 * [isRtl] maps to Compose `TextDirection.Rtl` on the laid-out context.
 */
public fun canvasDrawTextRunAtDp(
    xDp: Float,
    yDp: Float,
    fullText: String,
    runStart: Int,
    runEnd: Int,
    fontSizeSp: Float,
    fontWeight: Int,
    colorArgb: Int,
    maxWidthDp: Float = 0f,
    maxHeightDp: Float = 0f,
    textAlign: Int = 0,
    textAlignVertical: Int = 0,
    lineHeightSp: Float = 0f,
    rectTopIsFirstBaseline: Boolean = false,
    rectBottomIsLastBaseline: Boolean = false,
    contextStart: Int = 0,
    contextEndWire: Int = 0,
    isRtl: Boolean = false,
    lineHeightStyleWire: Int = 0,
    disableFontPadding: Boolean = false,
): RemoteCanvasOpProto =
    remoteCanvasOp(
        RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_DRAW_TEXT_RUN,
        rectL = xDp,
        rectT = yDp,
        rectR = maxWidthDp,
        rectB = maxHeightDp,
        colorArgb = colorArgb,
        textBody = fullText,
        fontSizeSp = fontSizeSp,
        fontWeight = fontWeight,
        textAlign = textAlign,
        textAlignVertical = textAlignVertical,
        textLineHeightSp = lineHeightSp.coerceAtLeast(0f),
        drawTextRectTopIsFirstBaseline = rectTopIsFirstBaseline,
        drawTextRectBottomIsLastBaseline = rectBottomIsLastBaseline,
        drawTextRunStart = runStart,
        drawTextRunEnd = runEnd,
        drawTextRunContextStart = contextStart,
        drawTextRunContextEnd = contextEndWire,
        drawTextRunIsRtl = isRtl,
        drawTextLineHeightStyle = lineHeightStyleWire,
        drawTextDisableFontPadding = disableFontPadding,
    )

/**
 * Clip with axis-aligned rect until [canvasPopClip].
 * [clipOp] 0 = Compose `ClipOp.Intersect`, 1 = `ClipOp.Difference` (matches AndroidX `RemoteCanvas.clipRect` default vs subtract).
 */
public fun canvasPushClipRectDp(
    leftDp: Float,
    topDp: Float,
    rightDp: Float,
    bottomDp: Float,
    clipOp: Int = 0,
): RemoteCanvasOpProto =
    remoteCanvasOp(
        RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_PUSH_CLIP_RECT,
        leftDp,
        topDp,
        rightDp,
        bottomDp,
        clipOp = clipOp,
    )

/** Rounded-rect clip; radii as [canvasFillRoundRectDp]. [clipOp] as [canvasPushClipRectDp]. Compose `clipPath(RoundRect)` on player. */
public fun canvasPushClipRoundRectDp(
    leftDp: Float,
    topDp: Float,
    rightDp: Float,
    bottomDp: Float,
    cornerRadiusDp: Float,
    clipOp: Int = 0,
    cornerRadiusYDp: Float = 0f,
): RemoteCanvasOpProto =
    remoteCanvasOp(
        RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_PUSH_CLIP_ROUND_RECT,
        leftDp,
        topDp,
        rightDp,
        bottomDp,
        cornerRadiusDp = cornerRadiusDp,
        cornerRadiusYDp = cornerRadiusYDp,
        clipOp = clipOp,
    )

/** Closed polygon clip in dp (even [xyDp] length ≥ 6). [clipOp] as [canvasPushClipRectDp]. */
public fun canvasPushClipPolylineDp(
    xyDp: List<Float>,
    clipOp: Int = 0,
): RemoteCanvasOpProto {
    require(xyDp.size >= 6 && xyDp.size % 2 == 0) {
        "canvasPushClipPolylineDp: need an even number of floats ≥ 6 (x,y pairs, closed polygon)"
    }
    return remoteCanvasOp(
        code = RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_PUSH_CLIP_POLYLINE,
        rectL = 0f,
        rectT = 0f,
        rectR = 0f,
        rectB = 0f,
        polyline = RemoteCanvasPolylineProto(xy_dp = xyDp),
        clipOp = clipOp,
    )
}

/** Clip to arbitrary path until [canvasPopClip]; [clipOp] as [canvasPushClipRectDp]. Matches AndroidX `RemoteCanvas.clipPath`. */
public fun canvasPushClipPathDp(
    path: RemoteCanvasPathProto,
    clipOp: Int = 0,
): RemoteCanvasOpProto =
    remoteCanvasOp(
        code = RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_PUSH_CLIP_PATH,
        rectL = 0f,
        rectT = 0f,
        rectR = 0f,
        rectB = 0f,
        clipOp = clipOp,
        path = path,
    )

public fun canvasPopClip(): RemoteCanvasOpProto =
    remoteCanvasOp(
        RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_POP_CLIP,
        0f,
        0f,
        0f,
        0f,
    )

/** Snapshot clip + transform stacks until [canvasPopCanvasRestore]. Matches AndroidX `RemoteCanvas.save()`. */
public fun canvasPushCanvasSave(): RemoteCanvasOpProto =
    remoteCanvasOp(
        RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_PUSH_CANVAS_SAVE,
        0f,
        0f,
        0f,
        0f,
    )

/** Restore last [canvasPushCanvasSave]; extra pop is a no-op. Matches AndroidX `RemoteCanvas.restore()`. */
public fun canvasPopCanvasRestore(): RemoteCanvasOpProto =
    remoteCanvasOp(
        RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_POP_CANVAS_RESTORE,
        0f,
        0f,
        0f,
        0f,
    )

/** Pivot ([pivotXDp], [pivotYDp]) in dp; clockwise [degrees] (negative allowed). Affects subsequent Canvas vector draws until [canvasPopTransform]. */
public fun canvasPushRotateDegDp(
    pivotXDp: Float,
    pivotYDp: Float,
    degrees: Float,
): RemoteCanvasOpProto =
    remoteCanvasOp(
        RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_PUSH_ROTATE_DEG,
        pivotXDp,
        pivotYDp,
        0f,
        0f,
        transformRotateDeg = degrees,
    )

/** Same as [canvasPushRotateDegDp] with pivot **(0, 0) dp** — AndroidX `rotate(degrees)` overload without explicit pivot (origin). */
public fun canvasPushRotateDegAroundOriginDp(degrees: Float): RemoteCanvasOpProto =
    canvasPushRotateDegDp(0f, 0f, degrees)

/** Delta ([dxDp], [dyDp]) in dp; aligns with AndroidX Remote Compose `RemoteCanvas.translate`. */
public fun canvasPushTranslateDp(
    dxDp: Float,
    dyDp: Float,
): RemoteCanvasOpProto =
    remoteCanvasOp(
        RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_PUSH_TRANSLATE_DP,
        dxDp,
        dyDp,
        0f,
        0f,
    )

/** Pivot ([pivotXDp], [pivotYDp]); axis scale [scaleX], [scaleY] (>0). Aligns with AndroidX `RemoteCanvas.scale` (pivot). */
public fun canvasPushScaleDp(
    pivotXDp: Float,
    pivotYDp: Float,
    scaleX: Float,
    scaleY: Float,
): RemoteCanvasOpProto =
    remoteCanvasOp(
        RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_PUSH_SCALE_DP,
        pivotXDp,
        pivotYDp,
        0f,
        0f,
        transformScaleX = scaleX,
        transformScaleY = scaleY,
    )

/** Same as [canvasPushScaleDp] with pivot **(0, 0) dp** — AndroidX `scale(sx, sy)` without explicit pivot. */
public fun canvasPushScaleAtOriginDp(
    scaleX: Float,
    scaleY: Float,
): RemoteCanvasOpProto =
    canvasPushScaleDp(0f, 0f, scaleX, scaleY)

/**
 * Row-major 4×4 as [androidx.compose.ui.graphics.Matrix] `values` (16 floats). Concat until [canvasPopTransform].
 * Matches AndroidX `RemoteCanvas.transform(matrix)`.
 */
public fun canvasPushTransformMatrixRowMajor4x4(values: List<Float>): RemoteCanvasOpProto {
    require(values.size == 16) {
        "canvasPushTransformMatrixRowMajor4x4: need exactly 16 floats (Matrix.values row-major)"
    }
    return remoteCanvasOp(
        RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_PUSH_TRANSFORM_MATRIX,
        0f,
        0f,
        0f,
        0f,
        transformMatrixValues = values,
    )
}

public fun canvasPopTransform(): RemoteCanvasOpProto =
    remoteCanvasOp(
        RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_POP_TRANSFORM,
        0f,
        0f,
        0f,
        0f,
    )

/** Invisible hit rect: tap dispatches [actionId] on the enclosing `PodcaRemoteCanvasProgram` node key (op-stream touch slot). */
public fun canvasPointerInputRectDp(
    leftDp: Float,
    topDp: Float,
    rightDp: Float,
    bottomDp: Float,
    actionId: String,
): RemoteCanvasOpProto =
    remoteCanvasOp(
        RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_POINTER_INPUT_RECT,
        leftDp,
        topDp,
        rightDp,
        bottomDp,
        pointerActionId = actionId,
    )

/**
 * @param wireOpsetVersion Monotonic program opset revision; player refuses the whole program when
 *   value exceeds its supported ceiling (see `PodcaRenderRemoteCanvasProgramNode` and AGENTS / remote README).
 * @param loopExpandMaxRepeatPerBlock Wire [RemoteCanvasProgramProto.loop_expand_max_repeat_per_block] (**0** = player default cap per `LOOP_BEGIN`).
 * @param offscreenBitmapPoolMaxEntries Wire [RemoteCanvasProgramProto.offscreen_bitmap_pool_max_entries] (**0** = player default named pool cap).
 */
public fun remoteCanvasProgram(
    widthDp: Float,
    heightDp: Float,
    vararg ops: RemoteCanvasOpProto,
    wireOpsetVersion: Int = 1,
    loopExpandMaxRepeatPerBlock: Int = 0,
    offscreenBitmapPoolMaxEntries: Int = 0,
): RemoteCanvasProgramProto =
    RemoteCanvasProgramProto(
        width_dp = widthDp,
        height_dp = heightDp,
        ops = ops.toList(),
        wire_opset_version = wireOpsetVersion,
        loop_expand_max_repeat_per_block = loopExpandMaxRepeatPerBlock,
        offscreen_bitmap_pool_max_entries = offscreenBitmapPoolMaxEntries,
    )

/**
 * Embeds a [RemoteCanvasProgramProto] as `remote.CanvasProgram` (op-stream playback in `remote-player-compose`).
 */
@Composable
public fun PodcaRemoteCanvasProgram(
    program: RemoteCanvasProgramProto,
    key: String = "",
) {
    PodcaNode(
        type = "remote.CanvasProgram",
        message = program,
        encode = RemoteCanvasProgramProto.ADAPTER::encode,
        key = key,
    )
}
