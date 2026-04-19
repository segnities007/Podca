package com.podca.sdui.studio.ui.material3

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.core.ActionBindingProto
import com.podca.sdui.protocol.material3.MaterialTextProto
import com.podca.sdui.protocol.ui.graphics.ColorProto
import com.podca.sdui.protocol.ui.text.TextAlignProto
import com.podca.sdui.protocol.ui.text.TextStyleProto
import com.podca.sdui.protocol.ui.text.font.FontFamilyProto
import com.podca.sdui.protocol.ui.text.font.FontStyleProto
import com.podca.sdui.protocol.ui.text.font.FontWeightProto
import com.podca.sdui.studio.core.PodcaNode
import com.podca.sdui.studio.ui.core.PodcaModifier
import com.podca.sdui.studio.ui.core.PodcaTextUnit

@Composable
public fun PodcaText(
    text: String,
    modifier: PodcaModifier = PodcaModifier.Empty,
    color: ColorProto? = null,
    style: TextStyleProto? = null,
    fontSize: Float? = null,
    fontWeight: Int? = null,
    fontStyle: FontStyleProto? = null,
    fontFamily: FontFamilyProto? = null,
    letterSpacing: Float? = null,
    lineHeight: Float? = null,
    textAlign: TextAlignProto? = null,
    softWrap: Boolean = true,
    maxLines: Int = Int.MAX_VALUE,
    minLines: Int = 1,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
) {
    PodcaNode(
        type = "material3.Text",
        message = MaterialTextProto(
            modifier = modifier.toProto(),
            text = text,
            color = color,
            style = style,
            font_size = fontSize?.let { PodcaTextUnit(it) },
            font_weight = fontWeight?.let { FontWeightProto(weight = it) },
            font_style = fontStyle ?: FontStyleProto.FONT_STYLE_NORMAL,
            font_family = fontFamily,
            letter_spacing = letterSpacing?.let { PodcaTextUnit(it) },
            line_height = lineHeight?.let { PodcaTextUnit(it) },
            text_align = textAlign ?: TextAlignProto.TEXT_ALIGN_UNSPECIFIED,
            soft_wrap = softWrap,
            max_lines = maxLines,
            min_lines = minLines,
        ),
        key = key,
        actions = actions,
        encode = MaterialTextProto.ADAPTER::encode,
    )
}
