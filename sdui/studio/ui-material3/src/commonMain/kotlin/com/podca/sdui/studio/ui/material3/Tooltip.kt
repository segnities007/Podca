package com.podca.sdui.studio.ui.material3

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.material3.RichTooltipColorsProto
import com.podca.sdui.protocol.material3.TooltipAnchorPositionProto
import com.podca.sdui.protocol.material3.TooltipProto
import com.podca.sdui.studio.core.PodcaNode
import com.podca.sdui.studio.ui.core.PodcaModifier

@Composable
public fun PodcaTooltip(
    message: String,
    anchorPosition: TooltipAnchorPositionProto = TooltipAnchorPositionProto.TOOLTIP_ANCHOR_POSITION_UNSPECIFIED,
    modifier: PodcaModifier = PodcaModifier.Empty,
    colors: RichTooltipColorsProto? = null,
    content: @Composable () -> Unit,
) {
    PodcaNode(
        type = "material3.Tooltip",
        message = TooltipProto(
            modifier = modifier.toProto(),
            message = message,
            anchor_position = anchorPosition,
            colors = colors,
            has_content = true,
        ),
        encode = TooltipProto.ADAPTER::encode,
        content = content,
    )
}
