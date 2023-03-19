package ru.blays.timetable.ObjectBox.Boxes

import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany
import ru.blays.timetable.ObjectBox.Boxes.DaysInTimeTableBox

@Entity
data class GroupListBox (
    @Id var id: Long = 0,
    var groupCode: String = "",
    var href: String = ""
    ) {
    @Backlink(to = "group")
    lateinit var days: ToMany<DaysInTimeTableBox>
}