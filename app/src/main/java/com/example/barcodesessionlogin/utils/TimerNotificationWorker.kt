package com.example.barcodesessionlogin.utils

import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.os.Build
import android.os.SystemClock
import android.widget.RemoteViews
import androidx.core.app.NotificationCompat
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import com.example.barcodesessionlogin.R
import kotlinx.coroutines.delay

class TimerNotificationWorker(context: Context, params: WorkerParameters) : CoroutineWorker(context, params) {

    private var locationId: String? = ""
    private var locationDetail: String? = ""
    private var pricePerMin = 0.0F
    private val mContext = context

    override suspend fun doWork(): Result {
        val totalTime = (System.currentTimeMillis() - BarCodeScannerSharedPref.getStartTime()?.toLong()!!).div(1000)
        inputData.apply {
            locationDetail = this.getString("locationDetail")
            locationId = this.getString("locationId")
            pricePerMin = this.getFloat("pricePerMin", 0.0F)
        }
        setForeground(sendNotification(totalTime = totalTime))
        delay(900002L)
        return Result.success()
    }

    private fun sendNotification(totalTime: Long): ForegroundInfo {

        val customView = RemoteViews(mContext.packageName, R.layout.custom_timer_notification)
        customView.setChronometer(R.id.noteChronometer, SystemClock.elapsedRealtime() - (totalTime * 1000), null, true)
        customView.setTextViewText(R.id.locationId, "LocationId - ".plus(locationId ?: ""))
        customView.setTextViewText(R.id.locationDetail, "Location Details - ".plus(locationDetail ?: ""))
        customView.setTextViewText(R.id.pricePerMin, "Price/Min - ".plus(pricePerMin.toString()))

        val notificationManager = mContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channel = NotificationChannel("default", "Default", NotificationManager.IMPORTANCE_HIGH)
            notificationManager.createNotificationChannel(channel)
        }
        val notification: NotificationCompat.Builder = NotificationCompat.Builder(mContext, "default")
            .setContentTitle("BarCodeScanner")
            .setOngoing(true)
            .setSmallIcon(R.mipmap.ic_launcher)
            .setCustomBigContentView(customView)
        return ForegroundInfo(0, notification.build())
    }
}

