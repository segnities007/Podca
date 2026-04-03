package com.podca.sdui.studio.ui.material3

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.core.ActionBindingProto
import com.podca.sdui.protocol.material3.SliderColorsProto
import com.podca.sdui.protocol.material3.SliderProto
import com.podca.sdui.studio.core.PodcaNode
import com.podca.sdui.studio.ui.core.PodcaModifier

@Composable
public fun PodcaSlider(
    value: Float,
    modifier: PodcaModifier = PodcaModifier.Empty,
    enabled: Boolean = true,
    valueRangeStart: Float = 0f,
    valueRangeEnd: Float = 1f,
    steps: Int = 0,
    colors: SliderColorsProto? = null,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
) {
    PodcaNode(
        type = "material3.Slider",
        message = SliderProto(
            modifier = modifier.toProto(),
            value_ = value,
            enabled = enabled,
            value_range_start = valueRangeStart,
            value_range_end = valueRangeEnd,
            steps = steps,
            colors = colors,
        ),
        key = key,
        actions = actions,
        encode = SliderProto.ADAPTER::encode,
    )
}
