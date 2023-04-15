package ru.blays.timetable.data.repository.timetableRepository

import android.util.Log
import io.objectbox.Box
import ru.blays.timetable.data.models.ObjectBox.Boxes.DaysInTimeTableBox
import ru.blays.timetable.data.models.ObjectBox.Boxes.DaysInTimeTableBox_
import ru.blays.timetable.data.models.ObjectBox.Boxes.GroupListBox
import ru.blays.timetable.data.models.ObjectBox.Boxes.GroupListBox_
import ru.blays.timetable.data.models.ObjectBox.Boxes.SubjectsListBox
import ru.blays.timetable.domain.models.GetDaysListModel
import ru.blays.timetable.domain.models.GetGroupListModel
import ru.blays.timetable.domain.models.GetSubjectsListModel
import ru.blays.timetable.domain.models.GetTimetableModel
import ru.blays.timetable.domain.models.SaveTimetableModel
import ru.blays.timetable.domain.repository.TimetableRepositoryInterface

class TimetableRepositoryImplementation(
    private val groupListBox: Box<GroupListBox>,
    private val daysListBox: Box<DaysInTimeTableBox>,
    private val subjectsListBox: Box<SubjectsListBox>
) : TimetableRepositoryInterface {
    override fun getGroupList(): List<GetGroupListModel> {

        val groupListBox = groupListBox.all
        val getGroupListBox = mutableListOf<GetGroupListModel>()

        groupListBox.forEach { days ->

            getGroupListBox.add(
                GetGroupListModel(
                    groupCode = days.groupCode,
                    href = days.href,
                    updateTime = days.updateTime,
                    days = null))
        }
        return getGroupListBox
    }

    override fun getDaysList(href: String): GetTimetableModel {
        val query = groupListBox.query(GroupListBox_.href.equal(href)).build().find()[0]

        val daysListBox = query.days
        val getDaysListModel = mutableListOf<GetDaysListModel>()

        daysListBox.forEach { days ->

            val subjectsListBox = days.subjects
            val getSubjectsListModel = mutableListOf<GetSubjectsListModel>()

            subjectsListBox.forEach { subjects ->
                getSubjectsListModel.add(
                    GetSubjectsListModel(
                        position = subjects.position,
                        subgroups = subjects.subgroups,
                        subject = subjects.subject,
                        lecturer = subjects.lecturer,
                        auditory = subjects.auditory
                    )
                )
            }

            getDaysListModel.add(
                GetDaysListModel(
                    day = days.day,
                    href = days.href,
                    subjects = getSubjectsListModel
                )
            )
        }

        return GetTimetableModel(
            daysWithSubjectsList = getDaysListModel,
            href = query.href,
            updateDate = query.updateTime,
            groupCode = query.groupCode
        )
    }

    override fun saveGroupList(getGroupsList: List<GetGroupListModel>) {
        val groupList = mutableListOf<GroupListBox>()

        getGroupsList.forEach { groups ->
            groupList.add(
                GroupListBox(
                    groupCode = groups.groupCode,
                    href = groups.href,
                    updateTime = groups.updateTime
                )
            )
        }

        groupListBox.put(groupList)
    }

    override fun saveDaysList(timetableModel: SaveTimetableModel) {
        val groupRow = groupListBox.query(GroupListBox_.href.equal(timetableModel.href)).build().find()

        val subjectsList = mutableListOf<SubjectsListBox>()
        val getDaysListModel = timetableModel.boxModel

        getDaysListModel.subjects.forEach { subjects ->
            subjectsList.add(
                SubjectsListBox(
                    position = subjects.position,
                    subgroups = subjects.subgroups,
                    subject = subjects.subject,
                    lecturer = subjects.lecturer,
                    auditory = subjects.auditory
                )
            )
        }

        val daysList = DaysInTimeTableBox(
            day = timetableModel.boxModel.day,
            href = timetableModel.boxModel.href
        )
        daysList.subjects.addAll(subjectsList)

        groupRow[0].days.add(daysList)
        groupRow[0].updateTime = timetableModel.updateTime
        groupListBox.put(groupRow)
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