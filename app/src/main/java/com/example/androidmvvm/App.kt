package com.example.androidmvvm

import android.app.Application
import timber.log.Timber
import timber.log.Timber.DebugTree


class App : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG)
            Timber.plant(DebugTree())

        com.facebook.stetho.Stetho.initialize(
            com.facebook.stetho.Stetho.newInitializerBuilder(applicationContext)
                .enableDumpapp(
                    com.facebook.stetho.Stetho.defaultDumperPluginsProvider(applicationContext)
                )
                .enableWebKitInspector(
                    com.facebook.stetho.Stetho.defaultInspectorModulesProvider(applicationContext)
                )
                .build()
        )

    }
}