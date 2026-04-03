package com.podca.sdui.studio.ui.material3

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.core.ActionBindingProto
import com.podca.sdui.protocol.material3.MaterialTextProto
import com.podca.sdui.protocol.ui.graphics.ColorProto
import com.podca.sdui.protocol.ui.text.TextStyleProto
import com.podca.sdui.studio.core.PodcaNode
import com.podca.sdui.studio.ui.core.PodcaModifier

@Composable
public fun PodcaText(
    text: String,
    modifier: PodcaModifier = PodcaModifier.Empty,
    color: ColorProto? = null,
    style: TextStyleProto? = null,
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
            soft_wrap = softWrap,
            max_lines = maxLines,
            min_lines = minLines,
        ),
        key = key,
        actions = actions,
        encode = MaterialTextProto.ADAPTER::encode,
    )
}
