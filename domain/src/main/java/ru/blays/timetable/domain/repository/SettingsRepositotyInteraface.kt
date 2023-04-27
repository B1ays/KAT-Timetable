package ru.blays.timetable.domain.repository

interface SettingsRepositoryInterface {

    var appTheme: Int

    var monetTheme: Boolean

    var accentColor: Int

    var favorite: String

    var favoriteSource: Int

    var firstStart: Boolean

    var  openFavoriteOnStart: Boolean

}