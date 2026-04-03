package com.podca.sdui.studio.ui.material3

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.material3.TonalPaletteProto
import com.podca.sdui.protocol.ui.graphics.ColorProto
import com.podca.sdui.studio.core.PodcaNode

@Composable
public fun PodcaTonalPalette(
    tone0: ColorProto? = null,
    tone10: ColorProto? = null,
    tone20: ColorProto? = null,
    tone30: ColorProto? = null,
    tone40: ColorProto? = null,
    tone50: ColorProto? = null,
    tone60: ColorProto? = null,
    tone70: ColorProto? = null,
    tone80: ColorProto? = null,
    tone90: ColorProto? = null,
    tone95: ColorProto? = null,
    tone98: ColorProto? = null,
    tone99: ColorProto? = null,
    tone100: ColorProto? = null,
) {
    PodcaNode(
        type = "material3.TonalPalette",
        message = TonalPaletteProto(
            tone0 = tone0,
            tone10 = tone10,
            tone20 = tone20,
            tone30 = tone30,
            tone40 = tone40,
            tone50 = tone50,
            tone60 = tone60,
            tone70 = tone70,
            tone80 = tone80,
            tone90 = tone90,
            tone95 = tone95,
            tone98 = tone98,
            tone99 = tone99,
            tone100 = tone100,
        ),
        encode = TonalPaletteProto.ADAPTER::encode,
    )
}
