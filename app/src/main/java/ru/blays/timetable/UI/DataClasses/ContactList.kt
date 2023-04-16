package ru.blays.timetable.UI.DataClasses

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
        Contact(iconID = R.drawable.ic_4pda, "4PDA", "https://4pda.to/forum/index.php?showuser=7576426")

    )
}