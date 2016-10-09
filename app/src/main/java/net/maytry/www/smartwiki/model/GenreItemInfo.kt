package net.maytry.www.smartwiki.model

import android.databinding.Bindable
import net.maytry.www.smartwiki.BR
import net.maytry.www.smartwiki.enums.GenreItemInfoType
import java.util.*

/**
 * Created by slont on 8/29/16.
 */
class GenreItemInfo(
        id: Long? = null,
        name: String,
        parentId: Long = -1,
        type: GenreItemInfoType,
        contentList: MutableList<String> = mutableListOf(""),
        favorite: Boolean = false,
        created: Date = Date(),
        updated: Date = Date())
: AbstractModel(id) {
    /**
     * 名前
     */
    @Bindable
    var name: String = name
    set(value) {
        field = value
        notifyPropertyChanged(BR.name)
    }

    /**
     * 親GenreID
     */
    var parentId: Long = parentId

    /**
     * 情報種別
     */
    val type: GenreItemInfoType = type

    /**
     * 情報詳細一覧
     */
    val contentList: MutableList<String> = contentList

    /**
     * お気に入り
     */
    var favorite: Boolean = favorite

    /**
     * 作成日時
     */
    val created: Date = created

    /**
     * 変更日時
     */
    var updated: Date = updated
}
