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

    if (navigationViewModel.backStack.last() != navigationViewModel.currentScreen && navigationViewModel.currentScreen.Screen != ScreenList.update_TimeTable) {
        navigationViewModel.addToBackStack(navigationViewModel.currentScreen)
    }

    when(navigationViewModel.currentScreen.Screen) {
        ScreenList.main_screen -> {
            val screen = GroupListScreen(
                groupListViewModel = groupListViewModel,
                navigationViewModel = navigationViewModel
            )
            screen.Create()
        }
        ScreenList.timetable_screen -> {
            BackPressHandler(onBackPressed = { onBack(navigationViewModel) })
            val screen = TimeTableScreen(
                timetableViewModel = timetableViewModel,
                navigationViewModel = navigationViewModel)
            screen.Create()
        }
        ScreenList.settings_screen -> {
            BackPressHandler(onBackPressed = { onBack(navigationViewModel) })
            val screen = SettingsScreen(
                settingsViewModel = settingsViewModel
            )
            screen.Create()
        }
        ScreenList.about_screen -> {
            BackPressHandler(onBackPressed = { onBack(navigationViewModel) })
            val screen = AboutScreen()
            screen.Create()
        }
    }
}