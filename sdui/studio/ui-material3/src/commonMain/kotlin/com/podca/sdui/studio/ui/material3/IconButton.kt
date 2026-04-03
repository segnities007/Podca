package com.podca.sdui.studio.ui.material3

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.core.ActionBindingProto
import com.podca.sdui.protocol.material3.IconButtonColorsProto
import com.podca.sdui.protocol.material3.IconButtonProto
import com.podca.sdui.studio.core.PodcaNode
import com.podca.sdui.studio.ui.core.PodcaModifier

@Composable
public fun PodcaIconButton(
    modifier: PodcaModifier = PodcaModifier.Empty,
    enabled: Boolean = true,
    colors: IconButtonColorsProto? = null,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
    content: @Composable () -> Unit,
) {
    PodcaNode(
        type = "material3.IconButton",
        message = IconButtonProto(
            modifier = modifier.toProto(),
            enabled = enabled,
            colors = colors,
            has_content = true,
        ),
        key = key,
        actions = actions,
        encode = IconButtonProto.ADAPTER::encode,
        content = content,
    )
}
