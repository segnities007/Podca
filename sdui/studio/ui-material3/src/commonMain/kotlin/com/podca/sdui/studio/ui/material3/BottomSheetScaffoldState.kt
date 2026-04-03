package com.podca.sdui.studio.ui.material3

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.material3.BottomSheetScaffoldStateProto
import com.podca.sdui.protocol.material3.SheetStateProto
import com.podca.sdui.protocol.material3.SnackbarHostStateProto
import com.podca.sdui.studio.core.PodcaNode

@Composable
public fun PodcaBottomSheetScaffoldState(
    bottomSheetState: SheetStateProto? = null,
    snackbarHostState: SnackbarHostStateProto? = null,
) {
    PodcaNode(
        type = "material3.BottomSheetScaffoldState",
        message = BottomSheetScaffoldStateProto(
            bottom_sheet_state = bottomSheetState,
            snackbar_host_state = snackbarHostState,
        ),
        encode = BottomSheetScaffoldStateProto.ADAPTER::encode,
    )
}
