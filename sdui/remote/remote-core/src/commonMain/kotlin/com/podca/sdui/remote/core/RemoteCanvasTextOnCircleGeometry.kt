package com.podca.sdui.remote.core

import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.max
import kotlin.math.sin

/** Arc length in dp for layout along a circle of effective radius [radiusEffDp] and sweep [sweepDegIn] (0 or non-finite → 360° magnitude). */
public fun circleArcLayoutLengthDp(
    radiusEffDp: Float,
    sweepDegIn: Float,
): Float {
    val r =
        if (radiusEffDp.isFinite()) {
            radiusEffDp.coerceAtLeast(1e-6f)
        } else {
            return 0f
        }
    val sweepMag = sweepMagnitudeDeg(sweepDegIn)
    return r * sweepMag * (PI.toFloat() / 180f)
}

private fun sweepMagnitudeDeg(sweepDegIn: Float): Float =
    when {
        !sweepDegIn.isFinite() || sweepDegIn == 0f -> 360f
        else -> abs(sweepDegIn).coerceIn(0f, 360f)
    }

private fun sweepSign(sweepDegIn: Float): Float =
    when {
        !sweepDegIn.isFinite() || sweepDegIn == 0f -> 1f
        sweepDegIn < 0f -> -1f
        else -> 1f
    }

/**
 * Point on circle (dp) + tangent angle (radians) at arc length [arcLengthDp] from the layout start,
 * following Compose-style angles ([startAngleDeg] / [sweepDegIn] same convention as [RemoteCanvasOpProto.arc_start_deg] / [arc_sweep_deg] on DRAW_ARC).
 */
public fun pointAndTangentOnCircleAtArcLengthDp(
    centerXDp: Float,
    centerYDp: Float,
    radiusEffDp: Float,
    startAngleDeg: Float,
    sweepDegIn: Float,
    arcLengthDp: Float,
): TextOnPathPointTangent? {
    val total = circleArcLayoutLengthDp(radiusEffDp, sweepDegIn)
    if (total <= 0f) return null
    val s = arcLengthDp.coerceIn(0f, total)
    val sign = sweepSign(sweepDegIn)
    val sweepMag = sweepMagnitudeDeg(sweepDegIn)
    val r =
        if (radiusEffDp.isFinite()) {
            radiusEffDp.coerceAtLeast(1e-6f)
        } else {
            return null
        }

    fun pos(at: Float): Pair<Float, Float> {
        val u = at.coerceIn(0f, total)
        val frac = if (total > 1e-9f) u / total else 0f
        val thetaDeg = startAngleDeg + sign * sweepMag * frac
        val th = thetaDeg * (PI.toFloat() / 180f)
        return Pair(centerXDp + r * cos(th), centerYDp + r * sin(th))
    }

    val p1 = pos(s)
    val eps = max(1e-3f, total * 1e-4f)
    val s2 = (s + eps).coerceAtMost(total)
    val p2 =
        if (s2 > s) {
            pos(s2)
        } else {
            pos((s - eps).coerceAtLeast(0f))
        }
    val dx = p2.first - p1.first
    val dy = p2.second - p1.second
    if (dx * dx + dy * dy < 1e-12f) {
        return TextOnPathPointTangent(p1.first, p1.second, 0f)
    }
    return TextOnPathPointTangent(p1.first, p1.second, atan2(dy, dx))
}
