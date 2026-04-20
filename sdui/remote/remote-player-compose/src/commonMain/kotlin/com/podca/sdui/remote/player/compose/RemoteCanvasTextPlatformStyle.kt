package com.podca.sdui.remote.player.compose

import androidx.compose.ui.text.PlatformTextStyle

/**
 * When [disableFontPadding] is true, returns a [PlatformTextStyle] that turns off font padding where
 * the platform supports it (Android). Other targets return null in v1.
 */
internal expect fun remoteCanvasTextPlatformStyleForDisableFontPadding(disableFontPadding: Boolean): PlatformTextStyle?
