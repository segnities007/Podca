package com.podca.sdui.studio.ui.material3

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.material3.RippleAlphaProto
import com.podca.sdui.protocol.material3.RippleConfigurationProto
import com.podca.sdui.protocol.ui.graphics.ColorProto
import com.podca.sdui.studio.core.PodcaNode

@Composable
public fun PodcaRippleConfiguration(
    color: ColorProto? = null,
    rippleAlpha: RippleAlphaProto? = null,
    content: @Composable () -> Unit,
) {
    PodcaNode(
        type = "material3.RippleConfiguration",
        message = RippleConfigurationProto(
            color = color,
            ripple_alpha = rippleAlpha,
        ),
        encode = RippleConfigurationProto.ADAPTER::encode,
        content = content,
    )
}
