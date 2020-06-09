package com.puntogris.purpur.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.puntogris.purpur.R
import com.puntogris.purpur.databinding.FragmentGameBinding
import kotlinx.android.synthetic.main.fragment_game.view.*

class GameFragment : Fragment() {
private val viewModel:GameViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding: FragmentGameBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false)

        binding.gameView.didPlayerLose.observe(viewLifecycleOwner, Observer {
            if (it) navigateToPostGame()
        })
        return binding.root

    }

    fun navigateToPostGame(){
        findNavController().navigate(R.id.action_gameFragment_to_postGameFragment)
    }

}
