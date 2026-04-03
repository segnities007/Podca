package com.podca.sdui.studio.ui.material3

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.material3.NavigationBarProto
import com.podca.sdui.studio.core.PodcaNode
import com.podca.sdui.studio.ui.core.PodcaModifier

@Composable
public fun PodcaNavigationBar(
    selectedIndex: Int,
    modifier: PodcaModifier = PodcaModifier.Empty,
    content: @Composable () -> Unit,
) {
    PodcaNode(
        type = "material3.NavigationBar",
        message = NavigationBarProto(
            modifier = modifier.toProto(),
            selected_index = selectedIndex,
            has_content = true,
        ),
        encode = NavigationBarProto.ADAPTER::encode,
        content = content,
    )
}
