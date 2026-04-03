package com.podca.sdui.studio.ui.foundation.layout

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.core.ActionBindingProto
import com.podca.sdui.protocol.foundation.layout.ComposeFoundationLayoutFlagsProto
import com.podca.sdui.protocol.foundation.layout.LayoutScopeMarkerProto
import com.podca.sdui.studio.core.PodcaNode

@Composable
public fun PodcaComposeFoundationLayoutFlags(
    isOptimizationEnabled: Boolean = false,
    isContextualFlowLayoutEnabled: Boolean = false,
    isWindowInsetsRulersEnabled: Boolean = false,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
) {
    PodcaNode(
        type = "foundation.layout.ComposeFoundationLayoutFlags",
        message = ComposeFoundationLayoutFlagsProto(
            is_optimization_enabled = isOptimizationEnabled,
            is_contextual_flow_layout_enabled = isContextualFlowLayoutEnabled,
            is_window_insets_rulers_enabled = isWindowInsetsRulersEnabled,
        ),
        key = key,
        actions = actions,
        encode = ComposeFoundationLayoutFlagsProto.ADAPTER::encode,
    )
}

@Composable
public fun PodcaLayoutScopeMarker(
    scopeName: String = "",
    restrictsParentData: Boolean = false,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
) {
    PodcaNode(
        type = "foundation.layout.LayoutScopeMarker",
        message = LayoutScopeMarkerProto(
            scope_name = scopeName,
            restricts_parent_data = restrictsParentData,
        ),
        key = key,
        actions = actions,
        encode = LayoutScopeMarkerProto.ADAPTER::encode,
    )
}
