package ru.blays.timetable.UI.Compose.ComposeElements


/*
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
}*/
