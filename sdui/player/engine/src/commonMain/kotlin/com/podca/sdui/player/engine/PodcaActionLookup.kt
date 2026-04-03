package com.podca.sdui.player.engine

import com.podca.sdui.protocol.core.ActionBindingProto
import com.podca.sdui.protocol.core.ActionTriggerProto

public fun PodcaActionAt(
    actions: List<ActionBindingProto>,
    index: Int,
): ActionBindingProto? =
    actions.getOrNull(index)

public fun PodcaActionByTrigger(
    actions: List<ActionBindingProto>,
    trigger: ActionTriggerProto,
): ActionBindingProto? =
    actions.firstOrNull { it.trigger == trigger }
