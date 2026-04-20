package com.podca.sdui.remote.player.compose

import androidx.compose.ui.text.PlatformParagraphStyle
import androidx.compose.ui.text.PlatformSpanStyle
import androidx.compose.ui.text.PlatformTextStyle

internal actual fun remoteCanvasTextPlatformStyleForDisableFontPadding(disableFontPadding: Boolean): PlatformTextStyle? =
    if (!disableFontPadding) {
        null
    } else {
        PlatformTextStyle(
            spanStyle = PlatformSpanStyle.Default,
            paragraphStyle = PlatformParagraphStyle(includeFontPadding = false),
        )
    }
