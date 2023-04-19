package ru.blays.timetable.UI.Compose

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.blays.timetable.data.repository.preferenceRepository.SettingsRepositoryImpl
import ru.blays.timetable.domain.useCases.GetSettingsUseCase
import ru.blays.timetable.domain.useCases.SetSettingsUseCase

class MainViewModelFactory(context: Context) : ViewModelProvider.Factory {

    private val settingsRepositoryImpl = SettingsRepositoryImpl(
        context = context
    )

    private val getSettingsUseCase = GetSettingsUseCase(settingsRepositoryInterface = settingsRepositoryImpl)

    private val setSettingsUseCase = SetSettingsUseCase(settingsRepositoryInterface = settingsRepositoryImpl)

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(getSettingsUseCase, setSettingsUseCase) as T
    }
}