package ru.blays.timetable.ObjectBox

import android.content.Context
import android.util.Log
import io.objectbox.Box
import io.objectbox.BoxStore
import ru.blays.timetable.Compose.daysListBox
import ru.blays.timetable.Compose.groupListBox
import ru.blays.timetable.ObjectBox.Boxes.*

class ObjectBoxManager {
    lateinit var store: BoxStore
        private set

    fun init(context: Context) {
        store = MyObjectBox.builder()
            .androidContext(context.applicationContext)
            .build()
    }

    fun deleteBox(listOfBox: List<Box<*>>) {
        listOfBox.forEach {
            it.removeAll()
        }
    }

    fun deleteTimeTable(href: String) {
        val dayBox = daysListBox.query(DaysInTimeTableBox_.href.equal(href)).build().find()
        if (dayBox.isNotEmpty()) {
            try {
                /*val subjectsBox = dayBox[0].subjects*/
                daysListBox.remove(dayBox)
                /*subjectsListBox.remove(subjectsBox)*/
            } catch (e: IndexOutOfBoundsException) {
                Log.d("DeleteLog", e.toString())
            }
        }
    }

    fun insertTimetableToBox(href: String, updateTime: String, boxModel: DaysInTimeTableBox) {
        val groupRow = groupListBox.query(GroupListBox_.href.equal(href)).build().find()
        groupRow[0].days.add(boxModel)
        groupRow[0].updateTime = updateTime
        groupListBox.put(groupRow)
    }


    fun getDaysFromTable(href: String): List<GroupListBox> {
        return groupListBox.query(GroupListBox_.href.equal(href)).build().find()
    }
}