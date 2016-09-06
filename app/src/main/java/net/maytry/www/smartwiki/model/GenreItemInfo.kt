package net.maytry.www.smartwiki.model

import net.maytry.www.smartwiki.enums.GenreItemInfoType
import java.io.Serializable
import java.util.*

/**
 * Created by slont on 8/29/16.
 */
class GenreItemInfo(name: String, type: GenreItemInfoType, detailList: MutableList<String> = mutableListOf()) : Serializable {

    /**
     * 名前
     */
    var name: String

    /**
     * 情報種別
     */
    val type: GenreItemInfoType

    /**
     * 情報詳細一覧
     */
    val detailList: MutableList<String> = mutableListOf()

    init {
        this.name = name
        this.type = type
        this.detailList.addAll(detailList)
    }
}
