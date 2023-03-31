package ru.blays.timetable.Compose.ComposeElements

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.blays.timetable.Compose.ScreenData
import ru.blays.timetable.Compose.ScreenList
import ru.blays.timetable.Compose.Screens.AboutScreen
import ru.blays.timetable.R
import ru.blays.timetable.htmlParser

@ExperimentalAnimationApi
@Composable
fun Navigation(
    onTitleChange: (String) -> Unit,
    currentScreen: ScreenData,
    onScreenChange: (ScreenData) -> Unit,
    backStack: MutableList<ScreenData>
) {

    val onBack: () -> Unit = {
        if (backStack.count() > 1) {
            onScreenChange(backStack[backStack.lastIndex-1])
            backStack.removeLast()
        } else {
            onScreenChange(ScreenData(ScreenList.main_screen, ""))
        }
    }

    if (backStack.last() != currentScreen) {
        backStack.add(currentScreen)
    }

    when(currentScreen.Screen) {
        ScreenList.main_screen -> {
            SimpleList(
                onScreenChange,
                onTitleChange
            )
            onTitleChange(stringResource(id = R.string.Toolbar_MainScreen_title))
        }
        ScreenList.timetable_screen -> {
            BackPressHandler(onBackPressed = onBack)
            TimeTableView(currentScreen.Key)
        }
        ScreenList.settings_screen -> {
            BackPressHandler(onBackPressed = onBack)
            SettingsScreen()
        }
        ScreenList.about_screen -> {
            BackPressHandler(onBackPressed = onBack)
            onTitleChange("О приложении")
            AboutScreen()
        }
        ScreenList.update_TimeTable -> {
            var updateState by remember { mutableStateOf(false) }
                LaunchedEffect(true)
            {
                val job = launch(Dispatchers.IO) {  htmlParser.getTimeTable(currentScreen.Key) }
                job.join()
                updateState = job.isCompleted
            }
            if (updateState) {
                onScreenChange(ScreenData(ScreenList.timetable_screen, currentScreen.Key))
            }
        }
    }
}