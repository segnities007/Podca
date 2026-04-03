package com.podca.sdui.studio.ui.material3

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.material3.ExperimentalMaterial3ApiProto
import com.podca.sdui.protocol.material3.ExperimentalMaterial3ExpressiveApiProto
import com.podca.sdui.studio.core.PodcaNode

@Composable
public fun PodcaExperimentalMaterial3Api(
    featureName: String = "",
    sinceVersion: String = "",
    optInRequired: Boolean = false,
) {
    PodcaNode(
        type = "material3.ExperimentalMaterial3Api",
        message = ExperimentalMaterial3ApiProto(
            feature_name = featureName,
            since_version = sinceVersion,
            opt_in_required = optInRequired,
        ),
        encode = ExperimentalMaterial3ApiProto.ADAPTER::encode,
    )
}

@Composable
public fun PodcaExperimentalMaterial3ExpressiveApi(
    featureName: String = "",
    sinceVersion: String = "",
    optInRequired: Boolean = false,
    expressiveThemeRequired: Boolean = false,
) {
    PodcaNode(
        type = "material3.ExperimentalMaterial3ExpressiveApi",
        message = ExperimentalMaterial3ExpressiveApiProto(
            feature_name = featureName,
            since_version = sinceVersion,
            opt_in_required = optInRequired,
            expressive_theme_required = expressiveThemeRequired,
        ),
        encode = ExperimentalMaterial3ExpressiveApiProto.ADAPTER::encode,
    )
}
