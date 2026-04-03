package com.podca.sdui.studio.ui.material3

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.material3.HorizontalDividerProto
import com.podca.sdui.protocol.material3.VerticalDividerProto
import com.podca.sdui.protocol.ui.graphics.ColorProto
import com.podca.sdui.protocol.ui.unit.DpProto
import com.podca.sdui.studio.core.PodcaNode
import com.podca.sdui.studio.ui.core.PodcaModifier

@Composable
public fun PodcaHorizontalDivider(
    modifier: PodcaModifier = PodcaModifier.Empty,
    thickness: DpProto? = null,
    color: ColorProto? = null,
) {
    PodcaNode(
        type = "material3.HorizontalDivider",
        message = HorizontalDividerProto(
            modifier = modifier.toProto(),
            thickness = thickness,
            color = color,
        ),
        encode = HorizontalDividerProto.ADAPTER::encode,
    )
}

@Composable
public fun PodcaVerticalDivider(
    modifier: PodcaModifier = PodcaModifier.Empty,
    thickness: DpProto? = null,
    color: ColorProto? = null,
) {
    PodcaNode(
        type = "material3.VerticalDivider",
        message = VerticalDividerProto(
            modifier = modifier.toProto(),
            thickness = thickness,
            color = color,
        ),
        encode = VerticalDividerProto.ADAPTER::encode,
    )
}
