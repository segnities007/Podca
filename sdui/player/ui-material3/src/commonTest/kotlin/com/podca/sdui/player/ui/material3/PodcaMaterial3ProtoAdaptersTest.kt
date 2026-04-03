package com.podca.sdui.player.ui.material3

import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.CutCornerShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.layout.calculateEndPadding
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp
import com.podca.sdui.protocol.foundation.layout.PaddingValuesProto
import com.podca.sdui.protocol.material3.MaterialTextProto
import com.podca.sdui.protocol.ui.graphics.ColorComponentsProto
import com.podca.sdui.protocol.ui.graphics.ColorProto
import com.podca.sdui.protocol.ui.graphics.ShapeProto
import com.podca.sdui.protocol.ui.graphics.StrokeCapProto
import com.podca.sdui.protocol.ui.text.ParagraphStyleProto
import com.podca.sdui.protocol.ui.text.SpanStyleProto
import com.podca.sdui.protocol.ui.text.TextDecorationProto
import com.podca.sdui.protocol.ui.text.TextOverflowProto
import com.podca.sdui.protocol.ui.text.TextStyleProto
import com.podca.sdui.protocol.ui.text.font.FontFamilyDefaultProto
import com.podca.sdui.protocol.ui.text.font.FontFamilyGenericProto
import com.podca.sdui.protocol.ui.text.font.FontFamilyListProto
import com.podca.sdui.protocol.ui.text.font.FontFamilyProto
import com.podca.sdui.protocol.ui.text.font.FontFamilySansSerifProto
import com.podca.sdui.protocol.ui.text.font.FontStyleProto
import com.podca.sdui.protocol.ui.text.font.FontWeightProto
import com.podca.sdui.protocol.ui.unit.DpProto
import com.podca.sdui.protocol.ui.unit.TextUnitProto
import com.podca.sdui.protocol.ui.unit.TextUnitTypeProto
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertNull

class PodcaMaterial3ProtoAdaptersTest {
    @Test
    fun dp_proto_uses_value_or_default() {
        val proto: DpProto? = DpProto(value_ = 12.5f)
        val missing: DpProto? = null

        assertEquals(12.5.dp, proto.toComposeDp())
        assertEquals(4.dp, missing.toComposeDp(default = 4.dp))
    }

    @Test
    fun text_unit_proto_maps_sp_em_and_unspecified() {
        assertEquals(16.sp, TextUnitProto(value_ = 16f, type = TextUnitTypeProto.TEXT_UNIT_TYPE_SP).toComposeTextUnit())
        assertEquals(1.25.em, TextUnitProto(value_ = 1.25f, type = TextUnitTypeProto.TEXT_UNIT_TYPE_EM).toComposeTextUnit())
        assertEquals(TextUnit.Unspecified, TextUnitProto(value_ = 99f, type = TextUnitTypeProto.TEXT_UNIT_TYPE_UNSPECIFIED).toComposeTextUnit())
        assertEquals(3.sp, null.toComposeTextUnit(default = 3.sp))
    }

    @Test
    fun color_proto_maps_argb_packed_and_components() {
        val argb = ColorProto(argb = 0xFF336699.toInt())
        val packed = ColorProto(packed = 0xFF11223344556677uL.toLong())
        val components = ColorProto(
            components = ColorComponentsProto(
                red = 0.25f,
                green = 0.5f,
                blue = 0.75f,
                alpha = 0.8f,
            ),
        )

        assertEquals(Color(0xFF336699), argb.toComposeColorOrNull())
        assertEquals(Color(0xFF11223344556677uL.toLong()), packed.toComposeColorOrNull())
        assertEquals(Color(red = 0.25f, green = 0.5f, blue = 0.75f, alpha = 0.8f), components.toComposeColorOrNull())
        assertNull(null.toComposeColorOrNull())
    }

    @Test
    fun shape_proto_maps_rectangle_circle_and_corner_shapes() {
        assertEquals(RoundedCornerShape(0.dp), ShapeProto(rectangle = true).toComposeShapeOrNull())
        assertEquals(CircleShape, ShapeProto(circle = true).toComposeShapeOrNull())
        assertEquals(RoundedCornerShape(8.dp), ShapeProto(rounded_corner = DpProto(value_ = 8f)).toComposeShapeOrNull())
        assertEquals(CutCornerShape(6.dp), ShapeProto(cut_corner = DpProto(value_ = 6f)).toComposeShapeOrNull())
        assertEquals(
            RoundedCornerShape(
                topStart = 1.dp,
                topEnd = 2.dp,
                bottomEnd = 3.dp,
                bottomStart = 4.dp,
            ),
            ShapeProto(
                custom_rounded_corner = com.podca.sdui.protocol.ui.graphics.GenericRoundedCornerShapeProto(
                    top_start = DpProto(value_ = 1f),
                    top_end = DpProto(value_ = 2f),
                    bottom_end = DpProto(value_ = 3f),
                    bottom_start = DpProto(value_ = 4f),
                ),
            ).toComposeShapeOrNull(),
        )
        assertNull(null.toComposeShapeOrNull())
    }

    @Test
    fun text_decoration_proto_maps_mask_bits() {
        assertNull(TextDecorationProto(mask = 0).toComposeTextDecorationOrNull())
        assertEquals(TextDecoration.Underline, TextDecorationProto(mask = 0x1).toComposeTextDecorationOrNull())
        assertEquals(TextDecoration.LineThrough, TextDecorationProto(mask = 0x2).toComposeTextDecorationOrNull())
        assertEquals(
            TextDecoration.combine(listOf(TextDecoration.Underline, TextDecoration.LineThrough)),
            TextDecorationProto(mask = 0x3).toComposeTextDecorationOrNull(),
        )
    }

    @Test
    fun text_overflow_and_stroke_cap_map_known_values() {
        assertEquals(TextOverflow.Clip, TextOverflowProto.TEXT_OVERFLOW_CLIP.toComposeTextOverflowOrNull())
        assertEquals(TextOverflow.Ellipsis, TextOverflowProto.TEXT_OVERFLOW_ELLIPSIS.toComposeTextOverflowOrNull())
        assertEquals(TextOverflow.Visible, TextOverflowProto.TEXT_OVERFLOW_VISIBLE.toComposeTextOverflowOrNull())
        assertEquals(TextOverflow.StartEllipsis, TextOverflowProto.TEXT_OVERFLOW_START_ELLIPSIS.toComposeTextOverflowOrNull())
        assertEquals(TextOverflow.MiddleEllipsis, TextOverflowProto.TEXT_OVERFLOW_MIDDLE_ELLIPSIS.toComposeTextOverflowOrNull())
        assertNull(TextOverflowProto.TEXT_OVERFLOW_UNSPECIFIED.toComposeTextOverflowOrNull())
        assertNull(null.toComposeStrokeCapOrNull())
        assertEquals(StrokeCap.Butt, StrokeCapProto.STROKE_CAP_BUTT.toComposeStrokeCapOrNull())
        assertEquals(StrokeCap.Round, StrokeCapProto.STROKE_CAP_ROUND.toComposeStrokeCapOrNull())
        assertEquals(StrokeCap.Square, StrokeCapProto.STROKE_CAP_SQUARE.toComposeStrokeCapOrNull())
    }

    @Test
    fun font_family_weight_and_style_map_supported_values() {
        assertEquals(FontFamily.Default, FontFamilyProto(default_family = FontFamilyDefaultProto()).toComposeFontFamilyOrNull())
        assertEquals(FontFamily.Default, FontFamilyProto(generic_family = FontFamilyGenericProto(name = "serif-like")).toComposeFontFamilyOrNull())
        assertEquals(FontFamily.SansSerif, FontFamilyProto(sans_serif_family = FontFamilySansSerifProto(name = "sans", debug_name = "sans-debug")).toComposeFontFamilyOrNull())
        assertEquals(FontFamily.Default, FontFamilyProto(list_family = FontFamilyListProto()).toComposeFontFamilyOrNull())
        assertEquals(FontWeight(1), FontWeightProto(weight = 0).toComposeFontWeightOrNull())
        assertEquals(FontWeight(1000), FontWeightProto(weight = 1200).toComposeFontWeightOrNull())
        assertEquals(FontStyle.Italic, FontStyleProto.FONT_STYLE_ITALIC.toComposeFontStyleOrNull())
        assertEquals(FontStyle.Normal, FontStyleProto.FONT_STYLE_NORMAL.toComposeFontStyleOrNull())
        assertNull(null.toComposeFontStyleOrNull())
    }

    @Test
    fun text_style_merges_base_style_and_material_overrides() {
        val base = TextStyleProto(
            span_style = SpanStyleProto(
                font_size = TextUnitProto(value_ = 12f, type = TextUnitTypeProto.TEXT_UNIT_TYPE_SP),
                font_weight = FontWeightProto(weight = 300),
                font_style = FontStyleProto.FONT_STYLE_ITALIC,
                font_family = FontFamilyProto(default_family = FontFamilyDefaultProto()),
                letter_spacing = TextUnitProto(value_ = 0.5f, type = TextUnitTypeProto.TEXT_UNIT_TYPE_EM),
                text_decoration = TextDecorationProto(mask = 0x1),
            ),
            paragraph_style = ParagraphStyleProto(
                text_align = com.podca.sdui.protocol.ui.text.TextAlignProto.TEXT_ALIGN_CENTER,
                line_height = TextUnitProto(value_ = 14f, type = TextUnitTypeProto.TEXT_UNIT_TYPE_SP),
            ),
        )
        val style = MaterialTextProto(
            text = "Episode title",
            style = base,
            color = ColorProto(argb = 0xFF445566.toInt()),
            font_size = TextUnitProto(value_ = 18f, type = TextUnitTypeProto.TEXT_UNIT_TYPE_SP),
            font_style = FontStyleProto.FONT_STYLE_NORMAL,
            font_weight = FontWeightProto(weight = 700),
            font_family = FontFamilyProto(sans_serif_family = FontFamilySansSerifProto()),
            letter_spacing = TextUnitProto(value_ = 1.5f, type = TextUnitTypeProto.TEXT_UNIT_TYPE_EM),
            text_decoration = TextDecorationProto(mask = 0x2),
            text_align = com.podca.sdui.protocol.ui.text.TextAlignProto.TEXT_ALIGN_END,
            line_height = TextUnitProto(value_ = 20f, type = TextUnitTypeProto.TEXT_UNIT_TYPE_SP),
        ).toComposeTextStyle()

        assertEquals(Color(0xFF445566), style.color)
        assertEquals(18.sp, style.fontSize)
        assertEquals(FontStyle.Normal, style.fontStyle)
        assertEquals(FontWeight(700), style.fontWeight)
        assertEquals(FontFamily.SansSerif, style.fontFamily)
        assertEquals(1.5.em, style.letterSpacing)
        assertEquals(TextDecoration.LineThrough, style.textDecoration)
        assertEquals(TextAlign.End, style.textAlign)
        assertEquals(20.sp, style.lineHeight)
    }

    @Test
    fun padding_values_default_missing_edges_to_zero() {
        val proto = PaddingValuesProto(
            start = DpProto(value_ = 4f),
            end = DpProto(value_ = 8f),
        )
        val padding = proto.toComposePaddingValues()

        assertEquals(4.dp, padding.calculateStartPadding(LayoutDirection.Ltr))
        assertEquals(4.dp, padding.calculateStartPadding(LayoutDirection.Rtl))
        assertEquals(8.dp, padding.calculateEndPadding(LayoutDirection.Ltr))
        assertEquals(8.dp, padding.calculateEndPadding(LayoutDirection.Rtl))
        assertEquals(0.dp, padding.calculateTopPadding())
        assertEquals(0.dp, padding.calculateBottomPadding())
    }
}
