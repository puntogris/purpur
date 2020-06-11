package com.puntogris.purpur.models

import javax.inject.Inject

class Rocket @Inject constructor(var posx : Float, var posy: Float , var visibility: Boolean){

    var launchCounter = 0
    private val limitToLaunch = 700

    fun move(){
        posx += 7
    }

    fun visible() {
        visibility = true
    }
    fun hide(){
        visibility = false
    }
    fun inScreen(width: Int)= posx < width && visibility

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




