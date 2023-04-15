package ru.blays.timetable.domain.models


data class SaveTimetableModel(
    val boxModel: GetDaysListModel,
    val href: String,
    val updateTime: String
)
