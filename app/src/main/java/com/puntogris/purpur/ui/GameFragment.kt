package com.puntogris.purpur.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.activityViewModels
import androidx.lifecycle.Observer
import androidx.navigation.fragment.findNavController
import com.puntogris.purpur.R
import com.puntogris.purpur.databinding.FragmentGameBinding

class GameFragment : Fragment() {
private val viewModel:GameViewModel by activityViewModels()

private lateinit var binding: FragmentGameBinding
    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_game, container, false)

        viewModel.resetScore()

        binding.gameView.didPlayerLose.observe(viewLifecycleOwner, Observer {
            if (it) navigateToPostGame()
        })
        return binding.root
    }

    private fun navigateToPostGame(){
        viewModel.updateScore(binding.gameView.environment.returnScore())
        findNavController().navigate(R.id.action_gameFragment_to_postGameFragment)
    }

}
