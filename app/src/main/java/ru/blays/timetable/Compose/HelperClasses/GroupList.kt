package ru.blays.timetable.Compose.HelperClasses

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import ru.blays.timetable.data.models.ObjectBox.Boxes.GroupListBox

@SuppressLint("MutableCollectionMutableState")
object GroupList {

    var groupList by mutableStateOf(listOf<GroupListBox>())

    /*fun checkDBState() {
        if (groupListBox.isEmpty) {

            CoroutineScope(Dispatchers.IO).launch {
                try {
                    htmlParser.createMainDB()
                    groupList = groupListBox.all
                } catch (e: Exception) {
                    Log.d("getlog", e.toString())
                    AlertDialogState.changeText(e.toString())
                    AlertDialogState.openDialog()
                }
            }
        } else {
            groupList = groupListBox.all
        }
    }*/
}