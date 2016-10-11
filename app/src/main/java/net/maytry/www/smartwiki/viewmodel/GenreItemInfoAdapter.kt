package net.maytry.www.smartwiki.viewmodel

import android.content.Context
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import net.maytry.www.smartwiki.R
import net.maytry.www.smartwiki.databinding.*
import net.maytry.www.smartwiki.enums.GenreItemInfoType
import net.maytry.www.smartwiki.model.GenreItemInfo

/**
 * 一覧のアダプタ
 *
 * Created by slont on 8/29/16.
 */
class GenreItemInfoAdapter(context: Context, items: List<GenreItemInfo>) :
        ArrayAdapter<GenreItemInfo>(context, 0, items) {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val view: View
        val info = getItem(position)
        val binding: Any
        val key = GenreItemInfoType.getKey(info.type)
        if (null == convertView || null == convertView.getTag(key)) {
            binding = DataBindingUtil.inflate(inflater, GenreItemInfoType.getItemLayout(info.type), parent, false)
            view = binding.root
            view.setTag(key, binding)
        } else {
            binding = convertView.getTag(key)
            view = convertView
        }
        when (info.type) {
            GenreItemInfoType.TEXT -> (binding as GenreItemInfoListItemTextBinding).info = info
            GenreItemInfoType.TAG -> (binding as GenreItemInfoListItemTagBinding).info = info
            GenreItemInfoType.PHOTO -> (binding as GenreItemInfoListItemPhotoBinding).info = info
            GenreItemInfoType.MOVIE -> (binding as GenreItemInfoListItemMovieBinding).info = info
            GenreItemInfoType.TIME -> (binding as GenreItemInfoListItemTimeBinding).info = info
            GenreItemInfoType.MAP -> (binding as GenreItemInfoListItemMapBinding).info = info
            GenreItemInfoType.RADIO_BTN -> (binding as GenreItemInfoListItemRadioBtnBinding).info = info
            GenreItemInfoType.SEEK_BAR -> (binding as GenreItemInfoListItemSeekBarBinding).info = info
            GenreItemInfoType.RATING_BAR -> (binding as GenreItemInfoListItemRatingBarBinding).info = info
            GenreItemInfoType.ORIGINAL -> (binding as GenreItemInfoListItemOriginalBinding).info = info
            else -> (binding as GenreItemInfoListItemCommonBinding).info = info
        }
        return view
    }
}