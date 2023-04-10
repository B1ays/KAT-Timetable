package ru.blays.timetable.Compose.ComposeElements

import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import ru.blays.timetable.Compose.States.AlertDialogState


@Composable
fun CustomAlertDialog(message: String) {
    val intent = Intent(android.provider.Settings.ACTION_WIRELESS_SETTINGS)
    val context = LocalContext.current
    AlertDialog(
        dismissButton = {
        TextButton(onClick = { AlertDialogState.closeDialog() }) {
            Text(text = "Отмена")
        }
    },
        confirmButton = {
            TextButton(
                onClick = {
                    context.startActivity(intent)
                    AlertDialogState.closeDialog()
                }
            )
            {
         Text(text = "Ок")
    }
    },
    onDismissRequest = { AlertDialogState.closeDialog() },
    title = { Text(text = "Не удалось подключится к сайту!", textAlign = TextAlign.Center) },
    text = {
        Column(
            modifier = Modifier
                .fillMaxWidth(),
            verticalArrangement = Arrangement.SpaceAround
        )
        {
            Text(
                modifier = Modifier
                .padding(bottom = 20.dp),
                text = "Ошибка:\n$message"
            )
            Text(
                text = "Открыть настройки сети?",
                fontSize = 18.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
    )
}