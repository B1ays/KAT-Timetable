package ru.blays.timetable

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import ru.blays.timetable.RecyclerClasses.TimeTableRecyclerAdapter
import ru.blays.timetable.SQL.DBNameSecondaryTableClass
import ru.blays.timetable.SQL.DbManager
import ru.blays.timetable.databinding.ActivityMainBinding
import java.io.IOException

lateinit var dbManager: DbManager
lateinit var doc: Document
lateinit var tr: Elements

lateinit var adapter: TimeTableRecyclerAdapter
lateinit var binding: ActivityMainBinding

private var cellList: MutableList<String> = mutableListOf()


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        getWeb()

        dbManager = DbManager(this)


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
        Log.d("dbLog", "Initialize Recycler")

        setUpAdapter()
    }

    private fun getWeb() {
        Thread {
            try {
                doc = Jsoup.connect("http://service.aviakat.ru:4256/cg60.htm").get()
            } catch (_: IOException) {
            }
            Log.d("dbLog", "HTML parsed")
            runOnUiThread {
                parseHTML()
            }
        }.start()
    }
    private fun setUpAdapter() {

        cellList = dbManager.readDBCell(DBNameSecondaryTableClass.TABLE_NAME, DBNameSecondaryTableClass.COLUMN_SUBJECT_NAME)

        adapter = TimeTableRecyclerAdapter(this, cellList)

        binding.timetableRV.adapter = adapter
        binding.timetableRV.layoutManager = LinearLayoutManager(this)
    }


}