package com.podca.sdui.remote.player.compose

import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Canvas as GraphicsCanvas
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageBitmapConfig
import androidx.compose.ui.graphics.drawscope.CanvasDrawScope
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.LayoutDirection
import com.podca.sdui.remote.core.RemoteCanvasOpCodeProto
import com.podca.sdui.remote.core.RemoteCanvasOpProto
import kotlin.math.roundToInt

/** LRU-ish pool of named offscreen bitmaps (subset of AndroidX named `RemoteBitmap` reuse). */
internal class OffscreenBitmapPool(private val maxEntries: Int = 16) {
    internal data class Entry(
        val widthPx: Int,
        val heightPx: Int,
        val bitmap: ImageBitmap,
    )

    private val map = mutableMapOf<String, Entry>()
    private val accessOrder = ArrayDeque<String>()

    fun obtainBitmap(
        id: String,
        widthPx: Int,
        heightPx: Int,
    ): ImageBitmap {
        if (id.isEmpty()) {
            return ImageBitmap(widthPx.coerceAtLeast(1), heightPx.coerceAtLeast(1), ImageBitmapConfig.Argb8888)
        }
        val existing = map.remove(id)
        if (existing != null && existing.widthPx == widthPx && existing.heightPx == heightPx) {
            map[id] = existing
            touch(id)
            return existing.bitmap
        }
        val bmp = ImageBitmap(widthPx.coerceAtLeast(1), heightPx.coerceAtLeast(1), ImageBitmapConfig.Argb8888)
        map[id] = Entry(widthPx, heightPx, bmp)
        touch(id)
        while (map.size > maxEntries) {
            if (accessOrder.isEmpty()) break
            val oldest = accessOrder.removeFirst()
            map.remove(oldest)
        }
        return bmp
    }

    /** Removes a named entry after compositing (see [RemoteCanvasOpProto.offscreen_discard_pooled_bitmap_after_pop]). */
    fun discardPooledId(id: String) {
        if (id.isBlank()) return
        map.remove(id)
        accessOrder.remove(id)
    }

    /** Clears all named pooled entries. */
    fun clear() {
        map.clear()
        accessOrder.clear()
    }

    private fun touch(id: String) {
        accessOrder.remove(id)
        accessOrder.addLast(id)
    }
}

/** Index of the matching [RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_POP_OFFSCREEN_RENDER] after a PUSH, or null. */
internal fun findRemoteCanvasOffscreenEnd(
    ops: List<RemoteCanvasOpProto>,
    searchFrom: Int,
    endExclusive: Int,
): Int? {
    var depth = 1
    var k = searchFrom
    while (k < endExclusive) {
        when (ops[k].code) {
            RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_PUSH_OFFSCREEN_RENDER -> depth++
            RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_POP_OFFSCREEN_RENDER -> {
                depth--
                if (depth == 0) return k
            }
            else -> Unit
        }
        k++
    }
    return null
}

private fun drawImageFilterQualityFromWire(quality: Int): FilterQuality =
    when (quality.coerceIn(0, 3)) {
        1 -> FilterQuality.Low
        2 -> FilterQuality.Medium
        3 -> FilterQuality.High
        else -> FilterQuality.Medium
    }

private fun pushOffscreenConditionalStack(
    stack: MutableList<Boolean>,
    op: RemoteCanvasOpProto,
    resolveRemoteFloat: (String) -> Float?,
) {
    stack.add(evalRemoteCanvasConditionalBeginBranch(op, stack, resolveRemoteFloat))
}

/**
 * Rasterizes inner ops `[innerStart, innerEndExclusive)` into a bitmap for the PUSH at [pushOpIndex].
 * Supported inner ops: `CONDITIONAL_*`, nested `PUSH_OFFSCREEN_RENDER`, `FILL_RECT`, `DRAW_IMAGE`（`DRAW_TWEEN_PATH` / `FILL_ROUNDED_POLYGON` 等はスキップ）。
 */
internal fun rasterizeOffscreenAtPushIndex(
    density: Density,
    layoutDirection: LayoutDirection,
    paintingOps: List<RemoteCanvasOpProto>,
    pushOpIndex: Int,
    innerEndExclusive: Int,
    decodedPngByOpIndex: Map<Int, ImageBitmap>,
    pool: OffscreenBitmapPool,
    resolveRemoteFloat: (String) -> Float?,
): ImageBitmap? {
    val push = paintingOps.getOrNull(pushOpIndex) ?: return null
    if (push.code != RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_PUSH_OFFSCREEN_RENDER) return null
    val innerStart = pushOpIndex + 1
    if (innerStart >= innerEndExclusive) return null
    val innerWdp = (push.rect_r_dp - push.rect_l_dp).coerceAtLeast(1f)
    val innerHdp = (push.rect_b_dp - push.rect_t_dp).coerceAtLeast(1f)
    with(density) {
        val wPx = innerWdp.dp.toPx().roundToInt().coerceAtLeast(1)
        val hPx = innerHdp.dp.toPx().roundToInt().coerceAtLeast(1)
        val bmp = pool.obtainBitmap(push.offscreen_bitmap_id, wPx, hPx)
        val scope = CanvasDrawScope()
        scope.draw(
            density,
            layoutDirection,
            GraphicsCanvas(bmp),
            Size(wPx.toFloat(), hPx.toFloat()),
        ) {
            paintOffscreenInnerOps(
                paintingOps = paintingOps,
                innerStart = innerStart,
                innerEndExclusive = innerEndExclusive,
                decodedPngByOpIndex = decodedPngByOpIndex,
                pool = pool,
                pushOp = push,
                resolveRemoteFloat = resolveRemoteFloat,
            )
        }
        return bmp
    }
}

private fun DrawScope.paintOffscreenInnerOps(
    paintingOps: List<RemoteCanvasOpProto>,
    innerStart: Int,
    innerEndExclusive: Int,
    decodedPngByOpIndex: Map<Int, ImageBitmap>,
    pool: OffscreenBitmapPool,
    pushOp: RemoteCanvasOpProto,
    resolveRemoteFloat: (String) -> Float?,
) {
    if (!pushOp.offscreen_skip_clear_before_draw) {
        drawRect(color = Color(pushOp.offscreen_clear_color_argb))
    }
    val cond = mutableListOf<Boolean>()
    var i = innerStart
    inner@while (i < innerEndExclusive) {
        val op = paintingOps[i]
        when (op.code) {
            RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_CONDITIONAL_BEGIN -> {
                pushOffscreenConditionalStack(cond, op, resolveRemoteFloat)
                i++
                continue
            }

            RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_CONDITIONAL_END -> {
                cond.removeLastOrNull()
                i++
                continue
            }

            else -> Unit
        }
        if (cond.isNotEmpty() && !cond.all { it }) {
            i++
            continue
        }
        when (op.code) {
            RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_CLEAR_OFFSCREEN_BITMAP_POOL -> {
                pool.clear()
                i++
            }

            RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_PUSH_OFFSCREEN_RENDER -> {
                val j = findRemoteCanvasOffscreenEnd(paintingOps, i + 1, innerEndExclusive)
                if (j == null) {
                    i++
                    continue@inner
                }
                val subBmp =
                    rasterizeOffscreenAtPushIndex(
                        this,
                        this.layoutDirection,
                        paintingOps,
                        i,
                        j,
                        decodedPngByOpIndex,
                        pool,
                        resolveRemoteFloat,
                    )
                if (subBmp != null) {
                    val left = op.rect_l_dp.dp.toPx()
                    val top = op.rect_t_dp.dp.toPx()
                    val right = op.rect_r_dp.dp.toPx()
                    val bottom = op.rect_b_dp.dp.toPx()
                    if (right > left && bottom > top) {
                        val dw = (right - left).roundToInt().coerceAtLeast(1)
                        val dh = (bottom - top).roundToInt().coerceAtLeast(1)
                        drawImage(
                            image = subBmp,
                            srcOffset = IntOffset.Zero,
                            srcSize = IntSize(subBmp.width, subBmp.height),
                            dstOffset = IntOffset(left.roundToInt(), top.roundToInt()),
                            dstSize = IntSize(dw, dh),
                            filterQuality = drawImageFilterQualityFromWire(0),
                        )
                    }
                    if (op.offscreen_discard_pooled_bitmap_after_pop && op.offscreen_bitmap_id.isNotBlank()) {
                        pool.discardPooledId(op.offscreen_bitmap_id)
                    }
                }
                i = j + 1
            }

            RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_FILL_RECT -> {
                val left = op.rect_l_dp.dp.toPx()
                val top = op.rect_t_dp.dp.toPx()
                val right = op.rect_r_dp.dp.toPx()
                val bottom = op.rect_b_dp.dp.toPx()
                if (right > left && bottom > top) {
                    drawRect(
                        color = Color(op.color_argb),
                        topLeft = Offset(left, top),
                        size = Size(right - left, bottom - top),
                    )
                }
                i++
            }

            RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_DRAW_IMAGE -> {
                val img = decodedPngByOpIndex[i]
                if (img == null) {
                    i++
                    continue@inner
                }
                val left = op.rect_l_dp.dp.toPx()
                val top = op.rect_t_dp.dp.toPx()
                val right = op.rect_r_dp.dp.toPx()
                val bottom = op.rect_b_dp.dp.toPx()
                if (right > left && bottom > top) {
                    val (srcOffset, srcSize) =
                        if (op.image_src_rect_enabled) {
                            var sl = op.image_src_l_px
                            var st = op.image_src_t_px
                            var sr = op.image_src_r_px
                            var sb = op.image_src_b_px
                            if (sr <= sl || sb <= st) {
                                i++
                                continue@inner
                            }
                            sl = sl.coerceIn(0, img.width)
                            sr = sr.coerceIn(0, img.width)
                            st = st.coerceIn(0, img.height)
                            sb = sb.coerceIn(0, img.height)
                            if (sr <= sl || sb <= st) {
                                i++
                                continue@inner
                            }
                            val sw = (sr - sl).coerceAtLeast(1)
                            val sh = (sb - st).coerceAtLeast(1)
                            IntOffset(sl, st) to IntSize(sw, sh)
                        } else {
                            IntOffset.Zero to IntSize(img.width, img.height)
                        }
                    val (dstOffsetRaw, dstSizeRaw) =
                        drawImageDstOffsetAndSizePx(
                            leftPx = left,
                            topPx = top,
                            rightPx = right,
                            bottomPx = bottom,
                            srcWidthPx = srcSize.width,
                            srcHeightPx = srcSize.height,
                            scaleTypeEnabled = op.draw_image_scale_type_enabled,
                            scaleTypeWire = op.draw_image_scale_type,
                        )
                    val (dstOffset, dstSize) =
                        applyDrawImageUniformScaleFactorToDst(
                            dstOffsetRaw,
                            dstSizeRaw,
                            boxLeftPx = left,
                            boxTopPx = top,
                            boxRightPx = right,
                            boxBottomPx = bottom,
                            enabled = op.draw_image_scale_factor_enabled,
                            factor = op.draw_image_scale_factor,
                        )
                    val drawAlpha =
                        if (op.draw_image_alpha_enabled) {
                            op.draw_image_alpha.coerceIn(0f, 1f)
                        } else {
                            1f
                        }
                    val drawBlendMode =
                        if (op.draw_image_blend_mode_enabled) {
                            remoteCanvasDrawImageBlendModeFromWire(op.draw_image_blend_mode)
                        } else {
                            BlendMode.SrcOver
                        }
                    val drawFilterQuality = drawImageFilterQualityFromWire(op.draw_image_filter_quality)
                    val drawColorFilter = drawImageColorFilterFromOpOrNull(op)
                    drawImage(
                        image = img,
                        srcOffset = srcOffset,
                        srcSize = srcSize,
                        dstOffset = dstOffset,
                        dstSize = dstSize,
                        alpha = drawAlpha,
                        blendMode = drawBlendMode,
                        colorFilter = drawColorFilter,
                        filterQuality = drawFilterQuality,
                    )
                }
                i++
            }

            else -> i++
        }
    }
}
