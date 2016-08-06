package net.maytry.www.smartwiki.viewmodel

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import android.widget.TextView
import android.widget.ToggleButton
import net.maytry.www.smartwiki.R
import net.maytry.www.smartwiki.model.Genre
import java.util.*

/**
 * Created by slont on 8/7/16.
 */

class GenreAdapter(context: Context, textViewResourceId: Int, items: List<Genre>) : ArrayAdapter<Genre>(context, textViewResourceId, items) {
    //    var context: Context
    var textViewResourceId: Int
    val items: List<Genre>
    var inflater: LayoutInflater

    init {
//        this.context = context
        this.textViewResourceId = textViewResourceId
        this.items = items
        this.inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    }

    constructor(context: Context, textViewResourceId: Int) : this(context, textViewResourceId, ArrayList<Genre>())

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val view: View
        if (null != convertView) {
            view = convertView
        } else {
            view = inflater.inflate(textViewResourceId, null)
        }

        val item: Genre = items[position]

        (view.findViewById(R.id.nameTextView) as TextView).text = item.name
        (view.findViewById(R.id.favoriteToggleButton) as ToggleButton).isChecked = item.favorite

        return view
    }
}