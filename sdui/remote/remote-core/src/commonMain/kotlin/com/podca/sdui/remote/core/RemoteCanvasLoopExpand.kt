package com.podca.sdui.remote.core

/** When [RemoteCanvasProgramProto.loop_expand_max_repeat_per_block] is **0**, each [RemoteCanvasOpProto.loop_repeat_count] is clamped to this inclusive ceiling during [expandRemoteCanvasLoopBlocks]. */
public const val REMOTE_CANVAS_LOOP_EXPAND_DEFAULT_MAX_REPEAT_PER_BLOCK: Int = 512

/** Effective per-`LOOP_BEGIN` repeat ceiling from wire ([RemoteCanvasProgramProto.loop_expand_max_repeat_per_block]). */
public fun remoteCanvasLoopExpandEffectiveMaxRepeatPerBlock(loopExpandMaxRepeatPerBlock: Int): Int =
    if (loopExpandMaxRepeatPerBlock == 0) {
        REMOTE_CANVAS_LOOP_EXPAND_DEFAULT_MAX_REPEAT_PER_BLOCK
    } else {
        loopExpandMaxRepeatPerBlock.coerceAtLeast(1)
    }

/** Index of the matching [RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_LOOP_END] for ops after a LOOP_BEGIN, or null. */
private fun findRemoteCanvasLoopEnd(
    ops: List<RemoteCanvasOpProto>,
    searchFrom: Int,
    endExclusive: Int,
): Int? {
    var depth = 1
    var k = searchFrom
    while (k < endExclusive) {
        when (ops[k].code) {
            RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_LOOP_BEGIN -> depth++
            RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_LOOP_END -> {
                depth--
                if (depth == 0) return k
            }
            else -> Unit
        }
        k++
    }
    return null
}

/**
 * Expands `LOOP_BEGIN` / `LOOP_END` blocks into repeated op sequences (literal [loop_repeat_count] only).
 * Nested loops are expanded inside-out. Expansion runs **before** paint-time `CONDITIONAL_*` evaluation:
 * a false branch still **skips drawing** of the expanded ops, but **decoded op list size** already reflects
 * the repeat count — avoid large `loop_repeat_count` inside conditionals when wire/memory cost matters.
 *
 * Walks the full op list (including interiors of `PUSH_OFFSCREEN_RENDER` / `POP_OFFSCREEN_RENDER` pairs);
 * loops nested inside offscreen blocks expand the same as on the root canvas.
 *
 * @param loopExpandMaxRepeatPerBlock Raw [RemoteCanvasProgramProto.loop_expand_max_repeat_per_block] (**0** = [REMOTE_CANVAS_LOOP_EXPAND_DEFAULT_MAX_REPEAT_PER_BLOCK]).
 */
public fun expandRemoteCanvasLoopBlocks(
    ops: List<RemoteCanvasOpProto>,
    loopExpandMaxRepeatPerBlock: Int = 0,
): List<RemoteCanvasOpProto> {
    val cap = remoteCanvasLoopExpandEffectiveMaxRepeatPerBlock(loopExpandMaxRepeatPerBlock)
    return expandRemoteCanvasLoopSegment(ops, 0, ops.size, cap)
}

/** Expands using [program.loop_expand_max_repeat_per_block]. */
public fun expandRemoteCanvasLoopBlocks(program: RemoteCanvasProgramProto): List<RemoteCanvasOpProto> =
    expandRemoteCanvasLoopBlocks(program.ops, program.loop_expand_max_repeat_per_block)

private fun expandRemoteCanvasLoopSegment(
    ops: List<RemoteCanvasOpProto>,
    start: Int,
    endExclusive: Int,
    maxRepeatPerBlock: Int,
): List<RemoteCanvasOpProto> {
    val out = ArrayList<RemoteCanvasOpProto>((endExclusive - start).coerceAtLeast(0))
    var i = start
    while (i < endExclusive) {
        val op = ops[i]
        when (op.code) {
            RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_LOOP_BEGIN -> {
                val j = findRemoteCanvasLoopEnd(ops, i + 1, endExclusive)
                if (j == null) {
                    out.add(op)
                    i++
                } else {
                    val n = op.loop_repeat_count.coerceAtLeast(0).coerceAtMost(maxRepeatPerBlock)
                    val inner = expandRemoteCanvasLoopSegment(ops, i + 1, j, maxRepeatPerBlock)
                    repeat(n) { out.addAll(inner) }
                    i = j + 1
                }
            }

            RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_LOOP_END -> {
                i++
            }

            else -> {
                out.add(op)
                i++
            }
        }
    }
    return out
}
