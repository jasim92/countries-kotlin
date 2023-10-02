package com.example.kotlinlearn.aPIControllers

import com.example.kotlinlearn.models.Country
import com.example.kotlinlearn.models.CountryResponse
import retrofit2.Call
import retrofit2.http.GET

 interface ApiServices {

    @GET("all")
    fun getCountry(): Call<List<Country>>
}