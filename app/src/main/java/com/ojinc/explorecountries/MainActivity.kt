package com.ojinc.explorecountries

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.util.Log.d
import android.widget.Button
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.ojinc.explorecountries.data.CountryData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://restcountries.com/v3.1/"
class MainActivity : AppCompatActivity() {

    lateinit var countryAdapter: CountryAdapter
    lateinit var linearLayoutManager: LinearLayoutManager
    lateinit var recyclerView: RecyclerView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        recyclerView  = findViewById(R.id.countries_recycler_view)
        recyclerView.setHasFixedSize(true)
        linearLayoutManager = LinearLayoutManager(this)
        recyclerView.layoutManager = linearLayoutManager




        getMyData()
        val btn: Button = findViewById(R.id.btn)
        btn.setOnClickListener(){
            getMyData()
        }
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
                val responseBody = response.body()!!
//                Log.e("data", responseBody.toString())
                countryAdapter = CountryAdapter(baseContext, responseBody)
                countryAdapter.notifyDataSetChanged()
                recyclerView.adapter = countryAdapter

                countryAdapter.setOnItemClickListener(object : CountryAdapter.onItemClickListener{
                    override fun onItemClick(position: Int) {
                        val intent = Intent(this@MainActivity, DetailActivity::class.java)

                        startActivity(intent)
                    }

                })
            }

            override fun onFailure(call: Call<CountryData?>, t: Throwable) {
                d("MainActivity","onFailure: "+t.message)
            }
        })

    }


}