package ru.blays.timetable

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import com.xwray.groupie.GroupieAdapter
import io.objectbox.Box
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.blays.timetable.ObjectBox.Boxes.DaysInTimeTableBox
import ru.blays.timetable.ObjectBox.Boxes.GroupListBox
import ru.blays.timetable.ObjectBox.ObjectBoxManager
import ru.blays.timetable.ParseUtils.HTMLParser
import ru.blays.timetable.SQL.DbManager
import ru.blays.timetable.WebUtils.HTMLClient
import ru.blays.timetable.databinding.ActivityMainBinding

lateinit var dbManager: DbManager
lateinit var htmlClient: HTMLClient
lateinit var htmlParser: HTMLParser
lateinit var binding: ActivityMainBinding

lateinit var objectBoxManager: ObjectBoxManager
lateinit var groupListBox: Box<GroupListBox>
lateinit var daysListBox: Box<DaysInTimeTableBox>

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        objectBoxManager = ObjectBoxManager
        objectBoxManager.init(this)
        groupListBox = objectBoxManager.store.boxFor(GroupListBox::class.java)
        daysListBox = objectBoxManager.store.boxFor(DaysInTimeTableBox::class.java)
        htmlClient = HTMLClient()
        htmlParser = HTMLParser()
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        val daysInTimeTableBox = DaysInTimeTableBox()

        GlobalScope.launch {
            htmlParser.createMainDB()
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.app_bar_menu, menu)
        return true
    }


    override fun onOptionsItemSelected(item: MenuItem): Boolean {

        return true
    }

    override fun onDestroy() {
        super.onDestroy()
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
}