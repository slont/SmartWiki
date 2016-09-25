package net.maytry.www.smartwiki.viewmodel

import android.content.Context
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import net.maytry.www.smartwiki.R
import net.maytry.www.smartwiki.databinding.GenreItemInfoListItemBinding
import net.maytry.www.smartwiki.model.GenreItemInfo

/**
 * 一覧のアダプタ
 *
 * Created by slont on 8/29/16.
 */
class GenreItemInfoAdapter(context: Context, textViewResourceId: Int, items: List<GenreItemInfo>) :
        ArrayAdapter<GenreItemInfo>(context, textViewResourceId, items) {

    val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val view: View
        val binding: GenreItemInfoListItemBinding
        if (null == convertView) {
            binding = DataBindingUtil.inflate(inflater, R.layout.genre_item_info_list_item, parent, false)
            view = binding.root
            view.tag = binding
        } else {
            binding = convertView.tag as GenreItemInfoListItemBinding
            view = convertView
        }
        binding.info = getItem(position)
        return view
    }
}