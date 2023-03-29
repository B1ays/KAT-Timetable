package ru.blays.timetable.Compose.Utils

import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.ui.unit.dp

object CardShape {
    val CardStart = RoundedCornerShape(topStart = 10.dp, topEnd = 10.dp, bottomStart = 5.dp, bottomEnd = 5.dp)
    val CardMid = RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp, bottomStart = 5.dp, bottomEnd = 5.dp)
    val CardEnd = RoundedCornerShape(topStart = 5.dp, topEnd = 5.dp, bottomStart = 10.dp, bottomEnd = 10.dp)
    val CardStandalone = RoundedCornerShape(10.dp)
}