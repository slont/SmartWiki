package net.maytry.www.smartwiki.db


import android.content.Context
import android.database.Cursor
import net.maytry.www.smartwiki.model.GenreItem
import net.maytry.www.smartwiki.utils.DateUtil

/**
 * Created by slont on 9/15/16.
 */
class GenreItemTableAdapter(context: Context) : DBAdapter<GenreItem>(context) {

    companion object {
        val COL_NAME = "name"
        val COL_PARENT_ID = "parent_id"
        val COL_FAVORITE = "favorite"
        val COL_CREATED = "created"
        val COL_MODIFIED = "modified"
    }

    override val tableName = "genre_item"

    override val cols = mutableListOf(
            Pair(COL_ID, "INTEGER PRIMARY KEY AUTOINCREMENT"),
            Pair(COL_NAME, "TEXT UNIQUE"),
            Pair(COL_PARENT_ID, "INTEGER"),
            Pair(COL_FAVORITE, "INTEGER"),
            Pair(COL_CREATED, "INTEGER"),
            Pair(COL_MODIFIED, "INTEGER")
    )

    override fun cursorToModel(cursor: Cursor): GenreItem {
        val item = GenreItem(
                id = cursor.getLong(cursor.getColumnIndex(COL_ID)),
                name = cursor.getString(cursor.getColumnIndex(COL_NAME)),
                parentId = cursor.getLong(cursor.getColumnIndex(COL_PARENT_ID)),
                favorite = cursor.getInt(cursor.getColumnIndex(COL_FAVORITE)) == 1,
                created = DateUtil.stringToDate(cursor.getString(cursor.getColumnIndex(COL_CREATED))),
                modified = DateUtil.stringToDate(cursor.getString(cursor.getColumnIndex(COL_MODIFIED)))
        )
        return item
    }

}