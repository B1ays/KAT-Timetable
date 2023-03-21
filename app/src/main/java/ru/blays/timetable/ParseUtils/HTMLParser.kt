package ru.blays.timetable.ParseUtils

import android.util.Log
import kotlinx.coroutines.coroutineScope
import org.jsoup.select.Elements
import ru.blays.timetable.ObjectBox.Boxes.DaysInTimeTableBox
import ru.blays.timetable.ObjectBox.Boxes.GroupListBox
import ru.blays.timetable.ObjectBox.Boxes.SubjectsListBox
import ru.blays.timetable.daysListBox
import ru.blays.timetable.groupListBox
import ru.blays.timetable.htmlClient
import ru.blays.timetable.objectBoxManager

lateinit var tr: Elements
/*lateinit var dbManager: DbManager*/

class HTMLParser {

    suspend fun createMainDB() = coroutineScope {
        try {
            val doc = htmlClient.getHTTP("cg.htm")
            tr = doc.select("table.inf").select("tr")
        } catch (_: NullPointerException) {
            return@coroutineScope
        }

        objectBoxManager.deleteBox(listOf(daysListBox, groupListBox))

        tr.forEach { it1 ->
            if (it1.select(".ur").toString() != "") {
                val gr = it1.select(".z0")
                val href = gr.attr("href")
                val groupResult = GroupListBox(groupCode = gr.text(), href = href)
                //groupListBox.put(groupResult)
                Log.d("pasreLog", gr.text() + " | " + href)
                groupListBox.put(groupResult)
            }
        }
//        Log.d("getLog", groupListBox.all.toString())
        getDaysFromHTML("cg60.htm")
    }

    private suspend fun getDaysFromHTML(href: String) {
        val groupTimeTable = htmlClient.getHTTP(href)
        val htmlRows = groupTimeTable.select("table.inf").select("tr")
        htmlRows.forEach {
            if (it.select(".hd").select("[rowspan=7]").toString() != "") {
                // Log.d("pasreLog", it2.select(".hd").select("[rowspan=7]").text() + " | " + href)
                val d = DaysInTimeTableBox(
                    day = it.select(".hd").select("[rowspan=7]").text(),
                    href = href
                )
                objectBoxManager.insertToDaysBox(href, d)
            }
        }
        /*val builder = groupListBox.query(GroupListBox_.href.equal("cg60.htm")).build().find()
        for (i in builder[0].days.indices) {
            Log.d("getLog", builder[0].days[i].day)
        }*/

//        Log.d("addLog", groupResult.toString())
//        groupListBox.put(groupResult)
    }

    data class Range(
    val rangeStart: Int,
    val rangeEnd: Int
    )

    suspend fun getTimeTable(href: String) {
        val doc = htmlClient.getHTTP(href)
        val daysIndices = mutableListOf<Int>()
        val rowRange = mutableListOf<Range>()

        try {
            tr = doc.select("table.inf").select("tr")
        } catch (_: NullPointerException) {
            return
        }
        objectBoxManager.deleteBox(listOf(daysListBox, daysListBox))

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
            Log.d("parseLog", "rangeStart: ${it.rangeStart} rangeEnd: ${it.rangeEnd}" )
            val d = tr[it.rangeStart].select(".hd").select("[rowspan=7]").text()
            val days = DaysInTimeTableBox(
                day = d,
                href = href
            )
            Log.d("parseLog", d )
            for (i in it.rangeStart..it.rangeEnd) {
                val tri = tr[i]
                if (tri.select(".ur").toString() != "") {
                    val sid = SubjectsListBox(
                        position = tri.select(".hd").text(),
                        subject = tri.select(".z1").text(),
                        lecturer = tri.select(".z3").text(),
                        auditory = tri.select(".z2").text()
                    )
                    days.subjects.add(sid)
                }
            }
            objectBoxManager.insertToDaysBox(href, days)
        }
    }


}

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