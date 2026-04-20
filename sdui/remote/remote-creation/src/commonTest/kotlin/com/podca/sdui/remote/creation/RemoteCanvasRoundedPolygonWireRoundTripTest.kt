package com.podca.sdui.remote.creation

import com.podca.sdui.remote.core.RemoteCanvasOpCodeProto
import com.podca.sdui.remote.core.RemoteCanvasPolylineProto
import com.podca.sdui.remote.core.RemoteCanvasProgramProto
import kotlin.test.Test
import kotlin.test.assertEquals

class RemoteCanvasRoundedPolygonWireRoundTripTest {

    @Test
    fun roundedPolygonOpcodeValuesMatchProtoContract() {
        assertEquals(40, RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_FILL_ROUNDED_POLYGON.value)
        assertEquals(41, RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_STROKE_ROUNDED_POLYGON.value)
    }

    @Test
    fun fillRoundedPolygonWithMorphRoundTripsThroughWire() {
        val base =
            RemoteCanvasPolylineProto(
                xy_dp = listOf(0f, 0f, 10f, 0f, 5f, 8f),
            )
        val morph =
            RemoteCanvasPolylineProto(
                xy_dp = listOf(0f, 0f, 12f, 0f, 6f, 9f),
            )
        val program =
            remoteCanvasProgram(
                widthDp = 16f,
                heightDp = 16f,
                canvasFillRoundedPolygonDp(
                    polyline = base,
                    cornerRadiusDp = 1.5f,
                    colorArgb = 0xFFFF0000.toInt(),
                    morphPolyline = morph,
                    morphT = 0.5f,
                ),
            )
        val decoded = RemoteCanvasProgramProto.ADAPTER.decode(RemoteCanvasProgramProto.ADAPTER.encode(program))
        val op = decoded.ops[0]
        assertEquals(RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_FILL_ROUNDED_POLYGON, op.code)
        assertEquals(1.5f, op.corner_radius_dp)
        assertEquals(0xFFFF0000.toInt(), op.color_argb.toInt())
        assertEquals(0.5f, op.rounded_polygon_morph_t)
        assertEquals(base.xy_dp, op.polyline?.xy_dp)
        assertEquals(morph.xy_dp, op.rounded_polygon_morph_polyline?.xy_dp)
    }

    @Test
    fun strokeRoundedPolygonRoundTripsThroughWire() {
        val poly =
            RemoteCanvasPolylineProto(
                xy_dp = listOf(0f, 0f, 8f, 0f, 4f, 7f),
            )
        val program =
            remoteCanvasProgram(
                widthDp = 12f,
                heightDp = 12f,
                canvasStrokeRoundedPolygonDp(
                    polyline = poly,
                    cornerRadiusDp = 0.5f,
                    strokeWidthDp = 1f,
                    colorArgb = 0xFF0000FF.toInt(),
                    strokeJoin = 2,
                    strokeCap = 2,
                ),
            )
        val decoded = RemoteCanvasProgramProto.ADAPTER.decode(RemoteCanvasProgramProto.ADAPTER.encode(program))
        val op = decoded.ops[0]
        assertEquals(RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_STROKE_ROUNDED_POLYGON, op.code)
        assertEquals(1f, op.stroke_width_dp)
        assertEquals(2, op.stroke_join)
        assertEquals(2, op.stroke_cap)
    }
}
