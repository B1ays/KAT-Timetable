package ru.blays.timetable.Compose.ComposeElements.custom_toolbar

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import ru.blays.timetable.Compose.ScreenData
import ru.blays.timetable.Compose.ScreenList

class NavigationVM : ViewModel() {

    var currentScreen by mutableStateOf(ScreenData(ScreenList.main_screen))

    val backStack = mutableListOf(ScreenData(ScreenList.main_screen, ""))

    fun addToBackStack(currentScreen: ScreenData) {
        backStack.add(currentScreen)
    }

    fun changeScreen(newScreen: ScreenData) {
        currentScreen = newScreen
    }

}