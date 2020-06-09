package com.puntogris.purpur.models

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.media.MediaPlayer
import com.puntogris.purpur.R
import kotlinx.android.synthetic.main.fragment_game.view.*
import javax.inject.Inject
import javax.inject.Singleton

class Bomb @Inject constructor(var posx : Float = -500F, var posy: Float = 200F, var visibility: Boolean = false, context: Context?){

    private val image: Bitmap = BitmapFactory.decodeResource(context?.resources, R.raw.bombimage)
    val imageScaled: Bitmap = Bitmap.createScaledBitmap(image,170,190,true)

    val bombSound: MediaPlayer = MediaPlayer.create(context, R.raw.bombsound)
    private val crashBomb: MediaPlayer = MediaPlayer.create(context, R.raw.crashbombsound)

    private fun updatePosY(){
        posy += 7
    }

    private fun restoreToPosYIni(){
        posy = 200f
    }
    private fun getRandomPosX(width: Int){
        if(!visibility) posx = (100..width - 100).random().toFloat()
    }
    private fun visible(){
        visibility = true
    }
    private fun hide(){
        visibility = false
    }
    fun outOfScreen(height: Int) = posy > height

    fun inScreen(rocket: Rocket) = rocket.posx > posx && visibility

    fun draw(canvas: Canvas){
        bombSound.start()
        updatePosY()
        canvas.drawBitmap(imageScaled,posx - (imageScaled.width / 2),posy,null)
    }

    fun resetValues(){
        posx = -500F
        posy = 200F
        hide()
    }

    fun explodeSequence(){
        bombSound.stop()
        bombSound.prepareAsync()
        crashBomb.start()
        restoreToPosYIni()
        hide()
    }

    fun drop(width: Int){
        getRandomPosX(width)
        visible()
    }

}
