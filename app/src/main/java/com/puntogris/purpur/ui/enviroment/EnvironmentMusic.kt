package com.puntogris.purpur.ui.enviroment

import android.content.Context
import android.media.MediaPlayer
import com.puntogris.purpur.R
import javax.inject.Inject

class EnvironmentMusic @Inject constructor(context: Context) {
    val collisionSoundBirdCloud: MediaPlayer = MediaPlayer.create(context, R.raw.bouncesound)
    val bombSound: MediaPlayer = MediaPlayer.create(context, R.raw.bombsound)
    val crashBomb: MediaPlayer = MediaPlayer.create(context, R.raw.crashbombsound)
    val backgroundMusic: MediaPlayer = MediaPlayer.create(context, R.raw.backgroundmusic)
}