package net.maytry.www.smartwiki.model

import android.content.ContentValues
import net.maytry.www.smartwiki.db.DBAdapter
import net.maytry.www.smartwiki.db.GenreItemTableAdapter
import net.maytry.www.smartwiki.utils.DateUtil
import java.util.*

/**
 * Created by slont on 8/29/16.
 */
class GenreItem(

        /**
         * ID
         */
        id: Long? = null,

        /**
         * 名前
         */
        var name: String = "",

        /**
         * 親GenreID
         */
        var parentId: Long = -1,

        /**
         * アイテム情報
         */
        val infoList: MutableList<GenreItemInfo> = mutableListOf(),

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
        var updated: Date = Date())

: AbstractModel(id)
