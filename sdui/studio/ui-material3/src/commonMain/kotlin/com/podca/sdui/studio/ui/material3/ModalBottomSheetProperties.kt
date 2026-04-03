package com.podca.sdui.studio.ui.material3

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.material3.ModalBottomSheetPropertiesProto
import com.podca.sdui.studio.core.PodcaNode

@Composable
public fun PodcaModalBottomSheetProperties(
    shouldDismissOnBackPress: Boolean = false,
    shouldDismissOnClickOutside: Boolean = false,
) {
    PodcaNode(
        type = "material3.ModalBottomSheetProperties",
        message = ModalBottomSheetPropertiesProto(
            should_dismiss_on_back_press = shouldDismissOnBackPress,
            should_dismiss_on_click_outside = shouldDismissOnClickOutside,
        ),
        encode = ModalBottomSheetPropertiesProto.ADAPTER::encode,
    )
}
