package com.podca.sdui.studio.ui.core

import com.podca.sdui.protocol.ui.graphics.BrushProto
import com.podca.sdui.protocol.ui.graphics.ColorProto
import com.podca.sdui.protocol.ui.graphics.ShadowProto
import com.podca.sdui.protocol.ui.graphics.drawscope.DrawStyleProto
import com.podca.sdui.protocol.ui.text.AnnotatedStringProto
import com.podca.sdui.protocol.ui.text.AnnotationRangeProto
import com.podca.sdui.protocol.ui.text.BaselineShiftProto
import com.podca.sdui.protocol.ui.text.HyphensProto
import com.podca.sdui.protocol.ui.text.LineBreakProto
import com.podca.sdui.protocol.ui.text.LineHeightStyleModeProto
import com.podca.sdui.protocol.ui.text.LineHeightStyleProto
import com.podca.sdui.protocol.ui.text.LinkAnnotationClickableProto
import com.podca.sdui.protocol.ui.text.LinkAnnotationProto
import com.podca.sdui.protocol.ui.text.LinkAnnotationUrlProto
import com.podca.sdui.protocol.ui.text.ParagraphStyleProto
import com.podca.sdui.protocol.ui.text.PlaceholderProto
import com.podca.sdui.protocol.ui.text.PlaceholderVerticalAlignProto
import com.podca.sdui.protocol.ui.text.PlatformParagraphStyleProto
import com.podca.sdui.protocol.ui.text.PlatformSpanStyleProto
import com.podca.sdui.protocol.ui.text.PlatformTextStyleProto
import com.podca.sdui.protocol.ui.text.SpanStyleProto
import com.podca.sdui.protocol.ui.text.StringAnnotationProto
import com.podca.sdui.protocol.ui.text.TextAlignProto
import com.podca.sdui.protocol.ui.text.TextDecorationProto
import com.podca.sdui.protocol.ui.text.TextDirectionProto
import com.podca.sdui.protocol.ui.text.TextGeometricTransformProto
import com.podca.sdui.protocol.ui.text.TextIndentProto
import com.podca.sdui.protocol.ui.text.TextLinkStylesProto
import com.podca.sdui.protocol.ui.text.TextMotionProto
import com.podca.sdui.protocol.ui.text.TextRangeProto
import com.podca.sdui.protocol.ui.text.TextStyleProto
import com.podca.sdui.protocol.ui.text.UrlAnnotationProto
import com.podca.sdui.protocol.ui.text.VerbatimTtsAnnotationProto
import com.podca.sdui.protocol.ui.text.font.FontFamilyCursiveProto
import com.podca.sdui.protocol.ui.text.font.FontFamilyDefaultProto
import com.podca.sdui.protocol.ui.text.font.FontFamilyGenericProto
import com.podca.sdui.protocol.ui.text.font.FontFamilyListProto
import com.podca.sdui.protocol.ui.text.font.FontFamilyMonospaceProto
import com.podca.sdui.protocol.ui.text.font.FontFamilyProto
import com.podca.sdui.protocol.ui.text.font.FontFamilySansSerifProto
import com.podca.sdui.protocol.ui.text.font.FontFamilySerifProto
import com.podca.sdui.protocol.ui.text.font.FontProto
import com.podca.sdui.protocol.ui.text.font.FontStyleProto
import com.podca.sdui.protocol.ui.text.font.FontSynthesisProto
import com.podca.sdui.protocol.ui.text.font.FontWeightProto
import com.podca.sdui.protocol.ui.text.font.LoadedFontFamilyProto
import com.podca.sdui.protocol.ui.text.font.TypefaceProto
import com.podca.sdui.protocol.ui.text.intl.LocaleListProto
import com.podca.sdui.protocol.ui.text.intl.LocaleProto
import com.podca.sdui.protocol.ui.unit.TextUnitProto

public fun PodcaTextRange(
    start: Int,
    end: Int,
): TextRangeProto =
    TextRangeProto(
        start = start,
        end = end,
    )

public fun PodcaStringAnnotation(
    tag: String,
    value: String,
): StringAnnotationProto =
    StringAnnotationProto(
        tag = tag,
        value_ = value,
    )

public fun PodcaVerbatimTtsAnnotation(
    verbatim: String,
): VerbatimTtsAnnotationProto =
    VerbatimTtsAnnotationProto(
        verbatim = verbatim,
    )

public fun PodcaUrlAnnotation(
    url: String,
): UrlAnnotationProto =
    UrlAnnotationProto(
        url = url,
    )

public fun PodcaTextDecoration(
    mask: UInt,
): TextDecorationProto =
    TextDecorationProto(
        mask = mask.toInt(),
    )

public fun PodcaBaselineShift(
    multiplier: Float,
): BaselineShiftProto =
    BaselineShiftProto(
        multiplier = multiplier,
    )

public fun PodcaTextGeometricTransform(
    scaleX: Float = 1f,
    skewX: Float = 0f,
): TextGeometricTransformProto =
    TextGeometricTransformProto(
        scale_x = scaleX,
        skew_x = skewX,
    )

public fun PodcaFontWeight(
    weight: Int,
): FontWeightProto =
    FontWeightProto(
        weight = weight,
    )

public fun PodcaDefaultFontFamily(): FontFamilyProto =
    FontFamilyProto(
        default_family = FontFamilyDefaultProto(),
    )

public fun PodcaGenericFontFamily(
    name: String,
): FontFamilyProto =
    FontFamilyProto(
        generic_family = FontFamilyGenericProto(
            name = name,
        ),
    )

public fun PodcaSansSerifFontFamily(
    name: String = "",
    debugName: String = "",
): FontFamilyProto =
    FontFamilyProto(
        sans_serif_family = FontFamilySansSerifProto(
            name = name,
            debug_name = debugName,
        ),
    )

public fun PodcaSerifFontFamily(
    name: String = "",
    debugName: String = "",
): FontFamilyProto =
    FontFamilyProto(
        serif_family = FontFamilySerifProto(
            name = name,
            debug_name = debugName,
        ),
    )

public fun PodcaMonospaceFontFamily(
    name: String = "",
    debugName: String = "",
): FontFamilyProto =
    FontFamilyProto(
        monospace_family = FontFamilyMonospaceProto(
            name = name,
            debug_name = debugName,
        ),
    )

public fun PodcaCursiveFontFamily(
    name: String = "",
    debugName: String = "",
): FontFamilyProto =
    FontFamilyProto(
        cursive_family = FontFamilyCursiveProto(
            name = name,
            debug_name = debugName,
        ),
    )

public fun PodcaFontListFamily(
    fonts: List<FontProto> = emptyList(),
): FontFamilyProto =
    FontFamilyProto(
        list_family = FontFamilyListProto(
            fonts = fonts,
        ),
    )

public fun PodcaLoadedFontFamily(
    typeface: TypefaceProto? = null,
): FontFamilyProto =
    FontFamilyProto(
        loaded_family = LoadedFontFamilyProto(
            typeface = typeface,
        ),
    )

public fun PodcaLocale(
    languageTag: String,
): LocaleProto =
    LocaleProto(
        language_tag = languageTag,
    )

public fun PodcaLocaleList(
    locales: List<LocaleProto> = emptyList(),
): LocaleListProto =
    LocaleListProto(
        locales = locales,
    )

public fun PodcaPlatformSpanStyle(
    emojiSupportMatch: Boolean = false,
    isDefault: Boolean = false,
    supportsMerge: Boolean = false,
): PlatformSpanStyleProto =
    PlatformSpanStyleProto(
        emoji_support_match = emojiSupportMatch,
        is_default = isDefault,
        supports_merge = supportsMerge,
    )

public fun PodcaPlatformParagraphStyle(
    includeFontPadding: Boolean = false,
    isDefault: Boolean = false,
    supportsMerge: Boolean = false,
): PlatformParagraphStyleProto =
    PlatformParagraphStyleProto(
        include_font_padding = includeFontPadding,
        is_default = isDefault,
        supports_merge = supportsMerge,
    )

public fun PodcaPlatformTextStyle(
    spanStyle: PlatformSpanStyleProto? = null,
    paragraphStyle: PlatformParagraphStyleProto? = null,
    supportsSpanStyleMerge: Boolean = false,
    supportsParagraphStyleMerge: Boolean = false,
    supportsLerp: Boolean = false,
    supportsCreatePlatformTextStyle: Boolean = false,
): PlatformTextStyleProto =
    PlatformTextStyleProto(
        span_style = spanStyle,
        paragraph_style = paragraphStyle,
        supports_span_style_merge = supportsSpanStyleMerge,
        supports_paragraph_style_merge = supportsParagraphStyleMerge,
        supports_lerp = supportsLerp,
        supports_create_platform_text_style = supportsCreatePlatformTextStyle,
    )

public fun PodcaSpanStyle(
    color: ColorProto? = null,
    brush: BrushProto? = null,
    alpha: Float = 1f,
    fontSize: TextUnitProto? = null,
    fontWeight: FontWeightProto? = null,
    fontStyle: FontStyleProto = FontStyleProto.FONT_STYLE_NORMAL,
    fontSynthesis: FontSynthesisProto = FontSynthesisProto.FONT_SYNTHESIS_NONE,
    fontFamily: FontFamilyProto? = null,
    fontFeatureSettings: String = "",
    letterSpacing: TextUnitProto? = null,
    baselineShift: BaselineShiftProto? = null,
    textGeometricTransform: TextGeometricTransformProto? = null,
    localeList: LocaleListProto? = null,
    background: ColorProto? = null,
    textDecoration: TextDecorationProto? = null,
    shadow: ShadowProto? = null,
    platformStyle: PlatformSpanStyleProto? = null,
    drawStyle: DrawStyleProto? = null,
): SpanStyleProto =
    SpanStyleProto(
        color = color,
        brush = brush,
        alpha = alpha,
        font_size = fontSize,
        font_weight = fontWeight,
        font_style = fontStyle,
        font_synthesis = fontSynthesis,
        font_family = fontFamily,
        font_feature_settings = fontFeatureSettings,
        letter_spacing = letterSpacing,
        baseline_shift = baselineShift,
        text_geometric_transform = textGeometricTransform,
        locale_list = localeList,
        background = background,
        text_decoration = textDecoration,
        shadow = shadow,
        platform_style = platformStyle,
        draw_style = drawStyle,
    )

public fun PodcaTextIndent(
    firstLine: TextUnitProto? = null,
    restLine: TextUnitProto? = null,
): TextIndentProto =
    TextIndentProto(
        first_line = firstLine,
        rest_line = restLine,
    )

public fun PodcaLineHeightStyle(
    topRatio: Float = 0f,
    trimMask: UInt = 0u,
    mode: LineHeightStyleModeProto = LineHeightStyleModeProto.LINE_HEIGHT_STYLE_MODE_FIXED,
): LineHeightStyleProto =
    LineHeightStyleProto(
        top_ratio = topRatio,
        trim_mask = trimMask.toInt(),
        mode = mode,
    )

public fun PodcaParagraphStyle(
    textAlign: TextAlignProto = TextAlignProto.TEXT_ALIGN_UNSPECIFIED,
    textDirection: TextDirectionProto = TextDirectionProto.TEXT_DIRECTION_UNSPECIFIED,
    lineHeight: TextUnitProto? = null,
    textIndent: TextIndentProto? = null,
    platformStyle: PlatformParagraphStyleProto? = null,
    lineHeightStyle: LineHeightStyleProto? = null,
    lineBreak: LineBreakProto = LineBreakProto.LINE_BREAK_UNSPECIFIED,
    hyphens: HyphensProto = HyphensProto.HYPHENS_UNSPECIFIED,
    textMotion: TextMotionProto = TextMotionProto.TEXT_MOTION_STATIC,
): ParagraphStyleProto =
    ParagraphStyleProto(
        text_align = textAlign,
        text_direction = textDirection,
        line_height = lineHeight,
        text_indent = textIndent,
        platform_style = platformStyle,
        line_height_style = lineHeightStyle,
        line_break = lineBreak,
        hyphens = hyphens,
        text_motion = textMotion,
    )

public fun PodcaTextStyle(
    spanStyle: SpanStyleProto? = null,
    paragraphStyle: ParagraphStyleProto? = null,
    platformStyle: PlatformTextStyleProto? = null,
): TextStyleProto =
    TextStyleProto(
        span_style = spanStyle,
        paragraph_style = paragraphStyle,
        platform_style = platformStyle,
    )

public fun PodcaTextLinkStyles(
    style: SpanStyleProto? = null,
    focusedStyle: SpanStyleProto? = null,
    hoveredStyle: SpanStyleProto? = null,
    pressedStyle: SpanStyleProto? = null,
): TextLinkStylesProto =
    TextLinkStylesProto(
        style = style,
        focused_style = focusedStyle,
        hovered_style = hoveredStyle,
        pressed_style = pressedStyle,
    )

public fun PodcaLinkAnnotationUrl(
    url: String,
    styles: TextLinkStylesProto? = null,
    interactionTag: String = "",
): LinkAnnotationProto =
    LinkAnnotationProto(
        url = LinkAnnotationUrlProto(
            url = url,
            styles = styles,
            interaction_tag = interactionTag,
        ),
    )

public fun PodcaLinkAnnotationClickable(
    tag: String,
    styles: TextLinkStylesProto? = null,
    interactionTag: String = "",
): LinkAnnotationProto =
    LinkAnnotationProto(
        clickable = LinkAnnotationClickableProto(
            tag = tag,
            styles = styles,
            interaction_tag = interactionTag,
        ),
    )

public fun PodcaPlaceholder(
    width: TextUnitProto? = null,
    height: TextUnitProto? = null,
    placeholderVerticalAlign: PlaceholderVerticalAlignProto =
        PlaceholderVerticalAlignProto.PLACEHOLDER_VERTICAL_ALIGN_UNSPECIFIED,
): PlaceholderProto =
    PlaceholderProto(
        width = width,
        height = height,
        placeholder_vertical_align = placeholderVerticalAlign,
    )

public fun PodcaSpanStyleRange(
    start: Int,
    end: Int,
    tag: String = "",
    spanStyle: SpanStyleProto? = null,
): AnnotationRangeProto =
    AnnotationRangeProto(
        start = start,
        end = end,
        tag = tag,
        span_style = spanStyle,
    )

public fun PodcaParagraphStyleRange(
    start: Int,
    end: Int,
    tag: String = "",
    paragraphStyle: ParagraphStyleProto? = null,
): AnnotationRangeProto =
    AnnotationRangeProto(
        start = start,
        end = end,
        tag = tag,
        paragraph_style = paragraphStyle,
    )

public fun PodcaStringAnnotationRange(
    start: Int,
    end: Int,
    tag: String = "",
    stringAnnotation: StringAnnotationProto? = null,
): AnnotationRangeProto =
    AnnotationRangeProto(
        start = start,
        end = end,
        tag = tag,
        string_annotation = stringAnnotation,
    )

public fun PodcaLinkAnnotationRange(
    start: Int,
    end: Int,
    tag: String = "",
    linkAnnotation: LinkAnnotationProto? = null,
): AnnotationRangeProto =
    AnnotationRangeProto(
        start = start,
        end = end,
        tag = tag,
        link_annotation = linkAnnotation,
    )

public fun PodcaUrlAnnotationRange(
    start: Int,
    end: Int,
    tag: String = "",
    urlAnnotation: UrlAnnotationProto? = null,
): AnnotationRangeProto =
    AnnotationRangeProto(
        start = start,
        end = end,
        tag = tag,
        url_annotation = urlAnnotation,
    )

public fun PodcaVerbatimTtsAnnotationRange(
    start: Int,
    end: Int,
    tag: String = "",
    verbatimTtsAnnotation: VerbatimTtsAnnotationProto? = null,
): AnnotationRangeProto =
    AnnotationRangeProto(
        start = start,
        end = end,
        tag = tag,
        verbatim_tts_annotation = verbatimTtsAnnotation,
    )

public fun PodcaPlaceholderRange(
    start: Int,
    end: Int,
    tag: String = "",
    placeholder: PlaceholderProto? = null,
): AnnotationRangeProto =
    AnnotationRangeProto(
        start = start,
        end = end,
        tag = tag,
        placeholder = placeholder,
    )

public fun PodcaAnnotatedString(
    text: String,
    annotations: List<AnnotationRangeProto> = emptyList(),
): AnnotatedStringProto =
    AnnotatedStringProto(
        text = text,
        annotations = annotations,
    )
