package com.podca.sdui.studio.ui.material3

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.core.ActionBindingProto
import com.podca.sdui.protocol.material3.BorderStrokeProto
import com.podca.sdui.protocol.material3.SurfaceProto
import com.podca.sdui.protocol.ui.graphics.ColorProto
import com.podca.sdui.protocol.ui.graphics.ShapeProto
import com.podca.sdui.protocol.ui.unit.DpProto
import com.podca.sdui.studio.core.PodcaNode
import com.podca.sdui.studio.ui.core.PodcaModifier

@Composable
public fun PodcaSurface(
    modifier: PodcaModifier = PodcaModifier.Empty,
    shape: ShapeProto? = null,
    color: ColorProto? = null,
    contentColor: ColorProto? = null,
    tonalElevation: DpProto? = null,
    shadowElevation: DpProto? = null,
    border: BorderStrokeProto? = null,
    enabled: Boolean = true,
    selected: Boolean = false,
    checked: Boolean = false,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
    content: @Composable () -> Unit,
) {
    PodcaNode(
        type = "material3.Surface",
        message = SurfaceProto(
            modifier = modifier.toProto(),
            shape = shape,
            color = color,
            content_color = contentColor,
            tonal_elevation = tonalElevation,
            shadow_elevation = shadowElevation,
            border = border,
            enabled = enabled,
            selected = selected,
            checked = checked,
        ),
        key = key,
        actions = actions,
        encode = SurfaceProto.ADAPTER::encode,
        content = content,
    )
}
