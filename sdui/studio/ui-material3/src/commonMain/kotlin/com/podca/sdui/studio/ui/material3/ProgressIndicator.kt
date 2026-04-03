package com.podca.sdui.studio.ui.material3

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.material3.CircularProgressIndicatorProto
import com.podca.sdui.protocol.material3.LinearProgressIndicatorProto
import com.podca.sdui.protocol.ui.graphics.ColorProto
import com.podca.sdui.protocol.ui.graphics.StrokeCapProto
import com.podca.sdui.protocol.ui.unit.DpProto
import com.podca.sdui.studio.core.PodcaNode
import com.podca.sdui.studio.ui.core.PodcaModifier

@Composable
public fun PodcaLinearProgressIndicator(
    progress: Float,
    modifier: PodcaModifier = PodcaModifier.Empty,
    color: ColorProto? = null,
    trackColor: ColorProto? = null,
    strokeCap: StrokeCapProto = StrokeCapProto.STROKE_CAP_BUTT,
    gapSize: DpProto? = null,
) {
    PodcaNode(
        type = "material3.LinearProgressIndicator",
        message = LinearProgressIndicatorProto(
            modifier = modifier.toProto(),
            progress = progress,
            color = color,
            track_color = trackColor,
            stroke_cap = strokeCap,
            gap_size = gapSize,
        ),
        encode = LinearProgressIndicatorProto.ADAPTER::encode,
    )
}

@Composable
public fun PodcaCircularProgressIndicator(
    progress: Float,
    modifier: PodcaModifier = PodcaModifier.Empty,
    color: ColorProto? = null,
    trackColor: ColorProto? = null,
    strokeCap: StrokeCapProto = StrokeCapProto.STROKE_CAP_BUTT,
    gapSize: DpProto? = null,
) {
    PodcaNode(
        type = "material3.CircularProgressIndicator",
        message = CircularProgressIndicatorProto(
            modifier = modifier.toProto(),
            progress = progress,
            color = color,
            track_color = trackColor,
            stroke_cap = strokeCap,
            gap_size = gapSize,
        ),
        encode = CircularProgressIndicatorProto.ADAPTER::encode,
    )
}
