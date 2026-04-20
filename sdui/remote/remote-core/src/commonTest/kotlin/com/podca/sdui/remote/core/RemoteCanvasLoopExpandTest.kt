package com.podca.sdui.remote.core

import kotlin.test.Test
import kotlin.test.assertEquals

class RemoteCanvasLoopExpandTest {

    private fun fill(colorArgb: Int = 0xFFFF0000.toInt()) =
        RemoteCanvasOpProto(
            code = RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_FILL_RECT,
            rect_l_dp = 0f,
            rect_t_dp = 0f,
            rect_r_dp = 4f,
            rect_b_dp = 4f,
            color_argb = colorArgb,
        )

    private fun loopBegin(repeatCount: Int) =
        RemoteCanvasOpProto(
            code = RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_LOOP_BEGIN,
            loop_repeat_count = repeatCount,
        )

    private fun loopEnd() =
        RemoteCanvasOpProto(
            code = RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_LOOP_END,
        )

    private fun pushOffscreen() =
        RemoteCanvasOpProto(
            code = RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_PUSH_OFFSCREEN_RENDER,
            rect_l_dp = 0f,
            rect_t_dp = 0f,
            rect_r_dp = 8f,
            rect_b_dp = 8f,
        )

    private fun popOffscreen() =
        RemoteCanvasOpProto(
            code = RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_POP_OFFSCREEN_RENDER,
        )

    @Test
    fun expandsLoopInsideOffscreenAfterProgramWireRoundTrip() {
        val program =
            RemoteCanvasProgramProto(
                width_dp = 16f,
                height_dp = 16f,
                ops =
                    listOf(
                        pushOffscreen(),
                        loopBegin(2),
                        fill(),
                        loopEnd(),
                        popOffscreen(),
                    ),
            )
        val roundTrip =
            RemoteCanvasProgramProto.ADAPTER.decode(RemoteCanvasProgramProto.ADAPTER.encode(program))
        val expanded = expandRemoteCanvasLoopBlocks(roundTrip)
        assertEquals(4, expanded.size)
        assertEquals(RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_PUSH_OFFSCREEN_RENDER, expanded[0].code)
        assertEquals(RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_FILL_RECT, expanded[1].code)
        assertEquals(RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_FILL_RECT, expanded[2].code)
        assertEquals(RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_POP_OFFSCREEN_RENDER, expanded[3].code)
    }

    @Test
    fun loopRepeatZeroInsideOffscreenDropsInnerOps() {
        val ops =
            listOf(
                pushOffscreen(),
                loopBegin(0),
                fill(0xFF00FF00.toInt()),
                loopEnd(),
                popOffscreen(),
            )
        val expanded = expandRemoteCanvasLoopBlocks(ops)
        assertEquals(2, expanded.size)
        assertEquals(RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_PUSH_OFFSCREEN_RENDER, expanded[0].code)
        assertEquals(RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_POP_OFFSCREEN_RENDER, expanded[1].code)
    }

    @Test
    fun expandsNestedLoopsAtProgramRoot() {
        val ops =
            listOf(
                loopBegin(2),
                loopBegin(3),
                fill(0xFF112233.toInt()),
                loopEnd(),
                loopEnd(),
            )
        val expanded = expandRemoteCanvasLoopBlocks(ops, 0)
        assertEquals(6, expanded.size)
        for (op in expanded) {
            assertEquals(RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_FILL_RECT, op.code)
        }
    }

    @Test
    fun expandsNestedLoopsInsideOffscreenInnerOut() {
        val ops =
            listOf(
                pushOffscreen(),
                loopBegin(2),
                loopBegin(3),
                fill(0xFF0000FF.toInt()),
                loopEnd(),
                loopEnd(),
                popOffscreen(),
            )
        val expanded = expandRemoteCanvasLoopBlocks(ops, 0)
        assertEquals(8, expanded.size)
        assertEquals(RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_PUSH_OFFSCREEN_RENDER, expanded[0].code)
        for (i in 1..6) {
            assertEquals(RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_FILL_RECT, expanded[i].code)
        }
        assertEquals(RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_POP_OFFSCREEN_RENDER, expanded[7].code)
    }

    @Test
    fun clampsLoopRepeatCountToProgramCap() {
        val ops =
            listOf(
                loopBegin(10),
                fill(0xFF00AA00.toInt()),
                loopEnd(),
            )
        val expanded = expandRemoteCanvasLoopBlocks(ops, loopExpandMaxRepeatPerBlock = 2)
        assertEquals(2, expanded.size)
        assertEquals(RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_FILL_RECT, expanded[0].code)
        assertEquals(RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_FILL_RECT, expanded[1].code)
    }
}
