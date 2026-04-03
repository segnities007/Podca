package com.podca.sdui.studio.ui.material3

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.material3.MenuProto
import com.podca.sdui.studio.core.PodcaNode
import com.podca.sdui.studio.ui.core.PodcaModifier

@Composable
public fun PodcaMenu(
    expanded: Boolean,
    modifier: PodcaModifier = PodcaModifier.Empty,
    content: @Composable () -> Unit,
) {
    PodcaNode(
        type = "material3.Menu",
        message = MenuProto(
            modifier = modifier.toProto(),
            expanded = expanded,
            has_content = true,
        ),
        encode = MenuProto.ADAPTER::encode,
        content = content,
    )
}
