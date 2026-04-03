package com.podca.sdui.studio.ui.core

import com.podca.sdui.protocol.ui.FrameRateCategoryProto
import com.podca.sdui.protocol.ui.ModifierChainProto
import com.podca.sdui.protocol.ui.ModifierElementProto
import com.podca.sdui.protocol.ui.ModifierNodeProto
import com.podca.sdui.protocol.ui.graphics.ExperimentalGraphicsApiProto
import com.podca.sdui.protocol.ui.graphics.InlineClassHelperProto
import com.podca.sdui.protocol.ui.graphics.InterpolatableProto
import com.podca.sdui.protocol.ui.graphics.PathIteratorConicEvaluationProto
import com.podca.sdui.protocol.ui.graphics.PathIteratorProto
import com.podca.sdui.protocol.ui.graphics.PathSvgProto

public fun PodcaFrameRateCategory(
    frameRateCategory: FrameRateCategoryProto = FrameRateCategoryProto.FRAME_RATE_CATEGORY_DEFAULT,
): FrameRateCategoryProto = frameRateCategory

public fun PodcaModifierElement(
    type: String,
    debugPayload: String = "",
): ModifierElementProto =
    ModifierElementProto(
        type = type,
        debug_payload = debugPayload,
    )

public fun PodcaModifierNode(
    kindSet: Int = 0,
    isAttached: Boolean = false,
    shouldAutoInvalidate: Boolean = false,
): ModifierNodeProto =
    ModifierNodeProto(
        kind_set = kindSet,
        is_attached = isAttached,
        should_auto_invalidate = shouldAutoInvalidate,
    )

public fun PodcaModifierChain(
    elements: List<ModifierElementProto> = emptyList(),
): ModifierChainProto =
    ModifierChainProto(
        elements = elements,
    )

public fun PodcaExperimentalGraphicsApi(
    featureName: String,
    sinceVersion: String = "",
    optInRequired: Boolean = false,
): ExperimentalGraphicsApiProto =
    ExperimentalGraphicsApiProto(
        feature_name = featureName,
        since_version = sinceVersion,
        opt_in_required = optInRequired,
    )

public fun PodcaInlineClassHelper(
    wrappedTypeName: String,
    packsIntoPrimitive: Boolean = false,
    exposesRequirePrecondition: Boolean = false,
    exposesIllegalArgumentThrower: Boolean = false,
): InlineClassHelperProto =
    InlineClassHelperProto(
        wrapped_type_name = wrappedTypeName,
        packs_into_primitive = packsIntoPrimitive,
        exposes_require_precondition = exposesRequirePrecondition,
        exposes_illegal_argument_thrower = exposesIllegalArgumentThrower,
    )

public fun PodcaInterpolatable(
    targetType: String,
    supportsLerp: Boolean = false,
): InterpolatableProto =
    InterpolatableProto(
        target_type = targetType,
        supports_lerp = supportsLerp,
    )

public fun PodcaPathIteratorConicEvaluation(
    conicEvaluation: PathIteratorConicEvaluationProto =
        PathIteratorConicEvaluationProto.PATH_ITERATOR_CONIC_EVALUATION_AS_CONIC,
): PathIteratorConicEvaluationProto = conicEvaluation

public fun PodcaPathIterator(
    conicEvaluation: PathIteratorConicEvaluationProto =
        PathIteratorConicEvaluationProto.PATH_ITERATOR_CONIC_EVALUATION_AS_CONIC,
    tolerance: Float = 0f,
): PathIteratorProto =
    PathIteratorProto(
        conic_evaluation = conicEvaluation,
        tolerance = tolerance,
    )

public fun PodcaPathSvg(
    pathData: String,
    asDocument: Boolean = false,
): PathSvgProto =
    PathSvgProto(
        path_data = pathData,
        as_document = asDocument,
    )
