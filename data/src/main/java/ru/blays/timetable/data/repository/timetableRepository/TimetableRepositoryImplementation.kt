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
import ru.blays.timetable.domain.models.SaveGroupsListModel
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

        val query = groupListBox.query(GroupListBox_.href.equal(href)).build().find()

        if (query[0].days.isNotEmpty()) {
            val groupObject = query[0]
            val daysListBox = groupObject.days
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
                href = groupObject.href,
                updateDate = groupObject.updateTime,
                groupCode = groupObject.groupCode,
                success = true
            )
        } else {
            return GetTimetableModel(success = false)
        }
    }

    override fun saveGroupList(groupsList: List<SaveGroupsListModel>) {
        val groupList = mutableListOf<GroupListBox>()

        groupsList.forEach { groups ->
            groupList.add(
                GroupListBox(
                    groupCode = groups.groupCode,
                    href = groups.href
                )
            )
        }

        groupListBox.put(groupList)
    }

    override fun saveDaysList(timetableModel: SaveTimetableModel): String {

        val groupRow = groupListBox.query(GroupListBox_.href.equal(timetableModel.href)).build().find()[0]

        val getDaysListModel = timetableModel.boxModel

        val daysList = mutableListOf<DaysInTimeTableBox>()

        getDaysListModel.forEach { days ->
            val day = DaysInTimeTableBox(day = days.day,
            href = days.href)

            val subjectsList = mutableListOf<SubjectsListBox>()

            days.subjects.forEach { subjects ->
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
            day.subjects.addAll(subjectsList)
            daysList.add(day)
        }

        groupRow.days.addAll(daysList)
        groupRow.updateTime = timetableModel.updateTime
        groupListBox.put(groupRow)

        return groupRow.groupCode
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