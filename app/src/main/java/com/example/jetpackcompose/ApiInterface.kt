package com.example.jetpackcompose

import retrofit2.Response
import retrofit2.http.GET

interface ApiInterface {
    @GET("/get_memes")
    suspend fun getMemes():Response<MemesResponse>
}
