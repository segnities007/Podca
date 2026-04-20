package com.podca.sdui.remote.core

/**
 * Semantics subset read from the **first** [RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_STATE_LAYOUT_BEGIN]
 * that matches the resolved active [RemoteCanvasOpProto.state_layout_id] (same resolution as
 * [filterRemoteCanvasStateLayoutBlocksForPlayback]). Used by the player on the remote canvas root.
 */
public data class RemoteCanvasStateLayoutSemanticsHints(
    val mergeDescendants: Boolean,
    val testTag: String,
    val contentDescription: String,
)

/**
 * Returns hints from the active state-layout branch, or **null** when the program has no
 * `STATE_LAYOUT_*` markers (so the player skips attaching root semantics from this path).
 */
public fun remoteCanvasStateLayoutSemanticsHintsForPlayback(
    ops: List<RemoteCanvasOpProto>,
    activeIdFromHost: Int?,
): RemoteCanvasStateLayoutSemanticsHints? {
    val activeResolved = resolveRemoteCanvasStateLayoutActiveId(ops, activeIdFromHost) ?: return null
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
                return RemoteCanvasStateLayoutSemanticsHints(
                    mergeDescendants = op.state_layout_semantics_merge_descendants,
                    testTag = op.state_layout_semantics_test_tag,
                    contentDescription = op.state_layout_semantics_content_description,
                )
            }

            RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_STATE_LAYOUT_END -> {
                if (skipDepth > 0) {
                    skipDepth--
                }
            }

            else -> Unit
        }
    }
    return RemoteCanvasStateLayoutSemanticsHints(
        mergeDescendants = false,
        testTag = "",
        contentDescription = "",
    )
}
