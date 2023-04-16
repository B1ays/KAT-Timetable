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
import ru.blays.timetable.UI.Compose.mainViewModel
import ru.blays.timetable.UI.ComposeElements.Navigation
import ru.blays.timetable.UI.ScreenList
import ru.hh.toolbar.custom_toolbar.rememberToolbarScrollBehavior

@ExperimentalAnimationApi
@Composable
fun RootElements() {

    mainViewModel.mediatingRepository.callBack = {
        when (mainViewModel.mediatingRepository.currentScreen) {
            ScreenList.main_screen -> mainViewModel.titleText = "Главаная"
            ScreenList.about_screen -> mainViewModel.titleText = "О приложении"
            ScreenList.settings_screen -> mainViewModel.titleText = "Настройки"
            ScreenList.timetable_screen ->  mainViewModel.titleText = mainViewModel.mediatingRepository.currentGroupCode
            else -> mainViewModel.titleText = ""
        }
    }


    val scrollBehavior = rememberToolbarScrollBehavior()

    Scaffold(
        modifier = Modifier
            .nestedScroll(
                scrollBehavior.nestedScrollConnection
            ),
        topBar = {
            CollapsingAppBar(scrollBehavior)
        },
        floatingActionButton = {
            FloatingMenu()
        }
    )
    {
        Frame(it)
    }
}

@ExperimentalAnimationApi
@Composable
fun Frame(
    paddingValues: PaddingValues
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
           Navigation()
        }
    }
}