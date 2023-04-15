package ru.blays.timetable.domain.repository

import ru.blays.timetable.domain.models.GetGroupListModel
import ru.blays.timetable.domain.models.GetTimetableModel
import ru.blays.timetable.domain.models.SaveGroupsListModel
import ru.blays.timetable.domain.models.SaveTimetableModel

interface TimetableRepositoryInterface {

    fun getGroupList(): List<GetGroupListModel>

    fun getDaysList(href: String):  GetTimetableModel

    // save values //

    fun saveGroupList(groupsList:  List<SaveGroupsListModel>)

    fun saveDaysList(timetableModel: SaveTimetableModel)

    // delete list data //
    fun deleteTimetableFromBox(href: String)

}