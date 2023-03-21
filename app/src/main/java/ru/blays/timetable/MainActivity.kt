package ru.blays.timetable

import android.os.Bundle
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import io.objectbox.Box
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch
import ru.blays.timetable.ObjectBox.Boxes.DaysInTimeTableBox
import ru.blays.timetable.ObjectBox.Boxes.GroupListBox
import ru.blays.timetable.ObjectBox.Boxes.SubjectsListBox
import ru.blays.timetable.ObjectBox.ObjectBoxManager
import ru.blays.timetable.ParseUtils.HTMLParser
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

        GlobalScope.launch {
//            htmlParser.createMainDB()
            htmlParser.getTimeTable("cg60.htm")
        }

        if (savedInstanceState == null) {
            initFragmentList("dfgdf")
        }


        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.item_1 -> {
                    replaceFragmentList("cfg")
                    true
                }
                R.id.item_2 -> {
                    replaceFragmentTest("fragment 2", "test")
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

    /*override fun onCreateOptionsMenu(menu: Menu?): Boolean {
        val inflater: MenuInflater = menuInflater
        inflater.inflate(R.menu.app_bar_menu, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        Toast.makeText(this@MainActivity, "reset clicked", Toast.LENGTH_SHORT).show()
        return true
    }*/

    override fun onDestroy() {
        super.onDestroy()
    }

    private fun initFragment(arg1: String, arg2: String) {
            val fragment = TestFragment.newInstance(
                arg1,
                arg2
            )
            supportFragmentManager.beginTransaction()
                .add(R.id.content, fragment).commit()
    }

    private fun replaceFragmentTest(arg1: String, arg2: String) {
        val fragment = TestFragment.newInstance(
            arg1,
            arg2
        )
        supportFragmentManager.beginTransaction()
            .replace(R.id.content, fragment).commit()
    }

    private fun initFragmentList(arg1: String) {
        val fragment = MainScreenList.newInstance(
            arg1
        )
        supportFragmentManager.beginTransaction()
            .add(R.id.content, fragment).commit()
    }
    private fun replaceFragmentList(arg1: String) {
        val fragment = MainScreenList.newInstance(
            arg1
        )
        supportFragmentManager.beginTransaction()
            .replace(R.id.content, fragment).commit()
    }

   /* private fun initRV() {
        binding.mainRV.adapter = GroupieAdapter().apply { addAll(dbManager.readDBCell()) }
    }*/
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