package com.podca.sdui.remote.player.compose

import androidx.compose.ui.text.style.LineHeightStyle

/**
 * Wire [RemoteCanvasOpProto.draw_text_line_height_style]: optional [LineHeightStyle] when
 * [TextStyle.lineHeight] is set (ignored when line height is unspecified).
 *
 * **0** = omit (legacy player: no explicit [LineHeightStyle]).
 * **1** = [LineHeightStyle.Default] (Proportional + Trim.Both + Fixed).
 * **2** = Top + Trim.Both + Fixed (extra leading below each line — closer to many Canvas `drawText` stacks).
 * **3** = Bottom + Trim.Both + Fixed.
 * **4** = Center + Trim.Both + Fixed.
 * **5** = Proportional + Trim.None + Fixed.
 */
internal fun remoteCanvasLineHeightStyleFromWire(style: Int): LineHeightStyle? =
    when (style.coerceIn(0, 5)) {
        0 -> null
        1 -> LineHeightStyle.Default
        2 ->
            LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Top,
                trim = LineHeightStyle.Trim.Both,
                mode = LineHeightStyle.Mode.Fixed,
            )
        3 ->
            LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Bottom,
                trim = LineHeightStyle.Trim.Both,
                mode = LineHeightStyle.Mode.Fixed,
            )
        4 ->
            LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Center,
                trim = LineHeightStyle.Trim.Both,
                mode = LineHeightStyle.Mode.Fixed,
            )
        5 ->
            LineHeightStyle(
                alignment = LineHeightStyle.Alignment.Proportional,
                trim = LineHeightStyle.Trim.None,
                mode = LineHeightStyle.Mode.Fixed,
            )
        else -> null
    }
