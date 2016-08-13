package net.maytry.www.smartwiki.viewmodel

import android.content.Context
import android.databinding.BindingAdapter
import android.databinding.DataBindingUtil
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.*
import net.maytry.www.smartwiki.R
import net.maytry.www.smartwiki.databinding.GenreListItemBinding
import net.maytry.www.smartwiki.fragment.GenreListFragment
import net.maytry.www.smartwiki.model.Genre
import java.util.*

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
        binding.testListener = OnClickFavoriteButton(getItem(position))
        return view
    }

    /**
     * change genre favorite state
     *
     * @property genre
     */
    class OnClickFavoriteButton(genre: Genre) : View.OnClickListener {
        val genre: Genre = genre

        override fun onClick(v: View?) {
            genre.favorite = (v as ToggleButton).isChecked
        }
    }
}