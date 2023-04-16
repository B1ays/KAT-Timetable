package ru.blays.timetable.UI.Compose.Screens.SettingsScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import ru.blays.timetable.domain.models.SettingsModel
import ru.blays.timetable.domain.useCases.GetSettingsUseCase
import ru.blays.timetable.domain.useCases.SetSettingsUseCase

class SettingsScreenVM(
    private val setSettingsUseCase: SetSettingsUseCase,
    private val getSettingsUseCase: GetSettingsUseCase
) : ViewModel() {
    var settings = get()
        private set


    fun set(settingsModel: SettingsModel) {
        setSettingsUseCase.execut(settingsModel)
    }

    fun get() : SettingsModel {
        return getSettingsUseCase.execut()
    }

    var radioButtonSelectionState by  mutableStateOf(settings.appTheme)

}