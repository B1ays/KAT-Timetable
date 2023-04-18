package ru.blays.timetable.UI.Compose.Screens.SettingsScreen

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.blays.timetable.data.repository.preferenceRepository.SettingsRepositoryImpl
import ru.blays.timetable.domain.useCases.GetSettingsUseCase
import ru.blays.timetable.domain.useCases.SetSettingsUseCase

class SettingsScreenVMFactory(context: Context) : ViewModelProvider.Factory {

    private val settingsRepositoryImpl = SettingsRepositoryImpl(
        context = context
    )

    private val setSettingsUseCase = SetSettingsUseCase(settingsRepositoryInterface = settingsRepositoryImpl)

    private val getSettingsUseCase = GetSettingsUseCase(settingsRepositoryInterface = settingsRepositoryImpl)

    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return SettingsScreenVM(setSettingsUseCase, getSettingsUseCase) as T
    }
}