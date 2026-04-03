package com.podca.sdui.studio.ui.foundation.layout

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.core.ActionBindingProto
import com.podca.sdui.protocol.foundation.layout.ContextualFlowColumnOverflowProto
import com.podca.sdui.protocol.foundation.layout.ContextualFlowRowOverflowProto
import com.podca.sdui.protocol.foundation.layout.FlowColumnOverflowProto
import com.podca.sdui.protocol.foundation.layout.FlowLayoutOverflowProto
import com.podca.sdui.protocol.foundation.layout.FlowLayoutOverflowTypeProto
import com.podca.sdui.protocol.foundation.layout.FlowRowOverflowProto
import com.podca.sdui.studio.core.PodcaNode

@Composable
public fun PodcaFlowRowOverflow(
    type: FlowLayoutOverflowTypeProto = FlowLayoutOverflowTypeProto.FLOW_LAYOUT_OVERFLOW_TYPE_VISIBLE,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
) {
    PodcaNode(
        type = "foundation.layout.FlowRowOverflow",
        message = FlowRowOverflowProto(type = type),
        key = key,
        actions = actions,
        encode = FlowRowOverflowProto.ADAPTER::encode,
    )
}

@Composable
public fun PodcaFlowColumnOverflow(
    type: FlowLayoutOverflowTypeProto = FlowLayoutOverflowTypeProto.FLOW_LAYOUT_OVERFLOW_TYPE_VISIBLE,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
) {
    PodcaNode(
        type = "foundation.layout.FlowColumnOverflow",
        message = FlowColumnOverflowProto(type = type),
        key = key,
        actions = actions,
        encode = FlowColumnOverflowProto.ADAPTER::encode,
    )
}

@Composable
public fun PodcaContextualFlowRowOverflow(
    type: FlowLayoutOverflowTypeProto = FlowLayoutOverflowTypeProto.FLOW_LAYOUT_OVERFLOW_TYPE_VISIBLE,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
) {
    PodcaNode(
        type = "foundation.layout.ContextualFlowRowOverflow",
        message = ContextualFlowRowOverflowProto(type = type),
        key = key,
        actions = actions,
        encode = ContextualFlowRowOverflowProto.ADAPTER::encode,
    )
}

@Composable
public fun PodcaContextualFlowColumnOverflow(
    type: FlowLayoutOverflowTypeProto = FlowLayoutOverflowTypeProto.FLOW_LAYOUT_OVERFLOW_TYPE_VISIBLE,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
) {
    PodcaNode(
        type = "foundation.layout.ContextualFlowColumnOverflow",
        message = ContextualFlowColumnOverflowProto(type = type),
        key = key,
        actions = actions,
        encode = ContextualFlowColumnOverflowProto.ADAPTER::encode,
    )
}

@Composable
public fun PodcaFlowLayoutOverflow(
    type: FlowLayoutOverflowTypeProto = FlowLayoutOverflowTypeProto.FLOW_LAYOUT_OVERFLOW_TYPE_VISIBLE,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
) {
    PodcaNode(
        type = "foundation.layout.FlowLayoutOverflow",
        message = FlowLayoutOverflowProto(type = type),
        key = key,
        actions = actions,
        encode = FlowLayoutOverflowProto.ADAPTER::encode,
    )
}
