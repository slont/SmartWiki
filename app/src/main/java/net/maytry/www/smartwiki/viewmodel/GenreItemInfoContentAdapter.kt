package net.maytry.www.smartwiki.viewmodel

import android.content.Context
import android.databinding.DataBindingUtil
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.EditText
import net.maytry.www.smartwiki.R
import net.maytry.www.smartwiki.databinding.GenreItemInfoContentListItemBinding

/**
 * 一覧のアダプタ
 *
 * Created by slont on 8/29/16.
 */
class GenreItemInfoContentAdapter(context: Context, items: List<String>) : ArrayAdapter<String>(context, 0, items) {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private final val items: MutableList<String> = mutableListOf<String>()

    init {
        this.items.addAll(items)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val view: View
        val binding: GenreItemInfoContentListItemBinding
        if (null == convertView) {
            binding = DataBindingUtil.inflate(inflater, R.layout.genre_item_info_content_list_item, parent, false)
            view = binding.root
            view.tag = binding
        } else {
            binding = convertView.tag as GenreItemInfoContentListItemBinding
            view = convertView
        }
        binding.content = getItem(position)
        val et: EditText = binding.contentEdit
        et.addTextChangedListener(TextWatcherCustom(position))
        return view
    }

    internal inner class TextWatcherCustom(val position: Int) : TextWatcher {

        override fun afterTextChanged(s: Editable) {
            if (items[position].equals(s.toString()))
                return
            items[position] = s.toString()
            this@GenreItemInfoContentAdapter.clear()
            this@GenreItemInfoContentAdapter.addAll(items)
            this@GenreItemInfoContentAdapter.notifyDataSetChanged()
        }

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
//            this.mNotifyEditText.setText(s)
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {
        }
    }
}