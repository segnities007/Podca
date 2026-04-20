package com.podca.sdui.remote.creation

import com.podca.sdui.remote.core.RemoteCanvasOpCodeProto
import com.podca.sdui.remote.core.RemoteCanvasPathVerbProto
import com.podca.sdui.remote.core.RemoteCanvasProgramProto
import com.podca.sdui.remote.core.RemoteCanvasTweenPathIncompatibleFallbackWire
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse

class RemoteCanvasTweenPathWireRoundTripTest {

    @Test
    fun drawTweenPathOpcodeValueMatchesProtoContract() {
        assertEquals(39, RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_DRAW_TWEEN_PATH.value)
    }

    @Test
    fun drawTweenPathRoundTripsThroughWire() {
        val from =
            buildRemoteCanvasPath {
                moveTo(0f, 0f)
                lineTo(10f, 0f)
            }
        val to =
            buildRemoteCanvasPath {
                moveTo(0f, 0f)
                lineTo(20f, 10f)
            }
        val program =
            remoteCanvasProgram(
                widthDp = 32f,
                heightDp = 32f,
                canvasDrawTweenPathStrokeDp(
                    pathFrom = from,
                    pathTo = to,
                    fraction = 0.25f,
                    strokeWidthDp = 2f,
                    colorArgb = 0xFF00FF00.toInt(),
                    strokeJoin = 1,
                    strokeCap = 1,
                ),
            )
        val decoded = RemoteCanvasProgramProto.ADAPTER.decode(RemoteCanvasProgramProto.ADAPTER.encode(program))
        assertEquals(1, decoded.ops.size)
        val op = decoded.ops[0]
        assertEquals(RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_DRAW_TWEEN_PATH, op.code)
        assertEquals(0.25f, op.tween_path_fraction)
        assertEquals(2f, op.stroke_width_dp)
        assertEquals(0xFF00FF00.toInt(), op.color_argb.toInt())
        assertEquals(1, op.stroke_join)
        assertEquals(1, op.stroke_cap)
        assertEquals(0, op.tween_path_incompatible_fallback)
        assertEquals("", op.tween_path_fraction_remote_float_id)
        assertFalse(op.path_draw_blend_mode_enabled)
        assertFalse(op.path_draw_alpha_enabled)
        val pathFrom = requireNotNull(op.path)
        val pathTo = requireNotNull(op.tween_path_to)
        assertEquals(
            listOf(RemoteCanvasPathVerbProto.REMOTE_PATH_VERB_MOVE_TO, RemoteCanvasPathVerbProto.REMOTE_PATH_VERB_LINE_TO),
            pathFrom.verbs,
        )
        assertEquals(
            listOf(RemoteCanvasPathVerbProto.REMOTE_PATH_VERB_MOVE_TO, RemoteCanvasPathVerbProto.REMOTE_PATH_VERB_LINE_TO),
            pathTo.verbs,
        )
        assertEquals(listOf(0f, 0f, 10f, 0f), pathFrom.coords_dp)
        assertEquals(listOf(0f, 0f, 20f, 10f), pathTo.coords_dp)
    }

    @Test
    fun drawTweenPathIncompatibleFallbackRoundTripsThroughWire() {
        val from =
            buildRemoteCanvasPath {
                moveTo(0f, 0f)
                lineTo(10f, 0f)
            }
        val to =
            buildRemoteCanvasPath {
                moveTo(0f, 0f)
                quadraticTo(5f, 5f, 10f, 0f)
            }
        val program =
            remoteCanvasProgram(
                widthDp = 32f,
                heightDp = 32f,
                canvasDrawTweenPathStrokeDp(
                    pathFrom = from,
                    pathTo = to,
                    fraction = 0.5f,
                    strokeWidthDp = 1f,
                    colorArgb = 0xFFFF0000.toInt(),
                    incompatibleFallback = RemoteCanvasTweenPathIncompatibleFallbackWire.STROKE_TO,
                ),
            )
        val decoded = RemoteCanvasProgramProto.ADAPTER.decode(RemoteCanvasProgramProto.ADAPTER.encode(program))
        assertEquals(RemoteCanvasTweenPathIncompatibleFallbackWire.STROKE_TO, decoded.ops[0].tween_path_incompatible_fallback)
    }

    @Test
    fun drawTweenPathFractionRemoteFloatIdRoundTripsThroughWire() {
        val from =
            buildRemoteCanvasPath {
                moveTo(0f, 0f)
                lineTo(10f, 0f)
            }
        val to =
            buildRemoteCanvasPath {
                moveTo(0f, 0f)
                lineTo(20f, 10f)
            }
        val program =
            remoteCanvasProgram(
                widthDp = 32f,
                heightDp = 32f,
                canvasDrawTweenPathStrokeDp(
                    pathFrom = from,
                    pathTo = to,
                    fraction = 0f,
                    strokeWidthDp = 1f,
                    colorArgb = 0xFF0000FF.toInt(),
                    fractionRemoteFloatId = "tweenT",
                ),
            )
        val decoded = RemoteCanvasProgramProto.ADAPTER.decode(RemoteCanvasProgramProto.ADAPTER.encode(program))
        assertEquals("tweenT", decoded.ops[0].tween_path_fraction_remote_float_id)
    }
}
