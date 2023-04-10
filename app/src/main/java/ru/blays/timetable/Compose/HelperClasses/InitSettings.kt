package ru.blays.timetable.Compose.HelperClasses

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import ru.blays.timetable.Compose.HelperClasses.CurrentTimeTable.UpdateTimetableObject
import ru.blays.timetable.Compose.States.AppBarState
import ru.blays.timetable.Compose.States.AppBarState.UpdateAppBarState
import ru.blays.timetable.Compose.States.ThemeState
import ru.blays.timetable.Compose.prefs

@Composable
fun InitSettings() {

    UpdateAppBarState()

    UpdateTimetableObject()

    when(prefs.themePrefs) {
        0 -> ThemeState.isDarkMode = isSystemInDarkTheme()
        1 -> ThemeState.isDarkMode = true
        2 -> ThemeState.isDarkMode = false
    }

    ThemeState.isDynamicColor = prefs.monetPrefs

    ThemeState.changeAccentColor(AccentColorList.list[prefs.accentColorPrefs])

    AppBarState.currentFavoriteTimetable = prefs.favoriteTimetablePrefs!!

}