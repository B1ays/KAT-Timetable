package ru.blays.timetable.SQL

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase

public class DbManager(context: Context) {
    val dbHelper = DbHelper(context)
    var db: SQLiteDatabase? = null

    fun openDB() {
        db = dbHelper.writableDatabase
    }

    fun insertToDB(day: String, link: Int) {
        val values = ContentValues().apply() {
            put(DbNameClass.COLUMN_NAME_DAY, day)
            put(DbNameClass.COLUMN_SUBJECT_LINK, link)
        }
        db?.insert(DbNameClass.TABLE_NAME, null, values)
    }

    fun readDBCell(): ArrayList<String> {
        val list = ArrayList<String>()
            val cursor = db?.query(DbNameClass.TABLE_NAME, null, null, null, null, null, null)

        while (cursor?.moveToNext()!!) {
            val cellContent = cursor.getString(cursor.getColumnIndexOrThrow(DbNameClass.COLUMN_NAME_DAY))
            list.add(cellContent)
        }
        cursor?.close()

        return list
    }

    fun closeDB() {
        dbHelper.close()
    }

}