package com.podca.sdui.marketing

import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.core.ActionBindingProto
import com.podca.sdui.protocol.core.ActionTriggerProto
import com.podca.sdui.protocol.foundation.layout.ArrangementHorizontalProto
import com.podca.sdui.protocol.foundation.layout.ArrangementKindProto
import com.podca.sdui.protocol.foundation.layout.ArrangementVerticalProto
import com.podca.sdui.protocol.ui.alignment.HorizontalAlignmentPresetProto
import com.podca.sdui.protocol.ui.alignment.HorizontalAlignmentProto
import com.podca.sdui.protocol.ui.alignment.VerticalAlignmentPresetProto
import com.podca.sdui.protocol.ui.alignment.VerticalAlignmentProto
import com.podca.sdui.protocol.ui.modifier.ModifierPropertyProto
import com.podca.sdui.protocol.ui.text.TextAlignProto
import com.podca.sdui.protocol.ui.text.font.FontFamilyMonospaceProto
import com.podca.sdui.protocol.ui.text.font.FontFamilyProto
import com.podca.sdui.studio.PodcaStudio
import com.podca.sdui.remote.core.RemoteAlignmentCrossAxisProto
import com.podca.sdui.remote.core.RemoteArrangementMainAxisProto
import com.podca.sdui.remote.core.RemoteCanvasWireFixtures
import com.podca.sdui.remote.creation.PodcaRemoteNode
import com.podca.sdui.remote.creation.remoteClickable
import com.podca.sdui.remote.creation.remoteColumn
import com.podca.sdui.remote.creation.remoteRow
import com.podca.sdui.remote.creation.remoteVerticalScroll
import com.podca.sdui.remote.creation.withLayoutWeight
import com.podca.sdui.remote.creation.remotePadding
import com.podca.sdui.remote.creation.remoteSpacer
import com.podca.sdui.remote.creation.PodcaRemoteCanvasProgram
import com.podca.sdui.remote.creation.canvasDrawArcDp
import com.podca.sdui.remote.creation.canvasDrawImagePngInRectDp
import com.podca.sdui.remote.creation.canvasDrawLineDp
import com.podca.sdui.remote.creation.canvasDrawTextDp
import com.podca.sdui.remote.creation.canvasFillLinearGradientRectDp
import com.podca.sdui.remote.creation.canvasFillPolylineDp
import com.podca.sdui.remote.creation.canvasFillRadialGradientRectDp
import com.podca.sdui.remote.creation.canvasFillCircleDp
import com.podca.sdui.remote.creation.canvasFillSectorDp
import com.podca.sdui.remote.creation.canvasFillOvalDp
import com.podca.sdui.remote.creation.canvasFillRectDp
import com.podca.sdui.remote.creation.canvasFillRoundRectDp
import com.podca.sdui.remote.creation.canvasPointerInputRectDp
import com.podca.sdui.remote.creation.canvasPopClip
import com.podca.sdui.remote.creation.canvasPopTransform
import com.podca.sdui.remote.creation.canvasPushClipPolylineDp
import com.podca.sdui.remote.creation.canvasPushClipRectDp
import com.podca.sdui.remote.creation.canvasPushRotateDegDp
import com.podca.sdui.remote.creation.canvasPushScaleDp
import com.podca.sdui.remote.creation.canvasStrokePolylineDp
import com.podca.sdui.remote.creation.canvasStrokeRectDp
import com.podca.sdui.remote.creation.canvasStrokeRoundRectDp
import com.podca.sdui.remote.creation.remoteCanvasProgram
import com.podca.sdui.remote.creation.remoteText
import com.podca.sdui.studio.ui.core.PodcaColor
import com.podca.sdui.studio.ui.core.PodcaDp
import com.podca.sdui.studio.ui.core.PodcaModifier
import com.podca.sdui.studio.ui.foundation.layout.PodcaColumn
import com.podca.sdui.studio.ui.foundation.layout.PodcaRow
import com.podca.sdui.studio.ui.material3.PodcaButton
import com.podca.sdui.studio.ui.material3.PodcaCard
import com.podca.sdui.studio.ui.material3.PodcaElevatedCard
import com.podca.sdui.studio.ui.material3.PodcaHorizontalDivider
import com.podca.sdui.studio.ui.material3.PodcaOutlinedButton
import com.podca.sdui.studio.ui.material3.PodcaOutlinedCard
import com.podca.sdui.studio.ui.material3.PodcaScaffold
import com.podca.sdui.studio.ui.material3.PodcaSurface
import com.podca.sdui.studio.ui.material3.PodcaTab
import com.podca.sdui.studio.ui.material3.PodcaTabRow
import com.podca.sdui.studio.ui.material3.PodcaText
import com.podca.sdui.studio.ui.material3.PodcaTextButton

private val ClickTrigger: ActionTriggerProto = ActionTriggerProto.ACTION_TRIGGER_CLICK

/** Demo action for Remote IR tap → [PodcaRuntime.dispatch] (registered in composeApp). */
private const val RemoteDemoTapActionId: String = "podca.remote.demo_tap"

/** 0xFF6B7280 — muted body text for Remote `BasicText` demos. */
private val RemoteMutedBodyArgb: Int = (0xFF shl 24) or (0x6B shl 16) or (0x72 shl 8) or 0x80

private val RemoteCanvasBgArgb: Int = (0xFF shl 24) or (0x1A shl 16) or (0x1C shl 8) or 0x20
private val RemoteCanvasBarArgb: Int = (0xFF shl 24) or (0x26 shl 16) or (0xA6 shl 8) or 0x9A
private val RemoteCanvasAccentArgb: Int = (0xFF shl 24) or (0xF5 shl 16) or (0x9E shl 8) or 0x0B

private val RemoteCanvasHairlineArgb: Int = (0xFF shl 24) or (0x55 shl 16) or (0x5A shl 8) or 0x67
private val RemoteCanvasLabelArgb: Int = (0xFF shl 24) or (0xE8 shl 16) or (0xEA shl 8) or 0xED
private val RemoteCanvasGradientEndArgb: Int = (0xFF shl 24) or (0x22 shl 16) or (0x28 shl 8) or 0x36
/** Translucent teal for polyline fill demo (SSoT path ops). */
private val RemoteCanvasPolyFillArgb: Int = (0x55 shl 24) or (0x26 shl 16) or (0x99 shl 8) or 0x9A

private fun verticalSpaced(dp: Float): ArrangementVerticalProto =
    ArrangementVerticalProto(
        kind = ArrangementKindProto.ARRANGEMENT_KIND_SPACED_BY,
        spacing = PodcaDp(dp),
    )

private fun rowSpaced(dp: Float): ArrangementHorizontalProto =
    ArrangementHorizontalProto(
        kind = ArrangementKindProto.ARRANGEMENT_KIND_SPACED_BY,
        spacing = PodcaDp(dp),
        alignment = HorizontalAlignmentProto(
            preset = HorizontalAlignmentPresetProto.START,
        ),
    )

private fun modFillMaxWidth(): PodcaModifier =
    PodcaModifier.element(
        "fill",
        ModifierPropertyProto(key = "direction", int32_value = 0),
        ModifierPropertyProto(key = "fraction", float_value = 1f),
    )

private fun modPaddingSymmetric(horizontal: Float, vertical: Float): PodcaModifier =
    PodcaModifier.element(
        "padding",
        ModifierPropertyProto(key = "start", dp_value = PodcaDp(horizontal)),
        ModifierPropertyProto(key = "end", dp_value = PodcaDp(horizontal)),
        ModifierPropertyProto(key = "top", dp_value = PodcaDp(vertical)),
        ModifierPropertyProto(key = "bottom", dp_value = PodcaDp(vertical)),
    )

private val ColorMutedBody = PodcaColor(0.76f, 0.76f, 0.82f)
private val ColorMutedSmall = PodcaColor(0.62f, 0.62f, 0.70f)
private val ColorCodeBg = PodcaColor(0.12f, 0.12f, 0.14f)
@Composable internal fun PodcaMarketingIntroDocument(selectedTab: Int) {
    PodcaScaffold(
        topBar = {
            PodcaSurface(tonalElevation = PodcaDp(4f)) {
                PodcaColumn(
                    modifier = modFillMaxWidth(),
                    verticalArrangement = verticalSpaced(8f),
                ) {
                    PodcaRow(
                        modifier = modFillMaxWidth().then(modPaddingSymmetric(24f, 16f)),
                        horizontalArrangement = ArrangementHorizontalProto(
                            kind = ArrangementKindProto.ARRANGEMENT_KIND_SPACE_BETWEEN,
                        ),
                        verticalAlignment = VerticalAlignmentProto(
                            preset = VerticalAlignmentPresetProto.CENTER_VERTICALLY,
                        ),
                    ) {
                        PodcaColumn(
                            horizontalAlignment = HorizontalAlignmentProto(
                                preset = HorizontalAlignmentPresetProto.START,
                            ),
                        ) {
                            PodcaText(
                                text = "Podca",
                                fontSize = 26f,
                                fontWeight = 700,
                                letterSpacing = 0.4f,
                            )
                            PodcaText(
                                text = "Server-driven UI for Compose Multiplatform",
                                fontSize = 13f,
                                color = ColorMutedSmall,
                                lineHeight = 18f,
                            )
                        }
                        PodcaText(
                            text = "KMP · Material 3 · Protobuf",
                            fontSize = 11f,
                            fontWeight = 500,
                            color = ColorMutedSmall,
                            textAlign = TextAlignProto.TEXT_ALIGN_END,
                        )
                    }
                    PodcaTabRow(
                        selectedTabIndex = selectedTab,
                        modifier = modFillMaxWidth(),
                    ) {
                        NavTab(
                            selected = selectedTab == 0,
                            label = "Overview",
                            tabKey = "tab_nav_intro",
                            actionId = "podca.nav.home",
                        )
                        NavTab(
                            selected = selectedTab == 1,
                            label = "Architecture",
                            tabKey = "tab_nav_stack",
                            actionId = "podca.nav.stack",
                        )
                        NavTab(
                            selected = selectedTab == 2,
                            label = "Get started",
                            tabKey = "tab_nav_start",
                            actionId = "podca.nav.start",
                        )
                        NavTab(
                            selected = selectedTab == 3,
                            label = "Examples",
                            tabKey = "tab_nav_examples",
                            actionId = "podca.nav.examples",
                        )
                    }
                    PodcaHorizontalDivider()
                }
            }
        },
        bottomBar = {
            PodcaSurface(tonalElevation = PodcaDp(8f)) {
                PodcaColumn(
                    modifier = modFillMaxWidth().then(modPaddingSymmetric(20f, 20f)),
                    verticalArrangement = verticalSpaced(14f),
                    horizontalAlignment = HorizontalAlignmentProto(
                        preset = HorizontalAlignmentPresetProto.CENTER_HORIZONTALLY,
                    ),
                ) {
                    PodcaRow(
                        modifier = modFillMaxWidth(),
                        horizontalArrangement = ArrangementHorizontalProto(
                            kind = ArrangementKindProto.ARRANGEMENT_KIND_SPACE_EVENLY,
                        ),
                        verticalAlignment = VerticalAlignmentProto(
                            preset = VerticalAlignmentPresetProto.CENTER_VERTICALLY,
                        ),
                    ) {
                        FooterLink("Overview", "ft_home", "podca.nav.home")
                        FooterLink("Architecture", "ft_arch", "podca.nav.stack")
                        FooterLink("Get started", "ft_go", "podca.nav.start")
                        FooterLink("Examples", "ft_ex", "podca.nav.examples")
                    }
                    PodcaHorizontalDivider()
                    PodcaText(
                        text = "© 2026 Podca · Licensed under Apache License 2.0",
                        fontSize = 12f,
                        color = ColorMutedSmall,
                        textAlign = TextAlignProto.TEXT_ALIGN_CENTER,
                    )
                    PodcaText(
                        text = "Bytes are served from your API (demo: GET /api/podca/marketing-document?tab=0..3). The host supplies Player, theme colors, navigation bridge, and web URL sync.",
                        fontSize = 11f,
                        color = ColorMutedSmall,
                        textAlign = TextAlignProto.TEXT_ALIGN_CENTER,
                        lineHeight = 15f,
                    )
                }
            }
        },
        content = {
            PodcaSurface {
                when (selectedTab) {
                    0 -> IntroHomeSdui()
                    1 -> StackOverviewSdui()
                    2 -> GetStartedSdui()
                    else -> ExamplesSdui()
                }
            }
        },
    )
}

@Composable
private fun FooterLink(label: String, key: String, actionId: String) {
    PodcaTextButton(
        key = key,
        actions = listOf(
            ActionBindingProto(
                trigger = ClickTrigger,
                action_id = actionId,
            ),
        ),
    ) {
        PodcaText(text = label, fontSize = 13f)
    }
}

@Composable
private fun IntroHomeSdui() {
    PodcaColumn(
        modifier = modFillMaxWidth().then(modPaddingSymmetric(24f, 28f)),
        verticalArrangement = verticalSpaced(24f),
        horizontalAlignment = HorizontalAlignmentProto(
            preset = HorizontalAlignmentPresetProto.START,
        ),
    ) {
        PodcaText(
            text = "Ship UI from the server. Render everywhere.",
            fontSize = 32f,
            fontWeight = 700,
            lineHeight = 38f,
        )
        PodcaText(
            text = "Author trees in Kotlin with the Studio DSL, serialize them to a compact binary protocol, and let Player paint the same experience on Android, iOS, Desktop, JS, and Wasm — without shipping a new binary for every label or layout experiment.",
            fontSize = 16f,
            lineHeight = 24f,
            color = ColorMutedBody,
        )
        PodcaRow(
            horizontalArrangement = rowSpaced(12f),
            verticalAlignment = VerticalAlignmentProto(
                preset = VerticalAlignmentPresetProto.CENTER_VERTICALLY,
            ),
        ) {
            PodcaOutlinedButton(
                key = "hero_arch",
                actions = listOf(
                    ActionBindingProto(trigger = ClickTrigger, action_id = "podca.nav.stack"),
                ),
            ) {
                PodcaText("Architecture")
            }
            PodcaButton(
                key = "hero_start",
                actions = listOf(
                    ActionBindingProto(trigger = ClickTrigger, action_id = "podca.nav.start"),
                ),
            ) {
                PodcaText("Get started")
            }
            PodcaOutlinedButton(
                key = "hero_examples",
                actions = listOf(
                    ActionBindingProto(trigger = ClickTrigger, action_id = "podca.nav.examples"),
                ),
            ) {
                PodcaText("Examples")
            }
        }
        SectionHeading("Built like a product, not a toy demo")
        PodcaText(
            text = "Podca keeps a strict boundary: bytes describe structure and bindings; your app owns navigation, networking, and domain logic. That is the same separation you see in mature KMP ecosystems — explicit contracts, predictable runtime behavior.",
            fontSize = 15f,
            lineHeight = 22f,
            color = ColorMutedBody,
        )
        PodcaElevatedCard(
            modifier = modFillMaxWidth(),
        ) {
            PodcaColumn(
                modifier = modPaddingSymmetric(20f, 18f),
                verticalArrangement = verticalSpaced(10f),
            ) {
                PodcaText(
                    text = "Protocol → Studio → Player",
                    fontWeight = 600,
                    fontSize = 17f,
                )
                PodcaText(
                    text = "material3.* and foundation.* nodes map to real Compose widgets. Actions round-trip through PodcaRuntime.register so you can mirror browser history, deep links, or analytics without forking the renderer.",
                    fontSize = 14f,
                    lineHeight = 21f,
                    color = ColorMutedBody,
                )
            }
        }
        PodcaElevatedCard(
            modifier = modFillMaxWidth(),
            key = "card_remote_ir",
        ) {
            PodcaColumn(
                modifier = modPaddingSymmetric(20f, 18f),
                verticalArrangement = verticalSpaced(10f),
            ) {
                PodcaRemoteNode(
                    key = "remote_intro_callout",
                    actions =
                        listOf(
                            ActionBindingProto(
                                trigger = ClickTrigger,
                                action_id = RemoteDemoTapActionId,
                            ),
                        ),
                    root =
                        remoteVerticalScroll(
                            maxHeightDp = 200f,
                            fillMaxWidth = true,
                            child =
                                remoteColumn(
                                    padding = remotePadding(bottom = 4f),
                                    verticalMain = RemoteArrangementMainAxisProto.REMOTE_MAIN_AXIS_SPACED_BY,
                                    verticalSpacingDp = 8f,
                                    horizontalCross = RemoteAlignmentCrossAxisProto.REMOTE_CROSS_AXIS_UNSPECIFIED,
                                    remoteText(
                                        text = "Thin client, compact layout IR",
                                        fontSize = 17f,
                                        fontWeight = 600f,
                                    ),
                                    remoteText(
                                        text =
                                            "This block is one encoded RemoteNodeProto subtree (Column / Text / Spacer / VerticalScroll / fill / ARGB text) " +
                                                "rendered by Player without material3-specific codecs — add kinds in protocol when you need more primitives.",
                                        fontSize = 14f,
                                        textColorArgb = RemoteMutedBodyArgb,
                                    ),
                                    remoteRow(
                                        padding = null,
                                        horizontalMain = RemoteArrangementMainAxisProto.REMOTE_MAIN_AXIS_UNSPECIFIED,
                                        horizontalSpacingDp = 0f,
                                        verticalCross = RemoteAlignmentCrossAxisProto.REMOTE_CROSS_AXIS_UNSPECIFIED,
                                        remoteText(text = "Row + weight", fontSize = 12f).withLayoutWeight(1f),
                                        remoteText(text = "1 : 1", fontSize = 12f).withLayoutWeight(1f),
                                    ),
                                    remoteSpacer(heightDp = 10f),
                                    remoteClickable(
                                        actionId = RemoteDemoTapActionId,
                                        child =
                                            remoteText(
                                                text = "Tap here — dispatches podca.remote.demo_tap via PodcaRuntime",
                                                fontSize = 14f,
                                                fontWeight = 600f,
                                            ),
                                    ),
                                ),
                        ),
                )
            }
        }
        PodcaElevatedCard(
            modifier = modFillMaxWidth(),
            key = "card_remote_canvas_ops",
        ) {
            PodcaColumn(
                modifier = modPaddingSymmetric(20f, 18f),
                verticalArrangement = verticalSpaced(10f),
            ) {
                PodcaText(
                    text = "Canvas op stream (Remote Compose–style)",
                    fontWeight = 600,
                    fontSize = 17f,
                )
                PodcaText(
                    text =
                        "Op stream (parity with AndroidX RemoteCanvas): polyline, gradients, ovals, circle, arc / sector, clipPath (poly push + pop), text, translate/scale/rotate stack, stroke join/cap, `DRAW_IMAGE` (PNG → rect), pointer. " +
                            "Additive opcodes keep the default `wire_opset_version`; bump only on breaking interpreter changes (AGENTS.md).",
                    fontSize = 14f,
                    lineHeight = 21f,
                    color = ColorMutedBody,
                )
                PodcaRemoteCanvasProgram(
                    key = "remote_canvas_demo_stripes",
                    program =
                        remoteCanvasProgram(
                            widthDp = 200f,
                            heightDp = 88f,
                            canvasFillLinearGradientRectDp(
                                0f,
                                0f,
                                200f,
                                88f,
                                RemoteCanvasBgArgb,
                                RemoteCanvasGradientEndArgb,
                            ),
                            canvasFillLinearGradientRectDp(
                                0f,
                                0f,
                                6f,
                                88f,
                                RemoteCanvasAccentArgb,
                                RemoteCanvasHairlineArgb,
                                gradientAxis = 1,
                            ),
                            canvasStrokeRectDp(0f, 0f, 200f, 88f, 1f, RemoteCanvasHairlineArgb),
                            canvasPushClipRectDp(14f, 8f, 186f, 80f),
                            canvasFillRadialGradientRectDp(
                                138f,
                                4f,
                                194f,
                                32f,
                                RemoteCanvasAccentArgb,
                                RemoteCanvasBgArgb,
                            ),
                            canvasFillOvalDp(154f, 8f, 184f, 26f, RemoteCanvasAccentArgb),
                            canvasFillCircleDp(169f, 17f, 3.5f, RemoteCanvasLabelArgb),
                            canvasDrawArcDp(
                                154f,
                                8f,
                                184f,
                                26f,
                                startAngleDeg = -90f,
                                sweepAngleDeg = 115f,
                                strokeWidthDp = 1.2f,
                                colorArgb = RemoteCanvasHairlineArgb,
                            ),
                            canvasFillSectorDp(
                                148f,
                                52f,
                                188f,
                                78f,
                                startAngleDeg = 200f,
                                sweepAngleDeg = 48f,
                                colorArgb = RemoteCanvasPolyFillArgb,
                            ),
                            canvasDrawTextDp(
                                18f,
                                10f,
                                182f,
                                32f,
                                "Canvas op stream",
                                11f,
                                600,
                                RemoteCanvasLabelArgb,
                                textAlign = 1,
                                textAlignVertical = 1,
                            ),
                            canvasDrawLineDp(
                                18f,
                                34f,
                                182f,
                                46f,
                                1.5f,
                                RemoteCanvasAccentArgb,
                            ),
                            canvasFillRoundRectDp(18f, 48f, 182f, 66f, 8f, RemoteCanvasBarArgb),
                            canvasFillRectDp(18f, 66f, 182f, 76f, RemoteCanvasAccentArgb),
                            canvasStrokeRoundRectDp(17f, 47f, 183f, 67f, 8f, 1f, RemoteCanvasHairlineArgb),
                            canvasFillPolylineDp(
                                RemoteCanvasPolyFillArgb,
                                138f,
                                56f,
                                186f,
                                56f,
                                162f,
                                76f,
                            ),
                            canvasPushClipPolylineDp(
                                listOf(
                                    92f,
                                    58f,
                                    118f,
                                    74f,
                                    108f,
                                    78f,
                                    86f,
                                    68f,
                                ),
                            ),
                            canvasFillRectDp(20f, 56f, 180f, 80f, RemoteCanvasPolyFillArgb),
                            canvasPopClip(),
                            canvasPushScaleDp(pivotXDp = 99f, pivotYDp = 70f, scaleX = 1.06f, scaleY = 1.06f),
                            canvasPushRotateDegDp(pivotXDp = 99f, pivotYDp = 70f, degrees = -12f),
                            canvasStrokePolylineDp(
                                RemoteCanvasLabelArgb,
                                1f,
                                listOf(
                                    20f,
                                    68f,
                                    60f,
                                    72f,
                                    100f,
                                    68f,
                                    140f,
                                    73f,
                                    178f,
                                    69f,
                                ),
                                strokeJoin = 1,
                                strokeCap = 1,
                            ),
                            canvasPopTransform(),
                            canvasPopTransform(),
                            canvasPopClip(),
                            canvasDrawImagePngInRectDp(
                                158f,
                                4f,
                                190f,
                                30f,
                                RemoteCanvasWireFixtures.demoBadgePng8x8Teal26A69A,
                            ),
                            canvasPointerInputRectDp(
                                10f,
                                58f,
                                190f,
                                82f,
                                RemoteDemoTapActionId,
                            ),
                        ),
                )
                PodcaText(
                    text = "Tap the lower bar — same podca.remote.demo_tap as the declarative remote block.",
                    fontSize = 12f,
                    color = ColorMutedSmall,
                )
            }
        }
        SectionHeading("Why teams reach for SDUI")
        FeatureCard(
            title = "One definition, many surfaces",
            body = "The same encoded document can hydrate Player on mobile, desktop, and web. Marketing pages and in-app tools can share one authoring model.",
        )
        FeatureCard(
            title = "Safe, versioned payloads",
            body = "Protobuf-backed nodes keep the wire explicit. You can evolve fields and still reason about what older clients will ignore or default.",
        )
        FeatureCard(
            title = "Escape hatches that stay honest",
            body = "When encoding fails, the shell can show a small host fallback while the happy path remains fully SDUI — no shadow UI framework hiding in the demo.",
        )
    }
}

@Composable
private fun SectionHeading(text: String) {
    PodcaText(
        text = text,
        fontSize = 20f,
        fontWeight = 600,
        lineHeight = 26f,
    )
}

@Composable
private fun FeatureCard(title: String, body: String) {
    PodcaElevatedCard(modifier = modFillMaxWidth()) {
        PodcaColumn(
            modifier = modPaddingSymmetric(20f, 18f),
            verticalArrangement = verticalSpaced(8f),
        ) {
            PodcaText(
                text = title,
                fontWeight = 600,
                fontSize = 16f,
            )
            PodcaText(
                text = body,
                fontSize = 14f,
                lineHeight = 21f,
                color = ColorMutedBody,
            )
        }
    }
}

@Composable
private fun NavTab(
    selected: Boolean,
    label: String,
    tabKey: String,
    actionId: String,
) {
    PodcaTab(
        selected = selected,
        text = label,
        key = tabKey,
        actions = listOf(
            ActionBindingProto(
                trigger = ClickTrigger,
                action_id = actionId,
            ),
        ),
    )
}

@Composable
private fun StackOverviewSdui() {
    PodcaColumn(
        modifier = modFillMaxWidth().then(modPaddingSymmetric(24f, 28f)),
        verticalArrangement = verticalSpaced(22f),
        horizontalAlignment = HorizontalAlignmentProto(
            preset = HorizontalAlignmentPresetProto.START,
        ),
    ) {
        PodcaText(
            text = "Architecture",
            fontSize = 28f,
            fontWeight = 700,
        )
        PodcaText(
            text = "Three layers, one pipeline. Each layer has a narrow job so you can test, version, and ship them independently — similar to how Kotlin Multiplatform splits common code from platform entry points.",
            fontSize = 15f,
            lineHeight = 22f,
            color = ColorMutedBody,
        )
        LayerCard(
            step = "01",
            title = "Protocol",
            body = "Wire types describe nodes, layout, Material tokens, and action bindings. The payload stays explicit: no implicit JSON magic and no hand-rolled ad-hoc trees.",
        )
        LayerCard(
            step = "02",
            title = "Studio",
            body = "Composable-style DSLs assemble the tree. PodcaStudio.encode turns it into bytes you can store, cache, or stream from Ktor, CDN, or your CI preview service.",
        )
        LayerCard(
            step = "03",
            title = "Player",
            body = "The runtime decodes bytes, instantiates Compose, and dispatches actions back to your handlers — navigation, analytics, feature flags, or server round-trips.",
        )
        PodcaCard(modifier = modFillMaxWidth()) {
            PodcaColumn(
                modifier = modPaddingSymmetric(20f, 18f),
                verticalArrangement = verticalSpaced(8f),
            ) {
                PodcaText(
                    text = "Client integration sketch",
                    fontWeight = 600,
                    fontSize = 16f,
                )
                CodeLine("val runtime = PodcaRuntime()")
                CodeLine("runtime.loadDocument(bytes)")
                CodeLine("PodcaPlayer(runtime = runtime)")
                CodeLine("runtime.register(\"action.id\") { … }")
            }
        }
    }
}

@Composable
private fun LayerCard(step: String, title: String, body: String) {
    PodcaElevatedCard(modifier = modFillMaxWidth()) {
        PodcaRow(
            modifier = modPaddingSymmetric(18f, 18f),
            horizontalArrangement = rowSpaced(16f),
            verticalAlignment = VerticalAlignmentProto(
                preset = VerticalAlignmentPresetProto.TOP,
            ),
        ) {
            PodcaSurface(
                color = ColorCodeBg,
                shape = null,
            ) {
                PodcaColumn(modifier = modPaddingSymmetric(12f, 8f)) {
                    PodcaText(
                        text = step,
                        fontSize = 12f,
                        fontWeight = 700,
                        color = PodcaColor(0.78f, 0.70f, 1f),
                    )
                }
            }
            PodcaColumn(
                modifier = modFillMaxWidth(),
                verticalArrangement = verticalSpaced(6f),
            ) {
                PodcaText(
                    text = title,
                    fontSize = 18f,
                    fontWeight = 600,
                )
                PodcaText(
                    text = body,
                    fontSize = 14f,
                    lineHeight = 21f,
                    color = ColorMutedBody,
                )
            }
        }
    }
}

@Composable
private fun CodeLine(text: String) {
    PodcaText(
        text = text,
        fontSize = 13f,
        lineHeight = 19f,
        fontFamily = FontFamilyProto(
            monospace_family = FontFamilyMonospaceProto(),
        ),
        color = PodcaColor(0.85f, 0.88f, 0.92f),
    )
}

@Composable
private fun GetStartedSdui() {
    PodcaColumn(
        modifier = modFillMaxWidth().then(modPaddingSymmetric(24f, 28f)),
        verticalArrangement = verticalSpaced(20f),
        horizontalAlignment = HorizontalAlignmentProto(
            preset = HorizontalAlignmentPresetProto.START,
        ),
    ) {
        PodcaText(
            text = "Get started",
            fontSize = 28f,
            fontWeight = 700,
        )
        PodcaText(
            text = "Follow the same rhythm as adopting any KMP library: encode a document, load it in a runtime, render with one composable, then wire actions locally.",
            fontSize = 15f,
            lineHeight = 22f,
            color = ColorMutedBody,
        )
        StepBlock(
            n = 1,
            title = "Author on the server (or CI)",
            body = "Use PodcaScaffold, PodcaText, PodcaButton, foundation.layout.* nodes — same Kotlin DSL as Studio. Encode to bytes on the JVM and expose them over HTTP (this demo: /api/podca/marketing-document).",
            code = "client.get(\"/api/podca/marketing-document?tab=0\").bodyAsBytes()",
        )
        StepBlock(
            n = 2,
            title = "Load the document",
            body = "Hydrate the active tree on the client. Swap bytes when routes or experiments change.",
            code = "runtime.loadDocument(bytes)",
        )
        StepBlock(
            n = 3,
            title = "Host PodcaPlayer",
            body = "Drop Player inside your existing Compose hierarchy; keep MaterialTheme for brand colors.",
            code = "PodcaPlayer(runtime = runtime, modifier = Modifier.fillMaxSize())",
        )
        StepBlock(
            n = 4,
            title = "Register actions",
            body = "Map action_id strings to navigation, RPC, or analytics. Return PodcaAcceptedActionResult to acknowledge delivery.",
            code = "runtime.register(\"cart.checkout\") { event -> … }",
        )
        StepBlock(
            n = 5,
            title = "Ship the web bundle",
            body = "Produce static Wasm/JS output and host it beside your API or static CDN — same as other KMP web targets.",
            code = "./gradlew :composeApp:wasmJsBrowserDistribution",
        )
    }
}

@Composable
private fun StepBlock(n: Int, title: String, body: String, code: String) {
    PodcaOutlinedCard(modifier = modFillMaxWidth()) {
        PodcaColumn(
            modifier = modPaddingSymmetric(20f, 18f),
            verticalArrangement = verticalSpaced(12f),
        ) {
            PodcaRow(
                verticalAlignment = VerticalAlignmentProto(
                    preset = VerticalAlignmentPresetProto.CENTER_VERTICALLY,
                ),
                horizontalArrangement = rowSpaced(12f),
            ) {
                PodcaText(
                    text = n.toString(),
                    fontSize = 14f,
                    fontWeight = 700,
                    color = PodcaColor(0.78f, 0.70f, 1f),
                )
                PodcaText(
                    text = title,
                    fontSize = 17f,
                    fontWeight = 600,
                )
            }
            PodcaText(
                text = body,
                fontSize = 14f,
                lineHeight = 21f,
                color = ColorMutedBody,
            )
            PodcaSurface(color = ColorCodeBg) {
                PodcaColumn(modifier = modPaddingSymmetric(14f, 12f)) {
                    PodcaText(
                        text = code,
                        fontSize = 13f,
                        lineHeight = 19f,
                        fontFamily = FontFamilyProto(
                            monospace_family = FontFamilyMonospaceProto(),
                        ),
                        color = PodcaColor(0.88f, 0.90f, 0.94f),
                    )
                }
            }
        }
    }
}

@Composable
private fun ExamplesSdui() {
    PodcaColumn(
        modifier = modFillMaxWidth().then(modPaddingSymmetric(24f, 28f)),
        verticalArrangement = verticalSpaced(20f),
        horizontalAlignment = HorizontalAlignmentProto(
            preset = HorizontalAlignmentPresetProto.START,
        ),
    ) {
        PodcaText(
            text = "What this demo proves",
            fontSize = 28f,
            fontWeight = 700,
        )
        PodcaText(
            text = "If you squint, this page behaves like kotlinlang.org or insert-koin.io: clear hierarchy, dense but readable copy, primary and secondary actions, and a footer you can actually use. The difference is every pixel here came through the SDUI encoder.",
            fontSize = 15f,
            lineHeight = 22f,
            color = ColorMutedBody,
        )
        ExampleBullet(
            title = "Full chrome in the document",
            body = "Top bar, tabs, dividers, cards, buttons, code blocks, and footer links are material3.* / foundation.* nodes interpreted by Player.",
        )
        ExampleBullet(
            title = "Actions drive real navigation",
            body = "Tabs, hero CTAs, and footer TextButtons call the same action_ids that NavController listens to, so URL state and SDUI stay aligned on web.",
        )
        ExampleBullet(
            title = "Host responsibilities stay tiny",
            body = "PodcaPlayer, MaterialTheme colors, hash/history sync, and fetch-failure fallback (local encode) are the only local Compose concerns.",
        )
    }
}

@Composable
private fun ExampleBullet(title: String, body: String) {
    PodcaElevatedCard(modifier = modFillMaxWidth()) {
        PodcaColumn(
            modifier = modPaddingSymmetric(20f, 18f),
            verticalArrangement = verticalSpaced(8f),
        ) {
            PodcaText(
                text = title,
                fontWeight = 600,
                fontSize = 16f,
            )
            PodcaText(
                text = body,
                fontSize = 14f,
                lineHeight = 21f,
                color = ColorMutedBody,
            )
        }
    }
}

/** Encodes the marketing SDUI tree (same bytes the Ktor server serves at `/api/podca/marketing-document`). */
suspend fun encodePodcaMarketingDocument(selectedTab: Int): ByteArray =
    PodcaStudio.encode { PodcaMarketingIntroDocument(selectedTab = selectedTab) }
