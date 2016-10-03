package net.maytry.www.smartwiki.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.content.Context
import android.databinding.BindingAdapter
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ArrayAdapter
import android.widget.LinearLayout
import android.widget.ListView
import net.maytry.www.smartwiki.R
import net.maytry.www.smartwiki.databinding.DialogEditGenreItemInfoBinding
import net.maytry.www.smartwiki.model.GenreItemInfo
import net.maytry.www.smartwiki.viewmodel.GenreItemInfoContentAdapter
import org.apache.commons.lang3.SerializationUtils

/**
 * Created by slont on 9/30/16.
 */
class EditGenreItemInfoDialogFragment : DialogFragment() {

    private lateinit var mInfo: GenreItemInfo

    private var mListener: EditGenreItemInfoDialogFragment.OnFragmentInteractionListener? = null

    private lateinit var binding: DialogEditGenreItemInfoBinding

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
        val dialog = inflater.inflate(R.layout.dialog_edit_genre_item_info, null) as LinearLayout
        binding = DialogEditGenreItemInfoBinding.bind(dialog)
        binding.info = SerializationUtils.clone(mInfo)
        binding.contentListView.emptyView = binding.emptyListView
        val builder = AlertDialog.Builder(activity)
        return builder.setView(dialog)
                .setPositiveButton("ok") { dialog, which -> mListener?.onClickUpdateInfoButton(binding.info) }
                .setNegativeButton("cancel") { dialog, which -> }
                .setCancelable(true)
                .create()
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
        ((binding.contentListView as? ListView)?.adapter as? GenreItemInfoContentAdapter)?.notifyDataSetChanged()
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