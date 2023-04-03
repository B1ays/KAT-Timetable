package ru.blays.timetable.Compose.States

import android.util.Log
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

    private var accentColor = AccentColorList.list[1]

    private const val ColorOffsetOnPrimary = 1.7F

    private const val ColorOffsetSurfaceVariantDark = 10F
    private const val ColorOffsetSurfaceVariantLight = 1.2F

    var DarkColorScheme by mutableStateOf(darkColorScheme(
        primary = accentColor.accentDark,
        secondary = accentColor.accentDark.copy(alpha = 0.8F),
        onPrimary = accentColor.accentDark.copy(
            red = (accentColor.accentDark.red+(1-accentColor.accentDark.red)/ColorOffsetOnPrimary),
            blue = (accentColor.accentDark.blue+(1-accentColor.accentDark.blue)/ColorOffsetOnPrimary),
            green = (accentColor.accentDark.green+(1-accentColor.accentDark.green)/ColorOffsetOnPrimary)
        ),
        surfaceVariant = accentColor.accentDark.copy(
            red = (accentColor.accentDark.red+(1-accentColor.accentDark.red)/ ColorOffsetSurfaceVariantDark),
            blue = (accentColor.accentDark.blue+(1-accentColor.accentDark.blue)/ ColorOffsetSurfaceVariantDark),
            green = (accentColor.accentDark.green+(1-accentColor.accentDark.green)/ ColorOffsetSurfaceVariantDark)
        )
    ))

    var LightColorScheme by mutableStateOf(lightColorScheme(
        primary = accentColor.accentLight,
        secondary = accentColor.accentLight.copy(alpha = 0.8F),
        onPrimary = accentColor.accentLight.copy(
            red = (accentColor.accentLight.red+(1-accentColor.accentLight.red)/ ColorOffsetOnPrimary),
            blue = (accentColor.accentLight.blue+(1-accentColor.accentLight.blue)/ColorOffsetOnPrimary),
            green = (accentColor.accentLight.green+(1-accentColor.accentLight.green)/ColorOffsetOnPrimary)
        ),
        surfaceVariant = accentColor.accentDark.copy(
            red = (accentColor.accentDark.red+(1-accentColor.accentDark.red)/ ColorOffsetSurfaceVariantLight),
            blue = (accentColor.accentDark.blue+(1-accentColor.accentDark.blue)/ ColorOffsetSurfaceVariantLight),
            green = (accentColor.accentDark.green+(1-accentColor.accentDark.green)/ ColorOffsetSurfaceVariantLight)
        )
    ))

    fun changeTheme() {
        isDarkMode = !isDarkMode
    }

    fun changeDynamicColor() {
        isDynamicColor = !isDynamicColor
    }

    fun changeAccentColor(item: AccentColorItem) {
        val priorityColor = listOf(item.accentDark.green, item.accentDark.blue, item.accentDark.red).max()
        Log.d("ColorLog", priorityColor.toString())
        Log.d("ColorLog", " Green: " + item.accentDark.green.toString() + " Blue: " + item.accentDark.blue.toString() + " Red: " + item.accentDark.red.toString())

        DarkColorScheme = darkColorScheme(
            primary = item.accentDark,
            secondary = item.accentDark.copy(alpha = 0.8F),
            onPrimary = item.accentDark.copy(
                red = (item.accentDark.red+(1-item.accentDark.red)/ColorOffsetOnPrimary),
                blue = (item.accentDark.blue+(1-item.accentDark.blue)/ColorOffsetOnPrimary),
                green = (item.accentDark.green+(1-item.accentDark.green)/ColorOffsetOnPrimary)
            ),
            surfaceVariant = item.accentDark.copy(
                red = (if (item.accentDark.red == priorityColor) priorityColor - (priorityColor/1.1F) else item.accentDark.red+(1-item.accentDark.red) / (priorityColor / item.accentDark.red)),
                blue = (if (item.accentDark.blue == priorityColor) priorityColor - (priorityColor/1.1F) else item.accentDark.blue+(1-item.accentDark.blue) / (priorityColor / item.accentDark.blue)),
                green = (if (item.accentDark.green == priorityColor) priorityColor - (priorityColor/1.1F) else item.accentDark.green+(1-item.accentDark.green) / (priorityColor / item.accentDark.green))
            /* red = (if (item.accentDark.red == priorityColor) priorityColor - (priorityColor/2) else item.accentDark.red+(1-item.accentDark.red) / ColorOffsetSurfaceVariantDark),
                blue = (if (item.accentDark.blue == priorityColor) priorityColor - (priorityColor/2) else item.accentDark.blue+(1-item.accentDark.blue) / ColorOffsetSurfaceVariantDark),
                green = (if (item.accentDark.green == priorityColor) priorityColor - (priorityColor/2) else item.accentDark.green+(1-item.accentDark.green) / ColorOffsetSurfaceVariantDark)*/
            ))
        LightColorScheme = lightColorScheme(
            primary = item.accentLight,
            secondary = item.accentLight.copy(alpha = 0.8F),
            onPrimary = item.accentLight.copy(
                red = (item.accentLight.red+(1-item.accentLight.red)/ColorOffsetOnPrimary),
                blue = (item.accentLight.blue+(1-item.accentLight.blue)/ColorOffsetOnPrimary),
                green = (item.accentLight.green+(1-item.accentLight.green)/ColorOffsetOnPrimary)
            ),
            surfaceVariant = item.accentDark.copy(
                red = (item.accentDark.red+(1-item.accentDark.red)/ ColorOffsetSurfaceVariantLight),
                blue = (item.accentDark.blue+(1-item.accentDark.blue)/ ColorOffsetSurfaceVariantLight),
                green = (item.accentDark.green+(1-item.accentDark.green)/ ColorOffsetSurfaceVariantLight)
            )
        )
    }

}