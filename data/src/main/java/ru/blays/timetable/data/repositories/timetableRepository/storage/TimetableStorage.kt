package ru.blays.timetable.data.repositories.timetableRepository.storage

import ru.blays.timetable.data.models.ObjectBox.Boxes.GroupListBox
import ru.blays.timetable.data.models.ObjectBox.Boxes.SubjectsListBox
import ru.blays.timetable.data.models.SaveTimetableModel
import ru.blays.timetable.data.models.TimetableModel

interface TimetableStorage {

    // get values //

    fun getGroupList(): List<GroupListBox>

    fun getDaysList(href: String):  TimetableModel

    fun getSubjectsList():  List<SubjectsListBox>

    // save values //

    fun saveGroupList(groupsList:  List<GroupListBox>)

    fun saveDaysList(timetableModel: SaveTimetableModel)

    fun saveSubjectsList(subjectsListBox: List<SubjectsListBox>)

    // delete list data //
    fun deleteTimetableFromBox(href: String)
}