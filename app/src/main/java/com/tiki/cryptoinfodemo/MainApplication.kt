package com.tiki.cryptoinfodemo

import android.app.Application
import com.tiki.cryptoinfodemo.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.startKoin

class MainApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            // Log Koin into Android logger
            androidLogger()
            // Reference Android context
            androidContext(this@MainApplication)
            modules(appModule)
        }
    }
}