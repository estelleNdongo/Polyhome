package com.example.projet1.ui.devices

import android.os.Bundle
import android.view.View
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.example.projet1.R
import com.example.projet1.data.models.device.CommandData
import com.example.projet1.data.models.device.DeviceData
import com.example.projet1.data.repository.DeviceRepository
import com.example.projet1.utils.Constants

class DeviceDetailActivity: AppCompatActivity() {
    private var token : String? = null
    private var houseId : Int = 0
    private  lateinit var device: DeviceData
    private val deviceRepository = DeviceRepository()


    override fun onCreate(savedInstanceState: Bundle?) {

        super.onCreate(savedInstanceState)
        setContentView(R.layout.devices_detail_activity)
        token = intent.getStringExtra(Constants.KEY_TOKEN)
        houseId = intent.getIntExtra(Constants.KEY_HOUSE_ID, 0)

        @Suppress("DEPRECATION")
        device = intent.getParcelableExtra<DeviceData>(Constants.KEY_DEVICE)!!
        displayDeviceInfo()
        setupCommandButtons()


       // deviceId = intent.getStringExtra(Constants.KEY_DEVICE_ID).toString()
        //deviceOpenning = intent.getStringExtra(Constants.KEY_DEVICE_OPENING) as Int?
       // deviceOpenningMode = intent.getStringExtra(Constants.KEY_DEVICE_OPENING_MODE) as Int?
       // devicePower = intent.getStringExtra(Constants.KEY_DEVICE_POWER) as Int?
        //deviceType = intent.getStringExtra(Constants.KEY_DEVICE_TYPE).toString()


    }
    private fun displayDeviceInfo() {

        val icon = findViewById<ImageView>(R.id.detailIcon)
        val iconFrame = findViewById<FrameLayout>(R.id.detailIconFrame)
        val deviceName = findViewById<TextView>(R.id.detailName)
        val deviceBadge = findViewById<TextView>(R.id.detailBadge)
        val infoType = findViewById<TextView>(R.id.infoType)
        val infoStateLabel = findViewById<TextView>(R.id.infoStateLabel)
        val infoStateValue = findViewById<TextView>(R.id.infoStateValue)
        val infoModeValue = findViewById<TextView>(R.id.infoModeValue)
        val rowMode = findViewById<LinearLayout>(R.id.rowMode)
        val separatorMode = findViewById<View>(R.id.separatorMode)


        deviceName.text = device.id
        infoType.text = device.type


        when (device.type) {
            Constants.DEVICE_TYPE_SHUTTER -> {

                icon.setImageResource(R.drawable.ic_shutter)
                iconFrame.setBackgroundResource(R.drawable.logo_bg)


                infoStateLabel.text = "Ouverture"
                infoStateValue.text = device.opening.toString()
                infoModeValue.text = device.openingMode.toString()


                if (device.opening!! > 0) {
                    deviceBadge.text = "Ouvert"
                    deviceBadge.setBackgroundResource(R.drawable.badge_open)
                    deviceBadge.setTextColor(0xFF4691FF.toInt())
                } else {
                    deviceBadge.text = "Fermé"
                    deviceBadge.setBackgroundResource(R.drawable.badge_close)
                    deviceBadge.setTextColor(0xFF64748B.toInt())
                }


                rowMode.visibility = View.VISIBLE
                separatorMode.visibility = View.VISIBLE
            }

            Constants.DEVICE_TYPE_GARAGE -> {

                icon.setImageResource(R.drawable.ic_garage)
                iconFrame.setBackgroundResource(R.drawable.icon_bg_garage)


                infoStateLabel.text = "Ouverture"
                infoStateValue.text = device.opening.toString()
                infoModeValue.text = device.openingMode.toString()


                if (device.opening!! > 0) {
                    deviceBadge.text = "Ouvert"
                    deviceBadge.setBackgroundResource(R.drawable.badge_open)
                    deviceBadge.setTextColor(0xFF4691FF.toInt())
                } else {
                    deviceBadge.text = "Fermée"
                    deviceBadge.setBackgroundResource(R.drawable.badge_close)
                    deviceBadge.setTextColor(0xFF64748B.toInt())
                }


                rowMode.visibility = View.VISIBLE
                separatorMode.visibility = View.VISIBLE
            }

            Constants.DEVICE_TYPE_LIGHT -> {

                icon.setImageResource(R.drawable.ic_light)
                iconFrame.setBackgroundResource(R.drawable.icon_bg_light)


                infoStateLabel.text = "Puissance"
                infoStateValue.text = device.power.toString()


                if (device.power!! > 0) {
                    deviceBadge.text = "Allumé"
                    deviceBadge.setBackgroundResource(R.drawable.badge_on)
                    deviceBadge.setTextColor(0xFF16A34A.toInt())
                } else {
                    deviceBadge.text = "Éteint"
                    deviceBadge.setBackgroundResource(R.drawable.badge_close)
                    deviceBadge.setTextColor(0xFF64748B.toInt())
                }


                rowMode.visibility = View.GONE
                separatorMode.visibility = View.GONE
            }
        }
    }
    private fun setupCommandButtons() {

        val btnCommand1 = findViewById<LinearLayout>(R.id.btnCommand1)
        val btnCommand2 = findViewById<LinearLayout>(R.id.btnCommand2)
        val btnCommand3 = findViewById<LinearLayout>(R.id.btnCommand3)

        val iconCommand1 = findViewById<ImageView>(R.id.iconCommand1)
        val iconCommand2 = findViewById<ImageView>(R.id.iconCommand2)
        val iconCommand3 = findViewById<ImageView>(R.id.iconCommand3)

        val textCommand1 = findViewById<TextView>(R.id.textCommand1)
        val textCommand2 = findViewById<TextView>(R.id.textCommand2)
        val textCommand3 = findViewById<TextView>(R.id.textCommand3)


        when (device.type) {
            Constants.DEVICE_TYPE_SHUTTER, Constants.DEVICE_TYPE_GARAGE -> {
                // Bouton 1 : OPEN
                textCommand1.text = device.availableCommands[0]
                iconCommand1.setImageResource(R.drawable.ic_open)
                btnCommand1.setBackgroundResource(R.drawable.btn_command_open)
                textCommand1.setTextColor(0xFFFFFFFF.toInt())

                // Bouton 2 : CLOSE
                textCommand2.text = device.availableCommands[1]
                iconCommand2.setImageResource(R.drawable.ic_close)
                btnCommand2.setBackgroundResource(R.drawable.btn_command_default)
                textCommand2.setTextColor(0xFF0F172A.toInt())

                // Bouton 3 : STOP
                textCommand3.text = device.availableCommands[2]
                iconCommand3.setImageResource(R.drawable.ic_stop)
                btnCommand3.setBackgroundResource(R.drawable.btn_command_stop)
                textCommand3.setTextColor(0xFFB45309.toInt())
                btnCommand3.visibility = View.VISIBLE


                btnCommand1.setOnClickListener { sendCommand(device.availableCommands[0]) }
                btnCommand2.setOnClickListener { sendCommand(device.availableCommands[1]) }
                btnCommand3.setOnClickListener { sendCommand(device.availableCommands[2]) }
            }

            Constants.DEVICE_TYPE_LIGHT -> {
                // Bouton 1 : TURN ON
                textCommand1.text = device.availableCommands[0]
                iconCommand1.setImageResource(R.drawable.ic_turn_on)
                btnCommand1.setBackgroundResource(R.drawable.btn_command_on)
                textCommand1.setTextColor(0xFFFFFFFF.toInt())

                // Bouton 2 : TURN OFF
                textCommand2.text = device.availableCommands[1]
                iconCommand2.setImageResource(R.drawable.ic_turn_off)
                btnCommand2.setBackgroundResource(R.drawable.btn_command_default)
                textCommand2.setTextColor(0xFF0F172A.toInt())

                // Bouton 3 : Caché
                btnCommand3.visibility = View.GONE


                btnCommand1.setOnClickListener { sendCommand(device.availableCommands[0]) }
                btnCommand2.setOnClickListener { sendCommand(device.availableCommands[1]) }
            }
        }
    }
    private fun sendCommand(command: String){
        val commandData = CommandData(command)
        deviceRepository.sendCommand(
            houseId,
            device.id,
            commandData,
            token ?: "",
            ::commandSuccess

        )
    }
    private fun commandSuccess(responseCode: Int){
        runOnUiThread {
            if(responseCode == Constants.HTTP_SUCCESS){
                Toast.makeText(this, "Commande envoyée !", Toast.LENGTH_SHORT).show()
            }else{
                Toast.makeText(this, "Erreur lors de l'envoi", Toast.LENGTH_SHORT).show()
            }
        }
    }
    public fun goBack(view: View){
        finish()
    }
}