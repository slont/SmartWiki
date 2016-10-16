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
        with(cursor) {
            return Genre(
                    id = getLong(getColumnIndex(COL_ID)),
                    name = getString(getColumnIndex(COL_NAME)),
                    description = getString(getColumnIndex(COL_DESCRIPTION)),
                    image = getString(getColumnIndex(COL_IMAGE)),
                    favorite = getInt(getColumnIndex(COL_FAVORITE)) == 1,
                    createdUser = getString(getColumnIndex(COL_CREATED_USER)),
                    created = DateUtil.stringToDate(getString(getColumnIndex(COL_CREATED))),
                    updated = DateUtil.stringToDate(getString(getColumnIndex(COL_UPDATED)))
            )
        }
    }

    override fun contentValues(genre: Genre): ContentValues {
        return ContentValues().apply {
            put(COL_ID, genre.id)
            put(COL_NAME, genre.name)
            put(COL_DESCRIPTION, genre.description)
            put(COL_IMAGE, genre.image)
            put(COL_FAVORITE, genre.favorite)
            put(COL_CREATED_USER, genre.createdUser)
            put(COL_CREATED, DateUtil.dateToString(genre.created))
            put(COL_UPDATED, DateUtil.dateToString(genre.updated))
        }
    }
}