package ru.blays.timetable.Compose.States

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object ThemeState {
    var isDarkMode by mutableStateOf(true)

    var isDynamicColor by mutableStateOf(true)

    fun changeTheme(isDarkTheme: Boolean) {
        isDarkMode = !isDarkMode
    }

    fun changeDynamicColor(isDynamicColors: Boolean) {
        isDynamicColor = !isDynamicColor
    }

}