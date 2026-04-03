package com.podca.sdui.studio.ui.material3

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.material3.HorizontalCenterOpticallyProto
import com.podca.sdui.protocol.ui.unit.DpProto
import com.podca.sdui.studio.core.PodcaNode
import com.podca.sdui.studio.ui.core.PodcaModifier

@Composable
public fun PodcaHorizontalCenterOptically(
    modifier: PodcaModifier = PodcaModifier.Empty,
    opticalInsetStart: DpProto? = null,
    opticalInsetEnd: DpProto? = null,
) {
    PodcaNode(
        type = "material3.HorizontalCenterOptically",
        message = HorizontalCenterOpticallyProto(
            modifier = modifier.toProto(),
            optical_inset_start = opticalInsetStart,
            optical_inset_end = opticalInsetEnd,
        ),
        encode = HorizontalCenterOpticallyProto.ADAPTER::encode,
    )
}
