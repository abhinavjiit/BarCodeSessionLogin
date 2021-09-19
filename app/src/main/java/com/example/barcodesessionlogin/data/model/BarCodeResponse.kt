package com.example.barcodesessionlogin.data.model

data class BarCodeResponse(
    val location_id: String? = null,
    val location_detail: String? = null,
    val price_per_min: Float = 0.0f,
    val time_spent: Int = 0,
    val end_time: Long = 0
)
