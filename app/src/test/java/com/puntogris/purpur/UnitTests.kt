package com.puntogris.purpur

import com.puntogris.purpur.models.Bird
import com.puntogris.purpur.models.Bomb
import com.puntogris.purpur.models.Cloud
import com.puntogris.purpur.models.Rocket
import org.amshove.kluent.*
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test


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
            cloud.posy.`should be equal to`(-10.0)
        }

        @Test
        fun `does cloud set initial position`(){
            cloud.setInitialPosition(widthScreen,heightScreen)
            cloud.posx.`should be equal to`(widthScreen / 2.toDouble())
            cloud.posy.`should be equal to`(heightScreen.toDouble())
        }

        @Test
        fun `reset cloud to initial values`(){
            cloud.resetValues()
            cloud.posy.`should be equal to`(500.0)
            cloud.posx.`should be equal to`(0.0)
            cloud.velocity.`should be equal to`(10.0)
        }

        @Test
        fun `reset cloud to random position`(){
            cloud.resetPosition(heightScreen,widthScreen)
            cloud.posx.`should be in range`(0.0, widthScreen.toDouble())
            cloud.posy.`should be equal to`(heightScreen + 130.0)
        }
    }
    @Nested
    inner class BombTests{

        @Test
        fun `update bomb position`(){
            bomb.move()
            bomb.posy.`should be equal to`(7f)
        }

        @Test
        fun `restore to initial position`(){
            bomb.posy = 421f
            bomb.restoreToPosYIni()
            bomb.posy.`should be equal to`(200f)
        }

        @Test
        fun `should hide bomb`(){
            bomb.visibility = true
            bomb.hide()
            bomb.visibility.`should be equal to`(false)
        }

        @Test
        fun `should show bomb`(){
            bomb.visibility = false
            bomb.visible()
            bomb.visibility.`should be equal to`(true)
        }

        @Test
        fun `is bomb out of the screen`(){
            bomb.outOfScreen(heightScreen).`should be`(false)
            bomb.outOfScreen(Int.MIN_VALUE).`should be`(true)
            bomb.outOfScreen(0).`should be`(false)
        }

        @Test
        fun `is bomb in the screen`(){
            bomb.visibility = true
            rocket.posx = 600f
            bomb.posx = 500f
            bomb.inScreen(rocket).`should be equal to`(true)
            bomb.visibility = false
            bomb.inScreen(rocket).`should be equal to`(false)
            rocket.posx = 400f
            bomb.visibility = true
            bomb.inScreen(rocket).`should be equal to`(false)

        }

        @Test
        fun `should set random pos x for bomb`(){
            bomb.visibility = false
            bomb.getRandomPosX(widthScreen)
            bomb.posx.`should be in range`(100f, widthScreen - 100f)
        }
        @Test
        fun `should reset the bomb values`(){
            bomb.resetValues()
            bomb.should {
                posx.`should be equal to`(-500f)
                posy.`should be equal to`(200f)
                visibility.`should be equal to`(false)
                true
            }
        }
        @Test
        fun `should show the bomb and update x position`(){
            bomb.drop(widthScreen)
            bomb.visibility.`should be equal to`(true)
        }


    }
    @Nested
    inner class RocketTests{

        @Test
        fun `should move rocket`(){
            rocket.move()
            rocket.posx.`should be equal to`(7f)
        }

        @Test
        fun `should show the rocket`(){
            rocket.visibility = false
            rocket.visible()
            rocket.visibility.`should be equal to`(true)
        }
        @Test
        fun `should hide the rocket`(){
            rocket.visibility = true
            rocket.hide()
            rocket.visibility.`should be equal to`(false)
        }
        @Test
        fun `is rocket in the screen`(){
            rocket.posx = -200f
            rocket.visibility = false
            rocket.inScreen(widthScreen).`should be equal to`(false)
            rocket.posx = -200f
            rocket.visibility = true
            rocket.inScreen(widthScreen).`should be equal to`(true)
        }
        @Test
        fun `should reset rocket values and hide it`(){
            rocket.launchCounter = 400
            rocket.resetValues()
            rocket.should {
                posx.`should be equal to`(-700f)
                posy.`should be equal to`(200f)
                launchCounter.`should be equal to`(0)
                true
            }
        }

        @Test
        fun `should update rocket counter`(){
            rocket.timeToLaunch()
            rocket.launchCounter.`should be equal to`(1)
            rocket.timeToLaunch().`should be equal to`(false)
            rocket.launchCounter = 800
            rocket.timeToLaunch().`should be equal to`(true)
        }
    }

    @Nested
    inner class BirdTests{

        @Test
        fun `the animation counter should be zero at start`(){
            bird.animationCounter.`should be equal to`(0)
        }

        @Test
        fun `should update bird position and velocity`(){
            bird.updateVelocity()
            bird.velocity.`should be equal to`(0.5)
            bird.posy.`should be equal to`(0.5)
        }
        @Test
        fun `should set initial position`(){
            bird.setInitialPosition(widthScreen)
            bird.posx.`should be equal to`(widthScreen / 2.toDouble())
        }
        @Test
        fun `should update velocity on collision`(){
            val newVelocity = (bird.velocity * -1 ) - 0.5
            bird.updateVelocityOnCollision()
            bird.velocity.`should be equal to`(newVelocity)
        }
        @Test
        fun `should reset all values`(){
            bird.resetValues()
            bird.should {
                posx.`should be equal to`(0.0)
                posy.`should be equal to`(500.0)
                velocity.`should be equal to`(1.0)
                true
            }
        }
        @Test
        fun `should move bird in the x axis`(){
            bird.moveLeft()
            bird.posx.`should be equal to`(-2.0)
            bird.moveRight()
            bird.posx.`should be equal to`(0.0)
        }

    }

}
