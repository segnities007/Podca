package com.podca.sdui.remote.player.compose

import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import com.podca.sdui.remote.core.RemoteCanvasOpProto

/**
 * Optional per-path draw paint for [RemoteCanvasOpProto] codes that use `DrawScope.drawPath`
 * (`FILL_PATH`, `STROKE_PATH`, `DRAW_TWEEN_PATH` stroke), mirroring a small subset of AndroidX
 * `RemoteCanvas` / `Paint` alpha and PorterDuff-style blend on vector draws.
 */
internal data class RemoteCanvasPathDrawPaint(
    val blendMode: BlendMode,
    val alpha: Float,
    val colorFilter: ColorFilter?,
)

internal fun remoteCanvasPathDrawPaintFromOp(op: RemoteCanvasOpProto): RemoteCanvasPathDrawPaint {
    val blendMode =
        if (op.path_draw_blend_mode_enabled) {
            remoteCanvasDrawImageBlendModeFromWire(op.path_draw_blend_mode)
        } else {
            BlendMode.SrcOver
        }
    val alpha =
        if (op.path_draw_alpha_enabled) {
            op.path_draw_alpha.coerceIn(0f, 1f).takeIf { it.isFinite() } ?: 1f
        } else {
            1f
        }
    val tintBlendMode =
        if (op.path_draw_color_filter_tint_blend_mode_enabled) {
            remoteCanvasDrawImageBlendModeFromWire(op.path_draw_color_filter_tint_blend_mode)
        } else {
            BlendMode.SrcIn
        }
    val colorFilter =
        if (op.path_draw_color_filter_tint_enabled) {
            ColorFilter.tint(Color(op.path_draw_color_filter_tint_argb), tintBlendMode)
        } else {
            null
        }
    return RemoteCanvasPathDrawPaint(blendMode, alpha, colorFilter)
}
