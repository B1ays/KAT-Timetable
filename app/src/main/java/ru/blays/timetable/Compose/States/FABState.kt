package ru.blays.timetable.Compose.States

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object FABState {
    var isExpanded by  mutableStateOf(false)

    fun changeExpanded() {
        isExpanded = !isExpanded
    }

}