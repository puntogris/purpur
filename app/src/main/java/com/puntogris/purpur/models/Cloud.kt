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

class Cloud @Inject constructor(var posx: Double = 0.0, var posy: Double = 0.0, var velocity: Double = -10.0, val context: Context){

    val image: Bitmap = BitmapFactory.decodeResource(context.resources, R.raw.cloud)

    fun updateVelocity(){
        posy += velocity
    }

    fun resetPosition(height: Int, width:Int){
        posx =  (0..width).random().toDouble()
        posy = height + 130.0
    }

    fun setInitialPosition(width: Int, height: Int){
        posx = width / 2.0
        posy = height.toDouble()
    }

    fun draw(canvas: Canvas){
        canvas.drawBitmap(image,posx.toFloat() - (image.width / 2),posy.toFloat(),null)
    }

    fun resetValues(){
        posx = 0.0
        posy = 500.0
        velocity = -10.0
    }


}
