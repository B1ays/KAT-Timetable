package ru.blays.timetable.UI.Compose.ComposeElements.navigation

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import ru.blays.timetable.UI.ScreenData
import ru.blays.timetable.UI.ScreenList

class NavigationVM : ViewModel() {

    var currentScreen by mutableStateOf(ScreenData(ScreenList.MAIN_SCREEN))

    val backStack = mutableListOf(ScreenData(ScreenList.MAIN_SCREEN))

    fun addToBackStack(currentScreen: ScreenData) {
        backStack.add(currentScreen)
    }

    fun changeScreen(newScreen: ScreenData) {
        Log.d("screenChange", "changeScreen: $newScreen")
        currentScreen = newScreen
    }

}