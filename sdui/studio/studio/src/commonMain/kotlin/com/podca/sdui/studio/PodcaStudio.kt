package com.podca.sdui.studio

import androidx.compose.runtime.Composable
import com.podca.sdui.studio.core.PodcaNode
import com.podca.sdui.studio.core.encodePodcaTree
import com.podca.sdui.studio.core.renderPodcaTree

public object PodcaStudio {
    public suspend fun render(content: @Composable () -> Unit): PodcaNode =
        renderPodcaTree(content)

    public suspend fun encode(content: @Composable () -> Unit): ByteArray =
        encodePodcaTree(content)
}
