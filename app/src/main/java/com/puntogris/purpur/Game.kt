package com.puntogris.purpur

import android.content.Context
import android.content.Context.SENSOR_SERVICE
import android.graphics.*
import android.hardware.Sensor
import android.hardware.SensorEvent
import android.hardware.SensorEventListener
import android.hardware.SensorManager
import android.media.MediaPlayer
import android.net.Uri
import android.util.AttributeSet
import android.view.View
import kotlinx.android.synthetic.main.fragment_game.view.*
import kotlin.math.abs

class Game(context: Context?, attrs: AttributeSet?) : View(context, attrs), SensorEventListener,
    GameFragment.OnFragmentInteractionListener {

    //Images
    private val birdImage1 = BitmapFactory.decodeResource(context?.resources, R.raw.bird1)
    private val birdImage2 = BitmapFactory.decodeResource(context?.resources, R.raw.bird2)
    private val rocketImage = BitmapFactory.decodeResource(context?.resources, R.raw.rocket)
    private val cloudImage = BitmapFactory.decodeResource(context?.resources, R.raw.cloud)
    private val bombImage = BitmapFactory.decodeResource(context?.resources, R.raw.bombimage)
    private val rocketImageScaled: Bitmap = Bitmap.createScaledBitmap(rocketImage,500,260,true)
    private val bombImageScaled: Bitmap = Bitmap.createScaledBitmap(bombImage,170,190,true)
    private var birdImageFinal = birdImage1

    //Sound
    private var collisionSoundBirdCloud = MediaPlayer.create(gameView.context, R.raw.bouncesound)
    private var bombSound = MediaPlayer.create(gameView.context, R.raw.bombsound)
    private var crashBomb = MediaPlayer.create(gameView.context, R.raw.crashbombsound)

    //Objects
    private var bird = Bird(-100.0, -100.0, 0.0)
    private var cloud = Cloud(0.0, 0.0, 0.0)
    private var rocket = Rocket(-700f, 200f, false)
    private var bomb = Bomb(-500f, 200f, false)

    //Tell the fragment that the player lost
    var onLoseListener: () -> Unit = {
    }

    //Others that need initialization first
    private lateinit var runnable : Runnable
    private var mSensorManager : SensorManager ?= null
    private var counterScore: Int = 0
    private var counterFpsBirdImageChange = 0
    private var counterFpsRocketVisibility = 0
    private var randomStartPosXBomb = 0
    private var textPaint = Paint()


    init {
        mSensorManager = context!!.getSystemService(SENSOR_SERVICE) as SensorManager

        mSensorManager!!.registerListener(this,
            mSensorManager!!.getDefaultSensor(Sensor.TYPE_ACCELEROMETER),
            SensorManager.SENSOR_DELAY_FASTEST)

        textPaint.color = Color.BLACK
        textPaint.textSize = 80f
        textPaint.isAntiAlias = true

        gameView.post {
            run{
                bird = Bird(width / 2.0, bird.posYIni, 1.0)
                cloud = Cloud(width / 2.0, height.toDouble(), -10.0)
                randomStartPosXBomb = (100..width - 100).random()
            }
        }
        //Here we initialize our Runnable and put the code that we want to run once we call startAnimation()
        runnable = Runnable {

            bird.updateVelocity()
            cloud.updateVelocity()
            invalidate()

            //Calling post() inside here will loop the above code and you will see a smooth animation
            post(runnable)

        }
        startAnimation()
    }

    override fun onFragmentInteraction(uri: Uri) {
    }

    override fun onAccuracyChanged(p0: Sensor?, p1: Int) {
    }

    override fun onSensorChanged(p0: SensorEvent?) {
        val positionZ = (p0!!.values[0]*-1)
        if(positionZ < -1){
            bird.updatePosX(-2)
        }else if(positionZ > 1){
            bird.updatePosX(2)

        }
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        canvas.apply {

            drawBitmap(birdImageFinal, bird.posx.toFloat() - (birdImageFinal.width /2),bird.posy.toFloat(),null)
            drawBitmap(cloudImage,cloud.posx.toFloat()-(cloudImage.width / 2),cloud.posy.toFloat(),null)

            drawText(counterScore.toString(),
                width - 240f,
                200f,
                textPaint
            )

            if(counterFpsRocketVisibility >= 1000 ){
                rocket.visibility = true

                if(rocket.posx < width && rocket.visibility){
                    //ver si se puede poner en otro lugar donde se ejecute menos
                    bomb.getRandomPosX(width)
                    rocket.updatePosX()
                    drawBitmap(rocketImageScaled,rocket.posx,rocket.posy,null)
                    bomb.visibility = true

                }
                if(rocket.posx > bomb.posx && bomb.visibility){

                    bombSound.start()
                    drawBitmap(bombImageScaled,bomb.posx,bomb.posy,null)
                    bomb.updatePosY()

                    if (bomb.posy > height){
                        bombSound.stop()
                        bombSound.prepareAsync()
                        crashBomb.start()
                        rocket.restoreToPosXIni()
                        bomb.restoreToPosYIni()
                        bomb.visibility = false
                        rocket.visibility = false
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


    private fun startAnimation()
    {
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
        if (abs(cloud.posy - bird.posy) <= 150 &&
                bird.posx >= (cloud.posx - birdImageFinal.width / 2) &&
                bird.posx <= (cloud.posx + cloudImage.width - (birdImageFinal.width / 2))){
            bird.velocity *= -1
            bird.velocity -= 0.5

            collisionSoundBirdCloud.start()
            cloud.posy = height + 130.0
            cloud.posx =  (0..width).random().toDouble()
        }
    }

    private fun checkCollisionBirdBomb(){
            if(bomb.visibility) {
                if (bird.posx + birdImageFinal.width / 2 - 100 >= bomb.posx &&
                         bird.posx - birdImageFinal.width / 2 + 100 <= bomb.posx + bombImageScaled.width ){

                    if(bird.posy <= bomb.posy + bombImageScaled.height - 100 &&
                            bird.posy >= bomb.posy - birdImageFinal.height + 100){
                        bombSound.stop()
                        bombSound.prepareAsync()
                        stopAnimation()
                        onLoseListener.invoke()
                    }

                }
            }
    }

    private fun checkLoser() {

        if (bird.posy >= height) {
            stopAnimation()
            onLoseListener.invoke()

        }else{
            counterScore += 1
        }
    }

    fun returnScore(): String{
        return counterScore.toString()
    }


}

