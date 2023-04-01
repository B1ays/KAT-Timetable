package ru.blays.timetable.Compose.States

import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.Color

object ThemeState {
    var isDarkMode by mutableStateOf(true)

    var isDynamicColor by mutableStateOf(true)

    private var accentColor = Color(0xFF825BDD)

    var DarkColorScheme by mutableStateOf(darkColorScheme(
        primary = accentColor,
        secondary = accentColor.copy(alpha = 0.8F),
        onPrimary = accentColor.copy(
            red = (accentColor.red+(1-accentColor.red)/2.7).toFloat(),
            blue = (accentColor.blue+(1-accentColor.blue)/2.7).toFloat(),
            green = (accentColor.green+(1-accentColor.green)/2.7).toFloat()
        )
    ))

    var LightColorScheme by mutableStateOf(lightColorScheme(
        primary = accentColor,
        secondary = accentColor.copy(alpha = 0.8F),
        onPrimary = accentColor.copy(
            red = (accentColor.red+(1-accentColor.red)/2.7).toFloat(),
            blue = (accentColor.blue+(1-accentColor.blue)/2.7).toFloat(),
            green = (accentColor.green+(1-accentColor.green)/2.7).toFloat()
        )
    ))


    fun changeTheme() {
        isDarkMode = !isDarkMode
    }

    fun changeDynamicColor() {
        isDynamicColor = !isDynamicColor
    }

    fun changeAccentColor(color: Color) {
        DarkColorScheme = darkColorScheme(
            primary = color,
            secondary = color.copy(alpha = 0.8F),
            onPrimary = accentColor.copy(
                red = (color.red+(1-color.red)/2.7).toFloat(),
                blue = (color.blue+(1-color.blue)/2.7).toFloat(),
                green = (color.green+(1-color.green)/2.7).toFloat()
            )
        )
        LightColorScheme = lightColorScheme(
            primary = color,
            secondary = color.copy(alpha = 0.8F),
            onPrimary = accentColor.copy(
                red = (color.red+(1-color.red)/2.7).toFloat(),
                blue = (color.blue+(1-color.blue)/2.7).toFloat(),
                green = (color.green+(1-color.green)/2.7).toFloat()
            )
        )
    }

}