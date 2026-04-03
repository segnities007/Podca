package com.podca.sdui.studio.ui.material3

import com.podca.sdui.protocol.material3.AppBarClickableItemProto
import com.podca.sdui.protocol.material3.AppBarCustomItemProto
import com.podca.sdui.protocol.material3.AppBarMenuStateProto
import com.podca.sdui.protocol.material3.AppBarScopeProto
import com.podca.sdui.protocol.material3.AppBarColumnScopeProto
import com.podca.sdui.protocol.material3.AppBarRowScopeProto
import com.podca.sdui.protocol.material3.AppBarToggleableItemProto
import com.podca.sdui.protocol.material3.BorderStrokeProto
import com.podca.sdui.protocol.material3.BottomAppBarScrollBehaviorProto
import com.podca.sdui.protocol.material3.BottomAppBarStateProto
import com.podca.sdui.protocol.material3.ButtonColorsProto
import com.podca.sdui.protocol.material3.ButtonElevationProto
import com.podca.sdui.protocol.material3.CardColorsProto
import com.podca.sdui.protocol.material3.CardElevationProto
import com.podca.sdui.protocol.material3.RangeSliderStateProto
import com.podca.sdui.protocol.material3.SliderColorsProto
import com.podca.sdui.protocol.material3.SliderPositionsProto
import com.podca.sdui.protocol.material3.SliderStateProto
import com.podca.sdui.protocol.material3.SnackbarDataProto
import com.podca.sdui.protocol.material3.SnackbarDurationProto
import com.podca.sdui.protocol.material3.SnackbarHostStateProto
import com.podca.sdui.protocol.material3.SnackbarVisualsProto
import com.podca.sdui.protocol.material3.TopAppBarColorsProto
import com.podca.sdui.protocol.material3.TopAppBarStateProto
import com.podca.sdui.protocol.ui.graphics.ColorProto
import com.podca.sdui.protocol.ui.unit.DpProto

public fun PodcaBorderStroke(
    width: DpProto? = null,
    color: ColorProto? = null,
): BorderStrokeProto =
    BorderStrokeProto(
        width = width,
        color = color,
    )

public fun PodcaButtonColors(
    containerColor: ColorProto? = null,
    contentColor: ColorProto? = null,
    disabledContainerColor: ColorProto? = null,
    disabledContentColor: ColorProto? = null,
): ButtonColorsProto =
    ButtonColorsProto(
        container_color = containerColor,
        content_color = contentColor,
        disabled_container_color = disabledContainerColor,
        disabled_content_color = disabledContentColor,
    )

public fun PodcaButtonElevation(
    defaultElevation: DpProto? = null,
    pressedElevation: DpProto? = null,
    focusedElevation: DpProto? = null,
    hoveredElevation: DpProto? = null,
    disabledElevation: DpProto? = null,
): ButtonElevationProto =
    ButtonElevationProto(
        default_elevation = defaultElevation,
        pressed_elevation = pressedElevation,
        focused_elevation = focusedElevation,
        hovered_elevation = hoveredElevation,
        disabled_elevation = disabledElevation,
    )

public fun PodcaCardColors(
    containerColor: ColorProto? = null,
    contentColor: ColorProto? = null,
    disabledContainerColor: ColorProto? = null,
    disabledContentColor: ColorProto? = null,
): CardColorsProto =
    CardColorsProto(
        container_color = containerColor,
        content_color = contentColor,
        disabled_container_color = disabledContainerColor,
        disabled_content_color = disabledContentColor,
    )

public fun PodcaCardElevation(
    defaultElevation: DpProto? = null,
    pressedElevation: DpProto? = null,
    focusedElevation: DpProto? = null,
    hoveredElevation: DpProto? = null,
    draggedElevation: DpProto? = null,
    disabledElevation: DpProto? = null,
): CardElevationProto =
    CardElevationProto(
        default_elevation = defaultElevation,
        pressed_elevation = pressedElevation,
        focused_elevation = focusedElevation,
        hovered_elevation = hoveredElevation,
        dragged_elevation = draggedElevation,
        disabled_elevation = disabledElevation,
    )

public fun PodcaTopAppBarColors(
    containerColor: ColorProto? = null,
    scrolledContainerColor: ColorProto? = null,
    navigationIconContentColor: ColorProto? = null,
    titleContentColor: ColorProto? = null,
    actionIconContentColor: ColorProto? = null,
    subtitleContentColor: ColorProto? = null,
): TopAppBarColorsProto =
    TopAppBarColorsProto(
        container_color = containerColor,
        scrolled_container_color = scrolledContainerColor,
        navigation_icon_content_color = navigationIconContentColor,
        title_content_color = titleContentColor,
        action_icon_content_color = actionIconContentColor,
        subtitle_content_color = subtitleContentColor,
    )

public fun PodcaTopAppBarState(
    heightOffsetLimit: Float = 0f,
    heightOffset: Float = 0f,
    contentOffset: Float = 0f,
    collapsedFraction: Float = 0f,
    overlappedFraction: Float = 0f,
): TopAppBarStateProto =
    TopAppBarStateProto(
        height_offset_limit = heightOffsetLimit,
        height_offset = heightOffset,
        content_offset = contentOffset,
        collapsed_fraction = collapsedFraction,
        overlapped_fraction = overlappedFraction,
    )

public fun PodcaBottomAppBarState(
    heightOffsetLimit: Float = 0f,
    heightOffset: Float = 0f,
    contentOffset: Float = 0f,
    collapsedFraction: Float = 0f,
): BottomAppBarStateProto =
    BottomAppBarStateProto(
        height_offset_limit = heightOffsetLimit,
        height_offset = heightOffset,
        content_offset = contentOffset,
        collapsed_fraction = collapsedFraction,
    )

public fun PodcaBottomAppBarScrollBehavior(
    state: BottomAppBarStateProto? = null,
    canScroll: Boolean = true,
    isPinned: Boolean = false,
): BottomAppBarScrollBehaviorProto =
    BottomAppBarScrollBehaviorProto(
        state = state,
        can_scroll = canScroll,
        is_pinned = isPinned,
    )

public fun PodcaAppBarClickableItem(
    contentDescription: String = "",
    enabled: Boolean = true,
    visibleInOverflowMenu: Boolean = false,
): AppBarClickableItemProto =
    AppBarClickableItemProto(
        content_description = contentDescription,
        enabled = enabled,
        visible_in_overflow_menu = visibleInOverflowMenu,
    )

public fun PodcaAppBarToggleableItem(
    contentDescription: String = "",
    checked: Boolean = false,
    enabled: Boolean = true,
    visibleInOverflowMenu: Boolean = false,
): AppBarToggleableItemProto =
    AppBarToggleableItemProto(
        content_description = contentDescription,
        checked = checked,
        enabled = enabled,
        visible_in_overflow_menu = visibleInOverflowMenu,
    )

public fun PodcaAppBarCustomItem(
    visibleInOverflowMenu: Boolean = false,
): AppBarCustomItemProto =
    AppBarCustomItemProto(
        visible_in_overflow_menu = visibleInOverflowMenu,
    )

public fun PodcaAppBarScope(
    clickableItems: List<AppBarClickableItemProto> = emptyList(),
    toggleableItems: List<AppBarToggleableItemProto> = emptyList(),
    customItems: List<AppBarCustomItemProto> = emptyList(),
): AppBarScopeProto =
    AppBarScopeProto(
        clickable_items = clickableItems,
        toggleable_items = toggleableItems,
        custom_items = customItems,
    )

public fun PodcaAppBarColumnScope(
    scope: AppBarScopeProto? = null,
): AppBarColumnScopeProto =
    AppBarColumnScopeProto(
        scope = scope,
    )

public fun PodcaAppBarRowScope(
    scope: AppBarScopeProto? = null,
): AppBarRowScopeProto =
    AppBarRowScopeProto(
        scope = scope,
    )

public fun PodcaAppBarMenuState(
    expanded: Boolean = false,
): AppBarMenuStateProto =
    AppBarMenuStateProto(
        expanded = expanded,
    )

public fun PodcaSliderColors(
    thumbColor: ColorProto? = null,
    activeTrackColor: ColorProto? = null,
    activeTickColor: ColorProto? = null,
    inactiveTrackColor: ColorProto? = null,
    inactiveTickColor: ColorProto? = null,
    disabledThumbColor: ColorProto? = null,
    disabledActiveTrackColor: ColorProto? = null,
    disabledActiveTickColor: ColorProto? = null,
    disabledInactiveTrackColor: ColorProto? = null,
    disabledInactiveTickColor: ColorProto? = null,
): SliderColorsProto =
    SliderColorsProto(
        thumb_color = thumbColor,
        active_track_color = activeTrackColor,
        active_tick_color = activeTickColor,
        inactive_track_color = inactiveTrackColor,
        inactive_tick_color = inactiveTickColor,
        disabled_thumb_color = disabledThumbColor,
        disabled_active_track_color = disabledActiveTrackColor,
        disabled_active_tick_color = disabledActiveTickColor,
        disabled_inactive_track_color = disabledInactiveTrackColor,
        disabled_inactive_tick_color = disabledInactiveTickColor,
    )

public fun PodcaSliderPositions(
    activeRangeStart: Float = 0f,
    activeRangeEnd: Float = 0f,
    tickFractions: List<Float> = emptyList(),
): SliderPositionsProto =
    SliderPositionsProto(
        active_range_start = activeRangeStart,
        active_range_end = activeRangeEnd,
        tick_fractions = tickFractions,
    )

public fun PodcaSliderState(
    value: Float = 0f,
    steps: Int = 0,
    valueRangeStart: Float = 0f,
    valueRangeEnd: Float = 1f,
    shouldAutoSnap: Boolean = true,
    coercedValueAsFraction: Float = 0f,
    isDragging: Boolean = false,
): SliderStateProto =
    SliderStateProto(
        value_ = value,
        steps = steps,
        value_range_start = valueRangeStart,
        value_range_end = valueRangeEnd,
        should_auto_snap = shouldAutoSnap,
        coerced_value_as_fraction = coercedValueAsFraction,
        is_dragging = isDragging,
    )

public fun PodcaRangeSliderState(
    activeRangeStart: Float = 0f,
    activeRangeEnd: Float = 1f,
    steps: Int = 0,
    valueRangeStart: Float = 0f,
    valueRangeEnd: Float = 1f,
    isDragging: Boolean = false,
    isRtl: Boolean = false,
): RangeSliderStateProto =
    RangeSliderStateProto(
        active_range_start = activeRangeStart,
        active_range_end = activeRangeEnd,
        steps = steps,
        value_range_start = valueRangeStart,
        value_range_end = valueRangeEnd,
        is_dragging = isDragging,
        is_rtl = isRtl,
    )

public fun PodcaSnackbarVisuals(
    message: String,
    actionLabel: String = "",
    withDismissAction: Boolean = false,
    duration: SnackbarDurationProto = SnackbarDurationProto.SNACKBAR_DURATION_SHORT,
): SnackbarVisualsProto =
    SnackbarVisualsProto(
        message = message,
        action_label = actionLabel,
        with_dismiss_action = withDismissAction,
        duration = duration,
    )

public fun PodcaSnackbarData(
    visuals: SnackbarVisualsProto? = null,
): SnackbarDataProto =
    SnackbarDataProto(
        visuals = visuals,
    )

public fun PodcaSnackbarHostState(
    currentSnackbarData: SnackbarDataProto? = null,
): SnackbarHostStateProto =
    SnackbarHostStateProto(
        current_snackbar_data = currentSnackbarData,
    )
