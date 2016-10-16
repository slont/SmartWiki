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

    private lateinit var mGenreList: List<Genre>

    private var mListener: OnFragmentInteractionListener? = null

    private lateinit var mBinding: FragmentGenreBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            mGenreList = arguments.getSerializable(GENRE_LIST) as List<Genre>
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_genre, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        mBinding = FragmentGenreBinding.bind(view)
        mBinding.genreList = mGenreList
        mBinding.genreListView.setOnItemClickListener { parent, view, i, l -> mListener?.onClickGenre(parent, i) }
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

    companion object {
        private val GENRE_LIST = "genre_list"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.

         * @param genreList Parameter
         * *
         * @return A new instance of fragment GenreFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(genreList: List<Genre>): GenreFragment {
            val fragment = GenreFragment()
            val args = Bundle()
            args.putSerializable(GENRE_LIST, genreList as Serializable)
            fragment.arguments = args
            return fragment
        }
    }
}
