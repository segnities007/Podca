package com.podca.sdui.remote.core

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNotNull
import kotlin.test.assertNull

class RemoteCanvasTweenPathLerpTest {

    private fun linePath(x0: Float, y0: Float, x1: Float, y1: Float): RemoteCanvasPathProto =
        RemoteCanvasPathProto(
            verbs =
                listOf(
                    RemoteCanvasPathVerbProto.REMOTE_PATH_VERB_MOVE_TO,
                    RemoteCanvasPathVerbProto.REMOTE_PATH_VERB_LINE_TO,
                ),
            coords_dp = listOf(x0, y0, x1, y1),
        )

    @Test
    fun lerpAtZeroReturnsFromCoords() {
        val a = linePath(0f, 0f, 10f, 0f)
        val b = linePath(0f, 0f, 20f, 10f)
        val r = lerpRemoteCanvasPathProtosOrNull(a, b, 0f)!!
        assertEquals(listOf(0f, 0f, 10f, 0f), r.coords_dp)
    }

    @Test
    fun lerpAtOneReturnsToCoords() {
        val a = linePath(0f, 0f, 10f, 0f)
        val b = linePath(0f, 0f, 20f, 10f)
        val r = lerpRemoteCanvasPathProtosOrNull(a, b, 1f)!!
        assertEquals(listOf(0f, 0f, 20f, 10f), r.coords_dp)
    }

    @Test
    fun lerpMidpointInterpolates() {
        val a = linePath(0f, 0f, 10f, 0f)
        val b = linePath(0f, 0f, 20f, 10f)
        val r = lerpRemoteCanvasPathProtosOrNull(a, b, 0.5f)!!
        assertEquals(listOf(0f, 0f, 15f, 5f), r.coords_dp)
    }

    @Test
    fun mismatchedVerbsReturnsNull() {
        val a = linePath(0f, 0f, 1f, 1f)
        val b =
            RemoteCanvasPathProto(
                verbs =
                    listOf(
                        RemoteCanvasPathVerbProto.REMOTE_PATH_VERB_MOVE_TO,
                        RemoteCanvasPathVerbProto.REMOTE_PATH_VERB_CLOSE,
                    ),
                coords_dp = listOf(0f, 0f),
            )
        assertNull(lerpRemoteCanvasPathProtosOrNull(a, b, 0.5f))
    }

    @Test
    fun nanFractionTreatedAsZero() {
        val a = linePath(0f, 0f, 10f, 0f)
        val b = linePath(0f, 0f, 20f, 10f)
        val r = assertNotNull(lerpRemoteCanvasPathProtosOrNull(a, b, Float.NaN))
        assertEquals(listOf(0f, 0f, 10f, 0f), r.coords_dp)
    }

    @Test
    fun blendTUsesLiteralWhenRemoteFloatIdBlank() {
        val op =
            RemoteCanvasOpProto(
                tween_path_fraction = 0.7f,
                tween_path_fraction_remote_float_id = "",
            )
        assertEquals(0.7f, tweenPathBlendTForRemoteCanvasOp(op) { null })
    }

    @Test
    fun blendTUsesMapWhenRemoteFloatIdSet() {
        val op =
            RemoteCanvasOpProto(
                tween_path_fraction = 0.1f,
                tween_path_fraction_remote_float_id = "progress",
            )
        assertEquals(
            0.9f,
            tweenPathBlendTForRemoteCanvasOp(op) { id -> if (id == "progress") 0.9f else null },
        )
    }

    @Test
    fun blendTUnknownRemoteFloatIdTreatsAsZero() {
        val op =
            RemoteCanvasOpProto(
                tween_path_fraction = 0.5f,
                tween_path_fraction_remote_float_id = "missing",
            )
        assertEquals(0f, tweenPathBlendTForRemoteCanvasOp(op) { null })
    }
}
