package com.podca.sdui.remote.core

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class RemoteCanvasStateLayoutSemanticsHintsTest {

    @Test
    fun returnsNullWhenNoStateLayoutMarkers() {
        val ops =
            listOf(
                RemoteCanvasOpProto(
                    code = RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_FILL_RECT,
                    rect_l_dp = 0f,
                    rect_t_dp = 0f,
                    rect_r_dp = 1f,
                    rect_b_dp = 1f,
                    color_argb = 0xFFFF0000.toInt(),
                ),
            )
        assertNull(remoteCanvasStateLayoutSemanticsHintsForPlayback(ops, null))
    }

    @Test
    fun readsFirstActiveBeginHints() {
        val ops =
            listOf(
                RemoteCanvasOpProto(
                    code = RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_STATE_LAYOUT_BEGIN,
                    state_layout_id = 1,
                    state_layout_semantics_merge_descendants = true,
                    state_layout_semantics_test_tag = "a",
                    state_layout_semantics_content_description = "cd",
                ),
                RemoteCanvasOpProto(code = RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_STATE_LAYOUT_END),
            )
        val hints = remoteCanvasStateLayoutSemanticsHintsForPlayback(ops, 1)
        assertNotNull(hints)
        assertEquals(true, hints.mergeDescendants)
        assertEquals("a", hints.testTag)
        assertEquals("cd", hints.contentDescription)
    }

    @Test
    fun skipsInactiveBranchBeforeActive() {
        val ops =
            listOf(
                RemoteCanvasOpProto(
                    code = RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_STATE_LAYOUT_BEGIN,
                    state_layout_id = 0,
                    state_layout_semantics_test_tag = "wrong",
                ),
                RemoteCanvasOpProto(code = RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_STATE_LAYOUT_END),
                RemoteCanvasOpProto(
                    code = RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_STATE_LAYOUT_BEGIN,
                    state_layout_id = 2,
                    state_layout_semantics_test_tag = "ok",
                ),
                RemoteCanvasOpProto(code = RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_STATE_LAYOUT_END),
            )
        val hints = remoteCanvasStateLayoutSemanticsHintsForPlayback(ops, 2)
        assertNotNull(hints)
        assertEquals("ok", hints.testTag)
    }
}
