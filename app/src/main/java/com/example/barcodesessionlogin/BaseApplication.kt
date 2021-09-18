package com.example.barcodesessionlogin

import android.app.Application
import android.app.NotificationManager
import android.content.Context
import android.widget.Toast
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleEventObserver
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ProcessLifecycleOwner
import androidx.work.Data
import androidx.work.ExistingPeriodicWorkPolicy
import androidx.work.PeriodicWorkRequest
import androidx.work.WorkManager
import com.example.barcodesessionlogin.utils.BarCodeScannerSharedPref
import com.example.barcodesessionlogin.utils.TimerNotificationWorker
import dagger.hilt.android.HiltAndroidApp
import java.util.concurrent.TimeUnit

@HiltAndroidApp
class BaseApplication : Application(), LifecycleEventObserver {

    private lateinit var workManager: WorkManager

//    companion object {
//        @JvmField
//        var appContext: BaseApplication? = null
//
//        fun setInstance(application: BaseApplication) {
//            appContext = application
//        }
//
//        @JvmStatic
//        fun getInstance(): BaseApplication {
//            return appContext as BaseApplication
//        }
//    }

    override fun onCreate() {
        super.onCreate()
        ProcessLifecycleOwner.get().lifecycle.addObserver(this)
        BarCodeScannerSharedPref.getSharedPref(this)
    }

    override fun onStateChanged(source: LifecycleOwner, event: Lifecycle.Event) {
        when (event) {
            Lifecycle.Event.ON_STOP -> {
                if (BarCodeScannerSharedPref.isActive())
                    startWorkManager()
            }
            Lifecycle.Event.ON_START -> {
                cancelWorkManger()
            }
            else -> {
            }
        }
    }

    private fun startWorkManager() {
        val inputData = Data.Builder()
        BarCodeScannerSharedPref.getBarCodeData()?.let {
            inputData.apply {
                putString("locationId", it.location_id)
                putFloat("pricePerMin", it.price_per_min)
                putString("locationDetail", it.location_details)
            }
        }

        val periodicWorkRequest =
            PeriodicWorkRequest.Builder(TimerNotificationWorker::class.java, 15, TimeUnit.MINUTES)
                .setInputData(inputData.build())
                .setInitialDelay(100, TimeUnit.MILLISECONDS)
                .build()

        workManager = WorkManager.getInstance(this)
        workManager.enqueueUniquePeriodicWork("Timer", ExistingPeriodicWorkPolicy.REPLACE, periodicWorkRequest)
    }

    private fun cancelWorkManger() {
        if (::workManager.isInitialized) {
            workManager.cancelUniqueWork("Timer")
            (getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager).cancel(0)
        }
    }

    override fun onTerminate() {
        super.onTerminate()
        Toast.makeText(this, "jkadbfiuadfbe", Toast.LENGTH_LONG).show()
    }

}