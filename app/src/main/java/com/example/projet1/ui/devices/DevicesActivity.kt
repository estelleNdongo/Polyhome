package com.example.projet1.ui.devices

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.widget.EditText
import android.widget.LinearLayout
import android.widget.ListView
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projet1.R
import com.example.projet1.data.models.device.DeviceData
import com.example.projet1.data.models.device.DevicesResponse
import com.example.projet1.data.models.user.UserData
import com.example.projet1.data.network.Api
import com.example.projet1.data.repository.UserRepository
import com.example.projet1.ui.user.UsersAdapter
import com.example.projet1.utils.Constants
import java.util.ArrayList
import android.view.View
import com.example.projet1.data.models.user.AddUserData

class DevicesActivity : AppCompatActivity(){
    private var devices: ArrayList<DeviceData> = ArrayList()
    private lateinit var devicesAdapter : DevicesAdapter
    private var token : String? = null
    private var houseId : Int = 0
    private var userRepository = UserRepository()
    private var users : ArrayList<UserData> = ArrayList()
    private lateinit var userAdapter : UsersAdapter

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.devices_activity)
        token = intent.getStringExtra(Constants.KEY_TOKEN)
        houseId = intent.getIntExtra(Constants.KEY_HOUSE_ID, 0)
        initDevicesListView()
        initUserListView()
        setupTabs()
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
            intent.putExtra(Constants.KEY_HOUSE_ID, houseId)
            intent.putExtra(Constants.KEY_DEVICE, device)
           // intent.putExtra(Constants.KEY_DEVICE_ID, device.id)
           // intent.putExtra(Constants.KEY_DEVICE_TYPE, device.type)
           // intent.putExtra(Constants.KEY_DEVICE_OPENING, device.opening)
           // intent.putExtra(Constants.KEY_DEVICE_OPENING_MODE, device.openingMode)
            //intent.putExtra(Constants.KEY_DEVICE_POWER, device.power)
            //intent.putExtra(Constants.KEY_HOUSE_ID, houseId)
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
    private fun initUserListView(){
        val listView = findViewById<ListView>(R.id.listUsers)
        val btnAddUser = findViewById<TextView>(R.id.btnAddUser)
        val inputAddUser = findViewById<EditText>(R.id.inputAddUser)

        userAdapter = UsersAdapter(this, users){ user ->
            removeUser(user)

        }
        listView.adapter = userAdapter
        btnAddUser.setOnClickListener {
            val login = inputAddUser.text.toString()
            if(login.isEmpty()){
                Toast.makeText(this, "Entrez un nom", Toast.LENGTH_SHORT).show()
                return@setOnClickListener
            }
            val addUserData = AddUserData(login)
            userRepository.addUser(houseId, addUserData, token ?: "", ::addUserSuccess)
        }

    }
    private fun switchToDevicesTab() {

        findViewById<ListView>(R.id.listDevices).visibility = View.VISIBLE
        findViewById<ListView>(R.id.listUsers).visibility = View.GONE
        findViewById<LinearLayout>(R.id.addUserSection).visibility = View.GONE


        findViewById<TextView>(R.id.tabDevices).apply {
            setBackgroundResource(R.drawable.tab_active)
            setTextColor(0xFFFFFFFF.toInt())
        }
        findViewById<TextView>(R.id.tabUsers).apply {
            setBackgroundResource(R.drawable.tab_inactive)
            setTextColor(0xFF64748B.toInt())
        }
    }
    private fun switchToUsersTab() {

        findViewById<ListView>(R.id.listDevices).visibility = View.GONE
        findViewById<ListView>(R.id.listUsers).visibility = View.VISIBLE
        findViewById<LinearLayout>(R.id.addUserSection).visibility = View.VISIBLE

        // Style tabs
        findViewById<TextView>(R.id.tabDevices).apply {
            setBackgroundResource(R.drawable.tab_inactive)
            setTextColor(0xFF64748B.toInt())
        }
        findViewById<TextView>(R.id.tabUsers).apply {
            setBackgroundResource(R.drawable.tab_active)
            setTextColor(0xFFFFFFFF.toInt())
        }

        users.clear()
        loadUsers()
    }
    private fun loadUsers(){
        userRepository.getUsers(houseId , token ?: "", ::loadUsersSuccess)
    }
    private fun loadUsersSuccess(responseCode: Int, loadedUsers: List<UserData>?) {
        runOnUiThread {
            if (responseCode == Constants.HTTP_SUCCESS && loadedUsers != null) {
                users.clear()
                users.addAll(loadedUsers)
                userAdapter.notifyDataSetChanged()
            }
        }
    }
    private fun addUserSuccess(responseCode: Int) {
        runOnUiThread {
            when (responseCode) {
                Constants.HTTP_SUCCESS -> {
                    Toast.makeText(this, "User invité !", Toast.LENGTH_SHORT).show()
                    findViewById<EditText>(R.id.inputAddUser).text.clear()
                    users.clear()
                    loadUsers()
                }
                409 -> Toast.makeText(this, "User déjà associé !", Toast.LENGTH_SHORT).show()
                403 -> Toast.makeText(this, "Accès interdit !", Toast.LENGTH_SHORT).show()
                else -> Toast.makeText(this, "Erreur lors de l'invitation", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun removeUser(user: UserData){
        userRepository.removeUser(houseId, user.userLogin, token ?: "", ::removeUserSuccess)
    }
    private fun removeUserSuccess(responseCode: Int) {
        runOnUiThread {
            if (responseCode == Constants.HTTP_SUCCESS) {
                Toast.makeText(this, "User supprimé !", Toast.LENGTH_SHORT).show()
                users.clear()
                loadUsers()
            } else {
                Toast.makeText(this, "Erreur lors de la suppression", Toast.LENGTH_SHORT).show()
            }
        }
    }
    private fun setupTabs() {
        val tabDevices = findViewById<TextView>(R.id.tabDevices)
        val tabUsers = findViewById<TextView>(R.id.tabUsers)

        tabDevices.setOnClickListener { switchToDevicesTab() }
        tabUsers.setOnClickListener { switchToUsersTab() }
    }

}