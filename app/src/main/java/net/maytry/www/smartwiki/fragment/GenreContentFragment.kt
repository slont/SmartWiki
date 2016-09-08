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
import net.maytry.www.smartwiki.databinding.FragmentGenreContentBinding
import net.maytry.www.smartwiki.fragment.GenreContentFragment.OnFragmentInteractionListener
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
class GenreContentFragment : Fragment() {

    private var mItemList: List<GenreItem>? = null

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mItemList = arguments.getSerializable(ITEM_LIST) as List<GenreItem>
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_genre_content, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val binding = FragmentGenreContentBinding.bind(view)
        binding.itemList = mItemList
        binding.onClickGenreItemListItem = OnClickListItem(mListener)
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
        fun onClickGenreItemListItem(parent: AdapterView<*>?, view: View?, position: Int, id: Long)

    }

    class OnClickListItem(listener: OnFragmentInteractionListener?) : AdapterView.OnItemClickListener {
        private val listener = listener

        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            listener!!.onClickGenreItemListItem(parent, view, position, id)
        }
    }

    companion object {
        private val ITEM_LIST = "item_list"

        fun newInstance(itemList: List<GenreItem>): GenreContentFragment {
            val fragment = GenreContentFragment()
            val args = Bundle()
            args.putSerializable(ITEM_LIST, itemList as Serializable)
            fragment.arguments = args
            return fragment
        }
    }

    object CustomSetter {
        @JvmStatic
        @BindingAdapter("itemList")
        fun setContentList(listView: ListView, contentList: List<GenreItem>) {
            val adapter = GenreItemAdapter(listView.context, R.layout.genre_item_list_item, contentList)
            listView.adapter = adapter
        }
    }
}
