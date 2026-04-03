package com.podca.sdui.studio.ui.foundation.layout

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.core.ActionBindingProto
import com.podca.sdui.protocol.foundation.layout.FlowLayoutBuildingBlocksProto
import com.podca.sdui.protocol.foundation.layout.FlowLayoutOverflowProto
import com.podca.sdui.protocol.ui.unit.DpProto
import com.podca.sdui.studio.core.PodcaNode

@Composable
public fun PodcaFlowLayoutBuildingBlocks(
    maxItemsInMainAxis: Int = 0,
    maxLines: Int = 0,
    mainAxisSpacing: DpProto? = null,
    crossAxisSpacing: DpProto? = null,
    overflow: FlowLayoutOverflowProto? = null,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
) {
    PodcaNode(
        type = "foundation.layout.FlowLayoutBuildingBlocks",
        message = FlowLayoutBuildingBlocksProto(
            max_items_in_main_axis = maxItemsInMainAxis,
            max_lines = maxLines,
            main_axis_spacing = mainAxisSpacing,
            cross_axis_spacing = crossAxisSpacing,
            overflow = overflow,
        ),
        key = key,
        actions = actions,
        encode = FlowLayoutBuildingBlocksProto.ADAPTER::encode,
    )
}
