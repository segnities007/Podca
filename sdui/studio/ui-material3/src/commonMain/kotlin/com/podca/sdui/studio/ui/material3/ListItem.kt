package com.podca.sdui.studio.ui.material3

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.core.ActionBindingProto
import com.podca.sdui.protocol.material3.ListItemColorsProto
import com.podca.sdui.protocol.material3.ListItemProto
import com.podca.sdui.studio.core.PodcaNode
import com.podca.sdui.studio.ui.core.PodcaModifier

@Composable
public fun PodcaListItem(
    headline: String,
    modifier: PodcaModifier = PodcaModifier.Empty,
    enabled: Boolean = true,
    supporting: String = "",
    overline: String = "",
    hasLeadingIcon: Boolean = false,
    hasTrailingIcon: Boolean = false,
    colors: ListItemColorsProto? = null,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
    leadingContent: (@Composable () -> Unit)? = null,
    trailingContent: (@Composable () -> Unit)? = null,
) {
    PodcaNode(
        type = "material3.ListItem",
        message = ListItemProto(
            modifier = modifier.toProto(),
            enabled = enabled,
            headline = headline,
            supporting = supporting,
            overline = overline,
            has_leading_icon = hasLeadingIcon || leadingContent != null,
            has_trailing_icon = hasTrailingIcon || trailingContent != null,
            colors = colors,
        ),
        key = key,
        actions = actions,
        encode = ListItemProto.ADAPTER::encode,
    ) {
        if (leadingContent != null) {
            PodcaNode(
                type = "material3.ListItem.LeadingContentSlot",
                slot = "leadingContent",
                content = leadingContent,
            )
        }
        if (trailingContent != null) {
            PodcaNode(
                type = "material3.ListItem.TrailingContentSlot",
                slot = "trailingContent",
                content = trailingContent,
            )
        }
    }
}
