package ru.blays.timetable.domain.useCases

import org.jsoup.nodes.Document
import ru.blays.timetable.domain.models.GetDaysListModel
import ru.blays.timetable.domain.models.GetSubjectsListModel
import ru.blays.timetable.domain.models.GetTimetableModel
import ru.blays.timetable.domain.models.SaveTimetableModel
import ru.blays.timetable.domain.repository.TimetableRepositoryInterface
import ru.blays.timetable.domain.repository.WebRepositoryInterface

class GetTimetableUseCase(
    private val timetableRepositoryInterface: TimetableRepositoryInterface,
    private val webRepositoryInterface: WebRepositoryInterface) {

    suspend fun execut(href: String, source: Int, isUpdate: Boolean = false) : GetTimetableModel {

        var timetable = when (source) {
            0 -> timetableRepositoryInterface.getDaysForLecturer(href = href)
            1 -> timetableRepositoryInterface.getDaysForGroup(href = href)
            2 -> timetableRepositoryInterface.getDaysForAuditory(href = href)
            else -> GetTimetableModel(success = false)
        }


        if (!timetable.success || isUpdate) {

            timetable = try {
                val htmlBody = webRepositoryInterface.getHTMLBody(href = href)
                parseTimetable(htmlBody!!, href, source)
            } catch (e : Exception) {
                println(e)
                GetTimetableModel(success = false)
            }
        }

        /*println(timetable)*/

        return timetable

    }

    private fun parseTimetable(doc: Document, href: String, source: Int): GetTimetableModel {

        println("start parsing")

        timetableRepositoryInterface.deleteTimetableFromBox(href)

        val daysIndices = mutableListOf<Int>()
        val rowRange = mutableListOf<Range>()

        val getDaysListModel = mutableListOf<GetDaysListModel>()

        val tr = doc.select("table.inf").select("tr")

        val updateTime = doc.select(".ref").text()

        for (i in tr.indices) {
            val day = tr[i].select(".hd").select("[rowspan=7]").text()
            if (day != "") {
                daysIndices.add(i)
            }
        }
        val indicesCount = daysIndices.count()
        for (i in daysIndices.indices) {
            if (i < indicesCount - 1) {
                val range = Range(rangeStart = daysIndices[i], rangeEnd = daysIndices[i + 1] - 1)
                rowRange.add(range)
            }
        }

        rowRange.forEach {
            val d = tr[it.rangeStart].select(".hd").select("[rowspan=7]").text()
            val day = GetDaysListModel(
                day = d,
                href = href
            )

            for (i in it.rangeStart..it.rangeEnd) { // вся строки таблицы, относящиеся к конкретому дню
                val tri = tr[i] // table row №i
                val cell = tri.select(".ur")

                if (cell.toString() != "") {

                    if (tri.select(".nul").toString() != "") {
                        val td = tri.select("td")
                        var isFirstSubgroup = true

                        for (row in td) {
                            val rowCell = row.select(".ur")
                            if (row.select(".nul[colspan=1]").toString() != "") {
                                isFirstSubgroup = false
                            } else if (rowCell.toString() != "") {

                                val position =
                                    tri.select(".hd")[if (i == it.rangeStart) 1 else 0].text()
                                val subgroups = if (isFirstSubgroup) "1" else "2"
                                val subject = rowCell.select(".z1").text()
                                val lecturer = rowCell.select(".z3").text()
                                val auditory = rowCell.select(".z2").text()

                                val sid = GetSubjectsListModel(
                                    position = position,
                                    subgroups = subgroups,
                                    title = subject,
                                    subtitle1 = lecturer,
                                    subtitle2 = auditory
                                )

                                day.subjects.add(sid)
                            }
                        }
                    } else {

                        // Если первым в расписании идёт предмет по подгруппам, то:
                        if (cell.count() > 1) {

                            //Предмет у первой п/г
                            val position1 =
                                tri.select(".hd")[if (i == it.rangeStart) 1 else 0].text()
                            val subject1 = cell[0].select(".z1").text()
                            val subgroups1 = "1"
                            val lecturer1 = cell[0].select(".z3").text()
                            val auditory1 = cell[0].select(".z2").text()

                            //Предмет у второй п/г
                            val position2 =
                                tri.select(".hd")[if (i == it.rangeStart) 1 else 0].text()
                            val subgroups2 = "2"
                            val subject2 = cell[1].select(".z1").text()
                            val lecturer2 = cell[1].select(".z3").text()
                            val auditory2 = cell[1].select(".z2").text()

                            val sid1 = GetSubjectsListModel(
                                position = position1,
                                subgroups = subgroups1,
                                title = subject1,
                                subtitle1 = lecturer1,
                                subtitle2 = auditory1
                            )

                            val sid2 = GetSubjectsListModel(
                                position = position2,
                                subgroups = subgroups2,
                                title = subject2,
                                subtitle1 = lecturer2,
                                subtitle2 = auditory2
                            )
                            // Добавить в БД предмет для обеих подгрупп
                            day.subjects.add(sid1)
                            day.subjects.add(sid2)

                            // Если первым в расписании идёт предмет для обеих подгрупп, то:
                        } else {

                            val position =
                                tri.select(".hd")[if (i == it.rangeStart) 1 else 0].text()
                            val subgroups = "BOTH"
                            val subject = tri.select(".z1").text()
                            val lecturer = tri.select(".z3").text()
                            val auditory = tri.select(".z2").text()

                            val sid = GetSubjectsListModel(
                                position = position,
                                subgroups = subgroups,
                                title = subject,
                                subtitle1 = lecturer,
                                subtitle2 = auditory
                            )

                            day.subjects.add(sid)
                        }
                    }
                }
            }
            getDaysListModel.add(day)
        }

        println("End parsing")

        val timetableModel = SaveTimetableModel(
            boxModel = getDaysListModel,
            href = href,
            updateTime = updateTime
        )

        println(source)
        val groupCode = when(source) {
            0 -> timetableRepositoryInterface.saveDaysListForLecturer(timetableModel)
            1 -> timetableRepositoryInterface.saveDaysListForGroup(timetableModel)
            2 -> timetableRepositoryInterface.saveDaysListForAuditory(timetableModel)
            else -> ""
        }

        println(groupCode)

        val getModel = GetTimetableModel(href = href,
            updateDate = updateTime,
            groupCode = groupCode,
            daysWithSubjectsList = getDaysListModel,
            success = true
        )

        println(getModel)

        return getModel
    }

    data class Range(
        val rangeStart: Int,
        val rangeEnd: Int
    )
}