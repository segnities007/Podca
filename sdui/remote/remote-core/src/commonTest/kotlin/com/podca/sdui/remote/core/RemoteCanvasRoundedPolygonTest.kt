package com.podca.sdui.remote.core

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class RemoteCanvasRoundedPolygonTest {

    @Test
    fun lerpMidpointInterpolatesVertices() {
        val a = listOf(0f, 0f, 10f, 0f, 10f, 10f)
        val b = listOf(0f, 0f, 20f, 0f, 20f, 20f)
        val r = lerpClosedPolylineXyDpOrNull(a, b, 0.5f)!!
        assertEquals(listOf(0f, 0f, 15f, 0f, 15f, 15f), r)
    }

    @Test
    fun lerpReturnsNullWhenVertexCountDiffers() {
        assertNull(lerpClosedPolylineXyDpOrNull(listOf(0f, 0f, 1f, 0f, 1f, 1f), listOf(0f, 0f, 1f, 0f), 0.5f))
    }

    @Test
    fun triangleIsConvex() {
        assertTrue(isConvexPolygonXy(listOf(0f, 0f, 10f, 0f, 5f, 8f)))
    }

    @Test
    fun concavePentagonIsNotConvex() {
        assertFalse(
            isConvexPolygonXy(
                listOf(0f, 0f, 10f, 0f, 10f, 10f, 5f, 5f, 0f, 10f),
            ),
        )
    }

    @Test
    fun nanMorphTreatedAsZero() {
        val a = listOf(0f, 0f, 10f, 0f, 10f, 10f)
        val b = listOf(0f, 0f, 20f, 0f, 20f, 20f)
        val r = assertNotNull(lerpClosedPolylineXyDpOrNull(a, b, Float.NaN))
        assertEquals(a, r)
    }
}
