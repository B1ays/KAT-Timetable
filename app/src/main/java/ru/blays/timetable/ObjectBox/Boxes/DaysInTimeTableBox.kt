package ru.blays.timetable.ObjectBox.Boxes

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne

@Entity
data class DaysInTimeTableBox(
    @Id var id: Long = 0,
    var day: String = "",
    var href: String = ""
    ) {
    lateinit var group: ToOne<GroupListBox>
    }