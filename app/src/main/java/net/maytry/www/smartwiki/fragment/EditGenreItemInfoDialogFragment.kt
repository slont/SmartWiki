package net.maytry.www.smartwiki.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.content.Context
import android.databinding.BindingAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.LinearLayout
import android.widget.ListView
import net.maytry.www.smartwiki.R
import net.maytry.www.smartwiki.databinding.*
import net.maytry.www.smartwiki.enums.GenreItemInfoType
import net.maytry.www.smartwiki.model.GenreItemInfo
import net.maytry.www.smartwiki.viewmodel.GenreItemInfoContentAdapter
import org.apache.commons.lang3.SerializationUtils

/**
 * Created by slont on 9/30/16.
 */
class EditGenreItemInfoDialogFragment : DialogFragment() {

    private lateinit var mInfo: GenreItemInfo

    private var mListener: EditGenreItemInfoDialogFragment.OnFragmentInteractionListener? = null

    private lateinit var binding: Any

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mInfo = arguments.getSerializable(EditGenreItemInfoDialogFragment.INFO) as GenreItemInfo
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

        setListener(activity)
        val inflater = LayoutInflater.from(activity)
        val dialog = inflater.inflate(GenreItemInfoType.getEditLayout(mInfo.type), null) as LinearLayout
        val builder = AlertDialog.Builder(activity)
        builder.setView(dialog)
                .setNegativeButton("cancel") { dialog, which -> }
                .setCancelable(true)
        when (mInfo.type) {
            GenreItemInfoType.TAG -> {
                binding = DialogEditGenreItemInfoMultiBinding.bind(dialog)
                val b: DialogEditGenreItemInfoMultiBinding = binding as DialogEditGenreItemInfoMultiBinding
                b.info = SerializationUtils.clone(mInfo)
                b.contentListView.emptyView = b.emptyListView
                builder.setPositiveButton("ok") { dialog, which -> mListener?.onClickUpdateInfoButton(b.info) }
            }
            else -> {
                binding = DialogEditGenreItemInfoSingleBinding.bind(dialog)
                val b: DialogEditGenreItemInfoSingleBinding = binding as DialogEditGenreItemInfoSingleBinding
                b.info = SerializationUtils.clone(mInfo)
                builder.setPositiveButton("ok") { dialog, which -> mListener?.onClickUpdateInfoButton(b.info) }
            }
        }
        return builder.create()
    }

    private fun setListener(context: Context?) {
        if (context is OnFragmentInteractionListener) {
            mListener = context as OnFragmentInteractionListener?
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    fun notifyDataSetChanged() {
        val adapter = ((binding as DialogEditGenreItemInfoMultiBinding).contentListView as ListView).adapter as GenreItemInfoContentAdapter
        adapter.reload()
        adapter.notifyDataSetChanged()
    }

    interface OnFragmentInteractionListener {
        fun onClickAddContentButton(position: Int)
        fun onClickUpdateInfoButton(info: GenreItemInfo)
    }

    companion object {
        private val INFO = "info"

        fun newInstance(info: GenreItemInfo): EditGenreItemInfoDialogFragment {
            val fragment = EditGenreItemInfoDialogFragment()
            val args = Bundle()
            args.putSerializable(INFO, info)
            fragment.arguments = args
            return fragment
        }
    }

    object CustomSetter {
        @JvmStatic
        @BindingAdapter("contentList")
        fun setContentList(listView: ListView, contentList: List<String>) {
            val adapter = GenreItemInfoContentAdapter(listView.context, contentList)
            listView.adapter = adapter
        }
    }
}