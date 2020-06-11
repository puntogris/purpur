package com.puntogris.purpur.di

import android.content.Context
import com.puntogris.purpur.models.Bird
import com.puntogris.purpur.models.Bomb
import com.puntogris.purpur.models.Cloud
import com.puntogris.purpur.models.Rocket
import com.puntogris.purpur.utils.EnvironmentDrawer
import com.puntogris.purpur.utils.GameEnvironment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [GameModule::class])
interface AppComponent {
    val rocket: Rocket
    val cloud: Cloud
    val bird: Bird
    val bomb: Bomb
    val gameEnvironment: GameEnvironment
    val drawer:EnvironmentDrawer

    @Component.Factory
    interface Factory{
        fun create(
            @BindsInstance applicationContext: Context
        ): AppComponent
    }
}