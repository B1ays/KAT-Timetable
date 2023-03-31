package ru.blays.timetable.Compose.Utils

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object AppBarState {
    var titleText by mutableStateOf("defaultTitle")

    fun changeTitleText(text: String) {
        titleText = text
    }

}
