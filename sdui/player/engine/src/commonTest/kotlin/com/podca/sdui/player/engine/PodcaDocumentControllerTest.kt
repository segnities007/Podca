package com.podca.sdui.player.engine

import com.podca.sdui.protocol.core.ActionBindingProto
import com.podca.sdui.protocol.core.ActionResultProto
import com.podca.sdui.protocol.core.ActionTriggerProto
import com.podca.sdui.protocol.core.NodeProto
import com.podca.sdui.protocol.core.StatePatchProto
import okio.ByteString.Companion.encodeUtf8
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue
import kotlinx.coroutines.runBlocking

class PodcaDocumentControllerTest {
    @Test
    fun event_id_is_stable_for_fixed_timestamp() {
        val eventId = PodcaEventId(
            nodeKey = "node-1",
            actionId = "action.play",
            trigger = ActionTriggerProto.ACTION_TRIGGER_CLICK,
            clientTimestampEpochMillis = 42L,
        )

        assertEquals("node-1:action.play:ACTION_TRIGGER_CLICK:42", eventId)
    }

    @Test
    fun dispatch_applies_state_patch_from_handler() = runBlocking {
        val dispatcher = PodcaActionDispatcher()
        val controller = PodcaDocumentController(actionDispatcher = dispatcher)
        val root = NodeProto(
            type = "Root",
            payload = okio.ByteString.EMPTY,
            key = "root",
            actions = emptyList(),
            children = listOf(
                NodeProto(
                    type = "material3.Button",
                    payload = okio.ByteString.EMPTY,
                    key = "play-button",
                    actions = listOf(
                        ActionBindingProto(
                            trigger = ActionTriggerProto.ACTION_TRIGGER_CLICK,
                            action_id = "episode.play",
                            arguments = emptyList(),
                            expects_state_update = true,
                        ),
                    ),
                    children = emptyList(),
                ),
            ),
        )
        controller.setDocument(PodcaDocumentNode(root), revision = 7L)
        dispatcher.register("episode.play") { incoming ->
            val encodedRoot = NodeProto.ADAPTER.encode(
                NodeProto(
                    type = "Root",
                    payload = okio.ByteString.EMPTY,
                    key = "root-updated",
                    actions = emptyList(),
                    children = emptyList(),
                ),
            )
            val patch = StatePatchProto(
                encoded_root = okio.ByteString.of(*encodedRoot),
                revision = 8L,
            )
            ActionResultProto(
                event_id = incoming.event_id,
                accepted = true,
                state_patch = patch,
            )
        }

        val result = controller.dispatch("play-button")

        assertTrue(result.accepted)
        assertEquals(8L, controller.revision.value)
        assertEquals("root-updated", controller.document.value?.key)
    }

    @Test
    fun dispatch_returns_rejected_result_when_action_is_not_registered() = runBlocking {
        val controller = PodcaDocumentController(actionDispatcher = PodcaActionDispatcher())
        controller.setDocument(
            PodcaDocumentNode(
                NodeProto(
                    type = "Root",
                    payload = okio.ByteString.EMPTY,
                    key = "root",
                    actions = listOf(
                        ActionBindingProto(
                            trigger = ActionTriggerProto.ACTION_TRIGGER_CLICK,
                            action_id = "missing.handler",
                        ),
                    ),
                    children = emptyList(),
                ),
            ),
            revision = 3L,
        )

        val result = controller.dispatch("root")

        assertFalse(result.accepted)
        assertTrue(result.error_message.contains("missing.handler"))
        assertEquals(3L, controller.revision.value)
        assertEquals("root", controller.document.value?.key)
    }

    @Test
    fun decode_document_finds_nested_node_by_key() {
        val bytes = NodeProto(
            type = "Root",
            payload = okio.ByteString.EMPTY,
            key = "root",
            actions = emptyList(),
            children = listOf(
                NodeProto(
                    type = "material3.Text",
                    payload = "child".encodeUtf8(),
                    key = "child",
                    actions = emptyList(),
                    slot = "headline",
                    children = emptyList(),
                ),
            ),
        ).let(NodeProto.ADAPTER::encode)

        val document = decodePodcaDocument(bytes)

        assertEquals("Root", document.type)
        val child = assertNotNull(document.findByKey("child"))
        assertEquals("headline", child.slot)
        assertNull(document.findByKey("missing"))
    }
}
