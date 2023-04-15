package ru.blays.timetable.Compose.HelperClasses

import androidx.compose.ui.graphics.vector.ImageVector

data class FloatingMenuItemsModel(
    val title: String,
    val icon: ImageVector,
    val destinationScreen: String,
    val action: () -> Unit
)

data class FloatingMenuActionsModel(
    val icon: ImageVector,
    val action: () -> Unit
)

/*
object FloatingMenuItems {
    private val iconPath = androidx.compose.material.icons.Icons.Rounded
    val Items = listOf(
        FloatingMenuItemsModel(title = "Настройки", iconPath.Settings, ScreenList.settings_screen) {
            ScreenState.changeScreen(ScreenData(ScreenList.settings_screen))
        },
        FloatingMenuItemsModel(title = "Избранное", iconPath.Star, ScreenList.timetable_screen) {
            if (AppBarState.currentFavoriteTimetable != "no") {
                ScreenState.changeScreen(
                    ScreenData(
                        ScreenList.timetable_screen,
                        AppBarState.currentFavoriteTimetable
                    )
                )
            }
        },
        FloatingMenuItemsModel(title = "О приложении", iconPath.Info, ScreenList.about_screen) {
            ScreenState.changeScreen(ScreenData(ScreenList.about_screen))
        }
    )
}

object FloatingMenuActions {
    val refresh = FloatingMenuActionsModel(androidx.compose.material.icons.Icons.Rounded.Refresh) {
        ScreenState.changeScreen(ScreenData(ScreenList.update_TimeTable, ScreenState.currentScreen.Key))
    }
    val close = FloatingMenuActionsModel(androidx.compose.material.icons.Icons.Rounded.Close) {
        FloatingMenuState.changeExpanded()
    }
}*/
