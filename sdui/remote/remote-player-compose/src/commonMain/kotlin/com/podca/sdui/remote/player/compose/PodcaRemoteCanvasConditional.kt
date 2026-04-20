package com.podca.sdui.remote.player.compose

import com.podca.sdui.remote.core.RemoteCanvasOpProto
import com.podca.sdui.remote.core.evalRemoteCanvasConditionalBranch
import com.podca.sdui.remote.core.resolveRemoteCanvasConditionalOperands

/**
 * Whether the branch for a single [RemoteCanvasOpProto] with code `CONDITIONAL_BEGIN` is active,
 * given the current [conditionalStack] (parent branches) and a host [resolveRemoteFloat] for
 * [RemoteCanvasOpProto.conditional_remote_float_id_a] / [conditional_remote_float_id_b] and optional
 * [RemoteCanvasOpProto.conditional_operand_a_is_wire_nan] / [conditional_operand_b_is_wire_nan].
 */
internal fun evalRemoteCanvasConditionalBeginBranch(
    op: RemoteCanvasOpProto,
    conditionalStack: List<Boolean>,
    resolveRemoteFloat: (String) -> Float?,
): Boolean {
    val parentAllows = conditionalStack.isEmpty() || conditionalStack.all { it }
    if (!parentAllows) return false
    val (operandA, operandB) = resolveRemoteCanvasConditionalOperands(op, resolveRemoteFloat)
    return evalRemoteCanvasConditionalBranch(
        op.conditional_cmp,
        operandA,
        operandB,
        op.conditional_literal_nan_eq,
    )
}
