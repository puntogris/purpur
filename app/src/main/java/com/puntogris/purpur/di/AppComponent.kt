package com.puntogris.purpur.di

import android.content.Context
import com.puntogris.purpur.utils.EnvironmentDrawer
import com.puntogris.purpur.utils.EnvironmentMusic
import com.puntogris.purpur.utils.GameEnvironment
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(modules = [GameModule::class])
interface AppComponent {
    val gameEnvironment: GameEnvironment
    val drawer: EnvironmentDrawer
    val musicEnv: EnvironmentMusic

    @Component.Factory
    interface Factory{
        fun create(
            @BindsInstance applicationContext: Context
        ): AppComponent
    }
}