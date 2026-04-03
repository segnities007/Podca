package com.podca.sdui.studio.ui.foundation.layout

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.core.ActionBindingProto
import com.podca.sdui.protocol.foundation.layout.FrProto
import com.podca.sdui.protocol.foundation.layout.GridConfigurationProto
import com.podca.sdui.protocol.foundation.layout.GridFlowProto
import com.podca.sdui.protocol.foundation.layout.GridItemProto
import com.podca.sdui.protocol.foundation.layout.GridProto
import com.podca.sdui.protocol.foundation.layout.GridScopeProto
import com.podca.sdui.protocol.foundation.layout.GridTrackSizeProto
import com.podca.sdui.protocol.foundation.layout.GridTrackTypeProto
import com.podca.sdui.protocol.ui.alignment.AlignmentProto
import com.podca.sdui.protocol.ui.unit.DpProto
import com.podca.sdui.studio.core.PodcaNode
import com.podca.sdui.studio.ui.core.PodcaModifier

@Composable
public fun PodcaFr(
    value: Float = 0f,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
) {
    PodcaNode(
        type = "foundation.layout.Fr",
        message = FrProto(value_ = value),
        key = key,
        actions = actions,
        encode = FrProto.ADAPTER::encode,
    )
}

@Composable
public fun PodcaGridTrackSize(
    type: GridTrackTypeProto = GridTrackTypeProto.GRID_TRACK_TYPE_FIXED,
    fixed: DpProto? = null,
    percentage: Float? = null,
    flex: FrProto? = null,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
) {
    PodcaNode(
        type = "foundation.layout.GridTrackSize",
        message = GridTrackSizeProto(
            type = type,
            fixed = fixed,
            percentage = percentage,
            flex = flex,
        ),
        key = key,
        actions = actions,
        encode = GridTrackSizeProto.ADAPTER::encode,
    )
}

@Composable
public fun PodcaGridItem(
    row: Int = 0,
    column: Int = 0,
    rowSpan: Int = 0,
    columnSpan: Int = 0,
    alignment: AlignmentProto? = null,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
) {
    PodcaNode(
        type = "foundation.layout.GridItem",
        message = GridItemProto(
            row = row,
            column = column,
            row_span = rowSpan,
            column_span = columnSpan,
            alignment = alignment,
        ),
        key = key,
        actions = actions,
        encode = GridItemProto.ADAPTER::encode,
    )
}

@Composable
public fun PodcaGridConfiguration(
    flow: GridFlowProto = GridFlowProto.GRID_FLOW_ROW,
    columns: List<GridTrackSizeProto> = emptyList(),
    rows: List<GridTrackSizeProto> = emptyList(),
    rowGap: DpProto? = null,
    columnGap: DpProto? = null,
    maxGridIndex: Int = 0,
    gridIndexUnspecified: Int = 0,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
) {
    PodcaNode(
        type = "foundation.layout.GridConfiguration",
        message = GridConfigurationProto(
            flow = flow,
            columns = columns,
            rows = rows,
            row_gap = rowGap,
            column_gap = columnGap,
            max_grid_index = maxGridIndex,
            grid_index_unspecified = gridIndexUnspecified,
        ),
        key = key,
        actions = actions,
        encode = GridConfigurationProto.ADAPTER::encode,
    )
}

@Composable
public fun PodcaGridScope(
    gridItem: GridItemProto? = null,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
    content: @Composable () -> Unit = {},
) {
    PodcaNode(
        type = "foundation.layout.GridScope",
        message = GridScopeProto(grid_item = gridItem),
        key = key,
        actions = actions,
        encode = GridScopeProto.ADAPTER::encode,
        content = content,
    )
}

@Composable
public fun PodcaGrid(
    config: GridConfigurationProto? = null,
    modifier: PodcaModifier = PodcaModifier.Empty,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
    content: @Composable () -> Unit,
) {
    PodcaNode(
        type = "foundation.layout.Grid",
        message = GridProto(
            config = config,
            modifier = modifier.toProto(),
        ),
        key = key,
        actions = actions,
        encode = GridProto.ADAPTER::encode,
        content = content,
    )
}
