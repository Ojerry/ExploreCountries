package com.ojinc.explorecountries

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import android.view.View
import android.widget.Button
import android.widget.LinearLayout
import androidx.appcompat.widget.SearchView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ojinc.explorecountries.data.CountryData
import com.ojinc.explorecountries.data.CountryDataItem
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.*
import kotlin.collections.ArrayList

const val BASE_URL = "https://restcountries.com/v3.1/"
class MainActivity : AppCompatActivity() {

    lateinit var countryAdapter: CountryAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var recyclerView: RecyclerView
    lateinit var searchView: SearchView
    lateinit var tempCountryData: CountryData
    lateinit var filteredList: CountryData
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView  = findViewById(R.id.countries_recycler_view)
        recyclerView.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager

        filteredList = CountryData()

        getMyData()


        searchView = findViewById(R.id.searchView)
        searchView.clearFocus()
        searchView.setOnQueryTextListener(object: SearchView.OnQueryTextListener{
            override fun onQueryTextSubmit(query: String?): Boolean {
                return false
            }

            override fun onQueryTextChange(newText: String?): Boolean {
                if (newText != null) {
                    filterList(newText.lowercase())
                }
                Log.d("pressed", "beep")

                return true
            }

        })
    }

    private fun getMyData() {
        val retrofitBuilder = Retrofit.Builder()
            .addConverterFactory(GsonConverterFactory.create())
            .baseUrl(BASE_URL)
            .build()
            .create(GetApi::class.java)

        val retrofitData = retrofitBuilder.getData()
        retrofitData.enqueue(object : Callback<CountryData?> {
            override fun onResponse(call: Call<CountryData?>, response: Response<CountryData?>) {
//                var responseBody = response.body()!!
                var responseBody = CountryData()
                responseBody.addAll(response.body()!!.sortedBy { it.name.common })

                tempCountryData = CountryData()
                tempCountryData.addAll(response.body()!!.sortedBy { it.name.common })

                countryAdapter = CountryAdapter(baseContext, tempCountryData)
                countryAdapter.notifyDataSetChanged()
                recyclerView.adapter = countryAdapter



                countryAdapter.setOnItemClickListener(object : CountryAdapter.onItemClickListener{
                    override fun onItemClick(position: Int) {
//                        val myStringBuilder = StringBuilder()
//                        myStringBuilder.append(responseBody[position].name.common)
                        val intent = Intent(this@MainActivity, DetailActivity::class.java)
                        if (filteredList.isEmpty()){
                            filteredList = tempCountryData
                        }
//                        intent.putExtra("name", responseBody[position].name.common)
//                        intent.putExtra("population", responseBody[position].population.toString())
//                        intent.putExtra("flag", responseBody[position].flags.png)
//                        intent.putExtra("coatOfArms", responseBody[position].coatOfArms.png)
                        intent.putExtra("name", filteredList[position].name.common)
                        intent.putExtra("population", filteredList[position].population.toString())
                        intent.putExtra("flag", filteredList[position].flags.png)
                        intent.putExtra("coatOfArms", filteredList[position].coatOfArms.png)
                        startActivity(intent)
                    }
                })

            }

            override fun onFailure(call: Call<CountryData?>, t: Throwable) {
                d("MainActivity","onFailure: "+t.message)
            }
        })

    }
    fun filterList(newText: String){
        filteredList = CountryData()
        for(item in tempCountryData){
            if (item.name.common.toLowerCase(Locale.getDefault()).contains(newText)){
                filteredList.add(item)
            }
        }
        if (filteredList.isEmpty()){
            Toast.makeText(this, "No Data Found", Toast.LENGTH_SHORT).show()
        } else {
            countryAdapter.setFilteredList(filteredList)
        }
    }


}




//tempArrayData.clear()
//Log.d("check: ", tempArrayData.toString())
//val searchText = newText!!.toLowerCase(Locale.getDefault())
//if (searchText.isNotEmpty()){
//    tempArrayData.forEach{
//        if (it.name.common.toLowerCase(Locale.getDefault()).contains(searchText)){
//            tempArrayData.add(it)
//        }
//    }
//    recyclerView.adapter!!.notifyDataSetChanged()
//} else{
//    tempArrayData.clear()
//    tempArrayData.addAll(buff)
//    recyclerView.adapter!!.notifyDataSetChanged()
//}