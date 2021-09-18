package com.example.barcodesessionlogin.utils

import android.content.Context
import android.content.SharedPreferences
import com.example.barcodesessionlogin.data.model.BarCodeResponse
import com.google.gson.Gson

object BarCodeScannerSharedPref {

    private const val IS_ACTIVE = "isActive"

    private const val BAR_CODE_RESPONSE = "barCodeResponse"

    private const val START_TIME = "starTime"

    private lateinit var appPref: SharedPreferences

    fun getSharedPref(context: Context) {
        appPref = context.getSharedPreferences("BarCode", Context.MODE_PRIVATE)
    }

    fun setUserIsActive(isActive: Boolean) {
        appPref.edit().putBoolean(IS_ACTIVE, isActive).apply()
    }

    fun isActive() = appPref.getBoolean(IS_ACTIVE, false)

    fun saveBarData(barCodeResponse: BarCodeResponse) {
        Gson().toJson(barCodeResponse).let {
            appPref.edit().putString(BAR_CODE_RESPONSE, it).apply()
        }
    }

    fun getBarCodeData(): BarCodeResponse? {
        return try {
            appPref.getString(BAR_CODE_RESPONSE, "")?.let {
                Gson().fromJson(it, BarCodeResponse::class.java)
            } ?: kotlin.run { null }
        } catch (e: Exception) {
            null
        }
    }

    fun setStartTime() {
        appPref.edit().putString(START_TIME, System.currentTimeMillis().toString()).apply()
    }

    fun getStartTime() = appPref.getString(START_TIME, "")

    fun prefClear() {
        appPref.edit().clear().apply()
    }

}