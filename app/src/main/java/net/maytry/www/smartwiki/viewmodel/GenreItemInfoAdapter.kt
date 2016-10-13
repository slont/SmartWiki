package net.maytry.www.smartwiki.viewmodel

import android.content.Context
import android.databinding.BindingAdapter
import android.databinding.DataBindingUtil
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.WindowManager
import android.widget.*
import net.maytry.www.smartwiki.R
import net.maytry.www.smartwiki.databinding.*
import net.maytry.www.smartwiki.enums.GenreItemInfoType
import net.maytry.www.smartwiki.model.GenreItemInfo

/**
 * 一覧のアダプタ
 *
 * Created by slont on 8/29/16.
 */
class GenreItemInfoAdapter(context: Context, items: List<GenreItemInfo>) :
        ArrayAdapter<GenreItemInfo>(context, 0, items) {

    private val inflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    var wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    var maxDisplayWidth = wm.defaultDisplay.width
    var isEditable = false
    set(value) {
        field = value
        notifyDataSetChanged()
    }

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val view: View
        val info = getItem(position)
        val binding: Any
        val key = GenreItemInfoType.getKey(info.type)
        if (null == convertView || null == convertView.getTag(key)) {
            binding = DataBindingUtil.inflate(inflater, GenreItemInfoType.getItemLayout(info.type), parent, false)
            view = binding.root
            view.setTag(key, binding)
        } else {
            binding = convertView.getTag(key)
            view = convertView
        }
        when (info.type) {
            GenreItemInfoType.TEXT -> (binding as GenreItemInfoListItemTextBinding).info = info
            GenreItemInfoType.TAG -> {
                val b: GenreItemInfoListItemTagBinding = binding as GenreItemInfoListItemTagBinding
                b.info = info
                b.isEditable = isEditable
                b.tagLayout.removeAllViews()
                val displayWidth = maxDisplayWidth - (if (isEditable) 100 else 0)
                var totalSize = 0
                var preId = -1
                info.contentList.forEachIndexed { i, s ->
                    val tagBinding: ItemTagBinding = DataBindingUtil.inflate(inflater, R.layout.item_tag, parent, false)
                    val textView = tagBinding.root as TextView
                    textView.id = textView.id + i
                    textView.text = s
                    textView.measure(0, 0)
                    val thisSize = textView.measuredWidth + 5 * 2
                    val params = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT)
                    params.setMargins(5, 5, 5, 5)
                    if (i == 0) {
                        params.addRule(RelativeLayout.ALIGN_LEFT, RelativeLayout.TRUE)
                    } else if (displayWidth < totalSize + thisSize) {
                        // add to next line
                        totalSize = 0
                        params.addRule(RelativeLayout.BELOW, preId)
                        params.addRule(RelativeLayout.ALIGN_LEFT, RelativeLayout.TRUE)
                    } else {
                        // add to right
                        params.addRule(RelativeLayout.ALIGN_TOP, preId)
                        params.addRule(RelativeLayout.RIGHT_OF, preId)
                        params.setMargins(5, 0, 5, 0) // override
                    }
                    totalSize += thisSize
                    preId = textView.id
                    b.tagLayout.addView(textView, params)
                }
            }
            GenreItemInfoType.PHOTO -> (binding as GenreItemInfoListItemPhotoBinding).info = info
            GenreItemInfoType.MOVIE -> (binding as GenreItemInfoListItemMovieBinding).info = info
            GenreItemInfoType.TIME -> (binding as GenreItemInfoListItemTimeBinding).info = info
            GenreItemInfoType.MAP -> (binding as GenreItemInfoListItemMapBinding).info = info
            GenreItemInfoType.RADIO_BTN -> (binding as GenreItemInfoListItemRadioBtnBinding).info = info
            GenreItemInfoType.SEEK_BAR -> (binding as GenreItemInfoListItemSeekBarBinding).info = info
            GenreItemInfoType.RATING_BAR -> (binding as GenreItemInfoListItemRatingBarBinding).info = info
            GenreItemInfoType.ORIGINAL -> (binding as GenreItemInfoListItemOriginalBinding).info = info
            else -> (binding as GenreItemInfoListItemCommonBinding).info = info
        }
        return view
    }
}