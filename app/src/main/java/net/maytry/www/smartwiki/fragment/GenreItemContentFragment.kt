package net.maytry.www.smartwiki.fragment

import android.content.Context
import android.databinding.BindingAdapter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import net.maytry.www.smartwiki.R
import net.maytry.www.smartwiki.databinding.FragmentGenreItemContentBinding
import net.maytry.www.smartwiki.fragment.GenreItemContentFragment.OnFragmentInteractionListener
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
class GenreItemContentFragment : Fragment() {

    private lateinit var mInfoList: List<GenreItemInfo>

    private var mListener: OnFragmentInteractionListener? = null

    private lateinit var binding: FragmentGenreItemContentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mInfoList = arguments.getSerializable(INFO_LIST) as List<GenreItemInfo>
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_genre_item_content, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding = FragmentGenreItemContentBinding.bind(view)
        binding.infoList = mInfoList
        binding.infoListView.setOnItemClickListener { parent, view, i, l -> mListener?.onClickInfoListItem(parent, i) }
        mListener?.loadData()
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
        ((binding.infoListView as? ListView)?.adapter as? GenreItemInfoAdapter)?.notifyDataSetChanged()
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     *
     *
     * See the Android Training lesson [Communicating with Other Fragments](http://developer.android.com/training/basics/fragments/communicating.html) for more information.
     */
    interface OnFragmentInteractionListener {
        fun onClickInfoListItem(parent: AdapterView<*>?, position: Int)
        fun loadData()
    }

    companion object {
        private val INFO_LIST = "info_list"

        fun newInstance(infoList: List<GenreItemInfo>): GenreItemContentFragment {
            val fragment = GenreItemContentFragment()
            val args = Bundle()
            args.putSerializable(INFO_LIST, infoList as Serializable)
            fragment.arguments = args
            return fragment
        }
    }

    object CustomSetter {
        @JvmStatic
        @BindingAdapter("infoList")
        fun setInfoList(listView: ListView, infoList: List<GenreItemInfo>) {
            val adapter = GenreItemInfoAdapter(listView.context, infoList)
            listView.adapter = adapter
        }
    }
}
