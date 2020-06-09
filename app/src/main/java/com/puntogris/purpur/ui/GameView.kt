package com.puntogris.purpur.ui

import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.graphics.*
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.util.AttributeSet
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.puntogris.purpur.R
import com.puntogris.purpur.models.Bird
import com.puntogris.purpur.models.Bomb
import com.puntogris.purpur.models.Cloud
import com.puntogris.purpur.models.Rocket
import kotlinx.android.synthetic.main.fragment_game.view.*
import kotlin.math.abs

class GameView(context: Context?, attrs: AttributeSet?) : View(context, attrs), SensorEventListener {

    val didPlayerLose = MutableLiveData(false)

    private lateinit var runnable : Runnable
    private var counterScore: Int = 0
    private var counterFpsBirdImageChange = 0
    private var counterFpsRocketVisibility = 0
    private var textPaint = Paint().apply {
        color = Color.BLACK
        textSize = 80f
        isAntiAlias = true
    }

    private var bird = Bird()
    private var cloud = Cloud()
    private var rocket = Rocket()
    private var bomb = Bomb()

    private val cloudImage = getImageBitmap(cloud.cloudImage)
    private val bombImage = getImageBitmap(bomb.bombImage)
    private val rocketImage = getImageBitmap(rocket.rocketImage)
    private val birdImage1 = getImageBitmap(bird.birdImage1)
    private val birdImage2 = getImageBitmap(bird.birdImage2)

    private val rocketImageScaled: Bitmap = Bitmap.createScaledBitmap(rocketImage,500,260,true)
    private val bombImageScaled: Bitmap = Bitmap.createScaledBitmap(bombImage,170,190,true)
    private var birdImageFinal = birdImage1

    private var collisionSoundBirdCloud = MediaPlayer.create(gameView.context, R.raw.bouncesound)
    private var bombSound = MediaPlayer.create(gameView.context, R.raw.bombsound)
    private var crashBomb = MediaPlayer.create(gameView.context, R.raw.crashbombsound)

    init {
        registerSensorListener()

        gameView.post {
            bird.setInitialPosition(width)
            cloud.setInitialPosition(width,height)
        }
        runnable = Runnable {
            bird.updateVelocity()
            cloud.updateVelocity()
            invalidate()
            post(runnable)
        }
        startAnimation()
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int){
        //...
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        val positionZ = (p0!!.values[0] * -1)
        if(positionZ < -1) bird.updatePosX(-2)
        else if(positionZ > 1) bird.updatePosX(2)
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.apply {

            drawBitmap(birdImageFinal, bird.posx.toFloat() - (birdImageFinal.width /2),bird.posy.toFloat(),null)
            cloud.draw(cloudImage, this)

            drawText(counterScore.toString(),
                width - 240f,
                200f,
                textPaint
            )

            if(timeToLaunchRocket()){
                rocket.visible()

                if(rocket.inScreen(width)){
                    rocket.move()
                    rocket.draw(this, rocketImageScaled)
                    bomb.getRandomPosX(width)
                    bomb.visible()
                }
                if(bomb.inScreen(rocket)){
                    bombSound.start()
                    bomb.draw(this, bombImageScaled)
                    bomb.updatePosY()

                    if (bomb.outOfScreen(height)){
                        rocket.restoreToPosXIni()
                        rocket.hide()
                        bombSound.stop()
                        bombSound.prepareAsync()
                        crashBomb.start()
                        bomb.restoreToPosYIni()
                        bomb.hide()
                        counterFpsRocketVisibility = 0
                    }
                }
            }
            counterFpsRocketVisibility += 1

            changeImageBirdFps()
            checkCollisionBirdBomb()
            checkCollisionBirdCloud()
            checkLoser()
        }
    }

    private fun getImageBitmap(image:Int) = BitmapFactory.decodeResource(context?.resources,image)


    private fun startAnimation(){
        post(runnable)
    }


    private fun stopAnimation(){
        removeCallbacks(runnable)
    }

    private fun changeImageBirdFps(){
        if (counterFpsBirdImageChange >= 15){
            birdImageFinal = if(birdImageFinal == birdImage1){
                birdImage2
            }else{
                birdImage1
            }
            counterFpsBirdImageChange = 0
        }
        counterFpsBirdImageChange += 1
    }


    private fun checkCollisionBirdCloud(){
        if (
            abs(cloud.posy - bird.posy) <= 150 &&
            bird.posx >= (cloud.posx - birdImageFinal.width / 2) &&
            bird.posx <= (cloud.posx + cloudImage.width - (birdImageFinal.width / 2))){
                bird.updateVelocityOnCollision()
                collisionSoundBirdCloud.start()
                cloud.resetPosition(height, width)
        }
    }

    private fun checkCollisionBirdBomb(){
        if(bomb.visibility) {
            if (
                bird.posx + birdImageFinal.width / 2 - 100 >= bomb.posx &&
                bird.posx - birdImageFinal.width / 2 + 100 <= bomb.posx + bombImageScaled.width ){
                if(
                    bird.posy <= bomb.posy + bombImageScaled.height - 100 &&
                    bird.posy >= bomb.posy - birdImageFinal.height + 100){
                        bombSound.stop()
                        bombSound.prepareAsync()
                        stopAnimation()
                        didPlayerLose.value = true
                }
            }
        }
    }

    private fun checkLoser() {
        if (bird.posy >= height) {
            stopAnimation()
            didPlayerLose.value = true
        }else counterScore += 1
    }

    fun returnScore() = counterScore.toString()

    private fun registerSensorListener(){
        (context!!.getSystemService(SENSOR_SERVICE) as SensorManager).apply {
            registerListener(this@GameView, getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_FASTEST)
        }
    }

    private fun timeToLaunchRocket() = counterFpsRocketVisibility >= 1000

}