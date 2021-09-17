package com.example.barcodesessionlogin.ui.activity

import android.os.Bundle
import androidx.activity.viewModels
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.example.barcodesessionlogin.BarCodeScannerSharedPref
import com.example.barcodesessionlogin.R
import com.example.barcodesessionlogin.ui.fragment.BarCodeDetailInformationFragment
import com.example.barcodesessionlogin.ui.fragment.BarCodeScannerFragment
import com.example.barcodesessionlogin.ui.viewmodel.BarCodeScannerViewModel

class DashboardActivity : AppCompatActivity() {

    private val isActiveUSer by lazy { BarCodeScannerSharedPref.isActive() }

    private val viewmodel: BarCodeScannerViewModel by viewModels()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_dashboard)

        if (isActiveUSer) {
            // TODO(call barcodedetailfragment)
            loadFragment(BarCodeDetailInformationFragment.newInstance())

        } else {
            //TODO(call barcodescannerfragment)
            loadFragment(BarCodeScannerFragment.newInstance())
        }
    }

    private fun loadFragment(fragment: Fragment) {
        supportFragmentManager.beginTransaction().replace(R.id.container, fragment).commit()
    }
}