package com.podca.sdui.studio.ui.material3

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.core.ActionBindingProto
import com.podca.sdui.protocol.material3.FabPositionProto
import com.podca.sdui.protocol.material3.ScaffoldProto
import com.podca.sdui.studio.core.PodcaNode
import com.podca.sdui.studio.ui.core.PodcaModifier

@Composable
public fun PodcaScaffold(
    modifier: PodcaModifier = PodcaModifier.Empty,
    floatingActionButtonPosition: FabPositionProto = FabPositionProto.FAB_POSITION_END,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
    topBar: (@Composable () -> Unit)? = null,
    bottomBar: (@Composable () -> Unit)? = null,
    snackbarHost: (@Composable () -> Unit)? = null,
    floatingActionButton: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    PodcaNode(
        type = "material3.Scaffold",
        message = ScaffoldProto(
            modifier = modifier.toProto(),
            floating_action_button_position = floatingActionButtonPosition,
            has_content = true,
        ),
        key = key,
        actions = actions,
        encode = ScaffoldProto.ADAPTER::encode,
    ) {
        if (topBar != null) {
            PodcaNode(
                type = "material3.Scaffold.TopBarSlot",
                slot = "topBar",
                content = topBar,
            )
        }
        if (bottomBar != null) {
            PodcaNode(
                type = "material3.Scaffold.BottomBarSlot",
                slot = "bottomBar",
                content = bottomBar,
            )
        }
        if (snackbarHost != null) {
            PodcaNode(
                type = "material3.Scaffold.SnackbarHostSlot",
                slot = "snackbarHost",
                content = snackbarHost,
            )
        }
        if (floatingActionButton != null) {
            PodcaNode(
                type = "material3.Scaffold.FloatingActionButtonSlot",
                slot = "floatingActionButton",
                content = floatingActionButton,
            )
        }
        PodcaNode(
            type = "material3.Scaffold.ContentSlot",
            slot = "content",
            content = content,
        )
    }
}
