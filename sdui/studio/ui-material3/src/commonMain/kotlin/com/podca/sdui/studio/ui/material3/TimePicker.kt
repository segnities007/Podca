package com.podca.sdui.studio.ui.material3

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.material3.TimePickerColorsProto
import com.podca.sdui.protocol.material3.TimePickerLayoutTypeProto
import com.podca.sdui.protocol.material3.TimePickerProto
import com.podca.sdui.protocol.material3.TimePickerSelectionModeProto
import com.podca.sdui.studio.core.PodcaNode
import com.podca.sdui.studio.ui.core.PodcaModifier

@Composable
public fun PodcaTimePicker(
    selectedHour: Int,
    selectedMinute: Int,
    modifier: PodcaModifier = PodcaModifier.Empty,
    enabled: Boolean = true,
    layoutType: TimePickerLayoutTypeProto = TimePickerLayoutTypeProto.TIME_PICKER_LAYOUT_TYPE_VERTICAL,
    selectionMode: TimePickerSelectionModeProto = TimePickerSelectionModeProto.TIME_PICKER_SELECTION_MODE_HOUR,
    colors: TimePickerColorsProto? = null,
    content: @Composable () -> Unit = {},
) {
    PodcaNode(
        type = "material3.TimePicker",
        message = TimePickerProto(
            modifier = modifier.toProto(),
            selected_hour = selectedHour,
            selected_minute = selectedMinute,
            enabled = enabled,
            layout_type = layoutType,
            selection_mode = selectionMode,
            colors = colors,
            has_content = true,
        ),
        encode = TimePickerProto.ADAPTER::encode,
        content = content,
    )
}
