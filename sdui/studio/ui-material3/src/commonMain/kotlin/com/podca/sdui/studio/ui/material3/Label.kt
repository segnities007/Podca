package com.podca.sdui.studio.ui.material3

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.material3.LabelProto
import com.podca.sdui.protocol.ui.graphics.ColorProto
import com.podca.sdui.protocol.ui.text.TextStyleProto
import com.podca.sdui.protocol.ui.unit.TextUnitProto
import com.podca.sdui.studio.core.PodcaNode

@Composable
public fun PodcaLabel(
    text: String,
    color: ColorProto? = null,
    style: TextStyleProto? = null,
    minimizedLabelTextSize: TextUnitProto? = null,
    expanded: Boolean = false,
    minimized: Boolean = false,
) {
    PodcaNode(
        type = "material3.Label",
        message = LabelProto(
            text = text,
            color = color,
            style = style,
            minimized_label_text_size = minimizedLabelTextSize,
            expanded = expanded,
            minimized = minimized,
        ),
        encode = LabelProto.ADAPTER::encode,
    )
}
