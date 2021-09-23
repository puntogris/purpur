package com.puntogris.purpur.di

import android.content.Context
import com.puntogris.purpur.ui.enviroment.EnvironmentDrawer
import com.puntogris.purpur.ui.enviroment.EnvironmentMusic
import com.puntogris.purpur.ui.enviroment.GameEnvironment
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object GameModule {

    @JvmStatic @Provides
    fun getGameEnvironment (drawer: EnvironmentDrawer, music: EnvironmentMusic) = GameEnvironment(drawer, music)

    @JvmStatic @Provides @Singleton
    fun getDrawer (context: Context) = EnvironmentDrawer(context)

    @JvmStatic @Provides @Singleton
    fun getMusic (context: Context) = EnvironmentMusic(context)

}
