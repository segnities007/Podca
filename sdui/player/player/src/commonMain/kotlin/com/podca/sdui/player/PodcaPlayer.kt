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
import com.podca.sdui.remote.player.compose.PodcaRenderRemoteCanvasProgramNode
import com.podca.sdui.remote.player.compose.PodcaRenderRemoteDocumentNode
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

/**
 * [PodcaDocumentNode.type] に応じて子ツリーを再帰描画する。
 *
 * 振り分け順: `Root` → `remote.CanvasProgram`（描画 op 列）→ `remote.Node` → `foundation.*` → `material3.*` → `ui.*` → それ以外（子のみ再帰）。
 * マッチしない type もクラッシュさせず、子を辿る。
 */
@Composable
public fun PodcaRenderDocumentNode(
    node: PodcaDocumentNode,
    runtime: PodcaRuntime,
) {
    @Composable
    fun Subtree(child: PodcaDocumentNode) {
        PodcaRenderDocumentNode(node = child, runtime = runtime)
    }

    when {
        node.type == "Root" -> Column {
            PodcaRenderChildren(node = node, renderChild = { Subtree(it) })
        }

        node.type == "remote.CanvasProgram" -> PodcaRenderRemoteCanvasProgramNode(
            node = node,
            runtime = runtime,
        )

        node.type == "remote.Node" -> PodcaRenderRemoteDocumentNode(
            node = node,
            runtime = runtime,
        )

        node.type.startsWith("foundation.") -> PodcaRenderFoundationNode(
            node = node,
            renderChild = { Subtree(it) },
        )

        node.type.startsWith("material3.") -> PodcaRenderMaterial3Node(
            node = node,
            runtime = runtime,
            renderChild = { Subtree(it) },
        )

        node.type.startsWith("ui.") -> PodcaRenderUiNode(
            node = node,
            renderChild = { Subtree(it) },
        )

        else -> PodcaRenderChildren(
            node = node,
            renderChild = { Subtree(it) },
        )
    }
}
