package com.podca.sdui.remote.core

/** Wire values for [RemoteCanvasOpProto.tween_path_incompatible_fallback] (`DRAW_TWEEN_PATH`). */
public object RemoteCanvasTweenPathIncompatibleFallbackWire {
    /** When lerp fails, skip drawing (legacy). */
    public const val SKIP: Int = 0
    /** When lerp fails, stroke [RemoteCanvasOpProto.path] (from). */
    public const val STROKE_FROM: Int = 1
    /** When lerp fails, stroke [RemoteCanvasOpProto.tween_path_to] (to). */
    public const val STROKE_TO: Int = 2
}
