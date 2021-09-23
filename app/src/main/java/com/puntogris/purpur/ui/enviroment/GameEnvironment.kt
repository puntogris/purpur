package com.puntogris.purpur.ui.enviroment


import android.graphics.Canvas
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.puntogris.purpur.models.Bird
import com.puntogris.purpur.models.Bomb
import com.puntogris.purpur.models.Cloud
import com.puntogris.purpur.models.Rocket
import javax.inject.Inject
import kotlin.math.abs

class GameEnvironment @Inject constructor(private val drawer: EnvironmentDrawer, private val music: EnvironmentMusic) {

    val bird = Bird(-200.0 , -200.0 ,1.0)
    val cloud = Cloud(-200.0,-200.0,10.0)
    private val bomb = Bomb(-500F,200F, false)
    private val rocket = Rocket(-700F,200F,false)

    private fun imageWidth() = drawer.birdImageFinal.width
    private fun imageHeight() = drawer.birdImageFinal.height
    private var height = 0
    private var width = 0
    private var _counterScore = MutableLiveData(0)
    val counterScore: LiveData<Int> = _counterScore

    fun updateScore(){
        _counterScore.value = counterScore.value!! + 1
    }

    fun setDimensEnvironment(height: Int, width: Int){
        this.height = height
        this.width = width
        bird.resetValues(width)
        cloud.setInitialPosition(width,height)
    }

    fun checkCollisionBirdBomb(): Boolean{
        if(bomb.isVisible) {
            if (
                bird.posx + imageWidth()/ 2 - 100 >= bomb.posx &&
                bird.posx - imageWidth() / 2 + 100 <= bomb.posx + drawer.bombImageScaled.width ){
                if(
                    bird.posy <= bomb.posy + drawer.bombImageScaled.height - 100 &&
                    bird.posy >= bomb.posy - imageHeight() + 100){
                    music.bombSound.stop()
                    music.bombSound.prepare()
                    return true
                }
            }
        }
        return false
    }

    fun checkCollisionBirdCloud(){
        if (
            abs(cloud.posy - bird.posy) <= 150 &&
            bird.posx >= (cloud.posx - imageWidth() / 2) &&
            bird.posx <= (cloud.posx + drawer.cloudImage.width - (imageWidth() / 2))
        ) {
            bird.updateVelocityOnCollision()
            music.collisionSoundBirdCloud.start()
            cloud.resetValues(width, height)
        }
    }

    private fun explodeSequence(){
        music.apply {
            bombSound.stop()
            bombSound.prepare()
            crashBomb.start()
        }
        bomb.resetValues()
        rocket.resetValues()
    }

    fun rocketManager(canvas: Canvas){
        if(rocket.timeToLaunch()){
            rocket.visible()
            if(rocket.inScreen(width)){
                drawer.draw(canvas, rocket)
                rocket.move()
                bomb.drop(width)
            }
            if(bomb.inScreen(rocket)){
                checkCollisionBirdBomb()
                drawer.draw(canvas,bomb)
                music.bombSound.start()
                bomb.move()
                if (bomb.outOfScreen(height)) explodeSequence()
            }
        }
    }

    fun changeBirdAnimation(){
        with(bird){
            if (animationCounter >= 15){
                drawer.birdImageFinal =
                    if(drawer.birdImageFinal == drawer.birdImage1) drawer.birdImage2 else drawer.birdImage1
                animationCounter = 0
            }
            animationCounter += 1
        }
    }

    fun draw(canvas: Canvas){
        drawer.apply {
            draw(canvas, bird)
            draw(canvas, cloud)
            draw(canvas, counterScore.value.toString(), width)
        }
    }

    fun reset(){
        _counterScore.value = 0
        bird.resetValues(width)
        bomb.resetValues()
        rocket.resetValues()
        cloud.setInitialPosition(width, height)
    }
}
