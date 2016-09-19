package net.maytry.www.smartwiki.model

import android.content.ContentValues
import net.maytry.www.smartwiki.db.DBAdapter
import net.maytry.www.smartwiki.db.GenreItemInfoTableAdapter
import net.maytry.www.smartwiki.enums.GenreItemInfoType
import net.maytry.www.smartwiki.utils.DateUtil
import java.util.*

/**
 * Created by slont on 8/29/16.
 */
class GenreItemInfo(

        /**
         * ID
         */
        var id: Long? = null,

        /**
         * 名前
         */
        var name: String,

        /**
         * 親GenreID
         */
        var parentID: Long,

        /**
         * 情報種別
         */
        val type: GenreItemInfoType,

        /**
         * 情報詳細一覧
         */
        val contentList: MutableList<String> = mutableListOf(),

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
        var modified: Date = Date())

: AbstractModel<GenreItemInfo>() {
    override fun contentValues(): ContentValues {
            val values = ContentValues()
            values.put(DBAdapter.COL_ID, id)
            values.put(GenreItemInfoTableAdapter.COL_NAME, name)
            values.put(GenreItemInfoTableAdapter.COL_PARENT_ID, parentID)
            values.put(GenreItemInfoTableAdapter.COL_TYPE, type.toString())
            values.put(GenreItemInfoTableAdapter.COL_FAVORITE, favorite)
            values.put(GenreItemInfoTableAdapter.COL_CREATED, DateUtil.dateToString(created))
            values.put(GenreItemInfoTableAdapter.COL_MODIFIED, DateUtil.dateToString(modified))
            return values
    }
}
