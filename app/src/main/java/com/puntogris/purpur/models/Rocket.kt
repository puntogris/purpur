package com.puntogris.purpur.models

import android.graphics.Bitmap
import android.graphics.Canvas
import com.puntogris.purpur.R

class Rocket(var posx : Float = -700F, var posy: Float = 200F, var visibility: Boolean = false){

    val rocketImage = R.raw.rocket
    fun restoreToPosXIni(){
        posx = -700f
    }

    fun move(){
        posx += 7
    }

    fun visible() {
        visibility = true
    }
    fun hide(){
        visibility = false
    }
    fun inScreen(width: Int)= posx < width && visibility

    fun draw(canvas: Canvas, rocketImageScaled:Bitmap){
        canvas.drawBitmap(rocketImageScaled,posx,posy,null)
    }

}




