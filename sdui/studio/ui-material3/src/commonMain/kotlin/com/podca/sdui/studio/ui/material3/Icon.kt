package com.podca.sdui.studio.ui.material3

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.material3.IconProto
import com.podca.sdui.protocol.ui.graphics.ColorProto
import com.podca.sdui.protocol.ui.graphics.ImageBitmapProto
import com.podca.sdui.studio.core.PodcaNode
import com.podca.sdui.studio.ui.core.PodcaModifier

@Composable
public fun PodcaIcon(
    imageVectorName: String,
    contentDescription: String,
    modifier: PodcaModifier = PodcaModifier.Empty,
    tint: ColorProto? = null,
) {
    PodcaNode(
        type = "material3.Icon",
        message = IconProto(
            modifier = modifier.toProto(),
            content_description = contentDescription,
            tint = tint,
            image_vector_name = imageVectorName,
        ),
        encode = IconProto.ADAPTER::encode,
    )
}

@Composable
public fun PodcaIcon(
    bitmap: ImageBitmapProto,
    contentDescription: String,
    modifier: PodcaModifier = PodcaModifier.Empty,
    tint: ColorProto? = null,
) {
    PodcaNode(
        type = "material3.Icon",
        message = IconProto(
            modifier = modifier.toProto(),
            content_description = contentDescription,
            tint = tint,
            bitmap = bitmap,
        ),
        encode = IconProto.ADAPTER::encode,
    )
}

@Composable
public fun PodcaPainterIcon(
    painterName: String,
    contentDescription: String,
    modifier: PodcaModifier = PodcaModifier.Empty,
    tint: ColorProto? = null,
) {
    PodcaNode(
        type = "material3.Icon",
        message = IconProto(
            modifier = modifier.toProto(),
            content_description = contentDescription,
            tint = tint,
            painter_name = painterName,
        ),
        encode = IconProto.ADAPTER::encode,
    )
}
