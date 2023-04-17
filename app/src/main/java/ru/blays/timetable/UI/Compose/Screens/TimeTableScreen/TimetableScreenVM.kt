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
import ru.blays.timetable.domain.repository.MediatingRepository.MediatingRepository
import ru.blays.timetable.domain.useCases.GetTimetableUseCase

class TimetableScreenVM(
    private val getTimetableUseCase: GetTimetableUseCase,
    private val mediatingRepository: MediatingRepository
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
                timetable = this.first
                showProgress = this.second
                Log.d("getLog", "showProgress: $showProgress")
            }
            mediatingRepository.currentGroupCode = timetable.groupCode
            currentHref = timetable.href
            mediatingRepository.appBarStateCall()
        }
    }

    suspend fun update() = coroutineScope {
        showProgress = true
        getTimetableUseCase.execut(href = currentHref, isUpdate = true).apply {
            timetable = this.first
            showProgress = this.second
        }
    }
}