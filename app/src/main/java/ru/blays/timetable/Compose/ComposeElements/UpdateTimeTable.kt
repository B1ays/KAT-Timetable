package ru.blays.timetable.Compose.ComposeElements

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.blays.timetable.Compose.HelperClasses.CurrentTimeTable
import ru.blays.timetable.Compose.ScreenData
import ru.blays.timetable.Compose.ScreenList
import ru.blays.timetable.Compose.States.ScreenState

fun updateTimeTable() {
    CoroutineScope(Dispatchers.IO).launch {
        CurrentTimeTable.daysList = listOf()
        /*try {

        htmlParser.getTimeTable(ScreenState.currentScreen.Key)
        } catch (e: Exception) {
            Log.d("getlog", e.toString())
            AlertDialogState.changeText(e.toString())
            AlertDialogState.changeState()
        }*/
        ScreenState.changeScreen(ScreenData(ScreenList.timetable_screen, ScreenState.currentScreen.Key))
    }
}