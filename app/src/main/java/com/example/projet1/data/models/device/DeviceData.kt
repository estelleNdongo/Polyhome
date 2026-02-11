package com.example.projet1.data.models.device

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class DeviceData(
    val id : String,
    val type : String,
    val availableCommands : List<String>,
    val opening : Int? = null,
    val openingMode: Int? = null,
    val power: Int? = null
): Parcelable
