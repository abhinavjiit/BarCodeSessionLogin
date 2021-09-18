package com.example.barcodesessionlogin.di.sessionend

import com.example.barcodesessionlogin.domain.BarCodeScannerSessionEndRepository
import com.example.barcodesessionlogin.domain.BarCodeScannerSessionEndRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent

@InstallIn(ViewModelComponent::class)
@Module
abstract class BarCodeSessionEndModule {

    @Binds
    abstract fun providesSessionEndRepository(barCodeScannerSessionEndRepositoryImpl: BarCodeScannerSessionEndRepositoryImpl): BarCodeScannerSessionEndRepository

}