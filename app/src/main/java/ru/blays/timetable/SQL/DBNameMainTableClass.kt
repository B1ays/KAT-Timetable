package ru.blays.timetable.SQL

object DBNameMainTableClass {
    const val TABLE_NAME = "DaysInTimetable"
    const val COLUMN_NAME_DAY = "Day"
    const val COLUMN_SUBJECT_LINK = "key"

    const val DB_VERSION = 1
    const val DATABASE_NAME = "Timetable.db"

    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS $TABLE_NAME" +
            "( id INTEGER PRIMARY KEY, " +
            "$COLUMN_NAME_DAY TEXT, " +
            "$COLUMN_SUBJECT_LINK INTEGER )"

    const val DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
}