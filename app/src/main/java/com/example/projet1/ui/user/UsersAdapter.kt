package com.example.projet1.ui.user

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.BaseAdapter
import android.widget.TextView
import com.example.projet1.R
import com.example.projet1.data.models.user.UserData


class UsersAdapter(
    private val context : Context,
    private val dataSource: ArrayList<UserData>,
    private val onRemoveClick: (UserData) -> Unit
): BaseAdapter() {
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

    override fun getView(
        position: Int,
        convertView: View?,
        parent: ViewGroup?
    ): View? {
        val rowView = inflater.inflate(R.layout.user_list_item, parent,false)
        val username = rowView.findViewById<TextView>(R.id.userName)
        val badge = rowView.findViewById<TextView>(R.id.userBadge)
        val btnRemove = rowView.findViewById<TextView>(R.id.btnRemoveUser)

        val user = getItem(position) as UserData

        username.text = user.userLogin

        if(user.owner == 1){
            badge.text = "Propriétaire"
            badge.setBackgroundResource(R.drawable.badge_owner)
            badge.setTextColor(0xFF4691FF.toInt())
            btnRemove.visibility = View.GONE

        }else{
            badge.text = "Invité"
            badge.setBackgroundResource(R.drawable.badge_guest)
            badge.setTextColor(0xFFF59E0B.toInt())
            btnRemove.visibility = View.VISIBLE
            btnRemove.setOnClickListener { onRemoveClick(user) }
        }

        return rowView
    }
}