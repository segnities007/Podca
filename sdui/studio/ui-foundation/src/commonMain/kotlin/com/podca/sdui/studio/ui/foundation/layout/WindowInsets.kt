package com.podca.sdui.studio.ui.foundation.layout

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.core.ActionBindingProto
import com.podca.sdui.protocol.foundation.layout.ConsumedInsetsModifierProto
import com.podca.sdui.protocol.foundation.layout.InsetsPaddingModifierProto
import com.podca.sdui.protocol.foundation.layout.PaddingValuesProto
import com.podca.sdui.protocol.foundation.layout.PaddingValuesConsumingModifierProto
import com.podca.sdui.protocol.foundation.layout.RecalculateWindowInsetsModifierProto
import com.podca.sdui.protocol.foundation.layout.UnionInsetsConsumingModifierProto
import com.podca.sdui.protocol.foundation.layout.WindowInsetsHeightModifierProto
import com.podca.sdui.protocol.foundation.layout.WindowInsetsProto
import com.podca.sdui.protocol.foundation.layout.WindowInsetsSidesProto
import com.podca.sdui.protocol.foundation.layout.WindowInsetsWidthModifierProto
import com.podca.sdui.studio.core.PodcaNode

@Composable
public fun PodcaWindowInsetsSides(
    mask: UInt = 0u,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
) {
    PodcaNode(
        type = "foundation.layout.WindowInsetsSides",
        message = WindowInsetsSidesProto(mask = mask.toInt()),
        key = key,
        actions = actions,
        encode = WindowInsetsSidesProto.ADAPTER::encode,
    )
}

@Composable
public fun PodcaWindowInsets(
    left: Int = 0,
    top: Int = 0,
    right: Int = 0,
    bottom: Int = 0,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
) {
    PodcaNode(
        type = "foundation.layout.WindowInsets",
        message = WindowInsetsProto(
            left = left,
            top = top,
            right = right,
            bottom = bottom,
        ),
        key = key,
        actions = actions,
        encode = WindowInsetsProto.ADAPTER::encode,
    )
}

@Composable
public fun PodcaInsetsPaddingModifier(
    insets: WindowInsetsProto? = null,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
) {
    PodcaNode(
        type = "foundation.layout.InsetsPaddingModifier",
        message = InsetsPaddingModifierProto(insets = insets),
        key = key,
        actions = actions,
        encode = InsetsPaddingModifierProto.ADAPTER::encode,
    )
}

@Composable
public fun PodcaPaddingValuesConsumingModifier(
    paddingValues: PaddingValuesProto? = null,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
) {
    PodcaNode(
        type = "foundation.layout.PaddingValuesConsumingModifier",
        message = PaddingValuesConsumingModifierProto(
            padding_values = paddingValues,
        ),
        key = key,
        actions = actions,
        encode = PaddingValuesConsumingModifierProto.ADAPTER::encode,
    )
}

@Composable
public fun PodcaConsumedInsetsModifier(
    consumed: WindowInsetsProto? = null,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
) {
    PodcaNode(
        type = "foundation.layout.ConsumedInsetsModifier",
        message = ConsumedInsetsModifierProto(consumed = consumed),
        key = key,
        actions = actions,
        encode = ConsumedInsetsModifierProto.ADAPTER::encode,
    )
}

@Composable
public fun PodcaUnionInsetsConsumingModifier(
    insets: WindowInsetsProto? = null,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
) {
    PodcaNode(
        type = "foundation.layout.UnionInsetsConsumingModifier",
        message = UnionInsetsConsumingModifierProto(insets = insets),
        key = key,
        actions = actions,
        encode = UnionInsetsConsumingModifierProto.ADAPTER::encode,
    )
}

@Composable
public fun PodcaRecalculateWindowInsetsModifier(
    forceRecalculation: Boolean = false,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
) {
    PodcaNode(
        type = "foundation.layout.RecalculateWindowInsetsModifier",
        message = RecalculateWindowInsetsModifierProto(
            force_recalculation = forceRecalculation,
        ),
        key = key,
        actions = actions,
        encode = RecalculateWindowInsetsModifierProto.ADAPTER::encode,
    )
}

@Composable
public fun PodcaWindowInsetsWidthModifier(
    insets: WindowInsetsProto? = null,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
) {
    PodcaNode(
        type = "foundation.layout.WindowInsetsWidthModifier",
        message = WindowInsetsWidthModifierProto(insets = insets),
        key = key,
        actions = actions,
        encode = WindowInsetsWidthModifierProto.ADAPTER::encode,
    )
}

@Composable
public fun PodcaWindowInsetsHeightModifier(
    insets: WindowInsetsProto? = null,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
) {
    PodcaNode(
        type = "foundation.layout.WindowInsetsHeightModifier",
        message = WindowInsetsHeightModifierProto(insets = insets),
        key = key,
        actions = actions,
        encode = WindowInsetsHeightModifierProto.ADAPTER::encode,
    )
}
