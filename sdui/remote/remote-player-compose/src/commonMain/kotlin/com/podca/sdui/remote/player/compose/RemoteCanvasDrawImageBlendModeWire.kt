package com.podca.sdui.remote.player.compose

import androidx.compose.ui.graphics.BlendMode

/** Same integer ids as `sdui.ui.graphics.BlendModeProto` / [androidx.compose.ui.graphics.BlendMode] values (DRAW_IMAGE draw pass and tint ColorFilter). */
internal fun remoteCanvasDrawImageBlendModeFromWire(mode: Int): BlendMode =
    when (mode.coerceIn(0, 28)) {
        0 -> BlendMode.Clear
        1 -> BlendMode.Src
        2 -> BlendMode.Dst
        3 -> BlendMode.SrcOver
        4 -> BlendMode.DstOver
        5 -> BlendMode.SrcIn
        6 -> BlendMode.DstIn
        7 -> BlendMode.SrcOut
        8 -> BlendMode.DstOut
        9 -> BlendMode.SrcAtop
        10 -> BlendMode.DstAtop
        11 -> BlendMode.Xor
        12 -> BlendMode.Plus
        13 -> BlendMode.Modulate
        14 -> BlendMode.Screen
        15 -> BlendMode.Overlay
        16 -> BlendMode.Darken
        17 -> BlendMode.Lighten
        18 -> BlendMode.ColorDodge
        19 -> BlendMode.ColorBurn
        20 -> BlendMode.Hardlight
        21 -> BlendMode.Softlight
        22 -> BlendMode.Difference
        23 -> BlendMode.Exclusion
        24 -> BlendMode.Multiply
        25 -> BlendMode.Hue
        26 -> BlendMode.Saturation
        27 -> BlendMode.Color
        28 -> BlendMode.Luminosity
        else -> BlendMode.SrcOver
    }
