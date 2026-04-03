package com.podca.sdui.studio.ui.foundation.layout

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.core.ActionBindingProto
import com.podca.sdui.protocol.foundation.layout.BoxProto
import com.podca.sdui.protocol.foundation.layout.BoxWithConstraintsProto
import com.podca.sdui.protocol.foundation.layout.BoxWithConstraintsScopeProto
import com.podca.sdui.protocol.ui.alignment.AlignmentProto
import com.podca.sdui.studio.core.PodcaNode
import com.podca.sdui.studio.ui.core.PodcaModifier

@Composable
public fun PodcaBoxWithConstraints(
    modifier: PodcaModifier = PodcaModifier.Empty,
    contentAlignment: AlignmentProto? = null,
    propagateMinConstraints: Boolean = false,
    scope: BoxWithConstraintsScopeProto? = null,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
    content: @Composable () -> Unit,
) {
    PodcaNode(
        type = "foundation.layout.BoxWithConstraints",
        message = BoxWithConstraintsProto(
            box = BoxProto(
                modifier = modifier.toProto(),
                content_alignment = contentAlignment,
                propagate_min_constraints = propagateMinConstraints,
            ),
            scope = scope,
        ),
        key = key,
        actions = actions,
        encode = BoxWithConstraintsProto.ADAPTER::encode,
        content = content,
    )
}
