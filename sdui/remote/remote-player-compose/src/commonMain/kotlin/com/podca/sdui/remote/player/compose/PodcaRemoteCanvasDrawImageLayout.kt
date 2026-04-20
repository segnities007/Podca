package com.podca.sdui.remote.player.compose

import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt

/** Wire [RemoteCanvasOpProto.draw_image_scale_type] when [draw_image_scale_type_enabled] is true. */
internal object RemoteCanvasDrawImageScaleTypeWire {
    const val FIT: Int = 0
    const val CROP: Int = 1
    const val FILL_BOUNDS: Int = 2
}

/**
 * Dst placement for [androidx.compose.ui.graphics.drawscope.DrawScope.drawImage]:
 * uniform scale of the source (pixel size [srcWidthPx]×[srcHeightPx]) inside the axis box
 * [[leftPx],[topPx]) .. [[rightPx],[bottomPx]) in **px** (caller converts dp).
 */
internal fun drawImageDstOffsetAndSizePx(
    leftPx: Float,
    topPx: Float,
    rightPx: Float,
    bottomPx: Float,
    srcWidthPx: Int,
    srcHeightPx: Int,
    scaleTypeEnabled: Boolean,
    scaleTypeWire: Int,
): Pair<IntOffset, IntSize> {
    val boxW = (rightPx - leftPx).roundToInt().coerceAtLeast(1)
    val boxH = (bottomPx - topPx).roundToInt().coerceAtLeast(1)
    val sw = srcWidthPx.coerceAtLeast(1)
    val sh = srcHeightPx.coerceAtLeast(1)

    if (!scaleTypeEnabled) {
        return IntOffset(leftPx.roundToInt(), topPx.roundToInt()) to IntSize(boxW, boxH)
    }

    val mode =
        scaleTypeWire.coerceIn(
            RemoteCanvasDrawImageScaleTypeWire.FIT,
            RemoteCanvasDrawImageScaleTypeWire.FILL_BOUNDS,
        )
    if (mode == RemoteCanvasDrawImageScaleTypeWire.FILL_BOUNDS) {
        return IntOffset(leftPx.roundToInt(), topPx.roundToInt()) to IntSize(boxW, boxH)
    }

    val swf = sw.toFloat()
    val shf = sh.toFloat()
    val boxWf = boxW.toFloat()
    val boxHf = boxH.toFloat()

    val scale =
        (if (mode == RemoteCanvasDrawImageScaleTypeWire.CROP) {
            max(boxWf / swf, boxHf / shf)
        } else {
            min(boxWf / swf, boxHf / shf)
        }).let { s ->
            if (s.isFinite() && s > 0f) s else 1f
        }

    val dw = (swf * scale).roundToInt().coerceAtLeast(1)
    val dh = (shf * scale).roundToInt().coerceAtLeast(1)
    val ox = leftPx + (boxWf - dw) / 2f
    val oy = topPx + (boxHf - dh) / 2f
    return IntOffset(ox.roundToInt(), oy.roundToInt()) to IntSize(dw, dh)
}

/**
 * After [drawImageDstOffsetAndSizePx], optionally multiplies dst width/height by [factor] (>0, non-finite → 1)
 * and re-centers inside the axis-aligned box [[boxLeftPx],[boxTopPx])..[[boxRightPx],[boxBottomPx]) in px.
 */
internal fun applyDrawImageUniformScaleFactorToDst(
    dstOffset: IntOffset,
    dstSize: IntSize,
    boxLeftPx: Float,
    boxTopPx: Float,
    boxRightPx: Float,
    boxBottomPx: Float,
    enabled: Boolean,
    factor: Float,
): Pair<IntOffset, IntSize> {
    if (!enabled) return dstOffset to dstSize
    val f = if (factor.isFinite() && factor > 0f) factor else 1f
    val dw = (dstSize.width * f).roundToInt().coerceAtLeast(1)
    val dh = (dstSize.height * f).roundToInt().coerceAtLeast(1)
    val boxW = boxRightPx - boxLeftPx
    val boxH = boxBottomPx - boxTopPx
    val cx = boxLeftPx + boxW / 2f
    val cy = boxTopPx + boxH / 2f
    val ox = cx - dw / 2f
    val oy = cy - dh / 2f
    return IntOffset(ox.roundToInt(), oy.roundToInt()) to IntSize(dw, dh)
}
