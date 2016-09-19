package net.maytry.www.smartwiki.model

import android.content.ContentValues
import net.maytry.www.smartwiki.utils.DateUtil
import java.io.Serializable
import java.util.*

/**
 * Created by slont on 8/6/16.
 */
class Genre(

        /**
         * ID
         */
        var id: Long? = null,

        /**
         * 名前
         */
        var name: String = "",

        /**
         * アイテム情報
         */
        val itemMap: MutableMap<String, GenreItem> = mutableMapOf(),

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
        var modified: Date = Date()

) : Serializable {
    fun contentValues(): ContentValues {
        val values = ContentValues()
        values.put("id", id)
        values.put("name", name)
        values.put("favorite", favorite)
        values.put("created", DateUtil.dateToString(created))
        values.put("modified", DateUtil.dateToString(modified))
        return values
    }
}
