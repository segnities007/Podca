package com.podca.sdui.remote.player.compose

import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ColorMatrix
import com.podca.sdui.remote.core.RemoteCanvasOpProto

/** Compose `ColorFilter.tint` for `DRAW_IMAGE` when [RemoteCanvasOpProto.draw_image_color_filter_tint_enabled]. */
private fun drawImageColorFilterTintFromOpOrNull(op: RemoteCanvasOpProto): ColorFilter? =
    if (op.draw_image_color_filter_tint_enabled) {
        val tintBlend =
            if (op.draw_image_color_filter_tint_blend_mode_enabled) {
                remoteCanvasDrawImageBlendModeFromWire(op.draw_image_color_filter_tint_blend_mode)
            } else {
                BlendMode.SrcIn
            }
        ColorFilter.tint(Color(op.draw_image_color_filter_tint_argb), tintBlend)
    } else {
        null
    }

/** Compose `ColorFilter.lighting` for `DRAW_IMAGE` when [RemoteCanvasOpProto.draw_image_color_filter_lighting_enabled]. */
private fun drawImageColorFilterLightingFromOpOrNull(op: RemoteCanvasOpProto): ColorFilter? =
    if (op.draw_image_color_filter_lighting_enabled) {
        ColorFilter.lighting(
            Color(op.draw_image_color_filter_lighting_mul_argb),
            Color(op.draw_image_color_filter_lighting_add_argb),
        )
    } else {
        null
    }

/** Compose `ColorFilter.colorMatrix` for `DRAW_IMAGE` when [RemoteCanvasOpProto.draw_image_color_filter_color_matrix_enabled]. */
private fun drawImageColorFilterColorMatrixFromOpOrNull(op: RemoteCanvasOpProto): ColorFilter? {
    if (!op.draw_image_color_filter_color_matrix_enabled) return null
    val src = op.draw_image_color_filter_color_matrix
    if (src.size != 20) return null
    val arr = FloatArray(20)
    for (i in 0 until 20) {
        val v = src[i]
        if (!v.isFinite()) return null
        arr[i] = v
    }
    return ColorFilter.colorMatrix(ColorMatrix(arr))
}

/**
 * Resolved `ColorFilter` for `DRAW_IMAGE`. Precedence: **lighting** over **color matrix** over **tint**
 * (matches wire / `RemoteCanvasProgram.proto` v1 notes).
 */
internal fun drawImageColorFilterFromOpOrNull(op: RemoteCanvasOpProto): ColorFilter? {
    val lighting = drawImageColorFilterLightingFromOpOrNull(op)
    if (lighting != null) return lighting
    val matrix = drawImageColorFilterColorMatrixFromOpOrNull(op)
    if (matrix != null) return matrix
    return drawImageColorFilterTintFromOpOrNull(op)
}
