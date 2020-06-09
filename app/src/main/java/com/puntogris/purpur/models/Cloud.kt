package com.puntogris.purpur.models

import android.graphics.Bitmap
import android.graphics.Canvas
import com.puntogris.purpur.R

class Cloud(var posx: Double = 0.0, var posy: Double = 0.0, var velocity: Double = -10.0){

    val cloudImage = R.raw.cloud

    fun updateVelocity(){
        posy += velocity
    }

    fun resetPosition(height: Int, width:Int){
        posx =  (0..width).random().toDouble()
        posy = height + 130.0
    }

    fun setInitialPosition(width: Int, height: Int){
        posx = width/ 2.0
        posy = height.toDouble()
    }

    fun draw(cloudImage:Bitmap, canvas: Canvas){
        canvas.drawBitmap(cloudImage,posx.toFloat()-(cloudImage.width / 2),posy.toFloat(),null)
    }

}
