package com.podca.sdui.studio.ui.foundation.layout

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.foundation.layout.AspectRatioModifierProto
import com.podca.sdui.protocol.foundation.layout.DefaultMinSizeModifierProto
import com.podca.sdui.protocol.foundation.layout.FillModifierProto
import com.podca.sdui.protocol.foundation.layout.OffsetModifierProto
import com.podca.sdui.protocol.foundation.layout.OffsetPxModifierProto
import com.podca.sdui.protocol.foundation.layout.PaddingValuesProto
import com.podca.sdui.protocol.foundation.layout.SizeDirectionProto
import com.podca.sdui.protocol.foundation.layout.SizeModifierProto
import com.podca.sdui.protocol.foundation.layout.WrapContentModifierProto
import com.podca.sdui.protocol.ui.AlignmentBridgeProto
import com.podca.sdui.protocol.ui.unit.DpProto
import com.podca.sdui.protocol.ui.unit.IntOffsetProto
import com.podca.sdui.studio.core.PodcaNode

@Composable
public fun PodcaAspectRatioModifier(
    aspectRatio: Float,
    matchHeightConstraintsFirst: Boolean = false,
) {
    PodcaNode(
        type = "foundation.layout.AspectRatioModifier",
        message = AspectRatioModifierProto(
            aspect_ratio = aspectRatio,
            match_height_constraints_first = matchHeightConstraintsFirst,
        ),
        encode = AspectRatioModifierProto.ADAPTER::encode,
    )
}

@Composable
public fun PodcaOffsetModifier(
    x: DpProto? = null,
    y: DpProto? = null,
    rtlAware: Boolean = true,
) {
    PodcaNode(
        type = "foundation.layout.OffsetModifier",
        message = OffsetModifierProto(
            x = x,
            y = y,
            rtl_aware = rtlAware,
        ),
        encode = OffsetModifierProto.ADAPTER::encode,
    )
}

@Composable
public fun PodcaOffsetPxModifier(
    offset: IntOffsetProto? = null,
    rtlAware: Boolean = true,
) {
    PodcaNode(
        type = "foundation.layout.OffsetPxModifier",
        message = OffsetPxModifierProto(
            offset = offset,
            rtl_aware = rtlAware,
        ),
        encode = OffsetPxModifierProto.ADAPTER::encode,
    )
}

@Composable
public fun PodcaPaddingValues(
    start: DpProto? = null,
    top: DpProto? = null,
    end: DpProto? = null,
    bottom: DpProto? = null,
    rtlAware: Boolean = true,
) {
    PodcaNode(
        type = "foundation.layout.PaddingValues",
        message = PaddingValuesProto(
            start = start,
            top = top,
            end = end,
            bottom = bottom,
            rtl_aware = rtlAware,
        ),
        encode = PaddingValuesProto.ADAPTER::encode,
    )
}

@Composable
public fun PodcaSizeModifier(
    minWidth: DpProto? = null,
    minHeight: DpProto? = null,
    maxWidth: DpProto? = null,
    maxHeight: DpProto? = null,
    enforceIncoming: Boolean = true,
) {
    PodcaNode(
        type = "foundation.layout.SizeModifier",
        message = SizeModifierProto(
            min_width = minWidth,
            min_height = minHeight,
            max_width = maxWidth,
            max_height = maxHeight,
            enforce_incoming = enforceIncoming,
        ),
        encode = SizeModifierProto.ADAPTER::encode,
    )
}

@Composable
public fun PodcaFillModifier(
    direction: SizeDirectionProto = SizeDirectionProto.SIZE_DIRECTION_HORIZONTAL,
    fraction: Float = 0f,
) {
    PodcaNode(
        type = "foundation.layout.FillModifier",
        message = FillModifierProto(
            direction = direction,
            fraction = fraction,
        ),
        encode = FillModifierProto.ADAPTER::encode,
    )
}

@Composable
public fun PodcaWrapContentModifier(
    align: AlignmentBridgeProto? = null,
    unbounded: Boolean = false,
) {
    PodcaNode(
        type = "foundation.layout.WrapContentModifier",
        message = WrapContentModifierProto(
            align = align,
            unbounded = unbounded,
        ),
        encode = WrapContentModifierProto.ADAPTER::encode,
    )
}

@Composable
public fun PodcaDefaultMinSizeModifier(
    minWidth: DpProto? = null,
    minHeight: DpProto? = null,
) {
    PodcaNode(
        type = "foundation.layout.DefaultMinSizeModifier",
        message = DefaultMinSizeModifierProto(
            min_width = minWidth,
            min_height = minHeight,
        ),
        encode = DefaultMinSizeModifierProto.ADAPTER::encode,
    )
}
