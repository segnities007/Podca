# material3 Protocol

`sdui/material3` は Compose Material 3 の shared/public API を SDUI 用 `proto3` に写像したレイヤーです。

## 1:1 対応表

| Source / Proto | 状態 |
|---|---|
| `AlertDialog` | 実装 |
| `AppBar` | 実装 |
| `AppBarColumn` | 実装 |
| `AppBarDsl` | 実装 |
| `AppBarRow` | 実装 |
| `Badge` | 実装 |
| `BottomSheetScaffold` | 実装 |
| `Button` | 実装 |
| `CalendarLocale` | 実装 |
| `Card` | 実装 |
| `Checkbox` | 実装 |
| `Chip` | 実装 |
| `ColorScheme` | 実装 |
| `ContentColor` | 実装 |
| `DateInput` | 実装 |
| `DatePicker` | 実装 |
| `DatePickerDialog` | 実装 |
| `DateRangeInput` | 実装 |
| `DateRangePicker` | 実装 |
| `Divider` | 実装 |
| `DragHandle` | 実装 |
| `ExperimentalMaterial3Api` | 実装 |
| `ExperimentalMaterial3ExpressiveApi` | 実装 |
| `ExposedDropdownMenu` | 実装 |
| `FloatingActionButton` | 実装 |
| `HorizontalCenterOptically` | 実装 |
| `Icon` | 実装 |
| `IconButton` | 実装 |
| `IconButtonDefaults` | 実装 |
| `InteractiveComponentSize` | 実装 |
| `Label` | 実装 |
| `ListItem` | 実装 |
| `MaterialTheme` | 実装 |
| `Menu` | 実装 |
| `ModalBottomSheet` | 実装 |
| `MotionScheme` | 実装 |
| `NavigationBar` | 実装 |
| `NavigationDrawer` | 実装 |
| `NavigationDrawerState` | 実装 |
| `NavigationItem` | 実装 |
| `NavigationRail` | 実装 |
| `OutlinedTextField` | 実装 |
| `ProgressIndicator` | 実装 |
| `RadioButton` | 実装 |
| `Ripple` | 実装 |
| `Scaffold` | 実装 |
| `SearchBar` | 実装 |
| `SecureTextField` | 実装 |
| `SegmentedButton` | 実装 |
| `Shapes` | 実装 |
| `SheetDefaults` | 実装 |
| `ShortNavigationBar` | 実装 |
| `Slider` | 実装 |
| `Snackbar` | 実装 |
| `SnackbarHost` | 実装 |
| `Styles` | 互換プレースホルダ |
| `Surface` | 実装 |
| `SwipeToDismissBox` | 実装 |
| `Switch` | 実装 |
| `Tab` | 実装 |
| `TabRow` | 実装 |
| `Text` | 実装 |
| `TextField` | 実装 |
| `TextFieldDefaults` | 実装 |
| `Theme` | 互換プレースホルダ |
| `TimeFormat` | 実装 |
| `TimePicker` | 実装 |
| `TimePickerDialog` | 実装 |
| `TonalPalette` | 実装 |
| `Tooltip` | 実装 |
| `Typography` | 実装 |
| `Values` | 互換プレースホルダ |
| `WideNavigationRail` | 実装 |
| `WideNavigationRailState` | 実装 |

## メモ

- 方針上、shared/public API を優先しています
- 実装専用 token/helper は protocol 対象にしていません
- 除外: Android 専用実装, samples, `internal`/private, helper/utility
