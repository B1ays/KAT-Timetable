package ru.blays.timetable.Compose.States

import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import ru.blays.timetable.Compose.ScreenList

object AppBarState {
    var titleText by mutableStateOf("defaultTitle")

    fun changeTitleText(text: String) {
        titleText = text
    }

    var navigateBackButtonVisible by mutableStateOf(false)

    var favoriteButtonVisible by mutableStateOf(false)

    var favoriteButtonChecked by mutableStateOf(false)

    var currentFavoriteTimetable by mutableStateOf("")

    @Composable
    fun UpdateAppBarState() {

        navigateBackButtonVisible = ScreenState.currentScreen.Screen != ScreenList.main_screen

        favoriteButtonVisible = ScreenState.currentScreen.Screen == ScreenList.timetable_screen

        favoriteButtonChecked =  ScreenState.currentScreen.Key == currentFavoriteTimetable

    }

}
