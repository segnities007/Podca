package com.podca.sdui.studio.ui.foundation.layout

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.core.ActionBindingProto
import com.podca.sdui.protocol.foundation.layout.FlexAlignContentProto
import com.podca.sdui.protocol.foundation.layout.FlexAlignItemsProto
import com.podca.sdui.protocol.foundation.layout.FlexAlignSelfProto
import com.podca.sdui.protocol.foundation.layout.FlexBasisProto
import com.podca.sdui.protocol.foundation.layout.FlexBoxConfigProto
import com.podca.sdui.protocol.foundation.layout.FlexBoxProto
import com.podca.sdui.protocol.foundation.layout.FlexBoxScopeProto
import com.podca.sdui.protocol.foundation.layout.FlexConfigProto
import com.podca.sdui.protocol.foundation.layout.FlexDirectionProto
import com.podca.sdui.protocol.foundation.layout.FlexJustifyContentProto
import com.podca.sdui.protocol.foundation.layout.FlexWrapProto
import com.podca.sdui.protocol.ui.unit.DpProto
import com.podca.sdui.studio.core.PodcaNode
import com.podca.sdui.studio.ui.core.PodcaModifier

@Composable
public fun PodcaFlexBasis(
    dp: DpProto? = null,
    percent: Float? = null,
    auto: Boolean? = null,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
) {
    PodcaNode(
        type = "foundation.layout.FlexBasis",
        message = FlexBasisProto(
            dp = dp,
            percent = percent,
            auto = auto,
        ),
        key = key,
        actions = actions,
        encode = FlexBasisProto.ADAPTER::encode,
    )
}

@Composable
public fun PodcaFlexConfig(
    order: Int = 0,
    grow: Float = 0f,
    shrink: Float = 0f,
    basis: FlexBasisProto? = null,
    alignSelf: FlexAlignSelfProto = FlexAlignSelfProto.FLEX_ALIGN_SELF_AUTO,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
) {
    PodcaNode(
        type = "foundation.layout.FlexConfig",
        message = FlexConfigProto(
            order = order,
            grow = grow,
            shrink = shrink,
            basis = basis,
            align_self = alignSelf,
        ),
        key = key,
        actions = actions,
        encode = FlexConfigProto.ADAPTER::encode,
    )
}

@Composable
public fun PodcaFlexBoxConfig(
    direction: FlexDirectionProto = FlexDirectionProto.FLEX_DIRECTION_ROW,
    wrap: FlexWrapProto = FlexWrapProto.FLEX_WRAP_NOWRAP,
    justifyContent: FlexJustifyContentProto = FlexJustifyContentProto.FLEX_JUSTIFY_CONTENT_START,
    alignItems: FlexAlignItemsProto = FlexAlignItemsProto.FLEX_ALIGN_ITEMS_START,
    alignContent: FlexAlignContentProto = FlexAlignContentProto.FLEX_ALIGN_CONTENT_START,
    rowGap: DpProto? = null,
    columnGap: DpProto? = null,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
) {
    PodcaNode(
        type = "foundation.layout.FlexBoxConfig",
        message = FlexBoxConfigProto(
            direction = direction,
            wrap = wrap,
            justify_content = justifyContent,
            align_items = alignItems,
            align_content = alignContent,
            row_gap = rowGap,
            column_gap = columnGap,
        ),
        key = key,
        actions = actions,
        encode = FlexBoxConfigProto.ADAPTER::encode,
    )
}

@Composable
public fun PodcaFlexBoxScope(
    flex: FlexConfigProto? = null,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
    content: @Composable () -> Unit = {},
) {
    PodcaNode(
        type = "foundation.layout.FlexBoxScope",
        message = FlexBoxScopeProto(flex = flex),
        key = key,
        actions = actions,
        encode = FlexBoxScopeProto.ADAPTER::encode,
        content = content,
    )
}

@Composable
public fun PodcaFlexBox(
    config: FlexBoxConfigProto? = null,
    modifier: PodcaModifier = PodcaModifier.Empty,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
    content: @Composable () -> Unit,
) {
    PodcaNode(
        type = "foundation.layout.FlexBox",
        message = FlexBoxProto(
            modifier = modifier.toProto(),
            config = config,
        ),
        key = key,
        actions = actions,
        encode = FlexBoxProto.ADAPTER::encode,
        content = content,
    )
}
