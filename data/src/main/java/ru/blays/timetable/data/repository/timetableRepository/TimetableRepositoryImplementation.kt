package ru.blays.timetable.data.repository.timetableRepository

import android.util.Log
import io.objectbox.Box
import ru.blays.timetable.data.models.ObjectBox.Boxes.AuditoryListBox
import ru.blays.timetable.data.models.ObjectBox.Boxes.AuditoryListBox_
import ru.blays.timetable.data.models.ObjectBox.Boxes.DaysInTimeTableBox
import ru.blays.timetable.data.models.ObjectBox.Boxes.DaysInTimeTableBox_
import ru.blays.timetable.data.models.ObjectBox.Boxes.GroupListBox
import ru.blays.timetable.data.models.ObjectBox.Boxes.GroupListBox_
import ru.blays.timetable.data.models.ObjectBox.Boxes.LecturersListBox
import ru.blays.timetable.data.models.ObjectBox.Boxes.LecturersListBox_
import ru.blays.timetable.data.models.ObjectBox.Boxes.SubjectsListBox
import ru.blays.timetable.domain.models.GetDaysListModel
import ru.blays.timetable.domain.models.GetSimpleListModel
import ru.blays.timetable.domain.models.GetSubjectsListModel
import ru.blays.timetable.domain.models.GetTimetableModel
import ru.blays.timetable.domain.models.SaveSimpleListModel
import ru.blays.timetable.domain.models.SaveTimetableModel
import ru.blays.timetable.domain.repository.TimetableRepositoryInterface

class TimetableRepositoryImplementation(
    private val groupListBox: Box<GroupListBox>,
    private val lecturersListBox: Box<LecturersListBox>,
    private val auditoryListBox:  Box<AuditoryListBox>,
    private val daysListBox: Box<DaysInTimeTableBox>,
    private val subjectsListBox: Box<SubjectsListBox>
) : TimetableRepositoryInterface {
    override fun getGroupList(): List<GetSimpleListModel> {

        val groupListBox = groupListBox.all
        val getGroupListBox = mutableListOf<GetSimpleListModel>()

        groupListBox.forEach { group ->

            getGroupListBox.add(
                GetSimpleListModel(
                    name = group.groupCode,
                    href = group.href,
                    updateTime = group.updateTime,
                    days = null
                )
            )
        }
        return getGroupListBox
    }

    override fun getLecturersList(): List<GetSimpleListModel> {

        val lecturerListBox = lecturersListBox.all
        val getLecturersListBox = mutableListOf<GetSimpleListModel>()

        /*Log.d("getLecturersList", lecturersListBox[0].toString())*/

        lecturerListBox.forEach { lecturer ->

            getLecturersListBox.add(
                GetSimpleListModel(
                    name = lecturer.name,
                    href = lecturer.href,
                    updateTime = lecturer.updateTime,
                    days = null
                )
            )
        }

        return getLecturersListBox
    }

    override fun getAuditoryList(): List<GetSimpleListModel> {
        val auditoryListBox = auditoryListBox.all
        val getAuditoryListBox = mutableListOf<GetSimpleListModel>()

        auditoryListBox.forEach { auditory ->

            getAuditoryListBox.add(
                GetSimpleListModel(
                    name = auditory.number,
                    href = auditory.href,
                    updateTime = auditory.updateTime,
                    days = null
                )
            )
        }
        return getAuditoryListBox
    }


    override fun getDaysForGroup(href: String): GetTimetableModel {

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
                            title = subjects.subject,
                            subtitle1 = subjects.lecturer,
                            subtitle2 = subjects.auditory
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

    override fun getDaysForLecturer(href: String): GetTimetableModel {

        val query = lecturersListBox.query(LecturersListBox_.href.equal(href)).build().find()

        if (query[0].days.isNotEmpty()) {
            val queryObject = query[0]
            val daysListBox = queryObject.days
            val getDaysListModel = mutableListOf<GetDaysListModel>()

            daysListBox.forEach { days ->

                val subjectsListBox = days.subjects
                val getSubjectsListModel = mutableListOf<GetSubjectsListModel>()

                subjectsListBox.forEach { subjects ->
                    getSubjectsListModel.add(
                        GetSubjectsListModel(
                            position = subjects.position,
                            subgroups = subjects.subgroups,
                            title = subjects.subject,
                            subtitle1 = subjects.lecturer,
                            subtitle2 = subjects.auditory
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

            Log.d("getLecturersList", "$getDaysListModel")

            return GetTimetableModel(
                daysWithSubjectsList = getDaysListModel,
                href = queryObject.href,
                updateDate = queryObject.updateTime,
                groupCode = queryObject.name,
                success = true
            )
        } else {
            return GetTimetableModel(success = false)
        }
    }

    override fun getDaysForAuditory(href: String): GetTimetableModel {

        val query = auditoryListBox.query(AuditoryListBox_.href.equal(href)).build().find()

        if (query[0].days.isNotEmpty()) {
            val queryObject = query[0]
            val daysListBox = queryObject.days
            val getDaysListModel = mutableListOf<GetDaysListModel>()

            daysListBox.forEach { days ->

                val subjectsListBox = days.subjects
                val getSubjectsListModel = mutableListOf<GetSubjectsListModel>()

                subjectsListBox.forEach { subjects ->
                    getSubjectsListModel.add(
                        GetSubjectsListModel(
                            position = subjects.position,
                            subgroups = subjects.subgroups,
                            title = subjects.subject,
                            subtitle1 = subjects.lecturer,
                            subtitle2 = subjects.auditory
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
                href = queryObject.href,
                updateDate = queryObject.updateTime,
                groupCode = queryObject.number,
                success = true
            )
        } else {
            return GetTimetableModel(success = false)
        }
    }

    override fun saveGroupList(groupsList: List<SaveSimpleListModel>) {

        val groupList = mutableListOf<GroupListBox>()

        groupsList.forEach { groups ->
            groupList.add(
                GroupListBox(
                    groupCode = groups.name,
                    href = groups.href
                )
            )
        }

        groupListBox.put(groupList)
    }

    override fun saveLecturersList(lecturersList: MutableList<SaveSimpleListModel>) {

        val lecturerList = mutableListOf<LecturersListBox>()

        lecturersList.forEach { lecturer ->
            Log.d("saveLecturersList", "$lecturer")
            lecturerList.add(
                LecturersListBox(
                    name = lecturer.name,
                    href = lecturer.href
                )
            )
        }

        lecturersListBox.put(lecturerList)

    }

    override fun saveAuditoryList(auditoryList: MutableList<SaveSimpleListModel>) {

        val auditoriumsList = mutableListOf<AuditoryListBox>()

        auditoryList.forEach { groups ->
            auditoriumsList.add(
                AuditoryListBox(
                    number = groups.name,
                    href = groups.href
                )
            )
        }

        auditoryListBox.put(auditoriumsList)
    }

    override fun saveDaysListForGroup(timetableModel: SaveTimetableModel): String {

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
                        subject = subjects.title,
                        lecturer = subjects.subtitle1,
                        auditory = subjects.subtitle2
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

    override fun saveDaysListForLecturer(timetableModel: SaveTimetableModel): String {
        val auditoryRow = lecturersListBox.query(LecturersListBox_.href.equal(timetableModel.href)).build().find()[0]

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
                        subject = subjects.title,
                        lecturer = subjects.subtitle1,
                        auditory = subjects.subtitle2
                    )
                )
            }
            day.subjects.addAll(subjectsList)
            daysList.add(day)
        }

        auditoryRow.days.addAll(daysList)
        auditoryRow.updateTime = timetableModel.updateTime
        lecturersListBox.put(auditoryRow)

        return auditoryRow.name
    }

    override fun saveDaysListForAuditory(timetableModel: SaveTimetableModel): String {
        val groupRow = auditoryListBox.query(AuditoryListBox_.href.equal(timetableModel.href)).build().find()[0]

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
                        subject = subjects.title,
                        lecturer = subjects.subtitle1,
                        auditory = subjects.subtitle2
                    )
                )
            }
            day.subjects.addAll(subjectsList)
            daysList.add(day)
        }

        groupRow.days.addAll(daysList)
        groupRow.updateTime = timetableModel.updateTime
        auditoryListBox.put(groupRow)

        return groupRow.number
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