package com.podca.sdui.remote.creation

import com.podca.sdui.remote.core.RemoteCanvasOpCodeProto
import com.podca.sdui.remote.core.RemoteCanvasProgramProto
import kotlin.test.Test
import kotlin.test.assertEquals

class RemoteCanvasStateLayoutWireRoundTripTest {

    @Test
    fun stateLayoutBeginCarriesIdRoundTrip() {
        val program =
            remoteCanvasProgram(
                widthDp = 10f,
                heightDp = 10f,
                canvasStateLayoutBegin(7),
                canvasFillRectDp(0f, 0f, 10f, 10f, colorArgb = 0xFF000000.toInt()),
                canvasStateLayoutEnd(),
            )
        val decoded = RemoteCanvasProgramProto.ADAPTER.decode(RemoteCanvasProgramProto.ADAPTER.encode(program))
        assertEquals(3, decoded.ops.size)
        assertEquals(RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_STATE_LAYOUT_BEGIN, decoded.ops[0].code)
        assertEquals(7, decoded.ops[0].state_layout_id)
        assertEquals(RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_STATE_LAYOUT_END, decoded.ops[2].code)
        assertEquals(false, decoded.ops[0].state_layout_semantics_merge_descendants)
        assertEquals("", decoded.ops[0].state_layout_semantics_test_tag)
        assertEquals("", decoded.ops[0].state_layout_semantics_content_description)
    }

    @Test
    fun stateLayoutSemanticsRoundTripsThroughWire() {
        val program =
            remoteCanvasProgram(
                widthDp = 8f,
                heightDp = 8f,
                canvasStateLayoutBegin(
                    stateLayoutId = 3,
                    semanticsMergeDescendants = true,
                    semanticsTestTag = "remote-canvas",
                    semanticsContentDescription = "desc",
                ),
                canvasFillRectDp(0f, 0f, 4f, 4f, colorArgb = 0xFF0000FF.toInt()),
                canvasStateLayoutEnd(),
            )
        val decoded =
            RemoteCanvasProgramProto.ADAPTER.decode(RemoteCanvasProgramProto.ADAPTER.encode(program))
        assertEquals(true, decoded.ops[0].state_layout_semantics_merge_descendants)
        assertEquals("remote-canvas", decoded.ops[0].state_layout_semantics_test_tag)
        assertEquals("desc", decoded.ops[0].state_layout_semantics_content_description)
    }
}
