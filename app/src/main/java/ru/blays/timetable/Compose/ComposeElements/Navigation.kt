package ru.blays.timetable.Compose.ComposeElements

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringResource
import ru.blays.timetable.Compose.ScreenList
import ru.blays.timetable.Compose.Screens.AboutScreen
import ru.blays.timetable.Compose.Screens.DownloadProgressScreen
import ru.blays.timetable.Compose.States.AppBarState
import ru.blays.timetable.Compose.States.BackStackState.backStack
import ru.blays.timetable.Compose.States.ScreenState.currentScreen
import ru.blays.timetable.R

@ExperimentalAnimationApi
@Composable
fun Navigation() {


    if (backStack.last() != currentScreen && currentScreen.Screen != ScreenList.update_TimeTable) {
        backStack.add(currentScreen)
    }

    when(currentScreen.Screen) {
        ScreenList.main_screen -> {
            SimpleList()
            AppBarState.changeTitleText(stringResource(id = R.string.Toolbar_MainScreen_title))
        }
        ScreenList.timetable_screen -> {
            BackPressHandler(onBackPressed = onBack)
            TimeTableView()
        }
        ScreenList.settings_screen -> {
            BackPressHandler(onBackPressed = onBack)
            AppBarState.changeTitleText("Настройки")
            SettingsScreen()
        }
        ScreenList.about_screen -> {
            BackPressHandler(onBackPressed = onBack)
            AppBarState.changeTitleText("О приложении")
            AboutScreen()
        }
        ScreenList.update_TimeTable -> {
            AppBarState.changeTitleText("Обновление...")
            updateTimeTable()
            DownloadProgressScreen()
        }
    }
}