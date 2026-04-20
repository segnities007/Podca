package com.podca.sdui.remote.player.compose

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.dp
import com.podca.sdui.remote.core.isConvexPolygonXy
import kotlin.math.sqrt

private fun dist(
    a: Offset,
    b: Offset,
): Float {
    val dx = b.x - a.x
    val dy = b.y - a.y
    return sqrt(dx * dx + dy * dy)
}

private fun norm(o: Offset): Offset {
    val l = sqrt(o.x * o.x + o.y * o.y)
    if (l < 1e-6f) return Offset.Zero
    return Offset(o.x / l, o.y / l)
}

internal fun DrawScope.sharpClosedPolylinePathFromDp(xyDp: List<Float>): Path? {
    if (xyDp.size < 6 || xyDp.size % 2 != 0) return null
    val p = Path()
    p.moveTo(xyDp[0].dp.toPx(), xyDp[1].dp.toPx())
    var i = 2
    while (i < xyDp.size) {
        p.lineTo(xyDp[i].dp.toPx(), xyDp[i + 1].dp.toPx())
        i += 2
    }
    p.close()
    return p
}

/**
 * Closed polygon with **quadratic** corner rounding (control = vertex); convex-only rounded outline;
 * non-convex or [radiusDp] ≤ 0 falls back to a sharp closed polyline.
 */
internal fun DrawScope.roundedConvexPolygonPathFromPolylineDp(
    xyDp: List<Float>,
    radiusDp: Float,
): Path? {
    if (xyDp.size < 6 || xyDp.size % 2 != 0) return null
    val n = xyDp.size / 2
    if (n < 3) return null
    if (radiusDp <= 0f) {
        return sharpClosedPolylinePathFromDp(xyDp)
    }
    if (!isConvexPolygonXy(xyDp)) {
        return sharpClosedPolylinePathFromDp(xyDp)
    }
    val pts =
        Array(n) { i ->
            Offset(xyDp[2 * i].dp.toPx(), xyDp[2 * i + 1].dp.toPx())
        }
    val rPx = radiusDp.dp.toPx()

    data class Corner(
        val p1: Offset,
        val b: Offset,
        val p2: Offset,
    )
    val corners =
        Array(n) { i ->
            val b = pts[i]
            val a = pts[(i - 1 + n) % n]
            val c = pts[(i + 1) % n]
            val vToA = norm(Offset(a.x - b.x, a.y - b.y))
            val vToC = norm(Offset(c.x - b.x, c.y - b.y))
            val lenBa = dist(a, b)
            val lenBc = dist(b, c)
            val d = minOf(rPx, lenBa * 0.45f, lenBc * 0.45f)
            val p1 = Offset(b.x + vToA.x * d, b.y + vToA.y * d)
            val p2 = Offset(b.x + vToC.x * d, b.y + vToC.y * d)
            Corner(p1, b, p2)
        }

    val path = Path()
    path.moveTo(corners[0].p1.x, corners[0].p1.y)
    for (i in 0 until n) {
        val c = corners[i]
        path.quadraticTo(c.b.x, c.b.y, c.p2.x, c.p2.y)
        val ni = (i + 1) % n
        path.lineTo(corners[ni].p1.x, corners[ni].p1.y)
    }
    path.close()
    return path
}
