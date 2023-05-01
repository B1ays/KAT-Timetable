package ru.blays.timetable.domain.models

data class ChartModel(
    var lecturer: String = "",
    var subject: String = "",
    var totalHours: String = "",
    var hoursLeft: String = "",
    var percent: Float = 0F
)