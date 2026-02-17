package com.example.projet1.data.repository

import com.example.projet1.data.models.auth.RegisterData
import com.example.projet1.data.models.device.CommandData
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
    fun sendCommand(houseId: Int, deviceId: String, commandData: CommandData, token: String, onSuccess: (Int) -> Unit){
        Api().post<CommandData>(
            "${Constants.API_HOUSES}/$houseId/devices/$deviceId/command",
            commandData,
            onSuccess,
            token
        )
    }
}