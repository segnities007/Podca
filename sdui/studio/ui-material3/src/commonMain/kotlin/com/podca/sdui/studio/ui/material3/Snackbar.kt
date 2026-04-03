package com.podca.sdui.studio.ui.material3

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.core.ActionBindingProto
import com.podca.sdui.protocol.material3.SnackbarDataProto
import com.podca.sdui.protocol.material3.SnackbarHostProto
import com.podca.sdui.protocol.material3.SnackbarHostStateProto
import com.podca.sdui.protocol.material3.SnackbarProto
import com.podca.sdui.protocol.ui.graphics.ColorProto
import com.podca.sdui.protocol.ui.graphics.ShapeProto
import com.podca.sdui.studio.core.PodcaNode
import com.podca.sdui.studio.ui.core.PodcaModifier

@Composable
public fun PodcaSnackbar(
    modifier: PodcaModifier = PodcaModifier.Empty,
    hasAction: Boolean = false,
    hasDismissAction: Boolean = false,
    actionOnNewLine: Boolean = false,
    shape: ShapeProto? = null,
    containerColor: ColorProto? = null,
    contentColor: ColorProto? = null,
    actionContentColor: ColorProto? = null,
    dismissActionContentColor: ColorProto? = null,
    snackbarData: SnackbarDataProto? = null,
    actionColor: ColorProto? = null,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
    action: (@Composable () -> Unit)? = null,
    dismissAction: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit = {},
) {
    PodcaNode(
        type = "material3.Snackbar",
        message = SnackbarProto(
            modifier = modifier.toProto(),
            has_action = hasAction || action != null,
            has_dismiss_action = hasDismissAction || dismissAction != null,
            action_on_new_line = actionOnNewLine,
            shape = shape,
            container_color = containerColor,
            content_color = contentColor,
            action_content_color = actionContentColor,
            dismiss_action_content_color = dismissActionContentColor,
            snackbar_data = snackbarData,
            action_color = actionColor,
        ),
        key = key,
        actions = actions,
        encode = SnackbarProto.ADAPTER::encode,
    ) {
        if (action != null) {
            PodcaNode(
                type = "material3.Snackbar.ActionSlot",
                slot = "action",
                content = action,
            )
        }
        if (dismissAction != null) {
            PodcaNode(
                type = "material3.Snackbar.DismissActionSlot",
                slot = "dismissAction",
                content = dismissAction,
            )
        }
        content()
    }
}

@Composable
public fun PodcaSnackbarHost(
    hostState: SnackbarHostStateProto,
    modifier: PodcaModifier = PodcaModifier.Empty,
    hasCustomSnackbar: Boolean = false,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
    content: @Composable () -> Unit = {},
) {
    PodcaNode(
        type = "material3.SnackbarHost",
        message = SnackbarHostProto(
            host_state = hostState,
            modifier = modifier.toProto(),
            has_custom_snackbar = hasCustomSnackbar,
        ),
        key = key,
        actions = actions,
        encode = SnackbarHostProto.ADAPTER::encode,
        content = content,
    )
}
