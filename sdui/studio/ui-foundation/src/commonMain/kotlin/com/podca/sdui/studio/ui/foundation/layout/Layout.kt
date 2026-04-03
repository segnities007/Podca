package com.podca.sdui.studio.ui.foundation.layout

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.core.ActionBindingProto
import com.podca.sdui.protocol.foundation.layout.ArrangementHorizontalProto
import com.podca.sdui.protocol.foundation.layout.ArrangementVerticalProto
import com.podca.sdui.protocol.foundation.layout.BoxProto
import com.podca.sdui.protocol.foundation.layout.ColumnProto
import com.podca.sdui.protocol.foundation.layout.RowProto
import com.podca.sdui.protocol.foundation.layout.SpacerProto
import com.podca.sdui.protocol.ui.alignment.AlignmentProto
import com.podca.sdui.protocol.ui.alignment.HorizontalAlignmentProto
import com.podca.sdui.protocol.ui.alignment.VerticalAlignmentProto
import com.podca.sdui.studio.core.PodcaNode
import com.podca.sdui.studio.ui.core.PodcaModifier

@Composable
public fun PodcaBox(
    modifier: PodcaModifier = PodcaModifier.Empty,
    contentAlignment: AlignmentProto? = null,
    propagateMinConstraints: Boolean = false,
    content: @Composable () -> Unit,
) {
    PodcaNode(
        type = "foundation.layout.Box",
        message = BoxProto(
            modifier = modifier.toProto(),
            content_alignment = contentAlignment,
            propagate_min_constraints = propagateMinConstraints,
        ),
        encode = BoxProto.ADAPTER::encode,
        content = content,
    )
}

@Composable
public fun PodcaRow(
    modifier: PodcaModifier = PodcaModifier.Empty,
    horizontalArrangement: ArrangementHorizontalProto? = null,
    verticalAlignment: VerticalAlignmentProto? = null,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
    content: @Composable () -> Unit,
) {
    PodcaNode(
        type = "foundation.layout.Row",
        message = RowProto(
            modifier = modifier.toProto(),
            horizontal_arrangement = horizontalArrangement,
            vertical_alignment = verticalAlignment,
        ),
        key = key,
        actions = actions,
        encode = RowProto.ADAPTER::encode,
        content = content,
    )
}

@Composable
public fun PodcaColumn(
    modifier: PodcaModifier = PodcaModifier.Empty,
    verticalArrangement: ArrangementVerticalProto? = null,
    horizontalAlignment: HorizontalAlignmentProto? = null,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
    content: @Composable () -> Unit,
) {
    PodcaNode(
        type = "foundation.layout.Column",
        message = ColumnProto(
            modifier = modifier.toProto(),
            vertical_arrangement = verticalArrangement,
            horizontal_alignment = horizontalAlignment,
        ),
        key = key,
        actions = actions,
        encode = ColumnProto.ADAPTER::encode,
        content = content,
    )
}

@Composable
public fun PodcaSpacer(
    modifier: PodcaModifier = PodcaModifier.Empty,
) {
    PodcaNode(
        type = "foundation.layout.Spacer",
        message = SpacerProto(
            modifier = modifier.toProto(),
        ),
        encode = SpacerProto.ADAPTER::encode,
    )
}
