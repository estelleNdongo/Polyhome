package com.example.projet1.utils

object Constants {
    const val BASE_URL = "https://polyhome.lesmoulinsdudev.com"

    // Endpoints pour l'authentification
    const val API_USERS_AUTH = "$BASE_URL/api/users/auth"
    const val API_USERS_REGISTER = "$BASE_URL/api/users/register"

    // Endpoints pour les maisons
    const val API_HOUSES = "$BASE_URL/api/houses"

    //Endpoints pour les users
    const val API_USERS = "$BASE_URL/api/users"


    // Clés pour les Intent
    const val KEY_TOKEN = "Token"
    const val KEY_HOUSE_ID = "houseId"
    const val KEY_DEVICE_ID = "deviceId"
    const val KEY_DEVICE_TYPE = "deviceType"
    const val KEY_DEVICE_OPENING = "deviceOpening"
    const val KEY_DEVICE_OPENING_MODE = "deviceOpeningMode"
    const val KEY_DEVICE_POWER = "devicePower"
    const val KEY_DEVICE = "device"

    // Codes HTTP
    const val HTTP_SUCCESS = 200
    const val HTTP_SERVER_ERROR = 500

    // Device Types
    const val DEVICE_TYPE_SHUTTER = "rolling shutter"
    const val DEVICE_TYPE_GARAGE = "garage door"
    const val DEVICE_TYPE_LIGHT = "light"
}