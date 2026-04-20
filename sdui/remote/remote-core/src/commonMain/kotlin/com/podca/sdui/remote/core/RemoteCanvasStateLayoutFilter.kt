package com.podca.sdui.remote.core

/**
 * Resolves which [RemoteCanvasOpProto.state_layout_id] is **active** for playback when the host
 * does not override ([activeIdFromHost] null): if any **0** appears on a BEGIN, use **0**; else the
 * **smallest** id among BEGINs. When there are no [REMOTE_CANVAS_OP_STATE_LAYOUT_BEGIN] ops, returns null.
 */
public fun resolveRemoteCanvasStateLayoutActiveId(
    ops: List<RemoteCanvasOpProto>,
    activeIdFromHost: Int?,
): Int? {
    val ids =
        ops.mapNotNull { op ->
            if (op.code == RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_STATE_LAYOUT_BEGIN) {
                op.state_layout_id
            } else {
                null
            }
        }.distinct()
    if (ids.isEmpty()) return null
    if (activeIdFromHost != null) return activeIdFromHost
    return if (ids.contains(0)) 0 else ids.minOrNull()
}

/**
 * Drops inactive **state layout** branches and strips BEGIN/END markers for the active branch.
 * Run **after** [expandRemoteCanvasLoopBlocks]. When the program has no state-layout ops, returns [ops] unchanged.
 */
public fun filterRemoteCanvasStateLayoutBlocksForPlayback(
    ops: List<RemoteCanvasOpProto>,
    activeIdFromHost: Int?,
): List<RemoteCanvasOpProto> {
    val activeResolved = resolveRemoteCanvasStateLayoutActiveId(ops, activeIdFromHost) ?: return ops
    val out = ArrayList<RemoteCanvasOpProto>(ops.size)
    var skipDepth = 0
    for (op in ops) {
        when (op.code) {
            RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_STATE_LAYOUT_BEGIN -> {
                if (skipDepth > 0) {
                    skipDepth++
                    continue
                }
                if (op.state_layout_id != activeResolved) {
                    skipDepth = 1
                    continue
                }
            }

            RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_STATE_LAYOUT_END -> {
                if (skipDepth > 0) {
                    skipDepth--
                    continue
                }
            }

            else -> {
                if (skipDepth > 0) continue
                out.add(op)
            }
        }
    }
    return out
}
