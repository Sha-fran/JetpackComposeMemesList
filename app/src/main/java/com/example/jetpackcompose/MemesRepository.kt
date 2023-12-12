package com.example.jetpackcompose

import retrofit2.Response

class MemesRepository {
    private val apiClient = ApiClient.client.create(ApiInterface::class.java)

    suspend fun getMemes():Response<MemesResponse> = apiClient.getMemes()
}
