package ru.blays.timetable

import android.os.Bundle
import android.util.Log
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import ru.blays.timetable.SQL.DbManager
import java.io.IOException

lateinit var dbManager: DbManager
lateinit var doc: Document
lateinit var tr: Elements
var newDay: Boolean = false
var day: Int? = null
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
        day = 1

        for (cell in tr) {
            if (cell.select(".hd").select("[rowspan=7]").toString() != "") {
                val dt = cell.select(".hd").select("[rowspan=7]").text()
                dbManager.insertToDB(dt, day!!)
                Log.d("parsLog", dt)
                day = day!! + 1
            } else if ((cell.select(".ur").toString() != "") && (newDay)) {
                val si = cellModel(cell.select(".z1").text(), cell.select(".z2").text(), cell.select(".z3").text())
                val position = cell.select(".hd").text()
                subjectInfo.add(si)
                Log.d("parsLog", position + ") " + si.subject + "\n" + si.lecturer + " | " + si.auditory)
            }

        }

        val data = dbManager.readDBCell()
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