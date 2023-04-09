package ru.blays.timetable.Compose.HelperClasses

import ru.blays.timetable.R

data class Contact(
    val iconID: Int,
    val name: String,
    val link: String
)

object ContactList {
    val List = listOf(
        Contact(iconID = R.drawable.ic_vk, "Вконтакте", "https://vk.com/b1ays"),
        Contact(iconID = R.drawable.ic_telegram, "Telegram", "https://t.me/B1ays"),
        /*Contact(iconID = R.drawable.telegram_icon, "4PDA", "https://t.me/B1ays")*/

    )
}