package ru.blays.timetable.Compose.ComposeElements

import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import ru.blays.timetable.Compose.States.AlertDialogState


@Composable
fun CustomAlertDialog(message: String) {
    AlertDialog(
        confirmButton = {
            TextButton(onClick = { AlertDialogState.changeState() }) {
            Text(text = "Ok")
        }
    },
    onDismissRequest = { AlertDialogState.changeState() },
    title = { Text(text = "Внимание!") },
    text = { Text(text = message)})
}


/*
TextButton(onClick = { */
/*TODO*//*
 }) {
    Text(text = "ОК")
}*/
