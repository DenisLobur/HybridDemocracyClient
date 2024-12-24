package com.example.hybriddemocracy.data.datasource.remote

import com.example.hybriddemocracy.data.model.User
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface ApiService {
    @Headers(
        "Accept: application/json"
    )
    @GET("users/{id}")
    suspend fun getUserById(@Path("id") id: String): User
}