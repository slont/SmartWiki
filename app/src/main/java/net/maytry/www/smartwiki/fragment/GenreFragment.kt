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
import net.maytry.www.smartwiki.databinding.FragmentGenreBinding
import net.maytry.www.smartwiki.model.Genre
import net.maytry.www.smartwiki.viewmodel.GenreAdapter
import java.io.Serializable

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [GenreFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [GenreFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GenreFragment : Fragment() {

    companion object {
        private const val LAYOUT_RES = R.layout.fragment_genre
        private const val GENRE_LIST = "genre_list"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.

         * @param genreList Parameter
         * *
         * @return A new instance of fragment GenreFragment.
         */
        fun newInstance(genreList: List<Genre>): GenreFragment {
            return GenreFragment().apply {
                arguments = Bundle().apply { putSerializable(GENRE_LIST, genreList as Serializable) }
            }
        }
    }

    private lateinit var mGenreList: List<Genre>

    private var mListener: OnFragmentInteractionListener? = null

    private lateinit var mBinding: FragmentGenreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        arguments?.run { mGenreList = getSerializable(GENRE_LIST) as List<Genre> }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(LAYOUT_RES, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mBinding = FragmentGenreBinding.bind(view).apply {
            genreList = mGenreList
            genreListView.setOnItemClickListener { parent, view, i, l -> mListener?.onClickGenre(parent, i) }
        }
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
        ((mBinding.genreListView as? ListView)?.adapter as? GenreAdapter)?.notifyDataSetChanged()
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
        fun onClickGenre(parent: AdapterView<*>?, position: Int)
        fun loadData()
    }
}
