package com.example.hybriddemocracy.data.repository

import com.example.hybriddemocracy.data.model.Bill
import com.example.hybriddemocracy.data.model.User
import com.example.hybriddemocracy.data.model.response.AuthResponse
import com.example.hybriddemocracy.data.model.response.HelloResponse
import com.example.hybriddemocracy.utils.network.DataState
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun authenticate(email: String, password: String): Flow<DataState<AuthResponse>> // Get token
    suspend fun getBillsByUserId(id: Long): Flow<DataState<List<Bill>>>
    suspend fun login(email: String, password: String): Flow<DataState<User>>
    suspend fun getUserByEmail(email: String): Flow<DataState<User>>
}
