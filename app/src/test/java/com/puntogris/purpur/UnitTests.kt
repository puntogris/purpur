package com.puntogris.purpur

import com.puntogris.purpur.models.Bird
import com.puntogris.purpur.models.Bomb
import com.puntogris.purpur.models.Cloud
import com.puntogris.purpur.models.Rocket
import org.amshove.kluent.*
import org.junit.Test
import org.junit.jupiter.api.Nested


class UnitTests {

    private val widthScreen = 700
    private val heightScreen = 1400
    private val bomb = Bomb(0f,0f, false)
    private val cloud = Cloud(0.0,0.0, 10.0)
    private val rocket = Rocket(0f,0f,false)
    private val bird = Bird(0.0,0.0,0.0)

    @Nested
    inner class CloudTests{

        @Test
        fun `does cloud update posy`(){
            cloud.move()
            cloud.posy`should be equal to` -10.0
        }

        @Test
        fun `does cloud set initial position`(){
            cloud.setInitialPosition(widthScreen,heightScreen)
            cloud.posx`should be equal to` widthScreen / 2.toDouble()
            cloud.posy`should be equal to` heightScreen.toDouble()
        }

        @Test
        fun `reset cloud to initial values`(){
            cloud.resetValues(widthScreen,heightScreen)
            cloud.posx.`should be in range`(0.0, widthScreen.toDouble())
            cloud.posy`should be equal to` heightScreen + 130.0
            cloud.velocity`should be equal to` 10.0
        }

    }
    @Nested
    inner class BombTests{

        @Test
        fun `update bomb position`(){
            bomb.move()
            bomb.posy`should be equal to` 7f
        }


        @Test
        fun `should hide bomb`(){
            bomb.isVisible = true
            bomb.hide()
            bomb.isVisible`should be equal to` false
        }

        @Test
        fun `should show bomb`(){
            bomb.isVisible = false
            bomb.visible()
            bomb.isVisible`should be equal to` true
        }

        @Test
        fun `is bomb out of the screen`(){
            bomb.outOfScreen(heightScreen).`should be`(false)
            bomb.outOfScreen(Int.MIN_VALUE).`should be`(true)
            bomb.outOfScreen(0).`should be`(false)
        }

        @Test
        fun `is bomb in the screen`(){
            bomb.isVisible = true
            rocket.posx = 600f
            bomb.posx = 500f
            bomb.inScreen(rocket)`should be equal to` true
            bomb.isVisible = false
            bomb.inScreen(rocket)`should be equal to` false
            rocket.posx = 400f
            bomb.isVisible = true
            bomb.inScreen(rocket)`should be equal to` false

        }

        @Test
        fun `should set random pos x for bomb`(){
            bomb.isVisible = false
            bomb.getRandomPosX(widthScreen)
            bomb.posx.`should be in range`(100f, widthScreen - 100f)
        }
        @Test
        fun `should reset the bomb values`(){
            bomb.resetValues()
            bomb.should {
                posx`should be equal to` -500f
                posy`should be equal to` 200f
                isVisible`should be equal to` false
                true
            }
        }
        @Test
        fun `should show the bomb and update x position`(){
            bomb.drop(widthScreen)
            bomb.isVisible`should be equal to` true
        }
    }
    @Nested
    inner class RocketTests{

        @Test
        fun `should move rocket`(){
            rocket.move()
            rocket.posx`should be equal to` 7f
        }

        @Test
        fun `should show the rocket`(){
            rocket.isVisibility = false
            rocket.visible()
            rocket.isVisibility`should be equal to` true
        }
        @Test
        fun `should hide the rocket`(){
            rocket.isVisibility = true
            rocket.hide()
            rocket.isVisibility`should be equal to` false
        }
        @Test
        fun `is rocket in the screen`(){
            rocket.posx = -200f
            rocket.isVisibility = false
            rocket.inScreen(widthScreen)`should be equal to` false
            rocket.posx = -200f
            rocket.isVisibility = true
            rocket.inScreen(widthScreen)`should be equal to` true
        }
        @Test
        fun `should reset rocket values and hide it`(){
            rocket.launchCounter = 400
            rocket.resetValues()
            rocket.should {
                posx`should be equal to` -700f
                posy`should be equal to` 200f
                launchCounter`should be equal to` 0
                true
            }
        }

        @Test
        fun `should update rocket counter`(){
            rocket.timeToLaunch()
            rocket.launchCounter`should be equal to` 1
            rocket.timeToLaunch()`should be equal to` false
            rocket.launchCounter = 800
            rocket.timeToLaunch()`should be equal to` true
        }
    }

    @Nested
    inner class BirdTests{

        @Test
        fun `the animation counter should be zero at start`(){
            bird.animationCounter`should be equal to` 0
        }

        @Test
        fun `should update bird position and velocity`(){
            bird.updateVelocity()
            bird.velocity`should be equal to` 0.5
            bird.posy`should be equal to` 0.5
        }

        @Test
        fun `should update velocity on collision`(){
            val newVelocity = (bird.velocity * -1 ) - 0.5
            bird.updateVelocityOnCollision()
            bird.velocity`should be equal to` newVelocity
        }
        @Test
        fun `should reset all values`(){
            bird.resetValues(widthScreen)
            bird.should {
                posx`should be equal to` widthScreen / 2.toDouble()
                posy`should be equal to` 500.0
                velocity`should be equal to` 1.0
                true
            }
        }
        @Test
        fun `should move bird in the x axis`(){
            bird.moveLeft()
            bird.posx`should be equal to` -2.0
            bird.moveRight()
            bird.posx`should be equal to` 0.0
        }

    }

}
