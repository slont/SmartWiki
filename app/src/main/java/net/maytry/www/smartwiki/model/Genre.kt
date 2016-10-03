package net.maytry.www.smartwiki.model

import android.content.ContentValues
import net.maytry.www.smartwiki.db.DBAdapter
import net.maytry.www.smartwiki.db.GenreTableAdapter
import net.maytry.www.smartwiki.utils.DateUtil
import java.util.*

/**
 * Created by slont on 8/6/16.
 */
class Genre(

        /**
         * ID
         */
        id: Long? = null,

        /**
         * 名前
         */
        var name: String = "",

        /**
         * アイテム情報
         */
        val itemList: MutableList<GenreItem> = mutableListOf(),

        /**
         * お気に入り
         */
        var favorite: Boolean = false,

        /**
         * 作成日時
         */
        val created: Date = Date(),

        /**
         * 変更日時
         */
        var updated: Date = Date()

) : AbstractModel<Genre>(id) {
    override fun contentValues(): ContentValues {
        val values = ContentValues()
        values.put(DBAdapter.COL_ID, id)
        values.put(GenreTableAdapter.COL_NAME, name)
        values.put(GenreTableAdapter.COL_FAVORITE, favorite)
        values.put(GenreTableAdapter.COL_CREATED, DateUtil.dateToString(created))
        values.put(GenreTableAdapter.COL_UPDATED, DateUtil.dateToString(updated))
        return values
    }
}
