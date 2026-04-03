package com.podca.sdui.player.ui.foundation

import com.podca.sdui.player.engine.PodcaDocumentNode
import com.podca.sdui.protocol.foundation.layout.FlexAlignSelfProto
import com.podca.sdui.protocol.foundation.layout.FlexBasisProto
import com.podca.sdui.protocol.foundation.layout.FlexBoxScopeProto
import com.podca.sdui.protocol.foundation.layout.FlexConfigProto
import com.podca.sdui.protocol.foundation.layout.GridItemProto
import com.podca.sdui.protocol.foundation.layout.GridScopeProto
import com.podca.sdui.protocol.ui.alignment.AlignmentPresetProto
import com.podca.sdui.protocol.ui.alignment.AlignmentProto
import com.podca.sdui.protocol.ui.unit.DpProto
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import okio.ByteString.Companion.toByteString

class PodcaFoundationRendererScopeMetadataTest {
    @Test
    fun grid_scope_payload_decodes_into_metadata() {
        val payload = GridScopeProto.ADAPTER.encode(
            GridScopeProto(
                grid_item = GridItemProto(
                    row = 2,
                    column = 3,
                    row_span = 4,
                    column_span = 5,
                    alignment = AlignmentProto(preset = AlignmentPresetProto.CENTER_END),
                ),
            ),
        )
        val node = PodcaDocumentNode(
            type = "foundation.layout.GridScope",
            payload = payload.toByteString(),
            key = "grid-scope",
            actions = emptyList(),
            children = emptyList(),
        )

        val metadata = node.gridScopePlacementMetadata()

        assertEquals(2, metadata.row)
        assertEquals(3, metadata.column)
        assertEquals(4, metadata.rowSpan)
        assertEquals(5, metadata.columnSpan)
        assertEquals(AlignmentPresetProto.CENTER_END, GridScopeProto.ADAPTER.decode(payload).grid_item?.alignment?.preset)
        assertNotNull(metadata.alignment)
    }

    @Test
    fun flex_scope_payload_decodes_into_metadata() {
        val payload = FlexBoxScopeProto.ADAPTER.encode(
            FlexBoxScopeProto(
                flex = FlexConfigProto(
                    order = 7,
                    grow = 1.5f,
                    shrink = 0.25f,
                    basis = FlexBasisProto(dp = DpProto(value_ = 24f)),
                    align_self = FlexAlignSelfProto.FLEX_ALIGN_SELF_END,
                ),
            ),
        )
        val node = PodcaDocumentNode(
            type = "foundation.layout.FlexBoxScope",
            payload = payload.toByteString(),
            key = "flex-scope",
            actions = emptyList(),
            children = emptyList(),
        )

        val metadata = node.flexScopePlacementMetadata()

        assertEquals(7, metadata.order)
        assertEquals(1.5f, metadata.grow)
        assertEquals(0.25f, metadata.shrink)
        assertNotNull(metadata.basis)
        assertEquals(FlexAlignSelfProto.FLEX_ALIGN_SELF_END, metadata.alignSelf)
    }
}
