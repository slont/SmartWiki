package net.maytry.www.smartwiki.db


import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import net.maytry.www.smartwiki.model.GenreItem
import net.maytry.www.smartwiki.utils.DateUtil

/**
 * Created by slont on 9/15/16.
 */
class GenreItemTableAdapter(context: Context) : DBAdapter<GenreItem>(context) {

    companion object {
        const val COL_NAME = "name"
        const val COL_PARENT_ID = "parent_id"
        const val COL_DESCRIPTION = "description"
        const val COL_IMAGE = "image"
        const val COL_FAVORITE = "favorite"
        const val COL_CREATED = "created"
        const val COL_UPDATED = "updated"
    }

    override val tableName = "genre_item"

    override val cols = mutableListOf(
            Pair(COL_ID, "INTEGER PRIMARY KEY AUTOINCREMENT"),
            Pair(COL_NAME, "TEXT"),
            Pair(COL_PARENT_ID, "INTEGER"),
            Pair(COL_DESCRIPTION, "TEXT"),
            Pair(COL_IMAGE, "TEXT"),
            Pair(COL_FAVORITE, "INTEGER"),
            Pair(COL_CREATED, "INTEGER"),
            Pair(COL_UPDATED, "INTEGER")
    )

    override fun cursorToModel(cursor: Cursor): GenreItem {
        val item = GenreItem(
                id = cursor.getLong(cursor.getColumnIndex(COL_ID)),
                name = cursor.getString(cursor.getColumnIndex(COL_NAME)),
                parentId = cursor.getLong(cursor.getColumnIndex(COL_PARENT_ID)),
                description = cursor.getString(cursor.getColumnIndex(COL_DESCRIPTION)),
                image = cursor.getString(cursor.getColumnIndex(COL_IMAGE)),
                favorite = cursor.getInt(cursor.getColumnIndex(COL_FAVORITE)) == 1,
                created = DateUtil.stringToDate(cursor.getString(cursor.getColumnIndex(COL_CREATED))),
                updated = DateUtil.stringToDate(cursor.getString(cursor.getColumnIndex(COL_UPDATED)))
        )
        return item
    }

    override fun contentValues(item: GenreItem): ContentValues {
        val values = ContentValues()
        values.put(COL_ID, item.id)
        values.put(COL_NAME, item.name)
        values.put(COL_PARENT_ID, item.parentId)
        values.put(COL_DESCRIPTION, item.description)
        values.put(COL_IMAGE, item.image)
        values.put(COL_FAVORITE, item.favorite)
        values.put(COL_CREATED, DateUtil.dateToString(item.created))
        values.put(COL_UPDATED, DateUtil.dateToString(item.updated))
        return values
    }
}