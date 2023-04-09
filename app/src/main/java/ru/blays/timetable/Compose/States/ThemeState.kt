package ru.blays.timetable.Compose.States

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import ru.blays.timetable.Compose.HelperClasses.AccentColorItem
import ru.blays.timetable.Compose.HelperClasses.createDarkTheme
import ru.blays.timetable.Compose.HelperClasses.createLightTheme

object ThemeState {

    var isDarkMode  by mutableStateOf(false)

    var isDynamicColor by mutableStateOf(true)

    var DarkColorScheme by mutableStateOf(darkColorScheme())

    var LightColorScheme by mutableStateOf(lightColorScheme())


    fun changeDynamicColor() {
        isDynamicColor = !isDynamicColor
    }

    fun changeAccentColor(item: AccentColorItem) {
        DarkColorScheme = createDarkTheme(color = item.accentDark)
        LightColorScheme = createLightTheme(color = item.accentLight)
    }
}