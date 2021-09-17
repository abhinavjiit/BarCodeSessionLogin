package com.example.barcodesessionlogin.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.barcodesessionlogin.R
import com.example.barcodesessionlogin.ui.data.BarCodeResponse
import com.example.barcodesessionlogin.ui.viewmodel.BarCodeScannerViewModel
import com.google.gson.Gson
import com.google.zxing.integration.android.IntentIntegrator

class BarCodeScannerFragment : Fragment() {

    private val viewmodel: BarCodeScannerViewModel by activityViewModels()

    private val qrScanner by lazy { IntentIntegrator.forSupportFragment(this) }

    companion object {
        private const val TAG = "BarCodeScannerFragment"

        fun newInstance() = BarCodeScannerFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_barcode_login, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        view.findViewById<TextView>(R.id.tvScanNow).setOnClickListener {
            qrScanner.initiateScan()
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        val result = IntentIntegrator.parseActivityResult(requestCode, resultCode, data)
        result?.let { res ->
            res.contents?.let {
                try {
                    val jsonResponse = it.replace("\\", "", false)
                        .replace("\"{", "{", false)
                        .replace("}\"", "}", false)
                    val barCodeData = Gson().fromJson(jsonResponse, BarCodeResponse::class.java)
                    Log.i(TAG, "onActivityResult: $it")
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show();
                }
            } ?: run {
                Toast.makeText(requireContext(), "Result Not Found", Toast.LENGTH_LONG).show();
            }
        }
    }


}