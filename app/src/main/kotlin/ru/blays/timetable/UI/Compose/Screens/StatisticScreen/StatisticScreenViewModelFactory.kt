package ru.blays.timetable.UI.Compose.Screens.StatisticScreen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import ru.blays.timetable.data.repository.webRepository.WebRepositoryImpl
import ru.blays.timetable.domain.useCases.GetChartsForGroupUseCase

class StatisticScreenViewModelFactory : ViewModelProvider.Factory {

    private val webRepositoryInterface = WebRepositoryImpl()

    private val getChartsForGroupUseCase = GetChartsForGroupUseCase(webRepositoryInterface)

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return StatisticScreenViewModel(getChartsForGroupUseCase) as T
    }

}