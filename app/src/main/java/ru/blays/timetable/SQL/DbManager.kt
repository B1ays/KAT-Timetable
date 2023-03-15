package ru.blays.timetable.SQL

import android.content.ContentValues
import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.util.Log
import com.xwray.groupie.viewbinding.BindableItem
import ru.blays.timetable.Models.SecTableModel
import ru.blays.timetable.RecyclerViewItems.MainCardContainer
import ru.blays.timetable.RecyclerViewItems.RowItem
class DbManager(context: Context) {
    private val dbHelper = DbHelper(context)
    private var db: SQLiteDatabase? = null

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

    fun insertToSecondaryTable(cellModel: SecTableModel) {
        val values = ContentValues().apply() {
            put(DBNameSecondaryTableClass.COLUMN_SUBJECT_POSITION, cellModel.position)
            put(DBNameSecondaryTableClass.COLUMN_SUBJECT_NAME, cellModel.subject)
            put(DBNameSecondaryTableClass.COLUMN_SUBJECT_LECTURER, cellModel.lecturer)
            put(DBNameSecondaryTableClass.COLUMN_SUBJECT_AUDITORY, cellModel.auditory)
            put("key", cellModel.foreignKey)
        }
        db?.insert(DBNameSecondaryTableClass.TABLE_NAME, null, values)
    }

        fun readDBCell(/*table : String, columnName: String*/): MutableList<MainCardContainer> {

            val listCard: MutableList<MainCardContainer> = mutableListOf()

            var listItem: MutableList<BindableItem<*>> = mutableListOf()

            val cursorMain =
                db?.query(DBNameMainTableClass.TABLE_NAME, null, null, null, null, null, null)

            /*-----------------------------------------------------------------------------------------------------*/

            val date = cursorMain?.getColumnIndexOrThrow(DBNameMainTableClass.COLUMN_NAME_DAY)
            val key = cursorMain?.getColumnIndexOrThrow(DBNameMainTableClass.COLUMN_SUBJECT_LINK)

            /*-----------------------------------------------------------------------------------------------------*/


            while (cursorMain?.moveToNext()!!) {
                val d = cursorMain.getString(date!!)
                val k = cursorMain.getInt(key!!)
                /*Log.d("DbReadLog", "date: $d" + " | " + "key: $k")*/
                val cursorSec = db?.query(
                    DBNameSecondaryTableClass.TABLE_NAME,
                    arrayOf(
                        DBNameSecondaryTableClass.COLUMN_SUBJECT_POSITION,
                        DBNameSecondaryTableClass.COLUMN_SUBJECT_NAME,
                        DBNameSecondaryTableClass.COLUMN_SUBJECT_LECTURER,
                        DBNameSecondaryTableClass.COLUMN_SUBJECT_AUDITORY
                    ),
                    "key like ?",
                    arrayOf("$k"),
                    null,
                    null,
                    null
                )

                /*-----------------------------------------------------------------------------------------------------*/

                val positionIndex =
                    cursorSec?.getColumnIndexOrThrow(DBNameSecondaryTableClass.COLUMN_SUBJECT_POSITION)
                val subjectIndex =
                    cursorSec?.getColumnIndexOrThrow(DBNameSecondaryTableClass.COLUMN_SUBJECT_NAME)
                val lecturerIndex =
                    cursorSec?.getColumnIndexOrThrow(DBNameSecondaryTableClass.COLUMN_SUBJECT_LECTURER)
                val auditoryIndex =
                    cursorSec?.getColumnIndexOrThrow(DBNameSecondaryTableClass.COLUMN_SUBJECT_AUDITORY)

                /*-----------------------------------------------------------------------------------------------------*/

                /*Log.d("DbReadLog", "Cursor Content:")*/

                while (cursorSec?.moveToNext()!!) {
                    /*Log.d("DbReadLog", "RowItem: ${cursorSec.getString(subjectIndex!!)}")*/
                    val tmp = RowItem(
                        SecTableModel(
                            cursorSec.getString(positionIndex!!),
                            cursorSec.getString(subjectIndex!!),
                            cursorSec.getString(lecturerIndex!!),
                            cursorSec.getString(auditoryIndex!!),
                            null
                        )
                    )
                    listItem.add(tmp)
                    /*Log.d("DbReadLog", "list items Count: ${listItem.count()}")*/
                }
                listCard.add(MainCardContainer(d, listItem))
                listItem = mutableListOf()

                cursorSec.close()
            }
            cursorMain.close()

            /*Log.d("DbReadLog", "list card Count: ${listCard.count()}")*/
            return listCard
        }

    fun closeDB() {
        dbHelper.close()
    }

    fun tableExists(): Boolean {
        var exsist = true

        val cursor = db?.query(DBNameMainTableClass.TABLE_NAME, null, null, null, null, null, null)

        if (!cursor?.moveToNext()!!) {
            cursor.close()
            exsist = false
        }
        Log.d("check", "$exsist")
        cursor.close()
        return exsist
    }

    fun reCreateDB() {
        db?.execSQL(DBNameSecondaryTableClass.DELETE_TABLE)
        db?.execSQL((DBNameSecondaryTableClass.CREATE_TABLE))
        db?.execSQL(DBNameMainTableClass.DELETE_TABLE)
        db?.execSQL(DBNameMainTableClass.CREATE_TABLE)
    }

}