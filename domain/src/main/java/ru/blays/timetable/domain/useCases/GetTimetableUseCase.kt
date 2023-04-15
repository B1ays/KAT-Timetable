package ru.blays.timetable.domain.useCases

import ru.blays.timetable.domain.models.GetTimetableModel
import ru.blays.timetable.domain.repository.TimetableRepositoryInterface

class GetTimetableUseCase(private val timetableRepositoryInterface: TimetableRepositoryInterface) {

    fun execut(href: String) : GetTimetableModel {
        return timetableRepositoryInterface.getDaysList(href = href)
    }

}