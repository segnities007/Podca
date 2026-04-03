package com.podca.sdui.studio.ui.material3

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.material3.ColorSchemeProto
import com.podca.sdui.protocol.material3.MaterialThemeProto
import com.podca.sdui.protocol.material3.MotionSchemeProto
import com.podca.sdui.protocol.material3.ShapesProto
import com.podca.sdui.protocol.material3.TypographyProto
import com.podca.sdui.studio.core.PodcaNode

@Composable
public fun PodcaMaterialTheme(
    colorScheme: ColorSchemeProto? = null,
    typography: TypographyProto? = null,
    shapes: ShapesProto? = null,
    motionScheme: MotionSchemeProto = MotionSchemeProto.MOTION_SCHEME_UNSPECIFIED,
    content: @Composable () -> Unit,
) {
    PodcaNode(
        type = "material3.MaterialTheme",
        message = MaterialThemeProto(
            color_scheme = colorScheme,
            typography = typography,
            shapes = shapes,
            motion_scheme = motionScheme,
        ),
        encode = MaterialThemeProto.ADAPTER::encode,
        content = content,
    )
}
