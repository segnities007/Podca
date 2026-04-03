package com.podca.sdui.player.ui.material3

import com.podca.sdui.player.engine.PodcaDocumentNode
import kotlin.test.Test
import kotlin.test.assertEquals
import okio.ByteString.Companion.EMPTY

class PodcaMaterial3DateContentResolutionTest {
    @Test
    fun resolve_date_content_prefers_children_for_date_input() {
        val node = nodeWithChild("material3.DateInput")

        val resolution = resolveDateContent(
            node = node,
            placeholder = "DateInput",
        )

        assertEquals(DateContentResolution.Children, resolution)
    }

    @Test
    fun resolve_date_content_prefers_children_for_date_range_input() {
        val node = nodeWithChild("material3.DateRangeInput")

        val resolution = resolveDateContent(
            node = node,
            placeholder = "DateRangeInput",
        )

        assertEquals(DateContentResolution.Children, resolution)
    }

    @Test
    fun resolve_date_content_prefers_children_for_date_picker_dialog() {
        val node = nodeWithChild("material3.DatePickerDialog")

        val resolution = resolveDateContent(
            node = node,
            placeholder = "DatePickerDialog",
        )

        assertEquals(DateContentResolution.Children, resolution)
    }

    @Test
    fun resolve_date_content_uses_placeholder_when_children_are_missing() {
        val node = PodcaDocumentNode(
            type = "material3.DateInput",
            payload = EMPTY,
            key = "",
            actions = emptyList(),
            children = emptyList(),
        )

        val resolution = resolveDateContent(
            node = node,
            placeholder = "DateInput",
        )

        assertEquals(DateContentResolution.Placeholder("DateInput"), resolution)
    }

    private fun nodeWithChild(type: String) =
        PodcaDocumentNode(
            type = type,
            payload = EMPTY,
            key = "",
            actions = emptyList(),
            children = listOf(
                PodcaDocumentNode(
                    type = "material3.Label",
                    payload = EMPTY,
                    key = "",
                    actions = emptyList(),
                    children = emptyList(),
                ),
            ),
        )
}
