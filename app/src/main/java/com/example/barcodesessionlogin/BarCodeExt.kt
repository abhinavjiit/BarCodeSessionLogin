package com.example.barcodesessionlogin

import android.os.SystemClock

fun Long.calculateTotalPrice(pricePerMin: Float): Int {
    return try {
        val totalTime = (this - BarCodeScannerSharedPref.getStartTime()?.toLong()!!).div(1000)
        val totalPrice = totalTime.div(60)
        (totalPrice * pricePerMin).toInt()
    } catch (e: Exception) {
        0
    }
}

fun Long.calculateTotalTimeElapsed(): Long {
    val totalTime = (this - BarCodeScannerSharedPref.getStartTime()?.toLong()!!).div(1000)
    return SystemClock.elapsedRealtime() - (totalTime * 1000)
}