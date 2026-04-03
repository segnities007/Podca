package com.podca.sdui.studio.ui.material3

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.material3.OutlinedSecureTextFieldProto
import com.podca.sdui.protocol.material3.SecureTextFieldProto
import com.podca.sdui.protocol.material3.TextFieldColorsProto
import com.podca.sdui.protocol.material3.TextFieldProto
import com.podca.sdui.protocol.material3.TextFieldStateProto
import com.podca.sdui.protocol.ui.text.TextStyleProto
import com.podca.sdui.studio.core.PodcaNode
import com.podca.sdui.studio.ui.core.PodcaModifier

@Composable
public fun PodcaSecureTextField(
    state: TextFieldStateProto,
    modifier: PodcaModifier = PodcaModifier.Empty,
    revealed: Boolean = false,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyleProto? = null,
    label: String = "",
    placeholder: String = "",
    prefix: String = "",
    suffix: String = "",
    supportingText: String = "",
    isError: Boolean = false,
    colors: TextFieldColorsProto? = null,
    lineLimits: Int = 1,
    slots: TextFieldSlots = TextFieldSlots(),
) {
    PodcaNode(
        type = "material3.SecureTextField",
        message = SecureTextFieldProto(
            field_ = TextFieldProto(
                modifier = modifier.toProto(),
                state = state,
                enabled = enabled,
                read_only = readOnly,
                text_style = textStyle,
                label = label,
                placeholder = placeholder,
                prefix = prefix,
                suffix = suffix,
                supporting_text = supportingText,
                is_error = isError,
                colors = colors,
                line_limits = lineLimits,
            ),
            revealed = revealed,
        ),
        encode = SecureTextFieldProto.ADAPTER::encode,
    ) {
        emitTextFieldSlots(slots)
    }
}

@Composable
public fun PodcaOutlinedSecureTextField(
    state: TextFieldStateProto,
    modifier: PodcaModifier = PodcaModifier.Empty,
    revealed: Boolean = false,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    textStyle: TextStyleProto? = null,
    label: String = "",
    placeholder: String = "",
    prefix: String = "",
    suffix: String = "",
    supportingText: String = "",
    isError: Boolean = false,
    colors: TextFieldColorsProto? = null,
    lineLimits: Int = 1,
    slots: TextFieldSlots = TextFieldSlots(),
) {
    PodcaNode(
        type = "material3.OutlinedSecureTextField",
        message = OutlinedSecureTextFieldProto(
            field_ = TextFieldProto(
                modifier = modifier.toProto(),
                state = state,
                enabled = enabled,
                read_only = readOnly,
                text_style = textStyle,
                label = label,
                placeholder = placeholder,
                prefix = prefix,
                suffix = suffix,
                supporting_text = supportingText,
                is_error = isError,
                colors = colors,
                line_limits = lineLimits,
            ),
            revealed = revealed,
        ),
        encode = OutlinedSecureTextFieldProto.ADAPTER::encode,
    ) {
        emitTextFieldSlots(slots)
    }
}
