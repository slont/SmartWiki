package net.maytry.www.smartwiki.viewmodel

import android.content.Context
import android.databinding.BindingAdapter
import android.databinding.DataBindingUtil
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.ListView
import net.maytry.www.smartwiki.R
import net.maytry.www.smartwiki.databinding.GenreItemInfoContentBinding

/**
 * 一覧のアダプタ
 *
 * Created by slont on 8/29/16.
 */
class GenreItemInfoContentAdapter(context: Context, items: List<String>, val textViewResourceId: Int = R.layout.genre_item_info_content) :
        ArrayAdapter<String>(context, textViewResourceId, items) {

    object CustomSetter {
        @JvmStatic
        @BindingAdapter("contentList")
        fun setContentList(listView: ListView, contentList: List<String>) {
            listView.adapter = GenreItemInfoContentAdapter(listView.context, contentList)
        }
    }

    private val mInflater: LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater

    private val mItems = mutableListOf<String>().apply { addAll(items) }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val view: View
        val binding: GenreItemInfoContentBinding
        if (null == convertView) {
            binding = DataBindingUtil.inflate(mInflater, textViewResourceId, parent, false)
            view = binding.root
            view.tag = binding
            binding.contentEdit.addTextChangedListener(TextWatcherCustom(binding))
        } else {
            binding = convertView.tag as GenreItemInfoContentBinding
            view = convertView
        }
        binding.run {
            this.position = position.toString()
            content = getItem(position)
            addBtn.setOnClickListener { v ->
                mItems.add(position + 1, "")
                reload()
            }
            deleteBtn.setOnClickListener { v ->
                mItems.removeAt(position)
                reload()
            }
        }
        return view
    }
    fun reload() {
        clear()
        addAll(mItems)
        notifyDataSetChanged()
    }

    /**
     * 画面遷移時に呼び出す
     */
    fun safeSave() {
        clear()
        addAll(mItems.filter(String::isNotEmpty))
        notifyDataSetChanged()
    }

    private inner class TextWatcherCustom(var binding: GenreItemInfoContentBinding) : TextWatcher {
        var size = mItems.size

        override fun afterTextChanged(s: Editable) {}

        override fun onTextChanged(s: CharSequence, start: Int, before: Int, count: Int) {
            val position = binding.position.toInt()
            if (mItems[position] != s.toString() && size == mItems.size) {
                mItems[position] = s.toString()
                size = mItems.size
            }
        }

        override fun beforeTextChanged(s: CharSequence, start: Int, count: Int, after: Int) {}
    }
}