package com.puntogris.purpur.models

import com.puntogris.purpur.R

class Bird(var posx: Double = 0.0 ,var posy: Double = 500.0 ,var velocity: Double = 1.0){

    val birdImage1 = R.raw.bird1
    val birdImage2 = R.raw.bird2

    fun updatePosX(dist: Int){
        posx += dist
    }

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


}