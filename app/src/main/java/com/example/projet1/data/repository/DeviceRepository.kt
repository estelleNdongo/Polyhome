package com.example.projet1.data.repository

import com.example.projet1.data.models.device.DevicesResponse
import com.example.projet1.data.network.Api
import com.example.projet1.utils.Constants

class DeviceRepository {
    fun getDevices(houseId: Int, token: String, onSuccess: (Int, DevicesResponse?) -> Unit) {
        Api().get<DevicesResponse>(
            "${Constants.API_HOUSES}/$houseId/devices",
            onSuccess,
            token
        )
    }
}