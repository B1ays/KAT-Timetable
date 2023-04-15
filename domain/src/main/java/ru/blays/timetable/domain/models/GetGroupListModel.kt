package ru.blays.timetable.domain.models

data class GetGroupListModel(
    val groupCode: String? = null,
    val href: String? = null,
    val updateTime: String? = null,
    var days: List<GetDaysListModel>? = null
)
