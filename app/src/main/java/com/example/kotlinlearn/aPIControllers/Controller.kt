package com.example.kotlinlearn.aPIControllers

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class Controller {
    private val BASE_URL: String = "https://restcountries.com/v3.1/"

   private lateinit var apiController: Controller

   private var retrofit: Retrofit = Retrofit.Builder()
      .baseUrl(BASE_URL)
      .addConverterFactory(GsonConverterFactory.create())
      .build()


   fun getInstance(): Controller
   {
      if (!::apiController.isInitialized) {
         apiController = this
      }
      return apiController
   }

   fun getApis(): ApiServices
   {
      return retrofit.create<ApiServices>(ApiServices::class.java)
   }
}