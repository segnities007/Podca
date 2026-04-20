package com.podca.sdui.remote.core

/**
 * Returns a path whose [RemoteCanvasPathProto.coords_dp] entries are linearly interpolated in **dp space**
 * between [from] and [to] at [t] in `[0, 1]` (non-finite [t] is treated as `0`).
 *
 * Returns `null` unless both paths share the **same** [RemoteCanvasPathProto.verbs] list (including order)
 * and the same [RemoteCanvasPathProto.coords_dp] size — matching pairs of control points are lerped;
 * [RemoteCanvasPathVerbProto.REMOTE_PATH_VERB_CLOSE] consumes no coords. Subset behaviour for AndroidX-style
 * `drawTweenPath` when paths are structurally compatible.
 *
 * When this returns `null`, players may still stroke one endpoint if
 * [RemoteCanvasOpProto.tween_path_incompatible_fallback] is **1** (from) or **2** (to); see
 * [RemoteCanvasTweenPathIncompatibleFallbackWire].
 */
public fun lerpRemoteCanvasPathProtosOrNull(
    from: RemoteCanvasPathProto,
    to: RemoteCanvasPathProto,
    t: Float,
): RemoteCanvasPathProto? {
    val tv = t.coerceIn(0f, 1f).let { x -> if (x.isFinite()) x else 0f }
    val vFrom = from.verbs
    val vTo = to.verbs
    if (vFrom.isEmpty() || vFrom.size != vTo.size) return null
    if (vFrom != vTo) return null
    val cFrom = from.coords_dp
    val cTo = to.coords_dp
    if (cFrom.size != cTo.size) return null
    val out = ArrayList<Float>(cFrom.size)
    for (i in cFrom.indices) {
        val a = cFrom[i]
        val b = cTo[i]
        out.add(a + (b - a) * tv)
    }
    return RemoteCanvasPathProto(verbs = vFrom, coords_dp = out)
}

/**
 * Blend parameter **t** in `[0, 1]` for `DRAW_TWEEN_PATH`.
 *
 * When [RemoteCanvasOpProto.tween_path_fraction_remote_float_id] is non-blank, **t** is read from [resolveRemoteFloat]
 * (same host map as `CONDITIONAL_BEGIN` — typically `PodcaRuntime.remoteCanvasConditionalFloats`); unknown id or
 * non-finite resolved value is treated as **0** before clamp. When the id is blank, **t** comes from
 * [RemoteCanvasOpProto.tween_path_fraction] only.
 */
public fun tweenPathBlendTForRemoteCanvasOp(
    op: RemoteCanvasOpProto,
    resolveRemoteFloat: (String) -> Float?,
): Float {
    val id = op.tween_path_fraction_remote_float_id
    val raw =
        if (id.isNotBlank()) {
            resolveRemoteFloat(id) ?: Float.NaN
        } else {
            op.tween_path_fraction
        }
    return raw.let { x -> if (x.isFinite()) x.coerceIn(0f, 1f) else 0f }
}
