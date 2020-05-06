package com.puntogris.purpur

import android.content.Context
import android.net.Uri
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.FragmentTransaction
import kotlinx.android.synthetic.main.fragment_game.view.*

class GameFragment : Fragment() {
    private var listener: OnFragmentInteractionListener? = null

    override fun onCreateView(

        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        val view: View = inflater.inflate(R.layout.fragment_game, container, false)

        view.gameFragmentLayout.setBackgroundResource(R.drawable.ic_maingame)

        val customView = view.findViewById<Game>(R.id.gameView)

        customView.onLoseListener = {
            activity!!.supportFragmentManager
                .beginTransaction()
                .replace(
                    R.id.fragment_holder,
                    PostGameFragment.newInstance(customView.returnScore())
                )
                .addToBackStack(PostGameFragment.toString())
                .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                .commit()
        }

        // Return the fragment view/layout
        return view

    }

    override fun onAttach(context: Context) {
        super.onAttach(context)
        if (context is OnFragmentInteractionListener) {
            listener = context
        } else {
            throw RuntimeException("$context must implement OnFragmentInteractionListener")
        }
    }

    override fun onDetach() {
        super.onDetach()
        listener = null
    }

    interface OnFragmentInteractionListener {
        fun onFragmentInteraction(uri: Uri)
    }

    companion object {
        @JvmStatic
        fun newInstance() = GameFragment()
    }
}
