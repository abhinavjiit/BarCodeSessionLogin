package com.example.barcodesessionlogin.utils

import android.util.Log
import org.json.JSONObject

object Validator {

    fun validateBarCodeIdToLogout(currentId: String?, scannedId: String?): Boolean {
        if (currentId.isNullOrBlank() || scannedId.isNullOrBlank()) {
            return false
        }
        return currentId == scannedId
    }

    fun validateSessionOutData(locationId: String?, timeSpent: Int, endTime: Long): Boolean {
        return when {
            endTime <= 0 -> {
                false
            }
            locationId.isNullOrBlank() -> {
                false
            }
            timeSpent < 0 -> {
                false
            }
            else -> true
        }
    }

    fun validateBarCodeJsonResponse(jsonString: String): Boolean {
        val jsonObject = JSONObject(jsonString)
        try {
            if (jsonObject.has("location_id") && jsonObject.has("location_detail") && jsonObject.has("price_per_min")) {
                val locationId: String? = jsonObject.getString("location_id")
                val locationDetail: String? = jsonObject.getString("location_detail")
                val pricePerMin = jsonObject.getDouble("price_per_min")

                if (locationId.isNullOrBlank() || locationId == "null") {
                    return false
                } else if (locationDetail.isNullOrBlank() || locationDetail == "null") {
                    return false
                } else if (pricePerMin <= 0) {
                    return false
                }
                return true

            } else {
                return false
            }
        } catch (e: Exception) {
            Log.i("TAG", "validateBarCodeJsonResponse: $e")
            return false
        }
    }

}