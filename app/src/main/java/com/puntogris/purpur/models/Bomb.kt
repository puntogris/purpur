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

    val bombImage: Bitmap = BitmapFactory.decodeResource(context?.resources, R.raw.bombimage)
    val bombSound: MediaPlayer = MediaPlayer.create(context, R.raw.bombsound)
    val crashBomb: MediaPlayer = MediaPlayer.create(context, R.raw.crashbombsound)

    fun updatePosY(){
        posy += 7
    }

    fun restoreToPosYIni(){
        posy = 200f
    }
    fun getRandomPosX(width: Int){
        if(!visibility){
            posx = (100..width - 100).random().toFloat()
        }
    }
    fun visible(){
        visibility = true
    }
    fun hide(){
        visibility = false
    }
    fun outOfScreen(height: Int) = posy > height

    fun inScreen(rocket: Rocket) = rocket.posx > posx && visibility

    fun draw(canvas: Canvas){
        val rocketImageScaled: Bitmap = Bitmap.createScaledBitmap(bombImage,500,260,true)
        canvas.drawBitmap(rocketImageScaled,posx-(rocketImageScaled.width / 2),posy,null)
    }

    fun resetValues(){
        posx = -500F
        posy = 200F
        hide()
    }




}
