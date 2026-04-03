package com.podca.sdui.studio.ui.material3

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.core.ActionBindingProto
import com.podca.sdui.protocol.material3.SegmentedButtonColorsProto
import com.podca.sdui.protocol.material3.SegmentedButtonProto
import com.podca.sdui.studio.core.PodcaNode
import com.podca.sdui.studio.ui.core.PodcaModifier

@Composable
public fun PodcaSegmentedButton(
    text: String,
    selected: Boolean,
    modifier: PodcaModifier = PodcaModifier.Empty,
    enabled: Boolean = true,
    colors: SegmentedButtonColorsProto? = null,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
    content: @Composable () -> Unit = {},
) {
    PodcaNode(
        type = "material3.SegmentedButton",
        message = SegmentedButtonProto(
            modifier = modifier.toProto(),
            selected = selected,
            enabled = enabled,
            text = text,
            colors = colors,
            has_content = true,
        ),
        key = key,
        actions = actions,
        encode = SegmentedButtonProto.ADAPTER::encode,
        content = content,
    )
}
