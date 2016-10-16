package net.maytry.www.smartwiki.db


import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import net.maytry.www.smartwiki.model.Genre
import net.maytry.www.smartwiki.utils.DateUtil

/**
 * Created by slont on 9/15/16.
 */
class GenreTableAdapter(context: Context) : DBAdapter<Genre>(context) {

    companion object {
        const val COL_NAME = "name"
        const val COL_DESCRIPTION = "description"
        const val COL_IMAGE = "image"
        const val COL_FAVORITE = "favorite"
        const val COL_CREATED_USER = "created_user"
        const val COL_CREATED = "created"
        const val COL_UPDATED = "updated"
    }

    override val tableName = "genre"

    override val cols = mutableListOf(
            Pair(COL_ID, "INTEGER PRIMARY KEY AUTOINCREMENT"),
            Pair(COL_NAME, "TEXT"),
            Pair(COL_DESCRIPTION, "TEXT"),
            Pair(COL_IMAGE, "TEXT"),
            Pair(COL_FAVORITE, "INTEGER"),
            Pair(COL_CREATED_USER, "TEXT"),
            Pair(COL_CREATED, "INTEGER"),
            Pair(COL_UPDATED, "INTEGER")
    )

    override fun cursorToModel(cursor: Cursor): Genre {
        val genre = Genre(
                id = cursor.getLong(cursor.getColumnIndex(COL_ID)),
                name = cursor.getString(cursor.getColumnIndex(COL_NAME)),
                description = cursor.getString(cursor.getColumnIndex(COL_DESCRIPTION)),
                image = cursor.getString(cursor.getColumnIndex(COL_IMAGE)),
                favorite = cursor.getInt(cursor.getColumnIndex(COL_FAVORITE)) == 1,
                createdUser = cursor.getString(cursor.getColumnIndex(COL_CREATED_USER)),
                created = DateUtil.stringToDate(cursor.getString(cursor.getColumnIndex(COL_CREATED))),
                updated = DateUtil.stringToDate(cursor.getString(cursor.getColumnIndex(COL_UPDATED)))
        )
        return genre
    }

    override fun contentValues(genre: Genre): ContentValues {
        val values = ContentValues()
        values.put(COL_ID, genre.id)
        values.put(COL_NAME, genre.name)
        values.put(COL_DESCRIPTION, genre.description)
        values.put(COL_IMAGE, genre.image)
        values.put(COL_FAVORITE, genre.favorite)
        values.put(COL_CREATED_USER, genre.createdUser)
        values.put(COL_CREATED, DateUtil.dateToString(genre.created))
        values.put(COL_UPDATED, DateUtil.dateToString(genre.updated))
        return values
    }
}