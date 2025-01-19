package com.example.hybriddemocracy.data.datasource.remote

import com.example.hybriddemocracy.data.model.Bill
import com.example.hybriddemocracy.data.model.User
import com.example.hybriddemocracy.data.model.request.AuthRequest
import com.example.hybriddemocracy.data.model.response.AuthResponse
import com.example.hybriddemocracy.data.model.response.HelloResponse
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.POST
import retrofit2.http.Path

interface ApiService {
    @POST("authenticate")
    suspend fun authenticate(@Body request: AuthRequest): AuthResponse

    @GET("bills/pull/{citizenId}")
    suspend fun getBillsByUserId(@Path("citizenId") citizenId: Long): List<Bill>

    @GET("login")
    suspend fun login(email: String, password: String): User

//    @Headers(
//        "Accept: application/json"
//    )
    @GET("citizens/email/{email}")
    suspend fun getUserByEmail(@Path("email") email: String): User

    @GET("bills/bill/{id}")
    suspend fun getBillById(@Path("id") id: Long): Bill

    @Headers("User-Agent: OpenData")
    @GET("bills/text/{nreg}")
    suspend fun getBillTextByNreg(@Path("nreg") nreg: String): String
}