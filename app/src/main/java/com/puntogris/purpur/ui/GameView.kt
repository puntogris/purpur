package com.puntogris.purpur.ui

import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.graphics.*
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.util.AttributeSet
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.puntogris.purpur.di.injector
import kotlinx.android.synthetic.main.fragment_game.view.*

class GameView(context: Context?, attrs: AttributeSet?) : View(context, attrs), SensorEventListener {

    val didPlayerLose = MutableLiveData(false)
    private lateinit var runnable : Runnable
    private var counterScore: Int = 0
    private var textPaint = Paint().apply {
        color = Color.BLACK
        textSize = 80f
        isAntiAlias = true
    }
    private val cloud by lazy { injector.cloud }
    private val rocket by lazy { injector.rocket }
    private val bomb by lazy { injector.bomb }
    private val bird by lazy { injector.bird }

    private val sensorManager: SensorManager =
        (context!!.getSystemService(SENSOR_SERVICE) as SensorManager).apply {
            registerListener(this@GameView, getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_FASTEST)
    }

    init {
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
    }

    override fun onSensorChanged(sensor: SensorEvent?) {
        sensor?.let {
            val positionZ = (it.values[0] * -1)
            if(positionZ < -1) bird.moveLeft()
            else if(positionZ > 1) bird.moveRight()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        canvas.apply {
            bird.draw(this)
            cloud.draw(this)
            drawScore()
            rocketManager()
            if (bird.collideWithCloud(cloud)) cloud.resetPosition(height,width)
            checkLoser()
        }
    }

    private fun Canvas.rocketManager(){
        if(rocket.timeToLaunch()){
            rocket.visible()
            if(rocket.inScreen(width)){
                rocket.draw(this)
                bomb.drop(width)
            }
            if(bomb.inScreen(rocket)){
                checkCollisionBirdBomb()
                bomb.draw(this)
                if (bomb.outOfScreen(height)){
                    bomb.explodeSequence()
                    rocket.resetValues()
                }
            }
        }
    }


    private fun Canvas.drawScore(){
        drawText(counterScore.toString(),width - 240f,200f, textPaint )
    }

    private fun startAnimation(){
        post(runnable)
    }

    private fun stopAnimation(){
        removeCallbacks(runnable)
    }
    private fun checkCollisionBirdBomb(){
        if (bird.collideWithBomb(bomb)){
            stopAnimation()
            didPlayerLose.value = true
            sensorManager.unregisterListener(this)
        }
    }

    private fun checkLoser() {
        if (bird.posy >= height) {
            stopAnimation()
            didPlayerLose.value = true
            sensorManager.unregisterListener(this)

        }else counterScore += 1
    }

    fun returnScore() = counterScore.toString()

    private fun resetValues(){
        bird.resetValues()
        bomb.resetValues()
        rocket.resetValues()
        cloud.resetValues()
    }
}