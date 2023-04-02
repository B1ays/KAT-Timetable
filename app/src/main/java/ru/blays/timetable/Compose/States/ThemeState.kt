package ru.blays.timetable.Compose.States

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import ru.blays.timetable.Compose.helperClasses.AccentColorItem
import ru.blays.timetable.Compose.helperClasses.AccentColorList

object ThemeState {
    var isDarkMode by mutableStateOf(true)

    var isDynamicColor by mutableStateOf(true)

    private var accentColor = AccentColorList.list[0]

    private const val ColorOffset = 1.7F

    var DarkColorScheme by mutableStateOf(darkColorScheme(
        primary = accentColor.accentDark,
        secondary = accentColor.accentDark.copy(alpha = 0.8F),
        onPrimary = accentColor.accentDark.copy(
            red = (accentColor.accentDark.red+(1-accentColor.accentDark.red)/ColorOffset),
            blue = (accentColor.accentDark.blue+(1-accentColor.accentDark.blue)/ColorOffset),
            green = (accentColor.accentDark.green+(1-accentColor.accentDark.green)/ColorOffset)
        )
    ))

    var LightColorScheme by mutableStateOf(lightColorScheme(
        primary = accentColor.accentLight,
        secondary = accentColor.accentLight.copy(alpha = 0.8F),
        onPrimary = accentColor.accentLight.copy(
            red = (accentColor.accentLight.red+(1-accentColor.accentLight.red)/ColorOffset),
            blue = (accentColor.accentLight.blue+(1-accentColor.accentLight.blue)/ColorOffset),
            green = (accentColor.accentLight.green+(1-accentColor.accentLight.green)/ColorOffset)
        )
    ))

    fun changeTheme() {
        isDarkMode = !isDarkMode
    }

    fun changeDynamicColor() {
        isDynamicColor = !isDynamicColor
    }

    fun changeAccentColor(item: AccentColorItem) {
        DarkColorScheme = darkColorScheme(
            primary = item.accentDark,
            secondary = item.accentDark.copy(alpha = 0.8F),
            onPrimary = item.accentDark.copy(
                red = (item.accentDark.red+(1-item.accentDark.red)/ColorOffset),
                blue = (item.accentDark.blue+(1-item.accentDark.blue)/ColorOffset),
                green = (item.accentDark.green+(1-item.accentDark.green)/ColorOffset)
            )
        )
        LightColorScheme = lightColorScheme(
            primary = item.accentLight,
            secondary = item.accentLight.copy(alpha = 0.8F),
            onPrimary = item.accentLight.copy(
                red = (item.accentLight.red+(1-item.accentLight.red)/ColorOffset),
                blue = (item.accentLight.blue+(1-item.accentLight.blue)/ColorOffset),
                green = (item.accentLight.green+(1-item.accentLight.green)/ColorOffset)
            )
        )
    }

}