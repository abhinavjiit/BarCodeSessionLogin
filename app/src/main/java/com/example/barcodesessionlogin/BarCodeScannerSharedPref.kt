package com.example.barcodesessionlogin

import android.content.Context
import android.content.SharedPreferences

object BarCodeScannerSharedPref {

    private const val IS_ACTIVE = "isActive"

    private lateinit var appPref: SharedPreferences

    private lateinit var prefEditor: SharedPreferences.Editor

    fun getSharedPref(context: Context) {
        appPref = context.getSharedPreferences("BarCode", Context.MODE_PRIVATE)
    }

    fun setUserIsActive(isActive: Boolean) {
        appPref.edit().putBoolean(IS_ACTIVE, isActive).apply()
    }

    fun isActive() = appPref.getBoolean(IS_ACTIVE, false)

}