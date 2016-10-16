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
import net.maytry.www.smartwiki.databinding.FragmentAddGenreItemBinding
import net.maytry.www.smartwiki.fragment.AddGenreItemFragment.OnFragmentInteractionListener
import net.maytry.www.smartwiki.model.GenreItem
import net.maytry.www.smartwiki.viewmodel.GenreItemInfoAdapter

/**
 * A fragment representing a list of Items.
 *
 *
 * Activities containing this fragment MUST implement the [OnFragmentInteractionListener]
 * interface.
 */
class AddGenreItemFragment : Fragment() {

    companion object {
        private const val LAYOUT_RES = R.layout.fragment_add_genre_item
        private const val ITEM = "item"

        fun newInstance(item: GenreItem): AddGenreItemFragment {
            return AddGenreItemFragment().apply {
                arguments = Bundle().apply { putSerializable(ITEM, item) }
            }
        }
    }

    private lateinit var mItem: GenreItem

    private var mListener: OnFragmentInteractionListener? = null

    private lateinit var mBinding: FragmentAddGenreItemBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.run { mItem = getSerializable(ITEM) as GenreItem }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(LAYOUT_RES, container, false)
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
        mBinding = FragmentAddGenreItemBinding.bind(view).apply {
            item = mItem
            infoListView.setOnItemClickListener { parent, view, position, id -> mListener?.onClickGenreItem(parent, position) }
        }
    }

    override fun onDetach() {
        super.onDetach()
        mListener = null
    }

    fun notifyDataSetChanged() {
        ((mBinding.infoListView as? ListView)?.adapter as? GenreItemInfoAdapter)?.notifyDataSetChanged()
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
        fun onClickGenreItem(parent: AdapterView<*>?, position: Int)
    }
}
