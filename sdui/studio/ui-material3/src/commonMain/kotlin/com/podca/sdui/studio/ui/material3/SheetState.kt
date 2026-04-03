package com.podca.sdui.studio.ui.material3

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.material3.SheetStateProto
import com.podca.sdui.protocol.material3.SheetValueProto
import com.podca.sdui.studio.core.PodcaNode

@Composable
public fun PodcaSheetState(
    skipPartiallyExpanded: Boolean = false,
    currentValue: SheetValueProto = SheetValueProto.SHEET_VALUE_HIDDEN,
    targetValue: SheetValueProto = SheetValueProto.SHEET_VALUE_HIDDEN,
    isVisible: Boolean = false,
    isAnimationRunning: Boolean = false,
    hasExpandedState: Boolean = false,
    hasPartiallyExpandedState: Boolean = false,
    skipHiddenState: Boolean = false,
    offset: Float = 0f,
) {
    PodcaNode(
        type = "material3.SheetState",
        message = SheetStateProto(
            skip_partially_expanded = skipPartiallyExpanded,
            current_value = currentValue,
            target_value = targetValue,
            is_visible = isVisible,
            is_animation_running = isAnimationRunning,
            has_expanded_state = hasExpandedState,
            has_partially_expanded_state = hasPartiallyExpandedState,
            skip_hidden_state = skipHiddenState,
            offset = offset,
        ),
        encode = SheetStateProto.ADAPTER::encode,
    )
}
