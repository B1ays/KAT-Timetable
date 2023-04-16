package ru.blays.timetable.domain.models

data class GetDaysListModel(
    val day: String,
    val href: String,
    val subjects: MutableList<GetSubjectsListModel> = mutableListOf()
)
