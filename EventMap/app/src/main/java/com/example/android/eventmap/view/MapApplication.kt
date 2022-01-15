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
    private lateinit var INSTANCE: MapApplication

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
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