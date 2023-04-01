package ru.blays.timetable.Compose.helperClasses

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.blays.timetable.Compose.htmlParser
import ru.blays.timetable.Compose.objectBoxManager
import ru.blays.timetable.ObjectBox.Boxes.DaysInTimeTableBox

object CurrentTimeTable {

    var CurrentHref = ""
    var daysList by mutableStateOf(listOf<DaysInTimeTableBox>())

    fun getTimeTable(href: String) {
        if (CurrentHref != href) {
            CurrentHref = href
            val list = objectBoxManager.getDaysFromTable(href)[0].days
            if (list.isNotEmpty()) {
                daysList = list
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    htmlParser.getTimeTable(href)
                    daysList = objectBoxManager.getDaysFromTable(href)[0].days
                }
            }
        }
    }
}