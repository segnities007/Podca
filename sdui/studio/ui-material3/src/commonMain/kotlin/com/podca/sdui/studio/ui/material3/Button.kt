package com.podca.sdui.studio.ui.material3

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.core.ActionBindingProto
import com.podca.sdui.protocol.foundation.layout.PaddingValuesProto
import com.podca.sdui.protocol.material3.BorderStrokeProto
import com.podca.sdui.protocol.material3.ButtonColorsProto
import com.podca.sdui.protocol.material3.ButtonElevationProto
import com.podca.sdui.protocol.material3.ButtonProto
import com.podca.sdui.protocol.material3.CardProto
import com.podca.sdui.protocol.material3.ElevatedButtonProto
import com.podca.sdui.protocol.material3.FilledTonalButtonProto
import com.podca.sdui.protocol.material3.OutlinedButtonProto
import com.podca.sdui.protocol.material3.TextButtonProto
import com.podca.sdui.protocol.ui.graphics.ShapeProto
import com.podca.sdui.studio.core.PodcaNode
import com.podca.sdui.studio.ui.core.PodcaModifier

@Composable
public fun PodcaButton(
    modifier: PodcaModifier = PodcaModifier.Empty,
    enabled: Boolean = true,
    shape: ShapeProto? = null,
    colors: ButtonColorsProto? = null,
    elevation: ButtonElevationProto? = null,
    border: BorderStrokeProto? = null,
    contentPadding: PaddingValuesProto? = null,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
    content: @Composable () -> Unit,
) {
    PodcaNode(
        type = "material3.Button",
        message = ButtonProto(
            modifier = modifier.toProto(),
            enabled = enabled,
            shape = shape,
            colors = colors,
            elevation = elevation,
            border = border,
            content_padding = contentPadding,
            has_content = true,
        ),
        key = key,
        actions = actions,
        encode = ButtonProto.ADAPTER::encode,
        content = content,
    )
}

@Composable
public fun PodcaElevatedButton(
    modifier: PodcaModifier = PodcaModifier.Empty,
    enabled: Boolean = true,
    shape: ShapeProto? = null,
    colors: ButtonColorsProto? = null,
    elevation: ButtonElevationProto? = null,
    border: BorderStrokeProto? = null,
    contentPadding: PaddingValuesProto? = null,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
    content: @Composable () -> Unit,
) {
    PodcaNode(
        type = "material3.ElevatedButton",
        message = ElevatedButtonProto(
            modifier = modifier.toProto(),
            enabled = enabled,
            shape = shape,
            colors = colors,
            elevation = elevation,
            border = border,
            content_padding = contentPadding,
            has_content = true,
        ),
        key = key,
        actions = actions,
        encode = ElevatedButtonProto.ADAPTER::encode,
        content = content,
    )
}

@Composable
public fun PodcaFilledTonalButton(
    modifier: PodcaModifier = PodcaModifier.Empty,
    enabled: Boolean = true,
    shape: ShapeProto? = null,
    colors: ButtonColorsProto? = null,
    elevation: ButtonElevationProto? = null,
    border: BorderStrokeProto? = null,
    contentPadding: PaddingValuesProto? = null,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
    content: @Composable () -> Unit,
) {
    PodcaNode(
        type = "material3.FilledTonalButton",
        message = FilledTonalButtonProto(
            modifier = modifier.toProto(),
            enabled = enabled,
            shape = shape,
            colors = colors,
            elevation = elevation,
            border = border,
            content_padding = contentPadding,
            has_content = true,
        ),
        key = key,
        actions = actions,
        encode = FilledTonalButtonProto.ADAPTER::encode,
        content = content,
    )
}

@Composable
public fun PodcaOutlinedButton(
    modifier: PodcaModifier = PodcaModifier.Empty,
    enabled: Boolean = true,
    shape: ShapeProto? = null,
    colors: ButtonColorsProto? = null,
    elevation: ButtonElevationProto? = null,
    border: BorderStrokeProto? = null,
    contentPadding: PaddingValuesProto? = null,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
    content: @Composable () -> Unit,
) {
    PodcaNode(
        type = "material3.OutlinedButton",
        message = OutlinedButtonProto(
            modifier = modifier.toProto(),
            enabled = enabled,
            shape = shape,
            colors = colors,
            elevation = elevation,
            border = border,
            content_padding = contentPadding,
            has_content = true,
        ),
        key = key,
        actions = actions,
        encode = OutlinedButtonProto.ADAPTER::encode,
        content = content,
    )
}

@Composable
public fun PodcaTextButton(
    modifier: PodcaModifier = PodcaModifier.Empty,
    enabled: Boolean = true,
    shape: ShapeProto? = null,
    colors: ButtonColorsProto? = null,
    elevation: ButtonElevationProto? = null,
    border: BorderStrokeProto? = null,
    contentPadding: PaddingValuesProto? = null,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
    content: @Composable () -> Unit,
) {
    PodcaNode(
        type = "material3.TextButton",
        message = TextButtonProto(
            modifier = modifier.toProto(),
            enabled = enabled,
            shape = shape,
            colors = colors,
            elevation = elevation,
            border = border,
            content_padding = contentPadding,
            has_content = true,
        ),
        key = key,
        actions = actions,
        encode = TextButtonProto.ADAPTER::encode,
        content = content,
    )
}
@Composable
public fun PodcaCard(
    modifier: PodcaModifier = PodcaModifier.Empty,
    enabled: Boolean = true,
    shape: ShapeProto? = null,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
    content: @Composable () -> Unit,
) {
    PodcaNode(
        type = "material3.Card",
        message = CardProto(
            modifier = modifier.toProto(),
            enabled = enabled,
            shape = shape,
            has_content = true,
        ),
        key = key,
        actions = actions,
        encode = CardProto.ADAPTER::encode,
        content = content,
    )
}
