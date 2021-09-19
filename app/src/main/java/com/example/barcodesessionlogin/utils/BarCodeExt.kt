package com.example.barcodesessionlogin.utils

import android.os.SystemClock
import android.view.View
import java.text.SimpleDateFormat
import java.util.*

fun Long.calculateTotalPrice(pricePerMin: Float): Float {
    return try {
        val totalTime = (this - BarCodeScannerSharedPref.getStartTime()?.toLong()!!).div(1000)
        val totalPrice = totalTime.div(60)
        (totalPrice * pricePerMin)
    } catch (e: Exception) {
        0F
    }
}

fun Long.calculateTotalTimeElapsedTillNow(): Long {
    val totalTime = (this - BarCodeScannerSharedPref.getStartTime()?.toLong()!!).div(1000)
    return SystemClock.elapsedRealtime() - (totalTime * 1000)
}

fun Long.calculateTotalSpent(values: (Int, Long) -> Unit) {
    val totalTime = (this - BarCodeScannerSharedPref.getStartTime()?.toLong()!!).div(1000).toInt()
    values(totalTime.div(60), this)
}

fun getHumanReadableDate(epochSec: Long, dateFormatStr: String?): String {
    val date = Date(epochSec * 1000)
    val format = SimpleDateFormat(
        dateFormatStr,
        Locale.getDefault()
    )
    return format.format(date)
}

fun View.show() {
    visibility = View.VISIBLE
}

fun View.hide() {
    visibility = View.GONE
}

