package com.puntogris.purpur


class Bird(var posx: Double,var  posy :Double,var velocity: Double){

    val posYIni = 500.0

    fun updatePosX(dist: Int){
        posx += dist
    }

    fun updateVelocity(){
        velocity += 0.5
        posy += velocity

    }

}

class Cloud(var posx: Double, var posy: Double, var velocity: Double){

    fun updateVelocity(){
        posy += velocity
    }
}

class Rocket(var posx : Float, var posy: Float, var visibility: Boolean){

    fun restoreToPosXIni(){
        posx = -700f
    }

    fun updatePosX(){
        posx += 7
    }

}


class Bomb(var posx : Float, var posy: Float, var visibility: Boolean){

    fun updatePosY(){
        posy += 7
    }

    fun restoreToPosYIni(){
        posy = 200f
    }
    fun getRandomPosX(width: Int){
        if(!visibility){
            posx = (100..width - 100).random().toFloat()
        }

    }
}


