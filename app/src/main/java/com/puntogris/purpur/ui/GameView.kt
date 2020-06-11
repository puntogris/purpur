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

    private lateinit var runnable : Runnable
    val environment by lazy { injector.gameEnvironment }
    val didPlayerLose = MutableLiveData(false)
    private val sensorManager: SensorManager =
        (context!!.getSystemService(SENSOR_SERVICE) as SensorManager).apply {
            registerListener(this@GameView, getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_FASTEST)
    }

    init {
        resetEnvironment()
        gameView.post {
            environment.setDimensEnvironment(height,width)
        }
        runnable = Runnable {
            environment.bird.updateVelocity()
            environment.cloud.move()
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
            if(positionZ < -1) environment.bird.moveLeft()
            else if(positionZ > 1) environment.bird.moveRight()
        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        environment.apply {
            draw(canvas)
            changeBirdAnimation()
            rocketManager(canvas)
            checkCollisionBirdCloud()
            checkLoser()
            this@GameView.checkCollisionBirdBomb()
        }
    }

    private fun startAnimation(){
        post(runnable)
    }

    private fun stopAnimation(){
        removeCallbacks(runnable)
    }
    private fun checkCollisionBirdBomb(){
        if (environment.checkCollisionBirdBomb()){
            stopAnimation()
            didPlayerLose.value = true
            sensorManager.unregisterListener(this)
        }
    }

    private fun checkLoser() {
        if (environment.bird.posy >= height) {
            stopAnimation()
            didPlayerLose.value = true
            sensorManager.unregisterListener(this)

        }else environment.updateScore()
    }

    private fun resetEnvironment(){
        environment.reset()
    }
}