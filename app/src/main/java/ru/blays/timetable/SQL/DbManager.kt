package ru.blays.timetable.SQL

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import ru.blays.timetable.cellModel

public class DbManager(context: Context) {
    val dbHelper = DbHelper(context)
    var db: SQLiteDatabase? = null

    fun openDB() {
        db = dbHelper.writableDatabase
    }

    fun insertToMainTable(day: String, link: Int) {
        val values = ContentValues().apply() {
            put(DBNameMainTableClass.COLUMN_NAME_DAY, day)
            put(DBNameMainTableClass.COLUMN_SUBJECT_LINK, link)
        }
        db?.insert(DBNameMainTableClass.TABLE_NAME, null, values)
    }

    fun insertToSecondaryTable(cellModel: cellModel) {
        val values = ContentValues().apply() {
            put(DBNameSecondaryTableClass.COLUMN_SUBJECT_POSITION, cellModel.position)
            put(DBNameSecondaryTableClass.COLUMN_SUBJECT_NAME, cellModel.subjectName)
            put(DBNameSecondaryTableClass.COLUMN_SUBJECT_LECTURER, cellModel.lecturer)
            put(DBNameSecondaryTableClass.COLUMN_SUBJECT_AUDITORY, cellModel.auditory)
            put("key", cellModel.foreignKey)
        }
        db?.insert(DBNameSecondaryTableClass.TABLE_NAME, null, values)
    }

    fun readDBCell(table : String, columnName: String): ArrayList<String> {
        val list = ArrayList<String>()
            val cursor = db?.query(table, null, null, null, null, null, null)

        while (cursor?.moveToNext()!!) {
            val cellContent = cursor.getString(cursor.getColumnIndexOrThrow(columnName))
            list.add(cellContent)
        }
        cursor?.close()

        return list
    }

    fun closeDB() {
        dbHelper.close()
    }

    fun reCreateDB() {
        db?.execSQL(DBNameSecondaryTableClass.DELETE_TABLE)
        db?.execSQL((DBNameSecondaryTableClass.CREATE_TABLE))
        db?.execSQL(DBNameMainTableClass.DELETE_TABLE)
        db?.execSQL(DBNameMainTableClass.CREATE_TABLE)
    }

}