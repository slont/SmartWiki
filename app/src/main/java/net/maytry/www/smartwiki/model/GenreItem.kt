package net.maytry.www.smartwiki.model

import java.io.Serializable
import java.util.*

/**
 * Created by slont on 8/29/16.
 */
class GenreItem(name: String, infoList: MutableList<GenreItemInfo> = mutableListOf()) : Serializable {

    /**
     * 名前
     */
    var name: String

    /**
     * アイテム情報
     */
    val infoList: MutableList<GenreItemInfo> = mutableListOf()

    /**
     * お気に入り
     */
    var favorite: Boolean? = null

    /**
     * 作成日時
     */
    val createdDatetime: Date

    /**
     * 変更日時
     */
    var modifiedDatetime: Date

    init {
        this.name = name
        this.infoList.addAll(infoList)
//        this.favorite = isFavorite

        this.createdDatetime = Date()
        this.modifiedDatetime = Date()
    }
}
