package com.podca.sdui.remote.core

import kotlin.math.abs

/**
 * Linearly interpolates paired x,y in **dp** between [xyFrom] and [xyTo] at [t] in `[0, 1]`.
 * Returns `null` unless both lists have the same length ≥ 6, even length, and at least 3 vertices.
 */
public fun lerpClosedPolylineXyDpOrNull(
    xyFrom: List<Float>,
    xyTo: List<Float>,
    t: Float,
): List<Float>? {
    if (xyFrom.size != xyTo.size) return null
    if (xyFrom.size < 6 || xyFrom.size % 2 != 0) return null
    val tv = t.coerceIn(0f, 1f).let { x -> if (x.isFinite()) x else 0f }
    return xyFrom.indices.map { i ->
        val a = xyFrom[i]
        val b = xyTo[i]
        a + (b - a) * tv
    }
}

/**
 * True iff [xy] (paired x,y, at least 3 vertices) forms a **strictly convex** simple polygon
 * (no three consecutive collinear vertices; CCW or CW consistent turning).
 */
public fun isConvexPolygonXy(xy: List<Float>): Boolean {
    val n2 = xy.size
    if (n2 < 6 || n2 % 2 != 0) return false
    val n = n2 / 2
    if (n < 3) return false
    fun x(i: Int) = xy[((i % n + n) % n) * 2]
    fun y(i: Int) = xy[((i % n + n) % n) * 2 + 1]
    fun cross(
        oi: Int,
        ai: Int,
        bi: Int,
    ): Float {
        val ox = x(oi)
        val oy = y(oi)
        return (x(ai) - ox) * (y(bi) - oy) - (y(ai) - oy) * (x(bi) - ox)
    }
    var sign = 0
    for (i in 0 until n) {
        val c = cross(i, (i + 1) % n, (i + 2) % n)
        if (abs(c) < 1e-5f) return false
        val s = if (c > 0f) 1 else -1
        if (sign == 0) {
            sign = s
        } else if (s != sign) {
            return false
        }
    }
    return true
}
