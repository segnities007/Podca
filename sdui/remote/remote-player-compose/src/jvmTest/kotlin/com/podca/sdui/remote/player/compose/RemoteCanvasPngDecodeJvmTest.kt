package com.podca.sdui.remote.player.compose

import androidx.compose.ui.graphics.decodeToImageBitmap
import com.podca.sdui.remote.core.RemoteCanvasWireFixtures
import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * Validates the Compose `decodeToImageBitmap` path (Skiko on JVM) for [RemoteCanvasWireFixtures]
 * payloads — the same decoder [PodcaRenderRemoteCanvasProgramNode] uses for `DRAW_IMAGE`.
 */
class RemoteCanvasPngDecodeJvmTest {

    @Test
    fun minimalWireFixturePngDecodesWithComposeDecoder() {
        val bitmap = RemoteCanvasWireFixtures.minimalPng1x1Rgba.decodeToImageBitmap()
        assertEquals(1, bitmap.width)
        assertEquals(1, bitmap.height)
    }

    @Test
    fun demoBadgeWireFixturePngDecodesWithComposeDecoder() {
        val bitmap = RemoteCanvasWireFixtures.demoBadgePng8x8Teal26A69A.decodeToImageBitmap()
        assertEquals(8, bitmap.width)
        assertEquals(8, bitmap.height)
    }
}
