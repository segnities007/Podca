package com.podca.sdui.studio.ui.material3

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.material3.SwipeToDismissBoxStateProto
import com.podca.sdui.protocol.material3.SwipeToDismissBoxValueProto
import com.podca.sdui.studio.core.PodcaNode

@Composable
public fun PodcaSwipeToDismissBoxState(
    currentValue: SwipeToDismissBoxValueProto =
        SwipeToDismissBoxValueProto.SWIPE_TO_DISMISS_BOX_VALUE_SETTLED,
    targetValue: SwipeToDismissBoxValueProto =
        SwipeToDismissBoxValueProto.SWIPE_TO_DISMISS_BOX_VALUE_SETTLED,
    settledValue: SwipeToDismissBoxValueProto =
        SwipeToDismissBoxValueProto.SWIPE_TO_DISMISS_BOX_VALUE_SETTLED,
    progress: Float = 0f,
    dismissDirection: SwipeToDismissBoxValueProto =
        SwipeToDismissBoxValueProto.SWIPE_TO_DISMISS_BOX_VALUE_SETTLED,
) {
    PodcaNode(
        type = "material3.SwipeToDismissBoxState",
        message = SwipeToDismissBoxStateProto(
            current_value = currentValue,
            target_value = targetValue,
            settled_value = settledValue,
            progress = progress,
            dismiss_direction = dismissDirection,
        ),
        encode = SwipeToDismissBoxStateProto.ADAPTER::encode,
    )
}

public fun PodcaSwipeToDismissBoxValue(
    value: SwipeToDismissBoxValueProto =
        SwipeToDismissBoxValueProto.SWIPE_TO_DISMISS_BOX_VALUE_SETTLED,
): SwipeToDismissBoxValueProto = value
