package ru.blays.timetable.Compose.helperClasses

import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.blays.timetable.Compose.States.AlertDialogState
import ru.blays.timetable.Compose.htmlParser
import ru.blays.timetable.Compose.objectBoxManager
import ru.blays.timetable.ObjectBox.Boxes.DaysInTimeTableBox

object CurrentTimeTable {

    var CurrentHref = ""
    var groupCode by mutableStateOf("")
    var daysList by mutableStateOf(listOf<DaysInTimeTableBox>())

    fun getTimeTable(href: String) {
        if (CurrentHref != href || daysList.isEmpty()) {
            CurrentHref = href
            val query = objectBoxManager.getDaysFromTable(href)[0]
            groupCode = query.groupCode
            Log.d("getLog", groupCode)

            if (query.days.isNotEmpty()) {
            daysList = query.days
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        Log.d("getLog", "get timetable")
                        htmlParser.getTimeTable(href)
                        val query = objectBoxManager.getDaysFromTable(href)[0]
                        groupCode = query.groupCode
                        daysList = query.days
                    } catch (e: Exception) {
                        Log.d("getlog", e.toString())
                        AlertDialogState.changeText(e.toString())
                        AlertDialogState.changeState()
                    }
                }
            }
        }
    }
}