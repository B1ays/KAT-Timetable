package ru.blays.timetable.UI.Compose.Screens.SettingsScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import ru.blays.timetable.domain.models.SettingsModel
import ru.blays.timetable.domain.repository.MediatingRepository.MediatingRepository
import ru.blays.timetable.domain.useCases.GetSettingsUseCase
import ru.blays.timetable.domain.useCases.SetSettingsUseCase

class SettingsScreenVM(
    private val setSettingsUseCase: SetSettingsUseCase,
    private val getSettingsUseCase: GetSettingsUseCase,
    private val mediatingRepository: MediatingRepository
) : ViewModel() {

    var settings = get()
        private set


    fun set(settingsModel: SettingsModel) {
        setSettingsUseCase.execut(settingsModel)
    }

    fun get() : SettingsModel {
        return getSettingsUseCase.execut()
    }

    fun changeTheme(darkMode: Boolean) {
        mediatingRepository.isDarkMode = darkMode
        mediatingRepository.themeChangeCall()
    }

    fun changeMonetUsage(isMonetTheme: Boolean) {
        mediatingRepository.isMonetTheme = isMonetTheme
        monetTheme = isMonetTheme
        setSettingsUseCase.execut(SettingsModel(monetTheme = isMonetTheme))
        mediatingRepository.monetChangeCall()
    }

    var radioButtonSelectionState by  mutableStateOf(settings.appTheme)

    var monetTheme by mutableStateOf(settings.monetTheme)
}