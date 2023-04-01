package ru.blays.timetable.Compose.ComposeElements

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import ru.blays.timetable.Compose.ScreenData
import ru.blays.timetable.Compose.ScreenList
import ru.blays.timetable.Compose.States.ScreenState
import ru.blays.timetable.htmlParser


fun updateTimeTable() {
    val job = CoroutineScope(Dispatchers.IO)
    job.launch {
        htmlParser.getTimeTable(ScreenState.currentScreen.Key)
        delay(5000)
        ScreenState.changeScreen(ScreenData(ScreenList.timetable_screen, ScreenState.currentScreen.Key))
    }
}