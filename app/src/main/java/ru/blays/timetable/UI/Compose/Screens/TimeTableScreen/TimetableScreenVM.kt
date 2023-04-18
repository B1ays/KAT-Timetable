package ru.blays.timetable.UI.Compose.Screens.TimeTableScreen

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.withContext
import ru.blays.timetable.domain.models.GetTimetableModel
import ru.blays.timetable.domain.useCases.GetTimetableUseCase

class TimetableScreenVM(
    private val getTimetableUseCase: GetTimetableUseCase
) : ViewModel() {

    private var currentHref = ""

    var timetable by mutableStateOf(
        GetTimetableModel(
            daysWithSubjectsList = listOf(),
            href = "",
            updateDate = "",
            groupCode = ""
        )
    )

    var showProgress by mutableStateOf(false)

    suspend fun get(href: String) {
        showProgress = true
        withContext(Dispatchers.IO) {
            getTimetableUseCase.execut(href = href).run {
                if (success) timetable = this
                showProgress = !success
                currentHref = href
                Log.d("getLog", "return: $this")
            }
        }
    }

    suspend fun update() = coroutineScope {
        showProgress = true
        getTimetableUseCase.execut(href = currentHref, isUpdate = true).run {
            if (success) timetable = this
            showProgress = !success
            Log.d("getLog", "return: $this")
        }
    }
}