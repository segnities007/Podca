package com.podca.sdui.studio.ui.material3

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.material3.CalendarLocaleProto
import com.podca.sdui.studio.core.PodcaNode

@Composable
public fun PodcaCalendarLocale(
    languageTag: String,
) {
    PodcaNode(
        type = "material3.CalendarLocale",
        message = CalendarLocaleProto(
            language_tag = languageTag,
        ),
        encode = CalendarLocaleProto.ADAPTER::encode,
    )
}
