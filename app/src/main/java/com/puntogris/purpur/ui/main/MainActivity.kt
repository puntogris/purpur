package com.puntogris.purpur.ui.main

import android.content.Intent
import android.view.WindowManager
import com.puntogris.purpur.ui.enviroment.MusicService
import com.puntogris.purpur.R
import com.puntogris.purpur.databinding.ActivityMainBinding
import com.puntogris.purpur.ui.base.BaseActivity
import com.puntogris.purpur.utils.gone
import com.puntogris.purpur.utils.visible

class MainActivity : BaseActivity<ActivityMainBinding>(R.layout.activity_main){

    override fun initializeViews() {
        binding.activity = this
        window.setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS, WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS)

        startService()
        subscribeGameStateUi()
    }

    private fun subscribeGameStateUi(){
        binding.apply {
            gameView.didPlayerLose.observe(this@MainActivity, {
                if (it){
                    gameView.gone()
                    postGameGroupView.visible()
                    playerScore.text = gameView.environment.counterScore.value.toString()
                }
            })
        }
    }

    fun onPlayAgain(){
        binding.apply {
            gameView.visible()
            postGameGroupView.gone()
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



