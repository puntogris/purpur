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

class GameView(context: Context?, attrs: AttributeSet?) : View(context, attrs), SensorEventListener {

    val environment by lazy { injector.gameEnvironment }
    private lateinit var runnable : Runnable
    val didPlayerLose = MutableLiveData(false)
    private val sensorManager: SensorManager = (context!!.getSystemService(SENSOR_SERVICE) as SensorManager)

    init {
        post {
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

    fun startGame(){
        didPlayerLose.value = false
        startAnimation()
        registerSensorListener()

    }

    //Sensor Manager && Listener
    private fun registerSensorListener(){
        sensorManager.registerListener(this@GameView, sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
                SensorManager.SENSOR_DELAY_FASTEST)
    }
    private fun unregisterSensorListener(){
        sensorManager.unregisterListener(this)
    }

    override fun onSensorChanged(sensor: SensorEvent?) {
        sensor?.let {
            val positionZ = (it.values[0] * -1)
            if(positionZ < -1) environment.bird.moveLeft()
            else if(positionZ > 1) environment.bird.moveRight()
        }
    }

    //Drawing by the Environment Drawer Class
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

    //Runner Management
    private fun startAnimation(){
        post(runnable)
    }

    private fun stopAnimation(){
        removeCallbacks(runnable)
    }

    //Checks
    private fun checkCollisionBirdBomb(){
        if (environment.checkCollisionBirdBomb()){
            stopAnimation()
            didPlayerLose.value = true
            unregisterSensorListener()
        }
    }

    private fun checkLoser() {
        if (environment.bird.posy >= height) {
            stopAnimation()
            environment.cloud.setInitialPosition(width,height)
            unregisterSensorListener()
            didPlayerLose.value = true

        }else environment.updateScore()
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int){}
}