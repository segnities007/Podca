package com.podca.sdui.studio.ui.material3

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.material3.SearchBarColorsProto
import com.podca.sdui.protocol.material3.SearchBarProto
import com.podca.sdui.protocol.material3.SearchBarValueProto
import com.podca.sdui.studio.core.PodcaNode
import com.podca.sdui.studio.ui.core.PodcaModifier

@Composable
public fun PodcaSearchBar(
    text: String,
    value: SearchBarValueProto,
    modifier: PodcaModifier = PodcaModifier.Empty,
    enabled: Boolean = true,
    colors: SearchBarColorsProto? = null,
    content: @Composable () -> Unit,
) {
    PodcaNode(
        type = "material3.SearchBar",
        message = SearchBarProto(
            modifier = modifier.toProto(),
            value_ = value,
            text = text,
            enabled = enabled,
            colors = colors,
            has_content = true,
        ),
        encode = SearchBarProto.ADAPTER::encode,
        content = content,
    )
}
