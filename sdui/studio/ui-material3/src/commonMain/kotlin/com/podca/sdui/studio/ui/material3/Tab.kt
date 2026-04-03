package com.podca.sdui.studio.ui.material3

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.core.ActionBindingProto
import com.podca.sdui.protocol.material3.LeadingIconTabProto
import com.podca.sdui.protocol.material3.TabProto
import com.podca.sdui.studio.core.PodcaNode
import com.podca.sdui.studio.ui.core.PodcaModifier

@Composable
public fun PodcaTab(
    selected: Boolean,
    text: String,
    modifier: PodcaModifier = PodcaModifier.Empty,
    enabled: Boolean = true,
    hasIcon: Boolean = false,
    icon: (@Composable () -> Unit)? = null,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
    content: @Composable () -> Unit = {},
) {
    PodcaNode(
        type = "material3.Tab",
        message = TabProto(
            modifier = modifier.toProto(),
            selected = selected,
            enabled = enabled,
            text = text,
            has_icon = hasIcon || icon != null,
        ),
        key = key,
        actions = actions,
        encode = TabProto.ADAPTER::encode,
    ) {
        if (icon != null) {
            PodcaNode(
                type = "material3.Tab.IconSlot",
                slot = "icon",
                content = icon,
            )
        }
        content()
    }
}

@Composable
public fun PodcaLeadingIconTab(
    selected: Boolean,
    text: String,
    modifier: PodcaModifier = PodcaModifier.Empty,
    enabled: Boolean = true,
    hasIcon: Boolean = false,
    icon: (@Composable () -> Unit)? = null,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
    content: @Composable () -> Unit = {},
) {
    PodcaNode(
        type = "material3.LeadingIconTab",
        message = LeadingIconTabProto(
            modifier = modifier.toProto(),
            selected = selected,
            enabled = enabled,
            text = text,
            has_icon = hasIcon || icon != null,
        ),
        key = key,
        actions = actions,
        encode = LeadingIconTabProto.ADAPTER::encode,
    ) {
        if (icon != null) {
            PodcaNode(
                type = "material3.LeadingIconTab.IconSlot",
                slot = "icon",
                content = icon,
            )
        }
        content()
    }
}
