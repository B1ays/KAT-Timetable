package ru.blays.timetable.data.repositories.timetableRepository.storage

import android.util.Log
import io.objectbox.Box
import ru.blays.timetable.data.models.ObjectBox.Boxes.DaysInTimeTableBox
import ru.blays.timetable.data.models.ObjectBox.Boxes.DaysInTimeTableBox_
import ru.blays.timetable.data.models.ObjectBox.Boxes.GroupListBox
import ru.blays.timetable.data.models.ObjectBox.Boxes.GroupListBox_
import ru.blays.timetable.data.models.ObjectBox.Boxes.SubjectsListBox
import ru.blays.timetable.data.models.SaveTimetableModel
import ru.blays.timetable.data.models.TimetableModel

class ObjectBoxStorageImpl(
    private val groupListBox: Box<GroupListBox>,
    private val daysListBox: Box<DaysInTimeTableBox>,
    private val subjectsListBox: Box<SubjectsListBox>
    ) : TimetableStorage {
    override fun getGroupList(): List<GroupListBox> {
        return groupListBox.all
    }

    override fun getDaysList(href: String): TimetableModel {
        val query = groupListBox.query(GroupListBox_.href.equal(href)).build().find()[0]
        return TimetableModel(
            daysWithSubjectsList = query.days,
            href = query.href,
            updateDate = query.updateTime,
            groupCode = query.groupCode
        )
    }

    override fun getSubjectsList(): List<SubjectsListBox> {
        TODO("Not yet implemented")
    }

    override fun saveGroupList(groupsList: List<GroupListBox>) {
        groupListBox.put(groupsList)
    }

    override fun saveDaysList(timetableModel: SaveTimetableModel) {
        val groupRow = groupListBox.query(GroupListBox_.href.equal(timetableModel.href)).build().find()
        groupRow[0].days.add(timetableModel.boxModel)
        groupRow[0].updateTime = timetableModel.updateTime
        groupListBox.put(groupRow)
    }

    override fun saveSubjectsList(subjectsListBox: List<SubjectsListBox>) {
        TODO("Not yet implemented")
    }

    override fun deleteTimetableFromBox(href: String) {
        val dayBox = daysListBox.query(DaysInTimeTableBox_.href.equal(href)).build().find()
        if (dayBox.isNotEmpty()) {
            try {
                daysListBox.remove(dayBox)
            } catch (e: IndexOutOfBoundsException) {
                Log.d("DeleteLog", e.toString())
            }
        }
    }
}