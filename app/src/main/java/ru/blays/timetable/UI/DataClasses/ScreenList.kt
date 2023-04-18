package ru.blays.timetable.UI

data class ScreenData(
    var Screen: String,
    var Key: String = ""
)
object ScreenList {
    const val MAIN_SCREEN = "main_screen"
    const val TIMETABLE_SCREEN = "timetable_screen"
    const val SETTINGS_SCREEN = "settings_screen"
    const val ABOUT_SCREEN = "about_screen"
}