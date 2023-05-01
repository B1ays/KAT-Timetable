package ru.blays.timetable.domain.repository

import ru.blays.timetable.domain.models.GetSimpleListModel
import ru.blays.timetable.domain.models.GetTimetableModel
import ru.blays.timetable.domain.models.SaveSimpleListModel
import ru.blays.timetable.domain.models.SaveTimetableModel

interface TimetableRepositoryInterface {

    fun getGroupList(): List<GetSimpleListModel>

    fun getLecturersList(): List<GetSimpleListModel>

    fun getAuditoryList(): List<GetSimpleListModel>

    fun getDaysForGroup(href: String):  GetTimetableModel

    fun getDaysForAuditory(href: String): GetTimetableModel

    fun getDaysForLecturer(href: String): GetTimetableModel

    // save values //

    fun saveGroupList(groupsList:  List<SaveSimpleListModel>)

    fun saveLecturersList(lecturersList: MutableList<SaveSimpleListModel>)

    fun saveAuditoryList(auditoryList: MutableList<SaveSimpleListModel>)

    fun saveDaysListForGroup(timetableModel: SaveTimetableModel): String

    fun saveDaysListForLecturer(timetableModel: SaveTimetableModel): String

    fun saveDaysListForAuditory(timetableModel: SaveTimetableModel): String

    // delete list data //
    fun deleteTimetableFromBox(href: String)
}