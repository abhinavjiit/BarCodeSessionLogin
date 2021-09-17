package com.example.barcodesessionlogin.application

import android.app.Application
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import com.example.barcodesessionlogin.BarCodeScannerSharedPref
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class BaseApplication : Application(), LifecycleEventObserver {

    companion object {
        @JvmField
        var appContext: BaseApplication? = null

        fun setInstance(application: BaseApplication) {
            appContext = application
        }

        @JvmStatic
        fun getInstance(): BaseApplication {
            return appContext as BaseApplication
        }
    }

    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        BarCodeScannerSharedPref.getSharedPref(this)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_STOP -> {
            }
            Lifecycle.Event.ON_START -> {
            }
            else -> {
            }
        }
    }

}