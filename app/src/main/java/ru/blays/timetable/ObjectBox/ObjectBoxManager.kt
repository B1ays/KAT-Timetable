package ru.blays.timetable.ObjectBox

import android.content.Context
import android.util.Log
import io.objectbox.Box
import io.objectbox.BoxStore
import ru.blays.timetable.ObjectBox.Boxes.DaysInTimeTableBox
import ru.blays.timetable.ObjectBox.Boxes.GroupListBox
import ru.blays.timetable.ObjectBox.Boxes.GroupListBox_
import ru.blays.timetable.ObjectBox.Boxes.MyObjectBox
import ru.blays.timetable.RecyclerViewItems.SimpleListItem
import ru.blays.timetable.UI.groupListBox

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

    fun insertToGroupBox(boxModel: GroupListBox) {
        groupListBox.put(boxModel)
    }

    fun insertToDaysBox(href: String, boxModel: DaysInTimeTableBox) {
        val groupRow = groupListBox.query(GroupListBox_.href.equal(href)).build().find()
        Log.d("getLog", groupRow.toString())
        groupRow[0].days.add(boxModel)
        groupListBox.put(groupRow)
    }

    fun getGroupListFromBox(): MutableList<SimpleListItem> {
        val groupList = mutableListOf<SimpleListItem>()
        val get = groupListBox.all
        for (i in get.indices) { groupList.add(SimpleListItem(groupCode = get[i].groupCode, onClick = {
        })) }
        return groupList
    }


    /*fun deleteFromBox(box: Box<*>) {

    }*/
}