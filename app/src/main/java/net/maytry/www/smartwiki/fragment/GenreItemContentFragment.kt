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
class GenreItemContentFragment : Fragment() {

    private lateinit var mItemList: List<GenreItem>

    private var mListener: OnFragmentInteractionListener? = null

    private lateinit var binding: FragmentGenreItemContentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mItemList = arguments.getSerializable(ITEM_LIST) as List<GenreItem>
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_genre_item_content, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding = FragmentGenreItemContentBinding.bind(view)
        binding.itemList = mItemList
        binding.itemListView.setOnItemClickListener { parent, view, i, l -> mListener?.onClickItemListItem(parent, i) }
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
        ((binding.itemListView as? ListView)?.adapter as? GenreItemAdapter)?.notifyDataSetChanged()
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
        fun onClickItemListItem(parent: AdapterView<*>?, position: Int)
        fun loadData()
    }

    companion object {
        private val ITEM_LIST = "item_list"

        fun newInstance(itemList: List<GenreItem>): GenreItemContentFragment {
            val fragment = GenreItemContentFragment()
            val args = Bundle()
            args.putSerializable(ITEM_LIST, itemList as Serializable)
            fragment.arguments = args
            return fragment
        }
    }

    object CustomSetter {
        @JvmStatic
        @BindingAdapter("itemList")
        fun setItemList(listView: ListView, itemList: List<GenreItem>) {
            val adapter = GenreItemAdapter(listView.context, itemList)
            listView.adapter = adapter
        }
    }
}
