package com.example.barcodesessionlogin.ui.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Chronometer
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.activityViewModels
import com.example.barcodesessionlogin.*
import com.example.barcodesessionlogin.data.model.BarCodeResponse
import com.example.barcodesessionlogin.data.viewmodel.BarCodeScannerViewModel
import com.example.barcodesessionlogin.utils.BarCodeScannerSharedPref
import com.example.barcodesessionlogin.utils.IResult
import com.google.gson.Gson
import com.google.zxing.integration.android.IntentIntegrator

class BarCodeDetailInformationFragment : Fragment() {

    private val viewmodel: BarCodeScannerViewModel by activityViewModels()

    private val qrScanner by lazy { IntentIntegrator.forSupportFragment(this) }

    private lateinit var locationId: String

    private var pricePerMin: Float = 0.0F

    private lateinit var endSession: TextView

    private lateinit var endSessionLoading: ProgressBar

    companion object {
        fun newInstance() = BarCodeDetailInformationFragment()
    }

    override fun onCreateView(inflater: LayoutInflater, container: ViewGroup?, savedInstanceState: Bundle?): View? {
        return inflater.inflate(R.layout.fragment_barcode_detail, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {

        setupObserver(view)
        endSession = view.findViewById(R.id.tvEndSession)
        endSessionLoading = view.findViewById(R.id.progress)
        endSession.setOnClickListener {
            qrScanner.initiateScan()
        }
    }

    private fun setupObserver(view: View) {
        viewmodel.barCodeData.observe(viewLifecycleOwner) { data ->
            data?.let {
                locationId = it.location_id ?: ""
                pricePerMin = it.price_per_min
                "LocationId -".plus(it.location_id ?: "").also { view.findViewById<TextView>(R.id.tvLocationId).text = it }
                "Price/Min - ".plus(it.price_per_min.toString()).also { view.findViewById<TextView>(R.id.tvPrice).text = it }
                view.findViewById<Chronometer>(R.id.timer).apply {
                    base = System.currentTimeMillis().calculateTotalTimeElapsedTillNow()
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
                    if (Validator.validateBarCodeJsonResponse(jsonResponse)) {
                        logout(Gson().fromJson(jsonResponse, BarCodeResponse::class.java))
                    } else {
                        Toast.makeText(requireContext(), "Either field is missing or invalid data", Toast.LENGTH_SHORT).show()
                    }
                } catch (e: Exception) {
                    Toast.makeText(requireContext(), it, Toast.LENGTH_LONG).show()
                }
            } ?: run {
                Toast.makeText(requireContext(), "Result Not Found", Toast.LENGTH_LONG).show()
            }
        }
    }

    private fun logout(barCodeResponse: BarCodeResponse) {
        if (Validator.validateBarCodeIdToLogout(barCodeResponse.location_id, locationId)) {
            System.currentTimeMillis().calculateTotalSpent { totalTime, endTime ->
                val postSessionEndResponse = BarCodeResponse().copy(location_id = locationId, time_spent = totalTime, end_time = endTime)
                viewmodel.onSessionEnd(postSessionEndResponse).observe(viewLifecycleOwner) {
                    when (it) {
                        is IResult.Success -> {
                            endSession.hide()
                            endSessionLoading.show()
                            Toast.makeText(requireContext(), "${System.currentTimeMillis().calculateTotalPrice(pricePerMin)}", Toast.LENGTH_LONG)
                                .show()
                            BarCodeScannerSharedPref.prefClear()
                            viewmodel.loadBarCodeScannerFragment()
                        }
                        is IResult.Error -> {
                            endSession.hide()
                            endSessionLoading.show()
                            Toast.makeText(requireContext(), it.throwable.localizedMessage, Toast.LENGTH_LONG).show()
                        }
                        else -> {
                            endSession.hide()
                            endSessionLoading.show()
                        }
                    }
                }
            }
        } else {
            Toast.makeText(requireContext(), "Invalid barcode to Logout", Toast.LENGTH_LONG).show()
        }
    }

}