package com.podca.sdui.studio.ui.foundation.layout

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.core.ActionBindingProto
import com.podca.sdui.protocol.foundation.layout.CrossAxisAlignmentProto
import com.podca.sdui.protocol.foundation.layout.OrientationIndependentConstraintsProto
import com.podca.sdui.studio.core.PodcaNode

@Composable
public fun PodcaCrossAxisAlignment(
    kind: UInt = 0u,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
) {
    PodcaNode(
        type = "foundation.layout.CrossAxisAlignment",
        message = CrossAxisAlignmentProto(kind = kind.toInt()),
        key = key,
        actions = actions,
        encode = CrossAxisAlignmentProto.ADAPTER::encode,
    )
}

@Composable
public fun PodcaOrientationIndependentConstraints(
    mainAxisMin: Int = 0,
    mainAxisMax: Int = 0,
    crossAxisMin: Int = 0,
    crossAxisMax: Int = 0,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
) {
    PodcaNode(
        type = "foundation.layout.OrientationIndependentConstraints",
        message = OrientationIndependentConstraintsProto(
            main_axis_min = mainAxisMin,
            main_axis_max = mainAxisMax,
            cross_axis_min = crossAxisMin,
            cross_axis_max = crossAxisMax,
        ),
        key = key,
        actions = actions,
        encode = OrientationIndependentConstraintsProto.ADAPTER::encode,
    )
}
