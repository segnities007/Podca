package com.podca.sdui.studio.ui.material3

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.core.ActionBindingProto
import com.podca.sdui.protocol.material3.OutlinedTextFieldProto
import com.podca.sdui.protocol.material3.TextFieldColorsProto
import com.podca.sdui.protocol.material3.TextFieldProto
import com.podca.sdui.protocol.material3.TextFieldStateProto
import com.podca.sdui.protocol.ui.text.TextStyleProto
import com.podca.sdui.studio.core.PodcaNode
import com.podca.sdui.studio.ui.core.PodcaModifier

public data class TextFieldSlots(
    public val label: (@Composable () -> Unit)? = null,
    public val placeholder: (@Composable () -> Unit)? = null,
    public val prefix: (@Composable () -> Unit)? = null,
    public val suffix: (@Composable () -> Unit)? = null,
    public val supportingText: (@Composable () -> Unit)? = null,
)

@Composable
public fun PodcaTextField(
    state: TextFieldStateProto,
    modifier: PodcaModifier = PodcaModifier.Empty,
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
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
    slots: TextFieldSlots = TextFieldSlots(),
) {
    PodcaNode(
        type = "material3.TextField",
        message = TextFieldProto(
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
        key = key,
        actions = actions,
        encode = TextFieldProto.ADAPTER::encode,
    ) {
        emitTextFieldSlots(slots)
    }
}

@Composable
public fun PodcaOutlinedTextField(
    state: TextFieldStateProto,
    modifier: PodcaModifier = PodcaModifier.Empty,
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
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
    slots: TextFieldSlots = TextFieldSlots(),
) {
    PodcaNode(
        type = "material3.OutlinedTextField",
        message = OutlinedTextFieldProto(
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
        ),
        key = key,
        actions = actions,
        encode = OutlinedTextFieldProto.ADAPTER::encode,
    ) {
        emitTextFieldSlots(slots)
    }
}

@Composable
internal fun emitTextFieldSlots(slots: TextFieldSlots) {
    slots.label?.let { label ->
        PodcaNode(
            type = "material3.TextField.LabelSlot",
            slot = "label",
            content = label,
        )
    }
    slots.placeholder?.let { placeholder ->
        PodcaNode(
            type = "material3.TextField.PlaceholderSlot",
            slot = "placeholder",
            content = placeholder,
        )
    }
    slots.prefix?.let { prefix ->
        PodcaNode(
            type = "material3.TextField.PrefixSlot",
            slot = "prefix",
            content = prefix,
        )
    }
    slots.suffix?.let { suffix ->
        PodcaNode(
            type = "material3.TextField.SuffixSlot",
            slot = "suffix",
            content = suffix,
        )
    }
    slots.supportingText?.let { supportingText ->
        PodcaNode(
            type = "material3.TextField.SupportingTextSlot",
            slot = "supportingText",
            content = supportingText,
        )
    }
}
