package ru.blays.timetable.domain.models

data class SettingsModel(
    val appTheme: Int? = null,
    val monetTheme: Boolean? = null,
    val accentColor: Int? = null,
    val favorite: String? = null,
    val favoriteSource: Int? = null,
    val firstStart: Boolean? = null,
    val openFavoriteOnStart: Boolean? = null,
    val showTimeLabel: Boolean? = null
)