package ru.blays.timetable.Models

data class SecTableModel(
    val position: String,
    val subject: String,
    val lecturer: String,
    val auditory: String,
    val foreignKey: Int?
)
