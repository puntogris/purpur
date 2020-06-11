package com.puntogris.purpur.models

import javax.inject.Inject

class Bomb @Inject constructor(var posx : Float, var posy: Float, var visibility: Boolean){

    fun move(){
        posy += 7
    }
    fun getRandomPosX(width: Int){
        if(!visibility) posx = (100..width - 100).random().toFloat()
    }
    fun visible(){
        visibility = true
    }
    fun hide(){
        visibility = false
    }
    fun outOfScreen(height: Int) = posy > height

    fun inScreen(rocket: Rocket) = rocket.posx > posx && visibility

    fun resetValues(){
        posx = -500F
        posy = 200F
        hide()
    }
    fun drop(width: Int){
        getRandomPosX(width)
        visible()
    }
}
