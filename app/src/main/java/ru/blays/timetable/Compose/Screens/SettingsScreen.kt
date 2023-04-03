package ru.blays.timetable.Compose.ComposeElements

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.draw.scale
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import ru.blays.timetable.Compose.States.ThemeState
import ru.blays.timetable.Compose.helperClasses.AccentColorItem
import ru.blays.timetable.Compose.helperClasses.AccentColorList
import ru.blays.timetable.R

@Composable
fun SettingsScreen() {
    Column {
        Card(
            modifier = Modifier
                .padding(vertical = 5.dp, horizontal = 12.dp)
                .fillMaxWidth(),
            shape = RoundedCornerShape(10.dp)
        ) {
            Row(
                modifier = Modifier
                    .padding(vertical = 5.dp, horizontal = 12.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
            )
            {
                Text(text = "Тёмная тема")
            Switch(
                checked = ThemeState.isDarkMode,
                onCheckedChange = {
                ThemeState.changeTheme()
            })
        }
            Row(
                modifier = Modifier
                    .padding(vertical = 5.dp, horizontal = 12.dp)
                    .fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceBetween,
                verticalAlignment = Alignment.CenterVertically
            )
            {
                Text(text = "MaterialYou акцент")
                Switch(
                    checked = ThemeState.isDynamicColor,
                    onCheckedChange = {
                    ThemeState.changeDynamicColor()
                })
            }
        }
        AccentSelector()
    }

}

@Composable
fun AccentSelector() {
    var isExpanded by remember {
        mutableStateOf(false)
    }
    Card(
        modifier = Modifier
            .padding(vertical = 5.dp, horizontal = 12.dp)
            .fillMaxWidth()
            .clickable { isExpanded = !isExpanded },
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(2.dp)
    ) {
    Column(
        modifier = Modifier
            .padding(12.dp)
    )
    {
        Row(
            modifier = Modifier
                .padding(12.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        )
        {
            Text(text = "Цвет акцента")
            Icon(
                modifier = Modifier
                    .scale(1.5F)
                    .background(color = MaterialTheme.colorScheme.background, shape = CircleShape)
                    .rotate(if (isExpanded) 180F else 0F),
                imageVector = ImageVector.vectorResource(id = R.drawable.ic_arrow_down_24dp),
                contentDescription = "Arrow")
        }
        if (isExpanded) {
            LazyRow(modifier = Modifier.padding(top = 16.dp)) {
                itemsIndexed(AccentColorList.list)
                    { _, item ->
                        ColorPickerItem(item = item)
                    }
                }
            }
        }
    }
}

@Composable
fun ColorPickerItem(item: AccentColorItem) {
Box(
    modifier = Modifier
        .size(50.dp)
        .padding(4.dp)
        .clip(CircleShape)
        .background(color = item.accentDark)
        .clickable {
            ThemeState.changeAccentColor(item)
        }
    )
}

@Composable
fun DBStateItem() {
    Card(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        shape = RoundedCornerShape(10.dp),
        elevation = CardDefaults.cardElevation(2.dp)
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