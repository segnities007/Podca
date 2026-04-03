package com.podca.sdui.player.ui.core

import androidx.compose.foundation.layout.Box
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.zIndex
import com.podca.sdui.player.engine.PodcaDocumentNode
import com.podca.sdui.protocol.ui.FrameRateProto
import com.podca.sdui.protocol.ui.KeepScreenOnProto
import com.podca.sdui.protocol.ui.SensitiveContentProto
import com.podca.sdui.protocol.ui.ZIndexModifierProto

@Composable
public fun PodcaRenderUiNode(
    node: PodcaDocumentNode,
    renderChild: @Composable (PodcaDocumentNode) -> Unit,
) {
    when (node.type) {
        "ui.ZIndexModifier" -> {
            val proto = runCatching { ZIndexModifierProto.ADAPTER.decode(node.payload) }
                .getOrElse { ZIndexModifierProto() }
            Box(modifier = Modifier.zIndex(proto.z_index)) {
                PodcaRenderChildren(node = node, renderChild = renderChild)
            }
        }

        "ui.FrameRate" -> {
            runCatching { FrameRateProto.ADAPTER.decode(node.payload) }
            PodcaRenderChildren(node = node, renderChild = renderChild)
        }

        "ui.KeepScreenOn" -> {
            runCatching { KeepScreenOnProto.ADAPTER.decode(node.payload) }
            PodcaRenderChildren(node = node, renderChild = renderChild)
        }

        "ui.SensitiveContent" -> {
            runCatching { SensitiveContentProto.ADAPTER.decode(node.payload) }
            PodcaRenderChildren(node = node, renderChild = renderChild)
        }

        else -> PodcaRenderChildren(node = node, renderChild = renderChild)
    }
}
