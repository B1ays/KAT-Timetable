package ru.blays.timetable.SQL

object DBNameSecondaryTableClass {
    const val TABLE_NAME = "SubjectsInTimetable"
    const val COLUMN_SUBJECT_POSITION = "Position"
    const val COLUMN_SUBJECT_NAME = "Subject"
    const val COLUMN_SUBJECT_AUDITORY = "Auditory"
    const val COLUMN_SUBJECT_LECTURER = "Lecturer"

    const val CREATE_TABLE = "CREATE TABLE IF NOT EXISTS $TABLE_NAME" +
            "( id INTEGER PRIMARY KEY, " +
            "$COLUMN_SUBJECT_POSITION INTEGER, " +
            "$COLUMN_SUBJECT_NAME TEXT, " +
            "$COLUMN_SUBJECT_LECTURER TEXT, " +
            "$COLUMN_SUBJECT_AUDITORY, " +
            "key INTEGER )"

    const val DELETE_TABLE = "DROP TABLE IF EXISTS $TABLE_NAME"
}