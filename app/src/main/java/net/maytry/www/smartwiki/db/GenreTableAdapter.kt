package net.maytry.www.smartwiki.db


import android.content.Context
import android.database.Cursor
import net.maytry.www.smartwiki.model.Genre
import net.maytry.www.smartwiki.utils.DateUtil

/**
 * Created by slont on 9/15/16.
 */
class GenreTableAdapter(context: Context) : DBAdapter<Genre>(context) {

    companion object {
        val COL_NAME = "name"
        val COL_FAVORITE = "favorite"
        val COL_CREATED = "created"
        val COL_UPDATED = "updated"
    }

    override val tableName = "genre"

    override val cols = mutableListOf(
            Pair(COL_ID, "INTEGER PRIMARY KEY AUTOINCREMENT"),
            Pair(COL_NAME, "TEXT UNIQUE"),
            Pair(COL_FAVORITE, "INTEGER"),
            Pair(COL_CREATED, "INTEGER"),
            Pair(COL_UPDATED, "INTEGER")
    )

    override fun cursorToModel(cursor: Cursor): Genre {
        val genre = Genre(
                id = cursor.getLong(cursor.getColumnIndex(DBAdapter.COL_ID)),
                name = cursor.getString(cursor.getColumnIndex(GenreTableAdapter.COL_NAME)),
                favorite = cursor.getInt(cursor.getColumnIndex(GenreTableAdapter.COL_FAVORITE)) == 1,
                created = DateUtil.stringToDate(cursor.getString(cursor.getColumnIndex(GenreTableAdapter.COL_CREATED))),
                updated = DateUtil.stringToDate(cursor.getString(cursor.getColumnIndex(GenreTableAdapter.COL_UPDATED)))
        )
        return genre
    }
}