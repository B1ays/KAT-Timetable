package ru.blays.timetable.Compose.States

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import ru.blays.timetable.Compose.helperClasses.AccentColorItem
import ru.blays.timetable.Compose.helperClasses.createDarkTheme
import ru.blays.timetable.Compose.helperClasses.createLightTheme
import ru.blays.timetable.Compose.prefs

object ThemeState {

    var isDarkMode  by mutableStateOf(false)

    var isDynamicColor by mutableStateOf(true)

    var DarkColorScheme by mutableStateOf(darkColorScheme())

    var LightColorScheme by mutableStateOf(lightColorScheme())

    fun ChangeTheme(pref: Int) {
        when(prefs.themePrefs) {
            0 -> isDarkMode = false
            1 -> isDarkMode = true
            2 -> isDarkMode = false
            }
        }

    fun changeDynamicColor() {
        isDynamicColor = !isDynamicColor
    }

    fun changeAccentColor(item: AccentColorItem) {
        DarkColorScheme = createDarkTheme(color = item.accentDark)
        LightColorScheme = createLightTheme(color = item.accentLight)
    }
}