package net.maytry.www.smartwiki.viewmodel

import android.content.Context
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import net.maytry.www.smartwiki.R
import net.maytry.www.smartwiki.databinding.GenreItemListItemBinding
import net.maytry.www.smartwiki.model.GenreItem

/**
 * 一覧のアダプタ
 *
 * Created by slont on 8/16/16.
 */
class GenreItemAdapter(context: Context, textViewResourceId: Int, items: List<GenreItem>) :
        ArrayAdapter<GenreItem>(context, textViewResourceId, items) {

    val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val view: View
        val binding: GenreItemListItemBinding
        if (null == convertView) {
            binding = DataBindingUtil.inflate(inflater, R.layout.genre_item_list_item, parent, false)
            view = binding.root
            view.tag = binding
        } else {
            binding = convertView.tag as GenreItemListItemBinding
            view = convertView
        }
        binding.item = getItem(position)
        binding.onClickFavoriteButtonListener = OnClickFavoriteButtonListener(getItem(position))
        return view
    }

    /**
     *
     * @property content
     */
    class OnClickFavoriteButtonListener(content: GenreItem) : View.OnClickListener {
        val content: GenreItem = content

        override fun onClick(v: View?) {
//            genre.favorite = (v as ToggleButton).isChecked
        }
    }
}