package com.puntogris.purpur.utils

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.puntogris.purpur.di.injector

class MusicService: Service() {
    private val musicEnv by lazy { injector.musicEnv }

    override fun onStartCommand(intent: Intent, flags: Int, startId: Int): Int {
        musicEnv.backgroundMusic.start()
        return START_STICKY
    }

    override fun onBind(p0: Intent?): IBinder? = null

    override fun onDestroy() {
        super.onDestroy()
        musicEnv.apply {
            backgroundMusic.stop()
            backgroundMusic.prepare()
        }
    }
}