package com.puntogris.purpur.ui

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.WindowManager
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.Observer
import com.puntogris.purpur.utils.MusicService
import com.puntogris.purpur.R
import com.puntogris.purpur.databinding.ActivityMainBinding

class MainActivity : AppCompatActivity(){
private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        binding.activity = this
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        startService()

        binding.apply {
            gameView.didPlayerLose.observe(this@MainActivity, Observer {
                if (it){
                    gameView.visibility = View.GONE
                    postGameGroupView.visibility = View.VISIBLE
                    playerScore.text = gameView.environment.counterScore.value.toString()
                }
            })
        }

    }

    fun onPlayAgain(){
        binding.apply {
            gameView.visibility = View.VISIBLE
            postGameGroupView.visibility = View.GONE
            gameView.startGame()
            gameView.environment.reset()
        }
    }

    override fun onPause() {
        super.onPause()
        stopService(Intent(this, MusicService::class.java))
    }

    override fun onResume() {
        super.onResume()
        startService()
    }

    private fun startService(){
        startService(Intent(this, MusicService::class.java))
    }
}



