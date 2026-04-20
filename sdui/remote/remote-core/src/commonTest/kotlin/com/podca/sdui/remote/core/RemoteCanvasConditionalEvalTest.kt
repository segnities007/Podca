package com.podca.sdui.remote.core

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class RemoteCanvasConditionalEvalTest {

    @Test
    fun eqNanDefaultIsFalse() {
        assertFalse(
            evalRemoteCanvasConditionalBranch(
                cmp = 0,
                literalA = Float.NaN,
                literalB = Float.NaN,
                literalNanEq = false,
            ),
        )
    }

    @Test
    fun eqNanWhenLiteralNanEqTrue() {
        assertTrue(
            evalRemoteCanvasConditionalBranch(
                cmp = 0,
                literalA = Float.NaN,
                literalB = Float.NaN,
                literalNanEq = true,
            ),
        )
    }

    @Test
    fun neqNanWhenLiteralNanEqTrue() {
        assertFalse(
            evalRemoteCanvasConditionalBranch(
                cmp = 1,
                literalA = Float.NaN,
                literalB = Float.NaN,
                literalNanEq = true,
            ),
        )
    }

    @Test
    fun ltWithNanOperandIsFalse() {
        assertFalse(
            evalRemoteCanvasConditionalBranch(
                cmp = 2,
                literalA = Float.NaN,
                literalB = 1f,
                literalNanEq = true,
            ),
        )
    }

    @Test
    fun missingRemoteFloatUsesNanWhenNoFallback() {
        val op =
            RemoteCanvasOpProto(
                conditional_remote_float_id_a = "x",
                conditional_literal_a = 3f,
                conditional_literal_b = 0f,
            )
        val (a, _) = resolveRemoteCanvasConditionalOperands(op) { null }
        assertTrue(a.isNaN())
    }

    @Test
    fun missingRemoteFloatUsesLiteralWhenFallbackEnabled() {
        val op =
            RemoteCanvasOpProto(
                conditional_remote_float_id_a = "x",
                conditional_literal_a = 3f,
                conditional_literal_b = 0f,
                conditional_remote_float_fallback_to_literal = true,
            )
        val (a, b) = resolveRemoteCanvasConditionalOperands(op) { null }
        assertEquals(3f, a)
        assertEquals(0f, b)
    }

    @Test
    fun wireNanOperandOverridesMapAndLiteral() {
        val op =
            RemoteCanvasOpProto(
                conditional_operand_a_is_wire_nan = true,
                conditional_remote_float_id_a = "x",
                conditional_literal_a = 99f,
                conditional_literal_b = 1f,
                conditional_remote_float_fallback_to_literal = true,
            )
        val (a, b) = resolveRemoteCanvasConditionalOperands(op) { 42f }
        assertTrue(a.isNaN())
        assertEquals(1f, b)
    }
}
