package com.example.projet1.data.repository

import com.example.projet1.data.models.auth.AuthResponse
import com.example.projet1.data.models.auth.LoginData
import com.example.projet1.data.models.auth.RegisterData
import com.example.projet1.data.network.Api
import com.example.projet1.utils.Constants

class AuthRepository {

    fun login(loginData: LoginData, onSuccess: (Int, AuthResponse?) -> Unit) {
        Api().post<LoginData, AuthResponse>(
            Constants.API_USERS_AUTH,
            loginData,
            onSuccess
        )
    }

    fun register(registerData: RegisterData, onSuccess: (Int) -> Unit) {
        Api().post<RegisterData>(
            Constants.API_USERS_REGISTER,
            registerData,
            onSuccess
        )
    }
}