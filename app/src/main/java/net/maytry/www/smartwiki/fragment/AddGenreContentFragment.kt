package net.maytry.www.smartwiki.fragment

import android.content.Context
import android.os.Bundle
import android.support.v4.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import net.maytry.www.smartwiki.R
import net.maytry.www.smartwiki.databinding.FragmentAddGenreContentBinding

/**
 *
 */
class AddGenreContentFragment : Fragment() {

    private var mListener: OnFragmentInteractionListener? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(inflater: LayoutInflater?, container: ViewGroup?,
                              savedInstanceState: Bundle?): View? {
        return inflater!!.inflate(R.layout.fragment_add_genre_content, container, false)
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)

        val binding = FragmentAddGenreContentBinding.bind(view)
        binding.onClickCancelButton = OnClickCancelButton(mListener)
        binding.onClickCreateButton = OnClickCreateButton(mListener)
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
        fun onClickCancelButton(v: View)
        fun onClickCreateButton(v: View)
    }

    class OnClickCancelButton(private val listener: OnFragmentInteractionListener?) : View.OnClickListener {
        override fun onClick(v: View) {
            listener!!.onClickCancelButton(v)
        }
    }

    class OnClickCreateButton(private val listener: OnFragmentInteractionListener?) : View.OnClickListener {
        override fun onClick(v: View) {
            listener!!.onClickCreateButton(v)
        }
    }

    companion object {
        fun newInstance(): AddGenreContentFragment {
            return AddGenreContentFragment()
        }
    }
}
