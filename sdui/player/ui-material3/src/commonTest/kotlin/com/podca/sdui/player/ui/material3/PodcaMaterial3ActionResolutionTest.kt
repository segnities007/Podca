package com.podca.sdui.player.ui.material3

import com.podca.sdui.player.engine.PodcaDocumentNode
import com.podca.sdui.protocol.core.ActionBindingProto
import com.podca.sdui.protocol.core.ActionTriggerProto
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull
import okio.ByteString.Companion.EMPTY

class PodcaMaterial3ActionResolutionTest {
    @Test
    fun resolve_action_prefers_exact_trigger_match() {
        val node = PodcaDocumentNode(
            type = "material3.Snackbar",
            payload = EMPTY,
            key = "snackbar-1",
            actions = listOf(
                ActionBindingProto(
                    trigger = ActionTriggerProto.ACTION_TRIGGER_UNSPECIFIED,
                    action_id = "fallback",
                ),
                ActionBindingProto(
                    trigger = ActionTriggerProto.ACTION_TRIGGER_DISMISS,
                    action_id = "dismiss",
                ),
            ),
            children = emptyList(),
        )

        val action = node.resolveAction(ActionTriggerProto.ACTION_TRIGGER_DISMISS)

        assertEquals("dismiss", action?.action_id)
    }

    @Test
    fun resolve_action_uses_unspecified_binding_as_fallback() {
        val node = PodcaDocumentNode(
            type = "material3.AlertDialog",
            payload = EMPTY,
            key = "dialog-1",
            actions = listOf(
                ActionBindingProto(
                    trigger = ActionTriggerProto.ACTION_TRIGGER_UNSPECIFIED,
                    action_id = "fallback",
                ),
            ),
            children = emptyList(),
        )

        val action = node.resolveAction(ActionTriggerProto.ACTION_TRIGGER_CLICK)

        assertEquals("fallback", action?.action_id)
    }

    @Test
    fun resolve_action_returns_null_when_no_binding_matches() {
        val node = PodcaDocumentNode(
            type = "material3.AlertDialog",
            payload = EMPTY,
            key = "dialog-1",
            actions = emptyList(),
            children = emptyList(),
        )

        val action = node.resolveAction(ActionTriggerProto.ACTION_TRIGGER_DISMISS)

        assertNull(action)
    }
}
