package ru.blays.timetable.ParseUtils

import kotlinx.coroutines.coroutineScope
import ru.blays.timetable.Compose.*
import ru.blays.timetable.ObjectBox.Boxes.DaysInTimeTableBox
import ru.blays.timetable.ObjectBox.Boxes.GroupListBox
import ru.blays.timetable.ObjectBox.Boxes.SubjectsListBox

class HTMLParser {

    suspend fun createMainDB() = coroutineScope {
        val tr = try {
            htmlClient.getHTTP("cg.htm").select("tr")
        } catch (_: NullPointerException) {
            return@coroutineScope
        }

        objectBoxManager.deleteBox(listOf(daysListBox, groupListBox, subjectsListBox))

        tr.forEach { it1 ->
            if (it1.select(".ur").toString() != "") {
                val gr = it1.select(".z0")
                val href = gr.attr("href")
                val groupResult = GroupListBox(groupCode = gr.text(), href = href)
                groupListBox.put(groupResult)
            }
        }
    }

    data class Range(
    val rangeStart: Int,
    val rangeEnd: Int
    )

    suspend fun getTimeTable(href: String): Boolean {
        val daysIndices = mutableListOf<Int>()
        val rowRange = mutableListOf<Range>()

        val tr = try {
            htmlClient.getHTTP(href).select("table.inf").select("tr")
        } catch (_: NullPointerException) {
            return false
        }

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
            val days = DaysInTimeTableBox(
                day = d,
                href = href
            )
            for (i in it.rangeStart..it.rangeEnd) {
                val tri = tr[i]
                if (tri.select(".ur").toString() != "") {
                    if (i == it.rangeStart) {
                        val sid = SubjectsListBox(
                            position = tri.select(".hd")[1].text(),
                            subject = tri.select(".z1").text(),
                            lecturer = tri.select(".z3").text(),
                            auditory = tri.select(".z2").text()
                        )
                        days.subjects.add(sid)
                    } else {
                        val sid = SubjectsListBox(
                            position = tri.select(".hd").text(),
                            subject = tri.select(".z1").text(),
                            lecturer = tri.select(".z3").text(),
                            auditory = tri.select(".z2").text()
                        )
                        days.subjects.add(sid)
                    }
                }
            }
            objectBoxManager.insertToDaysBox(href, days)
        }
        return true
    }
}

/*CoroutineScope(Dispatchers.IO).launch {
    val doc = htmlClient.getHTTP("vg60.htm")

    val tr = doc.select("table.inf").select("tr")

    for (row in tr) {
        val i = row.select(".vp").select("[align=left]").select("a[href]").text()
        val p = row.select("img").attr("width")
        Log.d("parseLog", "Subject: $i, Complete:  $p")
    }
}*/

    /*suspend fun parseHTML(href: String) {

        val doc = htmlClient.getHTTP(href)

        try {
            tr = doc.select("table.inf").select("tr")
        } catch (_: NullPointerException) {
            return
        }

        for (cell in tr) {
            if (cell.select(".hd").select("[rowspan=7]").toString() != "") {
                val dt = cell.select(".hd").select("[rowspan=7]").text()
                Log.d("parseLog", dt.toString())
                Log.d("ParseLog", dt + day)

                val cl = cell.select(".ur")
                if (cl.toString() != "") {
                    if (cl.count() > 1) {
                        val si1 = SecTableModel(
                            cell.select(".hd")[1].text() + "\n1 п/г",
                            cl[0].select(".z1").text(),
                            cl[0].select(".z2").text(),
                            cl[0].select(".z3").text()
                        )
                        val si2 = SecTableModel(
                            cell.select(".hd")[1].text() + "\n2 п/г",
                            cl[1].select(".z1").text(),
                            cl[1].select(".z2").text(),
                            cl[1].select(".z3").text()
                        )

                    } else {
                        val si = SecTableModel(
                            cell.select(".hd")[1].text() + "\n",
                            cell.select(".z1").text(),
                            cell.select(".z2").text(),
                            cell.select(".z3").text()
                        )
                    }
                }

            } else if (cell.select(".ur").toString() != "") {
                val cl = cell.select(".ur")
                if (cl.count() > 1) {
                    val si1 = SecTableModel(
                        cell.select(".hd").text() + "\n1 п/г",
                        cl[0].select(".z1").text(),
                        cl[0].select(".z2").text(),
                        cl[0].select(".z3").text()
                    )
                    val si2 = SecTableModel(
                        cell.select(".hd").text() + "\n2 п/г",
                        cl[1].select(".z1").text(),
                        cl[1].select(".z2").text(),
                        cl[1].select(".z3").text()

                } else {
                    val si = SecTableModel(
                        cell.select(".hd").text() + "\n",
                        cell.select(".z1").text(),
                        cell.select(".z2").text(),
                        cell.select(".z3").text()
                    )
                }
            }
        }
    }
    */