package com.example.forest.base

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class ForestApplication : Application() {
    companion object {
        lateinit var INSTANCE: ForestApplication
    }

    override fun onCreate() {
        super.onCreate()
        INSTANCE = this
    }
}