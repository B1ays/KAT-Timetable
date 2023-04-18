package ru.blays.timetable.UI.ComposeElements

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.Composable
import ru.blays.timetable.UI.Compose.ComposeElements.navigation.NavigationVM
import ru.blays.timetable.UI.Compose.MainViewModel
import ru.blays.timetable.UI.Compose.Screens.GroupListScreen.GroupListScreenVM
import ru.blays.timetable.UI.Compose.Screens.SettingsScreen.SettingsScreenVM
import ru.blays.timetable.UI.Compose.Screens.TimeTableScreen.TimetableScreenVM
import ru.blays.timetable.UI.ScreenList
import ru.blays.timetable.UI.Screens.AboutScreen

@ExperimentalAnimationApi
@Composable
fun Navigation(
    mainViewModel: MainViewModel,
    timetableViewModel: TimetableScreenVM,
    groupListViewModel: GroupListScreenVM,
    navigationViewModel: NavigationVM,
    settingsViewModel: SettingsScreenVM
) {

    if (navigationViewModel.backStack.last() != navigationViewModel.currentScreen) {
        navigationViewModel.addToBackStack(navigationViewModel.currentScreen)
    }

    when(navigationViewModel.currentScreen.Screen) {
        ScreenList.MAIN_SCREEN -> {
            GroupListScreen(
                groupListViewModel = groupListViewModel,
                navigationViewModel = navigationViewModel
            ).run { Create() }
        }
        ScreenList.TIMETABLE_SCREEN -> {
            BackPressHandler(onBackPressed = { onBack(navigationViewModel) })
            TimeTableScreen(
                timetableViewModel = timetableViewModel,
                navigationViewModel = navigationViewModel
            ).run { Create() }
        }
        ScreenList.SETTINGS_SCREEN -> {
            BackPressHandler(onBackPressed = { onBack(navigationViewModel) })
            SettingsScreen(
                settingsViewModel = settingsViewModel
            ).run { Create() }
        }
        ScreenList.ABOUT_SCREEN -> {
            BackPressHandler(onBackPressed = { onBack(navigationViewModel) })
            AboutScreen().run { Create() }
        }
    }
}