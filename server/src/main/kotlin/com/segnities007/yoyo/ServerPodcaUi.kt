package com.segnities007.yoyo

import androidx.compose.runtime.Composable
import com.podca.sdui.studio.PodcaStudio
import com.podca.sdui.studio.ui.foundation.layout.PodcaColumn
import com.podca.sdui.studio.ui.material3.PodcaText
import kotlinx.coroutines.runBlocking

/**
 * Example UI authored only with Podca Studio DSL (no androidx Compose UI/Material widgets).
 * Encode on the server and ship bytes to clients that use Podca Player.
 */
fun encodeServerWelcomeDocument(): ByteArray = runBlocking {
    PodcaStudio.encode { ServerWelcomeDocumentRoot() }
}

@Composable
private fun ServerWelcomeDocumentRoot() {
    PodcaColumn {
        PodcaText(text = "Podca (server-encoded)")
        PodcaText(text = "This node tree was built with PodcaStudio on the JVM.")
    }
}
