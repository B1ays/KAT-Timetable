package ru.blays.timetable.domain.models

data class GetSubjectsListModel(
    val position: String,
    val subgroups: String,
    val subject: String,
    val lecturer: String,
    val auditory: String
)
