package net.maytry.www.smartwiki.model

import net.maytry.www.smartwiki.enums.GenreItemInfoType
import java.io.Serializable
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

: Serializable {}
