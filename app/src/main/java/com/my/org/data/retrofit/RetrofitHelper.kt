package com.example.resgraphs

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object RetrofitHelper {
    val baseUrl = "http://10.0.2.2:5000"

    private val retrofit by lazy {
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    val resApi: ResApi by lazy {
        retrofit.create(ResApi::class.java)
    }
}