package com.segnities007.yoyo

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "Yoyo",
    ) {
        App()
    }
}