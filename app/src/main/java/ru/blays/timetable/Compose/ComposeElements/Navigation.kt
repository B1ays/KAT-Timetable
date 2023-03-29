package ru.blays.timetable.Compose.ComposeElements

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.blays.timetable.Compose.ScreenData
import ru.blays.timetable.Compose.ScreenList
import ru.blays.timetable.Compose.Screens.AboutScreen
import ru.blays.timetable.ObjectBox.Boxes.GroupListBox
import ru.blays.timetable.R
import ru.blays.timetable.htmlParser
import ru.blays.timetable.objectBoxManager

@ExperimentalAnimationApi
@Composable
fun Navigation(
    onTitleChange: (String) -> Unit,
    currentScreen: ScreenData,
    onScreenChange: (ScreenData) -> Unit
) {
    var groupList by remember { mutableStateOf(listOf<GroupListBox>()) }

    val onBack = {
        if (currentScreen.Screen != ScreenList.main_screen) {
            onScreenChange(ScreenData(ScreenList.main_screen, ""))
        }
    }

    groupList = objectBoxManager.getGroupListFromBox()!!

    when(currentScreen.Screen) {
        ScreenList.main_screen -> {
            SimpleList(
                list = groupList,
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