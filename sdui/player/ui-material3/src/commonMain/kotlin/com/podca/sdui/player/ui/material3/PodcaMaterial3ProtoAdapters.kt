package com.podca.sdui.player.ui.material3

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.runtime.Composable
import com.podca.sdui.protocol.foundation.layout.PaddingValuesProto
import com.podca.sdui.protocol.material3.ButtonElevationProto
import com.podca.sdui.protocol.material3.CardElevationProto
import com.podca.sdui.protocol.material3.MaterialTextProto
import com.podca.sdui.protocol.ui.graphics.ColorComponentsProto
import com.podca.sdui.protocol.ui.graphics.ColorProto
import com.podca.sdui.protocol.ui.graphics.ShapeProto
import com.podca.sdui.protocol.ui.graphics.StrokeCapProto
import com.podca.sdui.protocol.ui.modifier.ModifierProto
import com.podca.sdui.protocol.ui.text.ParagraphStyleProto
import com.podca.sdui.protocol.ui.text.TextDecorationProto
import com.podca.sdui.protocol.ui.text.TextOverflowProto
import com.podca.sdui.protocol.ui.text.TextStyleProto
import com.podca.sdui.protocol.ui.text.font.FontFamilyProto
import com.podca.sdui.protocol.ui.text.font.FontStyleProto
import com.podca.sdui.protocol.ui.text.font.FontWeightProto
import com.podca.sdui.protocol.ui.unit.DpProto
import com.podca.sdui.protocol.ui.unit.TextUnitProto
import androidx.compose.ui.graphics.StrokeCap

internal fun ModifierProto?.toComposeModifier(): androidx.compose.ui.Modifier =
    androidx.compose.ui.Modifier

internal fun DpProto?.toComposeDp(default: Dp = Dp.Unspecified): Dp =
    this?.value_?.dp ?: default

internal fun TextUnitProto?.toComposeTextUnit(default: TextUnit = TextUnit.Unspecified): TextUnit =
    when (val proto = this) {
        null -> default
        else -> when (proto.type) {
            com.podca.sdui.protocol.ui.unit.TextUnitTypeProto.TEXT_UNIT_TYPE_UNSPECIFIED -> default
            com.podca.sdui.protocol.ui.unit.TextUnitTypeProto.TEXT_UNIT_TYPE_SP -> proto.value_.sp
            com.podca.sdui.protocol.ui.unit.TextUnitTypeProto.TEXT_UNIT_TYPE_EM -> proto.value_.em
        }
    }

internal fun ColorProto?.toComposeColorOrNull(): Color? =
    when (val proto = this) {
        null -> null
        else -> {
            val argb = proto.argb
            val packed = proto.packed
            val components = proto.components
            when {
                argb != null -> Color(argb.toLong() and 0xffffffffL)
                packed != null -> Color(packed)
                components != null -> components.toComposeColor()
                else -> null
            }
        }
    }

internal fun ColorComponentsProto.toComposeColor(): Color =
    Color(red = red, green = green, blue = blue, alpha = alpha)

internal fun ShapeProto?.toComposeShapeOrNull(): Shape? =
    when (val proto = this) {
        null -> null
        else -> {
            val roundedCorner = proto.rounded_corner
            val cutCorner = proto.cut_corner
            val customRounded = proto.custom_rounded_corner
            when {
                proto.rectangle == true -> RoundedCornerShape(0.dp)
                proto.circle == true -> CircleShape
                roundedCorner != null -> RoundedCornerShape(roundedCorner.toComposeDp())
                cutCorner != null -> CutCornerShape(cutCorner.toComposeDp())
                customRounded != null -> RoundedCornerShape(
                    topStart = customRounded.top_start.toComposeDp(),
                    topEnd = customRounded.top_end.toComposeDp(),
                    bottomEnd = customRounded.bottom_end.toComposeDp(),
                    bottomStart = customRounded.bottom_start.toComposeDp(),
                )
                else -> null
            }
        }
    }

internal fun PaddingValuesProto?.toComposePaddingValues(): PaddingValues =
    PaddingValues(
        start = this?.start.toComposeDp(default = 0.dp),
        top = this?.top.toComposeDp(default = 0.dp),
        end = this?.end.toComposeDp(default = 0.dp),
        bottom = this?.bottom.toComposeDp(default = 0.dp),
    )

internal fun com.podca.sdui.protocol.material3.BorderStrokeProto?.toComposeBorderStrokeOrNull(): BorderStroke =
    when (val proto = this) {
        null -> BorderStroke(width = 0.dp, color = Color.Transparent)
        else -> BorderStroke(
            width = proto.width.toComposeDp(default = 1.dp),
            color = proto.color.toComposeColorOrNull() ?: Color.Transparent,
        )
    }

internal fun FontWeightProto?.toComposeFontWeightOrNull(): FontWeight? =
    this?.weight?.let { FontWeight(it.coerceIn(1, 1000)) }

internal fun FontStyleProto?.toComposeFontStyleOrNull(): FontStyle? =
    when (this) {
        FontStyleProto.FONT_STYLE_ITALIC -> FontStyle.Italic
        FontStyleProto.FONT_STYLE_NORMAL -> FontStyle.Normal
        null -> null
    }

internal fun FontFamilyProto?.toComposeFontFamilyOrNull(): FontFamily? =
    when (val proto = this) {
        null -> null
        else -> when {
            proto.default_family != null -> FontFamily.Default
            proto.generic_family != null -> FontFamily.Default
            proto.list_family != null -> FontFamily.Default
            proto.loaded_family != null -> FontFamily.Default
            proto.sans_serif_family != null -> FontFamily.SansSerif
            proto.serif_family != null -> FontFamily.Serif
            proto.monospace_family != null -> FontFamily.Monospace
            proto.cursive_family != null -> FontFamily.Cursive
            else -> null
        }
    }

internal fun TextOverflowProto?.toComposeTextOverflowOrNull(): TextOverflow? =
    when (this) {
        TextOverflowProto.TEXT_OVERFLOW_CLIP -> TextOverflow.Clip
        TextOverflowProto.TEXT_OVERFLOW_ELLIPSIS -> TextOverflow.Ellipsis
        TextOverflowProto.TEXT_OVERFLOW_VISIBLE -> TextOverflow.Visible
        TextOverflowProto.TEXT_OVERFLOW_START_ELLIPSIS -> TextOverflow.StartEllipsis
        TextOverflowProto.TEXT_OVERFLOW_MIDDLE_ELLIPSIS -> TextOverflow.MiddleEllipsis
        null, TextOverflowProto.TEXT_OVERFLOW_UNSPECIFIED -> null
    }

internal fun com.podca.sdui.protocol.ui.text.TextAlignProto?.toComposeTextAlignOrNull(): TextAlign? =
    when (this) {
        com.podca.sdui.protocol.ui.text.TextAlignProto.TEXT_ALIGN_LEFT -> TextAlign.Left
        com.podca.sdui.protocol.ui.text.TextAlignProto.TEXT_ALIGN_RIGHT -> TextAlign.Right
        com.podca.sdui.protocol.ui.text.TextAlignProto.TEXT_ALIGN_CENTER -> TextAlign.Center
        com.podca.sdui.protocol.ui.text.TextAlignProto.TEXT_ALIGN_JUSTIFY -> TextAlign.Justify
        com.podca.sdui.protocol.ui.text.TextAlignProto.TEXT_ALIGN_START -> TextAlign.Start
        com.podca.sdui.protocol.ui.text.TextAlignProto.TEXT_ALIGN_END -> TextAlign.End
        null, com.podca.sdui.protocol.ui.text.TextAlignProto.TEXT_ALIGN_UNSPECIFIED -> null
    }

internal fun TextDecorationProto?.toComposeTextDecorationOrNull(): TextDecoration? {
    val mask = this?.mask ?: return null
    val decorations = buildList {
        if (mask and 0x1u.toInt() != 0) add(TextDecoration.Underline)
        if (mask and 0x2u.toInt() != 0) add(TextDecoration.LineThrough)
    }
    return when (decorations.size) {
        0 -> null
        1 -> decorations.first()
        else -> TextDecoration.combine(decorations)
    }
}

internal fun ParagraphStyleProto?.toComposeTextAlignOrNull(): TextAlign? =
    this?.text_align.toComposeTextAlignOrNull()

internal fun TextStyleProto?.toComposeTextStyle(): TextStyle {
    val span = this?.span_style
    val paragraph = this?.paragraph_style
    return TextStyle(
        fontSize = span?.font_size.toComposeTextUnit(),
        fontStyle = span?.font_style.toComposeFontStyleOrNull(),
        fontWeight = span?.font_weight.toComposeFontWeightOrNull(),
        fontFamily = span?.font_family.toComposeFontFamilyOrNull(),
        letterSpacing = span?.letter_spacing.toComposeTextUnit(),
        textDecoration = span?.text_decoration.toComposeTextDecorationOrNull(),
        textAlign = paragraph?.text_align.toComposeTextAlignOrNull() ?: TextAlign.Start,
        lineHeight = paragraph?.line_height.toComposeTextUnit(),
    )
}

internal fun MaterialTextProto.toComposeTextStyle(): TextStyle {
    val base = style.toComposeTextStyle()
    return base.copy(
        color = color.toComposeColorOrNull() ?: base.color,
        fontSize = font_size.toComposeTextUnit(default = base.fontSize),
        fontStyle = font_style.toComposeFontStyleOrNull() ?: base.fontStyle,
        fontWeight = font_weight.toComposeFontWeightOrNull() ?: base.fontWeight,
        fontFamily = font_family.toComposeFontFamilyOrNull() ?: base.fontFamily,
        letterSpacing = letter_spacing.toComposeTextUnit(default = base.letterSpacing),
        textDecoration = text_decoration.toComposeTextDecorationOrNull() ?: base.textDecoration,
        textAlign = text_align.toComposeTextAlignOrNull() ?: base.textAlign,
        lineHeight = line_height.toComposeTextUnit(default = base.lineHeight),
    )
}

@Composable
internal fun com.podca.sdui.protocol.material3.ButtonColorsProto?.toComposeButtonColors() = ButtonDefaults.buttonColors(
    containerColor = this?.container_color.toComposeColorOrNull() ?: Color.Unspecified,
    contentColor = this?.content_color.toComposeColorOrNull() ?: Color.Unspecified,
    disabledContainerColor = this?.disabled_container_color.toComposeColorOrNull() ?: Color.Unspecified,
    disabledContentColor = this?.disabled_content_color.toComposeColorOrNull() ?: Color.Unspecified,
)

@Composable
internal fun ButtonElevationProto?.toComposeButtonElevation() = ButtonDefaults.buttonElevation(
    defaultElevation = this?.default_elevation.toComposeDp(default = 0.dp),
    pressedElevation = this?.pressed_elevation.toComposeDp(default = 0.dp),
    focusedElevation = this?.focused_elevation.toComposeDp(default = 0.dp),
    hoveredElevation = this?.hovered_elevation.toComposeDp(default = 0.dp),
    disabledElevation = this?.disabled_elevation.toComposeDp(default = 0.dp),
)

@Composable
internal fun com.podca.sdui.protocol.material3.CardColorsProto?.toComposeCardColors() = CardDefaults.cardColors(
    containerColor = this?.container_color.toComposeColorOrNull() ?: Color.Unspecified,
    contentColor = this?.content_color.toComposeColorOrNull() ?: Color.Unspecified,
    disabledContainerColor = this?.disabled_container_color.toComposeColorOrNull() ?: Color.Unspecified,
    disabledContentColor = this?.disabled_content_color.toComposeColorOrNull() ?: Color.Unspecified,
)

@Composable
internal fun CardElevationProto?.toComposeCardElevation() = CardDefaults.cardElevation(
    defaultElevation = this?.default_elevation.toComposeDp(default = 0.dp),
    pressedElevation = this?.pressed_elevation.toComposeDp(default = 0.dp),
    focusedElevation = this?.focused_elevation.toComposeDp(default = 0.dp),
    hoveredElevation = this?.hovered_elevation.toComposeDp(default = 0.dp),
    draggedElevation = this?.dragged_elevation.toComposeDp(default = 0.dp),
    disabledElevation = this?.disabled_elevation.toComposeDp(default = 0.dp),
)

internal fun StrokeCapProto?.toComposeStrokeCapOrNull(): StrokeCap? =
    when (this) {
        StrokeCapProto.STROKE_CAP_BUTT -> StrokeCap.Butt
        StrokeCapProto.STROKE_CAP_ROUND -> StrokeCap.Round
        StrokeCapProto.STROKE_CAP_SQUARE -> StrokeCap.Square
        null -> null
    }
