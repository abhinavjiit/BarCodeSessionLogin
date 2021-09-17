package com.example.barcodesessionlogin.ui.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.barcodesessionlogin.ui.data.BarCodeResponse
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class BarCodeScannerViewModel @Inject constructor() : ViewModel() {

    private val _barCodeData = MutableLiveData<BarCodeResponse>()
    val barCodeData: LiveData<BarCodeResponse> = _barCodeData

    private val _loadDetailFragment = MutableLiveData<Boolean>()
    val loadDetailFragment: LiveData<Boolean> = _loadDetailFragment

    private val _loadBarCodeScannerFragment = MutableLiveData<Boolean>()
    val loadBarCodeScannerFragment: LiveData<Boolean> = _loadBarCodeScannerFragment

    fun setBarCodeData(data: BarCodeResponse) {
        _barCodeData.postValue(data)
    }

    fun loadDetailFragment() {
        _loadDetailFragment.postValue(true)
    }

    fun loadBarCodeScannerFragment() {
        _loadBarCodeScannerFragment.postValue(true)
    }

}