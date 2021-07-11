package com.gaps.champion11

import android.content.Context
import android.support.multidex.MultiDexApplication

class BaseApplication : MultiDexApplication() {
    override fun onCreate() {
        super.onCreate()
        appContext = applicationContext
        instance = this
    }



    companion object {
        var appContext: Context? = null
            private set

        @get:Synchronized
        var instance: BaseApplication? = null
            private set
    }
}