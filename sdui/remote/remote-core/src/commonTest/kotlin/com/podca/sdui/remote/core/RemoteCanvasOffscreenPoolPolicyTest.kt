package com.podca.sdui.remote.core

import kotlin.test.Test
import kotlin.test.assertEquals

class RemoteCanvasOffscreenPoolPolicyTest {

    @Test
    fun wireZeroUsesDefault() {
        assertEquals(REMOTE_CANVAS_OFFSCREEN_POOL_DEFAULT_MAX_ENTRIES, remoteCanvasOffscreenPoolMaxEntriesFromWire(0))
    }

    @Test
    fun wirePositiveIsClampedToCap() {
        assertEquals(512, remoteCanvasOffscreenPoolMaxEntriesFromWire(9999))
        assertEquals(1, remoteCanvasOffscreenPoolMaxEntriesFromWire(1))
    }
}
