package ru.blays.timetable.Compose.Screens.TimeTableScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext

import ru.blays.timetable.domain.models.GetTimetableModel
import ru.blays.timetable.domain.useCases.GetTimetableUseCase
import ru.blays.timetable.domain.useCases.UpdateTimetableUseCase

class TimetableScreenVM(
    private val getTimetableUseCase: GetTimetableUseCase,
    private val updateTimetableUseCase: UpdateTimetableUseCase
) : ViewModel() {

    var timetable by mutableStateOf(
        GetTimetableModel(
            daysWithSubjectsList = listOf(),
            href = "",
            updateDate = "",
            groupCode = ""
        )
    )

    suspend fun get(href: String) {
        withContext(Dispatchers.IO) {
            timetable = getTimetableUseCase.execut(href = href)
        }
    }

}