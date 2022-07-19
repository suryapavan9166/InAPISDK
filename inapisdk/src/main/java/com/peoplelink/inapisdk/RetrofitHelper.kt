package com.peoplelink.inapisdk

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {

    fun getInstance(): Retrofit {
        return Retrofit.Builder().baseUrl(Utils().baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
}