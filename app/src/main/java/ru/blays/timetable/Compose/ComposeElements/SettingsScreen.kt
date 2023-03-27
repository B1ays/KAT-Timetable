package ru.blays.timetable.Compose.ComposeElements

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp

@Composable
fun SettingsScreen() {
    DBStateItem()
}

@Preview
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
                modifier =Modifier.fillMaxWidth(),
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