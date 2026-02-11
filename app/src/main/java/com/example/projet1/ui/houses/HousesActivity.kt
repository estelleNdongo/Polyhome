package com.example.projet1.ui.houses

import android.content.Intent
import android.os.Bundle
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.projet1.ui.devices.DevicesActivity
import com.example.projet1.R
import com.example.projet1.data.models.house.HousesData
import com.example.projet1.data.network.Api
import com.example.projet1.data.repository.HouseRepository
import com.example.projet1.utils.Constants
import java.util.ArrayList

class HousesActivity  : AppCompatActivity() {
    private var houses : ArrayList<HousesData> = ArrayList()
    private lateinit var houseAdapter : HousesAdapter
    private var token : String? = null
    private val houseRepository = HouseRepository()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.houses_activity)
        token = intent.getStringExtra(Constants.KEY_TOKEN)
        initHousesListView()


    }
    private fun initHousesListView(){
        val listView = findViewById<ListView>(R.id.listHouses)
        houseAdapter = HousesAdapter(this, houses)
        listView.adapter = houseAdapter
        loadHouses()
        listView.setOnItemClickListener { adapterView, view, position, longId ->
            val house = houses[position]
            val intent = Intent(
                this,
                DevicesActivity::class.java
            )
            intent.putExtra(Constants.KEY_TOKEN, token)
            intent.putExtra(Constants.KEY_HOUSE_ID, house.houseId)
            startActivity(intent)


        }
    }
    private fun loadHousesSuccess(responseCode: Int, loadedHouses: List<HousesData>?){
        if(responseCode == Constants.HTTP_SUCCESS && loadedHouses != null){
            houses.addAll(loadedHouses)
            houseAdapter.notifyDataSetChanged()

         }
    }
    private fun loadHouses(){
        houseRepository.getHouses(token ?: "", ::loadHousesSuccess)
        
    }
}