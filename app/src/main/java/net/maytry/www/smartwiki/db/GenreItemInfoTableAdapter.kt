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
        with(cursor) {
            val contentList = mutableListOf<String>().apply {
                val str = getString(getColumnIndex(COL_CONTENT_LIST))
                addAll(str.split(","))
            }
            return GenreItemInfo(
                    id = getLong(getColumnIndex(COL_ID)),
                    name = getString(getColumnIndex(COL_NAME)),
                    parentId = getLong(getColumnIndex(COL_PARENT_ID)),
                    description = getString(getColumnIndex(COL_DESCRIPTION)),
                    image = getString(getColumnIndex(COL_IMAGE)),
                    type = GenreItemInfoType.strToEnum(getString(getColumnIndex(COL_TYPE)))!!,
                    contentList = contentList,
                    favorite = getInt(getColumnIndex(COL_FAVORITE)) == 1,
                    created = DateUtil.stringToDate(getString(getColumnIndex(COL_CREATED))),
                    updated = DateUtil.stringToDate(getString(getColumnIndex(COL_UPDATED)))
            )
        }
    }

    override fun contentValues(info: GenreItemInfo): ContentValues {
        return ContentValues().apply {
            put(COL_ID, info.id)
            put(COL_NAME, info.name)
            put(COL_PARENT_ID, info.parentId)
            put(COL_DESCRIPTION, info.description)
            put(COL_IMAGE, info.image)
            put(COL_TYPE, info.type.toString())
            put(COL_CONTENT_LIST, info.contentList.reduce { s1, s2 -> "$s1,$s2" })
            put(COL_FAVORITE, info.favorite)
            put(COL_CREATED, DateUtil.dateToString(info.created))
            put(COL_UPDATED, DateUtil.dateToString(info.updated))
        }
    }
}