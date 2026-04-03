package com.podca.sdui.studio.ui.material3

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.material3.DatePickerProto
import com.podca.sdui.studio.core.PodcaNode
import com.podca.sdui.studio.ui.core.PodcaModifier

@Composable
public fun PodcaDatePicker(
    modifier: PodcaModifier = PodcaModifier.Empty,
    showModeToggle: Boolean = true,
    title: (@Composable () -> Unit)? = null,
    headline: (@Composable () -> Unit)? = null,
    content: @Composable () -> Unit = {},
) {
    PodcaNode(
        type = "material3.DatePicker",
        message = DatePickerProto(
            modifier = modifier.toProto(),
            show_mode_toggle = showModeToggle,
            has_title = title != null,
            has_headline = headline != null,
        ),
        encode = DatePickerProto.ADAPTER::encode,
    ) {
        if (title != null) {
            PodcaNode(
                type = "material3.DatePicker.TitleSlot",
                slot = "title",
                content = title,
            )
        }
        if (headline != null) {
            PodcaNode(
                type = "material3.DatePicker.HeadlineSlot",
                slot = "headline",
                content = headline,
            )
        }
        content()
    }
}
