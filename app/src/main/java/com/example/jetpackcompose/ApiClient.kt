package com.example.jetpackcompose

import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

object ApiClient {
    val client = Retrofit.Builder()
        .baseUrl("https://api.imgflip.com")
        .addConverterFactory(GsonConverterFactory.create())
        .build()
}
