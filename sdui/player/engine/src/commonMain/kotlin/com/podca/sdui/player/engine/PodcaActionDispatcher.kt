package com.podca.sdui.player.engine

import com.podca.sdui.protocol.core.ActionResultProto
import com.podca.sdui.protocol.core.ClientEventProto
import com.podca.sdui.protocol.core.StatePatchProto

public fun interface PodcaActionHandler {
    public suspend fun handle(event: ClientEventProto): ActionResultProto
}

/**
 * Routes a client event to a registered local action handler.
 *
 * If no handler is registered for the incoming `action_id`, the dispatcher returns
 * a rejected [ActionResultProto] instead of throwing.
 */
public class PodcaActionDispatcher {
    private val handlers: MutableMap<String, PodcaActionHandler> = mutableMapOf()

    public fun register(
        actionId: String,
        handler: PodcaActionHandler,
    ) {
        handlers[actionId] = handler
    }

    public fun unregister(actionId: String) {
        handlers.remove(actionId)
    }

    public suspend fun dispatch(event: ClientEventProto): ActionResultProto {
        val handler = handlers[event.action_id]
            ?: return ActionResultProto(
                event_id = event.event_id,
                accepted = false,
                error_message = "No PodcaActionHandler registered for action_id=${event.action_id}",
            )

        return handler.handle(event)
    }
}

public fun PodcaAcceptedActionResult(
    eventId: String,
    statePatch: StatePatchProto? = null,
): ActionResultProto =
    ActionResultProto(
        event_id = eventId,
        accepted = true,
        state_patch = statePatch,
    )

public fun PodcaRejectedActionResult(
    eventId: String,
    errorMessage: String,
): ActionResultProto =
    ActionResultProto(
        event_id = eventId,
        accepted = false,
        error_message = errorMessage,
    )
