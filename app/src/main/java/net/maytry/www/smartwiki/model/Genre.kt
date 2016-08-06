package net.maytry.www.smartwiki.model

import java.util.Date

/**
 * Created by slont on 8/6/16.
 */
class Genre(genreName: String, isFavorite: Boolean) {
    /**
     * Index
     */
    var index: Long = -1L

    /**
     * ジャンルの名前
     */
    var name: String

    /**
     * お気に入りかどうか
     */
    var favorite: Boolean
        private set

    /**
     * 作成日時
     */
    val createdDatetime: Date

    /**
     * 変更日時
     */
    var modifiedDatetime: Date

    init {
        this.name = genreName
        this.favorite = isFavorite
        this.createdDatetime = Date()
        this.modifiedDatetime = Date()
    }

    constructor(genreName: String) : this(genreName, false)
}
