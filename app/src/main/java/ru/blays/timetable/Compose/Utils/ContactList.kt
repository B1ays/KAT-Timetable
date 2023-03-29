package ru.blays.timetable.Compose.Utils

import ru.blays.timetable.R

data class Contact(
    val iconID: Int,
    val name: String,
    val link: String
)

object ContactList {
    val List = listOf(
        Contact(iconID = R.drawable.vk_icon, "Вконтакте", "https://vk.com/b1ays"),
        Contact(iconID = R.drawable.telegram_icon, "Telegram", "https://t.me/B1ays"),
        /*Contact(iconID = R.drawable.telegram_icon, "4PDA", "https://t.me/B1ays")*/

    )

}