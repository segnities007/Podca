package com.podca.sdui.studio.ui.core

import com.podca.sdui.protocol.ui.unit.ConstraintsProto
import com.podca.sdui.protocol.ui.unit.DpOffsetProto
import com.podca.sdui.protocol.ui.unit.DpProto
import com.podca.sdui.protocol.ui.unit.DpRectProto
import com.podca.sdui.protocol.ui.unit.DpSizeProto
import com.podca.sdui.protocol.ui.unit.IntOffsetProto
import com.podca.sdui.protocol.ui.unit.IntSizeProto
import com.podca.sdui.protocol.ui.unit.TextUnitProto
import com.podca.sdui.protocol.ui.unit.TextUnitTypeProto

public fun PodcaDp(
    value: Float,
): DpProto =
    DpProto(
        value_ = value,
    )

public fun PodcaDpOffset(
    x: Float,
    y: Float,
): DpOffsetProto =
    DpOffsetProto(
        x = x,
        y = y,
    )

public fun PodcaDpSize(
    width: Float,
    height: Float,
): DpSizeProto =
    DpSizeProto(
        width = width,
        height = height,
    )

public fun PodcaDpRect(
    left: Float,
    top: Float,
    right: Float,
    bottom: Float,
): DpRectProto =
    DpRectProto(
        left = left,
        top = top,
        right = right,
        bottom = bottom,
    )

public fun PodcaIntOffset(
    x: Int,
    y: Int,
): IntOffsetProto =
    IntOffsetProto(
        x = x,
        y = y,
    )

public fun PodcaIntSize(
    width: Int,
    height: Int,
): IntSizeProto =
    IntSizeProto(
        width = width,
        height = height,
    )

public fun PodcaConstraints(
    minWidth: Int = 0,
    maxWidth: Int = Int.MAX_VALUE,
    minHeight: Int = 0,
    maxHeight: Int = Int.MAX_VALUE,
): ConstraintsProto =
    ConstraintsProto(
        min_width = minWidth,
        max_width = maxWidth,
        min_height = minHeight,
        max_height = maxHeight,
    )

public fun PodcaTextUnit(
    value: Float,
    type: TextUnitTypeProto = TextUnitTypeProto.TEXT_UNIT_TYPE_SP,
): TextUnitProto =
    TextUnitProto(
        value_ = value,
        type = type,
    )
