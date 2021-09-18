package com.example.barcodesessionlogin.domain

import com.example.barcodesessionlogin.data.model.BarCodeResponse
import com.example.barcodesessionlogin.utils.IResult
import kotlinx.coroutines.flow.Flow

interface BarCodeScannerSessionEndRepository {
    suspend fun postSessionEndData(barCodeResponse: BarCodeResponse): Flow<IResult<Int>>
}