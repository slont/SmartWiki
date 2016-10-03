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
        val COL_UPDATED = "updated"
    }

    override val tableName = "genre_item_info"

    override val cols = mutableListOf(
            Pair(COL_ID, "INTEGER PRIMARY KEY AUTOINCREMENT"),
            Pair(COL_NAME, "TEXT"),
            Pair(COL_PARENT_ID, "INTEGER"),
            Pair(COL_TYPE, "TYPE"),
            Pair(COL_CONTENT_LIST, "TEXT"),
            Pair(COL_FAVORITE, "INTEGER"),
            Pair(COL_CREATED, "INTEGER"),
            Pair(COL_UPDATED, "INTEGER")
    )

    override fun cursorToModel(cursor: Cursor): GenreItemInfo {
        val contentList = mutableListOf<String>()
        val str = cursor.getString(cursor.getColumnIndex(COL_CONTENT_LIST))
        val list = str.split(",")
        contentList.addAll(list)
        val item = GenreItemInfo(
                id = cursor.getLong(cursor.getColumnIndex(COL_ID)),
                name = cursor.getString(cursor.getColumnIndex(COL_NAME)),
                parentId = cursor.getLong(cursor.getColumnIndex(COL_PARENT_ID)),
                type = GenreItemInfoType.strToEnum(cursor.getString(cursor.getColumnIndex(COL_TYPE)))!!,
                contentList = contentList,
                favorite = cursor.getInt(cursor.getColumnIndex(COL_FAVORITE)) == 1,
                created = DateUtil.stringToDate(cursor.getString(cursor.getColumnIndex(COL_CREATED))),
                updated = DateUtil.stringToDate(cursor.getString(cursor.getColumnIndex(COL_UPDATED)))
        )
        return item
    }
}