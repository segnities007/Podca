package com.podca.sdui.studio.ui.material3

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.material3.DrawerValueProto
import com.podca.sdui.protocol.material3.NavigationDrawerProto
import com.podca.sdui.studio.core.PodcaNode
import com.podca.sdui.studio.ui.core.PodcaModifier

@Composable
public fun PodcaNavigationDrawer(
    value: DrawerValueProto,
    modifier: PodcaModifier = PodcaModifier.Empty,
    content: @Composable () -> Unit,
) {
    PodcaNode(
        type = "material3.NavigationDrawer",
        message = NavigationDrawerProto(
            modifier = modifier.toProto(),
            value_ = value,
            has_content = true,
        ),
        encode = NavigationDrawerProto.ADAPTER::encode,
        content = content,
    )
}
