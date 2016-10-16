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
class GenreItemInfoAdapter(context: Context, items: List<GenreItemInfo>, var isEditable: Boolean = false) :
        ArrayAdapter<GenreItemInfo>(context, 0, items) {

    object CustomSetter {
        @JvmStatic
        @BindingAdapter("infoList")
        fun setInfoList(listView: ListView, infoList: List<GenreItemInfo>) {
            listView.adapter = GenreItemInfoAdapter(listView.context, infoList)
        }
    }

    private val mInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    var wm = context.getSystemService(Context.WINDOW_SERVICE) as WindowManager
    var maxDisplayWidth = wm.defaultDisplay.width

    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View? {
        val view: View
        val info = getItem(position)
        val binding: Any
        val key = GenreItemInfoType.getKey(info.type)
        if (null == convertView || null == convertView.getTag(key)) {
            binding = DataBindingUtil.inflate(mInflater, GenreItemInfoType.getItemLayout(info.type), parent, false)
            view = binding.root
            view.setTag(key, binding)
        } else {
            binding = convertView.getTag(key)
            view = convertView
        }
        when (info.type) {
            GenreItemInfoType.TEXT -> {
                (binding as GenreItemInfoTextBinding).run {
                    this.info = info
                    isEditable = this@GenreItemInfoAdapter.isEditable
                    deleteBtn.setOnClickListener {
                        // TODO
                    }
                }
            }

            GenreItemInfoType.TAG -> {
                (binding as GenreItemInfoTagBinding).run {
                    this.info = info
                    isEditable = this@GenreItemInfoAdapter.isEditable
                    deleteBtn.setOnClickListener {
                        // TODO
                    }
                    tagLayout.removeAllViews()
                    val displayWidth = maxDisplayWidth - (if (this@GenreItemInfoAdapter.isEditable) 100 else 0)
                    var totalSize = 0
                    var preId = -1
                    info.contentList.forEachIndexed { i, s ->
                        val tagBinding: ItemTagBinding = DataBindingUtil.inflate(mInflater, R.layout.item_tag, parent, false)
                        val textView = (tagBinding.root as TextView).apply {
                            id += i
                            text = s
                            measure(0, 0)
                        }
                        val thisSize = textView.measuredWidth + 5 * 2
                        val params = RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT).apply {
                            setMargins(5, 5, 5, 5)
                            if (i == 0) {
                                addRule(RelativeLayout.ALIGN_LEFT, RelativeLayout.TRUE)
                            } else if (displayWidth < totalSize + thisSize) {
                                // add to next line
                                totalSize = 0
                                addRule(RelativeLayout.BELOW, preId)
                                addRule(RelativeLayout.ALIGN_LEFT, RelativeLayout.TRUE)
                            } else {
                                // add to right
                                addRule(RelativeLayout.ALIGN_TOP, preId)
                                addRule(RelativeLayout.RIGHT_OF, preId)
                                setMargins(5, 0, 5, 0) // override
                            }
                        }
                        totalSize += thisSize
                        preId = textView.id
                        tagLayout.addView(textView, params)
                    }
                }
            }

            GenreItemInfoType.PHOTO -> {
                (binding as GenreItemInfoPhotoBinding).run {
                    this.info = info
                    isEditable = this@GenreItemInfoAdapter.isEditable
                    deleteBtn.setOnClickListener {
                        // TODO
                    }
                }
            }

            GenreItemInfoType.MOVIE -> {
                (binding as GenreItemInfoMovieBinding).run {
                    this.info = info
                    isEditable = this@GenreItemInfoAdapter.isEditable
                    deleteBtn.setOnClickListener {
                        // TODO
                    }
                }
            }

            GenreItemInfoType.TIME -> {
                (binding as GenreItemInfoTimeBinding).run {
                    this.info = info
                    isEditable = this@GenreItemInfoAdapter.isEditable
                    deleteBtn.setOnClickListener {
                        // TODO
                    }
                }
            }

            GenreItemInfoType.MAP -> {
                (binding as GenreItemInfoMapBinding).run {
                    this.info = info
                    isEditable = this@GenreItemInfoAdapter.isEditable
                    deleteBtn.setOnClickListener {
                        // TODO
                    }
                }
            }

            GenreItemInfoType.RADIO_BTN -> {
                (binding as GenreItemInfoRadioBtnBinding).run {
                    this.info = info
                    isEditable = this@GenreItemInfoAdapter.isEditable
                    deleteBtn.setOnClickListener {
                        // TODO
                    }
                }
            }

            GenreItemInfoType.SEEK_BAR -> {
                (binding as GenreItemInfoSeekBarBinding).run {
                    this.info = info
                    isEditable = this@GenreItemInfoAdapter.isEditable
                    deleteBtn.setOnClickListener {
                        // TODO
                    }
                }
            }

            GenreItemInfoType.RATING_BAR -> {
                (binding as GenreItemInfoRatingBarBinding).run {
                    this.info = info
                    isEditable = this@GenreItemInfoAdapter.isEditable
                    deleteBtn.setOnClickListener {
                        // TODO
                    }
                }
            }

            GenreItemInfoType.ORIGINAL -> {
                (binding as GenreItemInfoOriginalBinding).run {
                    this.info = info
                    isEditable = this@GenreItemInfoAdapter.isEditable
                    deleteBtn.setOnClickListener {
                        // TODO
                    }
                }
            }

            else -> {
                (binding as GenreItemInfoCommonBinding).run {
                    this.info = info
                    isEditable = this@GenreItemInfoAdapter.isEditable
                    deleteBtn.setOnClickListener {
                        // TODO
                    }
                }
            }
        }
        return view
    }
}