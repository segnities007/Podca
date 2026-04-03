package com.podca.sdui.studio.ui.material3

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.core.ActionBindingProto
import com.podca.sdui.protocol.material3.BadgeProto
import com.podca.sdui.protocol.material3.BadgedBoxProto
import com.podca.sdui.protocol.ui.graphics.ColorProto
import com.podca.sdui.studio.core.PodcaNode
import com.podca.sdui.studio.ui.core.PodcaModifier

@Composable
public fun PodcaBadgedBox(
    modifier: PodcaModifier = PodcaModifier.Empty,
    hasBadge: Boolean = true,
    hasContent: Boolean = true,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
    badge: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit,
) {
    PodcaNode(
        type = "material3.BadgedBox",
        message = BadgedBoxProto(
            modifier = modifier.toProto(),
            has_badge = hasBadge || badge != null,
            has_content = hasContent,
        ),
        key = key,
        actions = actions,
        encode = BadgedBoxProto.ADAPTER::encode,
    ) {
        if (badge != null) {
            PodcaNode(
                type = "material3.BadgedBox.BadgeSlot",
                slot = "badge",
                content = badge,
            )
        }
        content()
    }
}

@Composable
public fun PodcaBadge(
    modifier: PodcaModifier = PodcaModifier.Empty,
    containerColor: ColorProto? = null,
    contentColor: ColorProto? = null,
    hasContent: Boolean = true,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
    content: @Composable () -> Unit = {},
) {
    PodcaNode(
        type = "material3.Badge",
        message = BadgeProto(
            modifier = modifier.toProto(),
            container_color = containerColor,
            content_color = contentColor,
            has_content = hasContent,
        ),
        key = key,
        actions = actions,
        encode = BadgeProto.ADAPTER::encode,
        content = content,
    )
}
