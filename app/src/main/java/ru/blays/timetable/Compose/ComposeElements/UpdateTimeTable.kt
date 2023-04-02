package ru.blays.timetable.Compose.ComposeElements

import android.util.Log
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.blays.timetable.Compose.ScreenData
import ru.blays.timetable.Compose.ScreenList
import ru.blays.timetable.Compose.States.AlertDialogState
import ru.blays.timetable.Compose.States.ScreenState
import ru.blays.timetable.Compose.helperClasses.CurrentTimeTable
import ru.blays.timetable.Compose.htmlParser



fun updateTimeTable() {
    CoroutineScope(Dispatchers.IO).launch {
        try {
        htmlParser.getTimeTable(ScreenState.currentScreen.Key)
        CurrentTimeTable.getTimeTable(ScreenState.currentScreen.Key)
        } catch (e: Exception) {
            Log.d("getlog", e.toString())
            AlertDialogState.changeText(e.toString())
            AlertDialogState.changeState()
        }
        ScreenState.changeScreen(ScreenData(ScreenList.timetable_screen, ScreenState.currentScreen.Key))
    }
}