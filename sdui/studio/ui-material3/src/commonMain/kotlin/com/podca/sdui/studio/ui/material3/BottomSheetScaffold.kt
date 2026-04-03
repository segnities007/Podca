package com.podca.sdui.studio.ui.material3

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.material3.BottomSheetScaffoldProto
import com.podca.sdui.protocol.material3.BottomSheetScaffoldStateProto
import com.podca.sdui.protocol.ui.unit.DpProto
import com.podca.sdui.studio.core.PodcaNode
import com.podca.sdui.studio.ui.core.PodcaModifier

@Composable
public fun PodcaBottomSheetScaffold(
    modifier: PodcaModifier = PodcaModifier.Empty,
    scaffoldState: BottomSheetScaffoldStateProto? = null,
    sheetPeekHeight: DpProto? = null,
    sheetGesturesEnabled: Boolean = true,
    sheetContent: @Composable () -> Unit = {},
    content: @Composable () -> Unit = {},
) {
    PodcaNode(
        type = "material3.BottomSheetScaffold",
        message = BottomSheetScaffoldProto(
            modifier = modifier.toProto(),
            bottom_sheet_scaffold_state = scaffoldState,
            sheet_peek_height = sheetPeekHeight,
            sheet_gestures_enabled = sheetGesturesEnabled,
            has_content = true,
            has_sheet_content = true,
        ),
        encode = BottomSheetScaffoldProto.ADAPTER::encode,
    ) {
        PodcaNode(
            type = "material3.BottomSheetScaffold.SheetContentSlot",
            slot = "sheetContent",
            content = sheetContent,
        )
        PodcaNode(
            type = "material3.BottomSheetScaffold.ContentSlot",
            slot = "content",
            content = content,
        )
    }
}
