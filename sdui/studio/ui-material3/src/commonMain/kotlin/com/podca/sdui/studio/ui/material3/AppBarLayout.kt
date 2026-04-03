package com.podca.sdui.studio.ui.material3

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.core.ActionBindingProto
import com.podca.sdui.protocol.material3.AppBarColumnProto
import com.podca.sdui.protocol.material3.AppBarColumnScopeProto
import com.podca.sdui.protocol.material3.AppBarRowProto
import com.podca.sdui.protocol.material3.AppBarRowScopeProto
import com.podca.sdui.studio.core.PodcaNode
import com.podca.sdui.studio.ui.core.PodcaModifier

@Composable
public fun PodcaAppBarColumn(
    modifier: PodcaModifier = PodcaModifier.Empty,
    scope: AppBarColumnScopeProto? = null,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
    content: @Composable () -> Unit,
) {
    PodcaNode(
        type = "material3.AppBarColumn",
        message = AppBarColumnProto(
            modifier = modifier.toProto(),
            scope = scope,
        ),
        key = key,
        actions = actions,
        encode = AppBarColumnProto.ADAPTER::encode,
        content = content,
    )
}

@Composable
public fun PodcaAppBarRow(
    modifier: PodcaModifier = PodcaModifier.Empty,
    scope: AppBarRowScopeProto? = null,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
    content: @Composable () -> Unit,
) {
    PodcaNode(
        type = "material3.AppBarRow",
        message = AppBarRowProto(
            modifier = modifier.toProto(),
            scope = scope,
        ),
        key = key,
        actions = actions,
        encode = AppBarRowProto.ADAPTER::encode,
        content = content,
    )
}
