package net.maytry.www.smartwiki.model

import java.io.Serializable
import java.util.*

/**
 * Created by slont on 8/29/16.
 */
class GenreItem(

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
        var modified: Date = Date())

: Serializable {}
