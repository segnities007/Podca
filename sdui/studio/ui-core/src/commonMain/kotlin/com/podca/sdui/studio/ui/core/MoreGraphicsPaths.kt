package com.podca.sdui.studio.ui.core

import com.podca.sdui.protocol.ui.geometry.OffsetProto
import com.podca.sdui.protocol.ui.geometry.RectProto
import com.podca.sdui.protocol.ui.geometry.RoundRectProto
import com.podca.sdui.protocol.ui.graphics.AddOvalProto
import com.podca.sdui.protocol.ui.graphics.AddRoundRectProto
import com.podca.sdui.protocol.ui.graphics.AddPathProto
import com.podca.sdui.protocol.ui.graphics.AddRectProto
import com.podca.sdui.protocol.ui.graphics.ArcToProto
import com.podca.sdui.protocol.ui.graphics.ClosePathProto
import com.podca.sdui.protocol.ui.graphics.CubicBezierToProto
import com.podca.sdui.protocol.ui.graphics.LineToProto
import com.podca.sdui.protocol.ui.graphics.MoveToProto
import com.podca.sdui.protocol.ui.graphics.PathElementProto
import com.podca.sdui.protocol.ui.graphics.PathFillTypeProto
import com.podca.sdui.protocol.ui.graphics.PathOperationProto
import com.podca.sdui.protocol.ui.graphics.PathProto
import com.podca.sdui.protocol.ui.graphics.QuadraticBezierToProto
import com.podca.sdui.protocol.ui.graphics.RelativeCubicBezierToProto
import com.podca.sdui.protocol.ui.graphics.RelativeLineToProto
import com.podca.sdui.protocol.ui.graphics.RelativeMoveToProto
import com.podca.sdui.protocol.ui.graphics.RelativeQuadraticBezierToProto
import com.podca.sdui.protocol.ui.graphics.SetPathOperationProto
import com.podca.sdui.protocol.ui.graphics.TransformPathProto
import com.podca.sdui.protocol.ui.graphics.TranslatePathProto

public fun PodcaPath(
    fillType: PathFillTypeProto = PathFillTypeProto.PATH_FILL_TYPE_NON_ZERO,
    elements: List<PathElementProto> = emptyList(),
    isConvex: Boolean = false,
    isEmpty: Boolean = true,
    bounds: RectProto? = null,
): PathProto =
    PathProto(
        fill_type = fillType,
        elements = elements,
        is_convex = isConvex,
        is_empty = isEmpty,
        bounds = bounds,
    )

public fun PodcaPathElementMoveTo(
    moveTo: MoveToProto? = null,
): PathElementProto =
    PathElementProto(
        move_to = moveTo,
    )

public fun PodcaPathElementLineTo(
    lineTo: LineToProto? = null,
): PathElementProto =
    PathElementProto(
        line_to = lineTo,
    )

public fun PodcaPathElementQuadraticBezierTo(
    quadraticBezierTo: QuadraticBezierToProto? = null,
): PathElementProto =
    PathElementProto(
        quadratic_bezier_to = quadraticBezierTo,
    )

public fun PodcaPathElementCubicBezierTo(
    cubicBezierTo: CubicBezierToProto? = null,
): PathElementProto =
    PathElementProto(
        cubic_bezier_to = cubicBezierTo,
    )

public fun PodcaPathElementArcTo(
    arcTo: ArcToProto? = null,
): PathElementProto =
    PathElementProto(
        arc_to = arcTo,
    )

public fun PodcaPathElementClosePath(
    close: ClosePathProto? = null,
): PathElementProto =
    PathElementProto(
        close = close,
    )

public fun PodcaPathElementRelativeMoveTo(
    relativeMoveTo: RelativeMoveToProto? = null,
): PathElementProto =
    PathElementProto(
        relative_move_to = relativeMoveTo,
    )

public fun PodcaPathElementRelativeLineTo(
    relativeLineTo: RelativeLineToProto? = null,
): PathElementProto =
    PathElementProto(
        relative_line_to = relativeLineTo,
    )

public fun PodcaPathElementRelativeQuadraticBezierTo(
    relativeQuadraticBezierTo: RelativeQuadraticBezierToProto? = null,
): PathElementProto =
    PathElementProto(
        relative_quadratic_bezier_to = relativeQuadraticBezierTo,
    )

public fun PodcaPathElementRelativeCubicBezierTo(
    relativeCubicBezierTo: RelativeCubicBezierToProto? = null,
): PathElementProto =
    PathElementProto(
        relative_cubic_bezier_to = relativeCubicBezierTo,
    )

public fun PodcaPathElementAddRect(
    addRect: AddRectProto? = null,
): PathElementProto =
    PathElementProto(
        add_rect = addRect,
    )

public fun PodcaPathElementAddOval(
    addOval: AddOvalProto? = null,
): PathElementProto =
    PathElementProto(
        add_oval = addOval,
    )

public fun PodcaPathElementAddRoundRect(
    addRoundRect: AddRoundRectProto? = null,
): PathElementProto =
    PathElementProto(
        add_round_rect = addRoundRect,
    )

public fun PodcaPathElementAddPath(
    addPath: AddPathProto? = null,
): PathElementProto =
    PathElementProto(
        add_path = addPath,
    )

public fun PodcaPathElementTranslatePath(
    translate: TranslatePathProto? = null,
): PathElementProto =
    PathElementProto(
        translate = translate,
    )

public fun PodcaPathElementTransformPath(
    transform: TransformPathProto? = null,
): PathElementProto =
    PathElementProto(
        transform = transform,
    )

public fun PodcaPathElementSetPathOperation(
    applyOperation: SetPathOperationProto? = null,
): PathElementProto =
    PathElementProto(
        apply_operation = applyOperation,
    )

public fun PodcaMoveTo(
    x: Float,
    y: Float,
): MoveToProto =
    MoveToProto(
        x = x,
        y = y,
    )

public fun PodcaLineTo(
    x: Float,
    y: Float,
): LineToProto =
    LineToProto(
        x = x,
        y = y,
    )

public fun PodcaRelativeMoveTo(
    dx: Float,
    dy: Float,
): RelativeMoveToProto =
    RelativeMoveToProto(
        dx = dx,
        dy = dy,
    )

public fun PodcaRelativeLineTo(
    dx: Float,
    dy: Float,
): RelativeLineToProto =
    RelativeLineToProto(
        dx = dx,
        dy = dy,
    )

public fun PodcaQuadraticBezierTo(
    x1: Float,
    y1: Float,
    x2: Float,
    y2: Float,
): QuadraticBezierToProto =
    QuadraticBezierToProto(
        x1 = x1,
        y1 = y1,
        x2 = x2,
        y2 = y2,
    )

public fun PodcaCubicBezierTo(
    x1: Float,
    y1: Float,
    x2: Float,
    y2: Float,
    x3: Float,
    y3: Float,
): CubicBezierToProto =
    CubicBezierToProto(
        x1 = x1,
        y1 = y1,
        x2 = x2,
        y2 = y2,
        x3 = x3,
        y3 = y3,
    )

public fun PodcaRelativeQuadraticBezierTo(
    dx1: Float,
    dy1: Float,
    dx2: Float,
    dy2: Float,
): RelativeQuadraticBezierToProto =
    RelativeQuadraticBezierToProto(
        dx1 = dx1,
        dy1 = dy1,
        dx2 = dx2,
        dy2 = dy2,
    )

public fun PodcaRelativeCubicBezierTo(
    dx1: Float,
    dy1: Float,
    dx2: Float,
    dy2: Float,
    dx3: Float,
    dy3: Float,
): RelativeCubicBezierToProto =
    RelativeCubicBezierToProto(
        dx1 = dx1,
        dy1 = dy1,
        dx2 = dx2,
        dy2 = dy2,
        dx3 = dx3,
        dy3 = dy3,
    )

public fun PodcaArcTo(
    rect: RectProto? = null,
    startAngle: Float = 0f,
    sweepAngle: Float = 0f,
    forceMoveTo: Boolean = false,
): ArcToProto =
    ArcToProto(
        rect = rect,
        start_angle = startAngle,
        sweep_angle = sweepAngle,
        force_move_to = forceMoveTo,
    )

public fun PodcaAddRect(
    rect: RectProto? = null,
): AddRectProto =
    AddRectProto(
        rect = rect,
    )

public fun PodcaAddOval(
    ovalBounds: RectProto? = null,
): AddOvalProto =
    AddOvalProto(
        oval_bounds = ovalBounds,
    )

public fun PodcaAddRoundRect(
    roundRect: RoundRectProto? = null,
): AddRoundRectProto =
    com.podca.sdui.protocol.ui.graphics.AddRoundRectProto(
        round_rect = roundRect,
    )

public fun PodcaAddPath(
    path: PathProto? = null,
    offset: OffsetProto? = null,
): AddPathProto =
    AddPathProto(
        path = path,
        offset = offset,
    )

public fun PodcaTranslatePath(
    offset: OffsetProto? = null,
): TranslatePathProto =
    TranslatePathProto(
        offset = offset,
    )

public fun PodcaTransformPath(
    matrixValues: List<Float> = emptyList(),
): TransformPathProto =
    TransformPathProto(
        matrix_values = matrixValues,
    )

public fun PodcaSetPathOperation(
    operation: PathOperationProto = PathOperationProto.PATH_OPERATION_UNION,
    other: PathProto? = null,
): SetPathOperationProto =
    SetPathOperationProto(
        operation = operation,
        other = other,
    )
