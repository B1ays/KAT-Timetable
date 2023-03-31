package ru.blays.timetable.Compose.States

import ru.blays.timetable.Compose.ScreenData
import ru.blays.timetable.Compose.ScreenList

object BackStackState {

    val backStack = mutableListOf(ScreenData(ScreenList.main_screen, ""))

    fun addToBackStack(currentScreen: ScreenData) {
        backStack.add(currentScreen)
    }
}