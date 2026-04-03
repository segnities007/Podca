package com.podca.sdui.studio.ui.material3

import com.podca.sdui.protocol.core.ActionTriggerProto
import com.podca.sdui.protocol.material3.CheckboxProto
import com.podca.sdui.protocol.material3.BadgedBoxProto
import com.podca.sdui.protocol.material3.IconButtonProto
import com.podca.sdui.protocol.material3.ListItemProto
import com.podca.sdui.protocol.material3.NavigationBarProto
import com.podca.sdui.protocol.material3.NavigationDrawerProto
import com.podca.sdui.protocol.material3.NavigationRailProto
import com.podca.sdui.protocol.material3.SearchBarProto
import com.podca.sdui.protocol.material3.SearchBarValueProto
import com.podca.sdui.protocol.material3.SegmentedButtonProto
import com.podca.sdui.protocol.material3.RadioButtonProto
import com.podca.sdui.protocol.material3.SnackbarProto
import com.podca.sdui.protocol.material3.SnackbarHostStateProto
import com.podca.sdui.protocol.material3.SnackbarVisualsProto
import com.podca.sdui.protocol.material3.TextFieldProto
import com.podca.sdui.protocol.material3.TimePickerLayoutTypeProto
import com.podca.sdui.protocol.material3.TimePickerProto
import com.podca.sdui.protocol.material3.TimePickerSelectionModeProto
import com.podca.sdui.protocol.material3.TooltipAnchorPositionProto
import com.podca.sdui.protocol.material3.TooltipProto
import com.podca.sdui.protocol.material3.SwitchProto
import com.podca.sdui.studio.core.PodcaActionBinding
import com.podca.sdui.studio.core.PodcaNode
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue
import okio.ByteString.Companion.toByteString

class PodcaMaterial3EncodingTest {
    @Test
    fun button_node_encodes_key_and_actions() {
        val button = PodcaNode(
            type = "material3.Button",
            key = "play-button",
            actions = listOf(
                PodcaActionBinding(
                    actionId = "episode.play",
                    trigger = ActionTriggerProto.ACTION_TRIGGER_CLICK,
                ),
            ),
        )

        val proto = button.toProto()

        assertEquals("material3.Button", proto.type)
        assertEquals("play-button", proto.key)
        assertEquals("", proto.slot)
        assertEquals(1, proto.actions.size)
        assertEquals("episode.play", proto.actions.single().action_id)
        assertTrue(proto.children.isEmpty())
    }

    @Test
    fun badged_box_dsl_encodes_badge_slot() {
        val node = PodcaNode(
            type = "material3.BadgedBox",
            payload = BadgedBoxProto(
                has_badge = true,
            ).let(BadgedBoxProto.ADAPTER::encode).toByteString(),
        )

        val proto = node.toProto()

        assertEquals("material3.BadgedBox", proto.type)
        assertTrue(proto.actions.isEmpty())
        assertTrue(BadgedBoxProto.ADAPTER.decode(proto.payload).has_badge)
    }

    @Test
    fun node_encodes_slot_name() {
        val node = PodcaNode(
            type = "material3.Icon",
            slot = "leadingIcon",
        )

        val proto = node.toProto()

        assertEquals("material3.Icon", proto.type)
        assertEquals("leadingIcon", proto.slot)
    }

    @Test
    fun card_node_encodes_key_and_actions() {
        val card = PodcaNode(
            type = "material3.Card",
            key = "episode-card",
            actions = listOf(
                PodcaActionBinding(
                    actionId = "episode.open",
                    trigger = ActionTriggerProto.ACTION_TRIGGER_CLICK,
                ),
            ),
        )

        val proto = card.toProto()

        assertEquals("material3.Card", proto.type)
        assertEquals("episode-card", proto.key)
        assertEquals("episode.open", proto.actions.single().action_id)
    }

    @Test
    fun snackbar_dsl_encodes_action_and_dismiss_action_slots() {
        val node = PodcaNode(
            type = "material3.Snackbar",
            payload = SnackbarProto(
                has_action = true,
                has_dismiss_action = true,
                snackbar_data = com.podca.sdui.protocol.material3.SnackbarDataProto(
                    visuals = SnackbarVisualsProto(
                        message = "Saved",
                        action_label = "Undo",
                        with_dismiss_action = true,
                    ),
                ),
            ).let(SnackbarProto.ADAPTER::encode).toByteString(),
        )

        val proto = node.toProto()

        assertEquals("material3.Snackbar", proto.type)
        assertTrue(SnackbarProto.ADAPTER.decode(proto.payload).has_action)
        assertTrue(SnackbarProto.ADAPTER.decode(proto.payload).has_dismiss_action)
    }

    @Test
    fun checkbox_dsl_encodes_payload_key_and_actions() {
        val node = PodcaNode(
            type = "material3.Checkbox",
            payload = CheckboxProto(
                checked = true,
                enabled = false,
            ).let(CheckboxProto.ADAPTER::encode).toByteString(),
            key = "privacy-checkbox",
            actions = listOf(
                PodcaActionBinding(
                    actionId = "settings.privacy.toggle",
                    trigger = ActionTriggerProto.ACTION_TRIGGER_CLICK,
                ),
            ),
        )

        val proto = node.toProto()
        val payload = CheckboxProto.ADAPTER.decode(proto.payload)

        assertEquals("material3.Checkbox", proto.type)
        assertEquals("privacy-checkbox", proto.key)
        assertEquals("settings.privacy.toggle", proto.actions.single().action_id)
        assertTrue(proto.children.isEmpty())
        assertTrue(payload.checked)
        assertTrue(!payload.enabled)
    }

    @Test
    fun radio_button_dsl_encodes_payload_key_and_actions() {
        val node = PodcaNode(
            type = "material3.RadioButton",
            payload = RadioButtonProto(
                selected = true,
                enabled = false,
            ).let(RadioButtonProto.ADAPTER::encode).toByteString(),
            key = "filter-radio",
            actions = listOf(
                PodcaActionBinding(
                    actionId = "filters.select",
                    trigger = ActionTriggerProto.ACTION_TRIGGER_CLICK,
                ),
            ),
        )

        val proto = node.toProto()
        val payload = RadioButtonProto.ADAPTER.decode(proto.payload)

        assertEquals("material3.RadioButton", proto.type)
        assertEquals("filter-radio", proto.key)
        assertEquals("filters.select", proto.actions.single().action_id)
        assertTrue(proto.children.isEmpty())
        assertTrue(payload.selected)
        assertTrue(!payload.enabled)
    }

    @Test
    fun switch_dsl_encodes_payload_key_and_actions() {
        val node = PodcaNode(
            type = "material3.Switch",
            payload = SwitchProto(
                checked = true,
                enabled = false,
                has_thumb_content = true,
            ).let(SwitchProto.ADAPTER::encode).toByteString(),
            key = "notifications-switch",
            actions = listOf(
                PodcaActionBinding(
                    actionId = "settings.notifications.toggle",
                    trigger = ActionTriggerProto.ACTION_TRIGGER_CLICK,
                ),
            ),
        )

        val proto = node.toProto()
        val payload = SwitchProto.ADAPTER.decode(proto.payload)

        assertEquals("material3.Switch", proto.type)
        assertEquals("notifications-switch", proto.key)
        assertEquals("settings.notifications.toggle", proto.actions.single().action_id)
        assertTrue(proto.children.isEmpty())
        assertTrue(payload.checked)
        assertTrue(!payload.enabled)
        assertTrue(payload.has_thumb_content)
    }

    @Test
    fun icon_button_dsl_encodes_payload_key_and_actions() {
        val node = PodcaNode(
            type = "material3.IconButton",
            payload = IconButtonProto(
                enabled = false,
                has_content = true,
            ).let(IconButtonProto.ADAPTER::encode).toByteString(),
            key = "search-icon-button",
            actions = listOf(
                PodcaActionBinding(
                    actionId = "search.open",
                    trigger = ActionTriggerProto.ACTION_TRIGGER_CLICK,
                ),
            ),
        )

        val proto = node.toProto()
        val payload = IconButtonProto.ADAPTER.decode(proto.payload)

        assertEquals("material3.IconButton", proto.type)
        assertEquals("search-icon-button", proto.key)
        assertEquals("search.open", proto.actions.single().action_id)
        assertTrue(proto.children.isEmpty())
        assertTrue(!payload.enabled)
        assertTrue(payload.has_content)
    }


    @Test
    fun list_item_dsl_encodes_payload_key_and_actions() {
        val node = PodcaNode(
            type = "material3.ListItem",
            payload = ListItemProto(
                headline = "Episode title",
                supporting = "Supporting text",
                overline = "Overline text",
                has_leading_icon = true,
                has_trailing_icon = true,
                enabled = false,
            ).let(ListItemProto.ADAPTER::encode).toByteString(),
            key = "episode-list-item",
            actions = listOf(
                PodcaActionBinding(
                    actionId = "episode.open",
                    trigger = ActionTriggerProto.ACTION_TRIGGER_CLICK,
                ),
            ),
        )

        val proto = node.toProto()
        val payload = ListItemProto.ADAPTER.decode(proto.payload)

        assertEquals("material3.ListItem", proto.type)
        assertEquals("episode-list-item", proto.key)
        assertEquals("episode.open", proto.actions.single().action_id)
        assertTrue(proto.children.isEmpty())
        assertEquals("Episode title", payload.headline)
        assertEquals("Supporting text", payload.supporting)
        assertEquals("Overline text", payload.overline)
        assertTrue(payload.has_leading_icon)
        assertTrue(payload.has_trailing_icon)
        assertTrue(!payload.enabled)
    }

    @Test
    fun navigation_bar_dsl_encodes_payload_key_and_actions() {
        val node = PodcaNode(
            type = "material3.NavigationBar",
            payload = NavigationBarProto(
                selected_index = 2,
                has_content = true,
            ).let(NavigationBarProto.ADAPTER::encode).toByteString(),
            key = "navigation-bar",
            actions = listOf(
                PodcaActionBinding(
                    actionId = "nav.select",
                    trigger = ActionTriggerProto.ACTION_TRIGGER_CLICK,
                ),
            ),
        )

        val proto = node.toProto()
        val payload = NavigationBarProto.ADAPTER.decode(proto.payload)

        assertEquals("material3.NavigationBar", proto.type)
        assertEquals("navigation-bar", proto.key)
        assertEquals("nav.select", proto.actions.single().action_id)
        assertTrue(proto.children.isEmpty())
        assertEquals(2, payload.selected_index)
        assertTrue(payload.has_content)
    }

    @Test
    fun navigation_rail_dsl_encodes_payload_key_and_actions() {
        val node = PodcaNode(
            type = "material3.NavigationRail",
            payload = NavigationRailProto(
                selected_index = 1,
                has_content = true,
            ).let(NavigationRailProto.ADAPTER::encode).toByteString(),
            key = "navigation-rail",
            actions = listOf(
                PodcaActionBinding(
                    actionId = "rail.select",
                    trigger = ActionTriggerProto.ACTION_TRIGGER_CLICK,
                ),
            ),
        )

        val proto = node.toProto()
        val payload = NavigationRailProto.ADAPTER.decode(proto.payload)

        assertEquals("material3.NavigationRail", proto.type)
        assertEquals("navigation-rail", proto.key)
        assertEquals("rail.select", proto.actions.single().action_id)
        assertTrue(proto.children.isEmpty())
        assertEquals(1, payload.selected_index)
        assertTrue(payload.has_content)
    }

    @Test
    fun navigation_drawer_dsl_encodes_payload_key_and_actions() {
        val node = PodcaNode(
            type = "material3.NavigationDrawer",
            payload = NavigationDrawerProto(
                value_ = com.podca.sdui.protocol.material3.DrawerValueProto.DRAWER_VALUE_OPEN,
                has_content = true,
            ).let(NavigationDrawerProto.ADAPTER::encode).toByteString(),
            key = "navigation-drawer",
            actions = listOf(
                PodcaActionBinding(
                    actionId = "drawer.toggle",
                    trigger = ActionTriggerProto.ACTION_TRIGGER_CLICK,
                ),
            ),
        )

        val proto = node.toProto()
        val payload = NavigationDrawerProto.ADAPTER.decode(proto.payload)

        assertEquals("material3.NavigationDrawer", proto.type)
        assertEquals("navigation-drawer", proto.key)
        assertEquals("drawer.toggle", proto.actions.single().action_id)
        assertTrue(proto.children.isEmpty())
        assertEquals(
            com.podca.sdui.protocol.material3.DrawerValueProto.DRAWER_VALUE_OPEN,
            payload.value_,
        )
        assertTrue(payload.has_content)
    }

    @Test
    fun segmented_button_dsl_encodes_payload_key_and_actions() {
        val node = PodcaNode(
            type = "material3.SegmentedButton",
            payload = SegmentedButtonProto(
                selected = true,
                enabled = false,
                text = "Segment",
                has_content = true,
            ).let(SegmentedButtonProto.ADAPTER::encode).toByteString(),
            key = "segmented-button",
            actions = listOf(
                PodcaActionBinding(
                    actionId = "segment.select",
                    trigger = ActionTriggerProto.ACTION_TRIGGER_CLICK,
                ),
            ),
        )

        val proto = node.toProto()
        val payload = SegmentedButtonProto.ADAPTER.decode(proto.payload)

        assertEquals("material3.SegmentedButton", proto.type)
        assertEquals("segmented-button", proto.key)
        assertEquals("segment.select", proto.actions.single().action_id)
        assertTrue(proto.children.isEmpty())
        assertTrue(payload.selected)
        assertTrue(!payload.enabled)
        assertEquals("Segment", payload.text)
        assertTrue(payload.has_content)
    }

    @Test
    fun search_bar_dsl_encodes_payload_key_and_actions() {
        val node = PodcaNode(
            type = "material3.SearchBar",
            payload = SearchBarProto(
                value_ = SearchBarValueProto.SEARCH_BAR_VALUE_EXPANDED,
                text = "query",
                enabled = false,
                has_content = true,
            ).let(SearchBarProto.ADAPTER::encode).toByteString(),
            key = "search-bar",
            actions = listOf(
                PodcaActionBinding(
                    actionId = "search.submit",
                    trigger = ActionTriggerProto.ACTION_TRIGGER_CLICK,
                ),
            ),
        )

        val proto = node.toProto()
        val payload = SearchBarProto.ADAPTER.decode(proto.payload)

        assertEquals("material3.SearchBar", proto.type)
        assertEquals("search-bar", proto.key)
        assertEquals("search.submit", proto.actions.single().action_id)
        assertTrue(proto.children.isEmpty())
        assertEquals(SearchBarValueProto.SEARCH_BAR_VALUE_EXPANDED, payload.value_)
        assertEquals("query", payload.text)
        assertTrue(!payload.enabled)
        assertTrue(payload.has_content)
    }

    @Test
    fun tooltip_dsl_encodes_payload_key_and_actions() {
        val node = PodcaNode(
            type = "material3.Tooltip",
            payload = TooltipProto(
                message = "Helpful tip",
                anchor_position = TooltipAnchorPositionProto.TOOLTIP_ANCHOR_POSITION_ABOVE,
                has_content = true,
            ).let(TooltipProto.ADAPTER::encode).toByteString(),
            key = "tooltip",
            actions = listOf(
                PodcaActionBinding(
                    actionId = "tooltip.show",
                    trigger = ActionTriggerProto.ACTION_TRIGGER_CLICK,
                ),
            ),
        )

        val proto = node.toProto()
        val payload = TooltipProto.ADAPTER.decode(proto.payload)

        assertEquals("material3.Tooltip", proto.type)
        assertEquals("tooltip", proto.key)
        assertEquals("tooltip.show", proto.actions.single().action_id)
        assertTrue(proto.children.isEmpty())
        assertEquals("Helpful tip", payload.message)
        assertEquals(
            TooltipAnchorPositionProto.TOOLTIP_ANCHOR_POSITION_ABOVE,
            payload.anchor_position,
        )
        assertTrue(payload.has_content)
    }

    @Test
    fun time_picker_dsl_encodes_payload_key_and_actions() {
        val node = PodcaNode(
            type = "material3.TimePicker",
            payload = TimePickerProto(
                selected_hour = 10,
                selected_minute = 45,
                enabled = false,
                layout_type = TimePickerLayoutTypeProto.TIME_PICKER_LAYOUT_TYPE_HORIZONTAL,
                selection_mode = TimePickerSelectionModeProto.TIME_PICKER_SELECTION_MODE_MINUTE,
                has_content = true,
            ).let(TimePickerProto.ADAPTER::encode).toByteString(),
            key = "time-picker",
            actions = listOf(
                PodcaActionBinding(
                    actionId = "time.change",
                    trigger = ActionTriggerProto.ACTION_TRIGGER_CLICK,
                ),
            ),
        )

        val proto = node.toProto()
        val payload = TimePickerProto.ADAPTER.decode(proto.payload)

        assertEquals("material3.TimePicker", proto.type)
        assertEquals("time-picker", proto.key)
        assertEquals("time.change", proto.actions.single().action_id)
        assertTrue(proto.children.isEmpty())
        assertEquals(10, payload.selected_hour)
        assertEquals(45, payload.selected_minute)
        assertTrue(!payload.enabled)
        assertEquals(
            TimePickerLayoutTypeProto.TIME_PICKER_LAYOUT_TYPE_HORIZONTAL,
            payload.layout_type,
        )
        assertEquals(
            TimePickerSelectionModeProto.TIME_PICKER_SELECTION_MODE_MINUTE,
            payload.selection_mode,
        )
        assertTrue(payload.has_content)
    }

}
