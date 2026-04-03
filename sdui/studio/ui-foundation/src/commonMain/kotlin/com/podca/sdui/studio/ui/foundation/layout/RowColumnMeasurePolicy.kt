package com.podca.sdui.studio.ui.foundation.layout

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.core.ActionBindingProto
import com.podca.sdui.protocol.foundation.layout.ArrangementHorizontalProto
import com.podca.sdui.protocol.foundation.layout.ArrangementVerticalProto
import com.podca.sdui.protocol.foundation.layout.CrossAxisAlignmentProto
import com.podca.sdui.protocol.foundation.layout.LayoutOrientationProto
import com.podca.sdui.protocol.foundation.layout.RowColumnMeasurePolicyProto
import com.podca.sdui.studio.core.PodcaNode

@Composable
public fun PodcaRowColumnMeasurePolicy(
    orientation: LayoutOrientationProto = LayoutOrientationProto.LAYOUT_ORIENTATION_HORIZONTAL,
    horizontalArrangement: ArrangementHorizontalProto? = null,
    verticalArrangement: ArrangementVerticalProto? = null,
    crossAxisAlignment: CrossAxisAlignmentProto? = null,
    isHorizontal: Boolean = false,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
) {
    PodcaNode(
        type = "foundation.layout.RowColumnMeasurePolicy",
        message = RowColumnMeasurePolicyProto(
            orientation = orientation,
            horizontal_arrangement = horizontalArrangement,
            vertical_arrangement = verticalArrangement,
            cross_axis_alignment = crossAxisAlignment,
            is_horizontal = isHorizontal,
        ),
        key = key,
        actions = actions,
        encode = RowColumnMeasurePolicyProto.ADAPTER::encode,
    )
}
