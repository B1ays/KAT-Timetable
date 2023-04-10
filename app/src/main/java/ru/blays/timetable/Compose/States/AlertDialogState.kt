package ru.blays.timetable.Compose.States

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue

object AlertDialogState {

    var isOpen by mutableStateOf(false)

    var text by mutableStateOf("")

    fun openDialog() = run {
        isOpen = true
    }
    fun closeDialog() = run {
        isOpen = false
    }

    fun changeText(message: String) = run {
        text = message
    }
}