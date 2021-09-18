package com.example.barcodesessionlogin.domain

import com.example.barcodesessionlogin.apiInterface.ApiService
import com.example.barcodesessionlogin.data.model.BarCodeResponse
import com.example.barcodesessionlogin.utils.IResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class BarCodeScannerSessionEndRepositoryImpl @Inject constructor(private val apiService: ApiService?) : BarCodeScannerSessionEndRepository {
    override suspend fun postSessionEndData(barCodeResponse: BarCodeResponse): Flow<IResult<Int>> {

        return flow {
            try {
                val response = apiService?.postSessionEnd(barCodeResponse)
                if (response?.isSuccessful == true) {
                    emit(IResult.Success(response.code()))
                }
            } catch (e: Exception) {
                emit(IResult.Error(e))
            }
        }
    }
}