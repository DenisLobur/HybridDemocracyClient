package com.example.hybriddemocracy.data.repository

import com.example.hybriddemocracy.data.datasource.remote.ApiService
import com.example.hybriddemocracy.data.model.User
import com.example.hybriddemocracy.data.model.request.AuthRequest
import com.example.hybriddemocracy.data.model.response.AuthResponse
import com.example.hybriddemocracy.data.model.response.HelloResponse
import com.example.hybriddemocracy.utils.network.DataState
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import javax.inject.Inject

class RepositoryImpl @Inject constructor(
    private val api: ApiService
) : Repository {

    override suspend fun authenticate(email: String, password: String): Flow<DataState<AuthResponse>> = flow {
        emit(DataState.Loading)
        try {
            val token = api.authenticate(AuthRequest(email, password))
            emit(DataState.Success(token))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    override suspend fun sayHello(): Flow<DataState<HelloResponse>> = flow {
        emit(DataState.Loading)
        try {
            val hello = api.sayHello()
            emit(DataState.Success(hello))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    override suspend fun login(email: String, password: String): Flow<DataState<User>> {
        TODO("Not yet implemented")
    }

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