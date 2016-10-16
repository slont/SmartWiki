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
import net.maytry.www.smartwiki.databinding.DialogEditGenreItemInfoMultiBinding
import net.maytry.www.smartwiki.databinding.DialogEditGenreItemInfoSingleBinding
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

    private lateinit var mBinding: Any

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
                mBinding = DialogEditGenreItemInfoMultiBinding.bind(dialog)
                val b: DialogEditGenreItemInfoMultiBinding = mBinding as DialogEditGenreItemInfoMultiBinding
                b.info = SerializationUtils.clone(mInfo)
                b.contentListView.emptyView = b.emptyListView
                builder.setPositiveButton("ok") { dialog, which -> mListener?.onClickUpdateInfoBtn(b.info) }
            }
            else -> {
                mBinding = DialogEditGenreItemInfoSingleBinding.bind(dialog)
                val b: DialogEditGenreItemInfoSingleBinding = mBinding as DialogEditGenreItemInfoSingleBinding
                b.info = SerializationUtils.clone(mInfo)
                builder.setPositiveButton("ok") { dialog, which -> mListener?.onClickUpdateInfoBtn(b.info) }
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
        (((mBinding as DialogEditGenreItemInfoMultiBinding).contentListView as ListView).adapter as GenreItemInfoContentAdapter).safeSave()
    }

    interface OnFragmentInteractionListener {
        fun onClickAddContentBtn(position: Int)
        fun onClickUpdateInfoBtn(info: GenreItemInfo)
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
}