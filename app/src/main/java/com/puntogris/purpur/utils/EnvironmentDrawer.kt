package com.puntogris.purpur.utils

import android.content.Context
import android.graphics.*
import android.media.MediaPlayer
import com.puntogris.purpur.R
import com.puntogris.purpur.models.Bird
import com.puntogris.purpur.models.Bomb
import com.puntogris.purpur.models.Cloud
import com.puntogris.purpur.models.Rocket
import javax.inject.Inject

class EnvironmentDrawer @Inject constructor(context: Context){
    //Cloud
    val cloudImage: Bitmap = BitmapFactory.decodeResource(context.resources, R.raw.cloud)
    val collisionSoundBirdCloud: MediaPlayer = MediaPlayer.create(context, R.raw.bouncesound)

    //Bomb
    private val bombImage: Bitmap = BitmapFactory.decodeResource(context.resources, R.raw.bombimage)
    val bombImageScaled: Bitmap = Bitmap.createScaledBitmap(bombImage,170,190,true)
    val bombSound: MediaPlayer = MediaPlayer.create(context, R.raw.bombsound)
    val crashBomb: MediaPlayer = MediaPlayer.create(context, R.raw.crashbombsound)

    //Rocket
    private val rocketImage: Bitmap = BitmapFactory.decodeResource(context.resources, R.raw.rocket)
    private val rocketImageScaled: Bitmap = Bitmap.createScaledBitmap(rocketImage,500,260,true)

    //Bird
    val birdImage1: Bitmap = BitmapFactory.decodeResource(context.resources, R.raw.bird1)
    val birdImage2: Bitmap = BitmapFactory.decodeResource(context.resources, R.raw.bird2)
    var birdImageFinal = birdImage1

    //Text
    private val textPaint = Paint().apply {
        color = Color.BLACK
        textSize = 80f
        isAntiAlias = true
    }

    fun draw(canvas: Canvas, playerScore:String, width: Int){
        canvas.drawText(playerScore ,width - 240f,200f, textPaint)
    }

    fun draw(canvas: Canvas, cloud: Cloud){
        canvas.drawBitmap(cloudImage, cloud.posx.toFloat() - (cloudImage.width / 2),cloud.posy.toFloat(),null)
    }

    fun draw(canvas: Canvas, rocket: Rocket){
        canvas.drawBitmap(rocketImageScaled,rocket.posx - (rocketImageScaled.width / 2), rocket.posy,null)
    }

    fun draw(canvas: Canvas, bomb: Bomb){
        canvas.drawBitmap(bombImageScaled, bomb.posx - (bombImageScaled.width / 2), bomb.posy,null)
    }

    fun draw(canvas: Canvas, bird: Bird){
        canvas.drawBitmap(birdImageFinal, bird.posx.toFloat() - (birdImageFinal.width / 2),bird.posy.toFloat(),null)
    }

}