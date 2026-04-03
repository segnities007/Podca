package com.podca.sdui.studio.ui.foundation.layout

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.core.ActionBindingProto
import com.podca.sdui.protocol.foundation.layout.RulerAlignmentProto
import com.podca.sdui.protocol.ui.unit.DpProto
import com.podca.sdui.studio.core.PodcaNode

@Composable
public fun PodcaRulerAlignment(
    offset: DpProto? = null,
    includePadding: Boolean = false,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
) {
    PodcaNode(
        type = "foundation.layout.RulerAlignment",
        message = RulerAlignmentProto(
            offset = offset,
            include_padding = includePadding,
        ),
        key = key,
        actions = actions,
        encode = RulerAlignmentProto.ADAPTER::encode,
    )
}
