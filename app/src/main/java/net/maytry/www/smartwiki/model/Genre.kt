package net.maytry.www.smartwiki.model

import java.io.Serializable
import java.util.Date

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

) : Serializable {}
