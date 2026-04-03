package com.podca.sdui.studio.ui.foundation.layout

import com.podca.sdui.protocol.core.ActionTriggerProto
import com.podca.sdui.studio.core.PodcaActionBinding
import com.podca.sdui.studio.core.PodcaNode
import kotlin.test.Test
import kotlin.test.assertEquals

class PodcaFoundationEncodingTest {
    @Test
    fun row_node_encodes_key_and_actions() {
        val row = PodcaNode(
            type = "foundation.layout.Row",
            key = "row-root",
            actions = listOf(
                PodcaActionBinding(
                    actionId = "layout.row.tap",
                    trigger = ActionTriggerProto.ACTION_TRIGGER_CLICK,
                ),
            ),
        )

        val proto = row.toProto()

        assertEquals("foundation.layout.Row", proto.type)
        assertEquals("row-root", proto.key)
        assertEquals(1, proto.actions.size)
        assertEquals("layout.row.tap", proto.actions.single().action_id)
    }
}
