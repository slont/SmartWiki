package net.maytry.www.smartwiki.fragment

import android.app.AlertDialog
import android.app.Dialog
import android.app.DialogFragment
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.EditText
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

    companion object {
        private const val INFO = "info"

        fun newInstance(info: GenreItemInfo): EditGenreItemInfoDialogFragment {
            return EditGenreItemInfoDialogFragment().apply {
                arguments = Bundle().apply { putSerializable(INFO, info) }
            }
        }
    }

    private lateinit var mInfo: GenreItemInfo

    private var mListener: EditGenreItemInfoDialogFragment.OnFragmentInteractionListener? = null

    private lateinit var mBinding: Any

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.run {
            mInfo = getSerializable(EditGenreItemInfoDialogFragment.INFO) as GenreItemInfo
        }
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        super.onCreateDialog(savedInstanceState)

        setListener(activity)
        val inflater = LayoutInflater.from(activity)
        val dialog = inflater.inflate(GenreItemInfoType.getEditLayout(mInfo.type), null) as LinearLayout
        val builder = AlertDialog.Builder(activity)
                .setView(dialog)
                .setNegativeButton("cancel") { dialog, which -> }
                .setCancelable(true)
        when (mInfo.type) {
            GenreItemInfoType.TAG -> {
                mBinding = DialogEditGenreItemInfoMultiBinding.bind(dialog).apply {
                    (this as DialogEditGenreItemInfoMultiBinding).run {
                        info = SerializationUtils.clone(mInfo)
                        contentListView.emptyView = emptyListView
                        builder.setPositiveButton("ok") { dialog, which ->
                            mListener?.onClickUpdateInfoBtn(info)
                        }
                    }
                }
            }
            else -> {
                mBinding = DialogEditGenreItemInfoSingleBinding.bind(dialog).apply {
                    (this as DialogEditGenreItemInfoSingleBinding).run {
                        info = SerializationUtils.clone(mInfo)
                        builder.setPositiveButton("ok") { dialog, which ->
                            mListener?.onClickUpdateInfoBtn(info)
                        }
                    }
                }
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

    fun safeSaveForSingle() {
        mInfo.contentList[0] = ((mBinding as DialogEditGenreItemInfoSingleBinding).contentEdit as EditText).text.toString()
    }

    fun safeSaveForMulti() {
        (((mBinding as DialogEditGenreItemInfoMultiBinding).contentListView as ListView).adapter as GenreItemInfoContentAdapter).safeSave()
    }

    interface OnFragmentInteractionListener {
        fun onClickAddContentBtn(position: Int)
        fun onClickUpdateInfoBtn(info: GenreItemInfo)
    }
}