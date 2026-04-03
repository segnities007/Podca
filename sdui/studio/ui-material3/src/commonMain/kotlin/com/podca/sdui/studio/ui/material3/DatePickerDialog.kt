package com.podca.sdui.studio.ui.material3

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.core.ActionBindingProto
import com.podca.sdui.protocol.material3.DatePickerDialogProto
import com.podca.sdui.protocol.ui.graphics.ColorProto
import com.podca.sdui.protocol.ui.unit.DpProto
import com.podca.sdui.studio.core.PodcaNode
import com.podca.sdui.studio.ui.core.PodcaModifier

@Composable
public fun PodcaDatePickerDialog(
    modifier: PodcaModifier = PodcaModifier.Empty,
    dismissOnBackPress: Boolean = true,
    dismissOnClickOutside: Boolean = true,
    containerColor: ColorProto? = null,
    tonalElevationOverlayColor: ColorProto? = null,
    tonalElevation: DpProto? = null,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
    content: @Composable () -> Unit,
) {
    PodcaNode(
        type = "material3.DatePickerDialog",
        message = DatePickerDialogProto(
            modifier = modifier.toProto(),
            dismiss_on_back_press = dismissOnBackPress,
            dismiss_on_click_outside = dismissOnClickOutside,
            container_color = containerColor,
            tonal_elevation_overlay_color = tonalElevationOverlayColor,
            tonal_elevation = tonalElevation,
        ),
        key = key,
        actions = actions,
        encode = DatePickerDialogProto.ADAPTER::encode,
        content = content,
    )
}
