package com.example.hybriddemocracy.data.repository

import com.example.hybriddemocracy.data.model.User
import com.example.hybriddemocracy.data.model.response.AuthResponse
import com.example.hybriddemocracy.utils.network.DataState
import kotlinx.coroutines.flow.Flow

interface Repository {
    suspend fun authenticate(username: String, password: String): Flow<DataState<AuthResponse>> // Get token
    suspend fun login(email: String, password: String): Flow<DataState<User>>
    suspend fun getUserById(userId: String): Flow<DataState<User>>
}
