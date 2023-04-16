package ru.blays.timetable.UI.ComposeElements

import android.util.Log
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import ru.blays.timetable.UI.Compose.navigationViewModel
import ru.blays.timetable.UI.ScreenList
import ru.blays.timetable.UI.Screens.AboutScreen
import ru.blays.timetable.UI.Screens.DownloadProgressScreen

@ExperimentalAnimationApi
@Composable
fun Navigation() {

    Log.d("callBackLog", navigationViewModel.mediatingRepository.toString())

    if (navigationViewModel.backStack.last() != navigationViewModel.currentScreen && navigationViewModel.currentScreen.Screen != ScreenList.update_TimeTable) {
        navigationViewModel.addToBackStack(navigationViewModel.currentScreen)
    }

    when(navigationViewModel.currentScreen.Screen) {
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
//            updateTimeTable()
            DownloadProgressScreen()
        }
    }
}