package com.podca.sdui.studio.ui.material3

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.core.ActionBindingProto
import com.podca.sdui.protocol.material3.CheckboxColorsProto
import com.podca.sdui.protocol.material3.CheckboxProto
import com.podca.sdui.protocol.material3.RadioButtonColorsProto
import com.podca.sdui.protocol.material3.RadioButtonProto
import com.podca.sdui.protocol.material3.SwitchColorsProto
import com.podca.sdui.protocol.material3.SwitchProto
import com.podca.sdui.studio.core.PodcaNode
import com.podca.sdui.studio.ui.core.PodcaModifier

@Composable
public fun PodcaCheckbox(
    checked: Boolean,
    modifier: PodcaModifier = PodcaModifier.Empty,
    enabled: Boolean = true,
    colors: CheckboxColorsProto? = null,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
) {
    PodcaNode(
        type = "material3.Checkbox",
        message = CheckboxProto(
            modifier = modifier.toProto(),
            checked = checked,
            enabled = enabled,
            colors = colors,
        ),
        key = key,
        actions = actions,
        encode = CheckboxProto.ADAPTER::encode,
    )
}

@Composable
public fun PodcaRadioButton(
    selected: Boolean,
    modifier: PodcaModifier = PodcaModifier.Empty,
    enabled: Boolean = true,
    colors: RadioButtonColorsProto? = null,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
) {
    PodcaNode(
        type = "material3.RadioButton",
        message = RadioButtonProto(
            modifier = modifier.toProto(),
            selected = selected,
            enabled = enabled,
            colors = colors,
        ),
        key = key,
        actions = actions,
        encode = RadioButtonProto.ADAPTER::encode,
    )
}

@Composable
public fun PodcaSwitch(
    checked: Boolean,
    modifier: PodcaModifier = PodcaModifier.Empty,
    enabled: Boolean = true,
    colors: SwitchColorsProto? = null,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
    thumbContent: @Composable (() -> Unit)? = null,
) {
    PodcaNode(
        type = "material3.Switch",
        message = SwitchProto(
            modifier = modifier.toProto(),
            checked = checked,
            enabled = enabled,
            colors = colors,
            has_thumb_content = thumbContent != null,
        ),
        key = key,
        actions = actions,
        encode = SwitchProto.ADAPTER::encode,
        content = { thumbContent?.invoke() },
    )
}
