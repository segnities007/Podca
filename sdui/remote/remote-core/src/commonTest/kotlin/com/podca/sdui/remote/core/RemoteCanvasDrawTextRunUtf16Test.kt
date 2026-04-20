package com.podca.sdui.remote.core

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class RemoteCanvasDrawTextRunUtf16Test {

    @Test
    fun negativeEndMeansEndOfString() {
        assertEquals("bc", remoteCanvasTextRunSliceUtf16("abc", 1, -1))
    }

    @Test
    fun emptySliceReturnsNull() {
        assertNull(remoteCanvasTextRunSliceUtf16("a", 1, 1))
    }

    @Test
    fun layoutContextExpandsToContainRun() {
        val ctx = remoteCanvasTextRunLayoutContextUtf16("abcdef", 2, 5, 3, 4)!!
        assertEquals("cde", ctx.contextBody)
        assertEquals(0, ctx.runStartInContext)
        assertEquals(3, ctx.runEndExclusiveInContext)
    }

    @Test
    fun layoutContextEndSentinelMeansEndOfString() {
        val ctx = remoteCanvasTextRunLayoutContextUtf16("ab", 0, 1, 0, 0)!!
        assertEquals("ab", ctx.contextBody)
        assertEquals(0, ctx.runStartInContext)
        assertEquals(1, ctx.runEndExclusiveInContext)
    }
}
