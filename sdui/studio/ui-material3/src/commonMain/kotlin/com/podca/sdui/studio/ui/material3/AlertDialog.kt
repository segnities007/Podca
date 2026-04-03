package com.podca.sdui.studio.ui.material3

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.core.ActionBindingProto
import com.podca.sdui.protocol.material3.AlertDialogProto
import com.podca.sdui.protocol.material3.BasicAlertDialogProto
import com.podca.sdui.protocol.ui.graphics.ColorProto
import com.podca.sdui.protocol.ui.graphics.ShapeProto
import com.podca.sdui.protocol.ui.unit.DpProto
import com.podca.sdui.studio.core.PodcaNode
import com.podca.sdui.studio.ui.core.PodcaModifier

@Composable
public fun PodcaAlertDialog(
    modifier: PodcaModifier = PodcaModifier.Empty,
    hasDismissButton: Boolean = false,
    hasIcon: Boolean = false,
    hasTitle: Boolean = false,
    hasText: Boolean = false,
    shape: ShapeProto? = null,
    containerColor: ColorProto? = null,
    iconContentColor: ColorProto? = null,
    titleContentColor: ColorProto? = null,
    textContentColor: ColorProto? = null,
    tonalElevation: DpProto? = null,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
    confirmButton: (@Composable () -> Unit)? = null,
    dismissButton: (@Composable () -> Unit)? = null,
    icon: (@Composable () -> Unit)? = null,
    title: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    PodcaNode(
        type = "material3.AlertDialog",
        message = AlertDialogProto(
            modifier = modifier.toProto(),
            has_dismiss_button = hasDismissButton || dismissButton != null,
            has_icon = hasIcon || icon != null,
            has_title = hasTitle || title != null,
            has_text = hasText,
            shape = shape,
            container_color = containerColor,
            icon_content_color = iconContentColor,
            title_content_color = titleContentColor,
            text_content_color = textContentColor,
            tonal_elevation = tonalElevation,
        ),
        key = key,
        actions = actions,
        encode = AlertDialogProto.ADAPTER::encode,
    ) {
        if (confirmButton != null) {
            PodcaNode(
                type = "material3.AlertDialog.ConfirmButtonSlot",
                slot = "confirmButton",
                content = confirmButton,
            )
        }
        if (dismissButton != null) {
            PodcaNode(
                type = "material3.AlertDialog.DismissButtonSlot",
                slot = "dismissButton",
                content = dismissButton,
            )
        }
        if (icon != null) {
            PodcaNode(
                type = "material3.AlertDialog.IconSlot",
                slot = "icon",
                content = icon,
            )
        }
        if (title != null) {
            PodcaNode(
                type = "material3.AlertDialog.TitleSlot",
                slot = "title",
                content = title,
            )
        }
        PodcaNode(
            type = "material3.AlertDialog.TextSlot",
            slot = "text",
            content = content,
        )
    }
}

@Composable
public fun PodcaBasicAlertDialog(
    modifier: PodcaModifier = PodcaModifier.Empty,
    hasContent: Boolean = true,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
    content: @Composable () -> Unit,
) {
    PodcaNode(
        type = "material3.BasicAlertDialog",
        message = BasicAlertDialogProto(
            modifier = modifier.toProto(),
            has_content = hasContent,
        ),
        key = key,
        actions = actions,
        encode = BasicAlertDialogProto.ADAPTER::encode,
        content = content,
    )
}
