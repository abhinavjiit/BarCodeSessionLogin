package com.example.barcodesessionlogin.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.barcodesessionlogin.BarCodeScannerSharedPref
import com.example.barcodesessionlogin.R
import com.example.barcodesessionlogin.calculateTotalPrice
import com.example.barcodesessionlogin.calculateTotalTimeElapsed
import com.example.barcodesessionlogin.ui.data.BarCodeResponse
import com.example.barcodesessionlogin.ui.viewmodel.BarCodeScannerViewModel
import com.google.gson.Gson
import com.google.zxing.integration.android.IntentIntegrator

class BarCodeDetailInformationFragment : Fragment() {

    private val viewmodel: BarCodeScannerViewModel by activityViewModels()

    private val qrScanner by lazy { IntentIntegrator.forSupportFragment(this) }

    private lateinit var locationId: String

    private var pricePerMin: Float = 0.0F

    companion object {
        fun newInstance() = BarCodeDetailInformationFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_barcode_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupObserver(view)
        view.findViewById<TextView>(R.id.tvEndSession).setOnClickListener {
            qrScanner.initiateScan()
        }
    }


    private fun setupObserver(view: View) {
        viewmodel.barCodeData.observe(viewLifecycleOwner) { data ->
            data?.let {
                locationId = it.location_id ?: ""
                pricePerMin = it.price_per_min
                view.findViewById<TextView>(R.id.tvLocationId).text = it.location_id ?: ""
                view.findViewById<TextView>(R.id.tvPrice).text = it.price_per_min.toString()
                view.findViewById<Chronometer>(R.id.timer).apply {
                    base = System.currentTimeMillis().calculateTotalTimeElapsed()
                    start()
                    setOnChronometerTickListener {
                        Log.d("TAG", "setupObserver:  $it")
                    }
                }
            }
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
                    logout(Gson().fromJson(jsonResponse, BarCodeResponse::class.java))
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
                }
            } ?: run {
                Toast.makeText(requireContext(), "Result Not Found", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun logout(barCodeResponse: BarCodeResponse) {
        if (barCodeResponse.location_id == locationId) {
            System.currentTimeMillis().calculateTotalPrice(pricePerMin)
            Toast.makeText(requireContext(), "${System.currentTimeMillis().calculateTotalPrice(pricePerMin)}", Toast.LENGTH_LONG).show()
            BarCodeScannerSharedPref.prefClear()
            viewmodel.loadBarCodeScannerFragment()
        } else {
            Toast.makeText(requireContext(), "Invalid BarCode To Logout", Toast.LENGTH_LONG).show()
        }
    }

}