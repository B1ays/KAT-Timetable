package ru.blays.timetable.UI

data class ScreenData(
    var Screen: String,
    var Key: TimetableKey = TimetableKey(0, "")
)

data class TimetableKey(
    val source: Int,
    val href: String
)

object ScreenList {
    const val MAIN_SCREEN = "main_screen"
    const val TIMETABLE_SCREEN = "timetable_screen"
    const val SETTINGS_SCREEN = "settings_screen"
    const val ABOUT_SCREEN = "about_screen"
}