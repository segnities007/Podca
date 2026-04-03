package com.podca.sdui.studio.ui.foundation.layout

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.foundation.layout.AlignmentLineProto
import com.podca.sdui.protocol.foundation.layout.ArrangementKindProto
import com.podca.sdui.protocol.foundation.layout.BoxWithConstraintsScopeProto
import com.podca.sdui.protocol.foundation.layout.ContextualFlowColumnOverflowScopeProto
import com.podca.sdui.protocol.foundation.layout.ContextualFlowColumnScopeProto
import com.podca.sdui.protocol.foundation.layout.ContextualFlowRowOverflowScopeProto
import com.podca.sdui.protocol.foundation.layout.ContextualFlowRowScopeProto
import com.podca.sdui.protocol.foundation.layout.FlexAlignContentProto
import com.podca.sdui.protocol.foundation.layout.FlexAlignItemsProto
import com.podca.sdui.protocol.foundation.layout.FlexAlignSelfProto
import com.podca.sdui.protocol.foundation.layout.FlexDirectionProto
import com.podca.sdui.protocol.foundation.layout.FlexJustifyContentProto
import com.podca.sdui.protocol.foundation.layout.FlexWrapProto
import com.podca.sdui.protocol.foundation.layout.FlowColumnOverflowScopeProto
import com.podca.sdui.protocol.foundation.layout.FlowColumnScopeProto
import com.podca.sdui.protocol.foundation.layout.FlowLayoutOverflowTypeProto
import com.podca.sdui.protocol.foundation.layout.FlowRowOverflowScopeProto
import com.podca.sdui.protocol.foundation.layout.FlowRowScopeProto
import com.podca.sdui.protocol.foundation.layout.GridFlowProto
import com.podca.sdui.protocol.foundation.layout.GridTrackTypeProto
import com.podca.sdui.protocol.foundation.layout.IntrinsicSizeProto
import com.podca.sdui.protocol.foundation.layout.LayoutOrientationProto
import com.podca.sdui.protocol.foundation.layout.SizeDirectionProto
import com.podca.sdui.protocol.ui.unit.ConstraintsProto
import com.podca.sdui.protocol.ui.unit.DpProto
import com.podca.sdui.studio.core.PodcaNode

public fun PodcaAlignmentLine(
    line: AlignmentLineProto = AlignmentLineProto.ALIGNMENT_LINE_UNSPECIFIED,
): AlignmentLineProto = line

public fun PodcaArrangementKind(
    kind: ArrangementKindProto = ArrangementKindProto.ARRANGEMENT_KIND_UNSPECIFIED,
): ArrangementKindProto = kind

public fun PodcaFlowLayoutOverflowType(
    type: FlowLayoutOverflowTypeProto = FlowLayoutOverflowTypeProto.FLOW_LAYOUT_OVERFLOW_TYPE_VISIBLE,
): FlowLayoutOverflowTypeProto = type

public fun PodcaGridFlow(
    flow: GridFlowProto = GridFlowProto.GRID_FLOW_ROW,
): GridFlowProto = flow

public fun PodcaGridTrackType(
    type: GridTrackTypeProto = GridTrackTypeProto.GRID_TRACK_TYPE_FIXED,
): GridTrackTypeProto = type

public fun PodcaIntrinsicSize(
    size: IntrinsicSizeProto = IntrinsicSizeProto.INTRINSIC_SIZE_MIN,
): IntrinsicSizeProto = size

public fun PodcaLayoutOrientation(
    orientation: LayoutOrientationProto = LayoutOrientationProto.LAYOUT_ORIENTATION_HORIZONTAL,
): LayoutOrientationProto = orientation

public fun PodcaSizeDirection(
    direction: SizeDirectionProto = SizeDirectionProto.SIZE_DIRECTION_HORIZONTAL,
): SizeDirectionProto = direction

public fun PodcaFlexDirection(
    direction: FlexDirectionProto = FlexDirectionProto.FLEX_DIRECTION_ROW,
): FlexDirectionProto = direction

public fun PodcaFlexWrap(
    wrap: FlexWrapProto = FlexWrapProto.FLEX_WRAP_NOWRAP,
): FlexWrapProto = wrap

public fun PodcaFlexJustifyContent(
    justifyContent: FlexJustifyContentProto =
        FlexJustifyContentProto.FLEX_JUSTIFY_CONTENT_START,
): FlexJustifyContentProto = justifyContent

public fun PodcaFlexAlignItems(
    alignItems: FlexAlignItemsProto = FlexAlignItemsProto.FLEX_ALIGN_ITEMS_START,
): FlexAlignItemsProto = alignItems

public fun PodcaFlexAlignContent(
    alignContent: FlexAlignContentProto = FlexAlignContentProto.FLEX_ALIGN_CONTENT_START,
): FlexAlignContentProto = alignContent

public fun PodcaFlexAlignSelf(
    alignSelf: FlexAlignSelfProto = FlexAlignSelfProto.FLEX_ALIGN_SELF_AUTO,
): FlexAlignSelfProto = alignSelf

public fun PodcaFlowRowScope(
    fillMaxRowHeightFraction: Float = 0f,
): FlowRowScopeProto =
    FlowRowScopeProto(
        fill_max_row_height_fraction = fillMaxRowHeightFraction,
    )

public fun PodcaFlowRowOverflowScope(
    rowScope: FlowRowScopeProto? = null,
    shownItemCount: Int = 0,
): FlowRowOverflowScopeProto =
    FlowRowOverflowScopeProto(
        row_scope = rowScope,
        shown_item_count = shownItemCount,
    )

public fun PodcaFlowColumnScope(
    fillMaxColumnWidthFraction: Float = 0f,
): FlowColumnScopeProto =
    FlowColumnScopeProto(
        fill_max_column_width_fraction = fillMaxColumnWidthFraction,
    )

public fun PodcaFlowColumnOverflowScope(
    columnScope: FlowColumnScopeProto? = null,
    shownItemCount: Int = 0,
): FlowColumnOverflowScopeProto =
    FlowColumnOverflowScopeProto(
        column_scope = columnScope,
        shown_item_count = shownItemCount,
    )

public fun PodcaContextualFlowRowOverflowScope(
    rowScope: ContextualFlowRowScopeProto? = null,
): ContextualFlowRowOverflowScopeProto =
    ContextualFlowRowOverflowScopeProto(
        row_scope = rowScope,
    )

public fun PodcaContextualFlowColumnOverflowScope(
    columnScope: ContextualFlowColumnScopeProto? = null,
): ContextualFlowColumnOverflowScopeProto =
    ContextualFlowColumnOverflowScopeProto(
        column_scope = columnScope,
    )

@Composable
public fun PodcaBoxWithConstraintsScope(
    constraints: ConstraintsProto? = null,
    minWidth: DpProto? = null,
    maxWidth: DpProto? = null,
    minHeight: DpProto? = null,
    maxHeight: DpProto? = null,
) {
    PodcaNode(
        type = "foundation.layout.BoxWithConstraintsScope",
        message = BoxWithConstraintsScopeProto(
            constraints = constraints,
            min_width = minWidth,
            max_width = maxWidth,
            min_height = minHeight,
            max_height = maxHeight,
        ),
        encode = BoxWithConstraintsScopeProto.ADAPTER::encode,
    )
}
