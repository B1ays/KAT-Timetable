package ru.blays.timetable.Compose.HelperClasses

import androidx.compose.ui.graphics.Color

data class AccentColorItem(
    val title: String,
    val accentDark: Color,
    val accentLight: Color
)

object AccentColorList {
    val list = listOf(AccentColorItem(
            title = "Красный",
            accentDark = Color(0xFFE23939),
            accentLight = Color(0xFF861717)
        ),
        AccentColorItem(
            title = "Синий",
            accentDark = Color(0xFF3969E2),
            accentLight = Color(0xFF183C97)
        ),
        AccentColorItem(
            title = "Розовый",
            accentDark = Color(0xFFE0277E),
            accentLight = Color(0xFF8F0947)
        ),
        AccentColorItem(
            title = "Бирюзовый",
            accentDark = Color(0xFF009688),
            accentLight = Color(0xFF05574F)
        ),
        AccentColorItem(
            title = "Ораньжевый",
            accentDark = Color(0xFFFF5722),
            accentLight = Color(0xFFAD3109)
        ),
        AccentColorItem(
            title = "Жёлтый",
            accentDark = Color(0xFFFFC107),
            accentLight = Color(0xFFA37C06)
        ),
        AccentColorItem(
            title = "Фиолетовый",
            accentDark = Color(0xFF673AB7),
            accentLight = Color(0xFF411F7C)
        ),
        AccentColorItem(
            title = "Зелёный",
            accentDark = Color(0xFF4CAF50),
            accentLight = Color(0xFF226B25)
        )
    )
}