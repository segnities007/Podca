package com.podca.sdui.studio.ui.core

import com.podca.sdui.protocol.ui.text.HyphensProto
import com.podca.sdui.protocol.ui.text.LineBreakProto
import com.podca.sdui.protocol.ui.text.LineHeightStyleModeProto
import com.podca.sdui.protocol.ui.text.ParagraphStyleProto
import com.podca.sdui.protocol.ui.text.TextAlignProto
import com.podca.sdui.protocol.ui.text.TextDirectionProto
import com.podca.sdui.protocol.ui.text.TextMotionProto

public fun PodcaTextAlign(
    textAlign: TextAlignProto = TextAlignProto.TEXT_ALIGN_UNSPECIFIED,
): TextAlignProto = textAlign

public fun PodcaTextDirection(
    textDirection: TextDirectionProto = TextDirectionProto.TEXT_DIRECTION_UNSPECIFIED,
): TextDirectionProto = textDirection

public fun PodcaTextMotion(
    textMotion: TextMotionProto = TextMotionProto.TEXT_MOTION_STATIC,
): TextMotionProto = textMotion

public fun PodcaHyphens(
    hyphens: HyphensProto = HyphensProto.HYPHENS_UNSPECIFIED,
): HyphensProto = hyphens

public fun PodcaLineBreak(
    lineBreak: LineBreakProto = LineBreakProto.LINE_BREAK_UNSPECIFIED,
): LineBreakProto = lineBreak

public fun PodcaLineHeightStyleMode(
    mode: LineHeightStyleModeProto = LineHeightStyleModeProto.LINE_HEIGHT_STYLE_MODE_FIXED,
): LineHeightStyleModeProto = mode

public fun PodcaParagraphStyleDefault(): ParagraphStyleProto =
    ParagraphStyleProto()
