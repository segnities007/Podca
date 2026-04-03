package com.podca.sdui.studio.ui.material3

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.core.ActionBindingProto
import com.podca.sdui.protocol.material3.ChipBorderProto
import com.podca.sdui.protocol.material3.ChipColorsProto
import com.podca.sdui.protocol.material3.ChipElevationProto
import com.podca.sdui.protocol.material3.ChipProto
import com.podca.sdui.studio.core.PodcaNode
import com.podca.sdui.studio.ui.core.PodcaModifier

@Composable
public fun PodcaChip(
    label: String,
    modifier: PodcaModifier = PodcaModifier.Empty,
    enabled: Boolean = true,
    colors: ChipColorsProto? = null,
    border: ChipBorderProto? = null,
    elevation: ChipElevationProto? = null,
    hasLeadingIcon: Boolean = false,
    hasTrailingIcon: Boolean = false,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
    leadingIcon: (@Composable () -> Unit)? = null,
    trailingIcon: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit = {},
) {
    PodcaNode(
        type = "material3.Chip",
        message = ChipProto(
            modifier = modifier.toProto(),
            enabled = enabled,
            label = label,
            has_leading_icon = hasLeadingIcon || leadingIcon != null,
            has_trailing_icon = hasTrailingIcon || trailingIcon != null,
            colors = colors,
            border = border,
            elevation = elevation,
        ),
        key = key,
        actions = actions,
        encode = ChipProto.ADAPTER::encode,
    ) {
        if (leadingIcon != null) {
            PodcaNode(
                type = "material3.Chip.LeadingIconSlot",
                slot = "leadingIcon",
                content = leadingIcon,
            )
        }
        if (trailingIcon != null) {
            PodcaNode(
                type = "material3.Chip.TrailingIconSlot",
                slot = "trailingIcon",
                content = trailingIcon,
            )
        }
        content()
    }
}
