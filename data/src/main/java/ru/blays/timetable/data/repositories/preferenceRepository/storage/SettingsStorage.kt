package ru.blays.timetable.data.repositories.preferenceRepository.storage

interface SettingsStorage {

    // get values //

    var appTheme: Int

    var monetTheme: Boolean

    var accentColor: Int

    var favorite: String

    var firstStart: Boolean

}