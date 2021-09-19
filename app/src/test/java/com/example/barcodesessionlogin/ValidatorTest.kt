package com.example.barcodesessionlogin

import com.example.barcodesessionlogin.utils.Validator
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
class ValidatorTest {

    @Test
    fun whenBarCodeIdIsInvalidToLogout() {
        val currentId = "abhinav"
        val scannedId = ""
        val result = Validator.validateBarCodeIdToLogout(currentId, scannedId)
        assertEquals(false, result)
    }

    @Test
    fun whenBarCodeIdIsValidToLogout() {
        val currentId = "abhinav"
        val scannedId = "abhinav"
        val result = Validator.validateBarCodeIdToLogout(currentId, scannedId)
        assertEquals(true, result)
    }

    @Test
    fun whenSessionOutPostDataIsValid() {
        val locationId = "abhinav"
        val timeSpent = 123
        val endTime = 123456L
        val result = Validator.validateSessionOutData(locationId, timeSpent, endTime)
        assertEquals(true, result)
    }

    @Test
    fun whenSessionOutPostDataIsInValid() {
        val locationId = ""
        val timeSpent = -123
        val endTime = 123456L
        val result = Validator.validateSessionOutData(locationId, timeSpent, endTime)
        assertEquals(false, result)
    }

    @Test
    fun whenValidJason() {
        val jsonString =
            "{\"location_id\":\"ButterKnifeLib-1234\",\"location_details\":\"ButterKnife Lib, 80 Feet Rd, Koramangala 1A Block, Bangalore\",\"price_per_min\":5.50}"
        val result = Validator.validateBarCodeJsonResponse(jsonString)
        assertEquals(true, result)
    }

    @Test
    fun whenInValidJason() {
        val jsonString =
            "{\"location_id\":\"ButterKnifeLib-1234\",\"location_details\":\"ButterKnife Lib, 80 Feet Rd, Koramangala 1A Block, Bangalore\"," + "\"price_per_min\":-12}"
        val result = Validator.validateBarCodeJsonResponse(jsonString)
        assertEquals(false, result)
    }

}