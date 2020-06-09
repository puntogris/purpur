package com.puntogris.purpur.models

import android.graphics.Bitmap
import android.graphics.Canvas
import com.puntogris.purpur.R

class Bomb(var posx : Float = -500F, var posy: Float = 200F, var visibility: Boolean = false){

    val bombImage = R.raw.bombimage

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

    fun draw(canvas: Canvas, bomImageScaled: Bitmap){
        canvas.drawBitmap(bomImageScaled, posx, posy,null)
    }
}
