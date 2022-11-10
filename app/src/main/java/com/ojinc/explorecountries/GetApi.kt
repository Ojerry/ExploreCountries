package com.ojinc.explorecountries

import com.ojinc.explorecountries.data.CountryData
import retrofit2.Call
import retrofit2.http.GET

interface GetApi {
    @GET("all")
    fun getData(): Call<CountryData>
}