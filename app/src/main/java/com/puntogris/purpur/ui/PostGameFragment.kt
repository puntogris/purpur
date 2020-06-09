package com.puntogris.purpur.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.FragmentTransaction
import androidx.fragment.app.activityViewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.puntogris.purpur.R
import com.puntogris.purpur.databinding.FragmentPostGameBinding
import kotlinx.android.synthetic.main.fragment_post_game.view.*

class PostGameFragment : Fragment() {
    private val viewModel:GameViewModel by activityViewModels()

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding:FragmentPostGameBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_post_game, container, false)

        binding.scoreTextView.text = viewModel.playerScore.toString()

        binding.playAgainButton.setOnClickListener {
            findNavController().navigate(R.id.gameFragment)
        }

        return binding.root

    }

}
