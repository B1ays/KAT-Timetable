package ru.blays.timetable.domain.useCases

import ru.blays.timetable.domain.models.GetTimetableModel
import ru.blays.timetable.domain.repository.TimetableRepositoryInterface
import ru.blays.timetable.domain.repository.WebRepositoryInterface

class GetTimetableUseCase(
    private val timetableRepositoryInterface: TimetableRepositoryInterface,
    private val webRepositoryInterface: WebRepositoryInterface,
    private val parseTimetableUseCase: ParseTimetableUseCase) {

    suspend fun execut(href: String) : GetTimetableModel {
        val timetable = timetableRepositoryInterface.getDaysList(href = href)

        if (timetable.daysWithSubjectsList.isNotEmpty()) {
            return timetable
        } else {
            val htmlBody = webRepositoryInterface.getHTMLBody(href)
            return parseTimetableUseCase.execut(htmlBody!!, href)
        }
    }

}