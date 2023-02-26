package ru.blays.timetable.SQL

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

class DbHelper(context: Context) : SQLiteOpenHelper(context, DBNameMainTableClass.DATABASE_NAME, null, DBNameMainTableClass.DB_VERSION) {

    override fun onCreate(db: SQLiteDatabase?) {
        /*db?.execSQL(DBNameMainTableClass.CREATE_TABLE)*/
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db?.execSQL(DBNameMainTableClass.DELETE_TABLE)
        onCreate(db)
    }
}