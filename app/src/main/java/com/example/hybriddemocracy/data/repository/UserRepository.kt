package com.example.hybriddemocracy.data.repository

import com.example.hybriddemocracy.data.model.User
import com.example.hybriddemocracy.utils.network.DataState
import kotlinx.coroutines.flow.Flow

interface UserRepository {
    suspend fun getUserById(userId: String): Flow<DataState<User>>
}