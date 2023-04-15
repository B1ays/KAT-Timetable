package ru.blays.timetable.Compose.Screens.TimeTableScreen

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import ru.blays.timetable.domain.models.GetTimetableModel

class TimetableScreenVM() : ViewModel() {

    val timetable by mutableStateOf(
        GetTimetableModel(
            daysWithSubjectsList = emptyList(),
            href = "",
            updateDate = "",
            groupCode = ""
        )
    )
}