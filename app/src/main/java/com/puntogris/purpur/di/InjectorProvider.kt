package com.puntogris.purpur.di

import com.puntogris.purpur.ui.GameView

interface InjectorProvider{
    val component: AppComponent
}

val GameView.injector get() = (context.applicationContext as InjectorProvider).component

