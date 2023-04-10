package ru.blays.timetable.Compose.ComposeElements

import ru.blays.timetable.Compose.HelperClasses.CurrentTimeTable
import ru.blays.timetable.Compose.ScreenData
import ru.blays.timetable.Compose.ScreenList
import ru.blays.timetable.Compose.States.ScreenState

fun updateTimeTable() {
    CurrentTimeTable.daysList = listOf()
    CurrentTimeTable.isUpdate = true
    ScreenState.changeScreen(ScreenData(ScreenList.timetable_screen, ScreenState.currentScreen.Key))
}