package ru.blays.timetable.UI.Compose.ComposeElements.navigation

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import ru.blays.timetable.UI.ScreenData
import ru.blays.timetable.UI.ScreenList
import ru.blays.timetable.domain.repository.MediatingRepository.MediatingRepository

class NavigationVM(val mediatingRepository: MediatingRepository) : ViewModel() {

    var currentScreen by mutableStateOf(ScreenData(ScreenList.main_screen))

    val backStack = mutableListOf(ScreenData(ScreenList.main_screen, ""))

    fun addToBackStack(currentScreen: ScreenData) {
        backStack.add(currentScreen)
    }

    fun changeScreen(newScreen: ScreenData) {
        currentScreen = newScreen
        mediatingRepository.currentScreen = newScreen.Screen
        mediatingRepository.appBarStateCall()
    }

}