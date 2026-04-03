package com.podca.sdui.studio.ui.material3

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.material3.DateRangePickerStateProto
import com.podca.sdui.protocol.material3.DisplayModeProto
import com.podca.sdui.studio.core.PodcaNode

@Composable
public fun PodcaDateRangePickerState(
    selectedStartDateMillis: Long = 0L,
    selectedEndDateMillis: Long = 0L,
    displayedMonthMillis: Long = 0L,
    displayMode: DisplayModeProto = DisplayModeProto.DISPLAY_MODE_PICKER,
    yearRangeStart: Int = 0,
    yearRangeEnd: Int = 0,
    localeTag: String = "",
) {
    PodcaNode(
        type = "material3.DateRangePickerState",
        message = DateRangePickerStateProto(
            selected_start_date_millis = selectedStartDateMillis,
            selected_end_date_millis = selectedEndDateMillis,
            displayed_month_millis = displayedMonthMillis,
            display_mode = displayMode,
            year_range_start = yearRangeStart,
            year_range_end = yearRangeEnd,
            locale_tag = localeTag,
        ),
        encode = DateRangePickerStateProto.ADAPTER::encode,
    )
}
