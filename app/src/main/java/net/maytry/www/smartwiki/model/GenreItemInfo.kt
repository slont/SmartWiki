package net.maytry.www.smartwiki.model

import android.content.ContentValues
import android.databinding.Bindable
import net.maytry.www.smartwiki.BR
import net.maytry.www.smartwiki.db.DBAdapter
import net.maytry.www.smartwiki.db.GenreItemInfoTableAdapter
import net.maytry.www.smartwiki.enums.GenreItemInfoType
import net.maytry.www.smartwiki.utils.DateUtil
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
: AbstractModel<GenreItemInfo>(id) {
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

    override fun contentValues(): ContentValues {
        val values = ContentValues()
        values.put(DBAdapter.COL_ID, id)
        values.put(GenreItemInfoTableAdapter.COL_NAME, name)
        values.put(GenreItemInfoTableAdapter.COL_PARENT_ID, parentId)
        values.put(GenreItemInfoTableAdapter.COL_TYPE, type.toString())
        values.put(GenreItemInfoTableAdapter.COL_CONTENT_LIST, contentList.reduce { s1, s2 -> "$s1,$s2" })
        values.put(GenreItemInfoTableAdapter.COL_FAVORITE, favorite)
        values.put(GenreItemInfoTableAdapter.COL_CREATED, DateUtil.dateToString(created))
        values.put(GenreItemInfoTableAdapter.COL_UPDATED, DateUtil.dateToString(updated))
        return values
    }
}
