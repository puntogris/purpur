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
import com.puntogris.purpur.App
import com.puntogris.purpur.R
import com.puntogris.purpur.di.AppComponent
import com.puntogris.purpur.di.injector
import com.puntogris.purpur.models.Bird
import com.puntogris.purpur.models.Bomb
import com.puntogris.purpur.models.Cloud
import com.puntogris.purpur.models.Rocket
import kotlinx.android.synthetic.main.fragment_game.view.*
import javax.inject.Inject
import kotlin.math.abs

class GameView(context: Context?, attrs: AttributeSet?) : View(context, attrs), SensorEventListener {

    val didPlayerLose = MutableLiveData(false)
    private lateinit var runnable : Runnable
    private var counterScore: Int = 0
    private var counterFpsRocketVisibility = 0
    private var textPaint = Paint().apply {
        color = Color.BLACK
        textSize = 80f
        isAntiAlias = true
    }

    private val cloud by lazy { injector.cloud }
    private val rocket by lazy { injector.rocket }
    private val bomb by lazy { injector.bomb }
    private val bird by lazy { injector.bird }


    init {
        registerSensorListener()
        resetValues()
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

            bird.draw(this)
            cloud.draw(this)

            drawText(counterScore.toString(),
                width - 240f,
                200f,
                textPaint
            )

            if(timeToLaunchRocket()){
                rocket.visible()

                if(rocket.inScreen(width)){
                    rocket.move()
                    rocket.draw( this)
                    bomb.getRandomPosX(width)
                    bomb.visible()
                }
                if(bomb.inScreen(rocket)){
                    bomb.bombSound.start()
                    bomb.draw(this)
                    bomb.updatePosY()

                    if (bomb.outOfScreen(height)){
                        rocket.restoreToPosXIni()
                        rocket.hide()
                        bomb.bombSound.stop()
                        bomb.bombSound.prepareAsync()
                        bomb.crashBomb.start()
                        bomb.restoreToPosYIni()
                        bomb.hide()
                        counterFpsRocketVisibility = 0
                    }
                }
            }
            counterFpsRocketVisibility += 1

            checkCollisionBirdBomb()
            checkCollisionBirdCloud()
            checkLoser()
        }
    }

    private fun startAnimation(){
        post(runnable)
    }

    private fun stopAnimation(){
        removeCallbacks(runnable)
    }


    private fun checkCollisionBirdCloud(){
        if (
            abs(cloud.posy - bird.posy) <= 150 &&
            bird.posx >= (cloud.posx - bird.imageWidth() / 2) &&
            bird.posx <= (cloud.posx + cloud.cloudImage.width - (bird.imageWidth() / 2))){
                bird.updateVelocityOnCollision()
                bird.collisionSoundBirdCloud.start()
                cloud.resetPosition(height, width)
        }
    }

    private fun checkCollisionBirdBomb(){
        if(bomb.visibility) {
            if (
                bird.posx + bird.imageWidth()/ 2 - 100 >= bomb.posx &&
                bird.posx - bird.imageWidth() / 2 + 100 <= bomb.posx + bomb.bombImage.width ){
                if(
                    bird.posy <= bomb.posy + bomb.bombImage.height - 100 &&
                    bird.posy >= bomb.posy - bird.imageHeight() + 100){
                        bomb.bombSound.stop()
                        bomb.bombSound.prepareAsync()
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

    private fun resetValues(){
        bomb.resetValues()
        bird.resetValues()
        rocket.resetValues()
        cloud.resetValues()
    }

}