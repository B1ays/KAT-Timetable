package ru.blays.timetable.data.models.ObjectBox.Boxes

import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany


@Entity
data class LecturersListBox (
    @Id var id: Long = 0,
    var name: String = "",
    var href: String = "",
    var updateTime: String = ""
    ) {

    @Backlink(to = "lecturer")
    lateinit var days: ToMany<DaysInTimeTableBox>

}