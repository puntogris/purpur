package com.puntogris.purpur.models

import javax.inject.Inject

class Bird @Inject constructor(var posx: Double,var posy: Double,var velocity: Double){

    var animationCounter = 0

    fun updateVelocity(){
        velocity += 0.5
        posy += velocity
    }

    fun updateVelocityOnCollision(){
        velocity *= -1
        velocity -= 0.5
    }

    fun resetValues(width: Int){
        posx = width / 2.toDouble()
        posy = 500.0
        velocity = 1.0
    }

    fun moveLeft(){
        posx -= 2
    }
    fun moveRight(){
        posx += 2
    }

}