package ru.blays.timetable.ObjectBox.Boxes

import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany


@Entity
data class GroupListBox (
    @Id var id: Long = 0,
    var groupCode: String = "",
    var href: String = "",
    var updateTime: String = ""
    ) {
    @Backlink(to = "group")
    lateinit var days: ToMany<DaysInTimeTableBox>
}