package com.puntogris.purpur.models

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import com.puntogris.purpur.App
import com.puntogris.purpur.R
import javax.inject.Inject
import javax.inject.Singleton

class Rocket @Inject constructor(var posx : Float = -700F, var posy: Float = 200F, var visibility: Boolean = false, val context: Context){

    private val rocketImage = BitmapFactory.decodeResource(context.resources,R.raw.rocket)
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

    fun draw(canvas: Canvas){
        val rocketImageScaled: Bitmap = Bitmap.createScaledBitmap(rocketImage,500,260,true)
        canvas.drawBitmap(rocketImageScaled,posx-(rocketImageScaled.width / 2),posy,null)
    }

    fun resetValues(){
        posx = -700F
        posy = 200F
        hide()
    }


}




