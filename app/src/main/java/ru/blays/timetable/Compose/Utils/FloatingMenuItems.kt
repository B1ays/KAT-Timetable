package ru.blays.timetable.Compose.Utils

import androidx.compose.material.icons.rounded.Close
import androidx.compose.material.icons.rounded.Favorite
import androidx.compose.material.icons.rounded.Refresh
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.ui.graphics.vector.ImageVector
import ru.blays.timetable.Compose.ScreenList

data class FloatingMenuItemsModel(
    val title: String,
    val icon: ImageVector,
    val destinationScreen: String
)

data class FloatingMenuActionsModel(
    val icon: ImageVector
)

object FloatingMenuItems {
    val Items = listOf(
        FloatingMenuItemsModel(title = "Настройки", androidx.compose.material.icons.Icons.Rounded.Settings, ScreenList.settings_screen),
        FloatingMenuItemsModel(title = "Избранное", androidx.compose.material.icons.Icons.Rounded.Favorite, ScreenList.timetable_screen)
    )
}

object FloatingMenuActions {
    val refresh = FloatingMenuActionsModel(androidx.compose.material.icons.Icons.Rounded.Refresh)
    val close = FloatingMenuActionsModel(androidx.compose.material.icons.Icons.Rounded.Close)
}
