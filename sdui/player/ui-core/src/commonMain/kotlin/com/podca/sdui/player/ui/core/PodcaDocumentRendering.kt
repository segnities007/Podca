package com.podca.sdui.player.ui.core

import androidx.compose.runtime.Composable
import com.podca.sdui.player.engine.PodcaDocumentNode

@Composable
public fun PodcaRenderChildren(
    node: PodcaDocumentNode,
    renderChild: @Composable (PodcaDocumentNode) -> Unit,
) {
    for (child in node.children) {
        renderChild(child)
    }
}

@Composable
public fun PodcaRenderSlotChildren(
    node: PodcaDocumentNode,
    slot: String,
    renderChild: @Composable (PodcaDocumentNode) -> Unit,
) {
    for (child in node.children) {
        if (child.slot == slot) {
            renderChild(child)
        }
    }
}

public fun PodcaDocumentNode.hasSlot(slot: String): Boolean =
    children.any { child -> child.slot == slot }
