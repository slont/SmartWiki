package net.maytry.www.smartwiki.db


import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import net.maytry.www.smartwiki.model.AbstractModel

/**
 * Created by slont on 9/14/16.
 */
abstract class DBAdapter<T : AbstractModel>(private val context: Context) {

    companion object {
        val COL_ID = "_id"
        internal val DB_NAME = "smartwiki.db"
        internal val DB_VERSION = 1
    }

    abstract val tableName: String
    abstract val cols: List<Pair<String, String>>

    protected val dbHelper: DatabaseHelper
    protected lateinit var db: SQLiteDatabase

    init {
        dbHelper = DatabaseHelper(context)
    }

    class DatabaseHelper(val context: Context) : SQLiteOpenHelper(context, DB_NAME, null, DB_VERSION) {

        /**
         * DBが存在しない場合に一度だけ呼ばれるため、ここで各テーブルを初期化するような処理を書く
         */
        override fun onCreate(db: SQLiteDatabase) {
            val genreTableAdapter = GenreTableAdapter(context)
            db.execSQL("CREATE TABLE ${genreTableAdapter.tableName} (" +
                    genreTableAdapter.cols.map { p -> "${p.first} ${p.second}" }
                            .reduce { s1, s2 -> "$s1, $s2" } + ");")

            val itemTableAdapter = GenreItemTableAdapter(context)
            db.execSQL("CREATE TABLE ${itemTableAdapter.tableName} (" +
                    itemTableAdapter.cols.map { p -> "${p.first} ${p.second}" }
                            .reduce { s1, s2 -> "$s1, $s2" } + ");")

            val infoTableAdapter = GenreItemInfoTableAdapter(context)
            db.execSQL("CREATE TABLE ${infoTableAdapter.tableName} (" +
                    infoTableAdapter.cols.map { p -> "${p.first} ${p.second}" }
                            .reduce { s1, s2 -> "$s1, $s2" } + ");")
        }

        override fun onUpgrade(db: SQLiteDatabase, oldVersion: Int, newVersion: Int) {
            val genreTableAdapter = GenreTableAdapter(context)
            db.execSQL("DROP TABLE IF EXISTS ${genreTableAdapter.tableName};")

            val itemTableAdapter = GenreItemTableAdapter(context)
            db.execSQL("DROP TABLE IF EXISTS ${itemTableAdapter.tableName};")

            val infoTableAdapter = GenreItemInfoTableAdapter(context)
            db.execSQL("DROP TABLE IF EXISTS ${infoTableAdapter.tableName};")
            onCreate(db)
        }
    }

    fun open(): DBAdapter<T> {
        db = dbHelper.writableDatabase
        return this
    }

    fun close() {
        dbHelper.close()
    }

    fun insert(obj: T): Long {
        return db.insert(tableName, null, contentValues(obj))
    }

    fun insertAll(list: List<T>): Long {
        var result = -1L
        list.forEach { obj ->
            result = insert(obj)
        }
        return result
    }

    fun update(obj: T): Int {
        return db.update(tableName, contentValues(obj), "_id=${obj.id}", null)
    }

    fun deleteAll(): Boolean {
        return db.delete(tableName, null, null) > 0
    }

    fun delete(id: Int): Boolean {
        return db.delete(tableName, COL_ID + "=" + id, null) > 0
    }

    fun find(where: String): List<T> {
        val objList: MutableList<T> = mutableListOf()
        val cursor = db.rawQuery("SELECT * FROM $tableName WHERE $where", arrayOf())
        try {
            while (cursor.moveToNext()) {
                objList.add(cursorToModel(cursor))
            }
        } finally {
            cursor.close()
        }
        return objList
    }

    fun findOne(id: Long): T? {
        var obj: T? = null
        val cursor = db.rawQuery("SELECT * FROM $tableName WHERE ${DBAdapter.COL_ID}=?", arrayOf(id.toString()))
        try {
            if (cursor.moveToNext()) {
                obj = cursorToModel(cursor)
            }
        } finally {
            cursor.close()
        }
        return obj
    }

    fun findAll(): List<T> {
        val objList: MutableList<T> = mutableListOf()
        val cursor = db.rawQuery("SELECT * FROM $tableName", arrayOf())
        try {
            while (cursor.moveToNext()) {
                objList.add(cursorToModel(cursor))
            }
        } finally {
            cursor.close()
        }
        return objList
    }

    abstract fun cursorToModel(cursor: Cursor): T
    abstract fun contentValues(obj: T): ContentValues
}