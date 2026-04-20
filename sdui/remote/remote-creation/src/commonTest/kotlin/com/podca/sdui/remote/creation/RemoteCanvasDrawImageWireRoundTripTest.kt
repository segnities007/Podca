package com.podca.sdui.remote.creation

import com.podca.sdui.protocol.ui.graphics.BlendModeProto
import com.podca.sdui.remote.core.RemoteCanvasOpCodeProto
import com.podca.sdui.remote.core.RemoteCanvasProgramProto
import com.podca.sdui.remote.core.RemoteCanvasWireFixtures
import kotlin.test.Test
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class RemoteCanvasDrawImageWireRoundTripTest {

    @Test
    fun drawImageWireNumberMatchesProtoContract() {
        assertEquals(32, RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_DRAW_IMAGE.value)
    }

    @Test
    fun drawImageOpWithDemoBadgeRoundTripsThroughRemoteCanvasProgramWire() {
        val png = RemoteCanvasWireFixtures.demoBadgePng8x8Teal26A69A
        val program =
            remoteCanvasProgram(
                widthDp = 64f,
                heightDp = 48f,
                canvasDrawImagePngInRectDp(0f, 0f, 32f, 32f, png),
            )
        val decoded = RemoteCanvasProgramProto.ADAPTER.decode(RemoteCanvasProgramProto.ADAPTER.encode(program))
        assertEquals(1, decoded.ops.size)
        val op = decoded.ops[0]
        assertEquals(RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_DRAW_IMAGE, op.code)
        assertContentEquals(png, op.image_png_bytes.toByteArray())
        assertFalse(op.draw_image_alpha_enabled)
        assertFalse(op.draw_image_blend_mode_enabled)
        assertEquals(0, op.draw_image_filter_quality)
        assertFalse(op.draw_image_color_filter_tint_enabled)
        assertEquals(0, op.draw_image_color_filter_tint_argb)
        assertFalse(op.draw_image_color_filter_lighting_enabled)
        assertEquals(0, op.draw_image_color_filter_lighting_mul_argb)
        assertEquals(0, op.draw_image_color_filter_lighting_add_argb)
        assertFalse(op.draw_image_color_filter_color_matrix_enabled)
        assertEquals(0, op.draw_image_color_filter_color_matrix.size)
        assertFalse(op.draw_image_color_filter_tint_blend_mode_enabled)
    }

    @Test
    fun drawImageWithScaleTypeRoundTripsThroughWire() {
        val png = RemoteCanvasWireFixtures.minimalPng1x1Rgba
        val program =
            remoteCanvasProgram(
                widthDp = 16f,
                heightDp = 16f,
                canvasDrawImagePngInRectDp(
                    0f,
                    0f,
                    16f,
                    16f,
                    png,
                    scaleType = RemoteCanvasDrawImageScaleType.CROP,
                ),
            )
        val decoded = RemoteCanvasProgramProto.ADAPTER.decode(RemoteCanvasProgramProto.ADAPTER.encode(program))
        val op = decoded.ops[0]
        assertTrue(op.draw_image_scale_type_enabled)
        assertEquals(RemoteCanvasDrawImageScaleType.CROP, op.draw_image_scale_type)
    }

    @Test
    fun drawImageWithFilterQualityRoundTripsThroughWire() {
        val png = RemoteCanvasWireFixtures.minimalPng1x1Rgba
        val program =
            remoteCanvasProgram(
                widthDp = 4f,
                heightDp = 4f,
                canvasDrawImagePngInRectDp(0f, 0f, 4f, 4f, png, filterQuality = 3),
            )
        val decoded = RemoteCanvasProgramProto.ADAPTER.decode(RemoteCanvasProgramProto.ADAPTER.encode(program))
        assertEquals(3, decoded.ops[0].draw_image_filter_quality)
    }

    @Test
    fun drawImageWithAlphaRoundTripsThroughWire() {
        val png = RemoteCanvasWireFixtures.minimalPng1x1Rgba
        val program =
            remoteCanvasProgram(
                widthDp = 8f,
                heightDp = 8f,
                canvasDrawImagePngInRectDp(0f, 0f, 8f, 8f, png, alpha = 0.35f),
            )
        val decoded = RemoteCanvasProgramProto.ADAPTER.decode(RemoteCanvasProgramProto.ADAPTER.encode(program))
        val op = decoded.ops[0]
        assertTrue(op.draw_image_alpha_enabled)
        assertEquals(0.35f, op.draw_image_alpha)
        assertFalse(op.draw_image_blend_mode_enabled)
    }

    @Test
    fun drawImageWithScaleFactorAndContentDescriptionRoundTripsThroughWire() {
        val png = RemoteCanvasWireFixtures.minimalPng1x1Rgba
        val program =
            remoteCanvasProgram(
                widthDp = 16f,
                heightDp = 16f,
                canvasDrawImagePngInRectDp(
                    0f,
                    0f,
                    16f,
                    16f,
                    png,
                    drawImageScaleFactorEnabled = true,
                    drawImageScaleFactor = 0.5f,
                    drawImageContentDescription = "badge",
                ),
            )
        val decoded = RemoteCanvasProgramProto.ADAPTER.decode(RemoteCanvasProgramProto.ADAPTER.encode(program))
        val op = decoded.ops[0]
        assertTrue(op.draw_image_scale_factor_enabled)
        assertEquals(0.5f, op.draw_image_scale_factor)
        assertEquals("badge", op.draw_image_content_description)
    }

    @Test
    fun drawImageWithBlendModeRoundTripsThroughWire() {
        val png = RemoteCanvasWireFixtures.minimalPng1x1Rgba
        val program =
            remoteCanvasProgram(
                widthDp = 4f,
                heightDp = 4f,
                canvasDrawImagePngInRectDp(
                    0f,
                    0f,
                    4f,
                    4f,
                    png,
                    blendMode = BlendModeProto.BLEND_MODE_MULTIPLY,
                ),
            )
        val decoded = RemoteCanvasProgramProto.ADAPTER.decode(RemoteCanvasProgramProto.ADAPTER.encode(program))
        val op = decoded.ops[0]
        assertTrue(op.draw_image_blend_mode_enabled)
        assertEquals(BlendModeProto.BLEND_MODE_MULTIPLY.value, op.draw_image_blend_mode)
    }

    @Test
    fun drawImageWithSrcSubrectRoundTripsThroughWire() {
        val png = RemoteCanvasWireFixtures.demoBadgePng8x8Teal26A69A
        val program =
            remoteCanvasProgram(
                widthDp = 24f,
                heightDp = 24f,
                canvasDrawImagePngSubrectInRectDp(
                    dstLeftDp = 0f,
                    dstTopDp = 0f,
                    dstRightDp = 16f,
                    dstBottomDp = 16f,
                    pngBytes = png,
                    srcLeftPx = 2,
                    srcTopPx = 2,
                    srcRightPx = 6,
                    srcBottomPx = 6,
                ),
            )
        val decoded = RemoteCanvasProgramProto.ADAPTER.decode(RemoteCanvasProgramProto.ADAPTER.encode(program))
        val op = decoded.ops[0]
        assertTrue(op.image_src_rect_enabled)
        assertEquals(2, op.image_src_l_px)
        assertEquals(2, op.image_src_t_px)
        assertEquals(6, op.image_src_r_px)
        assertEquals(6, op.image_src_b_px)
        assertContentEquals(png, op.image_png_bytes.toByteArray())
        assertFalse(op.draw_image_alpha_enabled)
        assertFalse(op.draw_image_blend_mode_enabled)
    }

    @Test
    fun drawImageColorFilterTintRoundTripsThroughWire() {
        val png = RemoteCanvasWireFixtures.minimalPng1x1Rgba
        val tintArgb = 0xFFFF5722.toInt()
        val program =
            remoteCanvasProgram(
                widthDp = 8f,
                heightDp = 8f,
                canvasDrawImagePngInRectDp(0f, 0f, 8f, 8f, png, colorFilterTintArgb = tintArgb),
            )
        val decoded = RemoteCanvasProgramProto.ADAPTER.decode(RemoteCanvasProgramProto.ADAPTER.encode(program))
        val op = decoded.ops[0]
        assertTrue(op.draw_image_color_filter_tint_enabled)
        assertEquals(tintArgb, op.draw_image_color_filter_tint_argb)
        assertFalse(op.draw_image_color_filter_tint_blend_mode_enabled)
    }

    @Test
    fun drawImageColorFilterTintBlendModeRoundTripsThroughWire() {
        val png = RemoteCanvasWireFixtures.minimalPng1x1Rgba
        val tintArgb = 0x80FF0000.toInt()
        val program =
            remoteCanvasProgram(
                widthDp = 4f,
                heightDp = 4f,
                canvasDrawImagePngInRectDp(
                    0f,
                    0f,
                    4f,
                    4f,
                    png,
                    colorFilterTintArgb = tintArgb,
                    colorFilterTintBlendMode = BlendModeProto.BLEND_MODE_MULTIPLY,
                ),
            )
        val decoded = RemoteCanvasProgramProto.ADAPTER.decode(RemoteCanvasProgramProto.ADAPTER.encode(program))
        val op = decoded.ops[0]
        assertTrue(op.draw_image_color_filter_tint_enabled)
        assertTrue(op.draw_image_color_filter_tint_blend_mode_enabled)
        assertEquals(BlendModeProto.BLEND_MODE_MULTIPLY.value, op.draw_image_color_filter_tint_blend_mode)
    }

    @Test
    fun drawImageColorFilterColorMatrixRoundTripsThroughWire() {
        val png = RemoteCanvasWireFixtures.minimalPng1x1Rgba
        val matrix =
            listOf(
                1f, 0f, 0f, 0f, 0f,
                0f, 1f, 0f, 0f, 0f,
                0f, 0f, 1f, 0f, 0f,
                0f, 0f, 0f, 1f, 0f,
            )
        val program =
            remoteCanvasProgram(
                widthDp = 4f,
                heightDp = 4f,
                canvasDrawImagePngInRectDp(0f, 0f, 4f, 4f, png, colorFilterColorMatrix = matrix),
            )
        val decoded = RemoteCanvasProgramProto.ADAPTER.decode(RemoteCanvasProgramProto.ADAPTER.encode(program))
        val op = decoded.ops[0]
        assertTrue(op.draw_image_color_filter_color_matrix_enabled)
        assertEquals(20, op.draw_image_color_filter_color_matrix.size)
        assertEquals(matrix, op.draw_image_color_filter_color_matrix)
    }

    @Test
    fun drawImageColorFilterLightingRoundTripsThroughWire() {
        val png = RemoteCanvasWireFixtures.minimalPng1x1Rgba
        val mul = 0xFF808080.toInt()
        val add = 0xFF101010.toInt()
        val program =
            remoteCanvasProgram(
                widthDp = 4f,
                heightDp = 4f,
                canvasDrawImagePngInRectDp(
                    0f,
                    0f,
                    4f,
                    4f,
                    png,
                    colorFilterLightingMulArgb = mul,
                    colorFilterLightingAddArgb = add,
                ),
            )
        val decoded = RemoteCanvasProgramProto.ADAPTER.decode(RemoteCanvasProgramProto.ADAPTER.encode(program))
        val op = decoded.ops[0]
        assertTrue(op.draw_image_color_filter_lighting_enabled)
        assertEquals(mul, op.draw_image_color_filter_lighting_mul_argb)
        assertEquals(add, op.draw_image_color_filter_lighting_add_argb)
    }

    @Test
    fun drawImageOpRoundTripsThroughRemoteCanvasProgramWire() {
        val png = RemoteCanvasWireFixtures.minimalPng1x1Rgba
        val program =
            remoteCanvasProgram(
                widthDp = 48f,
                heightDp = 32f,
                canvasDrawImagePngInRectDp(2f, 3f, 40f, 28f, png),
            )
        val bytes = RemoteCanvasProgramProto.ADAPTER.encode(program)
        assertTrue(bytes.isNotEmpty())
        val decoded = RemoteCanvasProgramProto.ADAPTER.decode(bytes)
        assertEquals(48f, decoded.width_dp)
        assertEquals(32f, decoded.height_dp)
        assertEquals(1, decoded.ops.size)
        val op = decoded.ops[0]
        assertEquals(RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_DRAW_IMAGE, op.code)
        assertEquals(2f, op.rect_l_dp)
        assertEquals(3f, op.rect_t_dp)
        assertEquals(40f, op.rect_r_dp)
        assertEquals(28f, op.rect_b_dp)
        assertTrue(op.image_png_bytes.size > 0)
        assertContentEquals(png, op.image_png_bytes.toByteArray())
        assertFalse(op.draw_image_alpha_enabled)
        assertFalse(op.draw_image_blend_mode_enabled)
    }
}
