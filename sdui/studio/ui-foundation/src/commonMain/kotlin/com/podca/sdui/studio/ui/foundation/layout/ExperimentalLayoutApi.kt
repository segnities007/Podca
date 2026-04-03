package com.podca.sdui.studio.ui.foundation.layout

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.core.ActionBindingProto
import com.podca.sdui.protocol.foundation.layout.ExperimentalFlexBoxApiProto
import com.podca.sdui.protocol.foundation.layout.ExperimentalGridApiProto
import com.podca.sdui.protocol.foundation.layout.ExperimentalLayoutApiProto
import com.podca.sdui.studio.core.PodcaNode

@Composable
public fun PodcaExperimentalLayoutApi(
    featureName: String = "",
    sinceVersion: String = "",
    optInRequired: Boolean = false,
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
) {
    PodcaNode(
        type = "foundation.layout.ExperimentalLayoutApi",
        message = ExperimentalLayoutApiProto(
            feature_name = featureName,
            since_version = sinceVersion,
            opt_in_required = optInRequired,
        ),
        key = key,
        actions = actions,
        encode = ExperimentalLayoutApiProto.ADAPTER::encode,
    )
}

@Composable
public fun PodcaExperimentalFlexBoxApi(
    message: String = "",
    retention: String = "",
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
) {
    PodcaNode(
        type = "foundation.layout.ExperimentalFlexBoxApi",
        message = ExperimentalFlexBoxApiProto(
            message = message,
            retention = retention,
        ),
        key = key,
        actions = actions,
        encode = ExperimentalFlexBoxApiProto.ADAPTER::encode,
    )
}

@Composable
public fun PodcaExperimentalGridApi(
    message: String = "",
    retention: String = "",
    key: String = "",
    actions: List<ActionBindingProto> = emptyList(),
) {
    PodcaNode(
        type = "foundation.layout.ExperimentalGridApi",
        message = ExperimentalGridApiProto(
            message = message,
            retention = retention,
        ),
        key = key,
        actions = actions,
        encode = ExperimentalGridApiProto.ADAPTER::encode,
    )
}
