package com.puntogris.purpur.di

import android.content.Context
import com.puntogris.purpur.models.Bird
import com.puntogris.purpur.models.Bomb
import com.puntogris.purpur.models.Cloud
import com.puntogris.purpur.models.Rocket
import com.puntogris.purpur.utils.EnvironmentDrawer
import com.puntogris.purpur.utils.GameEnvironment
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object GameModule {
    @JvmStatic @Provides
    fun getRocket () = Rocket(-700F,200F,false)

    @JvmStatic @Provides
    fun getCloud() = Cloud(500.0,0.0,10.0)

    @JvmStatic @Provides
    fun getBomb () = Bomb(-500F,200F, false)

    @JvmStatic @Provides
    fun getBird () = Bird(0.0 , 500.0 ,1.0)

    @JvmStatic @Provides
    fun getGameEnvironment (cloud: Cloud,rocket: Rocket,bomb: Bomb,bird: Bird, drawer: EnvironmentDrawer) = GameEnvironment(cloud,rocket,bomb,bird,drawer)

    @JvmStatic @Provides @Singleton
    fun getDrawer (context: Context) = EnvironmentDrawer(context)

}


