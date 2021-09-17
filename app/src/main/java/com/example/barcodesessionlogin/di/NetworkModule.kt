package com.example.barcodesessionlogin.di

import android.content.Context
import com.example.barcodesessionlogin.BarCodeScannerSharedPref
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class NetworkModule {

    @Singleton
    @Provides
    fun providesOkHttp() = OkHttpClient.Builder().build()

    @Singleton
    @Provides
    fun providesRetrofit(client: OkHttpClient, gsonConverterFactory: GsonConverterFactory): Retrofit = Retrofit.Builder()
        .baseUrl("https://en478jh796m7w.x.pipedream.net/")
        .addConverterFactory(gsonConverterFactory)
        .client(client)
        .build()

    @Singleton
    @Provides
    fun gson(): Gson = GsonBuilder().setLenient().create()

    @Singleton
    @Provides
    fun gsonConverterFactory(gson: Gson): GsonConverterFactory = GsonConverterFactory.create(gson)

}