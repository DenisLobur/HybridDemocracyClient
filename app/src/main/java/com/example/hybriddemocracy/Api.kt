package com.example.hybriddemocracy

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Path

interface Api {
    @Headers(
        "Accept: application/json"
    )
    @GET("users/{id}")
    abstract fun getUserById(@Path("id") id: String): Call<UserModel?>?
}