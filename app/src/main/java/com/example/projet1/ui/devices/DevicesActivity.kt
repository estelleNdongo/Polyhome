package com.example.projet1.ui.devices

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.example.projet1.R
import com.example.projet1.data.models.device.DeviceData
import com.example.projet1.data.models.device.DevicesResponse
import com.example.projet1.data.network.Api
import com.example.projet1.utils.Constants
import java.util.ArrayList

class DevicesActivity : AppCompatActivity(){
    private var devices: ArrayList<DeviceData> = ArrayList()
    private lateinit var devicesAdapter : DevicesAdapter
    private var token : String? = null
    private var houseId : Int = 0

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.devices_activity)
        token = intent.getStringExtra(Constants.KEY_TOKEN)
        houseId = intent.getIntExtra(Constants.KEY_HOUSE_ID, 0)
        initDevicesListView()
    }
    @SuppressLint("SetTextI18n")
    private fun initDevicesListView(){
        val listView = findViewById<ListView>(R.id.listDevices)
        val houseName = findViewById<TextView>(R.id.titleHouse)
        houseName.text = "Maison - ${houseId}"
        devicesAdapter = DevicesAdapter(this, devices)
        listView.adapter = devicesAdapter
        loadDevices()
        listView.setOnItemClickListener { adapterView, view, position, longId ->
            val device= devices[position]
            val intent = Intent(
                this,
                DeviceDetailActivity::class.java
            )
            intent.putExtra(Constants.KEY_TOKEN, token)
            intent.putExtra(Constants.KEY_DEVICE_ID, device.id)
            intent.putExtra(Constants.KEY_DEVICE_TYPE, device.type)
            intent.putExtra(Constants.KEY_DEVICE_OPENING, device.opening)
            intent.putExtra(Constants.KEY_DEVICE_OPENING_MODE, device.openingMode)
            intent.putExtra(Constants.KEY_DEVICE_POWER, device.power)
            intent.putExtra(Constants.KEY_HOUSE_ID, houseId)
            startActivity(intent)


        }
    }
    private fun loadDevicesSuccess(responseCode: Int, loadedDevices: DevicesResponse?){
        println("RESPONSE CODE: $responseCode")
        println("LOADED DEVICES: $loadedDevices")
        runOnUiThread {
            if(responseCode == Constants.HTTP_SUCCESS && loadedDevices != null){
                devices.addAll(loadedDevices.devices)
                devicesAdapter.notifyDataSetChanged()
            }
        }

    }
    private fun loadDevices(){
        println("HOUSE ID: $houseId")
        println("TOKEN: $token")
        Api().get<DevicesResponse>("${Constants.API_HOUSES}/$houseId/devices", ::loadDevicesSuccess,token, )
    }
}