package com.podca.sdui.studio.ui.material3

import com.podca.sdui.protocol.material3.AlertDialogDefaultsProto
import com.podca.sdui.protocol.material3.BadgeDefaultsProto
import com.podca.sdui.protocol.material3.CheckboxColorsProto
import com.podca.sdui.protocol.material3.ChipBorderProto
import com.podca.sdui.protocol.material3.ChipColorsProto
import com.podca.sdui.protocol.material3.ChipElevationProto
import com.podca.sdui.protocol.material3.ColorSchemeProto
import com.podca.sdui.protocol.material3.DatePickerColorsProto
import com.podca.sdui.protocol.material3.DateInputValidatorProto
import com.podca.sdui.protocol.material3.InputIdentifierProto
import com.podca.sdui.protocol.material3.DividerDefaultsProto
import com.podca.sdui.protocol.material3.DisplayModeProto
import com.podca.sdui.protocol.material3.DragHandleColorsProto
import com.podca.sdui.protocol.material3.DragHandleShapesProto
import com.podca.sdui.protocol.material3.DragHandleSizesProto
import com.podca.sdui.protocol.material3.FabPositionProto
import com.podca.sdui.protocol.material3.IconButtonColorsProto
import com.podca.sdui.protocol.material3.IconButtonDefaultsProto
import com.podca.sdui.protocol.material3.IconButtonWidthOptionProto
import com.podca.sdui.protocol.material3.IconToggleButtonColorsProto
import com.podca.sdui.protocol.material3.ListItemColorsProto
import com.podca.sdui.protocol.material3.MenuItemColorsProto
import com.podca.sdui.protocol.material3.MotionSchemeProto
import com.podca.sdui.protocol.material3.NavigationBarItemColorsProto
import com.podca.sdui.protocol.material3.NavigationDrawerItemColorsProto
import com.podca.sdui.protocol.material3.NavigationItemColorsProto
import com.podca.sdui.protocol.material3.NavigationItemIconPositionProto
import com.podca.sdui.protocol.material3.NavigationRailItemColorsProto
import com.podca.sdui.protocol.material3.RadioButtonColorsProto
import com.podca.sdui.protocol.material3.RangeSliderStateProto
import com.podca.sdui.protocol.material3.RichTooltipColorsProto
import com.podca.sdui.protocol.material3.RippleAlphaProto
import com.podca.sdui.protocol.material3.SearchBarColorsProto
import com.podca.sdui.protocol.material3.SearchBarValueProto
import com.podca.sdui.protocol.material3.SegmentedButtonColorsProto
import com.podca.sdui.protocol.material3.SheetStateProto
import com.podca.sdui.protocol.material3.SheetValueProto
import com.podca.sdui.protocol.material3.ShortNavigationBarArrangementProto
import com.podca.sdui.protocol.material3.SnackbarDefaultsProto
import com.podca.sdui.protocol.material3.SnackbarDurationProto
import com.podca.sdui.protocol.material3.SnackbarResultProto
import com.podca.sdui.protocol.material3.SwitchColorsProto
import com.podca.sdui.protocol.material3.TextFieldColorsProto
import com.podca.sdui.protocol.material3.TextFieldStateProto
import com.podca.sdui.protocol.material3.TimeFormatProto
import com.podca.sdui.protocol.material3.TimePickerColorsProto
import com.podca.sdui.protocol.material3.TimePickerLayoutTypeProto
import com.podca.sdui.protocol.material3.TimePickerDisplayModeProto
import com.podca.sdui.protocol.material3.TimePickerSelectionModeProto
import com.podca.sdui.protocol.material3.TonalPaletteProto
import com.podca.sdui.protocol.material3.TypographyProto
import com.podca.sdui.protocol.material3.WideNavigationRailColorsProto
import com.podca.sdui.protocol.material3.WideNavigationRailValueProto
import com.podca.sdui.protocol.material3.ExposedDropdownMenuAnchorTypeProto
import com.podca.sdui.protocol.ui.graphics.ColorProto
import com.podca.sdui.protocol.ui.graphics.ShapeProto
import com.podca.sdui.protocol.ui.graphics.StrokeCapProto
import com.podca.sdui.protocol.ui.unit.DpProto

public fun PodcaColorScheme(
    primary: ColorProto? = null,
    onPrimary: ColorProto? = null,
    primaryContainer: ColorProto? = null,
    onPrimaryContainer: ColorProto? = null,
    inversePrimary: ColorProto? = null,
    secondary: ColorProto? = null,
    onSecondary: ColorProto? = null,
    secondaryContainer: ColorProto? = null,
    onSecondaryContainer: ColorProto? = null,
    tertiary: ColorProto? = null,
    onTertiary: ColorProto? = null,
    tertiaryContainer: ColorProto? = null,
    onTertiaryContainer: ColorProto? = null,
    background: ColorProto? = null,
    onBackground: ColorProto? = null,
    surface: ColorProto? = null,
    onSurface: ColorProto? = null,
    surfaceVariant: ColorProto? = null,
    onSurfaceVariant: ColorProto? = null,
    surfaceTint: ColorProto? = null,
    inverseSurface: ColorProto? = null,
    inverseOnSurface: ColorProto? = null,
    error: ColorProto? = null,
    onError: ColorProto? = null,
    errorContainer: ColorProto? = null,
    onErrorContainer: ColorProto? = null,
    outline: ColorProto? = null,
    outlineVariant: ColorProto? = null,
    scrim: ColorProto? = null,
    surfaceBright: ColorProto? = null,
    surfaceDim: ColorProto? = null,
    surfaceContainer: ColorProto? = null,
    surfaceContainerHigh: ColorProto? = null,
    surfaceContainerHighest: ColorProto? = null,
    surfaceContainerLow: ColorProto? = null,
    surfaceContainerLowest: ColorProto? = null,
    primaryFixed: ColorProto? = null,
    primaryFixedDim: ColorProto? = null,
    onPrimaryFixed: ColorProto? = null,
    onPrimaryFixedVariant: ColorProto? = null,
    secondaryFixed: ColorProto? = null,
    secondaryFixedDim: ColorProto? = null,
    onSecondaryFixed: ColorProto? = null,
    onSecondaryFixedVariant: ColorProto? = null,
    tertiaryFixed: ColorProto? = null,
    tertiaryFixedDim: ColorProto? = null,
    onTertiaryFixed: ColorProto? = null,
    onTertiaryFixedVariant: ColorProto? = null,
): ColorSchemeProto =
    ColorSchemeProto(
        primary = primary,
        on_primary = onPrimary,
        primary_container = primaryContainer,
        on_primary_container = onPrimaryContainer,
        inverse_primary = inversePrimary,
        secondary = secondary,
        on_secondary = onSecondary,
        secondary_container = secondaryContainer,
        on_secondary_container = onSecondaryContainer,
        tertiary = tertiary,
        on_tertiary = onTertiary,
        tertiary_container = tertiaryContainer,
        on_tertiary_container = onTertiaryContainer,
        background = background,
        on_background = onBackground,
        surface = surface,
        on_surface = onSurface,
        surface_variant = surfaceVariant,
        on_surface_variant = onSurfaceVariant,
        surface_tint = surfaceTint,
        inverse_surface = inverseSurface,
        inverse_on_surface = inverseOnSurface,
        error = error,
        on_error = onError,
        error_container = errorContainer,
        on_error_container = onErrorContainer,
        outline = outline,
        outline_variant = outlineVariant,
        scrim = scrim,
        surface_bright = surfaceBright,
        surface_dim = surfaceDim,
        surface_container = surfaceContainer,
        surface_container_high = surfaceContainerHigh,
        surface_container_highest = surfaceContainerHighest,
        surface_container_low = surfaceContainerLow,
        surface_container_lowest = surfaceContainerLowest,
        primary_fixed = primaryFixed,
        primary_fixed_dim = primaryFixedDim,
        on_primary_fixed = onPrimaryFixed,
        on_primary_fixed_variant = onPrimaryFixedVariant,
        secondary_fixed = secondaryFixed,
        secondary_fixed_dim = secondaryFixedDim,
        on_secondary_fixed = onSecondaryFixed,
        on_secondary_fixed_variant = onSecondaryFixedVariant,
        tertiary_fixed = tertiaryFixed,
        tertiary_fixed_dim = tertiaryFixedDim,
        on_tertiary_fixed = onTertiaryFixed,
        on_tertiary_fixed_variant = onTertiaryFixedVariant,
    )

public fun PodcaAlertDialogDefaults(
    containerColor: ColorProto? = null,
    iconContentColor: ColorProto? = null,
    titleContentColor: ColorProto? = null,
    textContentColor: ColorProto? = null,
    tonalElevationOverlayColor: ColorProto? = null,
    tonalElevation: DpProto? = null,
): AlertDialogDefaultsProto =
    AlertDialogDefaultsProto(
        container_color = containerColor,
        icon_content_color = iconContentColor,
        title_content_color = titleContentColor,
        text_content_color = textContentColor,
        tonal_elevation_overlay_color = tonalElevationOverlayColor,
        tonal_elevation = tonalElevation,
    )

public fun PodcaShapes(
    extraSmall: ShapeProto? = null,
    small: ShapeProto? = null,
    medium: ShapeProto? = null,
    large: ShapeProto? = null,
    extraLarge: ShapeProto? = null,
    largeIncreased: ShapeProto? = null,
    extraLargeIncreased: ShapeProto? = null,
    extraExtraLarge: ShapeProto? = null,
): com.podca.sdui.protocol.material3.ShapesProto =
    com.podca.sdui.protocol.material3.ShapesProto(
        extra_small = extraSmall,
        small = small,
        medium = medium,
        large = large,
        extra_large = extraLarge,
        large_increased = largeIncreased,
        extra_large_increased = extraLargeIncreased,
        extra_extra_large = extraExtraLarge,
    )

public fun PodcaTypography(
    displayLarge: com.podca.sdui.protocol.ui.text.TextStyleProto? = null,
    displayMedium: com.podca.sdui.protocol.ui.text.TextStyleProto? = null,
    displaySmall: com.podca.sdui.protocol.ui.text.TextStyleProto? = null,
    headlineLarge: com.podca.sdui.protocol.ui.text.TextStyleProto? = null,
    headlineMedium: com.podca.sdui.protocol.ui.text.TextStyleProto? = null,
    headlineSmall: com.podca.sdui.protocol.ui.text.TextStyleProto? = null,
    titleLarge: com.podca.sdui.protocol.ui.text.TextStyleProto? = null,
    titleMedium: com.podca.sdui.protocol.ui.text.TextStyleProto? = null,
    titleSmall: com.podca.sdui.protocol.ui.text.TextStyleProto? = null,
    bodyLarge: com.podca.sdui.protocol.ui.text.TextStyleProto? = null,
    bodyMedium: com.podca.sdui.protocol.ui.text.TextStyleProto? = null,
    bodySmall: com.podca.sdui.protocol.ui.text.TextStyleProto? = null,
    labelLarge: com.podca.sdui.protocol.ui.text.TextStyleProto? = null,
    labelMedium: com.podca.sdui.protocol.ui.text.TextStyleProto? = null,
    labelSmall: com.podca.sdui.protocol.ui.text.TextStyleProto? = null,
    displayLargeEmphasized: com.podca.sdui.protocol.ui.text.TextStyleProto? = null,
    displayMediumEmphasized: com.podca.sdui.protocol.ui.text.TextStyleProto? = null,
    displaySmallEmphasized: com.podca.sdui.protocol.ui.text.TextStyleProto? = null,
    headlineLargeEmphasized: com.podca.sdui.protocol.ui.text.TextStyleProto? = null,
    headlineMediumEmphasized: com.podca.sdui.protocol.ui.text.TextStyleProto? = null,
    headlineSmallEmphasized: com.podca.sdui.protocol.ui.text.TextStyleProto? = null,
    titleLargeEmphasized: com.podca.sdui.protocol.ui.text.TextStyleProto? = null,
    titleMediumEmphasized: com.podca.sdui.protocol.ui.text.TextStyleProto? = null,
    titleSmallEmphasized: com.podca.sdui.protocol.ui.text.TextStyleProto? = null,
    bodyLargeEmphasized: com.podca.sdui.protocol.ui.text.TextStyleProto? = null,
    bodyMediumEmphasized: com.podca.sdui.protocol.ui.text.TextStyleProto? = null,
    bodySmallEmphasized: com.podca.sdui.protocol.ui.text.TextStyleProto? = null,
    labelLargeEmphasized: com.podca.sdui.protocol.ui.text.TextStyleProto? = null,
    labelMediumEmphasized: com.podca.sdui.protocol.ui.text.TextStyleProto? = null,
    labelSmallEmphasized: com.podca.sdui.protocol.ui.text.TextStyleProto? = null,
): TypographyProto =
    TypographyProto(
        display_large = displayLarge,
        display_medium = displayMedium,
        display_small = displaySmall,
        headline_large = headlineLarge,
        headline_medium = headlineMedium,
        headline_small = headlineSmall,
        title_large = titleLarge,
        title_medium = titleMedium,
        title_small = titleSmall,
        body_large = bodyLarge,
        body_medium = bodyMedium,
        body_small = bodySmall,
        label_large = labelLarge,
        label_medium = labelMedium,
        label_small = labelSmall,
        display_large_emphasized = displayLargeEmphasized,
        display_medium_emphasized = displayMediumEmphasized,
        display_small_emphasized = displaySmallEmphasized,
        headline_large_emphasized = headlineLargeEmphasized,
        headline_medium_emphasized = headlineMediumEmphasized,
        headline_small_emphasized = headlineSmallEmphasized,
        title_large_emphasized = titleLargeEmphasized,
        title_medium_emphasized = titleMediumEmphasized,
        title_small_emphasized = titleSmallEmphasized,
        body_large_emphasized = bodyLargeEmphasized,
        body_medium_emphasized = bodyMediumEmphasized,
        body_small_emphasized = bodySmallEmphasized,
        label_large_emphasized = labelLargeEmphasized,
        label_medium_emphasized = labelMediumEmphasized,
        label_small_emphasized = labelSmallEmphasized,
    )

public fun PodcaTextFieldColors(
    focusedTextColor: ColorProto? = null,
    unfocusedTextColor: ColorProto? = null,
    disabledTextColor: ColorProto? = null,
    errorTextColor: ColorProto? = null,
    focusedContainerColor: ColorProto? = null,
    unfocusedContainerColor: ColorProto? = null,
    disabledContainerColor: ColorProto? = null,
    errorContainerColor: ColorProto? = null,
    cursorColor: ColorProto? = null,
    errorCursorColor: ColorProto? = null,
    textSelectionColors: com.podca.sdui.protocol.ui.text.TextSelectionColorsProto? = null,
    focusedIndicatorColor: ColorProto? = null,
    unfocusedIndicatorColor: ColorProto? = null,
    disabledIndicatorColor: ColorProto? = null,
    errorIndicatorColor: ColorProto? = null,
    focusedLeadingIconColor: ColorProto? = null,
    unfocusedLeadingIconColor: ColorProto? = null,
    disabledLeadingIconColor: ColorProto? = null,
    errorLeadingIconColor: ColorProto? = null,
    focusedTrailingIconColor: ColorProto? = null,
    unfocusedTrailingIconColor: ColorProto? = null,
    disabledTrailingIconColor: ColorProto? = null,
    errorTrailingIconColor: ColorProto? = null,
    focusedLabelColor: ColorProto? = null,
    unfocusedLabelColor: ColorProto? = null,
    disabledLabelColor: ColorProto? = null,
    errorLabelColor: ColorProto? = null,
    focusedPlaceholderColor: ColorProto? = null,
    unfocusedPlaceholderColor: ColorProto? = null,
    disabledPlaceholderColor: ColorProto? = null,
    errorPlaceholderColor: ColorProto? = null,
    focusedSupportingTextColor: ColorProto? = null,
    unfocusedSupportingTextColor: ColorProto? = null,
    disabledSupportingTextColor: ColorProto? = null,
    errorSupportingTextColor: ColorProto? = null,
    focusedPrefixColor: ColorProto? = null,
    unfocusedPrefixColor: ColorProto? = null,
    disabledPrefixColor: ColorProto? = null,
    errorPrefixColor: ColorProto? = null,
    focusedSuffixColor: ColorProto? = null,
    unfocusedSuffixColor: ColorProto? = null,
    disabledSuffixColor: ColorProto? = null,
    errorSuffixColor: ColorProto? = null,
): TextFieldColorsProto =
    TextFieldColorsProto(
        focused_text_color = focusedTextColor,
        unfocused_text_color = unfocusedTextColor,
        disabled_text_color = disabledTextColor,
        error_text_color = errorTextColor,
        focused_container_color = focusedContainerColor,
        unfocused_container_color = unfocusedContainerColor,
        disabled_container_color = disabledContainerColor,
        error_container_color = errorContainerColor,
        cursor_color = cursorColor,
        error_cursor_color = errorCursorColor,
        text_selection_colors = textSelectionColors,
        focused_indicator_color = focusedIndicatorColor,
        unfocused_indicator_color = unfocusedIndicatorColor,
        disabled_indicator_color = disabledIndicatorColor,
        error_indicator_color = errorIndicatorColor,
        focused_leading_icon_color = focusedLeadingIconColor,
        unfocused_leading_icon_color = unfocusedLeadingIconColor,
        disabled_leading_icon_color = disabledLeadingIconColor,
        error_leading_icon_color = errorLeadingIconColor,
        focused_trailing_icon_color = focusedTrailingIconColor,
        unfocused_trailing_icon_color = unfocusedTrailingIconColor,
        disabled_trailing_icon_color = disabledTrailingIconColor,
        error_trailing_icon_color = errorTrailingIconColor,
        focused_label_color = focusedLabelColor,
        unfocused_label_color = unfocusedLabelColor,
        disabled_label_color = disabledLabelColor,
        error_label_color = errorLabelColor,
        focused_placeholder_color = focusedPlaceholderColor,
        unfocused_placeholder_color = unfocusedPlaceholderColor,
        disabled_placeholder_color = disabledPlaceholderColor,
        error_placeholder_color = errorPlaceholderColor,
        focused_supporting_text_color = focusedSupportingTextColor,
        unfocused_supporting_text_color = unfocusedSupportingTextColor,
        disabled_supporting_text_color = disabledSupportingTextColor,
        error_supporting_text_color = errorSupportingTextColor,
        focused_prefix_color = focusedPrefixColor,
        unfocused_prefix_color = unfocusedPrefixColor,
        disabled_prefix_color = disabledPrefixColor,
        error_prefix_color = errorPrefixColor,
        focused_suffix_color = focusedSuffixColor,
        unfocused_suffix_color = unfocusedSuffixColor,
        disabled_suffix_color = disabledSuffixColor,
        error_suffix_color = errorSuffixColor,
    )

public fun PodcaCheckboxColors(
    checkedCheckmarkColor: ColorProto? = null,
    uncheckedCheckmarkColor: ColorProto? = null,
    checkedBoxColor: ColorProto? = null,
    uncheckedBoxColor: ColorProto? = null,
    disabledCheckedBoxColor: ColorProto? = null,
    disabledUncheckedBoxColor: ColorProto? = null,
    disabledIndeterminateBoxColor: ColorProto? = null,
    checkedBorderColor: ColorProto? = null,
    uncheckedBorderColor: ColorProto? = null,
    disabledBorderColor: ColorProto? = null,
    disabledUncheckedBorderColor: ColorProto? = null,
    disabledIndeterminateBorderColor: ColorProto? = null,
): CheckboxColorsProto =
    CheckboxColorsProto(
        checked_checkmark_color = checkedCheckmarkColor,
        unchecked_checkmark_color = uncheckedCheckmarkColor,
        checked_box_color = checkedBoxColor,
        unchecked_box_color = uncheckedBoxColor,
        disabled_checked_box_color = disabledCheckedBoxColor,
        disabled_unchecked_box_color = disabledUncheckedBoxColor,
        disabled_indeterminate_box_color = disabledIndeterminateBoxColor,
        checked_border_color = checkedBorderColor,
        unchecked_border_color = uncheckedBorderColor,
        disabled_border_color = disabledBorderColor,
        disabled_unchecked_border_color = disabledUncheckedBorderColor,
        disabled_indeterminate_border_color = disabledIndeterminateBorderColor,
    )

public fun PodcaChipColors(
    containerColor: ColorProto? = null,
    labelColor: ColorProto? = null,
    leadingIconContentColor: ColorProto? = null,
    trailingIconContentColor: ColorProto? = null,
    disabledContainerColor: ColorProto? = null,
    disabledLabelColor: ColorProto? = null,
    disabledLeadingIconContentColor: ColorProto? = null,
    disabledTrailingIconContentColor: ColorProto? = null,
): ChipColorsProto =
    ChipColorsProto(
        container_color = containerColor,
        label_color = labelColor,
        leading_icon_content_color = leadingIconContentColor,
        trailing_icon_content_color = trailingIconContentColor,
        disabled_container_color = disabledContainerColor,
        disabled_label_color = disabledLabelColor,
        disabled_leading_icon_content_color = disabledLeadingIconContentColor,
        disabled_trailing_icon_content_color = disabledTrailingIconContentColor,
    )

public fun PodcaSelectableChipColors(
    containerColor: ColorProto? = null,
    labelColor: ColorProto? = null,
    leadingIconColor: ColorProto? = null,
    trailingIconColor: ColorProto? = null,
    disabledContainerColor: ColorProto? = null,
    disabledLabelColor: ColorProto? = null,
    disabledLeadingIconColor: ColorProto? = null,
    disabledTrailingIconColor: ColorProto? = null,
    selectedContainerColor: ColorProto? = null,
    disabledSelectedContainerColor: ColorProto? = null,
    selectedLabelColor: ColorProto? = null,
    selectedLeadingIconColor: ColorProto? = null,
    selectedTrailingIconColor: ColorProto? = null,
): com.podca.sdui.protocol.material3.SelectableChipColorsProto =
    com.podca.sdui.protocol.material3.SelectableChipColorsProto(
        container_color = containerColor,
        label_color = labelColor,
        leading_icon_color = leadingIconColor,
        trailing_icon_color = trailingIconColor,
        disabled_container_color = disabledContainerColor,
        disabled_label_color = disabledLabelColor,
        disabled_leading_icon_color = disabledLeadingIconColor,
        disabled_trailing_icon_color = disabledTrailingIconColor,
        selected_container_color = selectedContainerColor,
        disabled_selected_container_color = disabledSelectedContainerColor,
        selected_label_color = selectedLabelColor,
        selected_leading_icon_color = selectedLeadingIconColor,
        selected_trailing_icon_color = selectedTrailingIconColor,
    )

public fun PodcaChipBorder(
    borderColor: ColorProto? = null,
    disabledBorderColor: ColorProto? = null,
    borderWidth: DpProto? = null,
): ChipBorderProto =
    ChipBorderProto(
        border_color = borderColor,
        disabled_border_color = disabledBorderColor,
        border_width = borderWidth,
    )

public fun PodcaChipElevation(
    elevation: DpProto? = null,
    pressedElevation: DpProto? = null,
    focusedElevation: DpProto? = null,
    hoveredElevation: DpProto? = null,
    draggedElevation: DpProto? = null,
    disabledElevation: DpProto? = null,
): ChipElevationProto =
    ChipElevationProto(
        elevation = elevation,
        pressed_elevation = pressedElevation,
        focused_elevation = focusedElevation,
        hovered_elevation = hoveredElevation,
        dragged_elevation = draggedElevation,
        disabled_elevation = disabledElevation,
    )

public fun PodcaSelectableChipElevation(
    elevation: DpProto? = null,
    pressedElevation: DpProto? = null,
    focusedElevation: DpProto? = null,
    hoveredElevation: DpProto? = null,
    draggedElevation: DpProto? = null,
    disabledElevation: DpProto? = null,
): com.podca.sdui.protocol.material3.SelectableChipElevationProto =
    com.podca.sdui.protocol.material3.SelectableChipElevationProto(
        elevation = elevation,
        pressed_elevation = pressedElevation,
        focused_elevation = focusedElevation,
        hovered_elevation = hoveredElevation,
        dragged_elevation = draggedElevation,
        disabled_elevation = disabledElevation,
    )

public fun PodcaListItemColors(
    containerColor: ColorProto? = null,
    headlineColor: ColorProto? = null,
    leadingIconColor: ColorProto? = null,
    overlineColor: ColorProto? = null,
    supportingTextColor: ColorProto? = null,
    trailingIconColor: ColorProto? = null,
    disabledHeadlineColor: ColorProto? = null,
    disabledLeadingIconColor: ColorProto? = null,
    disabledTrailingIconColor: ColorProto? = null,
): ListItemColorsProto =
    ListItemColorsProto(
        container_color = containerColor,
        headline_color = headlineColor,
        leading_icon_color = leadingIconColor,
        overline_color = overlineColor,
        supporting_text_color = supportingTextColor,
        trailing_icon_color = trailingIconColor,
        disabled_headline_color = disabledHeadlineColor,
        disabled_leading_icon_color = disabledLeadingIconColor,
        disabled_trailing_icon_color = disabledTrailingIconColor,
    )

public fun PodcaIconButtonColors(
    containerColor: ColorProto? = null,
    contentColor: ColorProto? = null,
    disabledContainerColor: ColorProto? = null,
    disabledContentColor: ColorProto? = null,
): IconButtonColorsProto =
    IconButtonColorsProto(
        container_color = containerColor,
        content_color = contentColor,
        disabled_container_color = disabledContainerColor,
        disabled_content_color = disabledContentColor,
    )

public fun PodcaIconToggleButtonColors(
    containerColor: ColorProto? = null,
    contentColor: ColorProto? = null,
    disabledContainerColor: ColorProto? = null,
    disabledContentColor: ColorProto? = null,
    checkedContainerColor: ColorProto? = null,
    checkedContentColor: ColorProto? = null,
): IconToggleButtonColorsProto =
    IconToggleButtonColorsProto(
        container_color = containerColor,
        content_color = contentColor,
        disabled_container_color = disabledContainerColor,
        disabled_content_color = disabledContentColor,
        checked_container_color = checkedContainerColor,
        checked_content_color = checkedContentColor,
    )

public fun PodcaIconButtonDefaults(
    standardShape: ShapeProto? = null,
    smallShape: ShapeProto? = null,
    mediumShape: ShapeProto? = null,
    largeShape: ShapeProto? = null,
    extraSmallContainerSize: DpProto? = null,
    smallContainerSize: DpProto? = null,
    mediumContainerSize: DpProto? = null,
    largeContainerSize: DpProto? = null,
    extraLargeContainerSize: DpProto? = null,
    defaultWidthOption: IconButtonWidthOptionProto = IconButtonWidthOptionProto.ICON_BUTTON_WIDTH_OPTION_UNIFORM,
): IconButtonDefaultsProto =
    IconButtonDefaultsProto(
        standard_shape = standardShape,
        small_shape = smallShape,
        medium_shape = mediumShape,
        large_shape = largeShape,
        extra_small_container_size = extraSmallContainerSize,
        small_container_size = smallContainerSize,
        medium_container_size = mediumContainerSize,
        large_container_size = largeContainerSize,
        extra_large_container_size = extraLargeContainerSize,
        default_width_option = defaultWidthOption,
    )

public fun PodcaNavigationBarItemColors(
    selectedIconColor: ColorProto? = null,
    selectedTextColor: ColorProto? = null,
    selectedIndicatorColor: ColorProto? = null,
    unselectedIconColor: ColorProto? = null,
    unselectedTextColor: ColorProto? = null,
    disabledIconColor: ColorProto? = null,
    disabledTextColor: ColorProto? = null,
): NavigationBarItemColorsProto =
    NavigationBarItemColorsProto(
        selected_icon_color = selectedIconColor,
        selected_text_color = selectedTextColor,
        selected_indicator_color = selectedIndicatorColor,
        unselected_icon_color = unselectedIconColor,
        unselected_text_color = unselectedTextColor,
        disabled_icon_color = disabledIconColor,
        disabled_text_color = disabledTextColor,
    )

public fun PodcaNavigationDrawerItemColors(
    selectedContainerColor: ColorProto? = null,
    unselectedContainerColor: ColorProto? = null,
    selectedIconColor: ColorProto? = null,
    unselectedIconColor: ColorProto? = null,
    selectedTextColor: ColorProto? = null,
    unselectedTextColor: ColorProto? = null,
    selectedBadgeColor: ColorProto? = null,
    unselectedBadgeColor: ColorProto? = null,
): NavigationDrawerItemColorsProto =
    NavigationDrawerItemColorsProto(
        selected_container_color = selectedContainerColor,
        unselected_container_color = unselectedContainerColor,
        selected_icon_color = selectedIconColor,
        unselected_icon_color = unselectedIconColor,
        selected_text_color = selectedTextColor,
        unselected_text_color = unselectedTextColor,
        selected_badge_color = selectedBadgeColor,
        unselected_badge_color = unselectedBadgeColor,
    )

public fun PodcaNavigationItemColors(
    selectedIconColor: ColorProto? = null,
    selectedTextColor: ColorProto? = null,
    selectedIndicatorColor: ColorProto? = null,
    unselectedIconColor: ColorProto? = null,
    unselectedTextColor: ColorProto? = null,
    disabledIconColor: ColorProto? = null,
    disabledTextColor: ColorProto? = null,
): NavigationItemColorsProto =
    NavigationItemColorsProto(
        selected_icon_color = selectedIconColor,
        selected_text_color = selectedTextColor,
        selected_indicator_color = selectedIndicatorColor,
        unselected_icon_color = unselectedIconColor,
        unselected_text_color = unselectedTextColor,
        disabled_icon_color = disabledIconColor,
        disabled_text_color = disabledTextColor,
    )

public fun PodcaNavigationRailItemColors(
    selectedIconColor: ColorProto? = null,
    selectedTextColor: ColorProto? = null,
    selectedIndicatorColor: ColorProto? = null,
    unselectedIconColor: ColorProto? = null,
    unselectedTextColor: ColorProto? = null,
    disabledIconColor: ColorProto? = null,
    disabledTextColor: ColorProto? = null,
): NavigationRailItemColorsProto =
    NavigationRailItemColorsProto(
        selected_icon_color = selectedIconColor,
        selected_text_color = selectedTextColor,
        selected_indicator_color = selectedIndicatorColor,
        unselected_icon_color = unselectedIconColor,
        unselected_text_color = unselectedTextColor,
        disabled_icon_color = disabledIconColor,
        disabled_text_color = disabledTextColor,
    )

public fun PodcaRadioButtonColors(
    selectedColor: ColorProto? = null,
    unselectedColor: ColorProto? = null,
    disabledSelectedColor: ColorProto? = null,
    disabledUnselectedColor: ColorProto? = null,
): RadioButtonColorsProto =
    RadioButtonColorsProto(
        selected_color = selectedColor,
        unselected_color = unselectedColor,
        disabled_selected_color = disabledSelectedColor,
        disabled_unselected_color = disabledUnselectedColor,
    )

public fun PodcaSegmentedButtonColors(
    activeContainerColor: ColorProto? = null,
    activeContentColor: ColorProto? = null,
    activeBorderColor: ColorProto? = null,
    inactiveContainerColor: ColorProto? = null,
    inactiveContentColor: ColorProto? = null,
    inactiveBorderColor: ColorProto? = null,
    disabledActiveContainerColor: ColorProto? = null,
    disabledActiveContentColor: ColorProto? = null,
    disabledActiveBorderColor: ColorProto? = null,
    disabledInactiveContainerColor: ColorProto? = null,
    disabledInactiveContentColor: ColorProto? = null,
    disabledInactiveBorderColor: ColorProto? = null,
): SegmentedButtonColorsProto =
    SegmentedButtonColorsProto(
        active_container_color = activeContainerColor,
        active_content_color = activeContentColor,
        active_border_color = activeBorderColor,
        inactive_container_color = inactiveContainerColor,
        inactive_content_color = inactiveContentColor,
        inactive_border_color = inactiveBorderColor,
        disabled_active_container_color = disabledActiveContainerColor,
        disabled_active_content_color = disabledActiveContentColor,
        disabled_active_border_color = disabledActiveBorderColor,
        disabled_inactive_container_color = disabledInactiveContainerColor,
        disabled_inactive_content_color = disabledInactiveContentColor,
        disabled_inactive_border_color = disabledInactiveBorderColor,
    )

public fun PodcaSwitchColors(
    checkedThumbColor: ColorProto? = null,
    checkedTrackColor: ColorProto? = null,
    checkedBorderColor: ColorProto? = null,
    checkedIconColor: ColorProto? = null,
    uncheckedThumbColor: ColorProto? = null,
    uncheckedTrackColor: ColorProto? = null,
    uncheckedBorderColor: ColorProto? = null,
    uncheckedIconColor: ColorProto? = null,
    disabledCheckedThumbColor: ColorProto? = null,
    disabledCheckedTrackColor: ColorProto? = null,
    disabledCheckedBorderColor: ColorProto? = null,
    disabledCheckedIconColor: ColorProto? = null,
    disabledUncheckedThumbColor: ColorProto? = null,
    disabledUncheckedTrackColor: ColorProto? = null,
    disabledUncheckedBorderColor: ColorProto? = null,
    disabledUncheckedIconColor: ColorProto? = null,
): SwitchColorsProto =
    SwitchColorsProto(
        checked_thumb_color = checkedThumbColor,
        checked_track_color = checkedTrackColor,
        checked_border_color = checkedBorderColor,
        checked_icon_color = checkedIconColor,
        unchecked_thumb_color = uncheckedThumbColor,
        unchecked_track_color = uncheckedTrackColor,
        unchecked_border_color = uncheckedBorderColor,
        unchecked_icon_color = uncheckedIconColor,
        disabled_checked_thumb_color = disabledCheckedThumbColor,
        disabled_checked_track_color = disabledCheckedTrackColor,
        disabled_checked_border_color = disabledCheckedBorderColor,
        disabled_checked_icon_color = disabledCheckedIconColor,
        disabled_unchecked_thumb_color = disabledUncheckedThumbColor,
        disabled_unchecked_track_color = disabledUncheckedTrackColor,
        disabled_unchecked_border_color = disabledUncheckedBorderColor,
        disabled_unchecked_icon_color = disabledUncheckedIconColor,
    )

public fun PodcaDatePickerColors(
    containerColor: ColorProto? = null,
    titleContentColor: ColorProto? = null,
    headlineContentColor: ColorProto? = null,
    weekdayContentColor: ColorProto? = null,
    subheadContentColor: ColorProto? = null,
    navigationContentColor: ColorProto? = null,
    yearContentColor: ColorProto? = null,
    disabledYearContentColor: ColorProto? = null,
    currentYearContentColor: ColorProto? = null,
    selectedYearContentColor: ColorProto? = null,
    disabledSelectedYearContentColor: ColorProto? = null,
    selectedYearContainerColor: ColorProto? = null,
    disabledSelectedYearContainerColor: ColorProto? = null,
    dayContentColor: ColorProto? = null,
    disabledDayContentColor: ColorProto? = null,
    selectedDayContentColor: ColorProto? = null,
    disabledSelectedDayContentColor: ColorProto? = null,
    selectedDayContainerColor: ColorProto? = null,
    disabledSelectedDayContainerColor: ColorProto? = null,
    todayContentColor: ColorProto? = null,
    todayDateBorderColor: ColorProto? = null,
    dayInSelectionRangeContainerColor: ColorProto? = null,
    dayInSelectionRangeContentColor: ColorProto? = null,
    dividerColor: ColorProto? = null,
    dateTextFieldColors: TextFieldColorsProto? = null,
): DatePickerColorsProto =
    DatePickerColorsProto(
        container_color = containerColor,
        title_content_color = titleContentColor,
        headline_content_color = headlineContentColor,
        weekday_content_color = weekdayContentColor,
        subhead_content_color = subheadContentColor,
        navigation_content_color = navigationContentColor,
        year_content_color = yearContentColor,
        disabled_year_content_color = disabledYearContentColor,
        current_year_content_color = currentYearContentColor,
        selected_year_content_color = selectedYearContentColor,
        disabled_selected_year_content_color = disabledSelectedYearContentColor,
        selected_year_container_color = selectedYearContainerColor,
        disabled_selected_year_container_color = disabledSelectedYearContainerColor,
        day_content_color = dayContentColor,
        disabled_day_content_color = disabledDayContentColor,
        selected_day_content_color = selectedDayContentColor,
        disabled_selected_day_content_color = disabledSelectedDayContentColor,
        selected_day_container_color = selectedDayContainerColor,
        disabled_selected_day_container_color = disabledSelectedDayContainerColor,
        today_content_color = todayContentColor,
        today_date_border_color = todayDateBorderColor,
        day_in_selection_range_container_color = dayInSelectionRangeContainerColor,
        day_in_selection_range_content_color = dayInSelectionRangeContentColor,
        divider_color = dividerColor,
        date_text_field_colors = dateTextFieldColors,
    )

public fun PodcaInputIdentifier(
    value: Int,
): InputIdentifierProto =
    InputIdentifierProto(
        value_ = value,
    )

public fun PodcaDateInputValidator(
    acceptsPartialInput: Boolean = false,
): DateInputValidatorProto =
    DateInputValidatorProto(
        accepts_partial_input = acceptsPartialInput,
    )

public fun PodcaTextFieldState(
    text: String = "",
    selectionStart: Int = 0,
    selectionEnd: Int = 0,
): TextFieldStateProto =
    TextFieldStateProto(
        text = text,
        selection_start = selectionStart,
        selection_end = selectionEnd,
    )

public fun PodcaRippleAlpha(
    pressedAlpha: Float = 0f,
    focusedAlpha: Float = 0f,
    draggedAlpha: Float = 0f,
    hoveredAlpha: Float = 0f,
): RippleAlphaProto =
    RippleAlphaProto(
        pressed_alpha = pressedAlpha,
        focused_alpha = focusedAlpha,
        dragged_alpha = draggedAlpha,
        hovered_alpha = hoveredAlpha,
    )

public fun PodcaTimePickerColors(
    clockDialColor: ColorProto? = null,
    selectorColor: ColorProto? = null,
    containerColor: ColorProto? = null,
    periodSelectorBorderColor: ColorProto? = null,
    clockDialSelectedContentColor: ColorProto? = null,
    clockDialUnselectedContentColor: ColorProto? = null,
    periodSelectorSelectedContainerColor: ColorProto? = null,
    periodSelectorUnselectedContainerColor: ColorProto? = null,
    periodSelectorSelectedContentColor: ColorProto? = null,
    periodSelectorUnselectedContentColor: ColorProto? = null,
    timeSelectorSelectedContainerColor: ColorProto? = null,
    timeSelectorUnselectedContainerColor: ColorProto? = null,
    timeSelectorSelectedContentColor: ColorProto? = null,
    timeSelectorUnselectedContentColor: ColorProto? = null,
): TimePickerColorsProto =
    TimePickerColorsProto(
        clock_dial_color = clockDialColor,
        selector_color = selectorColor,
        container_color = containerColor,
        period_selector_border_color = periodSelectorBorderColor,
        clock_dial_selected_content_color = clockDialSelectedContentColor,
        clock_dial_unselected_content_color = clockDialUnselectedContentColor,
        period_selector_selected_container_color = periodSelectorSelectedContainerColor,
        period_selector_unselected_container_color = periodSelectorUnselectedContainerColor,
        period_selector_selected_content_color = periodSelectorSelectedContentColor,
        period_selector_unselected_content_color = periodSelectorUnselectedContentColor,
        time_selector_selected_container_color = timeSelectorSelectedContainerColor,
        time_selector_unselected_container_color = timeSelectorUnselectedContainerColor,
        time_selector_selected_content_color = timeSelectorSelectedContentColor,
        time_selector_unselected_content_color = timeSelectorUnselectedContentColor,
    )

public fun PodcaRichTooltipColors(
    containerColor: ColorProto? = null,
    contentColor: ColorProto? = null,
    titleContentColor: ColorProto? = null,
    actionContentColor: ColorProto? = null,
): RichTooltipColorsProto =
    RichTooltipColorsProto(
        container_color = containerColor,
        content_color = contentColor,
        title_content_color = titleContentColor,
        action_content_color = actionContentColor,
    )

public fun PodcaProgressIndicatorDefaults(
    linearColor: ColorProto? = null,
    linearTrackColor: ColorProto? = null,
    circularColor: ColorProto? = null,
    circularTrackColor: ColorProto? = null,
    linearStrokeCap: StrokeCapProto = StrokeCapProto.STROKE_CAP_BUTT,
    circularStrokeCap: StrokeCapProto = StrokeCapProto.STROKE_CAP_BUTT,
    linearIndicatorTrackGapSize: DpProto? = null,
    linearTrackStopIndicatorSize: DpProto? = null,
): com.podca.sdui.protocol.material3.ProgressIndicatorDefaultsProto =
    com.podca.sdui.protocol.material3.ProgressIndicatorDefaultsProto(
        linear_color = linearColor,
        linear_track_color = linearTrackColor,
        circular_color = circularColor,
        circular_track_color = circularTrackColor,
        linear_stroke_cap = linearStrokeCap,
        circular_stroke_cap = circularStrokeCap,
        linear_indicator_track_gap_size = linearIndicatorTrackGapSize,
        linear_track_stop_indicator_size = linearTrackStopIndicatorSize,
    )

public fun PodcaBadgeDefaults(
    containerColor: ColorProto? = null,
): BadgeDefaultsProto =
    BadgeDefaultsProto(
        container_color = containerColor,
    )

public fun PodcaDividerDefaults(
    thickness: DpProto? = null,
    color: ColorProto? = null,
): DividerDefaultsProto =
    DividerDefaultsProto(
        thickness = thickness,
        color = color,
    )

public fun PodcaSnackbarDefaults(
    shape: ShapeProto? = null,
    color: ColorProto? = null,
    contentColor: ColorProto? = null,
    actionColor: ColorProto? = null,
    actionContentColor: ColorProto? = null,
    dismissActionContentColor: ColorProto? = null,
): SnackbarDefaultsProto =
    SnackbarDefaultsProto(
        shape = shape,
        color = color,
        content_color = contentColor,
        action_color = actionColor,
        action_content_color = actionContentColor,
        dismiss_action_content_color = dismissActionContentColor,
    )

public fun PodcaDragHandleColors(
    color: ColorProto? = null,
    pressedColor: ColorProto? = null,
    draggedColor: ColorProto? = null,
): DragHandleColorsProto =
    DragHandleColorsProto(
        color = color,
        pressed_color = pressedColor,
        dragged_color = draggedColor,
    )

public fun PodcaDragHandleShapes(
    shape: ShapeProto? = null,
    pressedShape: ShapeProto? = null,
    draggedShape: ShapeProto? = null,
): DragHandleShapesProto =
    DragHandleShapesProto(
        shape = shape,
        pressed_shape = pressedShape,
        dragged_shape = draggedShape,
    )

public fun PodcaDragHandleSizes(
    size: com.podca.sdui.protocol.ui.unit.DpSizeProto? = null,
    pressedSize: com.podca.sdui.protocol.ui.unit.DpSizeProto? = null,
    draggedSize: com.podca.sdui.protocol.ui.unit.DpSizeProto? = null,
): DragHandleSizesProto =
    DragHandleSizesProto(
        size = size,
        pressed_size = pressedSize,
        dragged_size = draggedSize,
    )

public fun PodcaMotionScheme(
    value: MotionSchemeProto = MotionSchemeProto.MOTION_SCHEME_UNSPECIFIED,
): MotionSchemeProto = value

public fun PodcaSheetValue(
    value: SheetValueProto = SheetValueProto.SHEET_VALUE_HIDDEN,
): SheetValueProto = value

public fun PodcaDrawerValue(
    value: com.podca.sdui.protocol.material3.DrawerValueProto = com.podca.sdui.protocol.material3.DrawerValueProto.DRAWER_VALUE_CLOSED,
): com.podca.sdui.protocol.material3.DrawerValueProto = value

public fun PodcaSnackbarResult(
    value: SnackbarResultProto = SnackbarResultProto.SNACKBAR_RESULT_DISMISSED,
): SnackbarResultProto = value

public fun PodcaSnackbarDuration(
    value: SnackbarDurationProto = SnackbarDurationProto.SNACKBAR_DURATION_SHORT,
): SnackbarDurationProto = value

public fun PodcaDisplayMode(
    value: DisplayModeProto = DisplayModeProto.DISPLAY_MODE_PICKER,
): DisplayModeProto = value

public fun PodcaTimePickerLayoutType(
    value: TimePickerLayoutTypeProto = TimePickerLayoutTypeProto.TIME_PICKER_LAYOUT_TYPE_HORIZONTAL,
): TimePickerLayoutTypeProto = value

public fun PodcaTimePickerSelectionMode(
    value: TimePickerSelectionModeProto = TimePickerSelectionModeProto.TIME_PICKER_SELECTION_MODE_HOUR,
): TimePickerSelectionModeProto = value

public fun PodcaTimeFormat(
    value: TimeFormatProto = TimeFormatProto.TIME_FORMAT_12_HOUR,
): TimeFormatProto = value

public fun PodcaTimePickerDisplayMode(
    value: TimePickerDisplayModeProto = TimePickerDisplayModeProto.TIME_PICKER_DISPLAY_MODE_PICKER,
): TimePickerDisplayModeProto = value

public fun PodcaTooltipAnchorPosition(
    value: com.podca.sdui.protocol.material3.TooltipAnchorPositionProto = com.podca.sdui.protocol.material3.TooltipAnchorPositionProto.TOOLTIP_ANCHOR_POSITION_UNSPECIFIED,
): com.podca.sdui.protocol.material3.TooltipAnchorPositionProto = value

public fun PodcaNavigationItemIconPosition(
    value: NavigationItemIconPositionProto = NavigationItemIconPositionProto.NAVIGATION_ITEM_ICON_POSITION_TOP,
): NavigationItemIconPositionProto = value

public fun PodcaIconButtonWidthOption(
    value: IconButtonWidthOptionProto = IconButtonWidthOptionProto.ICON_BUTTON_WIDTH_OPTION_UNIFORM,
): IconButtonWidthOptionProto = value

public fun PodcaShortNavigationBarArrangement(
    value: ShortNavigationBarArrangementProto = ShortNavigationBarArrangementProto.SHORT_NAVIGATION_BAR_ARRANGEMENT_EQUAL_WEIGHT,
): ShortNavigationBarArrangementProto = value

public fun PodcaFabPosition(
    value: FabPositionProto = FabPositionProto.FAB_POSITION_END,
): FabPositionProto = value

public fun PodcaSearchBarValue(
    value: SearchBarValueProto = SearchBarValueProto.SEARCH_BAR_VALUE_COLLAPSED,
): SearchBarValueProto = value

public fun PodcaExposedDropdownMenuAnchorType(
    value: ExposedDropdownMenuAnchorTypeProto =
        ExposedDropdownMenuAnchorTypeProto.EXPOSED_DROPDOWN_MENU_ANCHOR_TYPE_UNSPECIFIED,
): ExposedDropdownMenuAnchorTypeProto = value

public fun PodcaWideNavigationRailColors(
    containerColor: ColorProto? = null,
    contentColor: ColorProto? = null,
    modalContainerColor: ColorProto? = null,
    modalScrimColor: ColorProto? = null,
    modalContentColor: ColorProto? = null,
): WideNavigationRailColorsProto =
    WideNavigationRailColorsProto(
        container_color = containerColor,
        content_color = contentColor,
        modal_container_color = modalContainerColor,
        modal_scrim_color = modalScrimColor,
        modal_content_color = modalContentColor,
    )

public fun PodcaWideNavigationRailValue(
    value: WideNavigationRailValueProto = WideNavigationRailValueProto.WIDE_NAVIGATION_RAIL_VALUE_COLLAPSED,
): WideNavigationRailValueProto = value
