package ru.blays.timetable.ObjectBox

import android.content.Context
import io.objectbox.Box
import io.objectbox.BoxStore
import ru.blays.timetable.ObjectBox.Boxes.*
import ru.blays.timetable.daysListBox
import ru.blays.timetable.groupListBox
import ru.blays.timetable.subjectsListBox

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
        val groupBox = groupListBox.query(GroupListBox_.href.equal(href)).build().find()
        val dayBox = daysListBox.query(DaysInTimeTableBox_.href.equal(href)).build().find()
        if (dayBox.isNotEmpty()) {
            try {
                val subjectsBox = dayBox[0].subjects

                dayBox.remove(dayBox[0])
                subjectsListBox.remove(subjectsBox)
            } catch (_: IndexOutOfBoundsException) { }
        }
    }

    fun insertToGroupBox(boxModel: GroupListBox) {
        groupListBox.put(boxModel)
    }

    fun insertToDaysBox(href: String, boxModel: DaysInTimeTableBox) {
        val groupRow = groupListBox.query(GroupListBox_.href.equal(href)).build().find()
        groupRow[0].days.add(boxModel)
        groupListBox.put(groupRow)
    }

    fun getGroupListFromBox(): MutableList<GroupListBox>?{
        return groupListBox.all
    }

    fun getDaysFromTable(href: String): List<GroupListBox> {
        return groupListBox.query(GroupListBox_.href.equal(href)).build().find()
    }


    /*fun deleteFromBox(box: Box<*>) {

    }*/
}