package ru.blays.timetable.data.models

import ru.blays.timetable.data.models.ObjectBox.Boxes.DaysInTimeTableBox

data class SaveTimetableModel(
    val boxModel: DaysInTimeTableBox,
    val href: String,
    val updateTime: String
)
