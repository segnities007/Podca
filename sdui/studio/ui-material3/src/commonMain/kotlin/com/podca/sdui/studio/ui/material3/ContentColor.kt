package com.podca.sdui.studio.ui.material3

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.material3.ContentColorProto
import com.podca.sdui.protocol.ui.graphics.ColorProto
import com.podca.sdui.studio.core.PodcaNode

@Composable
public fun PodcaContentColor(
    color: ColorProto? = null,
    isSpecified: Boolean = false,
    content: @Composable () -> Unit,
) {
    PodcaNode(
        type = "material3.ContentColor",
        message = ContentColorProto(
            color = color,
            is_specified = isSpecified,
        ),
        encode = ContentColorProto.ADAPTER::encode,
        content = content,
    )
}
