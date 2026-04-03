package com.podca.sdui.studio.ui.foundation.layout

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.core.ActionBindingProto
import com.podca.sdui.protocol.foundation.layout.ArrangementHorizontalProto
import com.podca.sdui.protocol.foundation.layout.ArrangementKindProto
import com.podca.sdui.protocol.foundation.layout.ArrangementVerticalProto
import com.podca.sdui.protocol.ui.alignment.HorizontalAlignmentProto
import com.podca.sdui.protocol.ui.alignment.VerticalAlignmentProto
import com.podca.sdui.protocol.ui.unit.DpProto
import com.podca.sdui.studio.core.PodcaNode

@Composable
public fun PodcaArrangementHorizontal(
    kind: ArrangementKindProto = ArrangementKindProto.ARRANGEMENT_KIND_UNSPECIFIED,
    spacing: DpProto? = null,
    alignment: HorizontalAlignmentProto? = null,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
) {
    PodcaNode(
        type = "foundation.layout.ArrangementHorizontal",
        message = ArrangementHorizontalProto(
            kind = kind,
            spacing = spacing,
            alignment = alignment,
        ),
        key = key,
        actions = actions,
        encode = ArrangementHorizontalProto.ADAPTER::encode,
    )
}

@Composable
public fun PodcaArrangementVertical(
    kind: ArrangementKindProto = ArrangementKindProto.ARRANGEMENT_KIND_UNSPECIFIED,
    spacing: DpProto? = null,
    alignment: VerticalAlignmentProto? = null,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
) {
    PodcaNode(
        type = "foundation.layout.ArrangementVertical",
        message = ArrangementVerticalProto(
            kind = kind,
            spacing = spacing,
            alignment = alignment,
        ),
        key = key,
        actions = actions,
        encode = ArrangementVerticalProto.ADAPTER::encode,
    )
}
