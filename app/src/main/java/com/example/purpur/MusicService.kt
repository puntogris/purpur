package com.example.purpur

import android.app.Service
import android.content.Intent
import android.media.MediaPlayer
import android.os.IBinder



class MusicService: Service() {
    lateinit var player: MediaPlayer

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        player.start()
        return START_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? {
        return null
    }

    override fun onCreate() {
        player = MediaPlayer.create(this, R.raw.backgroundmusic)


    }

    override fun onDestroy() {
        super.onDestroy()
        player.stop()
        player.release()
    }


}