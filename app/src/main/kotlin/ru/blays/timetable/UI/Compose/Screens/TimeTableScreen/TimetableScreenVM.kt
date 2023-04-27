package ru.blays.timetable.UI.Compose.Screens.TimeTableScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.blays.timetable.UI.TimetableKey
import ru.blays.timetable.domain.models.GetTimetableModel
import ru.blays.timetable.domain.useCases.GetTimetableUseCase

class TimetableScreenVM(
    private val getTimetableUseCase: GetTimetableUseCase
) : ViewModel() {

    var currentHref = ""
        private set

    var currentSource = 0
        private set

    var timetable by mutableStateOf(
        GetTimetableModel(
            daysWithSubjectsList = listOf(),
            href = "",
            updateDate = "",
            groupCode = ""
        )
    )

    var isRefreshing by mutableStateOf(true)

    suspend fun get(key: TimetableKey) {
        isRefreshing = true
        withContext(Dispatchers.IO) {
            getTimetableUseCase.execut(href = key.href, source = key.source).run {
                if (success) timetable = this
                isRefreshing = !success
                currentHref = href
                currentSource = key.source
            }
        }
    }

    suspend fun update() {
        Log.d("updateLog", "Update event")
        isRefreshing = true
        getTimetableUseCase.execut(href = currentHref, source = currentSource, isUpdate = true).run {
            if (success) timetable = this
            isRefreshing = !success
        }
    }
}