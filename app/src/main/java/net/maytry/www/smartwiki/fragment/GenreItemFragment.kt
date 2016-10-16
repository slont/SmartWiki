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
import net.maytry.www.smartwiki.databinding.FragmentGenreItemBinding
import net.maytry.www.smartwiki.fragment.GenreItemFragment.OnFragmentInteractionListener
import net.maytry.www.smartwiki.model.GenreItem
import net.maytry.www.smartwiki.viewmodel.GenreItemAdapter
import java.io.Serializable

/**
 * A fragment representing a list of Items.
 *
 *
 * Activities containing this fragment MUST implement the [OnFragmentInteractionListener]
 * interface.
 */
class GenreItemFragment : Fragment() {

    companion object {
        private const val LAYOUT_RES = R.layout.fragment_genre_item
        private const val ITEM_LIST = "item_list"

        fun newInstance(itemList: List<GenreItem>): GenreItemFragment {
            return GenreItemFragment().apply {
                arguments = Bundle().apply { putSerializable(ITEM_LIST, itemList as Serializable) }
            }
        }
    }

    private lateinit var mItemList: List<GenreItem>

    private var mListener: OnFragmentInteractionListener? = null

    private lateinit var mBinding: FragmentGenreItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mItemList = arguments.getSerializable(ITEM_LIST) as List<GenreItem>
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(LAYOUT_RES, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mBinding = FragmentGenreItemBinding.bind(view).apply {
            itemList = mItemList
            itemListView.setOnItemClickListener { parent, view, i, l -> mListener?.onClickItem(parent, i) }
        }
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
        ((mBinding.itemListView as? ListView)?.adapter as? GenreItemAdapter)?.notifyDataSetChanged()
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
        fun onClickItem(parent: AdapterView<*>?, position: Int)
        fun loadData()
    }
}
