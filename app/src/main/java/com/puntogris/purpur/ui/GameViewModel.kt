package com.puntogris.purpur.ui

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class GameViewModel: ViewModel() {

    private val _playerScore = MutableLiveData<String>()
    val playerScore:LiveData<String> = _playerScore

    fun updateScore(newScore: String){
        _playerScore.value = newScore
    }

    fun resetScore(){
        _playerScore.value = "0"
    }

}