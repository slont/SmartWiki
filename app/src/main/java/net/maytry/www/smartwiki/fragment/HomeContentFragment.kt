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
import net.maytry.www.smartwiki.databinding.FragmentHomeContentBinding
import net.maytry.www.smartwiki.model.Genre
import net.maytry.www.smartwiki.viewmodel.GenreAdapter
import java.io.Serializable

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [HomeContentFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [HomeContentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class HomeContentFragment : Fragment() {

    private var mGenreList: List<Genre>? = null

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mGenreList = arguments.getSerializable(GENRE_LIST) as List<Genre>
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_home_content, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val binding: FragmentHomeContentBinding = FragmentHomeContentBinding.bind(view)
        binding.genreList = mGenreList
        binding.onItemClickListener = OnClickListItem(mListener)
    }

    override fun onAttach(context: Context?) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            mListener = context as OnFragmentInteractionListener?
        } else {
            throw RuntimeException(context!!.toString() + " must implement OnFragmentInteractionListener")
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
        fun onClickGenreListItem(parent: AdapterView<*>?, view: View?, position: Int, id: Long)
    }

    class OnClickListItem(private val listener: OnFragmentInteractionListener?) : AdapterView.OnItemClickListener {
        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            listener!!.onClickGenreListItem(parent, view, position, id)
        }
    }

    companion object {
        private val GENRE_LIST = "genre_list"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.

         * @param genreList Parameter
         * *
         * @return A new instance of fragment HomeContentFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(genreList: List<Genre>): HomeContentFragment {
            val fragment = HomeContentFragment()
            val args = Bundle()
            args.putSerializable(GENRE_LIST, genreList as Serializable)
            fragment.arguments = args
            return fragment
        }
    }

    object CustomSetter {
        @JvmStatic
        @BindingAdapter("genreList")
        fun setGenreList(listView: ListView, genreList: List<Genre>) {
            val adapter: GenreAdapter = GenreAdapter(listView.context, R.layout.genre_list_item, genreList)
            listView.adapter = adapter
        }
    }
}
