package com.podca.sdui.studio.ui.core

import com.podca.sdui.protocol.ui.graphics.ColorProto
import com.podca.sdui.protocol.ui.geometry.SizeProto
import com.podca.sdui.protocol.ui.text.AnnotatedStringProto
import com.podca.sdui.protocol.ui.text.MultiParagraphIntrinsicsProto
import com.podca.sdui.protocol.ui.text.MultiParagraphProto
import com.podca.sdui.protocol.ui.text.ParagraphIntrinsicsProto
import com.podca.sdui.protocol.ui.text.TextLayoutInputProto
import com.podca.sdui.protocol.ui.text.TextLayoutResultProto
import com.podca.sdui.protocol.ui.text.TextMeasurerProto
import com.podca.sdui.protocol.ui.text.TextPainterProto
import com.podca.sdui.protocol.ui.text.TextSelectionColorsProto
import com.podca.sdui.protocol.ui.text.TextOverflowProto
import com.podca.sdui.protocol.ui.text.TextGranularityProto
import com.podca.sdui.protocol.ui.text.TextInclusionStrategyProto
import com.podca.sdui.protocol.ui.text.font.DelegatingFontLoaderForDeprecatedUsageProto
import com.podca.sdui.protocol.ui.text.font.FontFamilyResolverProto
import com.podca.sdui.protocol.ui.text.font.FontFamilyTypefaceAdapterProto
import com.podca.sdui.protocol.ui.text.font.FontListFontFamilyTypefaceAdapterProto
import com.podca.sdui.protocol.ui.text.font.FontLoadingStrategyProto
import com.podca.sdui.protocol.ui.text.font.FontMatcherProto
import com.podca.sdui.protocol.ui.text.font.FontProto
import com.podca.sdui.protocol.ui.text.font.FontStyleProto
import com.podca.sdui.protocol.ui.text.font.FontSynthesisProto
import com.podca.sdui.protocol.ui.text.font.FontVariationSettingProto
import com.podca.sdui.protocol.ui.text.font.FontVariationSettingsProto
import com.podca.sdui.protocol.ui.text.font.TypefaceProto
import com.podca.sdui.protocol.ui.unit.FontScalingProto
import com.podca.sdui.protocol.ui.unit.TextUnitProto
import com.podca.sdui.protocol.ui.unit.VelocityProto

public fun PodcaTextOverflow(
    textOverflow: TextOverflowProto = TextOverflowProto.TEXT_OVERFLOW_UNSPECIFIED,
): TextOverflowProto = textOverflow

public fun PodcaTextGranularity(
    textGranularity: TextGranularityProto = TextGranularityProto.TEXT_GRANULARITY_CHARACTER,
): TextGranularityProto = textGranularity

public fun PodcaTextInclusionStrategy(
    textInclusionStrategy: TextInclusionStrategyProto =
        TextInclusionStrategyProto.TEXT_INCLUSION_STRATEGY_CONTAINS_ALL,
): TextInclusionStrategyProto = textInclusionStrategy

public fun PodcaFontStyle(
    fontStyle: FontStyleProto = FontStyleProto.FONT_STYLE_NORMAL,
): FontStyleProto = fontStyle

public fun PodcaFontSynthesis(
    fontSynthesis: FontSynthesisProto = FontSynthesisProto.FONT_SYNTHESIS_NONE,
): FontSynthesisProto = fontSynthesis

public fun PodcaFontLoadingStrategy(
    fontLoadingStrategy: FontLoadingStrategyProto =
        FontLoadingStrategyProto.FONT_LOADING_STRATEGY_BLOCKING,
): FontLoadingStrategyProto = fontLoadingStrategy

public fun PodcaFontVariationSettingFloat(
    axisName: String,
    floatValue: Float,
): FontVariationSettingProto =
    FontVariationSettingProto(
        axis_name = axisName,
        float_value = floatValue,
    )

public fun PodcaFontVariationSettingInt(
    axisName: String,
    intValue: Int,
): FontVariationSettingProto =
    FontVariationSettingProto(
        axis_name = axisName,
        int_value = intValue,
    )

public fun PodcaFontVariationSettingTextUnit(
    axisName: String,
    textUnitValue: TextUnitProto? = null,
): FontVariationSettingProto =
    FontVariationSettingProto(
        axis_name = axisName,
        text_unit_value = textUnitValue,
    )

public fun PodcaFontVariationSettings(
    settings: List<FontVariationSettingProto> = emptyList(),
): FontVariationSettingsProto =
    FontVariationSettingsProto(
        settings = settings,
    )

public fun PodcaFont(
    resId: Int = 0,
    weight: com.podca.sdui.protocol.ui.text.font.FontWeightProto? = null,
    style: FontStyleProto = FontStyleProto.FONT_STYLE_NORMAL,
    loadingStrategy: FontLoadingStrategyProto =
        FontLoadingStrategyProto.FONT_LOADING_STRATEGY_BLOCKING,
    variationSettings: FontVariationSettingsProto? = null,
): FontProto =
    FontProto(
        res_id = resId,
        weight = weight,
        style = style,
        loading_strategy = loadingStrategy,
        variation_settings = variationSettings,
    )

public fun PodcaTypeface(
    typefaceId: String,
): TypefaceProto =
    TypefaceProto(
        typeface_id = typefaceId,
    )

public fun PodcaFontMatcher(
    matchesWeight: Boolean = false,
    matchesStyle: Boolean = false,
    matchesSynthesis: Boolean = false,
): FontMatcherProto =
    FontMatcherProto(
        matches_weight = matchesWeight,
        matches_style = matchesStyle,
        matches_synthesis = matchesSynthesis,
    )

public fun PodcaFontFamilyResolver(
    supportsAsyncLoading: Boolean = false,
    cachesTypefaces: Boolean = false,
): FontFamilyResolverProto =
    FontFamilyResolverProto(
        supports_async_loading = supportsAsyncLoading,
        caches_typefaces = cachesTypefaces,
    )

public fun PodcaFontFamilyTypefaceAdapter(
    supportsFontSynthesis: Boolean = false,
    supportsFontVariation: Boolean = false,
): FontFamilyTypefaceAdapterProto =
    FontFamilyTypefaceAdapterProto(
        supports_font_synthesis = supportsFontSynthesis,
        supports_font_variation = supportsFontVariation,
    )

public fun PodcaFontListFontFamilyTypefaceAdapter(
    resolvesFromFontList: Boolean = false,
    supportsBlockingFallback: Boolean = false,
): FontListFontFamilyTypefaceAdapterProto =
    FontListFontFamilyTypefaceAdapterProto(
        resolves_from_font_list = resolvesFromFontList,
        supports_blocking_fallback = supportsBlockingFallback,
    )

public fun PodcaDelegatingFontLoaderForDeprecatedUsage(
    delegatesToPlatformLoader: Boolean = false,
    preservesLegacyBehavior: Boolean = false,
): DelegatingFontLoaderForDeprecatedUsageProto =
    DelegatingFontLoaderForDeprecatedUsageProto(
        delegates_to_platform_loader = delegatesToPlatformLoader,
        preserves_legacy_behavior = preservesLegacyBehavior,
    )

public fun PodcaFontScaling(
    fontScale: Float,
): FontScalingProto =
    FontScalingProto(
        font_scale = fontScale,
    )

public fun PodcaVelocity(
    x: Float,
    y: Float,
): VelocityProto =
    VelocityProto(
        x = x,
        y = y,
    )

public fun PodcaTextSelectionColors(
    handleColor: ColorProto? = null,
    backgroundColor: ColorProto? = null,
): TextSelectionColorsProto =
    TextSelectionColorsProto(
        handle_color = handleColor,
        background_color = backgroundColor,
    )

public fun PodcaTextMeasurer(
    cacheSize: Int = 0,
): TextMeasurerProto =
    TextMeasurerProto(
        cache_size = cacheSize,
    )

public fun PodcaMultiParagraph(
    width: Float = 0f,
    height: Float = 0f,
    lineCount: Int = 0,
    didExceedMaxLines: Boolean = false,
): MultiParagraphProto =
    MultiParagraphProto(
        width = width,
        height = height,
        line_count = lineCount,
        did_exceed_max_lines = didExceedMaxLines,
    )

public fun PodcaMultiParagraphIntrinsics(
    minIntrinsicWidth: Float = 0f,
    maxIntrinsicWidth: Float = 0f,
    hasStaleResolvedFonts: Boolean = false,
): MultiParagraphIntrinsicsProto =
    MultiParagraphIntrinsicsProto(
        min_intrinsic_width = minIntrinsicWidth,
        max_intrinsic_width = maxIntrinsicWidth,
        has_stale_resolved_fonts = hasStaleResolvedFonts,
    )

public fun PodcaParagraphIntrinsics(
    hasStaleResolvedFonts: Boolean = false,
    minIntrinsicWidth: Float = 0f,
    maxIntrinsicWidth: Float = 0f,
): ParagraphIntrinsicsProto =
    ParagraphIntrinsicsProto(
        has_stale_resolved_fonts = hasStaleResolvedFonts,
        min_intrinsic_width = minIntrinsicWidth,
        max_intrinsic_width = maxIntrinsicWidth,
    )

public fun PodcaTextLayoutInputText(
    text: String,
    style: com.podca.sdui.protocol.ui.text.TextStyleProto? = null,
    maxLines: Int = 0,
    softWrap: Boolean = true,
    overflow: TextOverflowProto = TextOverflowProto.TEXT_OVERFLOW_UNSPECIFIED,
): TextLayoutInputProto =
    TextLayoutInputProto(
        text = text,
        style = style,
        max_lines = maxLines,
        soft_wrap = softWrap,
        overflow = overflow,
    )

public fun PodcaTextLayoutInputAnnotatedString(
    annotatedString: AnnotatedStringProto? = null,
    style: com.podca.sdui.protocol.ui.text.TextStyleProto? = null,
    maxLines: Int = 0,
    softWrap: Boolean = true,
    overflow: TextOverflowProto = TextOverflowProto.TEXT_OVERFLOW_UNSPECIFIED,
): TextLayoutInputProto =
    TextLayoutInputProto(
        annotated_string = annotatedString,
        style = style,
        max_lines = maxLines,
        soft_wrap = softWrap,
        overflow = overflow,
    )

public fun PodcaTextLayoutResult(
    layoutInput: TextLayoutInputProto? = null,
    multiParagraph: MultiParagraphProto? = null,
    size: SizeProto? = null,
    firstBaseline: Float = 0f,
    lastBaseline: Float = 0f,
    didOverflowHeight: Boolean = false,
    didOverflowWidth: Boolean = false,
    hasVisualOverflow: Boolean = false,
): TextLayoutResultProto =
    TextLayoutResultProto(
        layout_input = layoutInput,
        multi_paragraph = multiParagraph,
        size = size,
        first_baseline = firstBaseline,
        last_baseline = lastBaseline,
        did_overflow_height = didOverflowHeight,
        did_overflow_width = didOverflowWidth,
        has_visual_overflow = hasVisualOverflow,
    )
