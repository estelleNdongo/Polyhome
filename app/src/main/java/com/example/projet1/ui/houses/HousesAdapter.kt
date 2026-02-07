package com.example.projet1.ui.houses

import android.annotation.SuppressLint
import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.projet1.R
import com.example.projet1.data.models.house.HousesData

class HousesAdapter (
    private val context: Context,
    private val dataSource: ArrayList<HousesData>
): BaseAdapter(){
    private val inflater : LayoutInflater = context.getSystemService(Context.LAYOUT_INFLATER_SERVICE) as LayoutInflater
    override fun getCount(): Int {
        return dataSource.size
    }

    override fun getItem(position: Int): Any? {
       return dataSource[position]
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
        val rowView = inflater.inflate(R.layout.houses_list_item, parent,false)
        val houseId = rowView.findViewById<TextView>(R.id.houseName)
        val owner = rowView.findViewById<TextView>(R.id.houseBadge)
         val houses = getItem(position) as HousesData
        houseId.text = "Maison - ${houses.houseId}"
        if(houses.owner){
            owner.text = "Propriétaire"
            owner.setBackgroundResource(R.drawable.badge_owner)
            owner.setTextColor(0xFF4691FF.toInt())

        }else{
            owner.text = "Invité"
            owner.setBackgroundResource(R.drawable.badge_guest)
            owner.setTextColor(0xFFF59E0B.toInt())
        }

        return rowView
    }
}