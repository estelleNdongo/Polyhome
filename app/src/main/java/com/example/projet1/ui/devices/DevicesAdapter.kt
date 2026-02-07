package com.example.projet1.ui.devices

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.example.projet1.R
import com.example.projet1.data.models.device.DeviceData
import com.example.projet1.utils.Constants

class DevicesAdapter(
    val context: Context,
    val datasource : ArrayList<DeviceData>

    ): BaseAdapter(){
    private val inflater : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun getCount(): Int {
       return datasource.size
    }

    override fun getItem(position: Int): Any? {
        return datasource[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("SetTextI18n", "ViewHolder")
    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup?
    ): View? {
        val rowView = inflater.inflate(R.layout.device_list_item, parent, false)
        val deviceName = rowView.findViewById<TextView>(R.id.deviceName)
        val deviceState = rowView.findViewById<TextView>(R.id.deviceState)
        val deviceBadge = rowView.findViewById<TextView>(R.id.deviceBadge)
        val deviceIcon = rowView.findViewById<ImageView>(R.id.deviceIcon)
        val deviceIconFrame = rowView.findViewById<FrameLayout>(R.id.deviceIconFrame)

        val device = getItem(position) as DeviceData

        deviceName.text = device.id.toString()

        when (device.type) {
            Constants.DEVICE_TYPE_SHUTTER-> {
                deviceIcon.setImageResource(R.drawable.ic_shutter)
                deviceIconFrame.setBackgroundResource(R.drawable.logo_bg)
                deviceState.text = "Ouverture : ${device.opening}"
                if (device.opening!! > 0) {
                    deviceBadge.text = "Ouvert"
                    deviceBadge.setBackgroundResource(R.drawable.badge_open)
                    deviceBadge.setTextColor(0xFF4691FF.toInt())
                } else {
                    deviceBadge.text = "Fermé"
                    deviceBadge.setBackgroundResource(R.drawable.badge_close)
                    deviceBadge.setTextColor(0xFF64748B.toInt())
                }
            }

            Constants.DEVICE_TYPE_GARAGE-> {
                deviceIcon.setImageResource(R.drawable.ic_garage)
                deviceIconFrame.setBackgroundResource(R.drawable.icon_bg_garage)
                deviceState.text = "Ouverture : ${device.opening}"
                if (device.opening!! > 0) {
                    deviceBadge.text = "Ouvert"
                    deviceBadge.setBackgroundResource(R.drawable.badge_open)
                    deviceBadge.setTextColor(0xFF4691FF.toInt())
                } else {
                    deviceBadge.text = "Fermée"
                    deviceBadge.setBackgroundResource(R.drawable.badge_close)
                    deviceBadge.setTextColor(0xFF64748B.toInt())
                }
            }

            Constants.DEVICE_TYPE_LIGHT -> {
                deviceIcon.setImageResource(R.drawable.ic_light)
                deviceIconFrame.setBackgroundResource(R.drawable.icon_bg_light)
                deviceState.text = "Puissance : ${device.power}"
                if (device.power!! > 0) {
                    deviceBadge.text = "Allumé"
                    deviceBadge.setBackgroundResource(R.drawable.badge_on)
                    deviceBadge.setTextColor(0xFF16A34A.toInt())
                } else {
                    deviceBadge.text = "Éteint"
                    deviceBadge.setBackgroundResource(R.drawable.badge_close)
                    deviceBadge.setTextColor(0xFF64748B.toInt())
                }
            }
        }


        return rowView
    }
}