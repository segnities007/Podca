package com.podca.sdui.studio.ui.material3

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.material3.TabPositionProto
import com.podca.sdui.protocol.ui.unit.DpProto
import com.podca.sdui.studio.core.PodcaNode

@Composable
public fun PodcaTabPosition(
    left: DpProto? = null,
    width: DpProto? = null,
    contentWidth: DpProto? = null,
) {
    PodcaNode(
        type = "material3.TabPosition",
        message = TabPositionProto(
            left = left,
            width = width,
            content_width = contentWidth,
        ),
        encode = TabPositionProto.ADAPTER::encode,
    )
}
