package net.maytry.www.smartwiki.viewmodel

import android.content.Context
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import net.maytry.www.smartwiki.R
import net.maytry.www.smartwiki.databinding.GenreItemBinding
import net.maytry.www.smartwiki.model.GenreItem

/**
 * 一覧のアダプタ
 *
 * Created by slont on 8/16/16.
 */
class GenreItemAdapter(context: Context, items: List<GenreItem>, val textViewResourceId: Int = R.layout.genre_item) :
        ArrayAdapter<GenreItem>(context, textViewResourceId, items) {

    private val mInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val view: View
        val binding: GenreItemBinding
        if (null == convertView) {
            binding = DataBindingUtil.inflate(mInflater, textViewResourceId, parent, false)
            view = binding.root
            view.tag = binding
        } else {
            binding = convertView.tag as GenreItemBinding
            view = convertView
        }
        binding.item = getItem(position)
        return view
    }
}