package com.podca.sdui.remote.core

import kotlin.math.abs
import kotlin.test.Test
import kotlin.test.assertNotNull
import kotlin.test.assertTrue

class RemoteCanvasTextOnCircleGeometryTest {

    @Test
    fun fullCircleArcLengthIsTwoPiR() {
        val len = circleArcLayoutLengthDp(10f, 0f)
        assertTrue(abs(len - (2f * kotlin.math.PI.toFloat() * 10f)) < 0.05f)
    }

    @Test
    fun quarterCircleArcLength() {
        val len = circleArcLayoutLengthDp(40f, 90f)
        assertTrue(abs(len - (40f * kotlin.math.PI.toFloat() / 2f)) < 0.05f)
    }

    @Test
    fun startAngleZeroPlacesPointOnPositiveX() {
        val pt = pointAndTangentOnCircleAtArcLengthDp(0f, 0f, 100f, 0f, 360f, 0f)
        assertNotNull(pt)
        assertTrue(abs(pt.xDp - 100f) < 0.1f && abs(pt.yDp) < 0.1f)
    }
}
