package net.maytry.www.smartwiki.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import net.maytry.www.smartwiki.R
import net.maytry.www.smartwiki.databinding.FragmentGenreItemInfoBinding
import net.maytry.www.smartwiki.fragment.GenreItemInfoFragment.OnFragmentInteractionListener
import net.maytry.www.smartwiki.model.GenreItemInfo
import net.maytry.www.smartwiki.viewmodel.GenreItemInfoAdapter
import java.io.Serializable

/**
 * Created by slont on 9/8/16.
 *
 * A fragment representing a list of Items.
 *
 *
 * Activities containing this fragment MUST implement the [OnFragmentInteractionListener]
 * interface.
 */
class GenreItemInfoFragment : Fragment() {

    companion object {
        private const val LAYOUT_RES = R.layout.fragment_genre_item_info
        private const val INFO_LIST = "info_list"

        fun newInstance(infoList: List<GenreItemInfo>): GenreItemInfoFragment {
            return GenreItemInfoFragment().apply {
                arguments = Bundle().apply { putSerializable(INFO_LIST, infoList as Serializable) }
            }
        }
    }

    private lateinit var mInfoList: List<GenreItemInfo>

    private var mListener: OnFragmentInteractionListener? = null

    private lateinit var mBinding: FragmentGenreItemInfoBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.run { mInfoList = arguments.getSerializable(INFO_LIST) as List<GenreItemInfo> }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(LAYOUT_RES, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mBinding = FragmentGenreItemInfoBinding.bind(view).apply {
            infoList = mInfoList
            infoListView.setOnItemClickListener { parent, view, i, l -> mListener?.onClickInfo(parent, i) }
        }
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
        ((mBinding.infoListView as? ListView)?.adapter as? GenreItemInfoAdapter)?.notifyDataSetChanged()
    }

    interface OnFragmentInteractionListener {
        fun onClickInfo(parent: AdapterView<*>?, position: Int)
        fun loadData()
    }
}
