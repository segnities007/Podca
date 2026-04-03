@file:OptIn(androidx.compose.material3.ExperimentalMaterial3Api::class)

package com.podca.sdui.player.ui.material3

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.AssistChip
import androidx.compose.material3.Badge
import androidx.compose.material3.BadgedBox
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.BottomSheetScaffold
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Checkbox
import androidx.compose.material3.CheckboxDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.DatePicker
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.FloatingActionButtonDefaults
import androidx.compose.material3.ListItem
import androidx.compose.material3.ListItemDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.LeadingIconTab
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationRail
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.PrimaryTabRow
import androidx.compose.material3.RadioButton
import androidx.compose.material3.RadioButtonDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Slider
import androidx.compose.material3.Snackbar
import androidx.compose.material3.Surface
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Tab
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.material3.VerticalDivider
import androidx.compose.material3.rememberBottomSheetScaffoldState
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.TextUnit
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.podca.sdui.player.engine.PodcaDocumentNode
import com.podca.sdui.player.engine.PodcaDoubleActionArgument
import com.podca.sdui.player.engine.PodcaRuntime
import com.podca.sdui.player.engine.PodcaStringActionArgument
import com.podca.sdui.player.ui.core.PodcaRenderChildren
import com.podca.sdui.player.ui.core.PodcaRenderSlotChildren
import com.podca.sdui.player.ui.core.hasSlot
import com.podca.sdui.protocol.core.ActionBindingProto
import com.podca.sdui.protocol.core.ActionTriggerProto
import com.podca.sdui.protocol.material3.AlertDialogProto
import com.podca.sdui.protocol.material3.BadgeProto
import com.podca.sdui.protocol.material3.BadgedBoxProto
import com.podca.sdui.protocol.material3.BasicAlertDialogProto
import com.podca.sdui.protocol.material3.ButtonProto
import com.podca.sdui.protocol.material3.CardProto
import com.podca.sdui.protocol.material3.CheckboxProto
import com.podca.sdui.protocol.material3.ChipProto
import com.podca.sdui.protocol.material3.CircularProgressIndicatorProto
import com.podca.sdui.protocol.material3.DateInputProto
import com.podca.sdui.protocol.material3.DatePickerProto
import com.podca.sdui.protocol.material3.DatePickerDialogProto
import com.podca.sdui.protocol.material3.DateRangeInputProto
import com.podca.sdui.protocol.material3.ElevatedButtonProto
import com.podca.sdui.protocol.material3.ElevatedCardProto
import com.podca.sdui.protocol.material3.FilledTonalButtonProto
import com.podca.sdui.protocol.material3.FloatingActionButtonProto
import com.podca.sdui.protocol.material3.BottomSheetScaffoldProto
import com.podca.sdui.protocol.material3.HorizontalDividerProto
import com.podca.sdui.protocol.material3.IconButtonProto
import com.podca.sdui.protocol.material3.IconProto
import com.podca.sdui.protocol.material3.LabelProto
import com.podca.sdui.protocol.material3.LeadingIconTabProto
import com.podca.sdui.protocol.material3.ListItemProto
import com.podca.sdui.protocol.material3.LinearProgressIndicatorProto
import com.podca.sdui.protocol.material3.MaterialTextProto
import com.podca.sdui.protocol.material3.MaterialThemeProto
import com.podca.sdui.protocol.material3.MenuProto
import com.podca.sdui.protocol.material3.NavigationBarProto
import com.podca.sdui.protocol.material3.NavigationDrawerProto
import com.podca.sdui.protocol.material3.NavigationRailProto
import com.podca.sdui.protocol.material3.OutlinedButtonProto
import com.podca.sdui.protocol.material3.OutlinedCardProto
import com.podca.sdui.protocol.material3.OutlinedSecureTextFieldProto
import com.podca.sdui.protocol.material3.OutlinedTextFieldProto
import com.podca.sdui.protocol.material3.RadioButtonProto
import com.podca.sdui.protocol.material3.ScaffoldProto
import com.podca.sdui.protocol.material3.SearchBarProto
import com.podca.sdui.protocol.material3.SegmentedButtonProto
import com.podca.sdui.protocol.material3.SecureTextFieldProto
import com.podca.sdui.protocol.material3.SliderProto
import com.podca.sdui.protocol.material3.SnackbarHostProto
import com.podca.sdui.protocol.material3.SnackbarProto
import com.podca.sdui.protocol.material3.SurfaceProto
import com.podca.sdui.protocol.material3.SwitchProto
import com.podca.sdui.protocol.material3.TabProto
import com.podca.sdui.protocol.material3.TabRowProto
import com.podca.sdui.protocol.material3.TextButtonProto
import com.podca.sdui.protocol.material3.TextFieldProto
import com.podca.sdui.protocol.material3.TimePickerProto
import com.podca.sdui.protocol.material3.TooltipProto
import com.podca.sdui.protocol.material3.VerticalDividerProto
import com.podca.sdui.protocol.material3.WideNavigationRailProto
import com.podca.sdui.protocol.material3.BorderStrokeProto
import com.podca.sdui.protocol.material3.ButtonElevationProto
import com.podca.sdui.protocol.material3.CardElevationProto
import com.podca.sdui.protocol.material3.SnackbarDataProto
import com.podca.sdui.protocol.material3.SnackbarVisualsProto
import com.podca.sdui.protocol.ui.graphics.ColorProto
import com.podca.sdui.protocol.ui.graphics.ShapeProto
import com.podca.sdui.protocol.ui.modifier.ModifierProto
import com.podca.sdui.protocol.ui.text.TextOverflowProto
import com.podca.sdui.protocol.ui.text.font.FontStyleProto
import com.podca.sdui.protocol.ui.text.font.FontWeightProto
import com.podca.sdui.protocol.ui.unit.DpProto
import com.podca.sdui.protocol.ui.unit.TextUnitProto
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch

@Composable
public fun PodcaRenderMaterial3Node(
    node: PodcaDocumentNode,
    runtime: PodcaRuntime,
    renderChild: @Composable (PodcaDocumentNode) -> Unit,
) {
    val scope = rememberCoroutineScope()

    when (node.type) {
        "material3.Text" -> {
            val proto = runCatching { MaterialTextProto.ADAPTER.decode(node.payload) }.getOrElse { MaterialTextProto() }
            Text(
                text = proto.text ?: proto.annotated_string?.text.orEmpty(),
                modifier = proto.modifier.toComposeModifier(),
                color = proto.color.toComposeColorOrNull() ?: Color.Unspecified,
                style = proto.toComposeTextStyle(),
                softWrap = proto.soft_wrap,
                maxLines = if (proto.max_lines > 0) proto.max_lines else Int.MAX_VALUE,
                minLines = if (proto.min_lines > 0) proto.min_lines else 1,
                overflow = proto.overflow.toComposeTextOverflowOrNull() ?: TextOverflow.Clip,
            )
        }

        "material3.Icon" -> {
            val proto = runCatching { IconProto.ADAPTER.decode(node.payload) }.getOrElse { IconProto() }
            val sourceLabel = when {
                proto.image_vector_name?.isNotBlank() == true -> proto.image_vector_name.orEmpty()
                proto.painter_name?.isNotBlank() == true -> proto.painter_name.orEmpty()
                proto.content_description.isNotBlank() -> proto.content_description
                else -> "Icon"
            }
            Text(
                text = sourceLabel,
                modifier = proto.modifier.toComposeModifier(),
            )
        }

        "material3.Button" -> {
            val proto = runCatching { ButtonProto.ADAPTER.decode(node.payload) }.getOrElse { ButtonProto(enabled = true) }
            Button(
                onClick = { dispatchByKey(scope, runtime, node.key) },
                modifier = proto.modifier.toComposeModifier(),
                enabled = proto.enabled,
                shape = proto.shape.toComposeShapeOrNull() ?: MaterialTheme.shapes.small,
                colors = proto.colors.toComposeButtonColors(),
                elevation = proto.elevation.toComposeButtonElevation(),
                contentPadding = proto.content_padding.toComposePaddingValues(),
            ) {
                PodcaRenderChildren(node = node, renderChild = renderChild)
            }
        }

        "material3.ElevatedButton" -> {
            val proto = runCatching { ElevatedButtonProto.ADAPTER.decode(node.payload) }.getOrElse { ElevatedButtonProto(enabled = true) }
            ElevatedButton(
                onClick = { dispatchByKey(scope, runtime, node.key) },
                modifier = proto.modifier.toComposeModifier(),
                enabled = proto.enabled,
                shape = proto.shape.toComposeShapeOrNull() ?: MaterialTheme.shapes.small,
                colors = proto.colors.toComposeButtonColors(),
                elevation = proto.elevation.toComposeButtonElevation(),
                contentPadding = proto.content_padding.toComposePaddingValues(),
            ) {
                PodcaRenderChildren(node = node, renderChild = renderChild)
            }
        }

        "material3.FilledTonalButton" -> {
            val proto = runCatching { FilledTonalButtonProto.ADAPTER.decode(node.payload) }.getOrElse { FilledTonalButtonProto(enabled = true) }
            FilledTonalButton(
                onClick = { dispatchByKey(scope, runtime, node.key) },
                modifier = proto.modifier.toComposeModifier(),
                enabled = proto.enabled,
                shape = proto.shape.toComposeShapeOrNull() ?: MaterialTheme.shapes.small,
                colors = proto.colors.toComposeButtonColors(),
                elevation = proto.elevation.toComposeButtonElevation(),
                contentPadding = proto.content_padding.toComposePaddingValues(),
            ) {
                PodcaRenderChildren(node = node, renderChild = renderChild)
            }
        }

        "material3.FloatingActionButton" -> {
            val proto = runCatching { FloatingActionButtonProto.ADAPTER.decode(node.payload) }.getOrElse { FloatingActionButtonProto(enabled = true) }
            FloatingActionButton(
                onClick = {
                    if (proto.enabled) {
                        dispatchNodeAction(scope, runtime, node, ActionTriggerProto.ACTION_TRIGGER_CLICK)
                    }
                },
                modifier = proto.modifier.toComposeModifier(),
                elevation = FloatingActionButtonDefaults.elevation(),
            ) {
                if (proto.has_content) {
                    PodcaRenderChildren(node = node, renderChild = renderChild)
                }
            }
        }

        "material3.OutlinedButton" -> {
            val proto = runCatching { OutlinedButtonProto.ADAPTER.decode(node.payload) }.getOrElse { OutlinedButtonProto(enabled = true) }
            OutlinedButton(
                onClick = { dispatchByKey(scope, runtime, node.key) },
                modifier = proto.modifier.toComposeModifier(),
                enabled = proto.enabled,
                shape = proto.shape.toComposeShapeOrNull() ?: MaterialTheme.shapes.small,
                colors = proto.colors.toComposeButtonColors(),
                elevation = proto.elevation.toComposeButtonElevation(),
                border = proto.border.toComposeBorderStrokeOrNull(),
                contentPadding = proto.content_padding.toComposePaddingValues(),
            ) {
                PodcaRenderChildren(node = node, renderChild = renderChild)
            }
        }

        "material3.TextButton" -> {
            val proto = runCatching { TextButtonProto.ADAPTER.decode(node.payload) }.getOrElse { TextButtonProto(enabled = true) }
            TextButton(
                onClick = { dispatchByKey(scope, runtime, node.key) },
                modifier = proto.modifier.toComposeModifier(),
                enabled = proto.enabled,
                shape = proto.shape.toComposeShapeOrNull() ?: MaterialTheme.shapes.small,
                colors = proto.colors.toComposeButtonColors(),
                contentPadding = proto.content_padding.toComposePaddingValues(),
            ) {
                PodcaRenderChildren(node = node, renderChild = renderChild)
            }
        }

        "material3.IconButton" -> {
            val proto = runCatching { IconButtonProto.ADAPTER.decode(node.payload) }.getOrElse { IconButtonProto(enabled = true) }
            IconButton(
                onClick = { dispatchByKey(scope, runtime, node.key) },
                modifier = proto.modifier.toComposeModifier(),
                enabled = proto.enabled,
                colors = IconButtonDefaults.iconButtonColors(),
            ) {
                PodcaRenderChildren(node = node, renderChild = renderChild)
            }
        }

        "material3.Checkbox" -> {
            val proto = runCatching { CheckboxProto.ADAPTER.decode(node.payload) }.getOrElse { CheckboxProto(enabled = true) }
            Checkbox(
                checked = proto.checked,
                onCheckedChange = { dispatchByKey(scope, runtime, node.key) },
                modifier = proto.modifier.toComposeModifier(),
                enabled = proto.enabled,
                colors = CheckboxDefaults.colors(),
            )
        }

        "material3.RadioButton" -> {
            val proto = runCatching { RadioButtonProto.ADAPTER.decode(node.payload) }.getOrElse { RadioButtonProto(enabled = true) }
            RadioButton(
                selected = proto.selected,
                onClick = { dispatchByKey(scope, runtime, node.key) },
                modifier = proto.modifier.toComposeModifier(),
                enabled = proto.enabled,
                colors = RadioButtonDefaults.colors(),
            )
        }

        "material3.Switch" -> {
            val proto = runCatching { SwitchProto.ADAPTER.decode(node.payload) }.getOrElse { SwitchProto(enabled = true) }
            Switch(
                checked = proto.checked,
                onCheckedChange = { dispatchByKey(scope, runtime, node.key) },
                modifier = proto.modifier.toComposeModifier(),
                enabled = proto.enabled,
                thumbContent = if (proto.has_thumb_content) {
                    { PodcaRenderChildren(node = node, renderChild = renderChild) }
                } else {
                    null
                },
                colors = SwitchDefaults.colors(),
            )
        }

        "material3.Chip" -> {
            val proto = runCatching { ChipProto.ADAPTER.decode(node.payload) }.getOrElse { ChipProto(enabled = true) }
            AssistChip(
                onClick = { dispatchByKey(scope, runtime, node.key) },
                label = { Text(text = proto.label) },
                modifier = proto.modifier.toComposeModifier(),
                enabled = proto.enabled,
                leadingIcon = if (proto.has_leading_icon && node.hasSlot("leadingIcon")) {
                    {
                        PodcaRenderSlotChildren(
                            node = node,
                            slot = "leadingIcon",
                            renderChild = renderChild,
                        )
                    }
                } else {
                    null
                },
                trailingIcon = if (proto.has_trailing_icon && node.hasSlot("trailingIcon")) {
                    {
                        PodcaRenderSlotChildren(
                            node = node,
                            slot = "trailingIcon",
                            renderChild = renderChild,
                        )
                    }
                } else {
                    null
                },
            )
        }

        "material3.Slider" -> {
            val proto = runCatching { SliderProto.ADAPTER.decode(node.payload) }.getOrElse { SliderProto(enabled = true) }
            var value by remember(node.key, proto.value_) {
                mutableStateOf(proto.value_)
            }
            Slider(
                value = value,
                onValueChange = { newValue ->
                    value = newValue
                    if (node.key.isNotBlank()) {
                        scope.launch {
                            runtime.dispatch(
                                nodeKey = node.key,
                                trigger = ActionTriggerProto.ACTION_TRIGGER_VALUE_CHANGE,
                                arguments = listOf(
                                    PodcaDoubleActionArgument(
                                        name = "value",
                                        value = newValue.toDouble(),
                                    ),
                                ),
                            )
                        }
                    }
                },
                modifier = proto.modifier.toComposeModifier(),
                enabled = proto.enabled,
                valueRange = proto.value_range_start..proto.value_range_end,
                steps = proto.steps,
            )
        }

        "material3.Scaffold" -> {
            val proto = runCatching { ScaffoldProto.ADAPTER.decode(node.payload) }.getOrElse { ScaffoldProto() }
            Scaffold(
                modifier = proto.modifier.toComposeModifier(),
                topBar = {
                    PodcaRenderSlotChildren(
                        node = node,
                        slot = "topBar",
                        renderChild = renderChild,
                    )
                },
                bottomBar = {
                    PodcaRenderSlotChildren(
                        node = node,
                        slot = "bottomBar",
                        renderChild = renderChild,
                    )
                },
                snackbarHost = {
                    PodcaRenderSlotChildren(
                        node = node,
                        slot = "snackbarHost",
                        renderChild = renderChild,
                    )
                },
                floatingActionButton = {
                    PodcaRenderSlotChildren(
                        node = node,
                        slot = "floatingActionButton",
                        renderChild = renderChild,
                    )
                },
                floatingActionButtonPosition = when (proto.floating_action_button_position.name) {
                    "FAB_POSITION_CENTER" -> FabPosition.Center
                    else -> FabPosition.End
                },
            ) {
                Column {
                    if (node.hasSlot("content")) {
                        PodcaRenderSlotChildren(
                            node = node,
                            slot = "content",
                            renderChild = renderChild,
                        )
                    } else {
                        PodcaRenderChildren(node = node, renderChild = renderChild)
                    }
                }
            }
        }

        "material3.ListItem" -> {
            val proto = runCatching { ListItemProto.ADAPTER.decode(node.payload) }.getOrElse { ListItemProto(enabled = true) }
            ListItem(
                headlineContent = { Text(text = proto.headline) },
                supportingContent = proto.supporting.takeIf { it.isNotBlank() }?.let { supporting ->
                    { Text(text = supporting) }
                },
                overlineContent = proto.overline.takeIf { it.isNotBlank() }?.let { overline ->
                    { Text(text = overline) }
                },
                leadingContent = if (proto.has_leading_icon && node.hasSlot("leadingContent")) {
                    {
                        PodcaRenderSlotChildren(
                            node = node,
                            slot = "leadingContent",
                            renderChild = renderChild,
                        )
                    }
                } else {
                    null
                },
                trailingContent = if (proto.has_trailing_icon && node.hasSlot("trailingContent")) {
                    {
                        PodcaRenderSlotChildren(
                            node = node,
                            slot = "trailingContent",
                            renderChild = renderChild,
                        )
                    }
                } else {
                    null
                },
                modifier = proto.modifier.toComposeModifier().then(
                    Modifier.clickable(enabled = node.key.isNotBlank() && proto.enabled) {
                        dispatchByKey(scope, runtime, node.key)
                    },
                ),
                colors = ListItemDefaults.colors(),
            )
        }

        "material3.Menu" -> {
            val proto = runCatching { MenuProto.ADAPTER.decode(node.payload) }.getOrElse { MenuProto() }
            if (proto.expanded) {
                Column(modifier = proto.modifier.toComposeModifier()) {
                    PodcaRenderChildren(node = node, renderChild = renderChild)
                }
            }
        }

        "material3.NavigationBar" -> {
            val proto = runCatching { NavigationBarProto.ADAPTER.decode(node.payload) }.getOrElse { NavigationBarProto() }
            NavigationBar(modifier = proto.modifier.toComposeModifier()) {
                PodcaRenderChildren(node = node, renderChild = renderChild)
            }
        }

        "material3.NavigationRail" -> {
            val proto = runCatching { NavigationRailProto.ADAPTER.decode(node.payload) }.getOrElse { NavigationRailProto() }
            NavigationRail(modifier = proto.modifier.toComposeModifier()) {
                PodcaRenderChildren(node = node, renderChild = renderChild)
            }
        }

        "material3.WideNavigationRail" -> {
            val proto = runCatching { WideNavigationRailProto.ADAPTER.decode(node.payload) }.getOrElse { WideNavigationRailProto() }
            NavigationRail(
                modifier = proto.modifier.toComposeModifier(),
                header = if (node.hasSlot("header")) {
                    {
                        PodcaRenderSlotChildren(
                            node = node,
                            slot = "header",
                            renderChild = renderChild,
                        )
                    }
                } else {
                    null
                },
            ) {
                PodcaRenderChildren(node = node, renderChild = renderChild)
            }
        }

        "material3.NavigationDrawer" -> {
            val proto = runCatching { NavigationDrawerProto.ADAPTER.decode(node.payload) }.getOrElse { NavigationDrawerProto() }
            if (proto.value_.name == "DRAWER_VALUE_OPEN") {
                Surface(modifier = proto.modifier.toComposeModifier()) {
                    Column {
                        PodcaRenderChildren(node = node, renderChild = renderChild)
                    }
                }
            }
        }

        "material3.SearchBar" -> {
            val proto = runCatching { SearchBarProto.ADAPTER.decode(node.payload) }.getOrElse { SearchBarProto(enabled = true) }
            var value by remember(node.key, proto.text) {
                mutableStateOf(proto.text)
            }
            Column(modifier = proto.modifier.toComposeModifier()) {
                OutlinedTextField(
                    value = value,
                    onValueChange = { newValue ->
                        value = newValue
                        if (node.key.isNotBlank()) {
                            scope.launch {
                                runtime.dispatch(
                                    nodeKey = node.key,
                                    trigger = ActionTriggerProto.ACTION_TRIGGER_VALUE_CHANGE,
                                    arguments = listOf(
                                        PodcaStringActionArgument(
                                            name = "text",
                                            value = newValue,
                                        ),
                                    ),
                                )
                            }
                        }
                    },
                    enabled = proto.enabled,
                )
                if (proto.value_.name == "SEARCH_BAR_VALUE_EXPANDED") {
                    PodcaRenderChildren(node = node, renderChild = renderChild)
                }
            }
        }

        "material3.SegmentedButton" -> {
            val proto = runCatching { SegmentedButtonProto.ADAPTER.decode(node.payload) }.getOrElse { SegmentedButtonProto(enabled = true) }
            TextButton(
                onClick = { dispatchByKey(scope, runtime, node.key) },
                enabled = proto.enabled,
                modifier = proto.modifier.toComposeModifier(),
            ) {
                Text(text = if (proto.selected) "[${proto.text}]" else proto.text)
                PodcaRenderChildren(node = node, renderChild = renderChild)
            }
        }

        "material3.TimePicker" -> {
            val proto = runCatching { TimePickerProto.ADAPTER.decode(node.payload) }.getOrElse { TimePickerProto(enabled = true) }
            Column(modifier = proto.modifier.toComposeModifier()) {
                Text(
                    text = "TimePicker %02d:%02d".format(
                        proto.selected_hour.coerceIn(0, 23),
                        proto.selected_minute.coerceIn(0, 59),
                    ),
                )
                PodcaRenderChildren(node = node, renderChild = renderChild)
            }
        }

        "material3.Tooltip" -> {
            val proto = runCatching { TooltipProto.ADAPTER.decode(node.payload) }.getOrElse { TooltipProto() }
            Surface(modifier = proto.modifier.toComposeModifier()) {
                Column {
                    Text(text = proto.message)
                    PodcaRenderChildren(node = node, renderChild = renderChild)
                }
            }
        }

        "material3.Card" -> {
            val proto = runCatching { CardProto.ADAPTER.decode(node.payload) }.getOrElse { CardProto(enabled = true) }
            Card(
                modifier = proto.modifier.toComposeModifier().then(
                    Modifier.clickable(enabled = node.key.isNotBlank() && proto.enabled) {
                        dispatchByKey(scope, runtime, node.key)
                    },
                ),
                shape = proto.shape.toComposeShapeOrNull() ?: MaterialTheme.shapes.medium,
                colors = proto.colors.toComposeCardColors(),
                elevation = proto.elevation.toComposeCardElevation(),
                border = proto.border.toComposeBorderStrokeOrNull(),
            ) {
                PodcaRenderChildren(node = node, renderChild = renderChild)
            }
        }

        "material3.ElevatedCard" -> {
            val proto = runCatching { ElevatedCardProto.ADAPTER.decode(node.payload) }.getOrElse { ElevatedCardProto(enabled = true) }
            ElevatedCard(
                modifier = proto.modifier.toComposeModifier().then(
                    Modifier.clickable(enabled = node.key.isNotBlank() && proto.enabled) {
                        dispatchByKey(scope, runtime, node.key)
                    },
                ),
                shape = proto.shape.toComposeShapeOrNull() ?: MaterialTheme.shapes.medium,
                colors = proto.colors.toComposeCardColors(),
                elevation = proto.elevation.toComposeCardElevation(),
            ) {
                PodcaRenderChildren(node = node, renderChild = renderChild)
            }
        }

        "material3.OutlinedCard" -> {
            val proto = runCatching { OutlinedCardProto.ADAPTER.decode(node.payload) }.getOrElse { OutlinedCardProto(enabled = true) }
            OutlinedCard(
                modifier = proto.modifier.toComposeModifier().then(
                    Modifier.clickable(enabled = node.key.isNotBlank() && proto.enabled) {
                        dispatchByKey(scope, runtime, node.key)
                    },
                ),
                shape = proto.shape.toComposeShapeOrNull() ?: MaterialTheme.shapes.medium,
                colors = proto.colors.toComposeCardColors(),
                border = proto.border.toComposeBorderStrokeOrNull(),
            ) {
                PodcaRenderChildren(node = node, renderChild = renderChild)
            }
        }

        "material3.Surface" -> {
            val proto = runCatching { SurfaceProto.ADAPTER.decode(node.payload) }.getOrElse { SurfaceProto() }
            Surface(
                modifier = proto.modifier.toComposeModifier(),
                shape = proto.shape.toComposeShapeOrNull() ?: RoundedCornerShape(0.dp),
                color = proto.color.toComposeColorOrNull() ?: MaterialTheme.colorScheme.surface,
                contentColor = proto.content_color.toComposeColorOrNull() ?: MaterialTheme.colorScheme.onSurface,
                tonalElevation = proto.tonal_elevation.toComposeDp(default = 0.dp),
                shadowElevation = proto.shadow_elevation.toComposeDp(default = 0.dp),
                border = proto.border.toComposeBorderStrokeOrNull(),
            ) {
                PodcaRenderChildren(node = node, renderChild = renderChild)
            }
        }

        "material3.TextField" -> {
            val proto = runCatching { TextFieldProto.ADAPTER.decode(node.payload) }.getOrElse { TextFieldProto() }
            PodcaRenderTextField(
                node = node,
                runtime = runtime,
                scope = scope,
                renderChild = renderChild,
                text = proto.state?.text.orEmpty(),
                modifier = proto.modifier.toComposeModifier(),
                enabled = proto.enabled,
                readOnly = proto.read_only,
                label = proto.label,
                placeholder = proto.placeholder,
                prefix = proto.prefix,
                suffix = proto.suffix,
                supportingText = proto.supporting_text,
                isError = proto.is_error,
                lineLimits = proto.line_limits,
                style = proto.text_style.toComposeTextStyle(),
                colors = proto.colors,
                secure = false,
                revealed = true,
            )
        }

        "material3.OutlinedTextField" -> {
            val proto = runCatching { OutlinedTextFieldProto.ADAPTER.decode(node.payload) }.getOrElse { OutlinedTextFieldProto() }
            val field = proto.field_ ?: TextFieldProto()
            PodcaRenderTextField(
                node = node,
                runtime = runtime,
                scope = scope,
                renderChild = renderChild,
                text = field.state?.text.orEmpty(),
                modifier = field.modifier.toComposeModifier(),
                enabled = field.enabled,
                readOnly = field.read_only,
                label = field.label,
                placeholder = field.placeholder,
                prefix = field.prefix,
                suffix = field.suffix,
                supportingText = field.supporting_text,
                isError = field.is_error,
                lineLimits = field.line_limits,
                style = field.text_style.toComposeTextStyle(),
                colors = field.colors,
                secure = false,
                revealed = true,
                outlined = true,
            )
        }

        "material3.SecureTextField" -> {
            val proto = runCatching { SecureTextFieldProto.ADAPTER.decode(node.payload) }.getOrElse { SecureTextFieldProto() }
            val field = proto.field_ ?: TextFieldProto()
            PodcaRenderTextField(
                node = node,
                runtime = runtime,
                scope = scope,
                renderChild = renderChild,
                text = field.state?.text.orEmpty(),
                modifier = field.modifier.toComposeModifier(),
                enabled = field.enabled,
                readOnly = field.read_only,
                label = field.label,
                placeholder = field.placeholder,
                prefix = field.prefix,
                suffix = field.suffix,
                supportingText = field.supporting_text,
                isError = field.is_error,
                lineLimits = field.line_limits,
                style = field.text_style.toComposeTextStyle(),
                colors = field.colors,
                secure = true,
                revealed = proto.revealed,
            )
        }

        "material3.OutlinedSecureTextField" -> {
            val proto = runCatching { OutlinedSecureTextFieldProto.ADAPTER.decode(node.payload) }.getOrElse { OutlinedSecureTextFieldProto() }
            val field = proto.field_ ?: TextFieldProto()
            PodcaRenderTextField(
                node = node,
                runtime = runtime,
                scope = scope,
                renderChild = renderChild,
                text = field.state?.text.orEmpty(),
                modifier = field.modifier.toComposeModifier(),
                enabled = field.enabled,
                readOnly = field.read_only,
                label = field.label,
                placeholder = field.placeholder,
                prefix = field.prefix,
                suffix = field.suffix,
                supportingText = field.supporting_text,
                isError = field.is_error,
                lineLimits = field.line_limits,
                style = field.text_style.toComposeTextStyle(),
                colors = field.colors,
                secure = true,
                revealed = proto.revealed,
                outlined = true,
            )
        }

        "material3.Snackbar" -> {
            val proto = runCatching { SnackbarProto.ADAPTER.decode(node.payload) }.getOrElse { SnackbarProto() }
            val visuals = proto.snackbar_data?.visuals
            Snackbar(
                modifier = proto.modifier.toComposeModifier(),
                action = when {
                    node.hasSlot("action") -> {
                        {
                            PodcaRenderSlotChildren(
                                node = node,
                                slot = "action",
                                renderChild = renderChild,
                            )
                        }
                    }

                    proto.has_action && visuals?.action_label?.isNotBlank() == true -> {
                        val label = visuals.action_label
                        {
                            SnackbarActionButton(
                                label = label,
                                onClick = { dispatchNodeAction(scope, runtime, node, ActionTriggerProto.ACTION_TRIGGER_CLICK) },
                            )
                        }
                    }

                    else -> null
                },
                dismissAction = when {
                    node.hasSlot("dismissAction") -> {
                        {
                            PodcaRenderSlotChildren(
                                node = node,
                                slot = "dismissAction",
                                renderChild = renderChild,
                            )
                        }
                    }

                    proto.has_dismiss_action -> {
                        {
                            SnackbarActionButton(
                                label = "Dismiss",
                                onClick = { dispatchNodeAction(scope, runtime, node, ActionTriggerProto.ACTION_TRIGGER_DISMISS) },
                            )
                        }
                    }

                    else -> null
                },
                actionOnNewLine = proto.action_on_new_line,
                shape = proto.shape.toComposeShapeOrNull() ?: MaterialTheme.shapes.small,
                containerColor = proto.container_color.toComposeColorOrNull() ?: MaterialTheme.colorScheme.inverseSurface,
                contentColor = proto.content_color.toComposeColorOrNull() ?: MaterialTheme.colorScheme.inverseOnSurface,
                actionContentColor = proto.action_color.toComposeColorOrNull() ?: MaterialTheme.colorScheme.inversePrimary,
                dismissActionContentColor = proto.dismiss_action_content_color.toComposeColorOrNull()
                    ?: MaterialTheme.colorScheme.inverseOnSurface,
            ) {
                Text(text = visuals?.message ?: "Snackbar")
            }
        }

        "material3.SnackbarHost" -> {
            val proto = runCatching { SnackbarHostProto.ADAPTER.decode(node.payload) }.getOrElse { SnackbarHostProto() }
            Column(modifier = proto.modifier.toComposeModifier()) {
                val snackbar = proto.host_state?.current_snackbar_data?.visuals
                if (snackbar != null) {
                    Snackbar(
                        action = snackbar.action_label.takeIf { it.isNotBlank() }?.let { label ->
                            {
                                SnackbarActionButton(
                                    label = label,
                                    onClick = { dispatchNodeAction(scope, runtime, node, ActionTriggerProto.ACTION_TRIGGER_CLICK) },
                                )
                            }
                        },
                        dismissAction = if (snackbar.with_dismiss_action) {
                            {
                                SnackbarActionButton(
                                    label = "Dismiss",
                                    onClick = { dispatchNodeAction(scope, runtime, node, ActionTriggerProto.ACTION_TRIGGER_DISMISS) },
                                )
                            }
                        } else {
                            null
                        },
                    ) {
                        Text(text = snackbar.message)
                    }
                }
                if (proto.has_custom_snackbar) {
                    PodcaRenderChildren(node = node, renderChild = renderChild)
                }
            }
        }

        "material3.BottomSheetScaffold" -> {
            val proto = runCatching { BottomSheetScaffoldProto.ADAPTER.decode(node.payload) }.getOrElse { BottomSheetScaffoldProto() }
            BottomSheetScaffold(
                scaffoldState = rememberBottomSheetScaffoldState(),
                modifier = proto.modifier.toComposeModifier(),
                sheetPeekHeight = proto.sheet_peek_height.toComposeDp(default = 0.dp),
                sheetSwipeEnabled = proto.sheet_gestures_enabled,
                sheetContent = {
                    if (proto.has_sheet_content || node.hasSlot("sheetContent")) {
                        PodcaRenderSlotChildren(
                            node = node,
                            slot = "sheetContent",
                            renderChild = renderChild,
                        )
                    }
                },
            ) {
                if (proto.has_content || node.hasSlot("content")) {
                    PodcaRenderSlotChildren(
                        node = node,
                        slot = "content",
                        renderChild = renderChild,
                    )
                }
            }
        }

        "material3.BadgedBox" -> {
            val proto = runCatching { BadgedBoxProto.ADAPTER.decode(node.payload) }.getOrElse { BadgedBoxProto() }
            BadgedBox(
                modifier = proto.modifier.toComposeModifier(),
                badge = {
                    if (proto.has_badge) {
                        Badge {
                            if (node.hasSlot("badge")) {
                                PodcaRenderSlotChildren(
                                    node = node,
                                    slot = "badge",
                                    renderChild = renderChild,
                                )
                            }
                        }
                    }
                },
            ) {
                if (proto.has_content) {
                    PodcaRenderChildren(node = node, renderChild = renderChild)
                }
            }
        }

        "material3.Badge" -> {
            val proto = runCatching { BadgeProto.ADAPTER.decode(node.payload) }.getOrElse { BadgeProto() }
            Badge(
                modifier = proto.modifier.toComposeModifier(),
                containerColor = proto.container_color.toComposeColorOrNull() ?: MaterialTheme.colorScheme.primary,
                contentColor = proto.content_color.toComposeColorOrNull() ?: MaterialTheme.colorScheme.onPrimary,
            ) {
                if (proto.has_content) {
                    PodcaRenderChildren(node = node, renderChild = renderChild)
                }
            }
        }

        "material3.HorizontalDivider" -> {
            val proto = runCatching { HorizontalDividerProto.ADAPTER.decode(node.payload) }.getOrElse { HorizontalDividerProto() }
            HorizontalDivider(
                modifier = proto.modifier.toComposeModifier(),
                thickness = proto.thickness.toComposeDp(default = 1.dp),
                color = proto.color.toComposeColorOrNull() ?: MaterialTheme.colorScheme.outlineVariant,
            )
        }

        "material3.VerticalDivider" -> {
            val proto = runCatching { VerticalDividerProto.ADAPTER.decode(node.payload) }.getOrElse { VerticalDividerProto() }
            VerticalDivider(
                modifier = proto.modifier.toComposeModifier(),
                thickness = proto.thickness.toComposeDp(default = 1.dp),
                color = proto.color.toComposeColorOrNull() ?: MaterialTheme.colorScheme.outlineVariant,
            )
        }

        "material3.LinearProgressIndicator" -> {
            val proto = runCatching { LinearProgressIndicatorProto.ADAPTER.decode(node.payload) }.getOrElse { LinearProgressIndicatorProto() }
            LinearProgressIndicator(
                progress = { proto.progress.coerceIn(0f, 1f) },
                modifier = proto.modifier.toComposeModifier(),
                color = proto.color.toComposeColorOrNull() ?: MaterialTheme.colorScheme.primary,
                trackColor = proto.track_color.toComposeColorOrNull() ?: MaterialTheme.colorScheme.surfaceVariant,
            )
        }

        "material3.CircularProgressIndicator" -> {
            val proto = runCatching { CircularProgressIndicatorProto.ADAPTER.decode(node.payload) }.getOrElse { CircularProgressIndicatorProto() }
            CircularProgressIndicator(
                progress = { proto.progress.coerceIn(0f, 1f) },
                modifier = proto.modifier.toComposeModifier(),
                color = proto.color.toComposeColorOrNull() ?: MaterialTheme.colorScheme.primary,
                trackColor = proto.track_color.toComposeColorOrNull() ?: MaterialTheme.colorScheme.surfaceVariant,
            )
        }

        "material3.AlertDialog" -> {
            val proto = runCatching { AlertDialogProto.ADAPTER.decode(node.payload) }.getOrElse { AlertDialogProto() }
            AlertDialog(
                onDismissRequest = { dispatchNodeAction(scope, runtime, node, ActionTriggerProto.ACTION_TRIGGER_DISMISS) },
                confirmButton = {
                    if (node.hasSlot("confirmButton")) {
                        PodcaRenderSlotChildren(
                            node = node,
                            slot = "confirmButton",
                            renderChild = renderChild,
                        )
                    }
                },
                modifier = proto.modifier.toComposeModifier(),
                dismissButton = if (proto.has_dismiss_button) {
                    {
                        if (node.hasSlot("dismissButton")) {
                            PodcaRenderSlotChildren(
                                node = node,
                                slot = "dismissButton",
                                renderChild = renderChild,
                            )
                        }
                    }
                } else {
                    null
                },
                icon = if (proto.has_icon) {
                    {
                        if (node.hasSlot("icon")) {
                            PodcaRenderSlotChildren(
                                node = node,
                                slot = "icon",
                                renderChild = renderChild,
                            )
                        }
                    }
                } else {
                    null
                },
                title = if (proto.has_title) {
                    {
                        if (node.hasSlot("title")) {
                            PodcaRenderSlotChildren(
                                node = node,
                                slot = "title",
                                renderChild = renderChild,
                            )
                        }
                    }
                } else {
                    null
                },
                text = if (proto.has_text) {
                    {
                        if (node.hasSlot("text")) {
                            PodcaRenderSlotChildren(
                                node = node,
                                slot = "text",
                                renderChild = renderChild,
                            )
                        }
                    }
                } else {
                    null
                },
                shape = proto.shape.toComposeShapeOrNull() ?: MaterialTheme.shapes.large,
                containerColor = proto.container_color.toComposeColorOrNull() ?: MaterialTheme.colorScheme.surface,
                iconContentColor = proto.icon_content_color.toComposeColorOrNull() ?: MaterialTheme.colorScheme.primary,
                titleContentColor = proto.title_content_color.toComposeColorOrNull() ?: MaterialTheme.colorScheme.onSurface,
                textContentColor = proto.text_content_color.toComposeColorOrNull() ?: MaterialTheme.colorScheme.onSurfaceVariant,
                tonalElevation = proto.tonal_elevation.toComposeDp(default = 0.dp),
            )
        }

        "material3.BasicAlertDialog" -> {
            val proto = runCatching { BasicAlertDialogProto.ADAPTER.decode(node.payload) }.getOrElse { BasicAlertDialogProto() }
            BasicAlertDialog(
                onDismissRequest = { dispatchNodeAction(scope, runtime, node, ActionTriggerProto.ACTION_TRIGGER_DISMISS) },
                modifier = proto.modifier.toComposeModifier(),
            ) {
                Column {
                    if (proto.has_content) {
                        PodcaRenderChildren(node = node, renderChild = renderChild)
                    }
                }
            }
        }

        "material3.Tab" -> {
            val proto = runCatching { TabProto.ADAPTER.decode(node.payload) }.getOrElse { TabProto() }
            val tabIcon: (@Composable () -> Unit)? = if (proto.has_icon && node.hasSlot("icon")) {
                {
                    PodcaRenderSlotChildren(
                        node = node,
                        slot = "icon",
                        renderChild = renderChild,
                    )
                }
            } else {
                null
            }
            Tab(
                selected = proto.selected,
                enabled = proto.enabled,
                onClick = { dispatchByKey(scope, runtime, node.key) },
                modifier = proto.modifier.toComposeModifier(),
                text = { Text(text = proto.text.orEmpty()) },
                icon = tabIcon,
            )
        }

        "material3.LeadingIconTab" -> {
            val proto = runCatching { LeadingIconTabProto.ADAPTER.decode(node.payload) }.getOrElse { LeadingIconTabProto() }
            LeadingIconTab(
                selected = proto.selected,
                enabled = proto.enabled,
                onClick = { dispatchByKey(scope, runtime, node.key) },
                modifier = proto.modifier.toComposeModifier(),
                text = { Text(text = proto.text.orEmpty()) },
                icon = {
                    if (node.hasSlot("icon")) {
                        PodcaRenderSlotChildren(
                            node = node,
                            slot = "icon",
                            renderChild = renderChild,
                        )
                    }
                },
            )
        }

        "material3.TabRow" -> {
            val proto = runCatching { TabRowProto.ADAPTER.decode(node.payload) }.getOrElse { TabRowProto() }
            PrimaryTabRow(
                selectedTabIndex = proto.selected_tab_index,
                modifier = proto.modifier.toComposeModifier(),
            ) {
                PodcaRenderChildren(node = node, renderChild = renderChild)
            }
        }

        "material3.AppBarColumn" -> {
            Column {
                PodcaRenderChildren(node = node, renderChild = renderChild)
            }
        }

        "material3.AppBarRow" -> {
            Row {
                PodcaRenderChildren(node = node, renderChild = renderChild)
            }
        }

        "material3.MaterialTheme" -> {
            runCatching { MaterialThemeProto.ADAPTER.decode(node.payload) }.getOrElse { MaterialThemeProto() }
            MaterialTheme(
                colorScheme = MaterialTheme.colorScheme,
                typography = MaterialTheme.typography,
                shapes = MaterialTheme.shapes,
            ) {
                PodcaRenderChildren(node = node, renderChild = renderChild)
            }
        }

        "material3.Label" -> {
            val proto = runCatching { LabelProto.ADAPTER.decode(node.payload) }.getOrElse { LabelProto() }
            Text(
                text = proto.text,
                color = proto.color.toComposeColorOrNull() ?: MaterialTheme.colorScheme.onSurface,
                style = proto.style.toComposeTextStyle(),
            )
        }

        "material3.DateInput" -> {
            runCatching { DateInputProto.ADAPTER.decode(node.payload) }
            Column {
                resolveDateContent(node = node, placeholder = "DateInput").toComposable(
                    node = node,
                    renderChild = renderChild,
                )
            }
        }

        "material3.DatePicker" -> {
            val proto = runCatching { DatePickerProto.ADAPTER.decode(node.payload) }
                .getOrElse { DatePickerProto(show_mode_toggle = true) }
            DatePicker(
                state = rememberDatePickerState(),
                modifier = proto.modifier.toComposeModifier(),
                showModeToggle = proto.show_mode_toggle,
                title = if (node.hasSlot("title")) {
                    {
                        PodcaRenderSlotChildren(
                            node = node,
                            slot = "title",
                            renderChild = renderChild,
                        )
                    }
                } else {
                    null
                },
                headline = if (node.hasSlot("headline")) {
                    {
                        PodcaRenderSlotChildren(
                            node = node,
                            slot = "headline",
                            renderChild = renderChild,
                        )
                    }
                } else {
                    null
                },
            )
            PodcaRenderChildren(node = node, renderChild = renderChild)
        }

        "material3.DateRangeInput" -> {
            runCatching { DateRangeInputProto.ADAPTER.decode(node.payload) }
            Column {
                resolveDateContent(node = node, placeholder = "DateRangeInput").toComposable(
                    node = node,
                    renderChild = renderChild,
                )
            }
        }

        "material3.DatePickerDialog" -> {
            runCatching { DatePickerDialogProto.ADAPTER.decode(node.payload) }
            BasicAlertDialog(
                onDismissRequest = { dispatchNodeAction(scope, runtime, node, ActionTriggerProto.ACTION_TRIGGER_DISMISS) },
            ) {
                Column {
                    resolveDateContent(node = node, placeholder = "DatePickerDialog").toComposable(
                        node = node,
                        renderChild = renderChild,
                    )
                }
            }
        }

        "material3.ColorScheme",
        "material3.ContentColor",
        "material3.InteractiveComponentSize",
        "material3.RippleConfiguration",
        "material3.SheetState",
        "material3.DrawerState",
        "material3.CalendarLocale",
        "material3.BottomSheetScaffoldState",
        "material3.ModalBottomSheetProperties",
        "material3.DateRangePickerState",
        "material3.TonalPalette",
        "material3.WideNavigationRailState",
        "material3.ModalWideNavigationRailProperties",
        "material3.ExperimentalMaterial3Api",
        "material3.ExperimentalMaterial3ExpressiveApi",
        "material3.FloatingActionButtonElevation",
        "material3.TopAppBarColors",
        "material3.TopAppBarState",
        "material3.BottomAppBarState",
        "material3.BottomAppBarScrollBehavior",
        "material3.AppBarColumnScope",
        "material3.AppBarRowScope",
        "material3.AppBarScope",
        "material3.AppBarClickableItem",
        "material3.AppBarToggleableItem",
        "material3.AppBarCustomItem",
        "material3.AppBarMenuState",
        "material3.ButtonColors",
        "material3.ButtonElevation",
        "material3.CardColors",
        "material3.CardElevation",
        "material3.TextFieldColors",
        "material3.BadgeDefaults",
        "material3.DividerDefaults",
        "material3.ProgressIndicatorDefaults",
        "material3.SnackbarDefaults",
        "material3.IconButtonColors",
        "material3.IconToggleButtonColors",
        "material3.IconButtonDefaults",
        "material3.NavigationBarItemColors",
        "material3.NavigationDrawerItemColors",
        "material3.NavigationItemColors",
        "material3.NavigationRailItemColors",
        "material3.RadioButtonColors",
        "material3.SegmentedButtonColors",
        "material3.SwitchColors",
        "material3.DatePickerColors",
        "material3.TimePickerColors",
        "material3.RichTooltipColors",
        "material3.DragHandleColors",
        "material3.DragHandleShapes",
        "material3.DragHandleSizes",
        "material3.MotionScheme",
        "material3.Shapes",
        "material3.Typography",
        "material3.SheetValue",
        "material3.DrawerValue",
        "material3.SnackbarResult",
        "material3.SnackbarDuration",
        "material3.DisplayMode",
        "material3.TimePickerLayoutType",
        "material3.TimePickerSelectionMode",
        "material3.TimeFormat",
        "material3.TimePickerDisplayMode",
        "material3.TooltipAnchorPosition",
        "material3.NavigationItemIconPosition",
        "material3.IconButtonWidthOption",
        "material3.ShortNavigationBarArrangement",
        "material3.FabPosition",
        "material3.SearchBarValue",
        "material3.ExposedDropdownMenuAnchorType",
        "material3.WideNavigationRailColors",
        "material3.WideNavigationRailValue",
        "material3.HorizontalCenterOptically",
        "material3.SwipeToDismissBoxState",
        "material3.SwipeToDismissBoxValue",
        "material3.BorderStroke",
        -> PodcaRenderChildren(node = node, renderChild = renderChild)

        else -> PodcaRenderChildren(node = node, renderChild = renderChild)
    }
}

@Composable
private fun PodcaRenderTextField(
    node: PodcaDocumentNode,
    runtime: PodcaRuntime,
    scope: CoroutineScope,
    renderChild: @Composable (PodcaDocumentNode) -> Unit,
    text: String,
    modifier: Modifier,
    enabled: Boolean,
    readOnly: Boolean,
    label: String,
    placeholder: String,
    prefix: String,
    suffix: String,
    supportingText: String,
    isError: Boolean,
    lineLimits: Int,
    style: TextStyle,
    colors: com.podca.sdui.protocol.material3.TextFieldColorsProto?,
    secure: Boolean,
    revealed: Boolean,
    outlined: Boolean = false,
) {
    var value by remember(node.key, text) {
        mutableStateOf(text)
    }

    val onValueChange: (String) -> Unit = { newValue ->
        value = newValue
        if (node.key.isNotBlank()) {
            scope.launch {
                runtime.dispatch(
                    nodeKey = node.key,
                    trigger = ActionTriggerProto.ACTION_TRIGGER_VALUE_CHANGE,
                    arguments = listOf(
                        PodcaStringActionArgument(
                            name = "text",
                            value = newValue,
                        ),
                    ),
                )
            }
        }
    }

    val prefixContent = resolveTextFieldSlotContent(node, "prefix", prefix).toComposable(node, renderChild)
    val suffixContent = resolveTextFieldSlotContent(node, "suffix", suffix).toComposable(node, renderChild)
    val labelContent = resolveTextFieldSlotContent(node, "label", label).toComposable(node, renderChild)
    val placeholderContent = resolveTextFieldSlotContent(node, "placeholder", placeholder).toComposable(node, renderChild)
    val supportingContent = resolveTextFieldSlotContent(node, "supportingText", supportingText).toComposable(node, renderChild)
    val visualTransformation = if (secure && !revealed) {
        androidx.compose.ui.text.input.PasswordVisualTransformation()
    } else {
        androidx.compose.ui.text.input.VisualTransformation.None
    }

    if (outlined) {
        OutlinedTextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier,
            enabled = enabled,
            readOnly = readOnly,
            label = labelContent,
            placeholder = placeholderContent,
            prefix = prefixContent,
            suffix = suffixContent,
            supportingText = supportingContent,
            isError = isError,
            singleLine = lineLimits <= 1,
            textStyle = style,
            visualTransformation = visualTransformation,
        )
    } else {
        TextField(
            value = value,
            onValueChange = onValueChange,
            modifier = modifier,
            enabled = enabled,
            readOnly = readOnly,
            label = labelContent,
            placeholder = placeholderContent,
            prefix = prefixContent,
            suffix = suffixContent,
            supportingText = supportingContent,
            isError = isError,
            singleLine = lineLimits <= 1,
            textStyle = style,
            visualTransformation = visualTransformation,
        )
    }
}

internal sealed interface TextFieldSlotContentResolution {
    data class Slot(val slot: String) : TextFieldSlotContentResolution
    data class StringValue(val value: String) : TextFieldSlotContentResolution
    data object Empty : TextFieldSlotContentResolution
}

internal fun resolveTextFieldSlotContent(
    node: PodcaDocumentNode,
    slot: String,
    fallback: String,
): TextFieldSlotContentResolution =
    when {
        node.hasSlot(slot) -> TextFieldSlotContentResolution.Slot(slot)
        fallback.isNotBlank() -> TextFieldSlotContentResolution.StringValue(fallback)
        else -> TextFieldSlotContentResolution.Empty
    }

internal sealed interface DateContentResolution {
    data object Children : DateContentResolution
    data class Placeholder(val text: String) : DateContentResolution
}

internal fun resolveDateContent(
    node: PodcaDocumentNode,
    placeholder: String,
): DateContentResolution =
    if (node.children.isNotEmpty()) {
        DateContentResolution.Children
    } else {
        DateContentResolution.Placeholder(placeholder)
    }

@Composable
private fun DateContentResolution.toComposable(
    node: PodcaDocumentNode,
    renderChild: @Composable (PodcaDocumentNode) -> Unit,
): Unit =
    when (this) {
        DateContentResolution.Children -> {
            PodcaRenderChildren(node = node, renderChild = renderChild)
        }

        is DateContentResolution.Placeholder -> {
            Text(text = text)
        }
    }

@Composable
private fun TextFieldSlotContentResolution.toComposable(
    node: PodcaDocumentNode,
    renderChild: @Composable (PodcaDocumentNode) -> Unit,
): (@Composable (() -> Unit))? =
    when (this) {
        is TextFieldSlotContentResolution.Slot -> {
            { PodcaRenderSlotChildren(node = node, slot = slot, renderChild = renderChild) }
        }

        is TextFieldSlotContentResolution.StringValue -> {
            { Text(text = value) }
        }

        TextFieldSlotContentResolution.Empty -> null
    }

private fun dispatchByKey(scope: CoroutineScope, runtime: PodcaRuntime, key: String) {
    if (key.isBlank()) return
    scope.launch {
        runtime.dispatch(key)
    }
}

@Composable
private fun SnackbarActionButton(
    label: String,
    onClick: () -> Unit,
) {
    TextButton(onClick = onClick) {
        Text(text = label)
    }
}

internal fun PodcaDocumentNode.resolveAction(
    trigger: ActionTriggerProto,
): ActionBindingProto? =
    actions.firstOrNull { action ->
        action.trigger == trigger
    } ?: actions.firstOrNull { action ->
        action.trigger == ActionTriggerProto.ACTION_TRIGGER_UNSPECIFIED
    }

private fun dispatchNodeAction(
    scope: CoroutineScope,
    runtime: PodcaRuntime,
    node: PodcaDocumentNode,
    trigger: ActionTriggerProto,
) {
    if (node.key.isBlank()) return
    val action = node.resolveAction(trigger) ?: return
    scope.launch {
        runtime.dispatch(
            nodeKey = node.key,
            action = action,
        )
    }
}
