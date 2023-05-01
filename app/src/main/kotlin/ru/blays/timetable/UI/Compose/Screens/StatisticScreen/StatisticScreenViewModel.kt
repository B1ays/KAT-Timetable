package ru.blays.timetable.UI.Compose.Screens.StatisticScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.blays.timetable.domain.models.ChartModel
import ru.blays.timetable.domain.useCases.GetChartsForGroupUseCase

class StatisticScreenViewModel(private val getChartsForGroupUseCase: GetChartsForGroupUseCase) : ViewModel() {

    var list by mutableStateOf(listOf<ChartModel>())

    var isLoaded by mutableStateOf(false)

    fun get(href: String) {
        CoroutineScope(Dispatchers.IO).launch {
            /*isLoaded = true*/
            list = getChartsForGroupUseCase.execute(href)
            /*isLoaded = list.isEmpty()*/
        }
    }
}