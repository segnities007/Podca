package com.podca.sdui.studio.ui.core

import com.podca.sdui.protocol.ui.AlignmentBridgeProto
import com.podca.sdui.protocol.ui.HorizontalAlignmentBridgeProto
import com.podca.sdui.protocol.ui.VerticalAlignmentBridgeProto
import com.podca.sdui.protocol.ui.alignment.AlignmentPresetProto
import com.podca.sdui.protocol.ui.alignment.AlignmentProto
import com.podca.sdui.protocol.ui.alignment.BiasAbsoluteAlignmentProto
import com.podca.sdui.protocol.ui.alignment.BiasAlignmentProto
import com.podca.sdui.protocol.ui.alignment.BiasHorizontalAlignmentProto
import com.podca.sdui.protocol.ui.alignment.BiasVerticalAlignmentProto
import com.podca.sdui.protocol.ui.alignment.HorizontalAlignmentPresetProto
import com.podca.sdui.protocol.ui.alignment.HorizontalAlignmentProto
import com.podca.sdui.protocol.ui.alignment.VerticalAlignmentPresetProto
import com.podca.sdui.protocol.ui.alignment.VerticalAlignmentProto

public fun PodcaAlignment(
    preset: AlignmentPresetProto = AlignmentPresetProto.CENTER,
): AlignmentBridgeProto =
    AlignmentBridgeProto(
        alignment = AlignmentProto(
            preset = preset,
        ),
    )

public fun PodcaBiasAlignment(
    horizontalBias: Float,
    verticalBias: Float,
): AlignmentBridgeProto =
    AlignmentBridgeProto(
        alignment = AlignmentProto(
            bias = BiasAlignmentProto(
                horizontal_bias = horizontalBias,
                vertical_bias = verticalBias,
            ),
        ),
    )

public fun PodcaBiasAbsoluteAlignment(
    horizontalBias: Float,
    verticalBias: Float,
): AlignmentBridgeProto =
    AlignmentBridgeProto(
        alignment = AlignmentProto(
            absolute_bias = BiasAbsoluteAlignmentProto(
                horizontal_bias = horizontalBias,
                vertical_bias = verticalBias,
            ),
        ),
    )

public fun PodcaHorizontalAlignment(
    preset: HorizontalAlignmentPresetProto = HorizontalAlignmentPresetProto.CENTER_HORIZONTALLY,
): HorizontalAlignmentBridgeProto =
    HorizontalAlignmentBridgeProto(
        alignment = HorizontalAlignmentProto(
            preset = preset,
        ),
    )

public fun PodcaBiasHorizontalAlignment(
    bias: Float,
): HorizontalAlignmentBridgeProto =
    HorizontalAlignmentBridgeProto(
        alignment = HorizontalAlignmentProto(
            bias = BiasHorizontalAlignmentProto(
                bias = bias,
            ),
        ),
    )

public fun PodcaVerticalAlignment(
    preset: VerticalAlignmentPresetProto = VerticalAlignmentPresetProto.CENTER_VERTICALLY,
): VerticalAlignmentBridgeProto =
    VerticalAlignmentBridgeProto(
        alignment = VerticalAlignmentProto(
            preset = preset,
        ),
    )

public fun PodcaBiasVerticalAlignment(
    bias: Float,
): VerticalAlignmentBridgeProto =
    VerticalAlignmentBridgeProto(
        alignment = VerticalAlignmentProto(
            bias = BiasVerticalAlignmentProto(
                bias = bias,
            ),
        ),
    )
