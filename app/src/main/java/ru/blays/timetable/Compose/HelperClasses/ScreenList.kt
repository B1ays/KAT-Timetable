package ru.blays.timetable.Compose

data class ScreenData(
    var Screen: String,
    var Key: String = ""
)
object ScreenList {
    const val main_screen = "MAIN_SCREEN"
    const val timetable_screen = "TIMETABLE_SCREEN"
    const val settings_screen = "SETTINGS_SCREEN"
    const val about_screen = "ABOUT_SCREEN"
    const val update_TimeTable = "UPDATE"
    const val progress_screen = "PROGRESS_SCREEN"
}