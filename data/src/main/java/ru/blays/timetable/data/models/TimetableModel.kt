package ru.blays.timetable.data.models

import ru.blays.timetable.data.models.ObjectBox.Boxes.DaysInTimeTableBox

data class TimetableModel(
    val daysWithSubjectsList: List<DaysInTimeTableBox>,
    val href: String,
    val updateDate: String,
    val groupCode: String
)
