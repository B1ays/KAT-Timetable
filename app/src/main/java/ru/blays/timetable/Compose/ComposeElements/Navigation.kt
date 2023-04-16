package ru.blays.timetable.Compose.ComposeElements

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import ru.blays.timetable.Compose.ScreenList
import ru.blays.timetable.Compose.Screens.AboutScreen
import ru.blays.timetable.Compose.Screens.DownloadProgressScreen
import ru.blays.timetable.Compose.navigationVM

@ExperimentalAnimationApi
@Composable
fun Navigation() {


    if (navigationVM.backStack.last() != navigationVM.currentScreen && navigationVM.currentScreen.Screen != ScreenList.update_TimeTable) {
        navigationVM.addToBackStack(navigationVM.currentScreen)
    }



    when(navigationVM.currentScreen.Screen) {
        ScreenList.main_screen -> {
            SimpleList()
        }
        ScreenList.timetable_screen -> {
            BackPressHandler(onBackPressed = onBack)
            TimeTableView()
        }
        ScreenList.settings_screen -> {
            BackPressHandler(onBackPressed = onBack)
//            SettingsScreen()
        }
        ScreenList.about_screen -> {
            BackPressHandler(onBackPressed = onBack)
            AboutScreen()
        }
        ScreenList.update_TimeTable -> {
//            updateTimeTable()
            DownloadProgressScreen()
        }
    }
}