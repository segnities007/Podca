package com.podca.sdui.remote.creation

import com.podca.sdui.protocol.ui.graphics.BlendModeProto
import com.podca.sdui.remote.core.RemoteCanvasOpCodeProto
import com.podca.sdui.remote.core.RemoteCanvasProgramProto
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class RemoteCanvasPathDrawPaintWireRoundTripTest {

    private val triangle =
        buildRemoteCanvasPath {
            moveTo(0f, 0f)
            lineTo(10f, 0f)
            lineTo(5f, 8f)
            close()
        }

    @Test
    fun fillPathDefaultsOmitPathDrawPaintFlags() {
        val program =
            remoteCanvasProgram(
                widthDp = 16f,
                heightDp = 16f,
                canvasFillPathDp(triangle, colorArgb = 0xFF0000FF.toInt()),
            )
        val op = RemoteCanvasProgramProto.ADAPTER.decode(RemoteCanvasProgramProto.ADAPTER.encode(program)).ops[0]
        assertEquals(RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_FILL_PATH, op.code)
        assertFalse(op.path_draw_blend_mode_enabled)
        assertFalse(op.path_draw_alpha_enabled)
        assertFalse(op.path_draw_color_filter_tint_enabled)
        assertFalse(op.path_draw_color_filter_tint_blend_mode_enabled)
        assertFalse(op.stroke_dash_enabled)
    }

    @Test
    fun fillPathBlendAndAlphaRoundTripThroughWire() {
        val program =
            remoteCanvasProgram(
                widthDp = 16f,
                heightDp = 16f,
                canvasFillPathDp(
                    path = triangle,
                    colorArgb = 0xFFFF0000.toInt(),
                    pathBlendMode = BlendModeProto.BLEND_MODE_MULTIPLY,
                    pathAlpha = 0.4f,
                ),
            )
        val op = RemoteCanvasProgramProto.ADAPTER.decode(RemoteCanvasProgramProto.ADAPTER.encode(program)).ops[0]
        assertTrue(op.path_draw_blend_mode_enabled)
        assertEquals(BlendModeProto.BLEND_MODE_MULTIPLY.value, op.path_draw_blend_mode)
        assertTrue(op.path_draw_alpha_enabled)
        assertEquals(0.4f, op.path_draw_alpha)
    }

    @Test
    fun strokePathBlendRoundTripsThroughWire() {
        val program =
            remoteCanvasProgram(
                widthDp = 16f,
                heightDp = 16f,
                canvasStrokePathDp(
                    path = triangle,
                    strokeWidthDp = 2f,
                    colorArgb = 0xFF00FF00.toInt(),
                    pathBlendMode = BlendModeProto.BLEND_MODE_SCREEN,
                ),
            )
        val op = RemoteCanvasProgramProto.ADAPTER.decode(RemoteCanvasProgramProto.ADAPTER.encode(program)).ops[0]
        assertEquals(RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_STROKE_PATH, op.code)
        assertTrue(op.path_draw_blend_mode_enabled)
        assertEquals(BlendModeProto.BLEND_MODE_SCREEN.value, op.path_draw_blend_mode)
        assertFalse(op.path_draw_alpha_enabled)
    }

    @Test
    fun fillPathTintColorFilterRoundTripsThroughWire() {
        val program =
            remoteCanvasProgram(
                widthDp = 16f,
                heightDp = 16f,
                canvasFillPathDp(
                    path = triangle,
                    colorArgb = 0xFFFFFFFF.toInt(),
                    pathColorFilterTintArgb = 0xFF336699.toInt(),
                    pathColorFilterTintBlendMode = BlendModeProto.BLEND_MODE_MULTIPLY,
                ),
            )
        val op = RemoteCanvasProgramProto.ADAPTER.decode(RemoteCanvasProgramProto.ADAPTER.encode(program)).ops[0]
        assertTrue(op.path_draw_color_filter_tint_enabled)
        assertEquals(0xFF336699.toInt(), op.path_draw_color_filter_tint_argb)
        assertTrue(op.path_draw_color_filter_tint_blend_mode_enabled)
        assertEquals(BlendModeProto.BLEND_MODE_MULTIPLY.value, op.path_draw_color_filter_tint_blend_mode)
    }

    @Test
    fun strokePathDashRoundTripsThroughWire() {
        val program =
            remoteCanvasProgram(
                widthDp = 16f,
                heightDp = 16f,
                canvasStrokePathDp(
                    path = triangle,
                    strokeWidthDp = 2f,
                    colorArgb = 0xFFFFFFFF.toInt(),
                    strokeDashOnDp = 3f,
                    strokeDashOffDp = 2f,
                    strokeDashPhaseDp = 1f,
                ),
            )
        val op = RemoteCanvasProgramProto.ADAPTER.decode(RemoteCanvasProgramProto.ADAPTER.encode(program)).ops[0]
        assertTrue(op.stroke_dash_enabled)
        assertEquals(3f, op.stroke_dash_on_dp)
        assertEquals(2f, op.stroke_dash_off_dp)
        assertEquals(1f, op.stroke_dash_phase_dp)
    }
}
