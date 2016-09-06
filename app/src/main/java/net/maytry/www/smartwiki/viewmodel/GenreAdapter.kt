package net.maytry.www.smartwiki.viewmodel

import android.content.Context
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ToggleButton
import net.maytry.www.smartwiki.R
import net.maytry.www.smartwiki.databinding.GenreListItemBinding
import net.maytry.www.smartwiki.model.Genre

/**
 * ジャンル一覧のアダプタ
 *
 * Created by slont on 8/7/16.
 */
class GenreAdapter(context: Context, textViewResourceId: Int, items: List<Genre>) :
        ArrayAdapter<Genre>(context, textViewResourceId, items) {

    val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val view: View
        val binding: GenreListItemBinding
        if (null == convertView) {
            binding = DataBindingUtil.inflate(inflater, R.layout.genre_list_item, parent, false)
            view = binding.root
            view.tag = binding
        } else {
            binding = convertView.tag as GenreListItemBinding
            view = convertView
        }
        binding.genre = getItem(position)
        binding.onClickFavoriteButtonListener = OnClickFavoriteButtonListener(getItem(position))
        return view
    }

    /**
     * change genre favorite state
     *
     * @property genre
     */
    class OnClickFavoriteButtonListener(genre: Genre) : View.OnClickListener {
        val genre: Genre = genre

        override fun onClick(v: View?) {
            genre.favorite = (v as ToggleButton).isChecked
        }
    }
}