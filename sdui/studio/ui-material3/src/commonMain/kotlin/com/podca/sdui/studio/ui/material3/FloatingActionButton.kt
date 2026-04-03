package com.podca.sdui.studio.ui.material3

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.core.ActionBindingProto
import com.podca.sdui.protocol.material3.FloatingActionButtonProto
import com.podca.sdui.studio.core.PodcaNode
import com.podca.sdui.studio.ui.core.PodcaModifier

@Composable
public fun PodcaFloatingActionButton(
    modifier: PodcaModifier = PodcaModifier.Empty,
    enabled: Boolean = true,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
    content: @Composable () -> Unit = {},
) {
    PodcaNode(
        type = "material3.FloatingActionButton",
        message = FloatingActionButtonProto(
            modifier = modifier.toProto(),
            enabled = enabled,
            has_content = true,
        ),
        key = key,
        actions = actions,
        encode = FloatingActionButtonProto.ADAPTER::encode,
        content = content,
    )
}
