package com.podca.sdui.player.engine

import com.podca.sdui.protocol.core.ActionArgumentProto
import com.podca.sdui.protocol.core.ActionBindingProto
import com.podca.sdui.protocol.core.ActionResultProto
import com.podca.sdui.protocol.core.ActionTriggerProto
import com.podca.sdui.protocol.core.ClientEventProto
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlin.time.Clock

/**
 * Small orchestration layer for a Podca document.
 *
 * Responsibilities:
 * - keep the latest document in memory
 * - resolve a node by key
 * - resolve an action binding by trigger
 * - turn that binding into a client event
 * - dispatch to the registered local handler
 * - apply server-provided patches back into the store
 */
public class PodcaDocumentController(
    private val actionDispatcher: PodcaActionDispatcher,
    private val stateStore: PodcaStateStore = PodcaStateStore(),
) {
    public val document: StateFlow<PodcaDocumentNode?> = stateStore.document
    public val revision: StateFlow<Long> = stateStore.revision

    public fun setDocument(
        document: PodcaDocumentNode?,
        revision: Long = this.revision.value,
    ) {
        stateStore.setDocument(document = document, revision = revision)
    }

    public fun loadDocument(
        bytes: ByteArray,
        revision: Long = this.revision.value,
    ) {
        setDocument(
            document = decodePodcaDocument(bytes),
            revision = revision,
        )
    }

    public suspend fun dispatch(
        nodeKey: String,
        trigger: ActionTriggerProto = ActionTriggerProto.ACTION_TRIGGER_CLICK,
        arguments: List<ActionArgumentProto> = emptyList(),
    ): ActionResultProto {
        val currentDocument = document.value
            ?: return PodcaRejectedActionResult(
                eventId = PodcaEventId(
                    nodeKey = nodeKey,
                    actionId = "",
                    trigger = trigger,
                ),
                errorMessage = "No Podca document is loaded.",
            )

        val node = currentDocument.findByKey(nodeKey)
            ?: return PodcaRejectedActionResult(
                eventId = PodcaEventId(
                    nodeKey = nodeKey,
                    actionId = "",
                    trigger = trigger,
                ),
                errorMessage = "No Podca node found for key=$nodeKey.",
            )

        val action = node.findAction(trigger)
            ?: return PodcaRejectedActionResult(
                eventId = PodcaEventId(
                    nodeKey = nodeKey,
                    actionId = "",
                    trigger = trigger,
                ),
                errorMessage = "No Podca action binding found for key=$nodeKey and trigger=$trigger.",
            )

        val clientTimestampEpochMillis = Clock.System.now().toEpochMilliseconds()
        val event = PodcaClientEvent(
            eventId = PodcaEventId(
                nodeKey = nodeKey,
                actionId = action.action_id,
                trigger = action.trigger,
                clientTimestampEpochMillis = clientTimestampEpochMillis,
            ),
            nodeKey = nodeKey,
            actionId = action.action_id,
            trigger = action.trigger,
            arguments = if (arguments.isEmpty()) action.arguments else arguments,
            clientTimestampEpochMillis = clientTimestampEpochMillis,
        )

        val result = actionDispatcher.dispatch(event)
        stateStore.applyActionResult(result)
        return result
    }

    /**
     * Dispatches the given [action] for [nodeKey] without requiring [PodcaDocumentNode.actions]
     * to contain the same binding (used by `remote.Node` payload interpreters, etc.).
     *
     * The document node must still exist so correlation and future policy stay consistent.
     */
    public suspend fun dispatch(
        nodeKey: String,
        action: ActionBindingProto,
    ): ActionResultProto {
        val currentDocument = document.value
            ?: return PodcaRejectedActionResult(
                eventId = PodcaEventId(
                    nodeKey = nodeKey,
                    actionId = action.action_id,
                    trigger = action.trigger,
                ),
                errorMessage = "No Podca document is loaded.",
            )

        currentDocument.findByKey(nodeKey)
            ?: return PodcaRejectedActionResult(
                eventId = PodcaEventId(
                    nodeKey = nodeKey,
                    actionId = action.action_id,
                    trigger = action.trigger,
                ),
                errorMessage = "No Podca node found for key=$nodeKey.",
            )

        val clientTimestampEpochMillis = Clock.System.now().toEpochMilliseconds()
        val event =
            PodcaClientEvent(
                eventId =
                    PodcaEventId(
                        nodeKey = nodeKey,
                        actionId = action.action_id,
                        trigger = action.trigger,
                        clientTimestampEpochMillis = clientTimestampEpochMillis,
                    ),
                nodeKey = nodeKey,
                action = action,
                clientTimestampEpochMillis = clientTimestampEpochMillis,
            )

        val result = actionDispatcher.dispatch(event)
        stateStore.applyActionResult(result)
        return result
    }

    public fun register(
        actionId: String,
        handler: PodcaActionHandler,
    ) {
        actionDispatcher.register(actionId = actionId, handler = handler)
    }

    public fun unregister(actionId: String) {
        actionDispatcher.unregister(actionId)
    }

    private fun PodcaDocumentNode.findAction(
        trigger: ActionTriggerProto,
    ): ActionBindingProto? =
        actions.firstOrNull { action ->
            action.trigger == trigger ||
                action.trigger == ActionTriggerProto.ACTION_TRIGGER_UNSPECIFIED
        }
}

public class PodcaRuntime(
    private val controller: PodcaDocumentController = PodcaDocumentController(
        actionDispatcher = PodcaActionDispatcher(),
    ),
) {
    public val document: StateFlow<PodcaDocumentNode?> = controller.document
    public val revision: StateFlow<Long> = controller.revision

    private val remoteCanvasConditionalFloatsMutable =
        MutableStateFlow<Map<String, Float>>(emptyMap())

    private val remoteCanvasStateLayoutActiveIdMutable = MutableStateFlow<Int?>(null)

    /**
     * Named floats for `remote.CanvasProgram` **CONDITIONAL_BEGIN** when [com.podca.sdui.remote.core.RemoteCanvasOpProto.conditional_remote_float_id_a] / `_b` are set.
     * Host updates this map (e.g. after actions); the canvas player reads it during composition.
     */
    public val remoteCanvasConditionalFloats: StateFlow<Map<String, Float>> =
        remoteCanvasConditionalFloatsMutable.asStateFlow()

    /**
     * Active branch for **STATE_LAYOUT_BEGIN** / **STATE_LAYOUT_END** spans (`null` = use [com.podca.sdui.remote.core.resolveRemoteCanvasStateLayoutActiveId] defaults on the decoded op list).
     */
    public val remoteCanvasStateLayoutActiveId: StateFlow<Int?> =
        remoteCanvasStateLayoutActiveIdMutable.asStateFlow()

    public fun putRemoteCanvasConditionalFloat(
        id: String,
        value: Float,
    ) {
        if (id.isBlank()) return
        remoteCanvasConditionalFloatsMutable.update { it + (id to value) }
    }

    public fun removeRemoteCanvasConditionalFloat(id: String) {
        if (id.isBlank()) return
        remoteCanvasConditionalFloatsMutable.update { it - id }
    }

    public fun setRemoteCanvasStateLayoutActiveId(id: Int?) {
        remoteCanvasStateLayoutActiveIdMutable.value = id
    }

    public fun setDocument(
        document: PodcaDocumentNode?,
        revision: Long = this.revision.value,
    ) {
        controller.setDocument(document = document, revision = revision)
    }

    public fun loadDocument(
        bytes: ByteArray,
        revision: Long = this.revision.value,
    ) {
        controller.loadDocument(bytes = bytes, revision = revision)
    }

    public suspend fun dispatch(
        nodeKey: String,
        trigger: ActionTriggerProto = ActionTriggerProto.ACTION_TRIGGER_CLICK,
        arguments: List<ActionArgumentProto> = emptyList(),
    ): ActionResultProto =
        controller.dispatch(
            nodeKey = nodeKey,
            trigger = trigger,
            arguments = arguments,
        )

    /**
     * Dispatches [action] for an existing document node without requiring that binding
     * to appear in [PodcaDocumentNode.actions] (e.g. `remote.Node` payload interpreters).
     */
    public suspend fun dispatch(
        nodeKey: String,
        action: ActionBindingProto,
    ): ActionResultProto =
        controller.dispatch(nodeKey = nodeKey, action = action)

    public fun register(
        actionId: String,
        handler: PodcaActionHandler,
    ) {
        controller.register(actionId = actionId, handler = handler)
    }

    public fun unregister(actionId: String) {
        controller.unregister(actionId)
    }
}

/**
 * Builds a stable client-side event identifier for correlation and debugging.
 *
 * The identifier combines the node key, action id, trigger name, and client timestamp.
 */
public fun PodcaEventId(
    nodeKey: String,
    actionId: String,
    trigger: ActionTriggerProto,
    clientTimestampEpochMillis: Long = Clock.System.now().toEpochMilliseconds(),
): String =
    buildString {
        append(nodeKey)
        append(':')
        append(actionId)
        append(':')
        append(trigger.name)
        append(':')
        append(clientTimestampEpochMillis)
    }
