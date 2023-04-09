package ru.blays.timetable.Compose.States

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import ru.blays.timetable.Compose.HelperClasses.CurrentTimeTable
import ru.blays.timetable.Compose.ScreenList
import ru.blays.timetable.R

object AppBarState {
    var titleText by mutableStateOf("defaultTitle")

    var navigateBackButtonVisible by mutableStateOf(false)

    var favoriteButtonVisible by mutableStateOf(false)

    var favoriteButtonChecked by mutableStateOf(false)

    var currentFavoriteTimetable by mutableStateOf("")

    @Composable
    fun UpdateAppBarState() {

        titleText = when(ScreenState.currentScreen.Screen) {
            ScreenList.main_screen -> stringResource(id = R.string.Toolbar_MainScreen_title)
            ScreenList.timetable_screen -> CurrentTimeTable.groupCode
            ScreenList.about_screen -> "О приложении"
            ScreenList.settings_screen -> "Настройки"
            ScreenList.update_TimeTable -> "Обновление..."
            else -> ""
        }

        navigateBackButtonVisible = ScreenState.currentScreen.Screen != ScreenList.main_screen

        favoriteButtonVisible = ScreenState.currentScreen.Screen == ScreenList.timetable_screen

        favoriteButtonChecked =  ScreenState.currentScreen.Key == currentFavoriteTimetable

    }

}
