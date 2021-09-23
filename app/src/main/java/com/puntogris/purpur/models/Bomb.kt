package com.puntogris.purpur.models

class Bomb(var posx : Float, var posy: Float, var isVisible: Boolean){

    fun move(){
        posy += 7
    }
    fun getRandomPosX(width: Int){
        if(!isVisible) posx = (100..width - 100).random().toFloat()
    }
    fun visible(){
        isVisible = true
    }
    fun hide(){
        isVisible = false
    }
    fun outOfScreen(height: Int) = posy > height

    fun inScreen(rocket: Rocket) = rocket.posx > posx && isVisible

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
