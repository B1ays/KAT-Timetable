package ru.blays.timetable.Compose.helperClasses

import android.annotation.SuppressLint
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import ru.blays.timetable.Compose.objectBoxManager

@SuppressLint("MutableCollectionMutableState")
object GroupList {

    var groupList by  mutableStateOf(objectBoxManager.getGroupListFromBox()!!)

    fun updateGroupList() {
        groupList = objectBoxManager.getGroupListFromBox()!!
    }
}