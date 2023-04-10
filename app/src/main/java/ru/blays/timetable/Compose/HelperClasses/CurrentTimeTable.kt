package ru.blays.timetable.Compose.HelperClasses

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.blays.timetable.Compose.ScreenList
import ru.blays.timetable.Compose.States.AlertDialogState
import ru.blays.timetable.Compose.States.ScreenState
import ru.blays.timetable.Compose.htmlParser
import ru.blays.timetable.Compose.objectBoxManager
import ru.blays.timetable.ObjectBox.Boxes.DaysInTimeTableBox

object CurrentTimeTable {

    var currentHref = ""
    var isUpdate by mutableStateOf(false)
    var updateTime by mutableStateOf("")
    var groupCode by mutableStateOf("")
    var daysList by mutableStateOf(listOf<DaysInTimeTableBox>())

    @Composable
    fun UpdateTimetableObject() {
        if (ScreenState.currentScreen.Screen == ScreenList.timetable_screen) {
            getTimeTable(ScreenState.currentScreen.Key)
            isUpdate = false
        }
    }

    fun getTimeTable(href: String) {
        if (currentHref != href || daysList.isEmpty()) {
            currentHref = href
            val query = objectBoxManager.getDaysFromTable(href)[0]
            groupCode = query.groupCode
            updateTime = query.updateTime
            Log.d("getLog", groupCode)

            if (query.days.isNotEmpty() && !isUpdate) {
            daysList = query.days
            } else {
                CoroutineScope(Dispatchers.IO).launch {
                    try {
                        htmlParser.getTimeTable(href)
                        val query = objectBoxManager.getDaysFromTable(href)[0]
                        groupCode = query.groupCode
                        updateTime = query.updateTime
                        daysList = query.days
                    } catch (e: Exception) {
                        Log.d("getlog", e.toString())
                        AlertDialogState.changeText(e.toString())
                        AlertDialogState.openDialog()
                    }
                }
            }
        }
    }
}