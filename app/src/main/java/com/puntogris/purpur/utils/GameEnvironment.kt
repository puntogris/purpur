package com.puntogris.purpur.utils


import android.graphics.Canvas
import com.puntogris.purpur.models.Bird
import com.puntogris.purpur.models.Bomb
import com.puntogris.purpur.models.Cloud
import com.puntogris.purpur.models.Rocket
import javax.inject.Inject
import kotlin.math.abs

class GameEnvironment @Inject constructor(val cloud: Cloud, val rocket: Rocket, val bomb: Bomb, val bird: Bird, val drawer: EnvironmentDrawer) {

    private fun imageWidth() = drawer.birdImageFinal.width
    private fun imageHeight() = drawer.birdImageFinal.height
    private var height = 0
    private var width = 0
    private var counterScore: Int = 0

    fun returnScore() = counterScore.toString()

    fun updateScore(){
        counterScore += 1
    }

    fun setDimensEnvironment(height: Int, width: Int){
        this.height = height
        this.width = width
        bird.setInitialPosition(width)
        cloud.setInitialPosition(width,height)    }

    fun checkCollisionBirdBomb(): Boolean{
        if(bomb.visibility) {
            if (
                bird.posx + imageWidth()/ 2 - 100 >= bomb.posx &&
                bird.posx - imageWidth() / 2 + 100 <= bomb.posx + drawer.bombImageScaled.width ){
                if(
                    bird.posy <= bomb.posy + drawer.bombImageScaled.height - 100 &&
                    bird.posy >= bomb.posy - imageHeight() + 100){
                    drawer.bombSound.stop()
                    drawer.bombSound.prepareAsync()
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
            bird.posx <= (cloud.posx + drawer.cloudImage.width - (imageWidth() / 2))) {
            bird.updateVelocityOnCollision()
            drawer.collisionSoundBirdCloud.start()
            cloud.resetPosition(height,width)
        }
    }

    private fun explodeSequence(){
        drawer.apply {
            bombSound.stop()
            bombSound.prepareAsync()
            crashBomb.start()
        }
        bomb.restoreToPosYIni()
        bomb.hide()
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
                drawer.bombSound.start()
                bomb.move()
                if (bomb.outOfScreen(height)){
                    explodeSequence()
                }
            }
        }
    }

    fun changeBirdAnimation(){
        if (bird.animationCounter >= 15){
            drawer.birdImageFinal = if( drawer.birdImageFinal ==  drawer.birdImage1){
                drawer.birdImage2
            }else{
                drawer.birdImage1
            }
            bird.animationCounter = 0
        }
        bird.animationCounter += 1
    }

    fun draw(canvas: Canvas){
        drawer.apply {
            draw(canvas,bird)
            draw(canvas,cloud)
            draw(canvas, counterScore.toString(), width)
        }
    }

    fun reset(){
        bird.resetValues()
        bomb.resetValues()
        rocket.resetValues()
        cloud.resetValues()
    }
}
