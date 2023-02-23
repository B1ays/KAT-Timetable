package ru.blays.timetable

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.FrameLayout
import android.widget.FrameLayout.LayoutParams
import android.widget.LinearLayout
import android.widget.TextView
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.IOException

lateinit var doc: Document
lateinit var tr: Elements
var newDay: Boolean = false
/*lateinit var resultView: TextView*/
var timeTable: ArrayList<String> = ArrayList()
lateinit var scroll: LinearLayout
lateinit var frame: FrameLayout
lateinit var list: TextView

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        getWeb()
        /*resultView = findViewById(R.id.text)*/
        scroll = findViewById(R.id.scroll)

        frame = FrameLayout(this)
        list = TextView(this)

        frame.setBackgroundResource(R.drawable.card_background)
        frame.setPadding(12, 12, 12, 12)
        frame.layoutParams = LayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT)

        /*list.setTextColor(R.color.white)*/

    }

    private fun parseHTML() {
        tr = doc.select("table.inf").select("tr")

        for (cell in tr) {
            if (cell.select(".hd0").toString() != "") {
                /*Log.d("parseLog", "\n" + "new line")*/
                /*resultView.append("\n")*/
            } else if (cell.select(".hd").select("[rowspan=7]").toString() != "") {
                /*val cl = cell.text()*/
                /*Log.d("parseLog", cl)*/
                /*resultView.append(cl + "\n")*/
            } else if (cell.select(".ur").toString() != "") {
                val cl = cell.select(".z1").text() + " | " + cell.select(".z2").text() + " | " + cell.select(".z3").text()
                /*Log.d("parsLog", cl)*/
                timeTable.add(cl)
                /*resultView.append(cl + "\n")*/
            }
        }

        try {
            for (i in timeTable.indices) {
                val ls = list
                ls.text = timeTable[i]
                scroll.addView(ls, i)
            }
        } catch (_: java.lang.IllegalStateException) { }


        /*list.text = timeTable[1].toString()
        val list1 = list
        scroll.addView(list1, 0)*/

        /* tr.forEach {
            if ((it.select(".hd0") != null) && (it.select(".hd0").toString() != "")) {
                Log.d("parseLog", "\n" + "new line")
                resultView.append("\n")
            } else if ((it.select(".ur") != null) && (it.select(".ur").toString() != "")) {
                val cell = it.select(".z1").text() + " | " + it.select(".z2").text() + " | " + it.select(".z3").text()
                Log.d("parsLog", cell)
                resultView.append(cell + "\n")
            }*/
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