package com.podca.sdui.studio.ui.foundation.layout

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.core.ActionBindingProto
import com.podca.sdui.protocol.foundation.layout.ContextualFlowColumnProto
import com.podca.sdui.protocol.foundation.layout.ContextualFlowColumnScopeProto
import com.podca.sdui.protocol.foundation.layout.ContextualFlowRowProto
import com.podca.sdui.protocol.foundation.layout.ContextualFlowRowScopeProto
import com.podca.sdui.studio.core.PodcaNode
import com.podca.sdui.studio.ui.core.PodcaModifier

@Composable
public fun PodcaContextualFlowRowScope(
    fillMaxRowHeightFraction: Float = 0f,
    lineIndex: Int = 0,
    indexInLine: Int = 0,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
) {
    PodcaNode(
        type = "foundation.layout.ContextualFlowRowScope",
        message = ContextualFlowRowScopeProto(
            fill_max_row_height_fraction = fillMaxRowHeightFraction,
            line_index = lineIndex,
            index_in_line = indexInLine,
        ),
        key = key,
        actions = actions,
        encode = ContextualFlowRowScopeProto.ADAPTER::encode,
    )
}

@Composable
public fun PodcaContextualFlowColumnScope(
    fillMaxColumnWidthFraction: Float = 0f,
    lineIndex: Int = 0,
    indexInLine: Int = 0,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
) {
    PodcaNode(
        type = "foundation.layout.ContextualFlowColumnScope",
        message = ContextualFlowColumnScopeProto(
            fill_max_column_width_fraction = fillMaxColumnWidthFraction,
            line_index = lineIndex,
            index_in_line = indexInLine,
        ),
        key = key,
        actions = actions,
        encode = ContextualFlowColumnScopeProto.ADAPTER::encode,
    )
}

@Composable
public fun PodcaContextualFlowRow(
    itemCount: Int,
    modifier: PodcaModifier = PodcaModifier.Empty,
    maxLines: Int = Int.MAX_VALUE,
    maxItemsInEachRow: Int = Int.MAX_VALUE,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
    content: @Composable () -> Unit,
) {
    PodcaNode(
        type = "foundation.layout.ContextualFlowRow",
        message = ContextualFlowRowProto(
            modifier = modifier.toProto(),
            item_count = itemCount,
            max_lines = maxLines,
            max_items_in_each_row = maxItemsInEachRow,
        ),
        key = key,
        actions = actions,
        encode = ContextualFlowRowProto.ADAPTER::encode,
        content = content,
    )
}

@Composable
public fun PodcaContextualFlowColumn(
    itemCount: Int,
    modifier: PodcaModifier = PodcaModifier.Empty,
    maxLines: Int = Int.MAX_VALUE,
    maxItemsInEachColumn: Int = Int.MAX_VALUE,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
    content: @Composable () -> Unit,
) {
    PodcaNode(
        type = "foundation.layout.ContextualFlowColumn",
        message = ContextualFlowColumnProto(
            modifier = modifier.toProto(),
            item_count = itemCount,
            max_lines = maxLines,
            max_items_in_each_column = maxItemsInEachColumn,
        ),
        key = key,
        actions = actions,
        encode = ContextualFlowColumnProto.ADAPTER::encode,
        content = content,
    )
}
