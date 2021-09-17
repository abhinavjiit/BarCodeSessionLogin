package com.example.barcodesessionlogin.ui.fragment

import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.barcodesessionlogin.ui.viewmodel.BarCodeScannerViewModel

class BarCodeDetailInformationFragment : Fragment() {

    private val viewmodel: BarCodeScannerViewModel by activityViewModels()

    companion object {
        fun newInstance() = BarCodeDetailInformationFragment()
    }

}