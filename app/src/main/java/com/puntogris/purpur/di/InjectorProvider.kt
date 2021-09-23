package com.puntogris.purpur.di

import com.puntogris.purpur.ui.enviroment.MusicService
import com.puntogris.purpur.ui.main.GameView

interface InjectorProvider{
    val component: AppComponent
}

val GameView.injector get() = (context.applicationContext as InjectorProvider).component
val MusicService.injector get() = (applicationContext as InjectorProvider).component
