package net.maytry.www.smartwiki.model

import java.io.Serializable
import java.util.Date

/**
 * Created by slont on 8/6/16.
 */
class Genre(name: String, isFavorite: Boolean) : Serializable {

    /**
     * ジャンルの名前
     */
    var name: String

    /**
     * お気に入り
     */
    var favorite: Boolean

    /**
     * アイテム情報
     */
    val itemMap: MutableMap<String, GenreItem> = mutableMapOf()

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
        this.favorite = isFavorite
        this.createdDatetime = Date()
        this.modifiedDatetime = Date()
    }

    constructor(name: String) : this(name, false)
}
