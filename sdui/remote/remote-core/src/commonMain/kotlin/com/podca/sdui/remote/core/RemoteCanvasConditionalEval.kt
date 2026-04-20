package com.podca.sdui.remote.core

/**
 * Resolves the two float operands for `CONDITIONAL_BEGIN` (literals and/or named host-map floats).
 *
 * When [RemoteCanvasOpProto.conditional_remote_float_fallback_to_literal] is **true**, a non-empty id whose
 * [resolveRemoteFloat] returns **null** uses the corresponding [RemoteCanvasOpProto.conditional_literal_*] instead
 * of **NaN**. When **false** (legacy), missing map entries become **NaN**.
 *
 * [RemoteCanvasOpProto.conditional_operand_a_is_wire_nan] / [conditional_operand_b_is_wire_nan]: when **true**,
 * that operand is **Float.NaN** and literals / ids / fallback are ignored for that side (wire-explicit NaN token subset).
 */
public fun resolveRemoteCanvasConditionalOperands(
    op: RemoteCanvasOpProto,
    resolveRemoteFloat: (String) -> Float?,
): Pair<Float, Float> {
    val operandA =
        if (op.conditional_operand_a_is_wire_nan) {
            Float.NaN
        } else if (op.conditional_remote_float_id_a.isNotBlank()) {
            resolveRemoteFloat(op.conditional_remote_float_id_a)
                ?: if (op.conditional_remote_float_fallback_to_literal) {
                    op.conditional_literal_a
                } else {
                    Float.NaN
                }
        } else {
            op.conditional_literal_a
        }
    val operandB =
        if (op.conditional_operand_b_is_wire_nan) {
            Float.NaN
        } else if (op.conditional_remote_float_id_b.isNotBlank()) {
            resolveRemoteFloat(op.conditional_remote_float_id_b)
                ?: if (op.conditional_remote_float_fallback_to_literal) {
                    op.conditional_literal_b
                } else {
                    Float.NaN
                }
        } else {
            op.conditional_literal_b
        }
    return operandA to operandB
}

/**
 * AndroidX `ConditionalOperations.TYPE_*` subset for **literal** operands only ([conditional_literal_a], [conditional_literal_b]).
 *
 * @param literalNanEq When true, EQ treats two NaNs as equal; NEQ is the negation of that equality.
 *   Ordering comparisons (LT..GTE) return false if either operand is NaN.
 */
public fun evalRemoteCanvasConditionalBranch(
    cmp: Int,
    literalA: Float,
    literalB: Float,
    literalNanEq: Boolean,
): Boolean {
    val c = cmp.coerceIn(0, 5)
    val aN = literalA.isNaN()
    val bN = literalB.isNaN()
    val bothNan = aN && bN
    val literalEqual = literalA == literalB || (literalNanEq && bothNan)
    return when (c) {
        0 -> literalEqual
        1 -> !literalEqual
        2 -> !aN && !bN && literalA < literalB
        3 -> !aN && !bN && literalA <= literalB
        4 -> !aN && !bN && literalA > literalB
        5 -> !aN && !bN && literalA >= literalB
        else -> false
    }
}
