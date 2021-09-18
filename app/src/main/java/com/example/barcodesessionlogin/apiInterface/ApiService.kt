package com.example.barcodesessionlogin.apiInterface

import com.example.barcodesessionlogin.data.model.BarCodeResponse
import okhttp3.ResponseBody
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.POST

interface ApiService {

    @POST("submit-session")
    suspend fun postSessionEnd(@Body barCodeResponse: BarCodeResponse): Response<ResponseBody>
}