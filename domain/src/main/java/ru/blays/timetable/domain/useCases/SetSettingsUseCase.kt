package ru.blays.timetable.domain.useCases

import ru.blays.timetable.domain.models.SettingsModel
import ru.blays.timetable.domain.repository.SettingsRepositoryInterface

class SetSettingsUseCase(private val settingsRepositoryInterface: SettingsRepositoryInterface) {

    fun execut(settingsModel: SettingsModel) {
        if (settingsModel.appTheme != null) {
                settingsRepositoryInterface.appTheme = settingsModel.appTheme
            }

        if (settingsModel.monetTheme != null) {
            settingsRepositoryInterface.monetTheme = settingsModel.monetTheme
        }

        if (settingsModel.accentColor != null) {
            settingsRepositoryInterface.accentColor = settingsModel.accentColor
        }

        if (settingsModel.accentColor != null) {
            settingsRepositoryInterface.accentColor = settingsModel.accentColor
        }

        if (settingsModel.favorite != null) {
            settingsRepositoryInterface.favorite = settingsModel.favorite
        }

        if (settingsModel.firstStart != null) {
            settingsRepositoryInterface.firstStart = settingsModel.firstStart
        }

        if (settingsModel.openFavoriteOnStart!= null) {
            settingsRepositoryInterface.openFavoriteOnStart = settingsModel.openFavoriteOnStart
        }
    }
}