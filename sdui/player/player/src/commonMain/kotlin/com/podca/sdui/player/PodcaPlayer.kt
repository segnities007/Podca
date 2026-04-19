package com.podca.sdui.player

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import com.podca.sdui.player.engine.PodcaDocumentNode
import com.podca.sdui.player.engine.PodcaRuntime
import com.podca.sdui.player.ui.core.PodcaRenderChildren
import com.podca.sdui.player.ui.core.PodcaRenderUiNode
import com.podca.sdui.player.ui.foundation.PodcaRenderFoundationNode
import com.podca.sdui.player.ui.material3.PodcaRenderMaterial3Node

@Composable
public fun PodcaPlayer(
    runtime: PodcaRuntime,
    modifier: Modifier = Modifier,
) {
    val document by runtime.document.collectAsState()
    PodcaPlayer(
        document = document,
        runtime = runtime,
        modifier = modifier,
    )
}

@Composable
public fun PodcaPlayer(
    document: PodcaDocumentNode?,
    runtime: PodcaRuntime,
    modifier: Modifier = Modifier,
) {
    Box(modifier = modifier) {
        if (document != null) {
            PodcaRenderDocumentNode(
                node = document,
                runtime = runtime,
            )
        }
    }
}

@Composable
public fun PodcaRenderDocumentNode(
    node: PodcaDocumentNode,
    runtime: PodcaRuntime,
) {
    when {
        node.type == "Root" -> Column {
            PodcaRenderChildren(
                node = node,
                renderChild = { child ->
                    PodcaRenderDocumentNode(
                        node = child,
                        runtime = runtime,
                    )
                },
            )
        }

        node.type.startsWith("foundation.") -> PodcaRenderFoundationNode(
            node = node,
            renderChild = { child ->
                PodcaRenderDocumentNode(
                    node = child,
                    runtime = runtime,
                )
            },
        )

        node.type.startsWith("material3.") -> PodcaRenderMaterial3Node(
            node = node,
            runtime = runtime,
            renderChild = { child ->
                PodcaRenderDocumentNode(
                    node = child,
                    runtime = runtime,
                )
            },
        )

        node.type.startsWith("ui.") -> PodcaRenderUiNode(
            node = node,
            renderChild = { child ->
                PodcaRenderDocumentNode(
                    node = child,
                    runtime = runtime,
                )
            },
        )

        else -> PodcaRenderChildren(
            node = node,
            renderChild = { child ->
                PodcaRenderDocumentNode(
                    node = child,
                    runtime = runtime,
                )
            },
        )
    }
}
