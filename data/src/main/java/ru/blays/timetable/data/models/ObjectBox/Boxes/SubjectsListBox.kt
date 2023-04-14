package ru.blays.timetable.data.models.ObjectBox.Boxes

import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToOne

@Entity
class SubjectsListBox(
    @Id var id: Long = 0,
    var position: String = "",
    var subgroups: String = "",
    var subject: String = "",
    var lecturer: String = "",
    var auditory: String = ""
) {
    lateinit var day: ToOne<DaysInTimeTableBox>
}
