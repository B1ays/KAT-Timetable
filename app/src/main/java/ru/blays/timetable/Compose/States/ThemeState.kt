package ru.blays.timetable.Compose.States

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import ru.blays.timetable.Compose.helperClasses.AccentColorItem
import ru.blays.timetable.Compose.helperClasses.AccentColorList
import ru.blays.timetable.Compose.helperClasses.createDarkTheme
import ru.blays.timetable.Compose.helperClasses.createLightTheme

object ThemeState {
    var isDarkMode by mutableStateOf(true)

    var isDynamicColor by mutableStateOf(true)

    private var accentColor = AccentColorList.list[1]

    private const val ColorOffsetOnPrimary = 1.7F

    private const val ColorOffsetSurfaceVariantDark = 10F
    private const val ColorOffsetSurfaceVariantLight = 1.2F

    var DarkColorScheme by mutableStateOf(darkColorScheme())

    var LightColorScheme by mutableStateOf(lightColorScheme())

    fun changeTheme() {
        isDarkMode = !isDarkMode
    }

    fun changeDynamicColor() {
        isDynamicColor = !isDynamicColor
    }

    fun changeAccentColor(item: AccentColorItem) {
        DarkColorScheme = createDarkTheme(color = item.accentDark)
        LightColorScheme = createLightTheme(color = item.accentLight)
    }

}