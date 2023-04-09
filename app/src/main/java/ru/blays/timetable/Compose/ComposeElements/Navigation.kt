package ru.blays.timetable.Compose.ComposeElements

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import ru.blays.timetable.Compose.ScreenList
import ru.blays.timetable.Compose.Screens.AboutScreen
import ru.blays.timetable.Compose.Screens.DownloadProgressScreen
import ru.blays.timetable.Compose.States.BackStackState.backStack
import ru.blays.timetable.Compose.States.ScreenState.currentScreen

@ExperimentalAnimationApi
@Composable
fun Navigation() {


    if (backStack.last() != currentScreen && currentScreen.Screen != ScreenList.update_TimeTable) {
        backStack.add(currentScreen)
    }

    when(currentScreen.Screen) {
        ScreenList.main_screen -> {
            SimpleList()
        }
        ScreenList.timetable_screen -> {
            BackPressHandler(onBackPressed = onBack)
            TimeTableView()
        }
        ScreenList.settings_screen -> {
            BackPressHandler(onBackPressed = onBack)
            SettingsScreen()
        }
        ScreenList.about_screen -> {
            BackPressHandler(onBackPressed = onBack)
            AboutScreen()
        }
        ScreenList.update_TimeTable -> {
            updateTimeTable()
            DownloadProgressScreen()
        }
    }
}