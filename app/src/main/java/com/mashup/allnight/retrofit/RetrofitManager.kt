package com.mashup.allnight.retrofit

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitManager {
    private const val BASE_URL = "http://15.164.22.30:8080"

    private val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    val IRetrofitApi = retrofit.create(com.mashup.allnight.retrofit.IRetrofitApi::class.java)
}