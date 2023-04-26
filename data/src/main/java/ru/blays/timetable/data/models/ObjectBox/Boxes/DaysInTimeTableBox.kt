package ru.blays.timetable.data.models.ObjectBox.Boxes

import io.objectbox.annotation.Backlink
import io.objectbox.annotation.Entity
import io.objectbox.annotation.Id
import io.objectbox.relation.ToMany
import io.objectbox.relation.ToOne

@Entity
data class DaysInTimeTableBox(
    @Id var id: Long = 0,
    var day: String = "",
    var href: String = ""
    ) {
    lateinit var group: ToOne<GroupListBox>
    lateinit var lecturer: ToOne<LecturersListBox>
    lateinit var auditory: ToOne<AuditoryListBox>

    @Backlink(to = "day")
    lateinit var subjects: ToMany<SubjectsListBox>

    }