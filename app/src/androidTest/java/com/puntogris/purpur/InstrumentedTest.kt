package com.puntogris.purpur

import androidx.test.core.app.ActivityScenario
import androidx.test.internal.runner.junit4.AndroidJUnit4ClassRunner
import org.junit.Test
import androidx.test.espresso.Espresso.onView
import androidx.test.espresso.assertion.ViewAssertions.matches
import androidx.test.espresso.matcher.ViewMatchers.*
import com.puntogris.purpur.ui.main.MainActivity
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4ClassRunner::class)
class InstrumentedTest {

    val activityScenario: ActivityScenario<MainActivity> = ActivityScenario.launch(MainActivity::class.java)

    @Test
    fun is_mainActivity_layout_inflated(){
        onView(withId(R.id.constraintLayout)).check(matches(isDisplayed()))
    }

    @Test
    fun is_postGame_layout_visible(){
        onView(withId(R.id.postGameGroupView)).check(matches(isDisplayed()))
        onView(withId(R.id.playerScore)).check(matches(isDisplayed()))
        onView(withId(R.id.playButton)).check(matches(isDisplayed()))
    }

}
