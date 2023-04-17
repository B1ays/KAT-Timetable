package ru.blays.timetable.UI.Screens

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.input.nestedscroll.nestedScroll
import ru.blays.timetable.UI.Compose.ComposeElements.CollapsingAppBar
import ru.blays.timetable.UI.Compose.ComposeElements.FloatingMenu
import ru.blays.timetable.UI.Compose.ComposeElements.navigation.NavigationVM
import ru.blays.timetable.UI.Compose.MainViewModel
import ru.blays.timetable.UI.Compose.Screens.GroupListScreen.GroupListScreenVM
import ru.blays.timetable.UI.Compose.Screens.SettingsScreen.SettingsScreenVM
import ru.blays.timetable.UI.Compose.Screens.TimeTableScreen.TimetableScreenVM
import ru.blays.timetable.UI.ComposeElements.Navigation
import ru.blays.timetable.UI.ScreenList
import ru.hh.toolbar.custom_toolbar.rememberToolbarScrollBehavior

@ExperimentalAnimationApi
@Composable
fun RootElements(
    mainViewModel: MainViewModel,
    timetableViewModel: TimetableScreenVM,
    groupListViewModel: GroupListScreenVM,
    navigationViewModel: NavigationVM,
    settingsViewModel: SettingsScreenVM
) {

    observe(mainViewModel)

    val scrollBehavior = rememberToolbarScrollBehavior()

    Scaffold(
        modifier = Modifier
            .nestedScroll(
                scrollBehavior.nestedScrollConnection
            ),
        topBar = {
            val appBar = CollapsingAppBar(
                mainViewModel = mainViewModel,
                navigationViewModel = navigationViewModel
            )
            appBar.Create(scrollBehavior)
        },
        floatingActionButton = {
            val floatingMenu = FloatingMenu(
                mainViewModel = mainViewModel,
                navigationViewModel = navigationViewModel,
                timetableViewModel = timetableViewModel
            )
            floatingMenu.Create()
        }
    )
    {
        Frame(
            it,
            mainViewModel,
            timetableViewModel,
            groupListViewModel,
            navigationViewModel,
            settingsViewModel
        )
    }
}



@ExperimentalAnimationApi
@Composable
fun Frame(
    paddingValues: PaddingValues,
    mainViewModel: MainViewModel,
    timetableViewModel: TimetableScreenVM,
    groupListViewModel: GroupListScreenVM,
    navigationViewModel: NavigationVM,
    settingsViewModel: SettingsScreenVM
) {
    Surface(
        modifier = Modifier
            .padding(paddingValues),
        color = MaterialTheme.colorScheme.background
    )
    {
        MaterialTheme {
        /*if(AlertDialogState.isOpen) {
                CustomAlertDialog(message = AlertDialogState.text)
            }*/
           Navigation(
            mainViewModel,
            timetableViewModel,
            groupListViewModel,
            navigationViewModel,
            settingsViewModel
           )
        }
    }
}

fun observe(mainViewModel: MainViewModel) {
    mainViewModel.mediatingRepository.appBarStateCallBack = { currentScreen, currentGroupCode ->
        when (currentScreen) {
            ScreenList.main_screen -> mainViewModel.titleText = "Главаная"
            ScreenList.about_screen -> mainViewModel.titleText = "О приложении"
            ScreenList.settings_screen -> mainViewModel.titleText = "Настройки"
            ScreenList.timetable_screen -> mainViewModel.titleText = currentGroupCode
            else -> mainViewModel.titleText = ""
        }
    }

    mainViewModel.mediatingRepository.themeChangeCallBack = { isDarkMode ->
        mainViewModel.isDarkMode = isDarkMode
    }

    mainViewModel.mediatingRepository.monetChangeCallBack = { isMonetColors ->
        mainViewModel.monetColors = isMonetColors
    }

}