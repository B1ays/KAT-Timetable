package ru.blays.timetable.UI.Compose

import android.content.Context
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.blays.timetable.data.repository.preferenceRepository.SettingsRepositoryImpl
import ru.blays.timetable.domain.repository.MediatingRepository.MediatingRepository
import ru.blays.timetable.domain.useCases.GetSettingsUseCase

class MainViewModelFactory(private val mediatingRepository: MediatingRepository, context: Context) : ViewModelProvider.Factory {

    private val settingsRepositoryImpl = SettingsRepositoryImpl(
        context = context
    )

    private val getSettingsUseCase = GetSettingsUseCase(settingsRepositoryInterface = settingsRepositoryImpl)

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return MainViewModel(mediatingRepository, getSettingsUseCase) as T
    }
}