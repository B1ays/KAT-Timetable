package ru.blays.timetable

import android.os.Bundle
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.widget.Toast
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity
import io.objectbox.Box
import ru.blays.timetable.ObjectBox.Boxes.DaysInTimeTableBox
import ru.blays.timetable.ObjectBox.Boxes.GroupListBox
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

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)
        objectBoxManager = ObjectBoxManager()
        objectBoxManager.init(this)
        groupListBox = objectBoxManager.store.boxFor(GroupListBox::class.java)
        daysListBox = objectBoxManager.store.boxFor(DaysInTimeTableBox::class.java)
        htmlClient = HTMLClient()
        htmlParser = HTMLParser()
        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        /*GlobalScope.launch {
            htmlParser.createMainDB()
        }*/

        if (savedInstanceState == null) {
            initFragment("test", "test")
        }

        binding.bottomNavigationView.setOnItemSelectedListener {
            when(it.itemId) {
                R.id.item_1 -> {
                    Toast.makeText(this@MainActivity, "Item 1 clicked", Toast.LENGTH_SHORT).show()
                    replaceFragment("fragment 1", "test")
                    true
                }
                R.id.item_2 -> {
                    Toast.makeText(this@MainActivity, "Item 2 clicked", Toast.LENGTH_SHORT).show()
                    replaceFragment("fragment 2", "test")
                    true
                }
                R.id.item_3 -> {
                    Toast.makeText(this@MainActivity, "Item 3 clicked", Toast.LENGTH_SHORT).show()
                    replaceFragment("fragment 3", "test")
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

    private fun replaceFragment(arg1: String, arg2: String) {
        val fragment = TestFragment.newInstance(
            arg1,
            arg2
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