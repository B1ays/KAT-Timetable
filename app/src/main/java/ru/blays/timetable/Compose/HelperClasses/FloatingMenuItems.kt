package ru.blays.timetable.Compose.HelperClasses

import androidx.compose.material.icons.rounded.*
import androidx.compose.ui.graphics.vector.ImageVector
import ru.blays.timetable.Compose.ScreenData
import ru.blays.timetable.Compose.ScreenList
import ru.blays.timetable.Compose.States.FloatingMenuState
import ru.blays.timetable.Compose.States.ScreenState
import ru.blays.timetable.Compose.prefs

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

object FloatingMenuItems {
    private val iconPath = androidx.compose.material.icons.Icons.Rounded
    val Items = listOf(
        FloatingMenuItemsModel(title = "Настройки", iconPath.Settings, ScreenList.settings_screen) {
            ScreenState.changeScreen(ScreenData(ScreenList.settings_screen))
        },
        FloatingMenuItemsModel(title = "Избранное", iconPath.Star, ScreenList.timetable_screen) {
            ScreenState.changeScreen(ScreenData(ScreenList.timetable_screen,
                prefs.favoriteTimetablePrefs!!
            ))
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
}