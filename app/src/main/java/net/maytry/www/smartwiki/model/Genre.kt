package net.maytry.www.smartwiki.model

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
         * 説明
         */
        val description: String = "",

        /**
         * 画像
         */
        val image: String = "",

        /**
         * アイテム一覧
         */
        val itemList: MutableList<GenreItem> = mutableListOf(),

        /**
         * お気に入り
         */
        var favorite: Boolean = false,

        /**
         * 作成者
         */
        val createdUser: String = "",

        /**
         * 作成日時
         */
        val created: Date = Date(),

        /**
         * 変更日時
         */
        var updated: Date = Date()

) : AbstractModel(id)
