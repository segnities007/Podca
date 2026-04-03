package com.podca.sdui.studio.ui.material3

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.material3.NavigationRailProto
import com.podca.sdui.studio.core.PodcaNode
import com.podca.sdui.studio.ui.core.PodcaModifier

@Composable
public fun PodcaNavigationRail(
    selectedIndex: Int,
    modifier: PodcaModifier = PodcaModifier.Empty,
    content: @Composable () -> Unit,
) {
    PodcaNode(
        type = "material3.NavigationRail",
        message = NavigationRailProto(
            modifier = modifier.toProto(),
            selected_index = selectedIndex,
            has_content = true,
        ),
        encode = NavigationRailProto.ADAPTER::encode,
        content = content,
    )
}
