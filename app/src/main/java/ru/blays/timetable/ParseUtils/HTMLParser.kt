package ru.blays.timetable.ParseUtils

import ru.blays.timetable.Compose.*
import ru.blays.timetable.data.models.ObjectBox.Boxes.DaysInTimeTableBox
import ru.blays.timetable.data.models.ObjectBox.Boxes.GroupListBox
import ru.blays.timetable.data.models.ObjectBox.Boxes.SubjectsListBox

class HTMLParser {

    suspend fun createMainDB() {
        val tr = try {
            htmlClient.getHTTP("cg.htm").select("tr")
        } catch (_: Exception) {
            return
        }

        objectBoxManager.deleteBox(listOf(daysListBox, groupListBox, subjectsListBox))

        tr.forEach { it1 ->
            if (it1.select(".ur").toString() != "") {
                val gr = it1.select(".z0")
                val href = gr.attr("href")
                val groupResult = ru.blays.timetable.data.models.ObjectBox.Boxes.GroupListBox(
                    groupCode = gr.text(),
                    href = href
                )
                groupListBox.put(groupResult)
            }
        }
    }

    data class Range(
    val rangeStart: Int,
    val rangeEnd: Int
    )

    suspend fun getTimeTable(href: String) {
        val daysIndices = mutableListOf<Int>()
        val rowRange = mutableListOf<Range>()

        val htmlBody = try {
            htmlClient.getHTTP(href)
        } catch (_: NullPointerException) {
            return
        }

        val tr = htmlBody.select("table.inf").select("tr")

        val updateTime = htmlBody.select(".ref").text()

        objectBoxManager.deleteTimeTable(href)

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
            val days = ru.blays.timetable.data.models.ObjectBox.Boxes.DaysInTimeTableBox(
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

                                val sid =
                                    ru.blays.timetable.data.models.ObjectBox.Boxes.SubjectsListBox(
                                        position = position,
                                        subgroups = subgroups,
                                        subject = subject,
                                        lecturer = lecturer,
                                        auditory = auditory
                                    )

                                days.subjects.add(sid)
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

                            val sid1 =
                                ru.blays.timetable.data.models.ObjectBox.Boxes.SubjectsListBox(
                                    position = position1,
                                    subgroups = subgroups1,
                                    subject = subject1,
                                    lecturer = lecturer1,
                                    auditory = auditory1
                                )

                            val sid2 =
                                ru.blays.timetable.data.models.ObjectBox.Boxes.SubjectsListBox(
                                    position = position2,
                                    subgroups = subgroups2,
                                    subject = subject2,
                                    lecturer = lecturer2,
                                    auditory = auditory2
                                )
                            // Добавить в БД предмет для обеих подгрупп
                            days.subjects.add(sid1)
                            days.subjects.add(sid2)

                            // Если первым в расписании идёт предмет для обеих подгрупп, то:
                        } else {

                            val position =
                                tri.select(".hd")[if (i == it.rangeStart) 1 else 0].text()
                            val subgroups = "BOTH"
                            val subject = tri.select(".z1").text()
                            val lecturer = tri.select(".z3").text()
                            val auditory = tri.select(".z2").text()

                            val sid =
                                ru.blays.timetable.data.models.ObjectBox.Boxes.SubjectsListBox(
                                    position = position,
                                    subgroups = subgroups,
                                    subject = subject,
                                    lecturer = lecturer,
                                    auditory = auditory
                                )

                            days.subjects.add(sid)
                        }
                    }
                }
            }
            objectBoxManager.insertTimetableToBox(
                href = href,
                updateTime = updateTime,
                boxModel = days
            )
        }
    }
}