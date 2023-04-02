package ru.blays.timetable.Compose.States

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object AlertDialogState {

    var isOpen by mutableStateOf(false)

    var text by mutableStateOf("")

    fun changeState() = run {
        isOpen = !isOpen
    }

    fun changeText(message: String) = run {
        text = message
    }
}