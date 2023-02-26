package ru.blays.timetable

import android.os.Bundle
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import ru.blays.timetable.SQL.DBNameMainTableClass
import ru.blays.timetable.SQL.DBNameSecondaryTableClass
import ru.blays.timetable.SQL.DbManager
import java.io.IOException

lateinit var dbManager: DbManager
lateinit var doc: Document
lateinit var tr: Elements
/*var days: ArrayList<String> = ArrayList()
var dates: ArrayList<String> = ArrayList()*/
var subjectInfo: ArrayList<cellModel> = ArrayList()

lateinit var resultView: TextView
lateinit var scroll: LinearLayout

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getWeb()
        resultView = findViewById(R.id.resultView)
        dbManager = DbManager(this)

        /*list.setTextColor(R.color.white)*/

    }

    override fun onDestroy() {
        super.onDestroy()
        dbManager.closeDB()
    }

    private fun parseHTML() {
        tr = doc.select("table.inf").select("tr")
        dbManager.openDB()
        dbManager.reCreateDB()
        var day: Int = 1

        for (cell in tr) {
            if (cell.select(".hd").select("[rowspan=7]").toString() != "") {
                val dt = cell.select(".hd").select("[rowspan=7]").text()
                dbManager.insertToMainTable(dt, day)
                /*Log.d("parsLog", dt)*/
                day = day + 1
            } else if (cell.select(".ur").toString() != "") {
                val si = cellModel(cell.select(".hd").text() ,cell.select(".z1").text(), cell.select(".z2").text(), cell.select(".z3").text(), day)

                dbManager.insertToSecondaryTable(si)
                /*Log.d("parsLog", si.position + ") " + si.subjectName + "\n" + si.lecturer + " | " + si.auditory)*/
            }

        }

        var data = dbManager.readDBCell(DBNameMainTableClass.TABLE_NAME, DBNameMainTableClass.COLUMN_NAME_DAY)
        data.forEach {
            resultView.append(it)
            resultView.append("\n")
        }

        data = dbManager.readDBCell(DBNameSecondaryTableClass.TABLE_NAME, DBNameSecondaryTableClass.COLUMN_SUBJECT_NAME)
        data.forEach {
            resultView.append(it)
            resultView.append("\n")
        }
    }

    private fun getWeb() {
        Thread {
            try {
                doc = Jsoup.connect("http://service.aviakat.ru:4256/cg60.htm").get()
            } catch (_: IOException) {
            }
            runOnUiThread {
                parseHTML()
            }
        }.start()
    }
}