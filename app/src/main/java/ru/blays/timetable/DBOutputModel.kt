package ru.blays.timetable

data class DBOutputModel(
    val date: String,
    val cell: ArrayList<SecTableModel>
)
