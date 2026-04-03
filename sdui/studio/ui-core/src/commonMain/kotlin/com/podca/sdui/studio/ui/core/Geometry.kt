package com.podca.sdui.studio.ui.core

import com.podca.sdui.protocol.ui.geometry.CornerRadiusProto
import com.podca.sdui.protocol.ui.geometry.OffsetProto
import com.podca.sdui.protocol.ui.geometry.RectProto
import com.podca.sdui.protocol.ui.geometry.RoundRectProto
import com.podca.sdui.protocol.ui.geometry.SizeProto

public fun PodcaOffset(
    x: Float,
    y: Float,
): OffsetProto =
    OffsetProto(
        x = x,
        y = y,
    )

public fun PodcaSize(
    width: Float,
    height: Float,
): SizeProto =
    SizeProto(
        width = width,
        height = height,
    )

public fun PodcaRect(
    left: Float,
    top: Float,
    right: Float,
    bottom: Float,
): RectProto =
    RectProto(
        left = left,
        top = top,
        right = right,
        bottom = bottom,
    )

public fun PodcaCornerRadius(
    x: Float,
    y: Float,
): CornerRadiusProto =
    CornerRadiusProto(
        x = x,
        y = y,
    )

public fun PodcaRoundRect(
    left: Float,
    top: Float,
    right: Float,
    bottom: Float,
    topLeftRadius: CornerRadiusProto? = null,
    topRightRadius: CornerRadiusProto? = null,
    bottomRightRadius: CornerRadiusProto? = null,
    bottomLeftRadius: CornerRadiusProto? = null,
): RoundRectProto =
    RoundRectProto(
        left = left,
        top = top,
        right = right,
        bottom = bottom,
        top_left_radius = topLeftRadius,
        top_right_radius = topRightRadius,
        bottom_right_radius = bottomRightRadius,
        bottom_left_radius = bottomLeftRadius,
    )
