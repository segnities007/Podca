package com.podca.sdui.remote.core

/** Wire values for [RemoteCanvasOpProto.draw_text_line_height_style] (DRAW_TEXT / DRAW_TEXT_AT / DRAW_TEXT_RUN). */
public object RemoteCanvasDrawTextLineHeightStyleWire {
    /** Omit explicit Compose `LineHeightStyle` (legacy player behavior). */
    public const val LEGACY: Int = 0
    /** Compose `LineHeightStyle.Default` (Proportional + Trim.Both + Fixed). */
    public const val DEFAULT: Int = 1
    public const val TOP_TRIM_BOTH: Int = 2
    public const val BOTTOM_TRIM_BOTH: Int = 3
    public const val CENTER_TRIM_BOTH: Int = 4
    public const val PROPORTIONAL_TRIM_NONE: Int = 5
}
