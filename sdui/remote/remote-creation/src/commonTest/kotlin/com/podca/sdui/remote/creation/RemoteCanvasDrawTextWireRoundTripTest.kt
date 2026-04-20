package com.podca.sdui.remote.creation

import com.podca.sdui.remote.core.RemoteCanvasDrawTextLineHeightStyleWire
import com.podca.sdui.remote.core.RemoteCanvasOpCodeProto
import com.podca.sdui.remote.core.RemoteCanvasProgramProto
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RemoteCanvasDrawTextWireRoundTripTest {

    @Test
    fun drawTextAtWithLineHeightRoundTripsThroughWire() {
        val program =
            remoteCanvasProgram(
                widthDp = 100f,
                heightDp = 50f,
                canvasDrawTextAtDp(
                    xDp = 4f,
                    yDp = 4f,
                    text = "Hi",
                    fontSizeSp = 14f,
                    fontWeight = 400,
                    colorArgb = 0xFF000000.toInt(),
                    lineHeightSp = 20f,
                ),
            )
        val decoded = RemoteCanvasProgramProto.ADAPTER.decode(RemoteCanvasProgramProto.ADAPTER.encode(program))
        assertEquals(1, decoded.ops.size)
        val op = decoded.ops[0]
        assertEquals(RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_DRAW_TEXT_AT, op.code)
        assertTrue(op.text_line_height_sp > 0f)
        assertEquals(20f, op.text_line_height_sp)
    }

    @Test
    fun drawTextLineHeightStyleRoundTripsThroughWire() {
        val program =
            remoteCanvasProgram(
                widthDp = 100f,
                heightDp = 50f,
                canvasDrawTextAtDp(
                    xDp = 0f,
                    yDp = 0f,
                    text = "Hi",
                    fontSizeSp = 14f,
                    fontWeight = 400,
                    colorArgb = 0xFF000000.toInt(),
                    lineHeightSp = 22f,
                    lineHeightStyleWire = RemoteCanvasDrawTextLineHeightStyleWire.TOP_TRIM_BOTH,
                ),
            )
        val decoded = RemoteCanvasProgramProto.ADAPTER.decode(RemoteCanvasProgramProto.ADAPTER.encode(program))
        assertEquals(RemoteCanvasDrawTextLineHeightStyleWire.TOP_TRIM_BOTH, decoded.ops[0].draw_text_line_height_style)
    }

    @Test
    fun drawTextRectTopIsFirstBaselineRoundTripsThroughWire() {
        val program =
            remoteCanvasProgram(
                widthDp = 40f,
                heightDp = 20f,
                canvasDrawTextDp(
                    leftDp = 0f,
                    topDp = 10f,
                    rightDp = 40f,
                    bottomDp = 20f,
                    text = "A",
                    fontSizeSp = 12f,
                    fontWeight = 400,
                    colorArgb = 0xFF000000.toInt(),
                    rectTopIsFirstBaseline = true,
                ),
            )
        val decoded =
            RemoteCanvasProgramProto.ADAPTER.decode(RemoteCanvasProgramProto.ADAPTER.encode(program))
        assertTrue(decoded.ops[0].draw_text_rect_top_is_first_baseline)
    }

    @Test
    fun drawTextDisableFontPaddingRoundTripsThroughWire() {
        val program =
            remoteCanvasProgram(
                widthDp = 40f,
                heightDp = 24f,
                canvasDrawTextAtDp(
                    xDp = 0f,
                    yDp = 0f,
                    text = "A",
                    fontSizeSp = 12f,
                    fontWeight = 400,
                    colorArgb = 0xFF000000.toInt(),
                    disableFontPadding = true,
                ),
            )
        val decoded =
            RemoteCanvasProgramProto.ADAPTER.decode(RemoteCanvasProgramProto.ADAPTER.encode(program))
        assertEquals(true, decoded.ops[0].draw_text_disable_font_padding)
    }

    @Test
    fun drawTextRectBottomIsLastBaselineRoundTripsThroughWire() {
        val program =
            remoteCanvasProgram(
                widthDp = 40f,
                heightDp = 24f,
                canvasDrawTextDp(
                    leftDp = 0f,
                    topDp = 0f,
                    rightDp = 40f,
                    bottomDp = 20f,
                    text = "Z",
                    fontSizeSp = 12f,
                    fontWeight = 400,
                    colorArgb = 0xFF000000.toInt(),
                    rectBottomIsLastBaseline = true,
                ),
            )
        val decoded =
            RemoteCanvasProgramProto.ADAPTER.decode(RemoteCanvasProgramProto.ADAPTER.encode(program))
        assertTrue(decoded.ops[0].draw_text_rect_bottom_is_last_baseline)
    }

    @Test
    fun drawTextInRectWithLineHeightRoundTripsThroughWire() {
        val program =
            remoteCanvasProgram(
                widthDp = 80f,
                heightDp = 40f,
                canvasDrawTextDp(
                    leftDp = 0f,
                    topDp = 0f,
                    rightDp = 80f,
                    bottomDp = 40f,
                    text = "Body",
                    fontSizeSp = 12f,
                    fontWeight = 0,
                    colorArgb = 0xFFFFFFFF.toInt(),
                    lineHeightSp = 18f,
                ),
            )
        val decoded = RemoteCanvasProgramProto.ADAPTER.decode(RemoteCanvasProgramProto.ADAPTER.encode(program))
        assertEquals(18f, decoded.ops[0].text_line_height_sp)
    }

    @Test
    fun drawTextOnPathRoundTripsThroughWire() {
        val path =
            buildRemoteCanvasPath {
                moveTo(0f, 0f)
                lineTo(100f, 0f)
            }
        val program =
            remoteCanvasProgram(
                widthDp = 120f,
                heightDp = 40f,
                canvasDrawTextOnPathMoveLinePathDp(
                    path = path,
                    text = "Hi",
                    colorArgb = 0xFF000000.toInt(),
                    fontSizeSp = 14f,
                    fontWeight = 400,
                    textAlignAlongPath = 1,
                    startOffsetAlongPathDp = 3f,
                ),
            )
        val decoded = RemoteCanvasProgramProto.ADAPTER.decode(RemoteCanvasProgramProto.ADAPTER.encode(program))
        assertEquals(1, decoded.ops.size)
        val op = decoded.ops[0]
        assertEquals(RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_DRAW_TEXT_ON_PATH, op.code)
        assertEquals("Hi", op.text_body)
        assertEquals(3f, op.text_on_path_start_offset_dp)
        assertEquals(1, op.text_align)
        assertEquals(path.verbs, op.path?.verbs)
        assertEquals(path.coords_dp, op.path?.coords_dp)
    }

    @Test
    fun drawTextOnCircleRoundTripsThroughWire() {
        val program =
            remoteCanvasProgram(
                widthDp = 200f,
                heightDp = 200f,
                canvasDrawTextOnCircleDp(
                    centerXDp = 100f,
                    centerYDp = 100f,
                    radiusDp = 50f,
                    text = "Hi",
                    colorArgb = 0xFF000000.toInt(),
                    fontSizeSp = 14f,
                    fontWeight = 400,
                    arcStartDeg = -90f,
                    arcSweepDeg = 180f,
                    warpRadiusOffsetDp = 2f,
                    arcLengthStartOffsetDp = 1f,
                    textAlignAlongArc = 2,
                ),
            )
        val decoded = RemoteCanvasProgramProto.ADAPTER.decode(RemoteCanvasProgramProto.ADAPTER.encode(program))
        assertEquals(1, decoded.ops.size)
        val op = decoded.ops[0]
        assertEquals(RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_DRAW_TEXT_ON_CIRCLE, op.code)
        assertEquals("Hi", op.text_body)
        assertEquals(100f, op.rect_l_dp)
        assertEquals(100f, op.rect_t_dp)
        assertEquals(50f, op.circle_radius_dp)
        assertEquals(-90f, op.arc_start_deg)
        assertEquals(180f, op.arc_sweep_deg)
        assertEquals(2f, op.text_on_circle_warp_radius_offset_dp)
        assertEquals(1f, op.text_on_path_start_offset_dp)
        assertEquals(2, op.text_align)
    }

    @Test
    fun drawTextAnchoredRoundTripsThroughWire() {
        val program =
            remoteCanvasProgram(
                widthDp = 120f,
                heightDp = 80f,
                canvasDrawTextAnchoredDp(
                    anchorXDp = 10f,
                    anchorYDp = 20f,
                    text = "Hi",
                    colorArgb = 0xFF000000.toInt(),
                    fontSizeSp = 15f,
                    fontWeight = 500,
                    maxWidthDp = 100f,
                    maxHeightDp = 40f,
                    panXDp = 1f,
                    panYDp = -2f,
                    anchoredTextFlags = 3,
                    textLineHeightSp = 18f,
                ),
            )
        val decoded = RemoteCanvasProgramProto.ADAPTER.decode(RemoteCanvasProgramProto.ADAPTER.encode(program))
        assertEquals(1, decoded.ops.size)
        val op = decoded.ops[0]
        assertEquals(RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_DRAW_TEXT_ANCHORED, op.code)
        assertEquals("Hi", op.text_body)
        assertEquals(10f, op.rect_l_dp)
        assertEquals(20f, op.rect_t_dp)
        assertEquals(100f, op.rect_r_dp)
        assertEquals(40f, op.rect_b_dp)
        assertEquals(1f, op.text_anchor_pan_x_dp)
        assertEquals(-2f, op.text_anchor_pan_y_dp)
        assertEquals(3, op.draw_anchored_text_flags)
        assertEquals(18f, op.text_line_height_sp)
    }

    @Test
    fun drawTextRunRoundTripsThroughWire() {
        val program =
            remoteCanvasProgram(
                widthDp = 100f,
                heightDp = 40f,
                canvasDrawTextRunAtDp(
                    xDp = 2f,
                    yDp = 3f,
                    fullText = "abcdef",
                    runStart = 2,
                    runEnd = 5,
                    fontSizeSp = 11f,
                    fontWeight = 600,
                    colorArgb = 0xFFFF0000.toInt(),
                    maxWidthDp = 80f,
                    maxHeightDp = 30f,
                    textAlign = 1,
                    textAlignVertical = 2,
                    lineHeightSp = 14f,
                    rectTopIsFirstBaseline = true,
                    contextStart = 0,
                    contextEndWire = 6,
                    isRtl = true,
                ),
            )
        val decoded = RemoteCanvasProgramProto.ADAPTER.decode(RemoteCanvasProgramProto.ADAPTER.encode(program))
        val op = decoded.ops[0]
        assertEquals(RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_DRAW_TEXT_RUN, op.code)
        assertEquals("abcdef", op.text_body)
        assertEquals(2, op.draw_text_run_start)
        assertEquals(5, op.draw_text_run_end)
        assertEquals(0, op.draw_text_run_context_start)
        assertEquals(6, op.draw_text_run_context_end)
        assertEquals(true, op.draw_text_run_is_rtl)
        assertEquals(true, op.draw_text_rect_top_is_first_baseline)
    }
}
