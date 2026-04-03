package com.podca.sdui.player.ui.core

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.sizeIn
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.ui.Alignment
import androidx.compose.ui.BiasAlignment
import androidx.compose.ui.BiasAbsoluteAlignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.compose.ui.zIndex
import com.podca.sdui.protocol.foundation.layout.ArrangementHorizontalProto
import com.podca.sdui.protocol.foundation.layout.ArrangementKindProto
import com.podca.sdui.protocol.foundation.layout.ArrangementVerticalProto
import com.podca.sdui.protocol.foundation.layout.SizeDirectionProto
import com.podca.sdui.protocol.ui.alignment.AlignmentProto
import com.podca.sdui.protocol.ui.alignment.AlignmentPresetProto
import com.podca.sdui.protocol.ui.alignment.HorizontalAlignmentPresetProto
import com.podca.sdui.protocol.ui.alignment.HorizontalAlignmentProto
import com.podca.sdui.protocol.ui.alignment.VerticalAlignmentPresetProto
import com.podca.sdui.protocol.ui.alignment.VerticalAlignmentProto
import com.podca.sdui.protocol.ui.modifier.ModifierElementProto
import com.podca.sdui.protocol.ui.modifier.ModifierPropertyProto
import com.podca.sdui.protocol.ui.modifier.ModifierProto
import com.podca.sdui.protocol.ui.unit.DpProto
import com.podca.sdui.protocol.ui.unit.IntOffsetProto
import com.podca.sdui.protocol.ui.unit.TextUnitProto
import com.podca.sdui.protocol.ui.unit.TextUnitTypeProto

public fun ModifierProto?.toComposeModifier(): Modifier {
    if (this == null || elements.isEmpty()) return Modifier

    var result: Modifier = Modifier
    for (element in elements) {
        result = result.then(element.toComposeModifier())
    }
    return result
}

public fun AlignmentProto?.toComposeAlignment(): Alignment {
    val proto = this ?: return Alignment.TopStart
    proto.preset?.let { return it.toComposeAlignment() }
    proto.bias?.let {
        return BiasAlignment(
            horizontalBias = it.horizontal_bias,
            verticalBias = it.vertical_bias,
        )
    }
    proto.absolute_bias?.let {
        return BiasAbsoluteAlignment(
            horizontalBias = it.horizontal_bias,
            verticalBias = it.vertical_bias,
        )
    }
    return Alignment.TopStart
}

public fun HorizontalAlignmentProto?.toComposeHorizontalAlignment(): Alignment.Horizontal {
    val proto = this ?: return Alignment.Start
    proto.preset?.let { return it.toComposeHorizontalAlignment() }
    return Alignment.Start
}

public fun VerticalAlignmentProto?.toComposeVerticalAlignment(): Alignment.Vertical {
    val proto = this ?: return Alignment.Top
    proto.preset?.let { return it.toComposeVerticalAlignment() }
    return Alignment.Top
}

public fun ArrangementHorizontalProto?.toComposeArrangement(): Arrangement.Horizontal {
    val proto = this ?: return Arrangement.Start
    val spacing = proto.spacing.toDp()
    return when (proto.kind) {
        ArrangementKindProto.ARRANGEMENT_KIND_END -> Arrangement.End
        ArrangementKindProto.ARRANGEMENT_KIND_CENTER -> Arrangement.Center
        ArrangementKindProto.ARRANGEMENT_KIND_SPACE_BETWEEN -> Arrangement.SpaceBetween
        ArrangementKindProto.ARRANGEMENT_KIND_SPACE_AROUND -> Arrangement.SpaceAround
        ArrangementKindProto.ARRANGEMENT_KIND_SPACE_EVENLY -> Arrangement.SpaceEvenly
        ArrangementKindProto.ARRANGEMENT_KIND_SPACED_BY -> Arrangement.spacedBy(
            space = spacing,
            alignment = proto.alignment.toComposeHorizontalAlignment(),
        )
        ArrangementKindProto.ARRANGEMENT_KIND_ALIGNED -> Arrangement.spacedBy(
            space = spacing,
            alignment = proto.alignment.toComposeHorizontalAlignment(),
        )
        else -> Arrangement.Start
    }
}

public fun ArrangementVerticalProto?.toComposeArrangement(): Arrangement.Vertical {
    val proto = this ?: return Arrangement.Top
    val spacing = proto.spacing.toDp()
    return when (proto.kind) {
        ArrangementKindProto.ARRANGEMENT_KIND_BOTTOM -> Arrangement.Bottom
        ArrangementKindProto.ARRANGEMENT_KIND_CENTER -> Arrangement.Center
        ArrangementKindProto.ARRANGEMENT_KIND_SPACE_BETWEEN -> Arrangement.SpaceBetween
        ArrangementKindProto.ARRANGEMENT_KIND_SPACE_AROUND -> Arrangement.SpaceAround
        ArrangementKindProto.ARRANGEMENT_KIND_SPACE_EVENLY -> Arrangement.SpaceEvenly
        ArrangementKindProto.ARRANGEMENT_KIND_SPACED_BY -> Arrangement.spacedBy(
            space = spacing,
            alignment = proto.alignment.toComposeVerticalAlignment(),
        )
        ArrangementKindProto.ARRANGEMENT_KIND_ALIGNED -> Arrangement.spacedBy(
            space = spacing,
            alignment = proto.alignment.toComposeVerticalAlignment(),
        )
        else -> Arrangement.Top
    }
}

private fun ModifierElementProto.toComposeModifier(): Modifier {
    val normalizedName = name.lowercase()
    return when (normalizedName) {
        "aspectratio", "aspectratiomodifier" -> {
            val aspectRatio = floatProperty("aspect_ratio") ?: floatProperty("aspectRatio") ?: 1f
            val matchHeightConstraintsFirst = booleanProperty("match_height_constraints_first")
                ?: booleanProperty("matchHeightConstraintsFirst")
                ?: false
            androidx.compose.ui.Modifier.aspectRatio(
                ratio = aspectRatio,
                matchHeightConstraintsFirst = matchHeightConstraintsFirst,
            )
        }

        "offset", "offsetmodifier" -> {
            val x = dpProperty("x")
            val y = dpProperty("y")
            if (x != null || y != null) {
                androidx.compose.ui.Modifier.offset(
                    x = x ?: 0.dp,
                    y = y ?: 0.dp,
                )
            } else {
                androidx.compose.ui.Modifier
            }
        }

        "offsetpx", "offsetpxmodifier" -> {
            val offset = intOffsetProperty("offset")
            if (offset != null) {
                androidx.compose.ui.Modifier.offset { IntOffset(offset.x, offset.y) }
            } else {
                androidx.compose.ui.Modifier
            }
        }

        "padding", "paddingvalues", "insetspaddingmodifier", "paddingvaluesconsumingmodifier" -> {
            val all = dpProperty("all")
            if (all != null) {
                androidx.compose.ui.Modifier.padding(all)
            } else {
                val start = dpProperty("start")
                val top = dpProperty("top")
                val end = dpProperty("end")
                val bottom = dpProperty("bottom")
                if (start != null || top != null || end != null || bottom != null) {
                    androidx.compose.ui.Modifier.padding(
                        start = start ?: 0.dp,
                        top = top ?: 0.dp,
                        end = end ?: 0.dp,
                        bottom = bottom ?: 0.dp,
                    )
                } else {
                    androidx.compose.ui.Modifier
                }
            }
        }

        "size", "sizemodifier" -> {
            val minWidth = dpProperty("min_width") ?: dpProperty("minWidth")
            val minHeight = dpProperty("min_height") ?: dpProperty("minHeight")
            val maxWidth = dpProperty("max_width") ?: dpProperty("maxWidth")
            val maxHeight = dpProperty("max_height") ?: dpProperty("maxHeight")
            if (minWidth != null || minHeight != null || maxWidth != null || maxHeight != null) {
                androidx.compose.ui.Modifier.sizeIn(
                    minWidth = minWidth ?: Dp.Unspecified,
                    minHeight = minHeight ?: Dp.Unspecified,
                    maxWidth = maxWidth ?: Dp.Unspecified,
                    maxHeight = maxHeight ?: Dp.Unspecified,
                )
            } else {
                androidx.compose.ui.Modifier
            }
        }

        "fill", "fillmodifier" -> {
            val direction = intProperty("direction")
                ?: enumNameProperty("direction")?.let {
                    when (it) {
                        "SIZE_DIRECTION_VERTICAL" -> 1
                        "SIZE_DIRECTION_BOTH" -> 2
                        else -> 0
                    }
                }
            val fraction = floatProperty("fraction") ?: 0f
            when (direction) {
                1 -> androidx.compose.ui.Modifier.fillMaxHeight(fraction)
                2 -> androidx.compose.ui.Modifier.fillMaxSize(fraction)
                else -> androidx.compose.ui.Modifier.fillMaxWidth(fraction)
            }
        }

        "wrapcontent", "wrapcontentmodifier" -> {
            val align = alignmentProperty("align")
            val unbounded = booleanProperty("unbounded") ?: false
            androidx.compose.ui.Modifier.wrapContentSize(
                align = align ?: Alignment.TopStart,
                unbounded = unbounded,
            )
        }

        "defaultminsize", "defaultminsizemodifier" -> {
            val minWidth = dpProperty("min_width") ?: dpProperty("minWidth")
            val minHeight = dpProperty("min_height") ?: dpProperty("minHeight")
            if (minWidth != null || minHeight != null) {
                androidx.compose.ui.Modifier.defaultMinSize(
                    minWidth = minWidth ?: Dp.Unspecified,
                    minHeight = minHeight ?: Dp.Unspecified,
                )
            } else {
                androidx.compose.ui.Modifier
            }
        }

        "zindex", "zindexmodifier" -> {
            val zIndex = floatProperty("z_index") ?: floatProperty("zIndex") ?: 0f
            androidx.compose.ui.Modifier.zIndex(zIndex)
        }

        else -> androidx.compose.ui.Modifier
    }
}

private fun DpProto?.toDp(): Dp =
    this?.value_?.dp ?: Dp.Unspecified

private fun ModifierElementProto.booleanProperty(key: String): Boolean? =
    property(key)?.bool_value

private fun ModifierElementProto.floatProperty(key: String): Float? =
    property(key)?.float_value

private fun ModifierElementProto.intProperty(key: String): Int? =
    property(key)?.int32_value ?: property(key)?.int64_value?.toInt()

private fun ModifierElementProto.dpProperty(key: String): Dp? =
    property(key)?.dp_value.toDp()

private fun ModifierElementProto.intOffsetProperty(key: String): IntOffset? =
    property(key)?.int_offset_value?.let { IntOffset(it.x, it.y) }

private fun ModifierElementProto.alignmentProperty(key: String): Alignment? {
    val property = property(key) ?: return null
    property.alignment_value?.let { return it.toComposeAlignment() }
    return null
}

private fun ModifierElementProto.enumNameProperty(key: String): String? =
    property(key)?.string_value ?: property(key)?.int32_value?.toString()

private fun ModifierElementProto.property(key: String): ModifierPropertyProto? =
    properties.firstOrNull { it.key.equals(key, ignoreCase = true) }
        ?: if (properties.size == 1) properties.firstOrNull() else null

private fun AlignmentPresetProto.toComposeAlignment(): Alignment =
    when (this) {
        AlignmentPresetProto.TOP_START -> Alignment.TopStart
        AlignmentPresetProto.TOP_CENTER -> Alignment.TopCenter
        AlignmentPresetProto.TOP_END -> Alignment.TopEnd
        AlignmentPresetProto.CENTER_START -> Alignment.CenterStart
        AlignmentPresetProto.CENTER -> Alignment.Center
        AlignmentPresetProto.CENTER_END -> Alignment.CenterEnd
        AlignmentPresetProto.BOTTOM_START -> Alignment.BottomStart
        AlignmentPresetProto.BOTTOM_CENTER -> Alignment.BottomCenter
        AlignmentPresetProto.BOTTOM_END -> Alignment.BottomEnd
        AlignmentPresetProto.TOP_LEFT -> Alignment.TopStart
        AlignmentPresetProto.TOP_RIGHT -> Alignment.TopEnd
        AlignmentPresetProto.CENTER_LEFT -> Alignment.CenterStart
        AlignmentPresetProto.CENTER_RIGHT -> Alignment.CenterEnd
        AlignmentPresetProto.BOTTOM_LEFT -> Alignment.BottomStart
        AlignmentPresetProto.BOTTOM_RIGHT -> Alignment.BottomEnd
        else -> Alignment.TopStart
    }

private fun HorizontalAlignmentPresetProto.toComposeHorizontalAlignment(): Alignment.Horizontal =
    when (this) {
        HorizontalAlignmentPresetProto.START -> Alignment.Start
        HorizontalAlignmentPresetProto.CENTER_HORIZONTALLY -> Alignment.CenterHorizontally
        HorizontalAlignmentPresetProto.END -> Alignment.End
        HorizontalAlignmentPresetProto.LEFT -> Alignment.Start
        HorizontalAlignmentPresetProto.RIGHT -> Alignment.End
        else -> Alignment.Start
    }

private fun VerticalAlignmentPresetProto.toComposeVerticalAlignment(): Alignment.Vertical =
    when (this) {
        VerticalAlignmentPresetProto.TOP -> Alignment.Top
        VerticalAlignmentPresetProto.CENTER_VERTICALLY -> Alignment.CenterVertically
        VerticalAlignmentPresetProto.BOTTOM -> Alignment.Bottom
        else -> Alignment.Top
    }

private fun TextUnitProto.toComposeTextUnit() = when (type) {
    TextUnitTypeProto.TEXT_UNIT_TYPE_EM -> value_.em
    TextUnitTypeProto.TEXT_UNIT_TYPE_SP,
    TextUnitTypeProto.TEXT_UNIT_TYPE_UNSPECIFIED,
    -> value_.sp
}
