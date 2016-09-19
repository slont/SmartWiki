package net.maytry.www.smartwiki.db


import android.content.Context
import android.database.Cursor
import net.maytry.www.smartwiki.model.Genre
import net.maytry.www.smartwiki.utils.DateUtil

/**
 * Created by slont on 9/15/16.
 */
class GenreTableAdapter(context: Context) : DBAdapter(context) {

    companion object {
        val COL_ID = "id"
        val COL_NAME = "name"
        val COL_FAVORITE = "favorite"
        val COL_CREATED = "created"
        val COL_MODIFIED = "modified"
    }

    override val tableName = "genre"

    override val cols = mutableListOf(
            Pair(COL_ID, "INTEGER PRIMARY KEY AUTOINCREMENT"),
            Pair(COL_NAME, "TEXT UNIQUE"),
            Pair(COL_FAVORITE, "INTEGER"),
            Pair(COL_CREATED, "INTEGER"),
            Pair(COL_MODIFIED, "INTEGER")
    )

    fun insert(genre: Genre): Long {
        val values = genre.contentValues()
        return db!!.insert(tableName, null, values)
    }

    fun deleteAll(): Boolean {
        return db!!.delete(tableName, null, null) > 0
    }

    fun delete(id: Int): Boolean {
        return db!!.delete(tableName, COL_ID + "=" + id, null) > 0
    }

    fun selectByID(id: Long): Genre? {
        var genre: Genre? = null
        val cursor = db!!.rawQuery("SELECT * FROM ? WHERE id=?", arrayOf(tableName, "id5000"))
        try {
            if (cursor.moveToNext()) {
                genre = cursorToModel(cursor)
            }
        } finally {
            cursor.close()
        }
        return genre
    }

    fun selectAll(): List<Genre> {
        val genreList: MutableList<Genre> = mutableListOf()
        val cursor = db!!.query(tableName, null, null, null, null, null, null)
        try {
            if (cursor.moveToNext()) {
                val genre = cursorToModel(cursor)
                genreList.add(genre)
            }
        } finally {
            cursor.close()
        }
        return genreList
    }

    fun cursorToModel(c: Cursor): Genre {
        val genre = Genre(
                id = c.getLong(c.getColumnIndex(GenreTableAdapter.COL_ID)),
                name = c.getString(c.getColumnIndex(GenreTableAdapter.COL_NAME)),
                favorite = c.getInt(c.getColumnIndex(GenreTableAdapter.COL_FAVORITE)) == 1,
                created = DateUtil.stringToDate(c.getString(c.getColumnIndex(GenreTableAdapter.COL_CREATED))),
                modified = DateUtil.stringToDate(c.getString(c.getColumnIndex(GenreTableAdapter.COL_MODIFIED)))
        )
        return genre
    }
}