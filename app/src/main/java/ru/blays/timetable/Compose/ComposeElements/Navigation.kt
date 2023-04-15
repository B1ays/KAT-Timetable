package ru.blays.timetable.Compose.ComposeElements

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable

@ExperimentalAnimationApi
@Composable
fun Navigation() {


   /* if (backStack.last() != currentScreen && currentScreen.Screen != ScreenList.update_TimeTable) {
        backStack.add(currentScreen)
    }*/



    /*when(currentScreen.Screen) {
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
    }*/
}