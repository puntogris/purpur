package com.puntogris.purpur.di

import android.content.Context
import android.content.SharedPreferences
import com.puntogris.purpur.models.Bird
import com.puntogris.purpur.models.Bomb
import com.puntogris.purpur.models.Cloud
import com.puntogris.purpur.models.Rocket
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
object GameModule {
    @JvmStatic @Provides @Singleton
    fun getRocket (context: Context) = Rocket(context = context)

    @JvmStatic @Provides @Singleton
    fun getCloud (context: Context) = Cloud(context = context)

    @JvmStatic @Provides @Singleton
    fun getBomb (context: Context) = Bomb(context = context)

    @JvmStatic @Provides @Singleton
    fun getBird (context: Context) = Bird(context = context)

}


