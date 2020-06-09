package com.puntogris.purpur

import android.app.Application
import com.puntogris.purpur.di.AppComponent
import com.puntogris.purpur.di.DaggerAppComponent
import com.puntogris.purpur.di.InjectorProvider

class App : Application(), InjectorProvider {
    override  val component: AppComponent by lazy{
        DaggerAppComponent
            .factory()
            .create(applicationContext)
    }
}

