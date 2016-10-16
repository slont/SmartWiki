package net.maytry.www.smartwiki.model

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
         * 説明
         */
        val description: String = "",

        /**
         * 画像
         */
        val image: String = "",

        /**
         * 情報一覧
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
