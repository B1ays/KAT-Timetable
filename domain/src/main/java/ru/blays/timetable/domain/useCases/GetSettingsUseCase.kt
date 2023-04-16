package ru.blays.timetable.domain.useCases

import ru.blays.timetable.domain.models.SettingsModel
import ru.blays.timetable.domain.repository.SettingsRepositoryInterface

class GetSettingsUseCase(private val settingsRepositoryInterface: SettingsRepositoryInterface) {


    fun execut() : SettingsModel {
        val accentColor = settingsRepositoryInterface.accentColor
        val appTheme = settingsRepositoryInterface.appTheme
        val monetTheme = settingsRepositoryInterface.monetTheme
        val favorite = settingsRepositoryInterface.favorite
        val firstStart = settingsRepositoryInterface.firstStart

        return SettingsModel(
            appTheme, monetTheme, accentColor, favorite, firstStart
        )
    }
}