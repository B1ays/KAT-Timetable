package ru.blays.timetable.UI.ComposeElements

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.runtime.Composable
import com.theapache64.rebugger.Rebugger
import ru.blays.timetable.UI.Compose.ComposeElements.navigation.NavigationVM
import ru.blays.timetable.UI.Compose.MainViewModel
import ru.blays.timetable.UI.Compose.Screens.GroupListScreen.SimpleListScreenVM
import ru.blays.timetable.UI.Compose.Screens.GroupListScreen.ViewPagerFrame
import ru.blays.timetable.UI.Compose.Screens.SettingsScreen.SettingsScreenVM
import ru.blays.timetable.UI.Compose.Screens.TimeTableScreen.TimetableScreenVM
import ru.blays.timetable.UI.ScreenList
import ru.blays.timetable.UI.Screens.AboutScreen

@OptIn(ExperimentalMaterialApi::class)
@ExperimentalAnimationApi
@Composable
fun Navigation(
    mainViewModel: MainViewModel,
    timetableViewModel: TimetableScreenVM,
    groupListViewModel: SimpleListScreenVM,
    navigationViewModel: NavigationVM,
    settingsViewModel: SettingsScreenVM
) {

    if (navigationViewModel.backStack.last() != navigationViewModel.currentScreen) {
        navigationViewModel.addToBackStack(navigationViewModel.currentScreen)
    }

    /*if (mainViewModel.isInit && mainViewModel.initialSettings.openFavoriteOnStart == true && mainViewModel.favoriteHref != "no" && mainViewModel.favoriteHref != null) {
        navigationViewModel.changeScreen(newScreen = ScreenData(ScreenList.TIMETABLE_SCREEN, mainViewModel.favoriteHref!!))
    }*/

    when(navigationViewModel.currentScreen.Screen) {
        ScreenList.MAIN_SCREEN -> {
            ViewPagerFrame(
                simpleListScreenVM = groupListViewModel,
                navigationViewModel = navigationViewModel
            ).Create()
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

    Rebugger(trackMap = mapOf(
        "screen" to navigationViewModel.currentScreen.Screen,
    ))
}