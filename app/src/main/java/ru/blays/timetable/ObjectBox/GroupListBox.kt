package ru.blays.timetable.ObjectBox

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id

@Entity
data class GroupListBox (
    @Id var id: Long = 0,
    var groupCode: String = "",
    var href: String = ""
    )