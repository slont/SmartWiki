package net.maytry.www.smartwiki.fragment

import android.content.Context
import android.content.Intent
import android.databinding.BindingAdapter
import android.os.Bundle
import android.support.v4.app.Fragment
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.AdapterView
import android.widget.ListView
import net.maytry.www.smartwiki.R
import net.maytry.www.smartwiki.databinding.FragmentGenreListBinding
import net.maytry.www.smartwiki.model.Genre
import net.maytry.www.smartwiki.viewmodel.GenreAdapter
import java.io.Serializable

/**
 * A simple [Fragment] subclass.
 * Activities that contain this fragment must implement the
 * [GenreListFragment.OnFragmentInteractionListener] interface
 * to handle interaction events.
 * Use the [GenreListFragment.newInstance] factory method to
 * create an instance of this fragment.
 */
class GenreListFragment : Fragment() {

    private val mGenreList: MutableList<Genre> = mutableListOf()
    var genreList: List<Genre>? = null

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        if (arguments != null) {
            Log.d("onAct11", arguments.getSerializable(ARG_PARAM1).toString())
            genreList = arguments.getSerializable(ARG_PARAM1) as List<Genre>
        }
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        // Inflate the layout for this fragment
        return inflater!!.inflate(R.layout.fragment_genre_list, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val binding: FragmentGenreListBinding = FragmentGenreListBinding.bind(view)
        mGenreList.add(Genre("test1"))
        binding.genreList = genreList

    }

    // TODO: Rename method, update argument and hook method into UI event
//    fun onButtonPressed(uri: Uri) {
//        if (mListener != null) {
//            mListener!!.onChangeFavorite(uri)
//        }
//    }

    fun onClickFavoriteButton(view: View) {
        if (mListener != null) {
//            mListener!!.onChangeFavorite()
        }
        Log.d("onClick", "2")
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
        // TODO: Update argument type and name
        fun onChangeFavorite(data: Intent)
    }

    companion object {
        // TODO: Rename parameter arguments, choose names that match
        // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
        private val ARG_PARAM1 = "param1"

        /**
         * Use this factory method to create a new instance of
         * this fragment using the provided parameters.

         * @param param1 Parameter 1.
         * *
         * @param param2 Parameter 2.
         * *
         * @return A new instance of fragment HomeContentFragment.
         */
        // TODO: Rename and change types and number of parameters
        fun newInstance(genreList: List<Genre>): GenreListFragment {
            val fragment = GenreListFragment()
            val args = Bundle()
            args.putSerializable(ARG_PARAM1, genreList as Serializable)
            fragment.arguments = args
            Log.d("onAct", genreList.toString())
            return fragment
        }
    }

    object CustomSetter {
        @JvmStatic
        @BindingAdapter("genreList")
        fun setGenreList(listView: ListView, genreList: List<Genre>) {
            Log.d("bind1", genreList.toString())
            val adapter: GenreAdapter = GenreAdapter(listView.context, R.layout.genre_list_item, genreList)
            listView.adapter = adapter
            listView.onItemClickListener = OnClickGenre()
        }
    }


    class OnClickGenre : AdapterView.OnItemClickListener {
        override fun onItemClick(parent: AdapterView<*>?, view: View?, position: Int, id: Long) {
            val msg: String = position.toString() + "番目"
            Log.d("onClick", msg)
//            throw UnsupportedOperationException("not implemented") //To change body of created functions use File | Settings | File Templates.
        }
    }
}
