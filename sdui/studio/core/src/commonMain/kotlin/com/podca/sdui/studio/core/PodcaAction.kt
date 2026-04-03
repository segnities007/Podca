package com.podca.sdui.studio.core

import com.podca.sdui.protocol.core.ActionArgumentProto
import com.podca.sdui.protocol.core.ActionBindingProto
import com.podca.sdui.protocol.core.ActionTriggerProto

public fun PodcaActionBinding(
    actionId: String,
    trigger: ActionTriggerProto = ActionTriggerProto.ACTION_TRIGGER_CLICK,
    arguments: List<ActionArgumentProto> = emptyList(),
    expectsStateUpdate: Boolean = false,
): ActionBindingProto =
    ActionBindingProto(
        trigger = trigger,
        action_id = actionId,
        arguments = arguments,
        expects_state_update = expectsStateUpdate,
    )

public fun PodcaActionArgumentString(
    name: String,
    value: String,
): ActionArgumentProto =
    ActionArgumentProto(
        name = name,
        string_value = value,
    )

public fun PodcaActionArgumentInt(
    name: String,
    value: Long,
): ActionArgumentProto =
    ActionArgumentProto(
        name = name,
        int_value = value,
    )

public fun PodcaActionArgumentDouble(
    name: String,
    value: Double,
): ActionArgumentProto =
    ActionArgumentProto(
        name = name,
        double_value = value,
    )

public fun PodcaActionArgumentBool(
    name: String,
    value: Boolean,
): ActionArgumentProto =
    ActionArgumentProto(
        name = name,
        bool_value = value,
    )
