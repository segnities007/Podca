package com.podca.sdui.remote.creation

import com.podca.sdui.remote.core.RemoteCanvasOpCodeProto
import com.podca.sdui.remote.core.RemoteCanvasProgramProto
import kotlin.test.Test
import kotlin.test.assertEquals

class RemoteCanvasLoopWireRoundTripTest {

    @Test
    fun loopOpcodeValuesMatchProtoContract() {
        assertEquals(35, RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_LOOP_BEGIN.value)
        assertEquals(36, RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_LOOP_END.value)
    }

    @Test
    fun loopBeginCarriesRepeatCountThroughWire() {
        val program =
            remoteCanvasProgram(
                widthDp = 8f,
                heightDp = 8f,
                canvasLoopBegin(4),
                canvasFillRectDp(0f, 0f, 4f, 4f, colorArgb = 0xFF0000FF.toInt()),
                canvasLoopEnd(),
            )
        val decoded =
            RemoteCanvasProgramProto.ADAPTER.decode(RemoteCanvasProgramProto.ADAPTER.encode(program))
        assertEquals(3, decoded.ops.size)
        assertEquals(RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_LOOP_BEGIN, decoded.ops[0].code)
        assertEquals(4, decoded.ops[0].loop_repeat_count)
        assertEquals(RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_FILL_RECT, decoded.ops[1].code)
        assertEquals(RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_LOOP_END, decoded.ops[2].code)
        assertEquals(0, decoded.loop_expand_max_repeat_per_block)
    }

    @Test
    fun loopExpandMaxRepeatPerBlockCarriesThroughWire() {
        val program =
            remoteCanvasProgram(
                widthDp = 8f,
                heightDp = 8f,
                canvasLoopBegin(4),
                canvasFillRectDp(0f, 0f, 4f, 4f, colorArgb = 0xFF0000FF.toInt()),
                canvasLoopEnd(),
                loopExpandMaxRepeatPerBlock = 2,
            )
        val decoded =
            RemoteCanvasProgramProto.ADAPTER.decode(RemoteCanvasProgramProto.ADAPTER.encode(program))
        assertEquals(2, decoded.loop_expand_max_repeat_per_block)
    }
}
