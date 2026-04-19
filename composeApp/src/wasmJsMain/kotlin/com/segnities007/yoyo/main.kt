package com.segnities007.yoyo

import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.window.ComposeViewport
import kotlinx.browser.document
import org.w3c.dom.HTMLElement

@OptIn(ExperimentalComposeUiApi::class)
fun main() {
    val root = document.body as? HTMLElement ?: return
    ComposeViewport(root) {
        App()
    }
}
