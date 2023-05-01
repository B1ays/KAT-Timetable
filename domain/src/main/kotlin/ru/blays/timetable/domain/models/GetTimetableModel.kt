package ru.blays.timetable.domain.models

data class GetTimetableModel(
    val daysWithSubjectsList: List<GetDaysListModel> = emptyList(),
    val href: String = "",
    val updateDate: String = "",
    val groupCode: String = "",
    val success: Boolean = true
)