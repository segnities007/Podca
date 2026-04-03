package com.podca.sdui.studio.ui.material3

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.material3.InteractiveComponentSizeProto
import com.podca.sdui.protocol.ui.unit.DpProto
import com.podca.sdui.studio.core.PodcaNode

@Composable
public fun PodcaInteractiveComponentSize(
    minimumWidth: DpProto? = null,
    minimumHeight: DpProto? = null,
    enforced: Boolean = false,
    content: @Composable () -> Unit,
) {
    PodcaNode(
        type = "material3.InteractiveComponentSize",
        message = InteractiveComponentSizeProto(
            minimum_width = minimumWidth,
            minimum_height = minimumHeight,
            enforced = enforced,
        ),
        encode = InteractiveComponentSizeProto.ADAPTER::encode,
        content = content,
    )
}
