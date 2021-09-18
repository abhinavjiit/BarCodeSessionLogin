package com.example.barcodesessionlogin

import android.os.SystemClock
import android.view.View
import com.example.barcodesessionlogin.utils.BarCodeScannerSharedPref

fun Long.calculateTotalPrice(pricePerMin: Float): Int {
    return try {
        val totalTime = (this - BarCodeScannerSharedPref.getStartTime()?.toLong()!!).div(1000)
        val totalPrice = totalTime.div(60)
        (totalPrice * pricePerMin).toInt()
    } catch (e: Exception) {
        0
    }
}

fun Long.calculateTotalTimeElapsedTillNow(): Long {
    val totalTime = (this - BarCodeScannerSharedPref.getStartTime()?.toLong()!!).div(1000)
    return SystemClock.elapsedRealtime() - (totalTime * 1000)
}

fun Long.calculateTotalSpent(values: (Int, Long) -> Unit) {
    val totalTime = (this - BarCodeScannerSharedPref.getStartTime()?.toLong()!!).div(1000).toInt()
    values(totalTime, this)
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}