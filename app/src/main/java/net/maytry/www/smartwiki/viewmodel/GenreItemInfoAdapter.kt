package net.maytry.www.smartwiki.viewmodel

import android.content.Context
import android.databinding.BindingAdapter
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import net.maytry.www.smartwiki.R
import net.maytry.www.smartwiki.databinding.GenreItemInfoListItemCommonBinding
import net.maytry.www.smartwiki.databinding.GenreItemInfoListItemTimeBinding
import net.maytry.www.smartwiki.enums.GenreItemInfoType
import net.maytry.www.smartwiki.model.GenreItemInfo

/**
 * 一覧のアダプタ
 *
 * Created by slont on 8/29/16.
 */
class GenreItemInfoAdapter(context: Context, items: List<GenreItemInfo>) : ArrayAdapter<GenreItemInfo>(context, 0, items) {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val view: View
        val info = getItem(position)
        val binding: Any
        if (null == convertView || null == convertView.getTag(getKey(info))) {
            binding = DataBindingUtil.inflate(inflater, getLayout(info), parent, false)
            view = binding.root
            view.setTag(getKey(info), binding)
        } else {
            binding = convertView.getTag(getKey(info))
            view = convertView
        }
        when (info.type) {
            GenreItemInfoType.PHOTO -> (binding as GenreItemInfoListItemCommonBinding).info = info
            GenreItemInfoType.MOVIE -> (binding as GenreItemInfoListItemCommonBinding).info = info
            GenreItemInfoType.TIME -> (binding as GenreItemInfoListItemTimeBinding).info = info
            GenreItemInfoType.MAP -> (binding as GenreItemInfoListItemCommonBinding).info = info
            GenreItemInfoType.RADIO_BTN -> (binding as GenreItemInfoListItemCommonBinding).info = info
            GenreItemInfoType.SEEK_BAR -> (binding as GenreItemInfoListItemCommonBinding).info = info
            GenreItemInfoType.RATING_BAR -> (binding as GenreItemInfoListItemCommonBinding).info = info
            GenreItemInfoType.ORIGINAL -> (binding as GenreItemInfoListItemCommonBinding).info = info
            else -> (binding as GenreItemInfoListItemCommonBinding).info = info
        }
        return view
    }

    private fun getKey(info: GenreItemInfo): Int {
        when (info.type) {
            GenreItemInfoType.PHOTO -> return R.string.genre_item_info_type_photo
            GenreItemInfoType.MOVIE -> return R.string.genre_item_info_type_movie
            GenreItemInfoType.TIME -> return R.string.genre_item_info_type_time
            GenreItemInfoType.MAP -> return R.string.genre_item_info_type_map
            GenreItemInfoType.RADIO_BTN -> return R.string.genre_item_info_type_radio_btn
            GenreItemInfoType.SEEK_BAR -> return R.string.genre_item_info_type_seek_bar
            GenreItemInfoType.RATING_BAR -> return R.string.genre_item_info_type_rating_bar
            GenreItemInfoType.ORIGINAL -> return R.string.genre_item_info_type_original
            else -> return R.string.genre_item_info_type_photo
        }
    }

    private fun getLayout(info: GenreItemInfo): Int {
        when (info.type) {
            GenreItemInfoType.PHOTO -> return R.layout.genre_item_info_list_item_common
            GenreItemInfoType.MOVIE -> return R.layout.genre_item_info_list_item_common
            GenreItemInfoType.TIME -> return R.layout.genre_item_info_list_item_time
            GenreItemInfoType.MAP -> return R.layout.genre_item_info_list_item_common
            GenreItemInfoType.RADIO_BTN -> return R.layout.genre_item_info_list_item_common
            GenreItemInfoType.SEEK_BAR -> return R.layout.genre_item_info_list_item_common
            GenreItemInfoType.RATING_BAR -> return R.layout.genre_item_info_list_item_common
            GenreItemInfoType.ORIGINAL -> return R.layout.genre_item_info_list_item_common
            else -> return R.layout.genre_item_info_list_item_common
        }
    }
}