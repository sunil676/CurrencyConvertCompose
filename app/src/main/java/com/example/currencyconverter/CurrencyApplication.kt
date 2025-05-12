package com.example.currencyconverter

import androidx.multidex.BuildConfig
import androidx.multidex.MultiDexApplication
import dagger.hilt.android.HiltAndroidApp
import timber.log.Timber

@HiltAndroidApp
class CurrencyApplication : MultiDexApplication() {

    override fun onCreate() {
        super.onCreate()
        // Initialize any libraries or components here
        if (BuildConfig.DEBUG) {
            Timber.plant(Timber.DebugTree())
        }
    }
}