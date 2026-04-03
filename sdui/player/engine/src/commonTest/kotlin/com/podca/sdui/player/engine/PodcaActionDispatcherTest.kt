package com.podca.sdui.player.engine

import com.podca.sdui.protocol.core.ActionResultProto
import com.podca.sdui.protocol.core.ActionTriggerProto
import com.podca.sdui.protocol.core.ClientEventProto
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue
import kotlinx.coroutines.runBlocking

class PodcaActionDispatcherTest {
    @Test
    fun dispatch_returns_rejected_result_when_handler_is_missing() = runBlocking {
        val dispatcher = PodcaActionDispatcher()
        val event = ClientEventProto(
            event_id = "event-1",
            node_key = "node-1",
            action_id = "action.missing",
            trigger = ActionTriggerProto.ACTION_TRIGGER_CLICK,
        )

        val result = dispatcher.dispatch(event)

        assertFalse(result.accepted)
        assertEquals("event-1", result.event_id)
        assertTrue(result.error_message.contains("action.missing"))
    }

    @Test
    fun dispatch_invokes_registered_handler() = runBlocking {
        val dispatcher = PodcaActionDispatcher()
        val event = ClientEventProto(
            event_id = "event-2",
            node_key = "node-2",
            action_id = "action.play",
            trigger = ActionTriggerProto.ACTION_TRIGGER_CLICK,
        )
        dispatcher.register("action.play") { incoming ->
            ActionResultProto(
                event_id = incoming.event_id,
                accepted = true,
            )
        }

        val result = dispatcher.dispatch(event)

        assertTrue(result.accepted)
        assertEquals("event-2", result.event_id)
    }
}
