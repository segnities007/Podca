package com.podca.sdui.studio.ui.material3

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.core.ActionBindingProto
import com.podca.sdui.protocol.material3.TabRowProto
import com.podca.sdui.studio.core.PodcaNode
import com.podca.sdui.studio.ui.core.PodcaModifier

@Composable
public fun PodcaTabRow(
    selectedTabIndex: Int,
    modifier: PodcaModifier = PodcaModifier.Empty,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
    content: @Composable () -> Unit,
) {
    PodcaNode(
        type = "material3.TabRow",
        message = TabRowProto(
            modifier = modifier.toProto(),
            selected_tab_index = selectedTabIndex,
        ),
        key = key,
        actions = actions,
        encode = TabRowProto.ADAPTER::encode,
        content = content,
    )
}
