package net.maytry.www.smartwiki.db


import android.content.ContentValues
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
        const val COL_NAME = "name"
        const val COL_PARENT_ID = "parent_id"
        const val COL_DESCRIPTION = "description"
        const val COL_IMAGE = "image"
        const val COL_TYPE = "type"
        const val COL_CONTENT_LIST = "content_list"
        const val COL_FAVORITE = "favorite"
        const val COL_CREATED = "created"
        const val COL_UPDATED = "updated"
    }

    override val tableName = "genre_item_info"

    override val cols = mutableListOf(
            Pair(COL_ID, "INTEGER PRIMARY KEY AUTOINCREMENT"),
            Pair(COL_NAME, "TEXT UNIQUE"),
            Pair(COL_PARENT_ID, "INTEGER"),
            Pair(COL_DESCRIPTION, "TEXT"),
            Pair(COL_IMAGE, "TEXT"),
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
                description = cursor.getString(cursor.getColumnIndex(COL_DESCRIPTION)),
                image = cursor.getString(cursor.getColumnIndex(COL_IMAGE)),
                type = GenreItemInfoType.strToEnum(cursor.getString(cursor.getColumnIndex(COL_TYPE)))!!,
                contentList = contentList,
                favorite = cursor.getInt(cursor.getColumnIndex(COL_FAVORITE)) == 1,
                created = DateUtil.stringToDate(cursor.getString(cursor.getColumnIndex(COL_CREATED))),
                updated = DateUtil.stringToDate(cursor.getString(cursor.getColumnIndex(COL_UPDATED)))
        )
        return item
    }

    override fun contentValues(info: GenreItemInfo): ContentValues {
        val values = ContentValues()
        values.put(COL_ID, info.id)
        values.put(COL_NAME, info.name)
        values.put(COL_PARENT_ID, info.parentId)
        values.put(COL_DESCRIPTION, info.description)
        values.put(COL_IMAGE, info.image)
        values.put(COL_TYPE, info.type.toString())
        values.put(COL_CONTENT_LIST, info.contentList.reduce { s1, s2 -> "$s1,$s2" })
        values.put(COL_FAVORITE, info.favorite)
        values.put(COL_CREATED, DateUtil.dateToString(info.created))
        values.put(COL_UPDATED, DateUtil.dateToString(info.updated))
        return values
    }
}