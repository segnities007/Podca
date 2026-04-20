package com.podca.sdui.remote.player.compose

import kotlin.test.Test
import kotlin.test.assertNotSame
import kotlin.test.assertSame

class OffscreenBitmapPoolJvmTest {

    @Test
    fun reusesBitmapForSameIdAndSize() {
        val pool = OffscreenBitmapPool(maxEntries = 4)

        val first = pool.obtainBitmap(id = "hero", widthPx = 32, heightPx = 16)
        val second = pool.obtainBitmap(id = "hero", widthPx = 32, heightPx = 16)

        assertSame(first, second)
    }

    @Test
    fun replacesBitmapWhenSameIdUsesDifferentSize() {
        val pool = OffscreenBitmapPool(maxEntries = 4)

        val first = pool.obtainBitmap(id = "hero", widthPx = 32, heightPx = 16)
        val second = pool.obtainBitmap(id = "hero", widthPx = 64, heightPx = 16)

        assertNotSame(first, second)
    }

    @Test
    fun evictsLeastRecentlyUsedEntry() {
        val pool = OffscreenBitmapPool(maxEntries = 2)

        val a = pool.obtainBitmap(id = "a", widthPx = 8, heightPx = 8)
        val b = pool.obtainBitmap(id = "b", widthPx = 8, heightPx = 8)
        pool.obtainBitmap(id = "a", widthPx = 8, heightPx = 8) // make "a" most-recent

        pool.obtainBitmap(id = "c", widthPx = 8, heightPx = 8) // evict "b"

        val aAfterEviction = pool.obtainBitmap(id = "a", widthPx = 8, heightPx = 8)
        val bAfterEviction = pool.obtainBitmap(id = "b", widthPx = 8, heightPx = 8)

        assertSame(a, aAfterEviction)
        assertNotSame(b, bAfterEviction)
    }

    @Test
    fun clearDropsAllNamedEntries() {
        val pool = OffscreenBitmapPool(maxEntries = 2)
        val a = pool.obtainBitmap(id = "a", widthPx = 8, heightPx = 8)
        pool.obtainBitmap(id = "b", widthPx = 8, heightPx = 8)

        pool.clear()

        val aAfterClear = pool.obtainBitmap(id = "a", widthPx = 8, heightPx = 8)
        assertNotSame(a, aAfterClear)
    }
}
