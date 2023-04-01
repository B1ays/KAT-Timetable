package ru.blays.timetable.Compose.ComposeElements

import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.blays.timetable.Compose.States.ThemeState

@Composable
fun SettingsScreen() {
    var isDarkTheme by remember { mutableStateOf(false) }
    var isDynamicColor by remember { mutableStateOf(false) }
    isDarkTheme = isSystemInDarkTheme()
    isDynamicColor = Build.VERSION.SDK_INT >= Build.VERSION_CODES.S
    var isThemeChecked by remember { mutableStateOf(isDarkTheme) }
    var isDynamicChecked by remember { mutableStateOf(isDarkTheme) }
    Column {
        Text(
            modifier = Modifier.padding(6.dp),
            text = "Настроек нет, вообще нет!")

        Switch(checked = isThemeChecked, onCheckedChange = {
            isThemeChecked = it
            ThemeState.changeTheme(it)
        }
        )

        Switch(checked = isDynamicChecked, onCheckedChange = {
            isDynamicChecked = it
            ThemeState.changeDynamicColor(it)
        }
        )
    }
}

@Composable
fun DBStateItem() {
    Card(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(
            modifier = Modifier
                .padding(6.dp)
                .fillMaxWidth()
        )
        {
            Text(
                modifier = Modifier
                    .padding(bottom = 6.dp)
                    .fillMaxWidth(),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.titleMedium,
                text = "Состояние базы данных",
            )
            Row(modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically) {
                Text(
                    textAlign = TextAlign.Center,
                    text = "База данных в норме")
                Icon(
                    modifier = Modifier
                        .size(60.dp),
                    imageVector = androidx.compose.material.icons.Icons.Rounded.CheckCircle,
                    contentDescription = "state icon",
                    tint = Color.Green
                )
            }
            Box(
                modifier = Modifier.fillMaxWidth(),
                contentAlignment = Alignment.CenterEnd) {
                Button(modifier = Modifier
                    .padding(top = 4.dp),
                    onClick = { /*TODO*/ }
                ) {
                    Text(text = "Пересоздать базу данных")
                }
            }
        }

    }
}