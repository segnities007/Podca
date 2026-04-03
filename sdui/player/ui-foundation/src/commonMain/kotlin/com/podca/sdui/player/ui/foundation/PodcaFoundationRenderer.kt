package com.podca.sdui.player.ui.foundation

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowColumn
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.Layout
import androidx.compose.ui.layout.MeasureScope
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.constrainHeight
import androidx.compose.ui.unit.constrainWidth
import androidx.compose.ui.unit.dp
import com.podca.sdui.player.ui.core.toComposeAlignment
import com.podca.sdui.player.ui.core.toComposeArrangement
import com.podca.sdui.player.ui.core.toComposeHorizontalAlignment
import com.podca.sdui.player.ui.core.toComposeModifier
import com.podca.sdui.player.ui.core.toComposeVerticalAlignment
import com.podca.sdui.player.engine.PodcaDocumentNode
import com.podca.sdui.player.ui.core.PodcaRenderChildren
import com.podca.sdui.protocol.foundation.layout.AspectRatioModifierProto
import com.podca.sdui.protocol.foundation.layout.ContextualFlowColumnProto
import com.podca.sdui.protocol.foundation.layout.ContextualFlowRowProto
import com.podca.sdui.protocol.foundation.layout.DefaultMinSizeModifierProto
import com.podca.sdui.protocol.foundation.layout.FlexAlignContentProto
import com.podca.sdui.protocol.foundation.layout.FlexAlignItemsProto
import com.podca.sdui.protocol.foundation.layout.FlexAlignSelfProto
import com.podca.sdui.protocol.foundation.layout.FlexBoxScopeProto
import com.podca.sdui.protocol.foundation.layout.FlexConfigProto
import com.podca.sdui.protocol.foundation.layout.FlexDirectionProto
import com.podca.sdui.protocol.foundation.layout.FlexBoxProto
import com.podca.sdui.protocol.foundation.layout.FlexJustifyContentProto
import com.podca.sdui.protocol.foundation.layout.FlexWrapProto
import com.podca.sdui.protocol.foundation.layout.FillModifierProto
import com.podca.sdui.protocol.foundation.layout.GridProto
import com.podca.sdui.protocol.foundation.layout.GridFlowProto
import com.podca.sdui.protocol.foundation.layout.GridItemProto
import com.podca.sdui.protocol.foundation.layout.GridScopeProto
import com.podca.sdui.protocol.foundation.layout.GridTrackSizeProto
import com.podca.sdui.protocol.foundation.layout.GridTrackTypeProto
import com.podca.sdui.protocol.foundation.layout.InsetsPaddingModifierProto
import com.podca.sdui.protocol.foundation.layout.OffsetModifierProto
import com.podca.sdui.protocol.foundation.layout.OffsetPxModifierProto
import com.podca.sdui.protocol.foundation.layout.PaddingValuesConsumingModifierProto
import com.podca.sdui.protocol.foundation.layout.PaddingValuesProto
import com.podca.sdui.protocol.foundation.layout.SizeDirectionProto
import com.podca.sdui.protocol.foundation.layout.SizeModifierProto
import com.podca.sdui.protocol.foundation.layout.WindowInsetsHeightModifierProto
import com.podca.sdui.protocol.foundation.layout.WindowInsetsProto
import com.podca.sdui.protocol.foundation.layout.WindowInsetsWidthModifierProto
import com.podca.sdui.protocol.foundation.layout.WrapContentModifierProto
import com.podca.sdui.protocol.foundation.layout.BoxProto
import com.podca.sdui.protocol.foundation.layout.BoxWithConstraintsProto
import com.podca.sdui.protocol.foundation.layout.ColumnProto
import com.podca.sdui.protocol.foundation.layout.FlowColumnProto
import com.podca.sdui.protocol.foundation.layout.FlowRowProto
import com.podca.sdui.protocol.foundation.layout.RowProto
import com.podca.sdui.protocol.foundation.layout.SpacerProto
import com.podca.sdui.protocol.ui.unit.DpProto

@OptIn(ExperimentalLayoutApi::class)
@Composable
public fun PodcaRenderFoundationNode(
    node: PodcaDocumentNode,
    renderChild: @Composable (PodcaDocumentNode) -> Unit,
) {
    when (node.type) {
        "foundation.layout.Box" -> {
            val proto = runCatching { BoxProto.ADAPTER.decode(node.payload) }
                .getOrElse { BoxProto() }
            Box(
                modifier = proto.modifier.toComposeModifier(),
                contentAlignment = proto.content_alignment.toComposeAlignment(),
                propagateMinConstraints = proto.propagate_min_constraints,
            ) {
                PodcaRenderChildren(node = node, renderChild = renderChild)
            }
        }

        "foundation.layout.BoxWithConstraints" -> {
            val proto = runCatching { BoxWithConstraintsProto.ADAPTER.decode(node.payload) }
                .getOrElse { BoxWithConstraintsProto() }
            val box = proto.box ?: BoxProto()
            BoxWithConstraints(
                modifier = box.modifier.toComposeModifier(),
                contentAlignment = box.content_alignment.toComposeAlignment(),
                propagateMinConstraints = box.propagate_min_constraints,
            ) {
                PodcaRenderChildren(node = node, renderChild = renderChild)
            }
        }

        "foundation.layout.Row" -> {
            val proto = runCatching { RowProto.ADAPTER.decode(node.payload) }
                .getOrElse { RowProto() }
            Row(
                modifier = proto.modifier.toComposeModifier(),
                horizontalArrangement = proto.horizontal_arrangement.toComposeArrangement(),
                verticalAlignment = proto.vertical_alignment.toComposeVerticalAlignment(),
            ) {
                PodcaRenderChildren(node = node, renderChild = renderChild)
            }
        }

        "foundation.layout.Column" -> {
            val proto = runCatching { ColumnProto.ADAPTER.decode(node.payload) }
                .getOrElse { ColumnProto() }
            Column(
                modifier = proto.modifier.toComposeModifier(),
                verticalArrangement = proto.vertical_arrangement.toComposeArrangement(),
                horizontalAlignment = proto.horizontal_alignment.toComposeHorizontalAlignment(),
            ) {
                PodcaRenderChildren(node = node, renderChild = renderChild)
            }
        }

        "foundation.layout.FlowRow" -> {
            val proto = runCatching { FlowRowProto.ADAPTER.decode(node.payload) }
                .getOrElse { FlowRowProto() }
            FlowRow(
                modifier = proto.modifier.toComposeModifier(),
                horizontalArrangement = proto.horizontal_arrangement.toComposeArrangement(),
                verticalArrangement = proto.vertical_arrangement.toComposeArrangement(),
                maxItemsInEachRow = proto.max_items_in_each_row,
                maxLines = proto.max_lines,
            ) {
                PodcaRenderChildren(node = node, renderChild = renderChild)
            }
        }

        "foundation.layout.FlowColumn" -> {
            val proto = runCatching { FlowColumnProto.ADAPTER.decode(node.payload) }
                .getOrElse { FlowColumnProto() }
            FlowColumn(
                modifier = proto.modifier.toComposeModifier(),
                verticalArrangement = proto.vertical_arrangement.toComposeArrangement(),
                horizontalArrangement = proto.horizontal_arrangement.toComposeArrangement(),
                maxItemsInEachColumn = proto.max_items_in_each_column,
                maxLines = proto.max_lines,
            ) {
                PodcaRenderChildren(node = node, renderChild = renderChild)
            }
        }

        "foundation.layout.Spacer" -> {
            val proto = runCatching { SpacerProto.ADAPTER.decode(node.payload) }
                .getOrElse { SpacerProto() }
            Spacer(modifier = proto.modifier.toComposeModifier())
        }

        "foundation.layout.ContextualFlowRow" -> {
            val proto = runCatching { ContextualFlowRowProto.ADAPTER.decode(node.payload) }
                .getOrElse { ContextualFlowRowProto() }
            FlowRow(
                modifier = proto.modifier.toComposeModifier(),
                maxItemsInEachRow = proto.max_items_in_each_row.takeIf { it > 0 } ?: Int.MAX_VALUE,
                maxLines = proto.max_lines.takeIf { it > 0 } ?: Int.MAX_VALUE,
            ) {
                val children = proto.item_count.takeIf { it > 0 }?.let { count ->
                    node.children.take(count)
                } ?: node.children
                for (child in children) {
                    renderChild(child)
                }
            }
        }

        "foundation.layout.ContextualFlowColumn" -> {
            val proto = runCatching { ContextualFlowColumnProto.ADAPTER.decode(node.payload) }
                .getOrElse { ContextualFlowColumnProto() }
            FlowColumn(
                modifier = proto.modifier.toComposeModifier(),
                maxItemsInEachColumn = proto.max_items_in_each_column.takeIf { it > 0 } ?: Int.MAX_VALUE,
                maxLines = proto.max_lines.takeIf { it > 0 } ?: Int.MAX_VALUE,
            ) {
                val children = proto.item_count.takeIf { it > 0 }?.let { count ->
                    node.children.take(count)
                } ?: node.children
                for (child in children) {
                    renderChild(child)
                }
            }
        }

        "foundation.layout.FlexBox" -> {
            val proto = runCatching { FlexBoxProto.ADAPTER.decode(node.payload) }
                .getOrElse { FlexBoxProto() }
            val config = proto.config
            if (config?.wrap == FlexWrapProto.FLEX_WRAP_NOWRAP) {
                PodcaFlexBoxLayout(
                    proto = proto,
                    node = node,
                    renderChild = renderChild,
                )
            } else {
                when (config?.direction) {
                FlexDirectionProto.FLEX_DIRECTION_COLUMN,
                FlexDirectionProto.FLEX_DIRECTION_COLUMN_REVERSE,
                -> FlowColumn(
                    modifier = proto.modifier.toComposeModifier(),
                    verticalArrangement = config.toFlexVerticalArrangement(),
                    horizontalArrangement = config.toFlexHorizontalArrangement(),
                ) {
                    renderFlexChildren(proto = proto, node = node, renderChild = renderChild)
                }
                else -> FlowRow(
                    modifier = proto.modifier.toComposeModifier(),
                    horizontalArrangement = config.toFlexHorizontalArrangement(),
                    verticalArrangement = config.toFlexVerticalArrangement(),
                ) {
                    renderFlexChildren(proto = proto, node = node, renderChild = renderChild)
                }
                }
            }
        }

        "foundation.layout.Grid" -> {
            val proto = runCatching { GridProto.ADAPTER.decode(node.payload) }
                .getOrElse { GridProto() }
            PodcaGridLayout(
                proto = proto,
                node = node,
                renderChild = renderChild,
            )
        }

        "foundation.layout.GridScope" -> {
            PodcaRenderChildren(node = node, renderChild = renderChild)
        }

        "foundation.layout.FlexBoxScope" -> {
            PodcaRenderChildren(node = node, renderChild = renderChild)
        }

        "foundation.layout.AspectRatioModifier" -> {
            val proto = runCatching { AspectRatioModifierProto.ADAPTER.decode(node.payload) }
                .getOrElse { AspectRatioModifierProto(aspect_ratio = 1f) }
            Box(
                modifier = Modifier.aspectRatio(
                    ratio = proto.aspect_ratio.takeIf { it > 0f } ?: 1f,
                    matchHeightConstraintsFirst = proto.match_height_constraints_first,
                ),
            ) {
                PodcaRenderChildren(node = node, renderChild = renderChild)
            }
        }

        "foundation.layout.DefaultMinSizeModifier" -> {
            val proto = runCatching { DefaultMinSizeModifierProto.ADAPTER.decode(node.payload) }
                .getOrElse { DefaultMinSizeModifierProto() }
            Box(
                modifier = Modifier.defaultMinSize(
                    minWidth = proto.min_width.toDpOrUnspecified(),
                    minHeight = proto.min_height.toDpOrUnspecified(),
                ),
            ) {
                PodcaRenderChildren(node = node, renderChild = renderChild)
            }
        }

        "foundation.layout.FillModifier" -> {
            val proto = runCatching { FillModifierProto.ADAPTER.decode(node.payload) }
                .getOrElse { FillModifierProto() }
            val fraction = proto.fraction.takeIf { it > 0f } ?: 1f
            val modifier = when (proto.direction) {
                SizeDirectionProto.SIZE_DIRECTION_VERTICAL -> Modifier.fillMaxHeight(fraction)
                SizeDirectionProto.SIZE_DIRECTION_BOTH -> Modifier.fillMaxSize(fraction)
                SizeDirectionProto.SIZE_DIRECTION_HORIZONTAL -> Modifier.fillMaxWidth(fraction)
            }
            Box(modifier = modifier) {
                PodcaRenderChildren(node = node, renderChild = renderChild)
            }
        }

        "foundation.layout.InsetsPaddingModifier" -> {
            val proto = runCatching { InsetsPaddingModifierProto.ADAPTER.decode(node.payload) }
                .getOrElse { InsetsPaddingModifierProto() }
            Box(modifier = proto.insets.toComposePaddingModifier()) {
                PodcaRenderChildren(node = node, renderChild = renderChild)
            }
        }

        "foundation.layout.OffsetModifier" -> {
            val proto = runCatching { OffsetModifierProto.ADAPTER.decode(node.payload) }
                .getOrElse { OffsetModifierProto() }
            Box(
                modifier = Modifier.offset(
                    x = proto.x.toDpOrZero(),
                    y = proto.y.toDpOrZero(),
                ),
            ) {
                PodcaRenderChildren(node = node, renderChild = renderChild)
            }
        }

        "foundation.layout.OffsetPxModifier" -> {
            val proto = runCatching { OffsetPxModifierProto.ADAPTER.decode(node.payload) }
                .getOrElse { OffsetPxModifierProto() }
            Box(
                modifier = Modifier.offset {
                    IntOffset(
                        x = proto.offset?.x ?: 0,
                        y = proto.offset?.y ?: 0,
                    )
                },
            ) {
                PodcaRenderChildren(node = node, renderChild = renderChild)
            }
        }

        "foundation.layout.PaddingValues",
        "foundation.layout.PaddingValuesConsumingModifier",
        -> {
            val modifier = when (node.type) {
                "foundation.layout.PaddingValues" -> {
                    val proto = runCatching { PaddingValuesProto.ADAPTER.decode(node.payload) }
                        .getOrElse { PaddingValuesProto() }
                    proto.toComposePaddingModifier()
                }
                else -> {
                    val proto = runCatching { PaddingValuesConsumingModifierProto.ADAPTER.decode(node.payload) }
                        .getOrElse { PaddingValuesConsumingModifierProto() }
                    proto.padding_values.toComposePaddingModifier()
                }
            }
            Box(modifier = modifier) {
                PodcaRenderChildren(node = node, renderChild = renderChild)
            }
        }

        "foundation.layout.SizeModifier" -> {
            val proto = runCatching { SizeModifierProto.ADAPTER.decode(node.payload) }
                .getOrElse { SizeModifierProto() }
            Box(
                modifier = Modifier.sizeIn(
                    minWidth = proto.min_width.toDpOrUnspecified(),
                    minHeight = proto.min_height.toDpOrUnspecified(),
                    maxWidth = proto.max_width.toDpOrUnspecified(),
                    maxHeight = proto.max_height.toDpOrUnspecified(),
                ),
            ) {
                PodcaRenderChildren(node = node, renderChild = renderChild)
            }
        }

        "foundation.layout.WindowInsetsWidthModifier" -> {
            val proto = runCatching { WindowInsetsWidthModifierProto.ADAPTER.decode(node.payload) }
                .getOrElse { WindowInsetsWidthModifierProto() }
            Box(
                modifier = Modifier.sizeIn(
                    minWidth = ((proto.insets?.left ?: 0) + (proto.insets?.right ?: 0)).dp,
                ),
            ) {
                PodcaRenderChildren(node = node, renderChild = renderChild)
            }
        }

        "foundation.layout.WindowInsetsHeightModifier" -> {
            val proto = runCatching { WindowInsetsHeightModifierProto.ADAPTER.decode(node.payload) }
                .getOrElse { WindowInsetsHeightModifierProto() }
            Box(
                modifier = Modifier.sizeIn(
                    minHeight = ((proto.insets?.top ?: 0) + (proto.insets?.bottom ?: 0)).dp,
                ),
            ) {
                PodcaRenderChildren(node = node, renderChild = renderChild)
            }
        }

        "foundation.layout.WrapContentModifier" -> {
            val proto = runCatching { WrapContentModifierProto.ADAPTER.decode(node.payload) }
                .getOrElse { WrapContentModifierProto() }
            Box(
                modifier = Modifier.wrapContentSize(
                    align = proto.align?.alignment.toComposeAlignment(),
                    unbounded = proto.unbounded,
                ),
            ) {
                PodcaRenderChildren(node = node, renderChild = renderChild)
            }
        }

        "foundation.layout.AlignmentLine",
        "foundation.layout.ArrangementHorizontal",
        "foundation.layout.ArrangementVertical",
        "foundation.layout.BoxWithConstraintsScope",
        "foundation.layout.ComposeFoundationLayoutFlags",
        "foundation.layout.ConsumedInsetsModifier",
        "foundation.layout.ContextualFlowColumnOverflow",
        "foundation.layout.ContextualFlowColumnOverflowScope",
        "foundation.layout.ContextualFlowColumnScope",
        "foundation.layout.ContextualFlowRowOverflow",
        "foundation.layout.ContextualFlowRowOverflowScope",
        "foundation.layout.ContextualFlowRowScope",
        "foundation.layout.CrossAxisAlignment",
        "foundation.layout.ExperimentalFlexBoxApi",
        "foundation.layout.ExperimentalGridApi",
        "foundation.layout.ExperimentalLayoutApi",
        "foundation.layout.FlexBasis",
        "foundation.layout.FlexBoxConfig",
        "foundation.layout.FlexConfig",
        "foundation.layout.FlowColumnOverflow",
        "foundation.layout.FlowColumnOverflowScope",
        "foundation.layout.FlowLayoutBuildingBlocks",
        "foundation.layout.FlowLayoutOverflow",
        "foundation.layout.FlowRowOverflow",
        "foundation.layout.FlowRowOverflowScope",
        "foundation.layout.Fr",
        "foundation.layout.GridConfiguration",
        "foundation.layout.GridItem",
        "foundation.layout.GridTrackSize",
        "foundation.layout.LayoutScopeMarker",
        "foundation.layout.OrientationIndependentConstraints",
        "foundation.layout.RecalculateWindowInsetsModifier",
        "foundation.layout.RowColumnMeasurePolicy",
        "foundation.layout.RulerAlignment",
        "foundation.layout.UnionInsetsConsumingModifier",
        "foundation.layout.WindowInsets",
        "foundation.layout.WindowInsetsSides",
        -> PodcaRenderChildren(node = node, renderChild = renderChild)

        else -> PodcaRenderChildren(node = node, renderChild = renderChild)
    }
}

private fun DpProto?.toDpOrZero(): Dp =
    this?.value_?.dp ?: 0.dp

private fun DpProto?.toDpOrUnspecified(): Dp =
    this?.value_?.dp ?: Dp.Unspecified

private fun PaddingValuesProto?.toComposePaddingModifier(): Modifier {
    val proto = this ?: return Modifier
    return Modifier.padding(
        start = proto.start.toDpOrZero(),
        top = proto.top.toDpOrZero(),
        end = proto.end.toDpOrZero(),
        bottom = proto.bottom.toDpOrZero(),
    )
}

private fun WindowInsetsProto?.toComposePaddingModifier(): Modifier {
    val proto = this ?: return Modifier
    return Modifier.padding(
        start = proto.left.dp,
        top = proto.top.dp,
        end = proto.right.dp,
        bottom = proto.bottom.dp,
    )
}

private fun FlexJustifyContentProto?.toComposeHorizontalArrangement(): Arrangement.Horizontal =
    when (this) {
        FlexJustifyContentProto.FLEX_JUSTIFY_CONTENT_END -> Arrangement.End
        FlexJustifyContentProto.FLEX_JUSTIFY_CONTENT_CENTER -> Arrangement.Center
        FlexJustifyContentProto.FLEX_JUSTIFY_CONTENT_SPACE_BETWEEN -> Arrangement.SpaceBetween
        FlexJustifyContentProto.FLEX_JUSTIFY_CONTENT_SPACE_AROUND -> Arrangement.SpaceAround
        FlexJustifyContentProto.FLEX_JUSTIFY_CONTENT_SPACE_EVENLY -> Arrangement.SpaceEvenly
        null,
        FlexJustifyContentProto.FLEX_JUSTIFY_CONTENT_START,
        -> Arrangement.Start
    }

private fun FlexJustifyContentProto?.toComposeVerticalArrangement(): Arrangement.Vertical =
    when (this) {
        FlexJustifyContentProto.FLEX_JUSTIFY_CONTENT_END -> Arrangement.Bottom
        FlexJustifyContentProto.FLEX_JUSTIFY_CONTENT_CENTER -> Arrangement.Center
        FlexJustifyContentProto.FLEX_JUSTIFY_CONTENT_SPACE_BETWEEN -> Arrangement.SpaceBetween
        FlexJustifyContentProto.FLEX_JUSTIFY_CONTENT_SPACE_AROUND -> Arrangement.SpaceAround
        FlexJustifyContentProto.FLEX_JUSTIFY_CONTENT_SPACE_EVENLY -> Arrangement.SpaceEvenly
        null,
        FlexJustifyContentProto.FLEX_JUSTIFY_CONTENT_START,
        -> Arrangement.Top
    }

private fun FlexAlignContentProto?.toComposeHorizontalArrangement(): Arrangement.Horizontal =
    when (this) {
        FlexAlignContentProto.FLEX_ALIGN_CONTENT_END -> Arrangement.End
        FlexAlignContentProto.FLEX_ALIGN_CONTENT_CENTER -> Arrangement.Center
        FlexAlignContentProto.FLEX_ALIGN_CONTENT_SPACE_BETWEEN -> Arrangement.SpaceBetween
        FlexAlignContentProto.FLEX_ALIGN_CONTENT_SPACE_AROUND -> Arrangement.SpaceAround
        FlexAlignContentProto.FLEX_ALIGN_CONTENT_SPACE_EVENLY -> Arrangement.SpaceEvenly
        null,
        FlexAlignContentProto.FLEX_ALIGN_CONTENT_START,
        FlexAlignContentProto.FLEX_ALIGN_CONTENT_STRETCH,
        -> Arrangement.Start
    }

private fun FlexAlignContentProto?.toComposeVerticalArrangement(): Arrangement.Vertical =
    when (this) {
        FlexAlignContentProto.FLEX_ALIGN_CONTENT_END -> Arrangement.Bottom
        FlexAlignContentProto.FLEX_ALIGN_CONTENT_CENTER -> Arrangement.Center
        FlexAlignContentProto.FLEX_ALIGN_CONTENT_SPACE_BETWEEN -> Arrangement.SpaceBetween
        FlexAlignContentProto.FLEX_ALIGN_CONTENT_SPACE_AROUND -> Arrangement.SpaceAround
        FlexAlignContentProto.FLEX_ALIGN_CONTENT_SPACE_EVENLY -> Arrangement.SpaceEvenly
        null,
        FlexAlignContentProto.FLEX_ALIGN_CONTENT_START,
        FlexAlignContentProto.FLEX_ALIGN_CONTENT_STRETCH,
        -> Arrangement.Top
    }

private fun com.podca.sdui.protocol.foundation.layout.FlexBoxConfigProto?.toFlexHorizontalArrangement(): Arrangement.Horizontal {
    val spacing = this?.column_gap.toDpOrZero()
    val fallback = this?.justify_content.toComposeHorizontalArrangement()
    return if (spacing > 0.dp) Arrangement.spacedBy(spacing) else fallback
}

private fun com.podca.sdui.protocol.foundation.layout.FlexBoxConfigProto?.toFlexVerticalArrangement(): Arrangement.Vertical {
    val spacing = this?.row_gap.toDpOrZero()
    val fallback = this?.justify_content.toComposeVerticalArrangement()
    return if (spacing > 0.dp) Arrangement.spacedBy(spacing) else fallback
}

@Composable
private fun renderGridChildren(
    node: PodcaDocumentNode,
    renderChild: @Composable (PodcaDocumentNode) -> Unit,
) {
    val children = node.children.sortedWith(
        compareBy(
            { child -> child.gridScopePlacementMetadata().row },
            { child -> child.gridScopePlacementMetadata().column },
        ),
    )
    for (child in children) {
        renderChild(child)
    }
}

@Composable
private fun renderFlexChildren(
    proto: FlexBoxProto,
    node: PodcaDocumentNode,
    renderChild: @Composable (PodcaDocumentNode) -> Unit,
) {
    val orderedChildren = node.children.sortedBy { child -> child.flexScopePlacementMetadata().order }
    val children = when (proto.config?.direction) {
        FlexDirectionProto.FLEX_DIRECTION_ROW_REVERSE,
        FlexDirectionProto.FLEX_DIRECTION_COLUMN_REVERSE,
        -> orderedChildren.asReversed()
        else -> orderedChildren
    }
    for (child in children) {
        renderChild(child)
    }
}

@Composable
private fun PodcaGridLayout(
    proto: GridProto,
    node: PodcaDocumentNode,
    renderChild: @Composable (PodcaDocumentNode) -> Unit,
) {
    val children = node.children.sortedWith(
        compareBy(
            { child -> child.gridScopePlacementMetadata().row },
            { child -> child.gridScopePlacementMetadata().column },
        ),
    )
    Layout(
        content = {
            children.forEach { child ->
                val metadata = child.gridScopePlacementMetadata()
                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = metadata.alignment,
                ) {
                    renderChild(child)
                }
            }
        },
    ) { measurables, constraints ->
        val rowGap = proto.config?.row_gap.toDpOrZero().let { with(this) { it.roundToPx() } }
        val columnGap = proto.config?.column_gap.toDpOrZero().let { with(this) { it.roundToPx() } }
        val columnCount = maxOf(
            proto.config?.columns?.size?.takeIf { it > 0 } ?: 0,
            children.maxOfOrNull { child ->
                child.gridScopePlacementMetadata().column + child.gridScopePlacementMetadata().columnSpan
            } ?: 1,
        ).coerceAtLeast(1)
        val rowCount = maxOf(
            proto.config?.rows?.size?.takeIf { it > 0 } ?: 0,
            children.maxOfOrNull { child ->
                child.gridScopePlacementMetadata().row + child.gridScopePlacementMetadata().rowSpan
            } ?: 1,
        ).coerceAtLeast(1)
        val columnSizes = resolveGridTrackSizes(
            tracks = proto.config?.columns.orEmpty(),
            trackCount = columnCount,
            boundedSize = constraints.maxWidth,
            hasBoundedSize = constraints.hasBoundedWidth,
            gap = columnGap,
        ) { trackSize ->
            with(this) { trackSize.fixed?.value_?.dp?.roundToPx() } ?: 0
        }
        val rowSizes = resolveGridTrackSizes(
            tracks = proto.config?.rows.orEmpty(),
            trackCount = rowCount,
            boundedSize = constraints.maxHeight,
            hasBoundedSize = constraints.hasBoundedHeight,
            gap = rowGap,
        ) { trackSize ->
            with(this) { trackSize.fixed?.value_?.dp?.roundToPx() } ?: 0
        }
        val measured = measurables.mapIndexed { index, measurable ->
            val metadata = children[index].gridScopePlacementMetadata()
            val columnSpan = metadata.columnSpan.coerceAtLeast(1)
            val rowSpan = metadata.rowSpan.coerceAtLeast(1)
            val width = columnSizes
                .drop(metadata.column)
                .take(columnSpan)
                .sum() + (columnGap * (columnSpan - 1))
            val height = rowSizes
                .drop(metadata.row)
                .take(rowSpan)
                .sum() + (rowGap * (rowSpan - 1))
            measurable.measure(
                Constraints(
                    minWidth = width,
                    maxWidth = width,
                    minHeight = height,
                    maxHeight = height,
                ),
            )
        }
        val width = columnSizes.sum() + (columnGap * (columnCount - 1))
        val height = rowSizes.sum() + (rowGap * (rowCount - 1))
        layout(
            width = constraints.constrainWidth(width),
            height = constraints.constrainHeight(height),
        ) {
            var index = 0
            children.forEach { child ->
                val metadata = child.gridScopePlacementMetadata()
                val placeable = measured[index++]
                val x = columnSizes.take(metadata.column).sum() + (metadata.column * columnGap)
                val y = rowSizes.take(metadata.row).sum() + (metadata.row * rowGap)
                placeable.placeRelative(x = x, y = y)
            }
        }
    }
}

@Composable
private fun PodcaFlexBoxLayout(
    proto: FlexBoxProto,
    node: PodcaDocumentNode,
    renderChild: @Composable (PodcaDocumentNode) -> Unit,
) {
    val children = node.children.sortedBy { child -> child.flexScopePlacementMetadata().order }
    val orderedChildren = when (proto.config?.direction) {
        FlexDirectionProto.FLEX_DIRECTION_ROW_REVERSE,
        FlexDirectionProto.FLEX_DIRECTION_COLUMN_REVERSE,
        -> children.asReversed()
        else -> children
    }
    Layout(
        content = {
            orderedChildren.forEach { child ->
                Box {
                    renderChild(child)
                }
            }
        },
    ) { measurables, constraints ->
        val horizontal = proto.config?.direction != FlexDirectionProto.FLEX_DIRECTION_COLUMN &&
            proto.config?.direction != FlexDirectionProto.FLEX_DIRECTION_COLUMN_REVERSE
        val spacing = if (horizontal) {
            proto.config?.column_gap.toDpOrZero().let { with(this) { it.roundToPx() } }
        } else {
            proto.config?.row_gap.toDpOrZero().let { with(this) { it.roundToPx() } }
        }
        val metadataList = orderedChildren.map { child -> child.flexScopePlacementMetadata() }
        val maxMainAxis = if (horizontal) constraints.maxWidth else constraints.maxHeight
        val boundedMainAxis = if (horizontal) constraints.hasBoundedWidth else constraints.hasBoundedHeight
        val spacingTotal = spacing * (measurables.size - 1).coerceAtLeast(0)
        val basisSizes = measurables.mapIndexed { index, measurable ->
            metadataList[index].basisMainAxisPx(
                scope = this,
                maxMainAxis = maxMainAxis,
                fallback = {
                    val placeable = measurable.measure(Constraints())
                    if (horizontal) placeable.width else placeable.height
                },
            )
        }
        val totalGrow = metadataList.sumOf { metadata -> metadata.grow.coerceAtLeast(0f).toDouble() }.toFloat()
        val extraMainAxis = if (boundedMainAxis && totalGrow > 0f) {
            (maxMainAxis - basisSizes.sum() - spacingTotal).coerceAtLeast(0)
        } else {
            0
        }
        val placeables = measurables.mapIndexed { index, measurable ->
            val metadata = metadataList[index]
            val grownMainSize = basisSizes[index] + if (extraMainAxis > 0 && totalGrow > 0f && metadata.grow > 0f) {
                (extraMainAxis * (metadata.grow / totalGrow)).toInt()
            } else {
                0
            }
            val alignSelf = metadata.resolveAlignSelf(proto.config?.align_items)
            val childConstraints = if (horizontal) {
                Constraints(
                    minWidth = grownMainSize,
                    maxWidth = grownMainSize,
                    minHeight = if (alignSelf == FlexAlignSelfProto.FLEX_ALIGN_SELF_STRETCH && constraints.hasBoundedHeight) {
                        constraints.maxHeight
                    } else {
                        0
                    },
                    maxHeight = if (constraints.hasBoundedHeight) constraints.maxHeight else Constraints.Infinity,
                )
            } else {
                Constraints(
                    minWidth = if (alignSelf == FlexAlignSelfProto.FLEX_ALIGN_SELF_STRETCH && constraints.hasBoundedWidth) {
                        constraints.maxWidth
                    } else {
                        0
                    },
                    maxWidth = if (constraints.hasBoundedWidth) constraints.maxWidth else Constraints.Infinity,
                    minHeight = grownMainSize,
                    maxHeight = grownMainSize,
                )
            }
            measurable.measure(childConstraints)
        }
        val width = if (horizontal) {
            placeables.sumOf { it.width } + spacingTotal
        } else {
            placeables.maxOfOrNull { it.width } ?: 0
        }
        val height = if (horizontal) {
            placeables.maxOfOrNull { it.height } ?: 0
        } else {
            placeables.sumOf { it.height } + spacingTotal
        }
        layout(
            width = constraints.constrainWidth(width),
            height = constraints.constrainHeight(height),
        ) {
            var cursorX = 0
            var cursorY = 0
            placeables.forEachIndexed { index, placeable ->
                val metadata = metadataList[index]
                if (horizontal) {
                    val y = metadata.crossAxisOffset(
                        alignItems = proto.config?.align_items,
                        availableCrossAxis = height,
                        childCrossAxis = placeable.height,
                    )
                    placeable.placeRelative(x = cursorX, y = y)
                    cursorX += placeable.width + spacing
                } else {
                    val x = metadata.crossAxisOffset(
                        alignItems = proto.config?.align_items,
                        availableCrossAxis = width,
                        childCrossAxis = placeable.width,
                    )
                    placeable.placeRelative(x = x, y = cursorY)
                    cursorY += placeable.height + spacing
                }
            }
        }
    }
}

internal data class GridScopePlacementMetadata(
    val row: Int,
    val column: Int,
    val rowSpan: Int,
    val columnSpan: Int,
    val alignment: Alignment,
)

internal data class FlexBoxScopePlacementMetadata(
    val order: Int,
    val grow: Float,
    val shrink: Float,
    val basis: com.podca.sdui.protocol.foundation.layout.FlexBasisProto?,
    val alignSelf: FlexAlignSelfProto,
)

private fun GridScopeProto?.toPlacementMetadata(): GridScopePlacementMetadata {
    val item = this?.grid_item
    return GridScopePlacementMetadata(
        row = item?.row?.takeIf { it >= 0 } ?: 0,
        column = item?.column?.takeIf { it >= 0 } ?: 0,
        rowSpan = item?.row_span?.takeIf { it > 0 } ?: 1,
        columnSpan = item?.column_span?.takeIf { it > 0 } ?: 1,
        alignment = item?.alignment.toComposeAlignment(),
    )
}

private fun FlexBoxScopeProto?.toPlacementMetadata(): FlexBoxScopePlacementMetadata {
    val flex = this?.flex
    return FlexBoxScopePlacementMetadata(
        order = flex?.order ?: 0,
        grow = flex?.grow ?: 0f,
        shrink = flex?.shrink ?: 0f,
        basis = flex?.basis,
        alignSelf = flex?.align_self ?: FlexAlignSelfProto.FLEX_ALIGN_SELF_AUTO,
    )
}

internal fun PodcaDocumentNode.gridScopePlacementMetadata(): GridScopePlacementMetadata {
    if (type != "foundation.layout.GridScope") return GridScopePlacementMetadata(0, 0, 1, 1, Alignment.TopStart)
    return runCatching { GridScopeProto.ADAPTER.decode(payload).toPlacementMetadata() }
        .getOrElse { GridScopePlacementMetadata(0, 0, 1, 1, Alignment.TopStart) }
}

internal fun PodcaDocumentNode.flexScopePlacementMetadata(): FlexBoxScopePlacementMetadata {
    if (type != "foundation.layout.FlexBoxScope") {
        return FlexBoxScopePlacementMetadata(0, 0f, 0f, null, FlexAlignSelfProto.FLEX_ALIGN_SELF_AUTO)
    }
    return runCatching { FlexBoxScopeProto.ADAPTER.decode(payload).toPlacementMetadata() }
        .getOrElse { FlexBoxScopePlacementMetadata(0, 0f, 0f, null, FlexAlignSelfProto.FLEX_ALIGN_SELF_AUTO) }
}

private fun FlexBoxScopePlacementMetadata.toFlexScopeModifier(): Modifier {
    val basisDp = basis?.dp?.value_?.dp
    return when {
        basisDp != null -> Modifier.sizeIn(minWidth = basisDp, minHeight = basisDp)
        grow > 0f -> Modifier
        else -> Modifier
    }
}

private fun resolveGridTrackSizes(
    tracks: List<GridTrackSizeProto>,
    trackCount: Int,
    boundedSize: Int,
    hasBoundedSize: Boolean,
    gap: Int,
    resolveFixedTrackPx: (GridTrackSizeProto) -> Int,
): List<Int> {
    val normalizedTracks = if (tracks.isEmpty()) {
        List(trackCount) {
            GridTrackSizeProto(
                type = GridTrackTypeProto.GRID_TRACK_TYPE_FLEX,
                flex = com.podca.sdui.protocol.foundation.layout.FrProto(value_ = 1f),
            )
        }
    } else if (tracks.size < trackCount) {
        tracks + List(trackCount - tracks.size) {
            GridTrackSizeProto(
                type = GridTrackTypeProto.GRID_TRACK_TYPE_FLEX,
                flex = com.podca.sdui.protocol.foundation.layout.FrProto(value_ = 1f),
            )
        }
    } else {
        tracks.take(trackCount)
    }
    val totalGap = gap * (trackCount - 1).coerceAtLeast(0)
    val available = if (hasBoundedSize) (boundedSize - totalGap).coerceAtLeast(0) else 0
    val fixedSizes = normalizedTracks.map { track ->
        when (track.type) {
            GridTrackTypeProto.GRID_TRACK_TYPE_FIXED -> resolveFixedTrackPx(track).coerceAtLeast(0)
            GridTrackTypeProto.GRID_TRACK_TYPE_PERCENTAGE -> if (hasBoundedSize) {
                (available * (track.percentage ?: 0f)).toInt().coerceAtLeast(0)
            } else {
                0
            }
            else -> 0
        }
    }
    val remaining = if (hasBoundedSize) {
        (available - fixedSizes.sum()).coerceAtLeast(0)
    } else {
        0
    }
    val totalFr = normalizedTracks.sumOf { track ->
        when (track.type) {
            GridTrackTypeProto.GRID_TRACK_TYPE_FLEX,
            GridTrackTypeProto.GRID_TRACK_TYPE_AUTO,
            GridTrackTypeProto.GRID_TRACK_TYPE_MIN_CONTENT,
            GridTrackTypeProto.GRID_TRACK_TYPE_MAX_CONTENT,
            -> (track.flex?.value_ ?: 1f).coerceAtLeast(0f).toDouble()
            GridTrackTypeProto.GRID_TRACK_TYPE_FIXED,
            GridTrackTypeProto.GRID_TRACK_TYPE_PERCENTAGE,
            -> 0.0
        }
    }.toFloat().takeIf { it > 0f } ?: 1f
    return normalizedTracks.mapIndexed { index, track ->
        when (track.type) {
            GridTrackTypeProto.GRID_TRACK_TYPE_FIXED,
            GridTrackTypeProto.GRID_TRACK_TYPE_PERCENTAGE,
            -> fixedSizes[index]
            GridTrackTypeProto.GRID_TRACK_TYPE_FLEX,
            GridTrackTypeProto.GRID_TRACK_TYPE_AUTO,
            GridTrackTypeProto.GRID_TRACK_TYPE_MIN_CONTENT,
            GridTrackTypeProto.GRID_TRACK_TYPE_MAX_CONTENT,
            -> if (hasBoundedSize) {
                val fr = (track.flex?.value_ ?: 1f).coerceAtLeast(0f)
                (remaining * (fr / totalFr)).toInt().coerceAtLeast(0)
            } else {
                resolveFixedTrackPx(track).takeIf { it > 0 } ?: 0
            }
        }
    }
}

private fun FlexAlignItemsProto?.toComposeHorizontalAlignment(): androidx.compose.ui.Alignment.Horizontal =
    when (this) {
        FlexAlignItemsProto.FLEX_ALIGN_ITEMS_END -> androidx.compose.ui.Alignment.End
        FlexAlignItemsProto.FLEX_ALIGN_ITEMS_CENTER -> androidx.compose.ui.Alignment.CenterHorizontally
        FlexAlignItemsProto.FLEX_ALIGN_ITEMS_STRETCH -> androidx.compose.ui.Alignment.CenterHorizontally
        FlexAlignItemsProto.FLEX_ALIGN_ITEMS_BASELINE -> androidx.compose.ui.Alignment.Start
        null,
        FlexAlignItemsProto.FLEX_ALIGN_ITEMS_START,
        -> androidx.compose.ui.Alignment.Start
    }

private fun FlexAlignItemsProto?.toComposeVerticalAlignment(): androidx.compose.ui.Alignment.Vertical =
    when (this) {
        FlexAlignItemsProto.FLEX_ALIGN_ITEMS_END -> androidx.compose.ui.Alignment.Bottom
        FlexAlignItemsProto.FLEX_ALIGN_ITEMS_CENTER -> androidx.compose.ui.Alignment.CenterVertically
        FlexAlignItemsProto.FLEX_ALIGN_ITEMS_STRETCH -> androidx.compose.ui.Alignment.CenterVertically
        FlexAlignItemsProto.FLEX_ALIGN_ITEMS_BASELINE -> androidx.compose.ui.Alignment.CenterVertically
        null,
        FlexAlignItemsProto.FLEX_ALIGN_ITEMS_START,
        -> androidx.compose.ui.Alignment.Top
    }

private fun FlexBoxScopePlacementMetadata.basisMainAxisPx(
    scope: MeasureScope,
    maxMainAxis: Int,
    fallback: () -> Int,
): Int {
    basis?.dp?.value_?.let { basisDp ->
        return with(scope) { basisDp.dp.roundToPx() }.coerceAtLeast(0)
    }
    basis?.percent?.let { percent ->
        if (maxMainAxis != Constraints.Infinity) {
            return (maxMainAxis * percent).toInt().coerceAtLeast(0)
        }
    }
    return fallback()
}

private fun FlexBoxScopePlacementMetadata.resolveAlignSelf(
    alignItems: FlexAlignItemsProto?,
): FlexAlignSelfProto =
    when (alignSelf) {
        FlexAlignSelfProto.FLEX_ALIGN_SELF_AUTO -> when (alignItems) {
            FlexAlignItemsProto.FLEX_ALIGN_ITEMS_END -> FlexAlignSelfProto.FLEX_ALIGN_SELF_END
            FlexAlignItemsProto.FLEX_ALIGN_ITEMS_CENTER -> FlexAlignSelfProto.FLEX_ALIGN_SELF_CENTER
            FlexAlignItemsProto.FLEX_ALIGN_ITEMS_STRETCH -> FlexAlignSelfProto.FLEX_ALIGN_SELF_STRETCH
            FlexAlignItemsProto.FLEX_ALIGN_ITEMS_BASELINE -> FlexAlignSelfProto.FLEX_ALIGN_SELF_BASELINE
            null,
            FlexAlignItemsProto.FLEX_ALIGN_ITEMS_START,
            -> FlexAlignSelfProto.FLEX_ALIGN_SELF_START
        }
        else -> alignSelf
    }

private fun FlexBoxScopePlacementMetadata.crossAxisOffset(
    alignItems: FlexAlignItemsProto?,
    availableCrossAxis: Int,
    childCrossAxis: Int,
): Int =
    when (resolveAlignSelf(alignItems)) {
        FlexAlignSelfProto.FLEX_ALIGN_SELF_END -> (availableCrossAxis - childCrossAxis).coerceAtLeast(0)
        FlexAlignSelfProto.FLEX_ALIGN_SELF_CENTER -> ((availableCrossAxis - childCrossAxis) / 2).coerceAtLeast(0)
        FlexAlignSelfProto.FLEX_ALIGN_SELF_AUTO,
        FlexAlignSelfProto.FLEX_ALIGN_SELF_START,
        FlexAlignSelfProto.FLEX_ALIGN_SELF_STRETCH,
        FlexAlignSelfProto.FLEX_ALIGN_SELF_BASELINE,
        -> 0
    }
