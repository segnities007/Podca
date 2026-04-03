package com.podca.sdui.studio.ui.core

import com.podca.sdui.protocol.ui.modifier.ModifierElementProto
import com.podca.sdui.protocol.ui.modifier.ModifierPropertyProto
import com.podca.sdui.protocol.ui.modifier.ModifierProto

public class PodcaModifier private constructor(
    private val proto: ModifierProto,
) {
    public fun then(other: PodcaModifier): PodcaModifier =
        PodcaModifier(
            proto = ModifierProto(
                elements = proto.elements + other.proto.elements,
            ),
        )

    public fun toProto(): ModifierProto = proto

    public companion object {
        public val Empty: PodcaModifier = PodcaModifier(ModifierProto())

        public fun element(
            name: String,
            vararg properties: ModifierPropertyProto,
        ): PodcaModifier =
            PodcaModifier(
                ModifierProto(
                    elements = listOf(
                        ModifierElementProto(
                            name = name,
                            properties = properties.toList(),
                        ),
                    ),
                ),
            )
    }
}
