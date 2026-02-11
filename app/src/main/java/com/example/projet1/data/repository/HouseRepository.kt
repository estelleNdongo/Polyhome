package com.example.projet1.data.repository

import com.example.projet1.data.models.house.HousesData
import com.example.projet1.data.network.Api
import com.example.projet1.utils.Constants

class HouseRepository {
    fun getHouses(token: String, onSuccess: (Int, List<HousesData>?) -> Unit) {
        Api().get<List<HousesData>>(
            Constants.API_HOUSES,
            onSuccess,
            token
        )
    }
}