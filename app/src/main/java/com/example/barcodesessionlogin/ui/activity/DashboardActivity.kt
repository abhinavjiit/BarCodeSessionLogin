package com.example.barcodesessionlogin.ui.activity

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.example.barcodesessionlogin.BarCodeScannerSharedPref
import com.example.barcodesessionlogin.R

class DashboardActivity : AppCompatActivity() {

    private val isActiveUSer by lazy { BarCodeScannerSharedPref.isActive() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        if (isActiveUSer) {
            // TODO(call barcodedetailfragment)
        } else {
            //TODO(call barcodescannerfragment)
        }


    }
}