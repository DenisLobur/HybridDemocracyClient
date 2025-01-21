package com.example.hybriddemocracy.data.repository

import com.example.hybriddemocracy.data.datasource.remote.ApiService
import com.example.hybriddemocracy.data.model.Bill
import com.example.hybriddemocracy.data.model.User
import com.example.hybriddemocracy.data.model.request.AuthRequest
import com.example.hybriddemocracy.data.model.request.BillRequest
import com.example.hybriddemocracy.data.model.request.SentimentRequest
import com.example.hybriddemocracy.data.model.response.AuthResponse
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

    override suspend fun getBillsByUserId(id: Long): Flow<DataState<List<Bill>>> = flow {
        emit(DataState.Loading)
        try {
            val bills = api.getBillsByUserId(id)
            emit(DataState.Success(bills))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    override suspend fun getBillById(billId: Long, citizenId: Long): Flow<DataState<Bill>> = flow {
        emit(DataState.Loading)
        try {
            val user = api.getBillById(billId = billId, citizenId = citizenId)
            emit(DataState.Success(user))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    override suspend fun getUserByEmail(email: String): Flow<DataState<User>> = flow {
        emit(DataState.Loading)
        try {
            val user = api.getUserByEmail(email)
            emit(DataState.Success(user))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    override suspend fun getBillTextByNreg(nreg: String): Flow<DataState<String>> = flow {
        emit(DataState.Loading)
        try {
            val text = api.getBillTextByNreg(nreg)
            emit(DataState.Success(text))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    override suspend fun voteBill(billId: Long, citizenId: Long, rating: Int, feedback: String): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading)
        try {
            val voted = api.voteBill(billId = billId, citizenId = citizenId, request = BillRequest(rating, feedback))
            emit(DataState.Success(voted))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    override suspend fun saveSentiment(billId: Long, citizenId: Long, rating: Int, feedback: String): Flow<DataState<Boolean>> = flow {
        emit(DataState.Loading)
        try {
            val saved = api.saveSentiment(SentimentRequest(billId, citizenId, rating, feedback))
            emit(DataState.Success(saved))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }

    override suspend fun summarizeText(longText: String): Flow<DataState<String>> = flow {
        emit(DataState.Loading)
        try {
            val summary = api.summarizeText(longText)
            emit(DataState.Success(summary))
        } catch (e: Exception) {
            emit(DataState.Error(e))
        }
    }
}