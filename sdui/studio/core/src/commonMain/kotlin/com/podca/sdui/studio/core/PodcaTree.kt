package com.podca.sdui.studio.core

import androidx.compose.runtime.AbstractApplier
import androidx.compose.runtime.BroadcastFrameClock
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ComposeNode
import androidx.compose.runtime.Composition
import androidx.compose.runtime.Recomposer
import com.podca.sdui.protocol.core.ActionBindingProto
import com.podca.sdui.protocol.core.NodeProto
import com.squareup.wire.Message
import kotlin.time.TimeSource
import kotlin.coroutines.coroutineContext
import kotlinx.coroutines.CoroutineStart
import kotlinx.coroutines.cancelAndJoin
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.launch
import okio.ByteString
import okio.ByteString.Companion.toByteString

public class PodcaNode(
    public var type: String,
    public var payload: ByteString = ByteString.EMPTY,
    public var key: String = "",
    public var actions: List<ActionBindingProto> = emptyList(),
    public var slot: String = "",
) {
    private val mutableChildren: MutableList<PodcaNode> = mutableListOf()

    public val children: List<PodcaNode>
        get() = mutableChildren

    internal fun insert(index: Int, child: PodcaNode) {
        mutableChildren.add(index, child)
    }

    internal fun remove(index: Int, count: Int) {
        repeat(count) {
            mutableChildren.removeAt(index)
        }
    }

    internal fun move(from: Int, to: Int, count: Int) {
        if (from == to || count == 0) return

        val moving = mutableListOf<PodcaNode>()
        repeat(count) {
            moving += mutableChildren.removeAt(from)
        }
        val destination = if (from < to) to - count else to
        mutableChildren.addAll(destination, moving)
    }

    internal fun clear() {
        mutableChildren.clear()
    }

    public fun toProto(): NodeProto =
        NodeProto(
            type = type,
            payload = payload,
            children = mutableChildren.map(PodcaNode::toProto),
            key = key,
            actions = actions,
            slot = slot,
        )
}

public class PodcaApplier(
    root: PodcaNode,
) : AbstractApplier<PodcaNode>(root) {
    override fun insertTopDown(index: Int, instance: PodcaNode) {
        current.insert(index, instance)
    }

    override fun insertBottomUp(index: Int, instance: PodcaNode) = Unit

    override fun remove(index: Int, count: Int) {
        current.remove(index, count)
    }

    override fun move(from: Int, to: Int, count: Int) {
        current.move(from, to, count)
    }

    override fun onClear() {
        root.clear()
    }
}

@Composable
public fun PodcaNode(
    type: String,
    payload: ByteString = ByteString.EMPTY,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
    slot: String = "",
    content: @Composable () -> Unit = {},
) {
    ComposeNode<PodcaNode, PodcaApplier>(
        factory = {
            PodcaNode(
                type = type,
                payload = payload,
                key = key,
                actions = actions,
                slot = slot,
            )
        },
        update = {
            set(type) { this.type = it }
            set(payload) { this.payload = it }
            set(key) { this.key = it }
            set(actions) { this.actions = it }
            set(slot) { this.slot = it }
        },
        content = content,
    )
}

@Composable
public fun <M : Message<*, *>> PodcaNode(
    type: String,
    message: M,
    encode: (M) -> ByteArray,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
    slot: String = "",
    content: @Composable () -> Unit = {},
) {
    PodcaNode(
        type = type,
        payload = encode(message).toByteString(),
        key = key,
        actions = actions,
        slot = slot,
        content = content,
    )
}

public suspend fun renderPodcaTree(content: @Composable () -> Unit): PodcaNode =
    coroutineScope {
        val root = PodcaNode(type = "Root")
        val applier = PodcaApplier(root)
        lateinit var frameClock: BroadcastFrameClock
        val start = TimeSource.Monotonic.markNow()
        frameClock = BroadcastFrameClock {
            launch {
                frameClock.sendFrame(start.elapsedNow().inWholeNanoseconds)
            }
        }
        val recomposer = Recomposer(coroutineContext + frameClock)
        val runner = launch(context = frameClock, start = CoroutineStart.UNDISPATCHED) {
            recomposer.runRecomposeAndApplyChanges()
        }
        val composition = Composition(applier = applier, parent = recomposer)

        try {
            composition.setContent(content)
            recomposer.awaitIdle()
            recomposer.close()
            runner.join()
            root
        } finally {
            composition.dispose()
            recomposer.cancel()
            runner.cancelAndJoin()
        }
    }

public suspend fun encodePodcaTree(content: @Composable () -> Unit): ByteArray =
    NodeProto.ADAPTER.encode(renderPodcaTree(content).toProto())
