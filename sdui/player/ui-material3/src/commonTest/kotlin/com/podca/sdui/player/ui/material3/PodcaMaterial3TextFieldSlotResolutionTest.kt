package com.podca.sdui.player.ui.material3

import com.podca.sdui.player.engine.PodcaDocumentNode
import kotlin.test.Test
import kotlin.test.assertEquals
import okio.ByteString.Companion.EMPTY

class PodcaMaterial3TextFieldSlotResolutionTest {
    @Test
    fun resolve_text_field_slot_content_prefers_slot_over_fallback_string() {
        val node = PodcaDocumentNode(
            type = "material3.TextField",
            payload = EMPTY,
            key = "",
            actions = emptyList(),
            children = listOf(
                PodcaDocumentNode(
                    type = "material3.TextField.LabelSlot",
                    payload = EMPTY,
                    key = "",
                    actions = emptyList(),
                    children = emptyList(),
                    slot = "label",
                ),
            ),
        )

        val resolution = resolveTextFieldSlotContent(
            node = node,
            slot = "label",
            fallback = "Fallback label",
        )

        assertEquals(TextFieldSlotContentResolution.Slot("label"), resolution)
    }

    @Test
    fun resolve_text_field_slot_content_uses_string_fallback_when_slot_missing() {
        val node = PodcaDocumentNode(
            type = "material3.TextField",
            payload = EMPTY,
            key = "",
            actions = emptyList(),
            children = emptyList(),
        )

        val resolution = resolveTextFieldSlotContent(
            node = node,
            slot = "placeholder",
            fallback = "Fallback placeholder",
        )

        assertEquals(TextFieldSlotContentResolution.StringValue("Fallback placeholder"), resolution)
    }

    @Test
    fun resolve_text_field_slot_content_returns_empty_when_slot_missing_and_fallback_blank() {
        val node = PodcaDocumentNode(
            type = "material3.TextField",
            payload = EMPTY,
            key = "",
            actions = emptyList(),
            children = emptyList(),
        )

        val resolution = resolveTextFieldSlotContent(
            node = node,
            slot = "supportingText",
            fallback = " ",
        )

        assertEquals(TextFieldSlotContentResolution.Empty, resolution)
    }
}
