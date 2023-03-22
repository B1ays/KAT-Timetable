package ru.blays.timetable.UI

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import io.objectbox.Box
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import ru.blays.timetable.ObjectBox.Boxes.DaysInTimeTableBox
import ru.blays.timetable.ObjectBox.Boxes.GroupListBox
import ru.blays.timetable.ObjectBox.Boxes.SubjectsListBox
import ru.blays.timetable.ObjectBox.ObjectBoxManager
import ru.blays.timetable.ParseUtils.HTMLParser
import ru.blays.timetable.R
import ru.blays.timetable.WebUtils.HTMLClient
import ru.blays.timetable.databinding.ActivityMainBinding


lateinit var htmlClient: HTMLClient
lateinit var htmlParser: HTMLParser
private lateinit var binding: ActivityMainBinding

lateinit var objectBoxManager: ObjectBoxManager
lateinit var groupListBox: Box<GroupListBox>
lateinit var daysListBox: Box<DaysInTimeTableBox>
lateinit var subjectsListBox: Box<SubjectsListBox>

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        objectBoxManager = ObjectBoxManager()
        objectBoxManager.init(this)
        groupListBox = objectBoxManager.store.boxFor(GroupListBox::class.java)
        daysListBox = objectBoxManager.store.boxFor(DaysInTimeTableBox::class.java)
        subjectsListBox = objectBoxManager.store.boxFor(SubjectsListBox::class.java)
        htmlClient = HTMLClient()
        htmlParser = HTMLParser()
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        if (savedInstanceState == null) {
            runBlocking {
                if (groupListBox.isEmpty) {
                    val job = GlobalScope.launch { htmlParser.createMainDB() }
                    job.join()
                    initFragmentList("1")
                } else {
                    initFragmentList("2")
                }
            }
        }

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.item_1 -> {
                    replaceFragmentList("cfg")
                    true
                }
                R.id.item_2 -> {
                    replaceFragmentWeek("cg60.htm")
                    true
                }
                R.id.item_3 -> {
                    replaceFragmentTest("fragment 3", "test")
                    true
                }
                else -> false
            }
        }
    }

    override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.app_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Toast.makeText(this@MainActivity, "reset clicked", Toast.LENGTH_SHORT).show()
        return true
    }

    private fun replaceFragmentTest(arg1: String, arg2: String) {
        val fragment = FragmentTest.newInstance(
            arg1,
            arg2
        )
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.content, fragment)
            .commit()
    }

    private fun replaceFragmentWeek(href: String) {
        val fragment = FragmentWeekTimeTable.newInstance(
            href
        )
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.content, fragment)
            .commit()
    }


    private fun initFragmentList(arg1: String) {
        val fragment = FragmentGroupsList.newInstance(
            arg1
        )
        supportFragmentManager.beginTransaction()
            .add(R.id.content, fragment)
            .commit()
    }

    private fun replaceFragmentList(arg1: String) {
        val fragment = FragmentGroupsList.newInstance(
            arg1
        )
        supportFragmentManager.beginTransaction()
            .addToBackStack(null)
            .replace(R.id.content, fragment)
            .commit()
    }

    fun changeTitle(title: String) {
        binding.toolbar.title = title
    }

    private fun showAlertDialog() {
        val alertBuilder = AlertDialog.Builder(this)
        alertBuilder.setTitle("Не удалось получить страницу!")
        alertBuilder.setMessage("Проверьте соединение с интернетом и попытайтесь обновить расписание ещё раз")
        alertBuilder.setPositiveButton("Ок") { _, _ -> }
        val alertCreate = alertBuilder.create()
        alertCreate.show()
    }
}