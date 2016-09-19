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
import net.maytry.www.smartwiki.databinding.FragmentEditGenreItemContentBinding
import net.maytry.www.smartwiki.enums.EditType
import net.maytry.www.smartwiki.fragment.EditGenreItemContentFragment.OnFragmentInteractionListener
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
class EditGenreItemContentFragment : Fragment() {

    private var mItem: GenreItem? = null
    private var mType: EditType? = null

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mItem = arguments.getSerializable(ITEM) as GenreItem
            mType = arguments.getSerializable(TYPE) as EditType
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_edit_genre_item_content, container, false)
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
        val binding = FragmentEditGenreItemContentBinding.bind(view)
        binding.item = mItem
        binding.submitButtonName = if (EditType.UPDATE == mType) "更新" else "作成"
        binding.onClickGenreItemListItem = OnClickGenreItemListItem(mListener)
        binding.onClickCancelButton = OnClickCancelButton(mListener)
        binding.onClickSubmitButton = OnClickSubmitButton(mListener, mType)
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
        fun onClickCancelButton(v: View)
        fun onClickCreateButton(v: View)
        fun onClickUpdateButton(v: View)
    }

    private class OnClickGenreItemListItem(private val listener: OnFragmentInteractionListener?) : AdapterView.OnItemClickListener {
        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            listener!!.onClickGenreItemListItem(parent, view, position, id)
        }
    }

    private class OnClickCancelButton(private val listener: OnFragmentInteractionListener?) : View.OnClickListener {
        override fun onClick(v: View) {
            listener!!.onClickCancelButton(v)
        }
    }

    private class OnClickSubmitButton(private val listener: OnFragmentInteractionListener?, val type: EditType?) : View.OnClickListener {
        override fun onClick(v: View) {
            when (type) {
                EditType.UPDATE -> listener!!.onClickUpdateButton(v)
                EditType.CREATE -> listener!!.onClickCreateButton(v)
            }
        }
    }

    companion object {

        private val ITEM = "item"
        private val TYPE = "type"

        fun newInstance(item: GenreItem, type: EditType): EditGenreItemContentFragment {
            val fragment = EditGenreItemContentFragment()
            val args = Bundle()
            args.putSerializable(ITEM, item)
            args.putSerializable(TYPE, type)
            fragment.arguments = args
            return fragment
        }
    }

    object CustomSetter {
        @JvmStatic
        @BindingAdapter("infoList")
        fun setContentList(listView: ListView, infoList: List<GenreItemInfo>) {
            val adapter = GenreItemInfoAdapter(listView.context, R.layout.genre_item_info_list_item, infoList)
            listView.adapter = adapter
        }
    }
}
