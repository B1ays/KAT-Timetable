package ru.blays.timetable.Compose.HelperClasses

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object CurrentTimeTable {

    var currentHref = ""
    var isUpdate by mutableStateOf(false)
    var updateTime by mutableStateOf("")
    var groupCode by mutableStateOf("")
    var daysList by mutableStateOf(listOf<ru.blays.timetable.data.models.ObjectBox.Boxes.DaysInTimeTableBox>())

    /*@Composable
    fun UpdateTimetableObject() {
        if (ScreenState.currentScreen.Screen == ScreenList.timetable_screen) {
            getTimeTable(ScreenState.currentScreen.Key)
            isUpdate = false
        }
    }*/

    /*fun getTimeTable(href: String) {
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
    }*/
}