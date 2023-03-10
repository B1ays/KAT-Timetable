package ru.blays.timetable

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.xwray.groupie.GroupieAdapter
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import ru.blays.timetable.SQL.DbManager
import ru.blays.timetable.databinding.ActivityMainBinding
import java.io.IOException

lateinit var dbManager: DbManager
lateinit var tr: Elements

lateinit var binding: ActivityMainBinding

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        dbManager = DbManager(this)
        setContentView(binding.root)
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        dbManager.openDB()

        if (dbManager.tableExists()) {
            initRV()
        } else {
            dbManager.reCreateDB()
            getWeb()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.app_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        dbManager.reCreateDB()
        getWeb()
        return true
    }

    override fun onDestroy() {
        super.onDestroy()
        dbManager.closeDB()
    }

    private fun initRV() {
        binding.mainRV.adapter = GroupieAdapter().apply { addAll(dbManager.readDBCell()) }
    }
    private fun showAlertDialog() {
        val alertBuilder = AlertDialog.Builder(this)
        alertBuilder.setTitle("Не удалось получить страницу!")
        alertBuilder.setMessage("Проверьте соединение с интернетом и попытайтесь обновить расписание ещё раз")
        alertBuilder.setPositiveButton("Ок") { _, _ ->
        }
        val alertCreate = alertBuilder.create()
        alertCreate.show()
    }

    private fun parseHTML(doc: Document) {
        tr = doc.select("table.inf").select("tr")
        var day = 0

        for (cell in tr) {
            if (cell.select(".hd").select("[rowspan=7]").toString() != "") {
                day += 1
                val dt = cell.select(".hd").select("[rowspan=7]").text()
                dbManager.insertToMainTable(dt, day)

                val cl = cell.select(".ur")
                if (cl.toString() != "") {
                    if (cl.count() > 1) {
                        val si1 = SecTableModel(cell.select(".hd")[1].text() + "\n1 п/г" ,cl[0].select(".z1").text(), cl[0].select(".z2").text(), cl[0].select(".z3").text(), day)
                        val si2 = SecTableModel(cell.select(".hd")[1].text() + "\n2 п/г" ,cl[1].select(".z1").text(), cl[1].select(".z2").text(), cl[1].select(".z3").text(), day)
                        dbManager.insertToSecondaryTable(si1)
                        dbManager.insertToSecondaryTable(si2)
                    } else {
                        val si = SecTableModel(cell.select(".hd")[1].text() + "\n" ,cell.select(".z1").text(), cell.select(".z2").text(), cell.select(".z3").text(), day)
                        dbManager.insertToSecondaryTable(si)
                    }
                }

            } else if (cell.select(".ur").toString() != "") {
                val cl = cell.select(".ur")
                if (cl.count() > 1) {
                    val si1 = SecTableModel(cell.select(".hd").text() + "\n1 п/г" ,cl[0].select(".z1").text(), cl[0].select(".z2").text(), cl[0].select(".z3").text(), day)
                    val si2 = SecTableModel(cell.select(".hd").text() + "\n2 п/г" ,cl[1].select(".z1").text(), cl[1].select(".z2").text(), cl[1].select(".z3").text(), day)
                    dbManager.insertToSecondaryTable(si1)
                    dbManager.insertToSecondaryTable(si2)
                } else {
                val si = SecTableModel(cell.select(".hd").text() + "\n" ,cell.select(".z1").text(), cell.select(".z2").text(), cell.select(".z3").text(), day)
                dbManager.insertToSecondaryTable(si)
                    }
            }
        }
        initRV()
    }

    private fun getWeb() {

        Thread {1
            try {
                val doc = Jsoup.connect("http://service.aviakat.ru:4256/cg60.htm").get()
                runOnUiThread {parseHTML(doc)}
            } catch (_: IOException) {
                runOnUiThread {showAlertDialog()}
            }
        }.start()
    }
}