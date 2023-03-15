package ru.blays.timetable.ParseUtils

import android.content.Context
import android.util.Log
import kotlinx.coroutines.coroutineScope
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import ru.blays.timetable.Models.SecTableModel
import ru.blays.timetable.ObjectBox.DaysInTimeTableBox
import ru.blays.timetable.ObjectBox.GroupListBox
import ru.blays.timetable.SQL.DbManager
import ru.blays.timetable.daysListBox
import ru.blays.timetable.groupListBox
import ru.blays.timetable.htmlClient

lateinit var tr: Elements
lateinit var dbManager: DbManager

class HTMLParser() {

    fun parseHTML(context: Context, doc: Document) {
        dbManager = DbManager(context)


        try {
            tr = doc.select("table.inf").select("tr")
        } catch (_: NullPointerException) {
            return
        }
        var day = 0

        for (cell in tr) {
            if (cell.select(".hd").select("[rowspan=7]").toString() != "") {
                day += 1
                val dt = cell.select(".hd").select("[rowspan=7]").text()
                Log.d("parseLog", dt.toString())
                dbManager.insertToMainTable(dt, day)
                Log.d("PasreLog", dt + day)

                val cl = cell.select(".ur")
                if (cl.toString() != "") {
                    if (cl.count() > 1) {
                        val si1 = SecTableModel(
                            cell.select(".hd")[1].text() + "\n1 п/г",
                            cl[0].select(".z1").text(),
                            cl[0].select(".z2").text(),
                            cl[0].select(".z3").text(),
                            day
                        )
                        val si2 = SecTableModel(
                            cell.select(".hd")[1].text() + "\n2 п/г",
                            cl[1].select(".z1").text(),
                            cl[1].select(".z2").text(),
                            cl[1].select(".z3").text(),
                            day
                        )
                        dbManager.insertToSecondaryTable(si1)
                        dbManager.insertToSecondaryTable(si2)
                    } else {
                        val si = SecTableModel(
                            cell.select(".hd")[1].text() + "\n",
                            cell.select(".z1").text(),
                            cell.select(".z2").text(),
                            cell.select(".z3").text(),
                            day
                        )
                        dbManager.insertToSecondaryTable(si)
                    }
                }

            } else if (cell.select(".ur").toString() != "") {
                val cl = cell.select(".ur")
                if (cl.count() > 1) {
                    val si1 = SecTableModel(
                        cell.select(".hd").text() + "\n1 п/г",
                        cl[0].select(".z1").text(),
                        cl[0].select(".z2").text(),
                        cl[0].select(".z3").text(),
                        day
                    )
                    val si2 = SecTableModel(
                        cell.select(".hd").text() + "\n2 п/г",
                        cl[1].select(".z1").text(),
                        cl[1].select(".z2").text(),
                        cl[1].select(".z3").text(),
                        day
                    )
                    dbManager.insertToSecondaryTable(si1)
                    dbManager.insertToSecondaryTable(si2)
                } else {
                    val si = SecTableModel(
                        cell.select(".hd").text() + "\n",
                        cell.select(".z1").text(),
                        cell.select(".z2").text(),
                        cell.select(".z3").text(),
                        day
                    )
                    dbManager.insertToSecondaryTable(si)
                }
            }
        }
    }

    suspend fun createMainDB() = coroutineScope {
        try {
            val doc = htmlClient.getHTTP("cg.htm")
            tr = doc.select("table.inf").select("tr")
        } catch (_: NullPointerException) {
            return@coroutineScope
        }

        groupListBox.removeAll()
        daysListBox.removeAll()

        tr.forEach {
            if (it.select(".ur").toString() != "") {
                val group = it.select(".z0")
                val href = group.attr("href")
                val result = GroupListBox(groupCode = group.text(), href = href)
                groupListBox.put(result)
                Log.d("pasreLog", group.text() + " | " + href)
            }
        }
        val search = groupListBox.all
        search.forEach {it1 ->
            val doc = htmlClient.getHTTP(it1.href)
            val rows = doc.select("table.inf").select("tr")
            rows.forEach { it2 ->
                if (it2.select(".hd").select("[rowspan=7]").toString() != "") {
                    val result = DaysInTimeTableBox(day = it2.select(".hd").select("[rowspan=7]").text(), href = it1.href)
                    Log.d("daysParseLog", result.day)
                    daysListBox.put(result)
                }
            }
        }
    }
}