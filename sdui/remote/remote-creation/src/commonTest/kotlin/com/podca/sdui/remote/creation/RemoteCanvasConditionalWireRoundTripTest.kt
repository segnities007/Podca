package com.podca.sdui.remote.creation

import com.podca.sdui.remote.core.RemoteCanvasOpCodeProto
import com.podca.sdui.remote.core.RemoteCanvasProgramProto
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RemoteCanvasConditionalWireRoundTripTest {

    @Test
    fun conditionalOpcodeValuesMatchProtoContract() {
        assertEquals(33, RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_CONDITIONAL_BEGIN.value)
        assertEquals(34, RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_CONDITIONAL_END.value)
    }

    @Test
    fun conditionalBeginEndRoundTripsThroughRemoteCanvasProgramWire() {
        val program =
            remoteCanvasProgram(
                widthDp = 16f,
                heightDp = 16f,
                canvasConditionalBegin(RemoteCanvasConditionalCmp.LT, 1f, 2f),
                canvasFillRectDp(0f, 0f, 8f, 8f, colorArgb = 0xFFFF0000.toInt()),
                canvasConditionalEnd(),
            )
        val decoded =
            RemoteCanvasProgramProto.ADAPTER.decode(RemoteCanvasProgramProto.ADAPTER.encode(program))
        assertEquals(3, decoded.ops.size)
        assertEquals(RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_CONDITIONAL_BEGIN, decoded.ops[0].code)
        assertEquals(RemoteCanvasConditionalCmp.LT, decoded.ops[0].conditional_cmp)
        assertEquals(1f, decoded.ops[0].conditional_literal_a)
        assertEquals(2f, decoded.ops[0].conditional_literal_b)
        assertEquals(false, decoded.ops[0].conditional_remote_float_fallback_to_literal)
        assertEquals(false, decoded.ops[0].conditional_operand_a_is_wire_nan)
        assertEquals(false, decoded.ops[0].conditional_operand_b_is_wire_nan)
        assertEquals(RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_FILL_RECT, decoded.ops[1].code)
        assertEquals(RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_CONDITIONAL_END, decoded.ops[2].code)
    }

    @Test
    fun eqComparisonWithZeroOperandsStillEncodesCmp() {
        val program =
            remoteCanvasProgram(
                widthDp = 1f,
                heightDp = 1f,
                canvasConditionalBegin(RemoteCanvasConditionalCmp.EQ, 0f, 0f),
                canvasConditionalEnd(),
            )
        val decoded =
            RemoteCanvasProgramProto.ADAPTER.decode(RemoteCanvasProgramProto.ADAPTER.encode(program))
        val begin = decoded.ops[0]
        assertEquals(RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_CONDITIONAL_BEGIN, begin.code)
        assertEquals(0, begin.conditional_cmp)
        assertEquals(0f, begin.conditional_literal_a)
        assertEquals(0f, begin.conditional_literal_b)
    }

    @Test
    fun nestedConditionalBlockRoundTripsThroughWire() {
        val program =
            remoteCanvasProgram(
                widthDp = 4f,
                heightDp = 4f,
                canvasConditionalBegin(RemoteCanvasConditionalCmp.LT, 2f, 1f),
                canvasConditionalBegin(RemoteCanvasConditionalCmp.EQ, 1f, 1f),
                canvasFillRectDp(0f, 0f, 4f, 4f, colorArgb = 0xFF00FF00.toInt()),
                canvasConditionalEnd(),
                canvasConditionalEnd(),
            )
        val decoded =
            RemoteCanvasProgramProto.ADAPTER.decode(RemoteCanvasProgramProto.ADAPTER.encode(program))
        assertEquals(5, decoded.ops.size)
        assertEquals(RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_CONDITIONAL_BEGIN, decoded.ops[0].code)
        assertEquals(RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_CONDITIONAL_BEGIN, decoded.ops[1].code)
        assertEquals(RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_FILL_RECT, decoded.ops[2].code)
        assertEquals(RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_CONDITIONAL_END, decoded.ops[3].code)
        assertEquals(RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_CONDITIONAL_END, decoded.ops[4].code)
    }

    @Test
    fun conditionalRemoteFloatIdsRoundTripThroughWire() {
        val program =
            remoteCanvasProgram(
                widthDp = 8f,
                heightDp = 8f,
                canvasConditionalBegin(
                    comparisonType = RemoteCanvasConditionalCmp.EQ,
                    literalA = 0f,
                    literalB = 1f,
                    remoteFloatIdA = "a",
                    remoteFloatIdB = "b",
                ),
                canvasConditionalEnd(),
            )
        val decoded =
            RemoteCanvasProgramProto.ADAPTER.decode(RemoteCanvasProgramProto.ADAPTER.encode(program))
        val begin = decoded.ops[0]
        assertEquals("a", begin.conditional_remote_float_id_a)
        assertEquals("b", begin.conditional_remote_float_id_b)
    }

    @Test
    fun conditionalLiteralNanEqRoundTripsThroughWire() {
        val program =
            remoteCanvasProgram(
                widthDp = 1f,
                heightDp = 1f,
                canvasConditionalBegin(
                    comparisonType = RemoteCanvasConditionalCmp.EQ,
                    literalA = Float.NaN,
                    literalB = Float.NaN,
                    literalNanEq = true,
                ),
                canvasConditionalEnd(),
            )
        val decoded =
            RemoteCanvasProgramProto.ADAPTER.decode(RemoteCanvasProgramProto.ADAPTER.encode(program))
        assertTrue(decoded.ops[0].conditional_literal_nan_eq)
    }

    @Test
    fun conditionalRemoteFloatFallbackToLiteralRoundTripsThroughWire() {
        val program =
            remoteCanvasProgram(
                widthDp = 1f,
                heightDp = 1f,
                canvasConditionalBegin(
                    comparisonType = RemoteCanvasConditionalCmp.EQ,
                    literalA = 7f,
                    literalB = 8f,
                    remoteFloatIdA = "missing",
                    remoteFloatFallbackToLiteral = true,
                ),
                canvasConditionalEnd(),
            )
        val decoded =
            RemoteCanvasProgramProto.ADAPTER.decode(RemoteCanvasProgramProto.ADAPTER.encode(program))
        assertTrue(decoded.ops[0].conditional_remote_float_fallback_to_literal)
    }

    @Test
    fun conditionalWireNanOperandsRoundTripThroughWire() {
        val program =
            remoteCanvasProgram(
                widthDp = 1f,
                heightDp = 1f,
                canvasConditionalBegin(
                    comparisonType = RemoteCanvasConditionalCmp.EQ,
                    literalA = 1f,
                    literalB = 2f,
                    remoteFloatIdA = "ignored",
                    operandWireNanA = true,
                    operandWireNanB = true,
                    literalNanEq = true,
                ),
                canvasConditionalEnd(),
            )
        val decoded =
            RemoteCanvasProgramProto.ADAPTER.decode(RemoteCanvasProgramProto.ADAPTER.encode(program))
        val begin = decoded.ops[0]
        assertTrue(begin.conditional_operand_a_is_wire_nan)
        assertTrue(begin.conditional_operand_b_is_wire_nan)
    }
}
