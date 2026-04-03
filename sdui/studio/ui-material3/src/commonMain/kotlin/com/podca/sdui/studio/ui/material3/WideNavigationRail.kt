package com.podca.sdui.studio.ui.material3

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.material3.WideNavigationRailProto
import com.podca.sdui.studio.core.PodcaNode
import com.podca.sdui.studio.ui.core.PodcaModifier

@Composable
public fun PodcaWideNavigationRail(
    modifier: PodcaModifier = PodcaModifier.Empty,
    expanded: Boolean = false,
    modal: Boolean = false,
    header: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit = {},
) {
    PodcaNode(
        type = "material3.WideNavigationRail",
        message = WideNavigationRailProto(
            modifier = modifier.toProto(),
            expanded = expanded,
            modal = modal,
            has_header = header != null,
        ),
        encode = WideNavigationRailProto.ADAPTER::encode,
    ) {
        if (header != null) {
            PodcaNode(
                type = "material3.WideNavigationRail.HeaderSlot",
                slot = "header",
                content = header,
            )
        }
        content()
    }
}
