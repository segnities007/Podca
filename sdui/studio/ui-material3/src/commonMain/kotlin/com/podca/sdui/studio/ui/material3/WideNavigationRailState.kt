package com.podca.sdui.studio.ui.material3

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.material3.ModalWideNavigationRailPropertiesProto
import com.podca.sdui.protocol.material3.WideNavigationRailStateProto
import com.podca.sdui.protocol.material3.WideNavigationRailValueProto
import com.podca.sdui.studio.core.PodcaNode

@Composable
public fun PodcaWideNavigationRailState(
    isAnimating: Boolean = false,
    targetValue: WideNavigationRailValueProto = WideNavigationRailValueProto.WIDE_NAVIGATION_RAIL_VALUE_COLLAPSED,
    currentValue: WideNavigationRailValueProto = WideNavigationRailValueProto.WIDE_NAVIGATION_RAIL_VALUE_COLLAPSED,
    currentOffset: Float = 0f,
    isExpanded: Boolean = false,
    isCollapsed: Boolean = false,
    modalVisible: Boolean = false,
) {
    PodcaNode(
        type = "material3.WideNavigationRailState",
        message = WideNavigationRailStateProto(
            is_animating = isAnimating,
            target_value = targetValue,
            current_value = currentValue,
            current_offset = currentOffset,
            is_expanded = isExpanded,
            is_collapsed = isCollapsed,
            modal_visible = modalVisible,
        ),
        encode = WideNavigationRailStateProto.ADAPTER::encode,
    )
}

@Composable
public fun PodcaModalWideNavigationRailProperties(
    shouldDismissOnBackPress: Boolean = false,
) {
    PodcaNode(
        type = "material3.ModalWideNavigationRailProperties",
        message = ModalWideNavigationRailPropertiesProto(
            should_dismiss_on_back_press = shouldDismissOnBackPress,
        ),
        encode = ModalWideNavigationRailPropertiesProto.ADAPTER::encode,
    )
}
