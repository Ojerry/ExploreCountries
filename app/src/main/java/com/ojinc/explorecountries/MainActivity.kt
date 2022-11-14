package com.ojinc.explorecountries

import android.app.Dialog
import android.content.Intent
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import android.view.Gravity
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.*
import androidx.appcompat.app.AppCompatDelegate
import androidx.appcompat.widget.SearchView
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
    lateinit var toggleMode: ImageView
    var nightMode: Boolean = false
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
        toggleMode = findViewById(R.id.toggleNightMode)
        toggleMode.setOnClickListener {
//            setItem()
            if (nightMode){
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                toggleMode.setImageResource(R.drawable.ic_light_mode)
               nightMode = false
            }else{
//                AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                toggleMode.setImageResource(R.drawable.ic_night_mode)
                nightMode = true
            }
        }
        val filterButton : LinearLayout = findViewById(R.id.filterButton_linearLayout)
        filterButton.setOnClickListener(View.OnClickListener { v ->
            showFilterDialog()
        })
        val langButton : LinearLayout = findViewById(R.id.languageButton_linearLayout)
        langButton.setOnClickListener(View.OnClickListener { v ->
            showSheetDialog()
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
                        intent.putExtra("area", filteredList[position].area.toInt().toString())
                        intent.putExtra("region", filteredList[position].region)
                        intent.putExtra("unMember", filteredList[position].unMember.toString())
                        intent.putExtra("subRegion", filteredList[position].subregion)
                        intent.putExtra("timezone", filteredList[position].timezones.get(0))
                        intent.putExtra("independent", filteredList[position].independent.toString())
//                        intent.putExtra("area", filteredList[position].area.toString())
                        intent.putExtra("landlocked", filteredList[position].landlocked.toString())
                        intent.putExtra("name", filteredList[position].name.common)
//                        intent.putExtra("currency", filteredList[position].currencies)
                        intent.putExtra("continents", filteredList[position].continents?.get(0))
                        intent.putExtra("capital", filteredList[position].capital?.get(0))
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
//    fun setItem(){
////        val toggleNightMode: ImageView = findViewById(R.id.toggleNightMode)
////        toggleMode.setImageResource(R.drawable.ic_night_mode)
//        if (isDayMode){
//            toggleMode.setImageResource(R.drawable.ic_light_mode)
//        } else{
//            toggleMode.setImageResource(R.drawable.ic_night_mode)
//        }
//
//    }

    private fun showFilterDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.bottom_filter)

        dialog.show()
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
        dialog.window!!.setGravity(Gravity.BOTTOM)
    }
    private fun showSheetDialog() {
        val dialog = Dialog(this)
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)
        dialog.setContentView(R.layout.bottom_sheet)

        dialog.show()
        dialog.window!!.setLayout(
            ViewGroup.LayoutParams.MATCH_PARENT,
            ViewGroup.LayoutParams.WRAP_CONTENT
        )
        dialog.window!!.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog.window!!.attributes.windowAnimations = R.style.DialogAnimation
        dialog.window!!.setGravity(Gravity.BOTTOM)
    }
}
