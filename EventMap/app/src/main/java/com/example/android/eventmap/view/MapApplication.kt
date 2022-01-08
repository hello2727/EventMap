package com.example.android.eventmap.view

import android.app.Application
import android.content.res.Configuration
import dagger.hilt.android.HiltAndroidApp

/**
 * Created by Jihye Noh
 * Date: 2022-01-04
 */
@HiltAndroidApp
class MapApplication : Application() {
    override fun onCreate() {
        super.onCreate()
    }

    override fun onTerminate() {
        super.onTerminate()
    }

    override fun onLowMemory() {
        super.onLowMemory()
    }

    override fun onConfigurationChanged(newConfig: Configuration) {
        super.onConfigurationChanged(newConfig)
    }
}