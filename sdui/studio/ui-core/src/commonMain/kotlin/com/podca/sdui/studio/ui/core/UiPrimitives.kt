package com.podca.sdui.studio.ui.core

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.ui.ComposeUiFlagsProto
import com.podca.sdui.protocol.ui.ComposedModifierFactoryProto
import com.podca.sdui.protocol.ui.ComposedModifierProto
import com.podca.sdui.protocol.ui.AtomicReferenceProto
import com.podca.sdui.protocol.ui.ExpectProto
import com.podca.sdui.protocol.ui.FrameRateProto
import com.podca.sdui.protocol.ui.KeepScreenOnProto
import com.podca.sdui.protocol.ui.MotionDurationScaleProto
import com.podca.sdui.protocol.ui.SensitiveContentProto
import com.podca.sdui.protocol.ui.SessionMutexProto
import com.podca.sdui.protocol.ui.UiComposableProto
import com.podca.sdui.protocol.ui.ZIndexModifierProto
import com.podca.sdui.studio.core.PodcaNode

@Composable
public fun PodcaFrameRate(
    framesPerSecond: Float,
) {
    PodcaNode(
        type = "ui.FrameRate",
        message = FrameRateProto(
            frames_per_second = framesPerSecond,
        ),
        encode = FrameRateProto.ADAPTER::encode,
    )
}

@Composable
public fun PodcaKeepScreenOn(
    enabled: Boolean = true,
) {
    PodcaNode(
        type = "ui.KeepScreenOn",
        message = KeepScreenOnProto(
            enabled = enabled,
        ),
        encode = KeepScreenOnProto.ADAPTER::encode,
    )
}

@Composable
public fun PodcaSensitiveContent(
    isContentSensitive: Boolean = true,
) {
    PodcaNode(
        type = "ui.SensitiveContent",
        message = SensitiveContentProto(
            is_content_sensitive = isContentSensitive,
        ),
        encode = SensitiveContentProto.ADAPTER::encode,
    )
}

@Composable
public fun PodcaZIndexModifier(
    zIndex: Float,
) {
    PodcaNode(
        type = "ui.ZIndexModifier",
        message = ZIndexModifierProto(
            z_index = zIndex,
        ),
        encode = ZIndexModifierProto.ADAPTER::encode,
    )
}

public fun PodcaComposeUiFlags(
    areVectorBackedLayersEnabled: Boolean = false,
    isSemanticsPublishingEnabled: Boolean = false,
    isNestedScrollDispatchEnabled: Boolean = false,
): ComposeUiFlagsProto =
    ComposeUiFlagsProto(
        are_vector_backed_layers_enabled = areVectorBackedLayersEnabled,
        is_semantics_publishing_enabled = isSemanticsPublishingEnabled,
        is_nested_scroll_dispatch_enabled = isNestedScrollDispatchEnabled,
    )

public fun PodcaComposedModifierFactory(
    fullyQualifiedName: String,
    keys: List<String> = emptyList(),
): ComposedModifierFactoryProto =
    ComposedModifierFactoryProto(
        fully_qualified_name = fullyQualifiedName,
        keys = keys,
    )

public fun PodcaComposedModifier(
    factory: ComposedModifierFactoryProto? = null,
): ComposedModifierProto =
    ComposedModifierProto(
        factory = factory,
    )

public fun PodcaAtomicReference(
    debugValue: String = "",
): AtomicReferenceProto =
    AtomicReferenceProto(
        debug_value = debugValue,
    )

public fun PodcaExpect(
    declarationName: String,
    platform: String,
    fulfilledByActual: Boolean = false,
): ExpectProto =
    ExpectProto(
        declaration_name = declarationName,
        platform = platform,
        fulfilled_by_actual = fulfilledByActual,
    )

public fun PodcaMotionDurationScale(
    scaleFactor: Float,
): MotionDurationScaleProto =
    MotionDurationScaleProto(
        scale_factor = scaleFactor,
    )

public fun PodcaSessionMutex(
    locked: Boolean = false,
): SessionMutexProto =
    SessionMutexProto(
        locked = locked,
    )

public fun PodcaUiComposable(
    fqName: String,
    restartable: Boolean = false,
    skippable: Boolean = false,
): UiComposableProto =
    UiComposableProto(
        fq_name = fqName,
        restartable = restartable,
        skippable = skippable,
    )
