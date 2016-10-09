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
import net.maytry.www.smartwiki.R
import net.maytry.www.smartwiki.databinding.GenreItemInfoContentListItemBinding

/**
 * 一覧のアダプタ
 *
 * Created by slont on 8/29/16.
 */
class GenreItemInfoContentAdapter(context: Context, items: List<String>, val textViewResourceId: Int = R.layout.genre_item_info_content_list_item) :
        ArrayAdapter<String>(context, textViewResourceId, items) {

    private val inflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private val items: MutableList<String> = mutableListOf()

    init {
        this.items.addAll(items)
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val view: View
        val binding: GenreItemInfoContentListItemBinding
        if (null == convertView) {
            binding = DataBindingUtil.inflate(inflater, textViewResourceId, parent, false)
            view = binding.root
            view.tag = binding
            binding.contentEdit.addTextChangedListener(TextWatcherCustom(binding))
        } else {
            binding = convertView.tag as GenreItemInfoContentListItemBinding
            view = convertView
        }
        binding.position = position.toString()
        binding.content = getItem(position)
        binding.addButton.setOnClickListener { v ->
            items.add(position + 1, "")
            clear()
            addAll(items)
            notifyDataSetChanged()
        }
        android.util.Log.d(binding.contentEdit.toString(), "")
        return view
    }

    fun setValues() {
        clear()
        addAll(items)
        notifyDataSetChanged()
    }

    private inner class TextWatcherCustom(var binding: GenreItemInfoContentListItemBinding) : TextWatcher {
        var size = items.size

        override fun afterTextChanged(s: Editable) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val position = binding.position.toInt()
            if (items[position] != s.toString() && size == items.size) {
                items[position] = s.toString()
                size = items.size
            }
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
    }
}