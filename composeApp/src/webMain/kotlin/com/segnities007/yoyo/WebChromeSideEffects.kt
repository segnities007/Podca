package com.segnities007.yoyo

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import kotlinx.browser.document

@Composable
actual fun WebChromeSideEffects() {
    LaunchedEffect(Unit) {
        document.getElementById("loader")?.let { el ->
            el.parentElement?.removeChild(el)
        }
    }
}
