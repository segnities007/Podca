package com.podca.sdui.remote.core

import kotlin.test.Test
import kotlin.test.assertEquals

class RemoteCanvasStateLayoutFilterTest {

    @Test
    fun noMarkersReturnsSameList() {
        val ops =
            listOf(
                RemoteCanvasOpProto(code = RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_FILL_RECT, rect_l_dp = 0f, rect_t_dp = 0f, rect_r_dp = 1f, rect_b_dp = 1f),
            )
        assertEquals(ops, filterRemoteCanvasStateLayoutBlocksForPlayback(ops, null))
    }

    @Test
    fun defaultActiveIdZeroWhenPresent() {
        val ops =
            listOf(
                RemoteCanvasOpProto(code = RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_STATE_LAYOUT_BEGIN, state_layout_id = 0),
                RemoteCanvasOpProto(code = RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_FILL_RECT, rect_l_dp = 0f, rect_t_dp = 0f, rect_r_dp = 1f, rect_b_dp = 1f, color_argb = 1),
                RemoteCanvasOpProto(code = RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_STATE_LAYOUT_END),
                RemoteCanvasOpProto(code = RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_STATE_LAYOUT_BEGIN, state_layout_id = 1),
                RemoteCanvasOpProto(code = RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_FILL_RECT, rect_l_dp = 0f, rect_t_dp = 0f, rect_r_dp = 2f, rect_b_dp = 2f, color_argb = 2),
                RemoteCanvasOpProto(code = RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_STATE_LAYOUT_END),
            )
        val filtered = filterRemoteCanvasStateLayoutBlocksForPlayback(ops, null)
        assertEquals(1, filtered.size)
        assertEquals(RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_FILL_RECT, filtered[0].code)
        assertEquals(1, filtered[0].color_argb)
    }

    @Test
    fun hostOverrideSelectsBranch() {
        val ops =
            listOf(
                RemoteCanvasOpProto(code = RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_STATE_LAYOUT_BEGIN, state_layout_id = 2),
                RemoteCanvasOpProto(code = RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_FILL_RECT, rect_l_dp = 0f, rect_t_dp = 0f, rect_r_dp = 3f, rect_b_dp = 3f, color_argb = 3),
                RemoteCanvasOpProto(code = RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_STATE_LAYOUT_END),
            )
        val filtered = filterRemoteCanvasStateLayoutBlocksForPlayback(ops, 2)
        assertEquals(1, filtered.size)
        assertEquals(3, filtered[0].color_argb)
    }
}
