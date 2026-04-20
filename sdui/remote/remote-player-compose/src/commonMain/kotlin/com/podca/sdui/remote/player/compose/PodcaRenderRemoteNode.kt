package com.podca.sdui.remote.player.compose

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.podca.sdui.player.engine.PodcaDocumentNode
import com.podca.sdui.player.engine.PodcaRuntime
import com.podca.sdui.protocol.core.ActionBindingProto
import com.podca.sdui.protocol.core.ActionTriggerProto
import com.podca.sdui.remote.core.RemoteAlignmentCrossAxisProto
import com.podca.sdui.remote.core.RemoteArrangementMainAxisProto
import com.podca.sdui.remote.core.RemoteKindProto
import com.podca.sdui.remote.core.RemoteNodeProto
import com.podca.sdui.remote.core.RemotePaddingProto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

/**
 * Renders [PodcaDocumentNode] with type `remote.Node` by decoding [RemoteNodeProto] from [PodcaDocumentNode.payload].
 * Document-level [PodcaDocumentNode.children] are ignored for this node; the subtree lives entirely in the payload.
 */
@Composable
public fun PodcaRenderRemoteDocumentNode(
    node: PodcaDocumentNode,
    runtime: PodcaRuntime,
) {
    val root =
        runCatching {
            RemoteNodeProto.ADAPTER.decode(node.payload.toByteArray())
        }.getOrNull() ?: return

    val scope = rememberCoroutineScope()
    PodcaRenderRemoteSubtree(
        root = root,
        hostNode = node,
        runtime = runtime,
        scope = scope,
    )
}

@Composable
private fun PodcaRenderRemoteSubtree(
    root: RemoteNodeProto,
    hostNode: PodcaDocumentNode,
    runtime: PodcaRuntime,
    scope: CoroutineScope,
) {
    val modifier = Modifier.withRemoteFill(root).withRemotePadding(root.padding)
    when (root.kind) {
        RemoteKindProto.REMOTE_KIND_BOX ->
            Box(modifier = modifier) {
                for (child in root.children) {
                    PodcaRenderRemoteSubtree(child, hostNode, runtime, scope)
                }
            }

        RemoteKindProto.REMOTE_KIND_COLUMN ->
            Column(
                modifier = modifier,
                verticalArrangement = root.columnVerticalArrangement(),
                horizontalAlignment = root.columnCrossAxisAlignmentHorizontal(),
            ) {
                for (child in root.children) {
                    RemoteColumnChild(
                        child = child,
                        hostNode = hostNode,
                        runtime = runtime,
                        scope = scope,
                    )
                }
            }

        RemoteKindProto.REMOTE_KIND_ROW ->
            Row(
                modifier = modifier,
                horizontalArrangement = root.rowHorizontalArrangement(),
                verticalAlignment = root.rowCrossAxisAlignmentVertical(),
            ) {
                for (child in root.children) {
                    RemoteRowChild(
                        child = child,
                        hostNode = hostNode,
                        runtime = runtime,
                        scope = scope,
                    )
                }
            }

        RemoteKindProto.REMOTE_KIND_VERTICAL_SCROLL -> {
            val child = root.children.singleOrNull() ?: return
            val maxH = root.vertical_scroll_max_height_dp
            val scrollState = rememberScrollState()
            val scrollModifier =
                modifier
                    .then(if (maxH > 0f) Modifier.heightIn(max = maxH.dp) else Modifier)
                    .verticalScroll(scrollState)
            Column(modifier = scrollModifier) {
                PodcaRenderRemoteSubtree(child, hostNode, runtime, scope)
            }
        }

        RemoteKindProto.REMOTE_KIND_TEXT -> {
            val defaultSize = 14.sp
            val argb = root.text_color_argb
            val textColor =
                if (argb != 0) {
                    Color(argb)
                } else {
                    Color.Unspecified
                }
            val baseStyle =
                TextStyle(
                    fontSize = if (root.font_size > 0f) root.font_size.sp else defaultSize,
                    fontWeight =
                        if (root.font_weight > 0f) {
                            FontWeight(root.font_weight.toInt())
                        } else {
                            FontWeight.Normal
                        },
                    color = textColor,
                )
            BasicText(
                text = root.text,
                modifier = modifier,
                style = baseStyle,
            )
        }

        RemoteKindProto.REMOTE_KIND_SPACER -> {
            val h = root.spacer_height
            val w = root.spacer_width
            when {
                h > 0f && w > 0f ->
                    Spacer(
                        modifier
                            .width(w.dp)
                            .height(h.dp),
                    )

                h > 0f -> Spacer(modifier.height(h.dp))
                w > 0f -> Spacer(modifier.width(w.dp))
                else -> Spacer(modifier)
            }
        }

        RemoteKindProto.REMOTE_KIND_CLICKABLE -> {
            val child = root.children.firstOrNull()
            val actionId = root.click_action_id
            val clickModifier =
                if (actionId.isNotEmpty()) {
                    Modifier.clickable {
                        val binding =
                            ActionBindingProto(
                                trigger = ActionTriggerProto.ACTION_TRIGGER_CLICK,
                                action_id = actionId,
                            )
                        scope.launch {
                            runtime.dispatch(hostNode.key, binding)
                        }
                    }
                } else {
                    Modifier
                }
            Box(modifier = modifier.then(clickModifier)) {
                if (child != null) {
                    PodcaRenderRemoteSubtree(child, hostNode, runtime, scope)
                }
            }
        }

        RemoteKindProto.REMOTE_KIND_UNSPECIFIED -> {
            if (root.children.isNotEmpty()) {
                Column(modifier = modifier) {
                    for (child in root.children) {
                        PodcaRenderRemoteSubtree(child, hostNode, runtime, scope)
                    }
                }
            }
        }
    }
}

@Composable
private fun ColumnScope.RemoteColumnChild(
    child: RemoteNodeProto,
    hostNode: PodcaDocumentNode,
    runtime: PodcaRuntime,
    scope: CoroutineScope,
) {
    val w = child.layout_weight
    if (w > 0f) {
        Box(Modifier.weight(w)) {
            PodcaRenderRemoteSubtree(child, hostNode, runtime, scope)
        }
    } else {
        PodcaRenderRemoteSubtree(child, hostNode, runtime, scope)
    }
}

@Composable
private fun RowScope.RemoteRowChild(
    child: RemoteNodeProto,
    hostNode: PodcaDocumentNode,
    runtime: PodcaRuntime,
    scope: CoroutineScope,
) {
    val w = child.layout_weight
    if (w > 0f) {
        Box(Modifier.weight(w)) {
            PodcaRenderRemoteSubtree(child, hostNode, runtime, scope)
        }
    } else {
        PodcaRenderRemoteSubtree(child, hostNode, runtime, scope)
    }
}

private fun RemoteNodeProto.columnVerticalArrangement(): Arrangement.Vertical =
    when (column_vertical_main) {
        RemoteArrangementMainAxisProto.REMOTE_MAIN_AXIS_SPACED_BY -> {
            val space = column_vertical_spacing_dp
            if (space > 0f) Arrangement.spacedBy(space.dp) else Arrangement.spacedBy(0.dp)
        }
        RemoteArrangementMainAxisProto.REMOTE_MAIN_AXIS_CENTER -> Arrangement.Center
        RemoteArrangementMainAxisProto.REMOTE_MAIN_AXIS_END -> Arrangement.Bottom
        RemoteArrangementMainAxisProto.REMOTE_MAIN_AXIS_START,
        RemoteArrangementMainAxisProto.REMOTE_MAIN_AXIS_UNSPECIFIED -> Arrangement.Top
    }

private fun RemoteNodeProto.columnCrossAxisAlignmentHorizontal(): Alignment.Horizontal =
    when (column_horizontal_cross) {
        RemoteAlignmentCrossAxisProto.REMOTE_CROSS_AXIS_CENTER -> Alignment.CenterHorizontally
        RemoteAlignmentCrossAxisProto.REMOTE_CROSS_AXIS_END -> Alignment.End
        RemoteAlignmentCrossAxisProto.REMOTE_CROSS_AXIS_START,
        RemoteAlignmentCrossAxisProto.REMOTE_CROSS_AXIS_UNSPECIFIED -> Alignment.Start
    }

private fun RemoteNodeProto.rowHorizontalArrangement(): Arrangement.Horizontal =
    when (row_horizontal_main) {
        RemoteArrangementMainAxisProto.REMOTE_MAIN_AXIS_SPACED_BY -> {
            val space = row_horizontal_spacing_dp
            if (space > 0f) Arrangement.spacedBy(space.dp) else Arrangement.spacedBy(0.dp)
        }
        RemoteArrangementMainAxisProto.REMOTE_MAIN_AXIS_CENTER -> Arrangement.Center
        RemoteArrangementMainAxisProto.REMOTE_MAIN_AXIS_END -> Arrangement.End
        RemoteArrangementMainAxisProto.REMOTE_MAIN_AXIS_START,
        RemoteArrangementMainAxisProto.REMOTE_MAIN_AXIS_UNSPECIFIED -> Arrangement.Start
    }

private fun RemoteNodeProto.rowCrossAxisAlignmentVertical(): Alignment.Vertical =
    when (row_vertical_cross) {
        RemoteAlignmentCrossAxisProto.REMOTE_CROSS_AXIS_CENTER -> Alignment.CenterVertically
        RemoteAlignmentCrossAxisProto.REMOTE_CROSS_AXIS_END -> Alignment.Bottom
        RemoteAlignmentCrossAxisProto.REMOTE_CROSS_AXIS_START,
        RemoteAlignmentCrossAxisProto.REMOTE_CROSS_AXIS_UNSPECIFIED -> Alignment.Top
    }

private fun Modifier.withRemoteFill(root: RemoteNodeProto): Modifier {
    var m = this
    if (root.fill_max_width) m = m.then(Modifier.fillMaxWidth())
    if (root.fill_max_height) m = m.then(Modifier.fillMaxHeight())
    return m
}

private fun Modifier.withRemotePadding(p: RemotePaddingProto?): Modifier {
    if (p == null) return this
    if (p.start == 0f && p.top == 0f && p.end == 0f && p.bottom == 0f) return this
    return padding(p.start.dp, p.top.dp, p.end.dp, p.bottom.dp)
}
