package com.example.hybriddemocracy.data.datasource.remote

import com.example.hybriddemocracy.data.model.User
import com.example.hybriddemocracy.data.model.request.AuthRequest
import com.example.hybriddemocracy.data.model.response.AuthResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("authenticate")
    suspend fun authenticate(@Body request: AuthRequest): AuthResponse

    @GET("login")
    suspend fun login(email: String, password: String): User

    @Headers(
        "Accept: application/json"
    )
    @GET("users/{id}")
    suspend fun getUserById(@Path("id") id: String): User
}