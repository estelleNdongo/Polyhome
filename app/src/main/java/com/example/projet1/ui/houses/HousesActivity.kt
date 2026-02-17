package com.example.projet1.ui.houses

import android.content.Intent
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.AutoCompleteTextView
import android.widget.ListView
import androidx.appcompat.app.AppCompatActivity
import com.example.projet1.ui.devices.DevicesActivity
import com.example.projet1.R
import com.example.projet1.data.models.house.HousesData
import com.example.projet1.data.repository.HouseRepository
import com.example.projet1.utils.Constants
import java.util.ArrayList
import android.text.Editable
import android.text.TextWatcher
import android.view.View

class HousesActivity  : AppCompatActivity() {
    private var houses : ArrayList<HousesData> = ArrayList()
    private var filteredHouses : ArrayList<HousesData> = ArrayList()
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
        houseAdapter = HousesAdapter(this, filteredHouses)
        listView.adapter = houseAdapter
        loadHouses()
        listView.setOnItemClickListener { adapterView, view, position, longId ->
            val house = filteredHouses[position]
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
        runOnUiThread {
            if(responseCode == Constants.HTTP_SUCCESS && loadedHouses != null){
                houses.addAll(loadedHouses)
                filteredHouses.addAll(loadedHouses)
                houseAdapter.notifyDataSetChanged()
                setupSearch()

            }
        }

    }
    private fun loadHouses(){
        houseRepository.getHouses(token ?: "", ::loadHousesSuccess)
        
    }
    private fun setupSearch(){
        val searchHouse = findViewById<AutoCompleteTextView>(R.id.searchHouse)

        val houseIds = houses.map{it.houseId.toString()}
        val adapter = ArrayAdapter(this, android.R.layout.simple_dropdown_item_1line, houseIds)
        searchHouse.setAdapter(adapter)
        searchHouse.threshold = 1

        searchHouse.setOnItemClickListener { _, _, _, _ ->
            val searchValue = searchHouse.text.toString()
            filterHouses(searchValue)

        }
        searchHouse.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {}
            override fun afterTextChanged(s: Editable?) {}
            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                if (s.isNullOrEmpty()) {
                    filterHouses("")
                }
            }
        })
    }
    private fun filterHouses(value: String){
        filteredHouses.clear()
     if (value.isEmpty()) {
           filteredHouses.addAll(houses)
        } else {
            val filtered = houses.filter {
                it.houseId.toString().contains(value, ignoreCase = true)
            }
         filteredHouses.addAll(filtered)
        }
        houseAdapter.notifyDataSetChanged()
    }
    public fun goBack(view: View){
        finish()
    }
}