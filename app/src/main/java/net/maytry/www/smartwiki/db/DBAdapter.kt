package net.maytry.www.smartwiki.db


import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper

/**
 * Created by slont on 9/14/16.
 */
abstract class DBAdapter(private val context: Context) {

    companion object {
        internal val DB_NAME = "smartwiki"
        internal val DB_VERSION = 1
    }

    protected abstract val tableName: String
    protected abstract val cols: List<Pair<String, String>>

    protected val dbHelper: DatabaseHelper
    protected var db: SQLiteDatabase? = null

    init {
        dbHelper = DatabaseHelper(this)
    }

    class DatabaseHelper(val self: DBAdapter) : SQLiteOpenHelper(self.context, DB_NAME, null, DB_VERSION) {

        override fun onCreate(db: SQLiteDatabase) {
            db.execSQL("CREATE TABLE ${self.tableName} (" +
                    self.cols.map { p -> "${p.first} ${p.second}" }
                            .reduce { s1, s2 -> "$s1, $s2" } +
                    ");")
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            db.execSQL("DROP TABLE IF EXISTS ${self.tableName}")
            onCreate(db)
        }
    }

    fun open(): DBAdapter {
        db = dbHelper.writableDatabase
        return this
    }

    fun close() {
        dbHelper.close()
    }
}