package com.podca.sdui.studio.ui.foundation.layout

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.core.ActionBindingProto
import com.podca.sdui.protocol.foundation.layout.ArrangementHorizontalProto
import com.podca.sdui.protocol.foundation.layout.ArrangementVerticalProto
import com.podca.sdui.protocol.foundation.layout.FlowColumnProto
import com.podca.sdui.protocol.foundation.layout.FlowRowProto
import com.podca.sdui.studio.core.PodcaNode
import com.podca.sdui.studio.ui.core.PodcaModifier

@Composable
public fun PodcaFlowRow(
    modifier: PodcaModifier = PodcaModifier.Empty,
    horizontalArrangement: ArrangementHorizontalProto? = null,
    verticalArrangement: ArrangementVerticalProto? = null,
    maxItemsInEachRow: Int = Int.MAX_VALUE,
    maxLines: Int = Int.MAX_VALUE,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
    content: @Composable () -> Unit,
) {
    PodcaNode(
        type = "foundation.layout.FlowRow",
        message = FlowRowProto(
            modifier = modifier.toProto(),
            horizontal_arrangement = horizontalArrangement,
            vertical_arrangement = verticalArrangement,
            max_items_in_each_row = maxItemsInEachRow,
            max_lines = maxLines,
        ),
        key = key,
        actions = actions,
        encode = FlowRowProto.ADAPTER::encode,
        content = content,
    )
}

@Composable
public fun PodcaFlowColumn(
    modifier: PodcaModifier = PodcaModifier.Empty,
    verticalArrangement: ArrangementVerticalProto? = null,
    horizontalArrangement: ArrangementHorizontalProto? = null,
    maxItemsInEachColumn: Int = Int.MAX_VALUE,
    maxLines: Int = Int.MAX_VALUE,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
    content: @Composable () -> Unit,
) {
    PodcaNode(
        type = "foundation.layout.FlowColumn",
        message = FlowColumnProto(
            modifier = modifier.toProto(),
            vertical_arrangement = verticalArrangement,
            horizontal_arrangement = horizontalArrangement,
            max_items_in_each_column = maxItemsInEachColumn,
            max_lines = maxLines,
        ),
        key = key,
        actions = actions,
        encode = FlowColumnProto.ADAPTER::encode,
        content = content,
    )
}
