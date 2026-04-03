package com.podca.sdui.studio.ui.material3

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.material3.FloatingActionButtonElevationProto
import com.podca.sdui.protocol.ui.unit.DpProto
import com.podca.sdui.studio.core.PodcaNode

@Composable
public fun PodcaFloatingActionButtonElevation(
    defaultElevation: DpProto? = null,
    pressedElevation: DpProto? = null,
    focusedElevation: DpProto? = null,
    hoveredElevation: DpProto? = null,
) {
    PodcaNode(
        type = "material3.FloatingActionButtonElevation",
        message = FloatingActionButtonElevationProto(
            default_elevation = defaultElevation,
            pressed_elevation = pressedElevation,
            focused_elevation = focusedElevation,
            hovered_elevation = hoveredElevation,
        ),
        encode = FloatingActionButtonElevationProto.ADAPTER::encode,
    )
}
