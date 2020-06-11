package com.puntogris.purpur.di

import android.content.Context
import com.puntogris.purpur.models.Bird
import com.puntogris.purpur.models.Bomb
import com.puntogris.purpur.models.Cloud
import com.puntogris.purpur.models.Rocket
import com.puntogris.purpur.utils.EnvironmentDrawer
import com.puntogris.purpur.utils.EnvironmentMusic
import com.puntogris.purpur.utils.GameEnvironment
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
