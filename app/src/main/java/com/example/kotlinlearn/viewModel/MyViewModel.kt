package com.example.kotlinlearn.viewModel

import android.util.Log
import androidx.lifecycle.ViewModel
import com.example.kotlinlearn.aPIControllers.ApiServices
import com.example.kotlinlearn.aPIControllers.Controller
import com.example.kotlinlearn.models.Country
import com.example.kotlinlearn.models.CountryResponse
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response


class MyViewModel() : ViewModel() {

    private val controller: Controller = Controller().getInstance()
    private val apiServices: ApiServices = controller.getApis()

    fun fetchDataFromApi(onDataLoaded: (List<Country>?) -> Unit, onError:(Throwable)-> Unit){
        val call = apiServices.getCountry()

        call.enqueue(object: Callback<List<Country>> {
            override fun onResponse(call: Call<List<Country>>, response: Response<List<Country>>) {
                if(response.isSuccessful){
                    val country: List<Country>? = response.body()
                    if(country!=null){
                        Log.e("country", country.get(0).name.common)
                       onDataLoaded(country)
                    }else{

                        onError(Throwable("Country Response is null"))
                    }

                }else{

                    onError(Throwable("response is not successful"))
                }
            }

            override fun onFailure(call: Call<List<Country>>, t: Throwable) {
                Log.e("error", t.toString())
            }

        })
    }

}
