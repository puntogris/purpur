package com.puntogris.purpur.models

class Rocket(var posx : Float, var posy: Float , var isVisibility: Boolean){

    var launchCounter = 0
    private val limitToLaunch = 700

    fun move(){
        posx += 7
    }

    fun visible() {
        isVisibility = true
    }
    fun hide(){
        isVisibility = false
    }
    fun inScreen(width: Int)= posx < width && isVisibility

    fun resetValues(){
        posx = -700F
        posy = 200F
        hide()
        launchCounter = 0
    }

    fun timeToLaunch():Boolean{
        launchCounter += 1
        return launchCounter >= limitToLaunch
    }

}




