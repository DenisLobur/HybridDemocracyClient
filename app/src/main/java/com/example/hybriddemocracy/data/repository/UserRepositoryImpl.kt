package com.example.hybriddemocracy.data.repository

import com.example.hybriddemocracy.data.datasource.remote.ApiService
import com.example.hybriddemocracy.data.model.User
import com.example.hybriddemocracy.utils.network.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val api: ApiService
) : UserRepository {
    override suspend fun getUserById(userId: String): Flow<DataState<User>> = flow {
        emit(DataState.Loading)
        try {
            val user = api.getUserById(userId)
            emit(DataState.Success(user))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}