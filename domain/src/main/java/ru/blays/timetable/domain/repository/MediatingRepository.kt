package ru.blays.timetable.domain.repository

class MediatingRepository {

    var currentScreen = ""

    var currentGroupCode = ""

    var callBack : () -> Unit = { }

    fun call() = run{ callBack() }
}
