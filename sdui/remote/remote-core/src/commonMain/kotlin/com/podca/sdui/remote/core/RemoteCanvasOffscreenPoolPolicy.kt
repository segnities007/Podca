package com.podca.sdui.remote.core

/** When [RemoteCanvasProgramProto.offscreen_bitmap_pool_max_entries] is **0**, the player uses this many named pool slots (LRU pool in `remote-player-compose`). */
public const val REMOTE_CANVAS_OFFSCREEN_POOL_DEFAULT_MAX_ENTRIES: Int = 16

/** Defensive ceiling for wire-specified [RemoteCanvasProgramProto.offscreen_bitmap_pool_max_entries]. */
public const val REMOTE_CANVAS_OFFSCREEN_POOL_WIRE_CAP: Int = 512

/** Resolves the LRU cap for named [RemoteCanvasOpProto.offscreen_bitmap_id] reuse from program wire. */
public fun remoteCanvasOffscreenPoolMaxEntriesFromWire(wire: Int): Int =
    if (wire == 0) {
        REMOTE_CANVAS_OFFSCREEN_POOL_DEFAULT_MAX_ENTRIES
    } else {
        wire.coerceIn(1, REMOTE_CANVAS_OFFSCREEN_POOL_WIRE_CAP)
    }
