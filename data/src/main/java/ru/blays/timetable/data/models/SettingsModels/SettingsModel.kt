package ru.blays.timetable.data.models.SettingsModels

data class SettingsModel(
    val appTheme: Int? = null,
    val monetTheme: Boolean? = null,
    val accentColor: Int? = null,
    val favorite: String? = null,
    val firstStart: Boolean? = null
)