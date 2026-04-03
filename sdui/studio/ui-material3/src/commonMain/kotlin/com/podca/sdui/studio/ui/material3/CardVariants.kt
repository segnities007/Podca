package com.podca.sdui.studio.ui.material3

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.core.ActionBindingProto
import com.podca.sdui.protocol.material3.BorderStrokeProto
import com.podca.sdui.protocol.material3.CardColorsProto
import com.podca.sdui.protocol.material3.CardElevationProto
import com.podca.sdui.protocol.material3.ElevatedCardProto
import com.podca.sdui.protocol.material3.OutlinedCardProto
import com.podca.sdui.protocol.ui.graphics.ShapeProto
import com.podca.sdui.studio.core.PodcaNode
import com.podca.sdui.studio.ui.core.PodcaModifier

@Composable
public fun PodcaElevatedCard(
    modifier: PodcaModifier = PodcaModifier.Empty,
    enabled: Boolean = true,
    shape: ShapeProto? = null,
    colors: CardColorsProto? = null,
    elevation: CardElevationProto? = null,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
    content: @Composable () -> Unit,
) {
    PodcaNode(
        type = "material3.ElevatedCard",
        message = ElevatedCardProto(
            modifier = modifier.toProto(),
            enabled = enabled,
            shape = shape,
            colors = colors,
            elevation = elevation,
            has_content = true,
        ),
        key = key,
        actions = actions,
        encode = ElevatedCardProto.ADAPTER::encode,
        content = content,
    )
}

@Composable
public fun PodcaOutlinedCard(
    modifier: PodcaModifier = PodcaModifier.Empty,
    enabled: Boolean = true,
    shape: ShapeProto? = null,
    colors: CardColorsProto? = null,
    elevation: CardElevationProto? = null,
    border: BorderStrokeProto? = null,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
    content: @Composable () -> Unit,
) {
    PodcaNode(
        type = "material3.OutlinedCard",
        message = OutlinedCardProto(
            modifier = modifier.toProto(),
            enabled = enabled,
            shape = shape,
            colors = colors,
            elevation = elevation,
            border = border,
            has_content = true,
        ),
        key = key,
        actions = actions,
        encode = OutlinedCardProto.ADAPTER::encode,
        content = content,
    )
}
