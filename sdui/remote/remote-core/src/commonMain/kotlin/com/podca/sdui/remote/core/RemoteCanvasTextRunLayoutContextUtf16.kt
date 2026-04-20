package com.podca.sdui.remote.core

/**
 * UTF-16 indices for AndroidX-style `drawTextRun` shaping: layout uses [contextBody] (a slice of the
 * original buffer); the painted run is [runStartInContext] until [runEndExclusiveInContext] (exclusive),
 * both relative to [contextBody].
 *
 * Wire ([RemoteCanvasOpProto]): [text_body] is the full buffer; [draw_text_run_start] /
 * [draw_text_run_end] (−1 = end of string) select the run; [draw_text_run_context_start] /
 * [draw_text_run_context_end] select the shaping window. **[draw_text_run_context_end] ≤ 0** means
 * **end of [text_body]** (sentinel, same family as run end −1).
 */
public data class RemoteCanvasTextRunLayoutContext(
    public val contextBody: String,
    public val runStartInContext: Int,
    public val runEndExclusiveInContext: Int,
)

/**
 * Returns normalized context + run offsets, or null if the run slice is empty after clamping.
 * If the wire context does not fully contain the run, it is **expanded** to include the run
 * (minimal superset), then clamped to the buffer.
 */
public fun remoteCanvasTextRunLayoutContextUtf16(
    body: String,
    runStart: Int,
    runEndExclusiveWire: Int,
    contextStartWire: Int,
    contextEndExclusiveWire: Int,
): RemoteCanvasTextRunLayoutContext? {
    if (body.isEmpty()) return null
    val n = body.length
    val sRun = runStart.coerceIn(0, n)
    val eRun = (if (runEndExclusiveWire < 0) n else runEndExclusiveWire).coerceIn(sRun, n)
    if (sRun >= eRun) return null

    var cs = contextStartWire.coerceIn(0, n)
    var ce =
        if (contextEndExclusiveWire <= 0) {
            n
        } else {
            contextEndExclusiveWire.coerceIn(0, n)
        }
    if (cs > ce) {
        ce = cs
    }
    cs = minOf(cs, sRun)
    ce = maxOf(ce, eRun).coerceAtMost(n)
    if (cs >= ce) return null
    val contextBody = body.substring(cs, ce)
    return RemoteCanvasTextRunLayoutContext(
        contextBody = contextBody,
        runStartInContext = sRun - cs,
        runEndExclusiveInContext = eRun - cs,
    )
}
