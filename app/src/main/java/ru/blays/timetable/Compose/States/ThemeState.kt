package ru.blays.timetable.Compose.States

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object ThemeState {
    var isDarkMode by mutableStateOf(false)

    var isDynamicColor by mutableStateOf(false)

    fun changeTheme(isDarkTheme: Boolean) {
        isDarkMode = isDarkTheme
    }

    fun changeDynamicColor(isDynamicColors: Boolean) {
        isDynamicColor = isDynamicColors
    }

}