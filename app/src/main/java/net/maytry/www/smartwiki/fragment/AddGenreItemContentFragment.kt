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
import net.maytry.www.smartwiki.databinding.FragmentAddGenreItemContentBinding
import net.maytry.www.smartwiki.fragment.AddGenreItemContentFragment.OnFragmentInteractionListener
import net.maytry.www.smartwiki.model.GenreItem
import net.maytry.www.smartwiki.model.GenreItemInfo
import net.maytry.www.smartwiki.viewmodel.GenreItemInfoAdapter

/**
 * A fragment representing a list of Items.
 *
 *
 * Activities containing this fragment MUST implement the [OnFragmentInteractionListener]
 * interface.
 */
class AddGenreItemContentFragment : Fragment() {

    private lateinit var mItem: GenreItem

    private var mListener: OnFragmentInteractionListener? = null

    private lateinit var binding: FragmentAddGenreItemContentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mItem = arguments.getSerializable(ITEM) as GenreItem
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_add_genre_item_content, container, false)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context as OnFragmentInteractionListener?
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnListFragmentInteractionListener")
        }
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        binding = FragmentAddGenreItemContentBinding.bind(view)
        binding.item = mItem
        binding.infoListView.setOnItemClickListener { parent, view, position, id -> mListener?.onClickGenreItemListItem(parent, position) }
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
        fun onClickGenreItemListItem(parent: AdapterView<*>?, position: Int)
    }

    companion object {
        private val ITEM = "item"

        fun newInstance(item: GenreItem): AddGenreItemContentFragment {
            val fragment = AddGenreItemContentFragment()
            val args = Bundle()
            args.putSerializable(ITEM, item)
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
