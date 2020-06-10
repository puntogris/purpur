package com.puntogris.purpur

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder


class MusicService: Service() {
    private lateinit var backgroundMusic: MediaPlayer

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        backgroundMusic.start()
        return START_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onCreate() {
        backgroundMusic = MediaPlayer.create(this, R.raw.backgroundmusic)
    }

    override fun onDestroy() {
        super.onDestroy()
        backgroundMusic.stop()
        backgroundMusic.release()
    }

}