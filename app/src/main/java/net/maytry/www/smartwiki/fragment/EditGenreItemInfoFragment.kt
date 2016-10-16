package net.maytry.www.smartwiki.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import net.maytry.www.smartwiki.R
import net.maytry.www.smartwiki.databinding.FragmentEditGenreItemInfoBinding
import net.maytry.www.smartwiki.fragment.EditGenreItemInfoFragment.OnFragmentInteractionListener
import net.maytry.www.smartwiki.model.GenreItemInfo
import net.maytry.www.smartwiki.viewmodel.GenreItemInfoAdapter
import java.io.Serializable

/**
 * Created by slont on 10/15/16.
 *
 * A fragment representing a list of Items.
 *
 *
 * Activities containing this fragment MUST implement the [OnFragmentInteractionListener]
 * interface.
 */
class EditGenreItemInfoFragment : Fragment() {

    private lateinit var mInfoList: List<GenreItemInfo>

    private var mListener: OnFragmentInteractionListener? = null

    private lateinit var mBinding: FragmentEditGenreItemInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mInfoList = arguments.getSerializable(INFO_LIST) as List<GenreItemInfo>
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_edit_genre_item_info, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mBinding = FragmentEditGenreItemInfoBinding.bind(view)
        mBinding.infoListView.adapter = GenreItemInfoAdapter(mBinding.infoListView.context, mInfoList, true)
        mBinding.infoListView.setOnItemClickListener { parent, view, i, l -> mListener?.onClickInfo(parent, i) }
        mListener!!.loadData()
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
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
        (mBinding.infoListView?.adapter as? GenreItemInfoAdapter)?.notifyDataSetChanged()
    }

    interface OnFragmentInteractionListener {
        fun onClickInfo(parent: AdapterView<*>?, position: Int)
        fun loadData()
    }

    companion object {
        private val INFO_LIST = "info_list"

        fun newInstance(infoList: List<GenreItemInfo>): EditGenreItemInfoFragment {
            val fragment = EditGenreItemInfoFragment()
            val args = Bundle()
            args.putSerializable(INFO_LIST, infoList as Serializable)
            fragment.arguments = args
            return fragment
        }
    }
}
