package com.example.projet1.ui.devices

import android.os.Bundle
import android.os.PersistableBundle
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.projet1.R
import com.example.projet1.data.models.device.DeviceData
import com.example.projet1.utils.Constants

class DeviceDetailActivity: AppCompatActivity() {
    private var token : String? = null
    private var houseId : Int = 0
    private  lateinit var device: DeviceData


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.devices_detail_activity)

        @Suppress("DEPRECATION")
        device = intent.getParcelableExtra<DeviceData>(Constants.KEY_TOKEN)!!


        token = intent.getStringExtra(Constants.KEY_TOKEN)
       houseId = intent.getIntExtra(Constants.KEY_HOUSE_ID, 0)
       // deviceId = intent.getStringExtra(Constants.KEY_DEVICE_ID).toString()
        //deviceOpenning = intent.getStringExtra(Constants.KEY_DEVICE_OPENING) as Int?
       // deviceOpenningMode = intent.getStringExtra(Constants.KEY_DEVICE_OPENING_MODE) as Int?
       // devicePower = intent.getStringExtra(Constants.KEY_DEVICE_POWER) as Int?
        //deviceType = intent.getStringExtra(Constants.KEY_DEVICE_TYPE).toString()
        displayDeviceInfo()

    }
    private fun displayDeviceInfo(){
        val icon = findViewById<ImageView>(R.id.detailIcon)
        val deviceName = findViewById<TextView>(R.id.detailName)

        deviceName.text = device.type

    }
}