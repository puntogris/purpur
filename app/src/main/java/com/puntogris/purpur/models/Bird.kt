package com.puntogris.purpur.models

import android.content.Context
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.media.MediaPlayer
import com.puntogris.purpur.R
import javax.inject.Inject

class Bird @Inject constructor(var posx: Double = 0.0 ,var posy: Double = 500.0 ,var velocity: Double = 1.0, val context: Context?){

    private val birdImage1 = BitmapFactory.decodeResource(context?.resources, R.raw.bird1)
    private val birdImage2 = BitmapFactory.decodeResource(context?.resources, R.raw.bird2)
    private var birdImageFinal = birdImage1
    val collisionSoundBirdCloud: MediaPlayer = MediaPlayer.create(context, R.raw.bouncesound)
    private var animationCounter = 0

    fun updateVelocity(){
        velocity += 0.5
        posy += velocity
    }

    fun setInitialPosition(width: Int){
        posx = width / 2.0
    }

    fun updateVelocityOnCollision(){
        velocity *= -1
        velocity -= 0.5
    }

    fun resetValues(){
        posx = 0.0
        posy = 500.0
        velocity = 1.0
    }

    fun draw(canvas: Canvas){
        changeImageBirdFps()
        canvas.drawBitmap(birdImageFinal, posx.toFloat() - (birdImageFinal.width /2),posy.toFloat(),null)
    }

    private fun changeImageBirdFps(){
        if (animationCounter >= 15){
            birdImageFinal = if(birdImageFinal == birdImage1){
                birdImage2
            }else{
                birdImage1
            }
            animationCounter = 0
        }
         animationCounter += 1
    }

    fun imageWidth() = birdImageFinal.width
    fun imageHeight() = birdImageFinal.height

    fun moveLeft(){
        posx -= 2
    }
    fun moveRight(){
        posx += 2
    }

}