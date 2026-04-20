package com.podca.sdui.remote.creation

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.core.ActionBindingProto
import com.podca.sdui.remote.core.RemoteAlignmentCrossAxisProto
import com.podca.sdui.remote.core.RemoteArrangementMainAxisProto
import com.podca.sdui.remote.core.RemoteKindProto
import com.podca.sdui.remote.core.RemoteNodeProto
import com.podca.sdui.remote.core.RemotePaddingProto
import com.podca.sdui.studio.core.PodcaNode

public fun remotePadding(
    start: Float = 0f,
    top: Float = 0f,
    end: Float = 0f,
    bottom: Float = 0f,
): RemotePaddingProto =
    RemotePaddingProto(
        start = start,
        top = top,
        end = end,
        bottom = bottom,
    )

public fun remoteText(
    text: String,
    fontSize: Float = 0f,
    fontWeight: Float = 0f,
    textColorArgb: Int = 0,
    padding: RemotePaddingProto? = null,
): RemoteNodeProto =
    RemoteNodeProto(
        kind = RemoteKindProto.REMOTE_KIND_TEXT,
        text = text,
        font_size = fontSize,
        font_weight = fontWeight,
        text_color_argb = textColorArgb,
        padding = padding,
    )

public fun remoteSpacer(heightDp: Float = 0f, widthDp: Float = 0f): RemoteNodeProto =
    RemoteNodeProto(
        kind = RemoteKindProto.REMOTE_KIND_SPACER,
        spacer_height = heightDp,
        spacer_width = widthDp,
    )

/**
 * [children] must follow **named** arrangement parameters (e.g. [horizontalCross] = …)
 * so the first child is not mistaken for [horizontalCross].
 */
public fun remoteColumn(
    padding: RemotePaddingProto? = null,
    verticalMain: RemoteArrangementMainAxisProto =
        RemoteArrangementMainAxisProto.REMOTE_MAIN_AXIS_UNSPECIFIED,
    verticalSpacingDp: Float = 0f,
    horizontalCross: RemoteAlignmentCrossAxisProto =
        RemoteAlignmentCrossAxisProto.REMOTE_CROSS_AXIS_UNSPECIFIED,
    vararg children: RemoteNodeProto,
): RemoteNodeProto =
    RemoteNodeProto(
        kind = RemoteKindProto.REMOTE_KIND_COLUMN,
        children = children.toList(),
        padding = padding,
        column_vertical_main = verticalMain,
        column_vertical_spacing_dp = verticalSpacingDp,
        column_horizontal_cross = horizontalCross,
    )

/** See [remoteColumn] for vararg vs named-parameter ordering. */
public fun remoteRow(
    padding: RemotePaddingProto? = null,
    horizontalMain: RemoteArrangementMainAxisProto =
        RemoteArrangementMainAxisProto.REMOTE_MAIN_AXIS_UNSPECIFIED,
    horizontalSpacingDp: Float = 0f,
    verticalCross: RemoteAlignmentCrossAxisProto =
        RemoteAlignmentCrossAxisProto.REMOTE_CROSS_AXIS_UNSPECIFIED,
    vararg children: RemoteNodeProto,
): RemoteNodeProto =
    RemoteNodeProto(
        kind = RemoteKindProto.REMOTE_KIND_ROW,
        children = children.toList(),
        padding = padding,
        row_horizontal_main = horizontalMain,
        row_horizontal_spacing_dp = horizontalSpacingDp,
        row_vertical_cross = verticalCross,
    )

public fun remoteBox(
    padding: RemotePaddingProto? = null,
    vararg children: RemoteNodeProto,
): RemoteNodeProto =
    RemoteNodeProto(
        kind = RemoteKindProto.REMOTE_KIND_BOX,
        children = children.toList(),
        padding = padding,
    )

/**
 * Wraps a single child; tap dispatches [actionId] on the host `remote.Node` key via [PodcaRuntime.dispatch].
 * The host [PodcaRemoteNode] should list the same `action_id` in [PodcaRemoteNode.actions] so the wire tree stays self-describing.
 */
public fun remoteClickable(
    actionId: String,
    child: RemoteNodeProto,
): RemoteNodeProto =
    RemoteNodeProto(
        kind = RemoteKindProto.REMOTE_KIND_CLICKABLE,
        click_action_id = actionId,
        children = listOf(child),
    )

/** One scrollable column; [maxHeightDp] caps viewport height (must be greater than 0 for a bounded viewport). */
public fun remoteVerticalScroll(
    maxHeightDp: Float,
    child: RemoteNodeProto,
    padding: RemotePaddingProto? = null,
    fillMaxWidth: Boolean = false,
    fillMaxHeight: Boolean = false,
): RemoteNodeProto =
    RemoteNodeProto(
        kind = RemoteKindProto.REMOTE_KIND_VERTICAL_SCROLL,
        padding = padding,
        vertical_scroll_max_height_dp = maxHeightDp,
        fill_max_width = fillMaxWidth,
        fill_max_height = fillMaxHeight,
        children = listOf(child),
    )

/** Use inside [remoteColumn] / [remoteRow] when the child should consume flexible space along the main axis. */
public fun RemoteNodeProto.withLayoutWeight(weight: Float): RemoteNodeProto =
    copy(layout_weight = weight)

public fun RemoteNodeProto.withFillMax(
    fillMaxWidth: Boolean = false,
    fillMaxHeight: Boolean = false,
): RemoteNodeProto =
    copy(
        fill_max_width = fillMaxWidth,
        fill_max_height = fillMaxHeight,
    )

/**
 * Embeds a compact [RemoteNodeProto] subtree in the document as type `remote.Node`.
 * Playback lives in `:sdui:remote:remote-player-compose` (`PodcaRenderRemoteDocumentNode`).
 */
@Composable
public fun PodcaRemoteNode(
    root: RemoteNodeProto,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
) {
    PodcaNode(
        type = "remote.Node",
        message = root,
        encode = RemoteNodeProto.ADAPTER::encode,
        key = key,
        actions = actions,
    )
}
