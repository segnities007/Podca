package com.podca.sdui.remote.core

import kotlin.math.abs
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull
import kotlin.test.assertTrue

class RemoteCanvasTextOnPathGeometryTest {

    @Test
    fun moveLineOnlyPathFlattensToPolyline() {
        val path =
            RemoteCanvasPathProto(
                verbs =
                    listOf(
                        RemoteCanvasPathVerbProto.REMOTE_PATH_VERB_MOVE_TO,
                        RemoteCanvasPathVerbProto.REMOTE_PATH_VERB_LINE_TO,
                        RemoteCanvasPathVerbProto.REMOTE_PATH_VERB_LINE_TO,
                    ),
                coords_dp = listOf(0f, 0f, 10f, 0f, 10f, 10f),
            )
        val flat = openPolylineXyDpFromPathProtoMoveLineOnly(path)!!
        assertEquals(listOf(0f, 0f, 10f, 0f, 10f, 10f), flat)
    }

    @Test
    fun cubicVerbRejected() {
        val path =
            RemoteCanvasPathProto(
                verbs =
                    listOf(
                        RemoteCanvasPathVerbProto.REMOTE_PATH_VERB_MOVE_TO,
                        RemoteCanvasPathVerbProto.REMOTE_PATH_VERB_CUBIC_TO,
                    ),
                coords_dp = listOf(0f, 0f, 1f, 1f, 2f, 2f, 3f, 3f),
            )
        assertNull(openPolylineXyDpFromPathProtoMoveLineOnly(path))
    }

    @Test
    fun pointAtHalfDiagonal() {
        val xy = listOf(0f, 0f, 10f, 0f, 10f, 10f)
        assertTrue(abs(polylineTotalLengthDp(xy) - 20f) < 1e-3f)
        val pt = assertNotNull(pointAndTangentOnOpenPolylineAtLengthDp(xy, 5f))
        assertTrue(abs(pt.xDp - 5f) < 1e-3f)
        assertTrue(abs(pt.yDp) < 1e-3f)
    }
}
