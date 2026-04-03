package com.podca.sdui.studio.ui.core

import com.podca.sdui.protocol.ui.graphics.ColorComponentsProto
import com.podca.sdui.protocol.ui.graphics.ColorProto

public fun PodcaColor(
    red: Float,
    green: Float,
    blue: Float,
    alpha: Float = 1f,
    value: ULong = 0uL,
): ColorProto =
    if (value != 0uL) {
        ColorProto(packed = value.toLong())
    } else {
        ColorProto(
            components = ColorComponentsProto(
                red = red,
                green = green,
                blue = blue,
                alpha = alpha,
            ),
        )
    }

public fun colorProto(
    red: Float,
    green: Float,
    blue: Float,
    alpha: Float = 1f,
    value: ULong = 0uL,
): ColorProto = PodcaColor(
    red = red,
    green = green,
    blue = blue,
    alpha = alpha,
    value = value,
)
