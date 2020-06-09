package com.puntogris.purpur.models

import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import com.puntogris.purpur.R
import javax.inject.Inject

class Rocket @Inject constructor(var posx : Float = -700F, private var posy: Float = 200F, private var visibility: Boolean = false, val context: Context){

    private val image = BitmapFactory.decodeResource(context.resources,R.raw.rocket)
    private val imageScaled: Bitmap = Bitmap.createScaledBitmap(image,500,260,true)
    private var launchCounter = 0
    private val limitToLaunch = 1000

    private fun move(){
        posx += 7
    }

    fun visible() {
        visibility = true
    }
    private fun hide(){
        visibility = false
    }
    fun inScreen(width: Int)= posx < width && visibility

    fun draw(canvas: Canvas){
        move()
        canvas.drawBitmap(imageScaled,posx - (imageScaled.width / 2), posy,null)
    }

    fun resetValues(){
        posx = -700F
        posy = 200F
        hide()
        launchCounter = 0
    }

    fun timeToLaunch():Boolean{
        launchCounter += 1
        return launchCounter >= limitToLaunch
    }

}




