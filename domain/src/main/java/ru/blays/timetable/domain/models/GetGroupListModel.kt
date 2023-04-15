package ru.blays.timetable.domain.models

data class GetGroupListModel(
    val groupCode: String,
    val href: String,
    val updateTime: String,
    var days: List<GetDaysListModel>?
)
