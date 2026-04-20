package com.podca.sdui.remote.core

import kotlin.math.atan2
import kotlin.math.hypot

/** Point on an open polyline (dp) + tangent angle (radians, math convention: +x axis, CCW positive). */
public data class TextOnPathPointTangent(
    val xDp: Float,
    val yDp: Float,
    val tangentRadians: Float,
)

/**
 * Flattens [path] to an open **x,y in dp** list when verbs are only [RemoteCanvasPathVerbProto.REMOTE_PATH_VERB_MOVE_TO]
 * followed by one or more [RemoteCanvasPathVerbProto.REMOTE_PATH_VERB_LINE_TO] (AndroidX `drawTextOnPath` wire subset).
 */
public fun openPolylineXyDpFromPathProtoMoveLineOnly(path: RemoteCanvasPathProto): List<Float>? {
    val verbs = path.verbs
    val c = path.coords_dp
    if (verbs.isEmpty()) return null
    if (verbs.firstOrNull() != RemoteCanvasPathVerbProto.REMOTE_PATH_VERB_MOVE_TO) return null
    val out = ArrayList<Float>(c.size)
    var i = 0
    for (verb in verbs) {
        when (verb) {
            RemoteCanvasPathVerbProto.REMOTE_PATH_VERB_UNSPECIFIED -> return null
            RemoteCanvasPathVerbProto.REMOTE_PATH_VERB_MOVE_TO,
            RemoteCanvasPathVerbProto.REMOTE_PATH_VERB_LINE_TO,
            -> {
                if (i + 2 > c.size) return null
                out.add(c[i])
                out.add(c[i + 1])
                i += 2
            }
            else -> return null
        }
    }
    if (out.size < 4) return null
    return out
}

/** Total Euclidean length of consecutive vertex pairs in **dp**. */
public fun polylineTotalLengthDp(xyDp: List<Float>): Float {
    if (xyDp.size < 4 || xyDp.size % 2 != 0) return 0f
    var sum = 0f
    var p = 0
    while (p + 4 <= xyDp.size) {
        val x0 = xyDp[p]
        val y0 = xyDp[p + 1]
        val x1 = xyDp[p + 2]
        val y1 = xyDp[p + 3]
        sum += hypot(x1 - x0, y1 - y0)
        p += 2
    }
    return sum
}

/** Returns the point at arc length [arcLengthDp] from the start of the first segment, clamped to the path. */
public fun pointAndTangentOnOpenPolylineAtLengthDp(
    xyDp: List<Float>,
    arcLengthDp: Float,
): TextOnPathPointTangent? {
    if (xyDp.size < 4 || xyDp.size % 2 != 0) return null
    val total = polylineTotalLengthDp(xyDp)
    if (total <= 0f) return null
    var s =
        if (arcLengthDp.isFinite()) {
            arcLengthDp.coerceIn(0f, total)
        } else {
            0f
        }
    var idx = 0
    while (idx + 4 <= xyDp.size) {
        val x0 = xyDp[idx]
        val y0 = xyDp[idx + 1]
        val x1 = xyDp[idx + 2]
        val y1 = xyDp[idx + 3]
        val dx = x1 - x0
        val dy = y1 - y0
        val len = hypot(dx, dy)
        if (len <= 1e-6f) {
            idx += 2
            continue
        }
        if (s <= len) {
            val t = s / len
            val x = x0 + dx * t
            val y = y0 + dy * t
            return TextOnPathPointTangent(x, y, atan2(dy, dx))
        }
        s -= len
        idx += 2
    }
    val x0 = xyDp[xyDp.size - 4]
    val y0 = xyDp[xyDp.size - 3]
    val x1 = xyDp[xyDp.size - 2]
    val y1 = xyDp[xyDp.size - 1]
    val dx = x1 - x0
    val dy = y1 - y0
    return TextOnPathPointTangent(x1, y1, atan2(dy, dx))
}
