package com.podca.sdui.player.engine

import com.podca.sdui.protocol.core.ActionResultProto
import com.podca.sdui.protocol.core.StatePatchProto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

public class PodcaStateStore(
    initialDocument: PodcaDocumentNode? = null,
    initialRevision: Long = 0L,
) {
    private val mutableDocument: MutableStateFlow<PodcaDocumentNode?> =
        MutableStateFlow(initialDocument)
    private val mutableRevision: MutableStateFlow<Long> =
        MutableStateFlow(initialRevision)

    public val document: StateFlow<PodcaDocumentNode?> = mutableDocument
    public val revision: StateFlow<Long> = mutableRevision

    public fun setDocument(
        document: PodcaDocumentNode?,
        revision: Long = mutableRevision.value,
    ) {
        mutableDocument.value = document
        mutableRevision.value = revision
    }

    public fun applyPatch(patch: StatePatchProto) {
        mutableDocument.value = decodePodcaDocument(patch.encoded_root.toByteArray())
        mutableRevision.value = patch.revision
    }

    public fun applyActionResult(result: ActionResultProto) {
        val patch = result.state_patch ?: return
        applyPatch(patch)
    }

    public fun updateRevision(transform: (Long) -> Long) {
        mutableRevision.update(transform)
    }
}
