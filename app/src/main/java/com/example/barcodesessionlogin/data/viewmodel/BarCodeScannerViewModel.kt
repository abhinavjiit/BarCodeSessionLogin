package com.example.barcodesessionlogin.data.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.liveData
import com.example.barcodesessionlogin.Validator
import com.example.barcodesessionlogin.data.model.BarCodeResponse
import com.example.barcodesessionlogin.domain.BarCodeScannerSessionEndRepository
import com.example.barcodesessionlogin.utils.IResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.collect
import javax.inject.Inject

@HiltViewModel
class BarCodeScannerViewModel @Inject constructor(private val barCodeScannerSessionEndRepository: BarCodeScannerSessionEndRepository) : ViewModel() {

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

    fun onSessionEnd(barCodeResponse: BarCodeResponse) = liveData {
        emit(IResult.Loading)
        if (Validator.validateSessionOutData(barCodeResponse.location_id, barCodeResponse.time_spent, barCodeResponse.end_time)) {
            barCodeScannerSessionEndRepository.postSessionEndData(barCodeResponse)
                .catch {
                    emit(IResult.Error(it))
                }.collect {
                    emit(it)
                }
        } else {
            emit(IResult.Error(IllegalArgumentException("SessionOut data is invalid")))
        }

    }

}

