package com.podca.sdui.remote.core

/**
 * Returns the UTF-16 code-unit slice of [body] for [RemoteCanvasOpProto.draw_text_run_start] /
 * [RemoteCanvasOpProto.draw_text_run_end] (AndroidX `drawTextRun` index convention: [end] exclusive,
 * **−1** = end of string). Null when the slice is empty after clamping.
 */
public fun remoteCanvasTextRunSliceUtf16(
    body: String,
    start: Int,
    endExclusiveWire: Int,
): String? {
    if (body.isEmpty()) return null
    val n = body.length
    val s = start.coerceIn(0, n)
    val e = (if (endExclusiveWire < 0) n else endExclusiveWire).coerceIn(s, n)
    if (s >= e) return null
    return body.substring(s, e)
}
