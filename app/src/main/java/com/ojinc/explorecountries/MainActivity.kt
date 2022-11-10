package com.ojinc.explorecountries

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.ojinc.explorecountries.data.CountryData
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

const val BASE_URL = "https://restcountries.com/v3.1/"
class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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
                val myStringBuilder = StringBuilder()

                for (myData in responseBody){
                    myStringBuilder.append(myData.name.common + "\t"+ myData.capital + "\n")
                    Log.e("data", myStringBuilder.toString())
                }
                val txtId: TextView = findViewById(R.id.txtV)
                txtId.text = myStringBuilder
            }

            override fun onFailure(call: Call<CountryData?>, t: Throwable) {
                Log.d("MainActivity","onFailure: "+t.message)
            }
        })

    }


}