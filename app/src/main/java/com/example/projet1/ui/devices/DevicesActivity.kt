package com.example.projet1.ui.devices

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
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
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.FrameLayout
import android.widget.HorizontalScrollView
import androidx.appcompat.app.AlertDialog
import com.example.projet1.data.models.device.CommandData
import com.example.projet1.data.models.user.AddUserData
import com.example.projet1.data.models.user.PolyhomeUser
import com.example.projet1.data.repository.DeviceRepository

class DevicesActivity : AppCompatActivity(){
    private var devices: ArrayList<DeviceData> = ArrayList()
    private var filteredDevices : ArrayList<DeviceData> = ArrayList()
    private lateinit var devicesAdapter : DevicesAdapter
    private val deviceRepository = DeviceRepository()

    private var token : String? = null
    private var houseId : Int = 0
    private var userRepository = UserRepository()
    private var users : ArrayList<UserData> = ArrayList()
    private lateinit var userAdapter : UsersAdapter
    private var allPolyhomeUsers : ArrayList<PolyhomeUser> = ArrayList()
    private var isOwner : Boolean = false
    private var connectedUser: String = ""
    private var isFirstLoad = true

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.devices_activity)
        token = intent.getStringExtra(Constants.KEY_TOKEN)
        houseId = intent.getIntExtra(Constants.KEY_HOUSE_ID, 0)
        val sharedPreferences = getSharedPreferences(Constants.KEY_PREFERENCES, MODE_PRIVATE)
        connectedUser = sharedPreferences.getString(Constants.KEY_USER_PREFERENCES, "") ?: ""
        initDevicesListView()
        initUserListView()
        setupTabs()
        setupFilters()
        loadUsers()

        findViewById<TextView>(R.id.btnBack).setOnClickListener { finish() }
        findViewById<FrameLayout>(R.id.btnModeNuit).setOnClickListener {
            activateNightMode()
        }
    }
    @SuppressLint("SetTextI18n")
    private fun initDevicesListView(){
        val listView = findViewById<ListView>(R.id.listDevices)
        val houseName = findViewById<TextView>(R.id.titleHouse)
        houseName.text = "Maison - ${houseId}"
        devicesAdapter = DevicesAdapter(this, filteredDevices)
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
    private fun loadDevices(){
        deviceRepository.getDevices(houseId, token ?: "", ::loadDevicesSuccess)

    }
    private fun loadDevicesSuccess(responseCode: Int, loadedDevices: DevicesResponse?){
        runOnUiThread {
            if(responseCode == Constants.HTTP_SUCCESS && loadedDevices != null){
                devices.addAll(loadedDevices.devices)
                filteredDevices.addAll(loadedDevices.devices)
                devicesAdapter.notifyDataSetChanged()
            }
        }

    }
    private fun setupFilters(){
        var filterAll = findViewById<TextView>(R.id.filterAll)
        var filterShutter = findViewById<TextView>(R.id.filterShutter)
        var filterGarage = findViewById<TextView>(R.id.filterGarage)
        var filterLight = findViewById<TextView>(R.id.filterLight)


        filterAll.setOnClickListener {

            listOf(filterAll, filterShutter, filterGarage, filterLight).forEach { filter ->
                filter.setBackgroundResource(R.drawable.tab_inactive)
                filter.setTextColor(0xFF64748B.toInt())
            }


            filterAll.setBackgroundResource(R.drawable.tab_active)
            filterAll.setTextColor(0xFFFFFFFF.toInt())


            filteredDevices.clear()
            filteredDevices.addAll(devices)
            devicesAdapter.notifyDataSetChanged()
        }

        filterShutter.setOnClickListener {

            listOf(filterAll, filterShutter, filterGarage, filterLight).forEach { filter ->
                filter.setBackgroundResource(R.drawable.tab_inactive)
                filter.setTextColor(0xFF64748B.toInt())
            }


            filterShutter.setBackgroundResource(R.drawable.tab_active)
            filterShutter.setTextColor(0xFFFFFFFF.toInt())


            filteredDevices.clear()
            filteredDevices.addAll(devices.filter { it.type == Constants.DEVICE_TYPE_SHUTTER })
            devicesAdapter.notifyDataSetChanged()
        }

        filterGarage.setOnClickListener {

            listOf(filterAll, filterShutter, filterGarage, filterLight).forEach { filter ->
                filter.setBackgroundResource(R.drawable.tab_inactive)
                filter.setTextColor(0xFF64748B.toInt())
            }


            filterGarage.setBackgroundResource(R.drawable.tab_active)
            filterGarage.setTextColor(0xFFFFFFFF.toInt())


            filteredDevices.clear()
            filteredDevices.addAll(devices.filter { it.type == Constants.DEVICE_TYPE_GARAGE })
            devicesAdapter.notifyDataSetChanged()
        }

        filterLight.setOnClickListener {

            listOf(filterAll, filterShutter, filterGarage, filterLight).forEach { filter ->
                filter.setBackgroundResource(R.drawable.tab_inactive)
                filter.setTextColor(0xFF64748B.toInt())
            }


            filterLight.setBackgroundResource(R.drawable.tab_active)
            filterLight.setTextColor(0xFFFFFFFF.toInt())


            filteredDevices.clear()
            filteredDevices.addAll(devices.filter { it.type == Constants.DEVICE_TYPE_LIGHT })
            devicesAdapter.notifyDataSetChanged()
        }
    }

    private fun initUserListView(){
        val listView = findViewById<ListView>(R.id.listUsers)
        val btnAddUser = findViewById<TextView>(R.id.btnAddUser)
        val inputAddUser = findViewById<AutoCompleteTextView>(R.id.inputAddUser)

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
        findViewById<HorizontalScrollView>(R.id.filterSection).visibility = View.VISIBLE


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
        findViewById<HorizontalScrollView>(R.id.filterSection).visibility = View.GONE


        findViewById<TextView>(R.id.tabDevices).apply {
            setBackgroundResource(R.drawable.tab_inactive)
            setTextColor(0xFF64748B.toInt())
        }
        findViewById<TextView>(R.id.tabUsers).apply {
            setBackgroundResource(R.drawable.tab_active)
            setTextColor(0xFFFFFFFF.toInt())
        }
        loadAllPolyhomeUsers()
        users.clear()
        loadUsers()
    }
    private fun loadUsers(){
        userRepository.getUsers(houseId , token ?: "", ::loadUsersSuccess)
    }
    private fun loadUsersSuccess(responseCode: Int, loadedUsers: List<UserData>?) {
        runOnUiThread {
            if (responseCode == Constants.HTTP_SUCCESS && loadedUsers != null) {
                isOwner = loadedUsers.any{it.owner == 1 && it.userLogin == connectedUser}
                updateTabUsersVisibility()
                if(isFirstLoad){
                    isFirstLoad = false
                }else{
                    users.clear()
                    users.addAll(loadedUsers)
                    userAdapter.notifyDataSetChanged()
                }




            }
        }
    }
    private fun updateTabUsersVisibility(){
        val tabUsers = findViewById<TextView>(R.id.tabUsers)
        if(isOwner){
            tabUsers.visibility = View.VISIBLE
        }else{
            tabUsers.visibility = View.GONE
        }
    }
    private fun addUserSuccess(responseCode: Int) {
        runOnUiThread {
            when (responseCode) {
                Constants.HTTP_SUCCESS -> {
                    Toast.makeText(this, "User invité !", Toast.LENGTH_SHORT).show()
                    findViewById<AutoCompleteTextView>(R.id.inputAddUser).text.clear()
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

        AlertDialog.Builder(this)
            .setTitle("Supprimer l'accès")
            .setMessage("Voulez-vous vraiment supprimer l'accès à ${user.userLogin} ?")
            .setPositiveButton("Oui"){ _, _, ->
                val removeUserData = AddUserData(user.userLogin)
                userRepository.removeUser(houseId, removeUserData, token ?: "", ::removeUserSuccess)
            }
            .setNegativeButton("Annuler", null)
            .show()

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
    private fun loadAllPolyhomeUsers(){
        userRepository.getAllUsers(token ?: "", ::loadAllPolyhomeUsersSuccess)
    }
    private fun loadAllPolyhomeUsersSuccess(responseCode: Int, loadedUsers: List<PolyhomeUser>?){
        runOnUiThread {
            if(responseCode == Constants.HTTP_SUCCESS && loadedUsers != null){
                allPolyhomeUsers.clear()
                allPolyhomeUsers.addAll(loadedUsers)
                setupAutoComplete()
            }
        }
    }
    private fun setupAutoComplete(){
        val inputAddUser = findViewById<AutoCompleteTextView>(R.id.inputAddUser)
        val loginList = allPolyhomeUsers.map{it.login}
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, loginList)
        inputAddUser.setAdapter(adapter)
        inputAddUser.threshold = 1
    }
    private fun activateNightMode(){
        AlertDialog.Builder(this)
            .setTitle("🌙 Mode Nuit")
            .setMessage("Fermer tous les volets et garages et éteindre toutes les lumières")
            .setPositiveButton("Oui"){_, _, ->
                executeNightMode()
            }
            .setNegativeButton("Annuler", null)
            .show()

    }
    private fun executeNightMode(){
        var success = 0
        var error = 0
        var totalDevice = devices.size

        devices.forEach { device->
            val command = when (device.type){
                Constants.DEVICE_TYPE_SHUTTER, Constants.DEVICE_TYPE_GARAGE -> "CLOSE"
                Constants.DEVICE_TYPE_LIGHT -> "TURN OFF"
                else -> null
            }
            if (command != null){
                deviceRepository.sendCommand(
                    houseId,
                    device.id,
                    CommandData(command),
                    token ?: ""

                ){ responseCode ->
                    runOnUiThread {
                        if (responseCode == Constants.HTTP_SUCCESS){
                            success ++
                        }else {
                            error ++
                        }
                        if(success + error == totalDevice){
                            Toast.makeText(
                                this,
                                "\uD83C\uDF19 Mode Nuit : $success réussis, $error échecs",
                                Toast.LENGTH_SHORT
                            ).show()
                        }
                    }

                }
            }
        }
    }

}