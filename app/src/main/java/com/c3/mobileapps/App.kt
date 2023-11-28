package com.c3.mobileapps

import android.app.Application
import com.c3.mobileapps.di.KoinModule.apiModule
import com.c3.mobileapps.di.KoinModule.remoteModule
import com.c3.mobileapps.di.KoinModule.viewModelModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@App)
            modules(
                listOf(
                    apiModule,
                    remoteModule,
                    viewModelModule
                )
            )
        }
    }
}