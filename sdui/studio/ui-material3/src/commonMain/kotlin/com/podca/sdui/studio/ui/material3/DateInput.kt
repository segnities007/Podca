package com.podca.sdui.studio.ui.material3

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.material3.DateInputProto
import com.podca.sdui.protocol.material3.DateRangeInputProto
import com.podca.sdui.protocol.material3.DatePickerColorsProto
import com.podca.sdui.protocol.material3.InputIdentifierProto
import com.podca.sdui.protocol.ui.graphics.ColorProto
import com.podca.sdui.studio.core.PodcaNode

@Composable
public fun PodcaDateInput(
    hasEndDate: Boolean = false,
    inputIdentifier: InputIdentifierProto? = null,
    content: @Composable (() -> Unit)? = null,
) {
    PodcaNode(
        type = "material3.DateInput",
        message = DateInputProto(
            has_end_date = hasEndDate,
            input_identifier = inputIdentifier,
        ),
        encode = DateInputProto.ADAPTER::encode,
    ) {
        content?.invoke()
    }
}

@Composable
public fun PodcaDateRangeInput(
    colors: DatePickerColorsProto? = null,
    selectedRangeContainerColor: ColorProto? = null,
    selectedRangeContentColor: ColorProto? = null,
    startDatePattern: String = "",
    endDatePattern: String = "",
    localeTag: String = "",
    content: @Composable (() -> Unit)? = null,
) {
    PodcaNode(
        type = "material3.DateRangeInput",
        message = DateRangeInputProto(
            colors = colors,
            selected_range_container_color = selectedRangeContainerColor,
            selected_range_content_color = selectedRangeContentColor,
            start_date_pattern = startDatePattern,
            end_date_pattern = endDatePattern,
            locale_tag = localeTag,
        ),
        encode = DateRangeInputProto.ADAPTER::encode,
    ) {
        content?.invoke()
    }
}
