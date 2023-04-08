package ru.blays.timetable.Compose.helperClasses

import android.annotation.SuppressLint
import android.util.Log
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import ru.blays.timetable.Compose.States.AlertDialogState
import ru.blays.timetable.Compose.groupListBox
import ru.blays.timetable.Compose.htmlParser
import ru.blays.timetable.ObjectBox.Boxes.GroupListBox

@SuppressLint("MutableCollectionMutableState")
object GroupList {

    var groupList by mutableStateOf(listOf<GroupListBox>())

    fun checkDBState() {
        if (groupListBox.isEmpty) {

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    htmlParser.createMainDB()
                    groupList = groupListBox.all
                } catch (e: Exception) {
                    Log.d("getlog", e.toString())
                    AlertDialogState.changeText(e.toString())
                    AlertDialogState.changeState()
                }
            }
        } else {
            groupList = groupListBox.all
        }
    }
}