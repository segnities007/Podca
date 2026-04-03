package com.podca.sdui.studio.ui.material3

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.material3.DrawerStateProto
import com.podca.sdui.protocol.material3.DrawerValueProto
import com.podca.sdui.studio.core.PodcaNode

@Composable
public fun PodcaDrawerState(
    currentValue: DrawerValueProto = DrawerValueProto.DRAWER_VALUE_CLOSED,
    targetValue: DrawerValueProto = DrawerValueProto.DRAWER_VALUE_CLOSED,
    isOpen: Boolean = false,
    isClosed: Boolean = false,
    isAnimationRunning: Boolean = false,
    currentOffset: Float = 0f,
    confirmStateChange: Boolean = false,
) {
    PodcaNode(
        type = "material3.DrawerState",
        message = DrawerStateProto(
            current_value = currentValue,
            target_value = targetValue,
            is_open = isOpen,
            is_closed = isClosed,
            is_animation_running = isAnimationRunning,
            current_offset = currentOffset,
            confirm_state_change = confirmStateChange,
        ),
        encode = DrawerStateProto.ADAPTER::encode,
    )
}
