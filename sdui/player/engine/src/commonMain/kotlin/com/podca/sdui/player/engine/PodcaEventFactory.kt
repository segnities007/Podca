package com.podca.sdui.player.engine

import com.podca.sdui.protocol.core.ActionArgumentProto
import com.podca.sdui.protocol.core.ActionBindingProto
import com.podca.sdui.protocol.core.ActionTriggerProto
import com.podca.sdui.protocol.core.ClientEventProto
import kotlin.time.Clock

public fun PodcaClientEvent(
    eventId: String,
    nodeKey: String,
    actionId: String,
    trigger: ActionTriggerProto,
    arguments: List<ActionArgumentProto> = emptyList(),
    clientTimestampEpochMillis: Long = Clock.System.now().toEpochMilliseconds(),
): ClientEventProto =
    ClientEventProto(
        event_id = eventId,
        node_key = nodeKey,
        action_id = actionId,
        trigger = trigger,
        arguments = arguments,
        client_timestamp_epoch_millis = clientTimestampEpochMillis,
    )

public fun PodcaClientEvent(
    eventId: String,
    nodeKey: String,
    action: ActionBindingProto,
    clientTimestampEpochMillis: Long = Clock.System.now().toEpochMilliseconds(),
): ClientEventProto =
    PodcaClientEvent(
        eventId = eventId,
        nodeKey = nodeKey,
        actionId = action.action_id,
        trigger = action.trigger,
        arguments = action.arguments,
        clientTimestampEpochMillis = clientTimestampEpochMillis,
    )

public fun PodcaStringActionArgument(
    name: String,
    value: String,
): ActionArgumentProto =
    ActionArgumentProto(
        name = name,
        string_value = value,
    )

public fun PodcaIntActionArgument(
    name: String,
    value: Long,
): ActionArgumentProto =
    ActionArgumentProto(
        name = name,
        int_value = value,
    )

public fun PodcaDoubleActionArgument(
    name: String,
    value: Double,
): ActionArgumentProto =
    ActionArgumentProto(
        name = name,
        double_value = value,
    )

public fun PodcaBoolActionArgument(
    name: String,
    value: Boolean,
): ActionArgumentProto =
    ActionArgumentProto(
        name = name,
        bool_value = value,
    )
