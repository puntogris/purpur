package com.puntogris.purpur.models

import javax.inject.Inject

class Cloud @Inject constructor(var posx: Double, var posy: Double , var velocity: Double){

    fun move(){
        posy -= velocity
    }

    fun resetPosition(height: Int, width:Int){
        posx =  (0..width).random().toDouble()
        posy = height + 130.0
    }

    fun setInitialPosition(width: Int, height: Int){
        posx = width / 2.0
        posy = height.toDouble()
    }

    fun resetValues(){
        posx = 0.0
        posy = 500.0
        velocity = 10.0
    }

}
