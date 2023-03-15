package ru.blays.timetable.Models

data class DBOutputModel(
    val date: String,
    val cell: ArrayList<SecTableModel>
)
