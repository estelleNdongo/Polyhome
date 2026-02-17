package com.example.projet1.data.repository

import com.example.projet1.data.models.house.HousesData
import com.example.projet1.data.models.user.AddUserData
import com.example.projet1.data.models.user.PolyhomeUser
import com.example.projet1.data.models.user.UserData
import com.example.projet1.data.network.Api
import com.example.projet1.utils.Constants

class UserRepository {

    fun getUsers(houseId: Int, token : String, onSuccess: (Int, List<UserData>?) -> Unit){
        Api().get<List<UserData>>(
            "${Constants.API_HOUSES}/$houseId/users",
            onSuccess,
            token
        )
    }
    fun getAllUsers(token : String, onSuccess: (Int, List<PolyhomeUser>?) -> Unit){
            Api().get<List<PolyhomeUser>>(
                Constants.API_USERS,
                onSuccess,
                token
            )
    }
    fun addUser(houseId: Int, userLogin: AddUserData, token: String, onSuccess: (Int) -> Unit){
        Api().post<AddUserData>(
            "${Constants.API_HOUSES}/$houseId/users",
            userLogin,
            onSuccess,
            token

        )

    }
    fun removeUser(houseId: Int, userLogin: String, token: String, onSuccess: (Int) -> Unit){
        Api().delete(
            "${Constants.API_HOUSES}/$houseId/users",
            userLogin,
            onSuccess,
            token
        )
    }
}