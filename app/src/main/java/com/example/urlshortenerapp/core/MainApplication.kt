package com.example.urlshortenerapp.core

import android.app.Application
import com.example.urlshortenerapp.di.MainAppDI
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

class MainApplication: Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@MainApplication)
            modules(MainAppDI.modules)
        }
    }
}