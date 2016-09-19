package net.maytry.www.smartwiki.db


import android.content.Context
import android.database.Cursor
import net.maytry.www.smartwiki.enums.GenreItemInfoType
import net.maytry.www.smartwiki.model.GenreItemInfo
import net.maytry.www.smartwiki.utils.DateUtil

/**
 * Created by slont on 9/18/16.
 */
class GenreItemInfoTableAdapter(context: Context) : DBAdapter<GenreItemInfo>(context) {

    companion object {
        val COL_NAME = "name"
        val COL_PARENT_ID = "parent_id"
        val COL_TYPE = "type"
        val COL_CONTENT_LIST = "content_list"
        val COL_FAVORITE = "favorite"
        val COL_CREATED = "created"
        val COL_MODIFIED = "modified"
    }

    override val tableName = "genre_item_info"

    override val cols = mutableListOf(
            Pair(COL_ID, "INTEGER PRIMARY KEY AUTOINCREMENT"),
            Pair(COL_NAME, "TEXT UNIQUE"),
            Pair(COL_PARENT_ID, "INTEGER"),
            Pair(COL_TYPE, "TYPE"),
            Pair(COL_CONTENT_LIST, "TEXT"),
            Pair(COL_FAVORITE, "INTEGER"),
            Pair(COL_CREATED, "INTEGER"),
            Pair(COL_MODIFIED, "INTEGER")
    )

    override fun cursorToModel(cursor: Cursor): GenreItemInfo {
        val item = GenreItemInfo(
                id = cursor.getLong(cursor.getColumnIndex(COL_ID)),
                name = cursor.getString(cursor.getColumnIndex(COL_NAME)),
                parentID = cursor.getLong(cursor.getColumnIndex(COL_PARENT_ID)),
                type = GenreItemInfoType.intToEnum(cursor.getInt(cursor.getColumnIndex(COL_TYPE))),
                favorite = cursor.getInt(cursor.getColumnIndex(GenreItemTableAdapter.COL_FAVORITE)) == 1,
                created = DateUtil.stringToDate(cursor.getString(cursor.getColumnIndex(COL_CREATED))),
                modified = DateUtil.stringToDate(cursor.getString(cursor.getColumnIndex(COL_MODIFIED)))
        )
        return item
    }
}