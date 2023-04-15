package ru.blays.timetable.Compose.States

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import ru.blays.timetable.Compose.ScreenData
import ru.blays.timetable.Compose.ScreenList

object ScreenState {

    var currentScreen by mutableStateOf(ScreenData(ScreenList.main_screen, ""))


    fun changeScreen(newScreen: ScreenData) {
        currentScreen = newScreen
    }

}