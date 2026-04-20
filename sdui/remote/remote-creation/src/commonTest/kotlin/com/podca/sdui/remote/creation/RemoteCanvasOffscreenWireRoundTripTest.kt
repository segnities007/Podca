package com.podca.sdui.remote.creation

import com.podca.sdui.remote.core.RemoteCanvasOpCodeProto
import com.podca.sdui.remote.core.RemoteCanvasProgramProto
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

class RemoteCanvasOffscreenWireRoundTripTest {

    @Test
    fun offscreenOpcodeValuesMatchProtoContract() {
        assertEquals(37, RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_PUSH_OFFSCREEN_RENDER.value)
        assertEquals(38, RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_POP_OFFSCREEN_RENDER.value)
        assertEquals(48, RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_CLEAR_OFFSCREEN_BITMAP_POOL.value)
    }

    @Test
    fun offscreenPushCarriesRectThroughWire() {
        val program =
            remoteCanvasProgram(
                widthDp = 16f,
                heightDp = 16f,
                canvasPushOffscreenRenderDp(2f, 2f, 10f, 10f),
                canvasFillRectDp(0f, 0f, 8f, 8f, colorArgb = 0xFFFF0000.toInt()),
                canvasPopOffscreenRender(),
            )
        val decoded =
            RemoteCanvasProgramProto.ADAPTER.decode(RemoteCanvasProgramProto.ADAPTER.encode(program))
        assertEquals(3, decoded.ops.size)
        assertEquals(RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_PUSH_OFFSCREEN_RENDER, decoded.ops[0].code)
        assertEquals(2f, decoded.ops[0].rect_l_dp)
        assertEquals(2f, decoded.ops[0].rect_t_dp)
        assertEquals(10f, decoded.ops[0].rect_r_dp)
        assertEquals(10f, decoded.ops[0].rect_b_dp)
        assertEquals(RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_FILL_RECT, decoded.ops[1].code)
        assertEquals(RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_POP_OFFSCREEN_RENDER, decoded.ops[2].code)
        assertEquals(false, decoded.ops[0].offscreen_discard_pooled_bitmap_after_pop)
        assertEquals(0, decoded.offscreen_bitmap_pool_max_entries)
    }

    @Test
    fun offscreenPushCarriesBitmapIdThroughWire() {
        val program =
            remoteCanvasProgram(
                widthDp = 8f,
                heightDp = 8f,
                canvasPushOffscreenRenderDp(0f, 0f, 4f, 4f, bitmapId = "pool-a"),
                canvasPopOffscreenRender(),
            )
        val decoded =
            RemoteCanvasProgramProto.ADAPTER.decode(RemoteCanvasProgramProto.ADAPTER.encode(program))
        assertEquals(2, decoded.ops.size)
        assertEquals("pool-a", decoded.ops[0].offscreen_bitmap_id)
    }

    @Test
    fun offscreenPushCarriesClearFlagsThroughWire() {
        val program =
            remoteCanvasProgram(
                widthDp = 16f,
                heightDp = 16f,
                canvasPushOffscreenRenderDp(
                    leftDp = 0f,
                    topDp = 0f,
                    rightDp = 8f,
                    bottomDp = 8f,
                    skipClearBeforeDraw = true,
                    clearColorArgb = 0xFF00FF00.toInt(),
                ),
                canvasPopOffscreenRender(),
            )
        val decoded =
            RemoteCanvasProgramProto.ADAPTER.decode(RemoteCanvasProgramProto.ADAPTER.encode(program))
        val push = decoded.ops[0]
        assertTrue(push.offscreen_skip_clear_before_draw)
        assertEquals(0xFF00FF00.toInt(), push.offscreen_clear_color_argb)
    }

    @Test
    fun offscreenDiscardPooledBitmapAfterPopRoundTripsThroughWire() {
        val program =
            remoteCanvasProgram(
                widthDp = 8f,
                heightDp = 8f,
                canvasPushOffscreenRenderDp(
                    0f,
                    0f,
                    4f,
                    4f,
                    bitmapId = "once",
                    discardPooledBitmapAfterPop = true,
                ),
                canvasPopOffscreenRender(),
            )
        val decoded =
            RemoteCanvasProgramProto.ADAPTER.decode(RemoteCanvasProgramProto.ADAPTER.encode(program))
        assertTrue(decoded.ops[0].offscreen_discard_pooled_bitmap_after_pop)
    }

    @Test
    fun offscreenBitmapPoolMaxEntriesRoundTripsThroughWire() {
        val program =
            remoteCanvasProgram(
                widthDp = 4f,
                heightDp = 4f,
                canvasPushOffscreenRenderDp(0f, 0f, 2f, 2f),
                canvasPopOffscreenRender(),
                offscreenBitmapPoolMaxEntries = 32,
            )
        val decoded =
            RemoteCanvasProgramProto.ADAPTER.decode(RemoteCanvasProgramProto.ADAPTER.encode(program))
        assertEquals(32, decoded.offscreen_bitmap_pool_max_entries)
    }

    @Test
    fun clearOffscreenBitmapPoolOpcodeRoundTripsThroughWire() {
        val program =
            remoteCanvasProgram(
                widthDp = 4f,
                heightDp = 4f,
                canvasClearOffscreenBitmapPool(),
            )
        val decoded =
            RemoteCanvasProgramProto.ADAPTER.decode(RemoteCanvasProgramProto.ADAPTER.encode(program))
        assertEquals(1, decoded.ops.size)
        assertEquals(RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_CLEAR_OFFSCREEN_BITMAP_POOL, decoded.ops[0].code)
    }
}
