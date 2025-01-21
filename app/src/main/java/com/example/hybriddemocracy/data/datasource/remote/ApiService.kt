package com.example.hybriddemocracy.data.datasource.remote

import com.example.hybriddemocracy.data.model.Bill
import com.example.hybriddemocracy.data.model.User
import com.example.hybriddemocracy.data.model.request.AuthRequest
import com.example.hybriddemocracy.data.model.request.BillRequest
import com.example.hybriddemocracy.data.model.request.SentimentRequest
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

    @GET("citizens/{citizenId}/bills")
    suspend fun getBillsByUserId(@Path("citizenId") citizenId: Long): List<Bill>

    @GET("login")
    suspend fun login(email: String, password: String): User

    @GET("citizens/email/{email}")
    suspend fun getUserByEmail(@Path("email") email: String): User

    @GET("bills/citizen/{citizenId}/bill/{billId}")
    suspend fun getBillById(@Path("citizenId") citizenId: Long, @Path("billId") billId: Long, ): Bill

    @Headers("User-Agent: OpenData")
    @GET("bills/text/{nreg}")
    suspend fun getBillTextByNreg(@Path("nreg") nreg: String): String

    @POST("bills/{billId}/citizen/{citizenId}/vote")
    suspend fun voteBill(@Path("billId") billId: Long, @Path("citizenId") citizenId: Long, @Body request: BillRequest): Boolean

    @POST("sentiment")
    suspend fun saveSentiment(@Body request: SentimentRequest): Boolean

    @Headers("User-Agent: OpenData", "Content-Type: text/plain")
    @POST("analyze/summarize")
    suspend fun summarizeText(@Body longText: String): String
}