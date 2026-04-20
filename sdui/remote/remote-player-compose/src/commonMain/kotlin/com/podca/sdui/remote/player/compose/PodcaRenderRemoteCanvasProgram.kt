package com.podca.sdui.remote.player.compose

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.offset
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.ui.text.rememberTextMeasurer
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableFloatStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import androidx.compose.ui.semantics.testTag
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.CornerRadius
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Rect
import androidx.compose.ui.geometry.RoundRect
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Canvas as GraphicsCanvas
import androidx.compose.ui.graphics.ClipOp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageBitmapConfig
import androidx.compose.ui.graphics.Matrix
import androidx.compose.ui.graphics.Path
import androidx.compose.ui.graphics.PathEffect
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.StrokeJoin
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.decodeToImageBitmap
import androidx.compose.ui.graphics.drawscope.Fill
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.graphics.drawscope.clipPath
import androidx.compose.ui.graphics.drawscope.clipRect
import androidx.compose.ui.graphics.drawscope.rotate as drawScopeRotate
import androidx.compose.ui.graphics.drawscope.scale
import androidx.compose.ui.graphics.drawscope.translate
import androidx.compose.ui.graphics.drawscope.withTransform
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextMeasurer
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDirection
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Density
import androidx.compose.ui.unit.Constraints
import androidx.compose.ui.unit.IntOffset
import androidx.compose.ui.unit.IntSize
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.podca.sdui.player.engine.PodcaDocumentNode
import com.podca.sdui.player.engine.PodcaRuntime
import com.podca.sdui.protocol.core.ActionBindingProto
import com.podca.sdui.protocol.core.ActionTriggerProto
import com.podca.sdui.remote.core.RemoteCanvasOpCodeProto
import com.podca.sdui.remote.core.RemoteCanvasOpProto
import com.podca.sdui.remote.core.expandRemoteCanvasLoopBlocks
import com.podca.sdui.remote.core.lerpClosedPolylineXyDpOrNull
import com.podca.sdui.remote.core.lerpRemoteCanvasPathProtosOrNull
import com.podca.sdui.remote.core.tweenPathBlendTForRemoteCanvasOp
import com.podca.sdui.remote.core.RemoteCanvasPathProto
import com.podca.sdui.remote.core.RemoteCanvasTweenPathIncompatibleFallbackWire
import com.podca.sdui.remote.core.RemoteCanvasPathVerbProto
import com.podca.sdui.remote.core.RemoteCanvasProgramProto
import com.podca.sdui.remote.core.remoteCanvasTextRunLayoutContextUtf16
import com.podca.sdui.remote.core.filterRemoteCanvasStateLayoutBlocksForPlayback
import com.podca.sdui.remote.core.remoteCanvasOffscreenPoolMaxEntriesFromWire
import com.podca.sdui.remote.core.remoteCanvasStateLayoutSemanticsHintsForPlayback
import com.podca.sdui.remote.core.circleArcLayoutLengthDp
import com.podca.sdui.remote.core.openPolylineXyDpFromPathProtoMoveLineOnly
import com.podca.sdui.remote.core.pointAndTangentOnCircleAtArcLengthDp
import com.podca.sdui.remote.core.pointAndTangentOnOpenPolylineAtLengthDp
import com.podca.sdui.remote.core.polylineTotalLengthDp
import kotlin.math.PI
import kotlin.math.abs
import kotlin.math.max
import kotlin.math.min
import kotlin.math.roundToInt
import kotlinx.coroutines.launch

/**
 * Max [RemoteCanvasProgramProto.wire_opset_version] this build understands.
 * Bump only when interpreter semantics change incompatibly (not for every new opcode — additive ops are skipped by old binaries only after rebuild; on-wire unknown enum values are ignored).
 */
private const val SupportedWireOpsetVersion: Int = 1

/** Canvas vector transform stack; mirrors AndroidX `RemoteCanvas` translate / scale / rotate / transform(Matrix) ordering (outer = earlier push). */
private sealed class CanvasTransformFrame {
    data class Rotate(
        val pivotPx: Offset,
        val degrees: Float,
    ) : CanvasTransformFrame()

    data class Translate(
        val dxPx: Float,
        val dyPx: Float,
    ) : CanvasTransformFrame()

    data class Scale(
        val pivotPx: Offset,
        val sx: Float,
        val sy: Float,
    ) : CanvasTransformFrame()

    data class MatrixConcat(
        val matrix: Matrix,
    ) : CanvasTransformFrame()
}

private fun copyTransformFrame(f: CanvasTransformFrame): CanvasTransformFrame =
    when (f) {
        is CanvasTransformFrame.Rotate -> f.copy()
        is CanvasTransformFrame.Translate -> f.copy()
        is CanvasTransformFrame.Scale -> f.copy()
        is CanvasTransformFrame.MatrixConcat ->
            CanvasTransformFrame.MatrixConcat(Matrix(f.matrix.values.copyOf()))
    }

private fun effectiveAxisScale(v: Float): Float =
    if (!v.isFinite() || v == 0f) {
        1f
    } else {
        v
    }

private data class CanvasDpRect(
    val l: Float,
    val t: Float,
    val r: Float,
    val b: Float,
) {
    fun isEmpty(): Boolean = r <= l || b <= t

    fun intersect(o: CanvasDpRect): CanvasDpRect {
        val nl = maxOf(l, o.l)
        val nt = maxOf(t, o.t)
        val nr = minOf(r, o.r)
        val nb = minOf(b, o.b)
        return CanvasDpRect(nl, nt, nr, nb)
    }
}

private fun CanvasDpRect.offsetBy(dxDp: Float, dyDp: Float): CanvasDpRect =
    CanvasDpRect(l + dxDp, t + dyDp, r + dxDp, b + dyDp)

private data class CanvasStateSnapshot(
    val clips: List<CanvasClipEntry>,
    val transforms: List<CanvasTransformFrame>,
)

private fun duplicateClipPath(path: Path): Path =
    Path().apply { addPath(path) }

private fun copyClipEntry(e: CanvasClipEntry): CanvasClipEntry =
    when (e) {
        is CanvasClipEntry.RectClip -> e.copy()
        is CanvasClipEntry.PolyClip -> e.copy(path = duplicateClipPath(e.path))
    }

private fun snapshotClipStack(stack: List<CanvasClipEntry>): List<CanvasClipEntry> =
    stack.map(::copyClipEntry)

/** Rect or polygon clip (path in px at push time). Text/pointer: Intersect uses bounds; Difference pushes null to keep pop sync (bounds not modeled). */
private sealed class CanvasClipEntry {
    data class RectClip(
        val rect: CanvasDpRect,
        val clipOp: ClipOp,
    ) : CanvasClipEntry()

    data class PolyClip(
        val path: Path,
        val boundsDp: CanvasDpRect,
        val clipOp: ClipOp,
    ) : CanvasClipEntry()
}

private fun fullCanvasDp(widthDp: Float, heightDp: Float): CanvasDpRect =
    CanvasDpRect(0f, 0f, widthDp, heightDp)

private fun boundsFromPolylineDp(xy: List<Float>): CanvasDpRect? {
    if (xy.size < 4 || xy.size % 2 != 0) return null
    var minX = xy[0]
    var maxX = xy[0]
    var minY = xy[1]
    var maxY = xy[1]
    var i = 2
    while (i < xy.size) {
        val x = xy[i]
        val y = xy[i + 1]
        minX = minOf(minX, x)
        maxX = maxOf(maxX, x)
        minY = minOf(minY, y)
        maxY = maxOf(maxY, y)
        i += 2
    }
    return CanvasDpRect(minX, minY, maxX, maxY)
}

private fun clipOpFromProto(v: Int): ClipOp =
    when (v) {
        1 -> ClipOp.Difference
        else -> ClipOp.Intersect
    }

private fun combinedClipDp(
    stack: List<CanvasDpRect?>,
    widthDp: Float,
    heightDp: Float,
): CanvasDpRect {
    val full = fullCanvasDp(widthDp, heightDp)
    if (stack.isEmpty()) return full
    var acc: CanvasDpRect? = null
    for (r in stack) {
        if (r == null) continue
        acc = if (acc == null) r else acc.intersect(r)
    }
    return (acc ?: full).intersect(full)
}

private fun RemoteCanvasOpProto.toDpRect(): CanvasDpRect =
    CanvasDpRect(rect_l_dp, rect_t_dp, rect_r_dp, rect_b_dp)

/**
 * rx/ry in px for [drawRoundRect]. Null → axis-aligned rect.
 * Uniform (`cornerRadiusYDp` ≤ 0): same cap on both axes, `min(r, min(w,h)/2)` (legacy).
 * Non-uniform: cap rx by `w/2`, ry by `h/2` (matches separate rx/ry round rects).
 */
/** Clip entry for rounded rect: axis rect if radii degenerate, else `clipPath` of [RoundRect]. */
private fun DrawScope.roundRectClipEntryFromOp(op: RemoteCanvasOpProto): CanvasClipEntry? {
    val r = op.toDpRect()
    if (r.isEmpty()) return null
    val left = op.rect_l_dp.dp.toPx()
    val top = op.rect_t_dp.dp.toPx()
    val right = op.rect_r_dp.dp.toPx()
    val bottom = op.rect_b_dp.dp.toPx()
    if (right <= left || bottom <= top) return null
    val rw = right - left
    val rh = bottom - top
    val radii = roundRectCornerRadiusPx(op.corner_radius_dp, op.corner_radius_y_dp, rw, rh)
    val co = clipOpFromProto(op.clip_op)
    if (radii == null) {
        return CanvasClipEntry.RectClip(rect = r, clipOp = co)
    }
    val (rxPx, ryPx) = radii
    val rr = RoundRect(rect = Rect(left, top, right, bottom), cornerRadius = CornerRadius(rxPx, ryPx))
    val p = Path()
    p.addRoundRect(rr)
    return CanvasClipEntry.PolyClip(
        path = p,
        boundsDp = r,
        clipOp = co,
    )
}

private fun Density.roundRectCornerRadiusPx(
    cornerRadiusDp: Float,
    cornerRadiusYDp: Float,
    widthPx: Float,
    heightPx: Float,
): Pair<Float, Float>? {
    var rxDp = cornerRadiusDp.coerceAtLeast(0f)
    var ryDp = if (cornerRadiusYDp > 0f) cornerRadiusYDp.coerceAtLeast(0f) else rxDp
    if (rxDp <= 0f && ryDp > 0f) rxDp = ryDp
    if (ryDp <= 0f && rxDp > 0f) ryDp = rxDp
    if (rxDp <= 0f && ryDp <= 0f) return null
    val rxUnc = rxDp.dp.toPx()
    val ryUnc = ryDp.dp.toPx()
    return if (cornerRadiusYDp <= 0f) {
        val cap = minOf(rxUnc, ryUnc, min(widthPx, heightPx) / 2f).coerceAtLeast(0f)
        Pair(cap, cap)
    } else {
        Pair(
            min(rxUnc, widthPx / 2f).coerceAtLeast(0f),
            min(ryUnc, heightPx / 2f).coerceAtLeast(0f),
        )
    }
}

private fun Density.strokeWidthPx(op: RemoteCanvasOpProto): Float {
    val d = if (op.stroke_width_dp > 0f) op.stroke_width_dp else 1f
    return d.dp.toPx()
}

private fun strokeJoinFromProto(v: Int): StrokeJoin =
    when (v) {
        1 -> StrokeJoin.Round
        2 -> StrokeJoin.Bevel
        else -> StrokeJoin.Miter
    }

private fun strokeCapFromProto(v: Int): StrokeCap =
    when (v) {
        1 -> StrokeCap.Round
        2 -> StrokeCap.Square
        else -> StrokeCap.Butt
    }

private fun DrawScope.strokeDashPathEffectOrNull(op: RemoteCanvasOpProto): PathEffect? {
    if (!op.stroke_dash_enabled) return null
    val onPx = op.stroke_dash_on_dp.dp.toPx()
    val offPx = op.stroke_dash_off_dp.dp.toPx()
    if (!onPx.isFinite() || !offPx.isFinite() || onPx <= 0f || offPx <= 0f) return null
    val phasePx = op.stroke_dash_phase_dp.dp.toPx().takeIf { it.isFinite() } ?: 0f
    return PathEffect.dashPathEffect(intervals = floatArrayOf(onPx, offPx), phase = phasePx)
}

private fun DrawScope.strokeStyleFor(op: RemoteCanvasOpProto): Stroke =
    Stroke(
        width = strokeWidthPx(op),
        cap = strokeCapFromProto(op.stroke_cap),
        join = strokeJoinFromProto(op.stroke_join),
        pathEffect = strokeDashPathEffectOrNull(op),
    )

private fun fontWeightFromProto(w: Int): FontWeight =
    if (w <= 0) {
        FontWeight.Normal
    } else {
        FontWeight(w.coerceIn(100, 1000))
    }

private fun textAlignFromProto(v: Int): TextAlign =
    when (v) {
        1 -> TextAlign.Center
        2 -> TextAlign.Right
        else -> TextAlign.Left
    }

private fun alignmentFromTextVertical(v: Int): Alignment =
    when (v) {
        1 -> Alignment.Center
        2 -> Alignment.BottomCenter
        else -> Alignment.TopStart
    }

private fun boundsFromPathProtoDp(proto: RemoteCanvasPathProto): CanvasDpRect? {
    val verbs = proto.verbs
    val c = proto.coords_dp
    if (verbs.isEmpty()) return null
    if (verbs.firstOrNull() != RemoteCanvasPathVerbProto.REMOTE_PATH_VERB_MOVE_TO) return null
    var i = 0
    var has = false
    var minX = 0f
    var maxX = 0f
    var minY = 0f
    var maxY = 0f
    fun acc(x: Float, y: Float) {
        if (!has) {
            minX = x
            maxX = x
            minY = y
            maxY = y
            has = true
        } else {
            minX = min(minX, x)
            maxX = max(maxX, x)
            minY = min(minY, y)
            maxY = max(maxY, y)
        }
    }
    for (verb in verbs) {
        when (verb) {
            RemoteCanvasPathVerbProto.REMOTE_PATH_VERB_UNSPECIFIED -> return null
            RemoteCanvasPathVerbProto.REMOTE_PATH_VERB_MOVE_TO,
            RemoteCanvasPathVerbProto.REMOTE_PATH_VERB_LINE_TO,
            -> {
                if (i + 2 > c.size) return null
                acc(c[i], c[i + 1])
                i += 2
            }

            RemoteCanvasPathVerbProto.REMOTE_PATH_VERB_QUADRATIC_TO -> {
                if (i + 4 > c.size) return null
                acc(c[i], c[i + 1])
                acc(c[i + 2], c[i + 3])
                i += 4
            }

            RemoteCanvasPathVerbProto.REMOTE_PATH_VERB_CUBIC_TO -> {
                if (i + 6 > c.size) return null
                acc(c[i], c[i + 1])
                acc(c[i + 2], c[i + 3])
                acc(c[i + 4], c[i + 5])
                i += 6
            }

            RemoteCanvasPathVerbProto.REMOTE_PATH_VERB_CLOSE -> Unit
            else -> return null
        }
    }
    if (!has) return null
    return CanvasDpRect(minX, minY, maxX, maxY)
}

private fun DrawScope.pathFromRemoteProto(proto: RemoteCanvasPathProto): Path? {
    val verbs = proto.verbs
    val c = proto.coords_dp
    if (verbs.isEmpty()) return null
    if (verbs.firstOrNull() != RemoteCanvasPathVerbProto.REMOTE_PATH_VERB_MOVE_TO) return null
    val p = Path()
    var i = 0
    for (verb in verbs) {
        when (verb) {
            RemoteCanvasPathVerbProto.REMOTE_PATH_VERB_UNSPECIFIED -> return null
            RemoteCanvasPathVerbProto.REMOTE_PATH_VERB_MOVE_TO -> {
                if (i + 2 > c.size) return null
                p.moveTo(c[i].dp.toPx(), c[i + 1].dp.toPx())
                i += 2
            }

            RemoteCanvasPathVerbProto.REMOTE_PATH_VERB_LINE_TO -> {
                if (i + 2 > c.size) return null
                p.lineTo(c[i].dp.toPx(), c[i + 1].dp.toPx())
                i += 2
            }

            RemoteCanvasPathVerbProto.REMOTE_PATH_VERB_QUADRATIC_TO -> {
                if (i + 4 > c.size) return null
                p.quadraticBezierTo(
                    c[i].dp.toPx(),
                    c[i + 1].dp.toPx(),
                    c[i + 2].dp.toPx(),
                    c[i + 3].dp.toPx(),
                )
                i += 4
            }

            RemoteCanvasPathVerbProto.REMOTE_PATH_VERB_CUBIC_TO -> {
                if (i + 6 > c.size) return null
                p.cubicTo(
                    c[i].dp.toPx(),
                    c[i + 1].dp.toPx(),
                    c[i + 2].dp.toPx(),
                    c[i + 3].dp.toPx(),
                    c[i + 4].dp.toPx(),
                    c[i + 5].dp.toPx(),
                )
                i += 6
            }

            RemoteCanvasPathVerbProto.REMOTE_PATH_VERB_CLOSE -> p.close()
            else -> return null
        }
    }
    return p
}

private fun DrawScope.polylineToPath(
    xy: List<Float>,
    close: Boolean,
): Path? {
    if (xy.size < 4 || xy.size % 2 != 0) return null
    val p = Path()
    p.moveTo(xy[0].dp.toPx(), xy[1].dp.toPx())
    var i = 2
    while (i < xy.size) {
        p.lineTo(xy[i].dp.toPx(), xy[i + 1].dp.toPx())
        i += 2
    }
    if (close) {
        p.close()
    }
    return p
}

private fun DrawScope.withClipStack(
    clipStack: List<CanvasClipEntry>,
    widthDp: Float,
    heightDp: Float,
    draw: DrawScope.() -> Unit,
) {
    if (clipStack.isEmpty()) {
        draw()
        return
    }
    val full = fullCanvasDp(widthDp, heightDp)
    fun DrawScope.nested(i: Int) {
        if (i >= clipStack.size) {
            draw()
            return
        }
        when (val e = clipStack[i]) {
            is CanvasClipEntry.RectClip -> {
                val r = e.rect.intersect(full)
                if (r.isEmpty()) return
                val l = r.l.dp.toPx()
                val t = r.t.dp.toPx()
                val ri = r.r.dp.toPx()
                val b = r.b.dp.toPx()
                if (ri <= l || b <= t) return
                clipRect(left = l, top = t, right = ri, bottom = b, clipOp = e.clipOp) {
                    nested(i + 1)
                }
            }

            is CanvasClipEntry.PolyClip ->
                clipPath(path = e.path, clipOp = e.clipOp) {
                    nested(i + 1)
                }
        }
    }
    nested(0)
}

private fun DrawScope.withTransformStack(
    transformStack: List<CanvasTransformFrame>,
    draw: DrawScope.() -> Unit,
) {
    fun DrawScope.nested(i: Int) {
        if (i >= transformStack.size) {
            draw()
            return
        }
        when (val frame = transformStack[i]) {
            is CanvasTransformFrame.Rotate ->
                drawScopeRotate(degrees = frame.degrees, pivot = frame.pivotPx) {
                    nested(i + 1)
                }

            is CanvasTransformFrame.Translate ->
                translate(left = frame.dxPx, top = frame.dyPx) {
                    nested(i + 1)
                }

            is CanvasTransformFrame.Scale ->
                scale(scaleX = frame.sx, scaleY = frame.sy, pivot = frame.pivotPx) {
                    nested(i + 1)
                }

            is CanvasTransformFrame.MatrixConcat ->
                withTransform(transformBlock = { transform(frame.matrix) }) {
                    nested(i + 1)
                }
        }
    }
    nested(0)
}

private fun DrawScope.withClipAndTransform(
    clipStack: List<CanvasClipEntry>,
    transformStack: List<CanvasTransformFrame>,
    widthDp: Float,
    heightDp: Float,
    draw: DrawScope.() -> Unit,
) {
    withClipStack(clipStack, widthDp, heightDp) {
        withTransformStack(transformStack, draw)
    }
}

/** Line height in **sp** for DRAW_TEXT / DRAW_TEXT_AT layout hints and optional [TextStyle.lineHeight]. */
private fun remoteCanvasTextLineHeightSpForLayout(op: RemoteCanvasOpProto): Float {
    val fontSp = if (op.font_size_sp > 0f) op.font_size_sp else 12f
    return if (op.text_line_height_sp > 0f) op.text_line_height_sp else fontSp * 1.35f
}

/** Decodes [RemoteCanvasOpProto.image_png_bytes] per op; failures are skipped (same as empty). Test payloads: [com.podca.sdui.remote.core.RemoteCanvasWireFixtures]. */
private fun decodePngImageBitmapsByOpIndex(ops: List<RemoteCanvasOpProto>): Map<Int, ImageBitmap> =
    ops.mapIndexedNotNull { idx, op ->
        if (op.code != RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_DRAW_IMAGE) return@mapIndexedNotNull null
        if (op.image_png_bytes.size == 0) return@mapIndexedNotNull null
        val bmp =
            runCatching {
                op.image_png_bytes.toByteArray().decodeToImageBitmap()
            }.getOrNull() ?: return@mapIndexedNotNull null
        idx to bmp
    }.toMap()

/** Wire [RemoteCanvasOpProto.draw_image_filter_quality]: 0 = default Medium, 1 = Low, 2 = Medium, 3 = High. */
private fun drawImageFilterQualityFromWire(quality: Int): FilterQuality =
    when (quality.coerceIn(0, 3)) {
        1 -> FilterQuality.Low
        2 -> FilterQuality.Medium
        3 -> FilterQuality.High
        else -> FilterQuality.Medium
    }

private fun pushConditionalStack(
    stack: MutableList<Boolean>,
    op: RemoteCanvasOpProto,
    resolveRemoteFloat: (String) -> Float?,
) {
    stack.add(evalRemoteCanvasConditionalBeginBranch(op, stack, resolveRemoteFloat))
}

private fun mergedRoundedPolygonXyDp(op: RemoteCanvasOpProto): List<Float> {
    val base = op.polyline?.xy_dp ?: emptyList()
    val morph = op.rounded_polygon_morph_polyline?.xy_dp
    if (morph.isNullOrEmpty()) return base
    return lerpClosedPolylineXyDpOrNull(base, morph, op.rounded_polygon_morph_t) ?: base
}

private const val MaxTextOnPathGraphemes: Int = 256

private fun Char.isUtf16HighSurrogate(): Boolean = this in '\uD800'..'\uDBFF'

private fun Char.isUtf16LowSurrogate(): Boolean = this in '\uDC00'..'\uDFFF'

private fun utf16CodeUnitLengthAt(text: String, index: Int): Int {
    if (index !in text.indices) return 0
    val head = text[index]
    return if (
        head.isUtf16HighSurrogate() &&
        index + 1 < text.length &&
        text[index + 1].isUtf16LowSurrogate()
    ) {
        2
    } else {
        1
    }
}

private fun graphemeStringsForTextOnPath(
    body: String,
    max: Int,
): List<String> {
    val out = ArrayList<String>(body.length.coerceAtMost(max))
    var i = 0
    while (i < body.length && out.size < max) {
        val n = utf16CodeUnitLengthAt(body, i)
        out.add(body.substring(i, i + n))
        i += n
    }
    return out
}

@Composable
private fun RemoteCanvasTextOnPathGlyphOverlays(
    op: RemoteCanvasOpProto,
    widthDp: Float,
    heightDp: Float,
    clipStackText: List<CanvasDpRect?>,
    origin: Pair<Float, Float>?,
    textMeasurer: TextMeasurer,
) {
    if (op.text_body.isEmpty()) return
    val protoPath = op.path ?: return
    val xyDp = openPolylineXyDpFromPathProtoMoveLineOnly(protoPath) ?: return
    val pathBounds = boundsFromPathProtoDp(protoPath) ?: return
    val ox = origin?.first ?: 0f
    val oy = origin?.second ?: 0f
    val shifted = pathBounds.offsetBy(ox, oy)
    val bounds = shifted.intersect(combinedClipDp(clipStackText, widthDp, heightDp))
    if (bounds.isEmpty()) return
    val density = LocalDensity.current
    val pxPerDp = density.density
    val fontSp = if (op.font_size_sp > 0f) op.font_size_sp else 12f
    val textStyle =
        TextStyle(
            color = Color(op.color_argb),
            fontSize = fontSp.sp,
            fontWeight = fontWeightFromProto(op.font_weight),
            textAlign = TextAlign.Left,
            lineHeight =
                if (op.text_line_height_sp > 0f) {
                    op.text_line_height_sp.sp
                } else {
                    TextUnit.Unspecified
                },
        )
    val graphemes = remember(op.text_body) { graphemeStringsForTextOnPath(op.text_body, MaxTextOnPathGraphemes) }
    val totalPathPx = polylineTotalLengthDp(xyDp) * pxPerDp
    val startOffDp =
        if (op.text_on_path_start_offset_dp.isFinite()) {
            op.text_on_path_start_offset_dp
        } else {
            0f
        }
    val startOffPx = (startOffDp * pxPerDp).coerceIn(0f, totalPathPx.coerceAtLeast(0f))
    val widthsPx =
        remember(graphemes, textStyle, textMeasurer) {
            graphemes.map { g ->
                val layout =
                    textMeasurer.measure(
                        AnnotatedString(g),
                        textStyle,
                        overflow = TextOverflow.Visible,
                        softWrap = false,
                        maxLines = 1,
                        constraints = Constraints(maxWidth = Int.MAX_VALUE),
                    )
                layout.size.width.toFloat()
            }
        }
    val totalTextPx = widthsPx.sum().coerceAtLeast(0f)
    val startPx =
        startOffPx +
            when (op.text_align) {
                1 -> max(0f, (totalPathPx - totalTextPx) / 2f)
                2 -> max(0f, totalPathPx - totalTextPx)
                else -> 0f
            }
    val startPxClamped = startPx.coerceIn(0f, totalPathPx.coerceAtLeast(1e-6f))
    var cursorPx = startPxClamped
    val fw = bounds.r - bounds.l
    val fh = bounds.b - bounds.t
    Box(
        modifier =
            Modifier
                .offset(bounds.l.dp, bounds.t.dp)
                .size(fw.dp, fh.dp)
                .clip(RoundedCornerShape(0)),
    ) {
        graphemes.forEachIndexed { ix, g ->
            val w = widthsPx.getOrElse(ix) { 0f }
            if (w <= 0f) return@forEachIndexed
            val midPx = cursorPx + w * 0.5f
            val midDp = midPx / pxPerDp
            val pt =
                pointAndTangentOnOpenPolylineAtLengthDp(xyDp, midDp)
                    ?: return@forEachIndexed
            val lx = pt.xDp + ox - bounds.l
            val ly = pt.yDp + oy - bounds.t
            val layoutOne =
                textMeasurer.measure(
                    AnnotatedString(g),
                    textStyle,
                    overflow = TextOverflow.Visible,
                    softWrap = false,
                    maxLines = 1,
                    constraints = Constraints(maxWidth = Int.MAX_VALUE),
                )
            val baseline = layoutOne.getLineBaseline(0).roundToInt()
            val deg = pt.tangentRadians * 180f / PI.toFloat()
            Box(
                modifier =
                    Modifier
                        .offset(lx.dp, ly.dp)
                        .rotate(deg),
            ) {
                BasicText(
                    text = g,
                    style = textStyle,
                    maxLines = 1,
                    overflow = TextOverflow.Clip,
                    modifier =
                        Modifier.offset {
                            IntOffset(0, -baseline)
                        },
                )
            }
            cursorPx = (cursorPx + w).coerceAtMost(totalPathPx)
        }
    }
}

@Composable
private fun RemoteCanvasTextOnCircleGlyphOverlays(
    op: RemoteCanvasOpProto,
    widthDp: Float,
    heightDp: Float,
    clipStackText: List<CanvasDpRect?>,
    origin: Pair<Float, Float>?,
    textMeasurer: TextMeasurer,
) {
    if (op.text_body.isEmpty()) return
    val r0 = op.circle_radius_dp
    if (!r0.isFinite() || r0 <= 0f) return
    val warp =
        if (op.text_on_circle_warp_radius_offset_dp.isFinite()) {
            op.text_on_circle_warp_radius_offset_dp
        } else {
            0f
        }
    val rEff = r0 + warp
    if (!rEff.isFinite() || rEff <= 0f) return
    val cx = op.rect_l_dp
    val cy = op.rect_t_dp
    val ox = origin?.first ?: 0f
    val oy = origin?.second ?: 0f
    val fontSp = if (op.font_size_sp > 0f) op.font_size_sp else 12f
    val pad = fontSp * 2f
    val outer = max(rEff, r0 + abs(warp)) + pad
    val raw = CanvasDpRect(cx - outer, cy - outer, cx + outer, cy + outer)
    val shifted = raw.offsetBy(ox, oy)
    val bounds = shifted.intersect(combinedClipDp(clipStackText, widthDp, heightDp))
    if (bounds.isEmpty()) return
    val density = LocalDensity.current
    val pxPerDp = density.density
    val textStyle =
        TextStyle(
            color = Color(op.color_argb),
            fontSize = fontSp.sp,
            fontWeight = fontWeightFromProto(op.font_weight),
            textAlign = TextAlign.Left,
            lineHeight =
                if (op.text_line_height_sp > 0f) {
                    op.text_line_height_sp.sp
                } else {
                    TextUnit.Unspecified
                },
        )
    val graphemes = remember(op.text_body) { graphemeStringsForTextOnPath(op.text_body, MaxTextOnPathGraphemes) }
    val totalPathDp = circleArcLayoutLengthDp(rEff, op.arc_sweep_deg)
    if (totalPathDp <= 0f) return
    val totalPathPx = totalPathDp * pxPerDp
    val startOffDp =
        if (op.text_on_path_start_offset_dp.isFinite()) {
            op.text_on_path_start_offset_dp
        } else {
            0f
        }
    val startOffPx = (startOffDp * pxPerDp).coerceIn(0f, totalPathPx.coerceAtLeast(0f))
    val widthsPx =
        remember(graphemes, textStyle, textMeasurer) {
            graphemes.map { g ->
                val layout =
                    textMeasurer.measure(
                        AnnotatedString(g),
                        textStyle,
                        overflow = TextOverflow.Visible,
                        softWrap = false,
                        maxLines = 1,
                        constraints = Constraints(maxWidth = Int.MAX_VALUE),
                    )
                layout.size.width.toFloat()
            }
        }
    val totalTextPx = widthsPx.sum().coerceAtLeast(0f)
    val startPx =
        startOffPx +
            when (op.text_align) {
                1 -> max(0f, (totalPathPx - totalTextPx) / 2f)
                2 -> max(0f, totalPathPx - totalTextPx)
                else -> 0f
            }
    val startPxClamped = startPx.coerceIn(0f, totalPathPx.coerceAtLeast(1e-6f))
    var cursorPx = startPxClamped
    val fw = bounds.r - bounds.l
    val fh = bounds.b - bounds.t
    Box(
        modifier =
            Modifier
                .offset(bounds.l.dp, bounds.t.dp)
                .size(fw.dp, fh.dp)
                .clip(RoundedCornerShape(0)),
    ) {
        graphemes.forEachIndexed { ix, g ->
            val w = widthsPx.getOrElse(ix) { 0f }
            if (w <= 0f) return@forEachIndexed
            val midPx = cursorPx + w * 0.5f
            val midDp = midPx / pxPerDp
            val pt =
                pointAndTangentOnCircleAtArcLengthDp(
                    cx,
                    cy,
                    rEff,
                    op.arc_start_deg,
                    op.arc_sweep_deg,
                    midDp,
                ) ?: return@forEachIndexed
            val lx = pt.xDp + ox - bounds.l
            val ly = pt.yDp + oy - bounds.t
            val layoutOne =
                textMeasurer.measure(
                    AnnotatedString(g),
                    textStyle,
                    overflow = TextOverflow.Visible,
                    softWrap = false,
                    maxLines = 1,
                    constraints = Constraints(maxWidth = Int.MAX_VALUE),
                )
            val baseline = layoutOne.getLineBaseline(0).roundToInt()
            val deg = pt.tangentRadians * 180f / PI.toFloat()
            Box(
                modifier =
                    Modifier
                        .offset(lx.dp, ly.dp)
                        .rotate(deg),
            ) {
                BasicText(
                    text = g,
                    style = textStyle,
                    maxLines = 1,
                    overflow = TextOverflow.Clip,
                    modifier =
                        Modifier.offset {
                            IntOffset(0, -baseline)
                        },
                )
            }
            cursorPx = (cursorPx + w).coerceAtMost(totalPathPx)
        }
    }
}

@Composable
private fun RemoteCanvasAnchoredTextOverlay(
    op: RemoteCanvasOpProto,
    widthDp: Float,
    heightDp: Float,
    clipStackText: List<CanvasDpRect?>,
    origin: Pair<Float, Float>?,
    textMeasurer: TextMeasurer,
) {
    if (op.text_body.isEmpty()) return
    val density = LocalDensity.current
    val pxPerDp = density.density
    val ox = origin?.first ?: 0f
    val oy = origin?.second ?: 0f
    val ax = op.rect_l_dp + ox
    val ay = op.rect_t_dp + oy
    val panX = if (op.text_anchor_pan_x_dp.isFinite()) op.text_anchor_pan_x_dp else 0f
    val panY = if (op.text_anchor_pan_y_dp.isFinite()) op.text_anchor_pan_y_dp else 0f
    val aax = ax + panX
    val aay = ay + panY
    val fontSp = if (op.font_size_sp > 0f) op.font_size_sp else 12f
    val lineHDp = remoteCanvasTextLineHeightSpForLayout(op).coerceAtLeast(1f)
    val maxW =
        if (op.rect_r_dp > 0f) {
            op.rect_r_dp
        } else {
            (widthDp - ax).coerceAtLeast(1f)
        }
    val maxH =
        if (op.rect_b_dp > 0f) {
            op.rect_b_dp
        } else {
            (lineHDp * 5f).coerceAtLeast(lineHDp)
        }
    val maxWpx = (maxW * pxPerDp).roundToInt().coerceAtLeast(1)
    val maxLines = (maxH / lineHDp).toInt().coerceIn(1, 10)
    val textStyle =
        TextStyle(
            color = Color(op.color_argb),
            fontSize = fontSp.sp,
            fontWeight = fontWeightFromProto(op.font_weight),
            textAlign = TextAlign.Start,
            lineHeight =
                if (op.text_line_height_sp > 0f) {
                    op.text_line_height_sp.sp
                } else {
                    TextUnit.Unspecified
                },
        )
    val measured =
        remember(op.text_body, textStyle, maxWpx, maxLines, textMeasurer) {
            textMeasurer.measure(
                AnnotatedString(op.text_body),
                textStyle,
                overflow = TextOverflow.Ellipsis,
                softWrap = true,
                maxLines = maxLines,
                constraints = Constraints(maxWidth = maxWpx),
            )
        }
    val wPx = measured.size.width.toFloat()
    val hPx = measured.size.height.toFloat()
    val wDp = wPx / pxPerDp
    val hDp = hPx / pxPerDp
    val baseline0Dp = measured.getLineBaseline(0) / pxPerDp
    val lineCenterXDp =
        if (measured.lineCount > 0) {
            (measured.getLineLeft(0) + measured.getLineRight(0)) / (2f * pxPerDp)
        } else {
            0f
        }
    val flagMode =
        when (op.draw_anchored_text_flags) {
            1 -> 1
            2 -> 2
            3 -> 3
            else -> 0
        }
    val (boxLeftDp, boxTopDp) =
        when (flagMode) {
            1 -> (aax - wDp / 2f) to (aay - hDp / 2f)
            2 -> aax to (aay - baseline0Dp)
            3 -> (aax - lineCenterXDp) to (aay - baseline0Dp)
            else -> aax to aay
        }
    val raw = CanvasDpRect(boxLeftDp, boxTopDp, boxLeftDp + maxW, boxTopDp + maxH)
    val bounds = raw.intersect(combinedClipDp(clipStackText, widthDp, heightDp))
    if (bounds.isEmpty()) return
    RemoteCanvasBasicTextOverlay(
        bounds = bounds,
        body = op.text_body,
        colorArgb = op.color_argb,
        fontSizeSp = op.font_size_sp,
        fontWeightProto = op.font_weight,
        textAlignProto = 0,
        textAlignVerticalProto = 0,
        maxLines = maxLines,
        textLineHeightWireSp = op.text_line_height_sp,
        rectTopIsFirstBaseline = false,
        rectBottomIsLastBaseline = op.draw_text_rect_bottom_is_last_baseline,
        forceRtlTextDirection = false,
        lineHeightStyleWire = op.draw_text_line_height_style,
        disableFontPadding = op.draw_text_disable_font_padding,
    )
}

@Composable
private fun RemoteCanvasBasicTextOverlay(
    bounds: CanvasDpRect,
    body: String,
    colorArgb: Int,
    fontSizeSp: Float,
    fontWeightProto: Int,
    textAlignProto: Int,
    textAlignVerticalProto: Int,
    maxLines: Int,
    /** When > 0, sets [TextStyle.lineHeight] in sp; otherwise Compose default line height from font. */
    textLineHeightWireSp: Float = 0f,
    /** When true, [bounds.t] is the first line baseline Y (dp); see [RemoteCanvasOpProto.draw_text_rect_top_is_first_baseline]. */
    rectTopIsFirstBaseline: Boolean = false,
    /** When true and [rectTopIsFirstBaseline] is false, [bounds.b] is the last line baseline Y (dp); see [RemoteCanvasOpProto.draw_text_rect_bottom_is_last_baseline]. */
    rectBottomIsLastBaseline: Boolean = false,
    /** When true, [TextStyle.textDirection] is [TextDirection.Rtl] (e.g. [RemoteCanvasOpProto.draw_text_run_is_rtl]). */
    forceRtlTextDirection: Boolean = false,
    /**
     * When [drawTextRunVisibleStartUtf16] >= 0, [body] is laid out as a whole (shaping / ligatures across the full buffer)
     * but only UTF-16 indices in `[drawTextRunVisibleStartUtf16, drawTextRunVisibleEndExclusiveUtf16)` use [colorArgb];
     * outside uses [Color.Transparent] (AndroidX `drawTextRun` context window on the same buffer).
     */
    drawTextRunVisibleStartUtf16: Int = -1,
    drawTextRunVisibleEndExclusiveUtf16: Int = -1,
    /** Wire [RemoteCanvasOpProto.draw_text_line_height_style]; applied only when [textLineHeightWireSp] > 0. */
    lineHeightStyleWire: Int = 0,
    /** Wire [RemoteCanvasOpProto.draw_text_disable_font_padding] (Android applies `includeFontPadding = false`; other targets v1 no-op). */
    disableFontPadding: Boolean = false,
) {
    if (body.isEmpty()) return
    val fw = bounds.r - bounds.l
    val fh = bounds.b - bounds.t
    if (fw <= 0f || fh <= 0f) return
    val fontSp = if (fontSizeSp > 0f) fontSizeSp else 12f
    val density = LocalDensity.current

    val baselineShiftPx = remember { mutableFloatStateOf(0f) }
    LaunchedEffect(
        rectTopIsFirstBaseline,
        rectBottomIsLastBaseline,
        body,
        bounds.l,
        bounds.t,
        bounds.r,
        bounds.b,
        fontSp,
        fontWeightProto,
        textAlignProto,
        textLineHeightWireSp,
        maxLines,
        colorArgb,
        forceRtlTextDirection,
        drawTextRunVisibleStartUtf16,
        drawTextRunVisibleEndExclusiveUtf16,
        lineHeightStyleWire,
        disableFontPadding,
        density.density,
    ) {
        baselineShiftPx.floatValue = 0f
    }

    val platformPaddingStyle = remoteCanvasTextPlatformStyleForDisableFontPadding(disableFontPadding)

    val lineHeightStyleResolved =
        if (textLineHeightWireSp > 0f) {
            remoteCanvasLineHeightStyleFromWire(lineHeightStyleWire)
        } else {
            null
        }

    val useContextMask = drawTextRunVisibleStartUtf16 >= 0
    val maskStart = drawTextRunVisibleStartUtf16.coerceIn(0, body.length)
    val maskEnd = drawTextRunVisibleEndExclusiveUtf16.coerceIn(maskStart, body.length)
    val partialContextMask =
        useContextMask && (maskStart > 0 || maskEnd < body.length)
    val textForBasicText: AnnotatedString =
        if (!partialContextMask) {
            AnnotatedString(body)
        } else {
            buildAnnotatedString {
                var i = 0
                val n = body.length
                while (i < n) {
                    val w = utf16CodeUnitLengthAt(body, i)
                    val end = i + w
                    val overlaps = i < maskEnd && maskStart < end
                    withStyle(
                        SpanStyle(
                            color =
                                if (overlaps) {
                                    Color(colorArgb)
                                } else {
                                    Color.Transparent
                                },
                        ),
                    ) {
                        append(body, i, end)
                    }
                    i = end
                }
            }
        }
    val baseTextColor =
        if (partialContextMask) {
            Color.Unspecified
        } else {
            Color(colorArgb)
        }

    val contentAlignment =
        when {
            rectTopIsFirstBaseline ->
                when (textAlignProto) {
                    1 -> Alignment.TopCenter
                    2 -> Alignment.TopEnd
                    else -> Alignment.TopStart
                }
            rectBottomIsLastBaseline ->
                when (textAlignProto) {
                    1 -> Alignment.TopCenter
                    2 -> Alignment.TopEnd
                    else -> Alignment.TopStart
                }
            else -> alignmentFromTextVertical(textAlignVerticalProto)
        }

    Box(
        modifier =
            Modifier
                .offset(bounds.l.dp, bounds.t.dp)
                .size(fw.dp, fh.dp)
                .clip(RoundedCornerShape(0)),
        contentAlignment = contentAlignment,
    ) {
        BasicText(
            text = textForBasicText,
            style =
                TextStyle(
                    color = baseTextColor,
                    fontSize = fontSp.sp,
                    fontWeight = fontWeightFromProto(fontWeightProto),
                    textAlign = textAlignFromProto(textAlignProto),
                    textDirection = if (forceRtlTextDirection) TextDirection.Rtl else TextDirection.Ltr,
                    lineHeight =
                        if (textLineHeightWireSp > 0f) {
                            textLineHeightWireSp.sp
                        } else {
                            TextUnit.Unspecified
                        },
                    lineHeightStyle = lineHeightStyleResolved,
                    platformStyle = platformPaddingStyle,
                ),
            maxLines = maxLines,
            overflow = TextOverflow.Ellipsis,
            onTextLayout = { layout ->
                if (rectTopIsFirstBaseline && layout.lineCount > 0) {
                    baselineShiftPx.floatValue = -layout.getLineBaseline(0)
                } else if (rectBottomIsLastBaseline && !rectTopIsFirstBaseline && layout.lineCount > 0) {
                    val fhPx = fh * density.density
                    val last = layout.lineCount - 1
                    baselineShiftPx.floatValue = fhPx - layout.getLineBaseline(last)
                }
            },
            modifier =
                Modifier
                    .fillMaxWidth()
                    .offset { IntOffset(0, baselineShiftPx.floatValue.roundToInt()) },
        )
    }
}

/**
 * Renders [PodcaDocumentNode] with type `remote.CanvasProgram` by decoding [RemoteCanvasProgramProto].
 * Op-stream: clip stack (rect + **round-rect** `PUSH_CLIP_ROUND_RECT` + polyline + **verb path** `PUSH_CLIP_PATH`), transform stack translate/scale/rotate and **4×4 matrix** (`PUSH_TRANSFORM_MATRIX`, 16 floats row-major as `androidx.compose.ui.graphics.Matrix`), **save/restore** of clip+transform (`PUSH_CANVAS_SAVE` / `POP_CANVAS_RESTORE`, AndroidX `save`/`restore`), **`CONDITIONAL_BEGIN` / `CONDITIONAL_END`** (literals and/or **named floats** via `PodcaRuntime.remoteCanvasConditionalFloats`; optional **`conditional_remote_float_fallback_to_literal`**; optional **`conditional_operand_*_is_wire_nan`**; AndroidX `ConditionalOperations` subset), **`STATE_LAYOUT_BEGIN` / `STATE_LAYOUT_END`** (per-state spans + optional **root** `Modifier.semantics` from first active BEGIN — `remote-core` **`remoteCanvasStateLayoutSemanticsHintsForPlayback`** + **`filterRemoteCanvasStateLayoutBlocksForPlayback`** after loop expand; active id via `PodcaRuntime.remoteCanvasStateLayoutActiveId` or defaults), **`LOOP_BEGIN` / `LOOP_END`** (literal repeat count + program **`loop_expand_max_repeat_per_block`** (**0** = **512** cap per block) — `remote-core` **`expandRemoteCanvasLoopBlocks`** expands to a flat op list **before** state-layout filter and paint-time `CONDITIONAL_*`; raising the cap so large repeats inside falsy branches still **inflate the decoded op list** — see `ANDROIDX_REMOTE_MAP.md`), **`PUSH_OFFSCREEN_RENDER` / `POP_OFFSCREEN_RENDER`** (subset of AndroidX `drawToOffscreenBitmap`: inner ops in local 0…w,0…h dp, rasterized then drawn into `rect_*` on the parent; named **`offscreen_bitmap_id`** pool cap from program **`offscreen_bitmap_pool_max_entries`** — `remote-core` **`remoteCanvasOffscreenPoolMaxEntriesFromWire`**), fills (incl. circle, sector, linear axis + radial + polyline + **`FILL_ROUNDED_POLYGON`** + **`FILL_PATH`** verb stream), strokes (+ **`STROKE_ROUNDED_POLYGON`** + **`STROKE_PATH`** + **`DRAW_TWEEN_PATH`** + optional **`tween_path_fraction_remote_float_id`** on the same named-float map as `CONDITIONAL_*`), lines, arcs, **`DRAW_IMAGE`** (PNG bytes → dst rect; optional **src rect** in px = AndroidX `drawScaledBitmap` src/dst; optional **`draw_image_alpha_*`**; optional **`draw_image_blend_mode_*`** = `BlendModeProto` ids → Compose `BlendMode`; optional **`draw_image_color_filter_tint_*`** → Compose `ColorFilter.tint`; optional **`draw_image_color_filter_lighting_*`** → `ColorFilter.lighting` (tint + lighting → **lighting** in v1); optional **`draw_image_scale_factor_*`**; optional **`draw_image_content_description`**), [BasicText] (`DRAW_TEXT` / **`DRAW_TEXT_AT`** / **`DRAW_TEXT_RUN`** / **`DRAW_TEXT_ON_PATH`** / **`DRAW_TEXT_ON_CIRCLE`** / **`DRAW_TEXT_ANCHORED`**), pointer hits → [PodcaRuntime.dispatch].
 */
@Composable
public fun PodcaRenderRemoteCanvasProgramNode(
    node: PodcaDocumentNode,
    runtime: PodcaRuntime,
) {
    val program =
        runCatching {
            RemoteCanvasProgramProto.ADAPTER.decode(node.payload.toByteArray())
        }.getOrNull() ?: return

    val w = if (program.width_dp > 0f) program.width_dp.dp else 160.dp
    val h = if (program.height_dp > 0f) program.height_dp.dp else 96.dp
    val scope = rememberCoroutineScope()
    val textMeasurer = rememberTextMeasurer()

    val widthDp = program.width_dp.coerceAtLeast(1f)
    val heightDp = program.height_dp.coerceAtLeast(1f)
    val wireOpset = if (program.wire_opset_version == 0) 1 else program.wire_opset_version
    if (wireOpset > SupportedWireOpsetVersion) {
        Box(Modifier.size(w, h))
        return
    }

    val remoteCanvasFloats by runtime.remoteCanvasConditionalFloats.collectAsState()
    val remoteCanvasStateLayoutActiveId by runtime.remoteCanvasStateLayoutActiveId.collectAsState()

    val (paintingOps, stateLayoutRootSemantics) =
        remember(node.payload, remoteCanvasStateLayoutActiveId) {
            val expanded = expandRemoteCanvasLoopBlocks(program)
            val hints = remoteCanvasStateLayoutSemanticsHintsForPlayback(expanded, remoteCanvasStateLayoutActiveId)
            val semanticsModifier =
                if (hints != null &&
                    (hints.mergeDescendants || hints.testTag.isNotEmpty() || hints.contentDescription.isNotEmpty())
                ) {
                    Modifier.semantics(mergeDescendants = hints.mergeDescendants) {
                        if (hints.testTag.isNotEmpty()) {
                            testTag = hints.testTag
                        }
                        if (hints.contentDescription.isNotEmpty()) {
                            contentDescription = hints.contentDescription
                        }
                    }
                } else {
                    Modifier
                }
            filterRemoteCanvasStateLayoutBlocksForPlayback(expanded, remoteCanvasStateLayoutActiveId) to semanticsModifier
        }

    val decodedPngByOpIndex =
        remember(node.payload, paintingOps) {
            decodePngImageBitmapsByOpIndex(paintingOps)
        }

    val offscreenBitmapPool =
        remember(node.payload) {
            OffscreenBitmapPool(maxEntries = remoteCanvasOffscreenPoolMaxEntriesFromWire(program.offscreen_bitmap_pool_max_entries))
        }

    val resolveRemoteFloat: (String) -> Float? = { id -> remoteCanvasFloats[id] }

    Box(Modifier.size(w, h).then(stateLayoutRootSemantics)) {
        Canvas(Modifier.fillMaxSize()) {
            val clipStack = mutableListOf<CanvasClipEntry>()
            val transformStack = mutableListOf<CanvasTransformFrame>()
            val canvasSaveStack = mutableListOf<CanvasStateSnapshot>()
            val conditionalStack = mutableListOf<Boolean>()
            var opIndex = 0
            canvasPaint@while (opIndex < paintingOps.size) {
                val op = paintingOps[opIndex]
                when (op.code) {
                    RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_CONDITIONAL_BEGIN -> {
                        pushConditionalStack(conditionalStack, op, resolveRemoteFloat)
                        opIndex++
                        continue@canvasPaint
                    }

                    RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_CONDITIONAL_END -> {
                        conditionalStack.removeLastOrNull()
                        opIndex++
                        continue@canvasPaint
                    }

                    else -> Unit
                }
                if (conditionalStack.isNotEmpty() && !conditionalStack.all { it }) {
                    opIndex++
                    continue@canvasPaint
                }
                when (op.code) {
                    RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_CLEAR_OFFSCREEN_BITMAP_POOL -> {
                        offscreenBitmapPool.clear()
                    }

                    RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_PUSH_OFFSCREEN_RENDER -> {
                        val popIndex =
                            findRemoteCanvasOffscreenEnd(
                                paintingOps,
                                opIndex + 1,
                                paintingOps.size,
                            )
                        if (popIndex == null) {
                            opIndex++
                            continue@canvasPaint
                        }
                        val offBmp =
                            rasterizeOffscreenAtPushIndex(
                                density = this,
                                layoutDirection = this.layoutDirection,
                                paintingOps = paintingOps,
                                pushOpIndex = opIndex,
                                innerEndExclusive = popIndex,
                                decodedPngByOpIndex = decodedPngByOpIndex,
                                pool = offscreenBitmapPool,
                                resolveRemoteFloat = resolveRemoteFloat,
                            )
                        if (offBmp != null) {
                            val left = op.rect_l_dp.dp.toPx()
                            val top = op.rect_t_dp.dp.toPx()
                            val right = op.rect_r_dp.dp.toPx()
                            val bottom = op.rect_b_dp.dp.toPx()
                            if (right > left && bottom > top) {
                                val dw = (right - left).roundToInt().coerceAtLeast(1)
                                val dh = (bottom - top).roundToInt().coerceAtLeast(1)
                                withClipAndTransform(clipStack, transformStack, widthDp, heightDp) {
                                    drawImage(
                                        image = offBmp,
                                        srcOffset = IntOffset.Zero,
                                        srcSize = IntSize(offBmp.width, offBmp.height),
                                        dstOffset = IntOffset(left.roundToInt(), top.roundToInt()),
                                        dstSize = IntSize(dw, dh),
                                        filterQuality = drawImageFilterQualityFromWire(0),
                                    )
                                }
                            }
                            if (op.offscreen_discard_pooled_bitmap_after_pop && op.offscreen_bitmap_id.isNotBlank()) {
                                offscreenBitmapPool.discardPooledId(op.offscreen_bitmap_id)
                            }
                        }
                        opIndex = popIndex + 1
                        continue@canvasPaint
                    }

                    RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_PUSH_CLIP_RECT -> {
                        val r = op.toDpRect()
                        if (!r.isEmpty()) {
                            clipStack.add(
                                CanvasClipEntry.RectClip(
                                    rect = r,
                                    clipOp = clipOpFromProto(op.clip_op),
                                ),
                            )
                        }
                    }

                    RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_PUSH_CLIP_ROUND_RECT -> {
                        val e = roundRectClipEntryFromOp(op)
                        if (e == null) {
                            opIndex++
                            continue@canvasPaint
                        }
                        clipStack.add(e)
                    }

                    RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_PUSH_CLIP_POLYLINE -> {
                        val xy = op.polyline?.xy_dp ?: emptyList()
                        if (xy.size < 6 || xy.size % 2 != 0) {
                            opIndex++
                            continue@canvasPaint
                        }
                        val path = polylineToPath(xy, close = true)
                        if (path == null) {
                            opIndex++
                            continue@canvasPaint
                        }
                        val b = boundsFromPolylineDp(xy)
                        if (b == null || b.isEmpty()) {
                            opIndex++
                            continue@canvasPaint
                        }
                        clipStack.add(
                            CanvasClipEntry.PolyClip(
                                path = path,
                                boundsDp = b,
                                clipOp = clipOpFromProto(op.clip_op),
                            ),
                        )
                    }

                    RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_PUSH_CLIP_PATH -> {
                        val protoPath = op.path
                        if (protoPath == null) {
                            opIndex++
                            continue@canvasPaint
                        }
                        val path = pathFromRemoteProto(protoPath)
                        if (path == null) {
                            opIndex++
                            continue@canvasPaint
                        }
                        val b = boundsFromPathProtoDp(protoPath)
                        if (b == null || b.isEmpty()) {
                            opIndex++
                            continue@canvasPaint
                        }
                        clipStack.add(
                            CanvasClipEntry.PolyClip(
                                path = path,
                                boundsDp = b,
                                clipOp = clipOpFromProto(op.clip_op),
                            ),
                        )
                    }

                    RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_POP_CLIP -> {
                        clipStack.removeLastOrNull()
                    }

                    RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_PUSH_TRANSLATE_DP -> {
                        transformStack.add(
                            CanvasTransformFrame.Translate(
                                dxPx = op.rect_l_dp.dp.toPx(),
                                dyPx = op.rect_t_dp.dp.toPx(),
                            ),
                        )
                    }

                    RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_PUSH_SCALE_DP -> {
                        val sx = effectiveAxisScale(op.transform_scale_x)
                        val sy = effectiveAxisScale(op.transform_scale_y)
                        transformStack.add(
                            CanvasTransformFrame.Scale(
                                pivotPx = Offset(op.rect_l_dp.dp.toPx(), op.rect_t_dp.dp.toPx()),
                                sx = sx,
                                sy = sy,
                            ),
                        )
                    }

                    RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_PUSH_ROTATE_DEG -> {
                        transformStack.add(
                            CanvasTransformFrame.Rotate(
                                pivotPx = Offset(op.rect_l_dp.dp.toPx(), op.rect_t_dp.dp.toPx()),
                                degrees = op.transform_rotate_deg,
                            ),
                        )
                    }

                    RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_PUSH_TRANSFORM_MATRIX -> {
                        val v = op.transform_matrix_values
                        if (v.size != 16) {
                            opIndex++
                            continue@canvasPaint
                        }
                        val arr = FloatArray(16) { i -> v[i] }
                        transformStack.add(CanvasTransformFrame.MatrixConcat(Matrix(arr)))
                    }

                    RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_POP_TRANSFORM -> {
                        transformStack.removeLastOrNull()
                    }

                    RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_PUSH_CANVAS_SAVE -> {
                        canvasSaveStack.add(
                            CanvasStateSnapshot(
                                clips = snapshotClipStack(clipStack),
                                transforms = transformStack.map(::copyTransformFrame),
                            ),
                        )
                    }

                    RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_POP_CANVAS_RESTORE -> {
                        val snap = canvasSaveStack.removeLastOrNull()
                        if (snap == null) {
                            opIndex++
                            continue@canvasPaint
                        }
                        clipStack.clear()
                        clipStack.addAll(snap.clips)
                        transformStack.clear()
                        transformStack.addAll(snap.transforms)
                    }

                    RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_FILL_RECT -> {
                        val left = op.rect_l_dp.dp.toPx()
                        val top = op.rect_t_dp.dp.toPx()
                        val right = op.rect_r_dp.dp.toPx()
                        val bottom = op.rect_b_dp.dp.toPx()
                        if (right <= left || bottom <= top) {
                            opIndex++
                            continue@canvasPaint
                        }
                        withClipAndTransform(clipStack, transformStack, widthDp, heightDp) {
                            drawRect(
                                color = Color(op.color_argb),
                                topLeft = Offset(left, top),
                                size = Size(right - left, bottom - top),
                            )
                        }
                    }

                    RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_DRAW_IMAGE -> {
                        val img = decodedPngByOpIndex[opIndex]
                        if (img == null) {
                            opIndex++
                            continue@canvasPaint
                        }
                        val left = op.rect_l_dp.dp.toPx()
                        val top = op.rect_t_dp.dp.toPx()
                        val right = op.rect_r_dp.dp.toPx()
                        val bottom = op.rect_b_dp.dp.toPx()
                        if (right <= left || bottom <= top) {
                            opIndex++
                            continue@canvasPaint
                        }
                        val (srcOffset, srcSize) =
                            if (op.image_src_rect_enabled) {
                                var sl = op.image_src_l_px
                                var st = op.image_src_t_px
                                var sr = op.image_src_r_px
                                var sb = op.image_src_b_px
                                if (sr <= sl || sb <= st) {
                                    opIndex++
                                    continue@canvasPaint
                                }
                                sl = sl.coerceIn(0, img.width)
                                sr = sr.coerceIn(0, img.width)
                                st = st.coerceIn(0, img.height)
                                sb = sb.coerceIn(0, img.height)
                                if (sr <= sl || sb <= st) {
                                    opIndex++
                                    continue@canvasPaint
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
                        withClipAndTransform(clipStack, transformStack, widthDp, heightDp) {
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
                    }

                    RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_FILL_ROUND_RECT -> {
                        val left = op.rect_l_dp.dp.toPx()
                        val top = op.rect_t_dp.dp.toPx()
                        val right = op.rect_r_dp.dp.toPx()
                        val bottom = op.rect_b_dp.dp.toPx()
                        if (right <= left || bottom <= top) {
                            opIndex++
                            continue@canvasPaint
                        }
                        val rw = right - left
                        val rh = bottom - top
                        val radiiPx =
                            roundRectCornerRadiusPx(
                                op.corner_radius_dp,
                                op.corner_radius_y_dp,
                                rw,
                                rh,
                            )
                        withClipAndTransform(clipStack, transformStack, widthDp, heightDp) {
                            if (radiiPx == null) {
                                drawRect(
                                    color = Color(op.color_argb),
                                    topLeft = Offset(left, top),
                                    size = Size(rw, rh),
                                )
                            } else {
                                val (rxPx, ryPx) = radiiPx
                                drawRoundRect(
                                    color = Color(op.color_argb),
                                    topLeft = Offset(left, top),
                                    size = Size(rw, rh),
                                    cornerRadius = CornerRadius(rxPx, ryPx),
                                )
                            }
                        }
                    }

                    RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_STROKE_RECT -> {
                        val left = op.rect_l_dp.dp.toPx()
                        val top = op.rect_t_dp.dp.toPx()
                        val right = op.rect_r_dp.dp.toPx()
                        val bottom = op.rect_b_dp.dp.toPx()
                        if (right <= left || bottom <= top) {
                            opIndex++
                            continue@canvasPaint
                        }
                        withClipAndTransform(clipStack, transformStack, widthDp, heightDp) {
                            drawRect(
                                color = Color(op.color_argb),
                                topLeft = Offset(left, top),
                                size = Size(right - left, bottom - top),
                                style = strokeStyleFor(op),
                            )
                        }
                    }

                    RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_STROKE_ROUND_RECT -> {
                        val left = op.rect_l_dp.dp.toPx()
                        val top = op.rect_t_dp.dp.toPx()
                        val right = op.rect_r_dp.dp.toPx()
                        val bottom = op.rect_b_dp.dp.toPx()
                        if (right <= left || bottom <= top) {
                            opIndex++
                            continue@canvasPaint
                        }
                        val rw = right - left
                        val rh = bottom - top
                        val radiiPx =
                            roundRectCornerRadiusPx(
                                op.corner_radius_dp,
                                op.corner_radius_y_dp,
                                rw,
                                rh,
                            )
                        withClipAndTransform(clipStack, transformStack, widthDp, heightDp) {
                            if (radiiPx == null) {
                                drawRect(
                                    color = Color(op.color_argb),
                                    topLeft = Offset(left, top),
                                    size = Size(rw, rh),
                                    style = strokeStyleFor(op),
                                )
                            } else {
                                val (rxPx, ryPx) = radiiPx
                                drawRoundRect(
                                    color = Color(op.color_argb),
                                    topLeft = Offset(left, top),
                                    size = Size(rw, rh),
                                    cornerRadius = CornerRadius(rxPx, ryPx),
                                    style = strokeStyleFor(op),
                                )
                            }
                        }
                    }

                    RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_DRAW_LINE -> {
                        val x1 = op.rect_l_dp.dp.toPx()
                        val y1 = op.rect_t_dp.dp.toPx()
                        val x2 = op.rect_r_dp.dp.toPx()
                        val y2 = op.rect_b_dp.dp.toPx()
                        val sw = strokeWidthPx(op)
                        withClipAndTransform(clipStack, transformStack, widthDp, heightDp) {
                            drawLine(
                                color = Color(op.color_argb),
                                start = Offset(x1, y1),
                                end = Offset(x2, y2),
                                strokeWidth = sw,
                                cap = strokeCapFromProto(op.stroke_cap),
                            )
                        }
                    }

                    RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_FILL_OVAL -> {
                        val left = op.rect_l_dp.dp.toPx()
                        val top = op.rect_t_dp.dp.toPx()
                        val right = op.rect_r_dp.dp.toPx()
                        val bottom = op.rect_b_dp.dp.toPx()
                        if (right <= left || bottom <= top) {
                            opIndex++
                            continue@canvasPaint
                        }
                        withClipAndTransform(clipStack, transformStack, widthDp, heightDp) {
                            drawOval(
                                color = Color(op.color_argb),
                                topLeft = Offset(left, top),
                                size = Size(right - left, bottom - top),
                            )
                        }
                    }

                    RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_STROKE_OVAL -> {
                        val left = op.rect_l_dp.dp.toPx()
                        val top = op.rect_t_dp.dp.toPx()
                        val right = op.rect_r_dp.dp.toPx()
                        val bottom = op.rect_b_dp.dp.toPx()
                        if (right <= left || bottom <= top) {
                            opIndex++
                            continue@canvasPaint
                        }
                        withClipAndTransform(clipStack, transformStack, widthDp, heightDp) {
                            drawOval(
                                color = Color(op.color_argb),
                                topLeft = Offset(left, top),
                                size = Size(right - left, bottom - top),
                                style = strokeStyleFor(op),
                            )
                        }
                    }

                    RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_FILL_CIRCLE -> {
                        val r = op.circle_radius_dp.dp.toPx()
                        if (r <= 0f) {
                            opIndex++
                            continue@canvasPaint
                        }
                        val cx = op.rect_l_dp.dp.toPx()
                        val cy = op.rect_t_dp.dp.toPx()
                        withClipAndTransform(clipStack, transformStack, widthDp, heightDp) {
                            drawCircle(
                                color = Color(op.color_argb),
                                radius = r,
                                center = Offset(cx, cy),
                            )
                        }
                    }

                    RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_STROKE_CIRCLE -> {
                        val r = op.circle_radius_dp.dp.toPx()
                        if (r <= 0f) {
                            opIndex++
                            continue@canvasPaint
                        }
                        val cx = op.rect_l_dp.dp.toPx()
                        val cy = op.rect_t_dp.dp.toPx()
                        withClipAndTransform(clipStack, transformStack, widthDp, heightDp) {
                            drawCircle(
                                color = Color(op.color_argb),
                                radius = r,
                                center = Offset(cx, cy),
                                style = strokeStyleFor(op),
                            )
                        }
                    }

                    RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_DRAW_ARC -> {
                        val left = op.rect_l_dp.dp.toPx()
                        val top = op.rect_t_dp.dp.toPx()
                        val right = op.rect_r_dp.dp.toPx()
                        val bottom = op.rect_b_dp.dp.toPx()
                        if (right <= left || bottom <= top) {
                            opIndex++
                            continue@canvasPaint
                        }
                        if (op.arc_sweep_deg == 0f) {
                            opIndex++
                            continue@canvasPaint
                        }
                        val useCenter = op.arc_use_center != 0
                        val rw = right - left
                        val rh = bottom - top
                        withClipAndTransform(clipStack, transformStack, widthDp, heightDp) {
                            drawArc(
                                color = Color(op.color_argb),
                                startAngle = op.arc_start_deg,
                                sweepAngle = op.arc_sweep_deg,
                                useCenter = useCenter,
                                topLeft = Offset(left, top),
                                size = Size(rw, rh),
                                style = if (useCenter) Fill else strokeStyleFor(op),
                            )
                        }
                    }

                    RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_FILL_LINEAR_GRADIENT_RECT -> {
                        val left = op.rect_l_dp.dp.toPx()
                        val top = op.rect_t_dp.dp.toPx()
                        val right = op.rect_r_dp.dp.toPx()
                        val bottom = op.rect_b_dp.dp.toPx()
                        if (right <= left || bottom <= top) {
                            opIndex++
                            continue@canvasPaint
                        }
                        val rw = right - left
                        val rh = bottom - top
                        withClipAndTransform(clipStack, transformStack, widthDp, heightDp) {
                            if (op.gradient_end_color_argb == 0) {
                                drawRect(
                                    color = Color(op.color_argb),
                                    topLeft = Offset(left, top),
                                    size = Size(rw, rh),
                                )
                            } else {
                                val brush =
                                    when (op.gradient_axis) {
                                        1 ->
                                            Brush.verticalGradient(
                                                colors = listOf(Color(op.color_argb), Color(op.gradient_end_color_argb)),
                                                startY = top,
                                                endY = bottom,
                                            )

                                        else ->
                                            Brush.horizontalGradient(
                                                colors = listOf(Color(op.color_argb), Color(op.gradient_end_color_argb)),
                                                startX = left,
                                                endX = right,
                                            )
                                    }
                                drawRect(
                                    brush = brush,
                                    topLeft = Offset(left, top),
                                    size = Size(rw, rh),
                                )
                            }
                        }
                    }

                    RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_FILL_POLYLINE -> {
                        val xy = op.polyline?.xy_dp ?: emptyList()
                        if (xy.size < 6) {
                            opIndex++
                            continue@canvasPaint
                        }
                        val path = polylineToPath(xy, close = true)
                        if (path == null) {
                            opIndex++
                            continue@canvasPaint
                        }
                        withClipAndTransform(clipStack, transformStack, widthDp, heightDp) {
                            drawPath(
                                path = path,
                                color = Color(op.color_argb),
                                style = Fill,
                            )
                        }
                    }

                    RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_FILL_ROUNDED_POLYGON -> {
                        val xy = mergedRoundedPolygonXyDp(op)
                        if (xy.size < 6) {
                            opIndex++
                            continue@canvasPaint
                        }
                        val path = roundedConvexPolygonPathFromPolylineDp(xy, op.corner_radius_dp)
                        if (path == null) {
                            opIndex++
                            continue@canvasPaint
                        }
                        withClipAndTransform(clipStack, transformStack, widthDp, heightDp) {
                            drawPath(
                                path = path,
                                color = Color(op.color_argb),
                                style = Fill,
                            )
                        }
                    }

                    RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_STROKE_ROUNDED_POLYGON -> {
                        val xy = mergedRoundedPolygonXyDp(op)
                        if (xy.size < 6) {
                            opIndex++
                            continue@canvasPaint
                        }
                        val path = roundedConvexPolygonPathFromPolylineDp(xy, op.corner_radius_dp)
                        if (path == null) {
                            opIndex++
                            continue@canvasPaint
                        }
                        withClipAndTransform(clipStack, transformStack, widthDp, heightDp) {
                            drawPath(
                                path = path,
                                color = Color(op.color_argb),
                                style = strokeStyleFor(op),
                            )
                        }
                    }

                    RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_STROKE_POLYLINE -> {
                        val xy = op.polyline?.xy_dp ?: emptyList()
                        if (xy.size < 4) {
                            opIndex++
                            continue@canvasPaint
                        }
                        val path = polylineToPath(xy, close = false)
                        if (path == null) {
                            opIndex++
                            continue@canvasPaint
                        }
                        withClipAndTransform(clipStack, transformStack, widthDp, heightDp) {
                            drawPath(
                                path = path,
                                color = Color(op.color_argb),
                                style = strokeStyleFor(op),
                            )
                        }
                    }

                    RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_FILL_PATH -> {
                        val protoPath = op.path
                        if (protoPath == null) {
                            opIndex++
                            continue@canvasPaint
                        }
                        val path = pathFromRemoteProto(protoPath)
                        if (path == null) {
                            opIndex++
                            continue@canvasPaint
                        }
                        val pathPaint = remoteCanvasPathDrawPaintFromOp(op)
                        withClipAndTransform(clipStack, transformStack, widthDp, heightDp) {
                            drawPath(
                                path = path,
                                color = Color(op.color_argb),
                                alpha = pathPaint.alpha,
                                style = Fill,
                                blendMode = pathPaint.blendMode,
                                colorFilter = pathPaint.colorFilter,
                            )
                        }
                    }

                    RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_STROKE_PATH -> {
                        val protoPath = op.path
                        if (protoPath == null) {
                            opIndex++
                            continue@canvasPaint
                        }
                        val path = pathFromRemoteProto(protoPath)
                        if (path == null) {
                            opIndex++
                            continue@canvasPaint
                        }
                        val pathPaint = remoteCanvasPathDrawPaintFromOp(op)
                        withClipAndTransform(clipStack, transformStack, widthDp, heightDp) {
                            drawPath(
                                path = path,
                                color = Color(op.color_argb),
                                alpha = pathPaint.alpha,
                                style = strokeStyleFor(op),
                                blendMode = pathPaint.blendMode,
                                colorFilter = pathPaint.colorFilter,
                            )
                        }
                    }

                    RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_DRAW_TWEEN_PATH -> {
                        val protoFrom = op.path
                        val protoTo = op.tween_path_to
                        if (protoFrom == null || protoTo == null) {
                            opIndex++
                            continue@canvasPaint
                        }
                        if (protoFrom.verbs.isEmpty() || protoTo.verbs.isEmpty()) {
                            opIndex++
                            continue@canvasPaint
                        }
                        val tt = tweenPathBlendTForRemoteCanvasOp(op, resolveRemoteFloat)
                        val lerped = lerpRemoteCanvasPathProtosOrNull(protoFrom, protoTo, tt)
                        val fb = op.tween_path_incompatible_fallback
                        val chosenProto =
                            lerped
                                ?: when (fb) {
                                    RemoteCanvasTweenPathIncompatibleFallbackWire.STROKE_FROM -> protoFrom
                                    RemoteCanvasTweenPathIncompatibleFallbackWire.STROKE_TO -> protoTo
                                    else -> null
                                }
                        if (chosenProto == null) {
                            opIndex++
                            continue@canvasPaint
                        }
                        val path = pathFromRemoteProto(chosenProto)
                        if (path == null) {
                            opIndex++
                            continue@canvasPaint
                        }
                        val pathPaint = remoteCanvasPathDrawPaintFromOp(op)
                        withClipAndTransform(clipStack, transformStack, widthDp, heightDp) {
                            drawPath(
                                path = path,
                                color = Color(op.color_argb),
                                alpha = pathPaint.alpha,
                                style = strokeStyleFor(op),
                                blendMode = pathPaint.blendMode,
                                colorFilter = pathPaint.colorFilter,
                            )
                        }
                    }

                    RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_FILL_RADIAL_GRADIENT_RECT -> {
                        val left = op.rect_l_dp.dp.toPx()
                        val top = op.rect_t_dp.dp.toPx()
                        val right = op.rect_r_dp.dp.toPx()
                        val bottom = op.rect_b_dp.dp.toPx()
                        if (right <= left || bottom <= top) {
                            opIndex++
                            continue@canvasPaint
                        }
                        val rw = right - left
                        val rh = bottom - top
                        val cx = left + rw / 2f
                        val cy = top + rh / 2f
                        val radius = max(rw, rh) / 2f * 1.02f
                        withClipAndTransform(clipStack, transformStack, widthDp, heightDp) {
                            if (op.gradient_end_color_argb == 0) {
                                drawRect(
                                    color = Color(op.color_argb),
                                    topLeft = Offset(left, top),
                                    size = Size(rw, rh),
                                )
                            } else {
                                drawRect(
                                    brush =
                                        Brush.radialGradient(
                                            colors = listOf(Color(op.color_argb), Color(op.gradient_end_color_argb)),
                                            center = Offset(cx, cy),
                                            radius = radius,
                                        ),
                                    topLeft = Offset(left, top),
                                    size = Size(rw, rh),
                                )
                            }
                        }
                    }

                    else -> Unit
                }
                opIndex++
            }
        }

        val clipStackText = mutableListOf<CanvasDpRect?>()
        val textSaveStack = mutableListOf<List<CanvasDpRect?>>()
        val conditionalStackText = mutableListOf<Boolean>()
        val textOffscreenOriginStack = mutableListOf<Pair<Float, Float>>()
        for (op in paintingOps) {
            when (op.code) {
                RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_CONDITIONAL_BEGIN -> {
                    pushConditionalStack(conditionalStackText, op, resolveRemoteFloat)
                    continue
                }

                RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_CONDITIONAL_END -> {
                    conditionalStackText.removeLastOrNull()
                    continue
                }

                else -> Unit
            }
            if (conditionalStackText.isNotEmpty() && !conditionalStackText.all { it }) {
                continue
            }
            when (op.code) {
                RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_PUSH_CLIP_RECT -> {
                    val r = op.toDpRect()
                    if (!r.isEmpty()) {
                        clipStackText.add(if (op.clip_op == 1) null else r)
                    }
                }

                RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_PUSH_CLIP_ROUND_RECT -> {
                    val r = op.toDpRect()
                    if (!r.isEmpty()) {
                        clipStackText.add(if (op.clip_op == 1) null else r)
                    }
                }

                RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_PUSH_CLIP_POLYLINE -> {
                    val xy = op.polyline?.xy_dp ?: emptyList()
                    if (xy.size >= 6 && xy.size % 2 == 0) {
                        val b = boundsFromPolylineDp(xy)
                        if (b != null && !b.isEmpty()) {
                            clipStackText.add(if (op.clip_op == 1) null else b)
                        }
                    }
                }

                RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_PUSH_CLIP_PATH -> {
                    val protoPath = op.path ?: continue
                    val b = boundsFromPathProtoDp(protoPath)
                    if (b != null && !b.isEmpty()) {
                        clipStackText.add(if (op.clip_op == 1) null else b)
                    }
                }

                RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_POP_CLIP -> {
                    clipStackText.removeLastOrNull()
                }

                RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_PUSH_CANVAS_SAVE -> {
                    textSaveStack.add(clipStackText.toList())
                }

                RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_POP_CANVAS_RESTORE -> {
                    val snap = textSaveStack.removeLastOrNull() ?: continue
                    clipStackText.clear()
                    clipStackText.addAll(snap)
                }

                RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_PUSH_OFFSCREEN_RENDER -> {
                    val ox = textOffscreenOriginStack.lastOrNull()?.first ?: 0f
                    val oy = textOffscreenOriginStack.lastOrNull()?.second ?: 0f
                    textOffscreenOriginStack.add(ox + op.rect_l_dp to oy + op.rect_t_dp)
                }

                RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_POP_OFFSCREEN_RENDER -> {
                    textOffscreenOriginStack.removeLastOrNull()
                }

                RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_DRAW_TEXT -> {
                    if (op.text_body.isEmpty()) continue
                    val origin = textOffscreenOriginStack.lastOrNull()
                    val rawBounds = op.toDpRect()
                    val shifted =
                        if (origin != null) {
                            rawBounds.offsetBy(origin.first, origin.second)
                        } else {
                            rawBounds
                        }
                    val bounds = shifted.intersect(combinedClipDp(clipStackText, widthDp, heightDp))
                    if (bounds.isEmpty()) continue
                    val lineH = remoteCanvasTextLineHeightSpForLayout(op)
                    val fh = bounds.b - bounds.t
                    val maxLines = (fh / lineH).toInt().coerceIn(1, 10)
                    RemoteCanvasBasicTextOverlay(
                        bounds = bounds,
                        body = op.text_body,
                        colorArgb = op.color_argb,
                        fontSizeSp = op.font_size_sp,
                        fontWeightProto = op.font_weight,
                        textAlignProto = op.text_align,
                        textAlignVerticalProto = op.text_align_vertical,
                        maxLines = maxLines,
                        textLineHeightWireSp = op.text_line_height_sp,
                        rectTopIsFirstBaseline = op.draw_text_rect_top_is_first_baseline,
                        rectBottomIsLastBaseline = op.draw_text_rect_bottom_is_last_baseline,
                        forceRtlTextDirection = false,
                        lineHeightStyleWire = op.draw_text_line_height_style,
                        disableFontPadding = op.draw_text_disable_font_padding,
                    )
                }

                RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_DRAW_TEXT_AT -> {
                    if (op.text_body.isEmpty()) continue
                    val ox = textOffscreenOriginStack.lastOrNull()?.first ?: 0f
                    val oy = textOffscreenOriginStack.lastOrNull()?.second ?: 0f
                    val x = op.rect_l_dp + ox
                    val y = op.rect_t_dp + oy
                    val fontSp = if (op.font_size_sp > 0f) op.font_size_sp else 12f
                    val lineHDp = remoteCanvasTextLineHeightSpForLayout(op).coerceAtLeast(1f)
                    val maxW =
                        if (op.rect_r_dp > 0f) {
                            op.rect_r_dp
                        } else {
                            (widthDp - x).coerceAtLeast(1f)
                        }
                    val maxH =
                        if (op.rect_b_dp > 0f) {
                            op.rect_b_dp
                        } else {
                            (lineHDp * 5f).coerceAtLeast(lineHDp)
                        }
                    val raw = CanvasDpRect(x, y, x + maxW, y + maxH)
                    val bounds = raw.intersect(combinedClipDp(clipStackText, widthDp, heightDp))
                    if (bounds.isEmpty()) continue
                    val maxLines = (maxH / lineHDp).toInt().coerceIn(1, 10)
                    RemoteCanvasBasicTextOverlay(
                        bounds = bounds,
                        body = op.text_body,
                        colorArgb = op.color_argb,
                        fontSizeSp = op.font_size_sp,
                        fontWeightProto = op.font_weight,
                        textAlignProto = op.text_align,
                        textAlignVerticalProto = op.text_align_vertical,
                        maxLines = maxLines,
                        textLineHeightWireSp = op.text_line_height_sp,
                        rectTopIsFirstBaseline = op.draw_text_rect_top_is_first_baseline,
                        rectBottomIsLastBaseline = op.draw_text_rect_bottom_is_last_baseline,
                        forceRtlTextDirection = false,
                        lineHeightStyleWire = op.draw_text_line_height_style,
                        disableFontPadding = op.draw_text_disable_font_padding,
                    )
                }

                RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_DRAW_TEXT_RUN -> {
                    if (op.text_body.isEmpty()) continue
                    val layoutCtx =
                        remoteCanvasTextRunLayoutContextUtf16(
                            op.text_body,
                            op.draw_text_run_start,
                            op.draw_text_run_end,
                            op.draw_text_run_context_start,
                            op.draw_text_run_context_end,
                        ) ?: continue
                    val ox = textOffscreenOriginStack.lastOrNull()?.first ?: 0f
                    val oy = textOffscreenOriginStack.lastOrNull()?.second ?: 0f
                    val x = op.rect_l_dp + ox
                    val y = op.rect_t_dp + oy
                    val lineHDp = remoteCanvasTextLineHeightSpForLayout(op).coerceAtLeast(1f)
                    val maxW =
                        if (op.rect_r_dp > 0f) {
                            op.rect_r_dp
                        } else {
                            (widthDp - x).coerceAtLeast(1f)
                        }
                    val maxH =
                        if (op.rect_b_dp > 0f) {
                            op.rect_b_dp
                        } else {
                            (lineHDp * 5f).coerceAtLeast(lineHDp)
                        }
                    val raw = CanvasDpRect(x, y, x + maxW, y + maxH)
                    val bounds = raw.intersect(combinedClipDp(clipStackText, widthDp, heightDp))
                    if (bounds.isEmpty()) continue
                    val maxLines = (maxH / lineHDp).toInt().coerceIn(1, 10)
                    val fullRunVisibleInContext =
                        layoutCtx.runStartInContext <= 0 &&
                            layoutCtx.runEndExclusiveInContext >= layoutCtx.contextBody.length
                    RemoteCanvasBasicTextOverlay(
                        bounds = bounds,
                        body = layoutCtx.contextBody,
                        colorArgb = op.color_argb,
                        fontSizeSp = op.font_size_sp,
                        fontWeightProto = op.font_weight,
                        textAlignProto = op.text_align,
                        textAlignVerticalProto = op.text_align_vertical,
                        maxLines = maxLines,
                        textLineHeightWireSp = op.text_line_height_sp,
                        rectTopIsFirstBaseline = op.draw_text_rect_top_is_first_baseline,
                        rectBottomIsLastBaseline = op.draw_text_rect_bottom_is_last_baseline,
                        forceRtlTextDirection = op.draw_text_run_is_rtl,
                        drawTextRunVisibleStartUtf16 =
                            if (fullRunVisibleInContext) {
                                -1
                            } else {
                                layoutCtx.runStartInContext
                            },
                        drawTextRunVisibleEndExclusiveUtf16 =
                            if (fullRunVisibleInContext) {
                                -1
                            } else {
                                layoutCtx.runEndExclusiveInContext
                            },
                        lineHeightStyleWire = op.draw_text_line_height_style,
                        disableFontPadding = op.draw_text_disable_font_padding,
                    )
                }

                RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_DRAW_TEXT_ON_PATH -> {
                    RemoteCanvasTextOnPathGlyphOverlays(
                        op = op,
                        widthDp = widthDp,
                        heightDp = heightDp,
                        clipStackText = clipStackText,
                        origin = textOffscreenOriginStack.lastOrNull(),
                        textMeasurer = textMeasurer,
                    )
                }

                RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_DRAW_TEXT_ON_CIRCLE -> {
                    RemoteCanvasTextOnCircleGlyphOverlays(
                        op = op,
                        widthDp = widthDp,
                        heightDp = heightDp,
                        clipStackText = clipStackText,
                        origin = textOffscreenOriginStack.lastOrNull(),
                        textMeasurer = textMeasurer,
                    )
                }

                RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_DRAW_TEXT_ANCHORED -> {
                    RemoteCanvasAnchoredTextOverlay(
                        op = op,
                        widthDp = widthDp,
                        heightDp = heightDp,
                        clipStackText = clipStackText,
                        origin = textOffscreenOriginStack.lastOrNull(),
                        textMeasurer = textMeasurer,
                    )
                }

                else -> Unit
            }
        }

        val clipStackPointer = mutableListOf<CanvasDpRect?>()
        val pointerSaveStack = mutableListOf<List<CanvasDpRect?>>()
        val conditionalStackPointer = mutableListOf<Boolean>()
        val pointerOffscreenOriginStack = mutableListOf<Pair<Float, Float>>()
        for (op in paintingOps) {
            when (op.code) {
                RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_CONDITIONAL_BEGIN -> {
                    pushConditionalStack(conditionalStackPointer, op, resolveRemoteFloat)
                    continue
                }

                RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_CONDITIONAL_END -> {
                    conditionalStackPointer.removeLastOrNull()
                    continue
                }

                else -> Unit
            }
            if (conditionalStackPointer.isNotEmpty() && !conditionalStackPointer.all { it }) {
                continue
            }
            when (op.code) {
                RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_PUSH_CLIP_RECT -> {
                    val r = op.toDpRect()
                    if (!r.isEmpty()) {
                        clipStackPointer.add(if (op.clip_op == 1) null else r)
                    }
                }

                RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_PUSH_CLIP_ROUND_RECT -> {
                    val r = op.toDpRect()
                    if (!r.isEmpty()) {
                        clipStackPointer.add(if (op.clip_op == 1) null else r)
                    }
                }

                RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_PUSH_CLIP_POLYLINE -> {
                    val xy = op.polyline?.xy_dp ?: emptyList()
                    if (xy.size >= 6 && xy.size % 2 == 0) {
                        val b = boundsFromPolylineDp(xy)
                        if (b != null && !b.isEmpty()) {
                            clipStackPointer.add(if (op.clip_op == 1) null else b)
                        }
                    }
                }

                RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_PUSH_CLIP_PATH -> {
                    val protoPath = op.path ?: continue
                    val b = boundsFromPathProtoDp(protoPath)
                    if (b != null && !b.isEmpty()) {
                        clipStackPointer.add(if (op.clip_op == 1) null else b)
                    }
                }

                RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_POP_CLIP -> {
                    clipStackPointer.removeLastOrNull()
                }

                RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_PUSH_CANVAS_SAVE -> {
                    pointerSaveStack.add(clipStackPointer.toList())
                }

                RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_POP_CANVAS_RESTORE -> {
                    val snap = pointerSaveStack.removeLastOrNull() ?: continue
                    clipStackPointer.clear()
                    clipStackPointer.addAll(snap)
                }

                RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_PUSH_OFFSCREEN_RENDER -> {
                    val ox = pointerOffscreenOriginStack.lastOrNull()?.first ?: 0f
                    val oy = pointerOffscreenOriginStack.lastOrNull()?.second ?: 0f
                    pointerOffscreenOriginStack.add(ox + op.rect_l_dp to oy + op.rect_t_dp)
                }

                RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_POP_OFFSCREEN_RENDER -> {
                    pointerOffscreenOriginStack.removeLastOrNull()
                }

                RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_DRAW_IMAGE -> {
                    val cd = op.draw_image_content_description
                    if (cd.isBlank()) continue
                    val clipDp = combinedClipDp(clipStackPointer, widthDp, heightDp)
                    val origin = pointerOffscreenOriginStack.lastOrNull()
                    val raw = op.toDpRect()
                    val local =
                        if (origin != null) {
                            raw.offsetBy(origin.first, origin.second)
                        } else {
                            raw
                        }
                    val hit = local.intersect(clipDp)
                    if (hit.isEmpty()) continue
                    val widthHit = hit.r - hit.l
                    val heightHit = hit.b - hit.t
                    if (widthHit <= 0f || heightHit <= 0f) continue
                    Box(
                        Modifier
                            .offset(hit.l.dp, hit.t.dp)
                            .size(widthHit.dp, heightHit.dp)
                            .semantics { contentDescription = cd },
                    )
                }

                RemoteCanvasOpCodeProto.REMOTE_CANVAS_OP_POINTER_INPUT_RECT -> {
                    if (op.pointer_action_id.isBlank() || node.key.isBlank()) continue
                    val clipDp = combinedClipDp(clipStackPointer, widthDp, heightDp)
                    val origin = pointerOffscreenOriginStack.lastOrNull()
                    val rawHit = op.toDpRect()
                    val localHit =
                        if (origin != null) {
                            rawHit.offsetBy(origin.first, origin.second)
                        } else {
                            rawHit
                        }
                    val hit = localHit.intersect(clipDp)
                    if (hit.isEmpty()) continue
                    val widthHit = hit.r - hit.l
                    val heightHit = hit.b - hit.t
                    if (widthHit <= 0f || heightHit <= 0f) continue
                    val actionId = op.pointer_action_id
                    Box(
                        Modifier
                            .offset(hit.l.dp, hit.t.dp)
                            .size(widthHit.dp, heightHit.dp)
                            .clickable {
                                scope.launch {
                                    runtime.dispatch(
                                        nodeKey = node.key,
                                        action = ActionBindingProto(
                                            trigger = ActionTriggerProto.ACTION_TRIGGER_CLICK,
                                            action_id = actionId,
                                        ),
                                    )
                                }
                            },
                    )
                }

                else -> Unit
            }
        }
    }
}
