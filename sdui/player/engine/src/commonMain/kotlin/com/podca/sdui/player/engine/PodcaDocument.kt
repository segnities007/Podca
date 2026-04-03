package com.podca.sdui.player.engine

import com.podca.sdui.protocol.core.ActionBindingProto
import com.podca.sdui.protocol.core.NodeProto
import okio.ByteString

public class PodcaDocumentNode(
    public val type: String,
    public val payload: ByteString,
    public val key: String,
    public val actions: List<ActionBindingProto>,
    public val children: List<PodcaDocumentNode>,
    public val slot: String = "",
) {
    public fun findByKey(targetKey: String): PodcaDocumentNode? {
        if (key == targetKey) return this
        for (child in children) {
            val found = child.findByKey(targetKey)
            if (found != null) return found
        }
        return null
    }
}

public fun PodcaDocumentNode(proto: NodeProto): PodcaDocumentNode =
    PodcaDocumentNode(
        type = proto.type,
        payload = proto.payload,
        key = proto.key,
        actions = proto.actions,
        children = proto.children.map(::PodcaDocumentNode),
        slot = proto.slot,
    )

public fun decodePodcaDocument(bytes: ByteArray): PodcaDocumentNode =
    PodcaDocumentNode(
        proto = NodeProto.ADAPTER.decode(bytes),
    )
