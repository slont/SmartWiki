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
import net.maytry.www.smartwiki.model.Genre
import net.maytry.www.smartwiki.viewmodel.GenreAdapter
import java.io.Serializable

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [GenreContentFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [GenreContentFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GenreContentFragment : Fragment() {

    private lateinit var mGenreList: List<Genre>

    private var mListener: OnFragmentInteractionListener? = null

    private lateinit var binding: FragmentGenreContentBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mGenreList = arguments.getSerializable(GENRE_LIST) as List<Genre>
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_genre_content, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        binding = FragmentGenreContentBinding.bind(view)
        binding.genreList = mGenreList
        binding.genreListView.setOnItemClickListener { parent, view, i, l -> mListener?.onClickGenreListItem(parent, i) }
        mListener?.loadData()
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

    fun notifyDataSetChanged() {
        ((binding.genreListView as? ListView)?.adapter as? GenreAdapter)?.notifyDataSetChanged()
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
        fun onClickGenreListItem(parent: AdapterView<*>?, position: Int)
        fun loadData()
    }

    companion object {
        private val GENRE_LIST = "genre_list"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.

         * @param genreList Parameter
         * *
         * @return A new instance of fragment GenreContentFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(genreList: List<Genre>): GenreContentFragment {
            val fragment = GenreContentFragment()
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
            val adapter: GenreAdapter = GenreAdapter(listView.context, genreList)
            listView.adapter = adapter
        }
    }
}
