package net.maytry.www.smartwiki.viewmodel

import android.content.Context
import android.databinding.DataBindingUtil
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ToggleButton
import net.maytry.www.smartwiki.R
import net.maytry.www.smartwiki.databinding.GenreBinding
import net.maytry.www.smartwiki.model.Genre

/**
 * ジャンル一覧のアダプタ
 *
 * Created by slont on 8/7/16.
 */
class GenreAdapter(context: Context, items: List<Genre>, val textViewResourceId: Int = R.layout.genre) :
        ArrayAdapter<Genre>(context, textViewResourceId, items) {

    private val mInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val view: View
        val binding: GenreBinding
        if (null == convertView) {
            binding = DataBindingUtil.inflate(mInflater, textViewResourceId, parent, false)
            view = binding.root
            view.tag = binding
        } else {
            binding = convertView.tag as GenreBinding
            view = convertView
        }
        val genre = getItem(position)
        binding.genre = genre
        binding.favoriteToggle.setOnClickListener { v -> genre.favorite = (v as ToggleButton).isChecked }
        return view
    }
}