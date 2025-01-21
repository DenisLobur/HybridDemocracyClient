package com.example.hybriddemocracy.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hybriddemocracy.data.model.Bill
import com.example.hybriddemocracy.data.model.User
import com.example.hybriddemocracy.data.repository.RepositoryImpl
import com.example.hybriddemocracy.utils.network.DataState
import com.example.hybriddemocracy.utils.security.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject


@HiltViewModel
class HomeViewModel @Inject constructor(
    private val tokenManager: TokenManager,
    private val repo: RepositoryImpl
) : ViewModel() {

    private val _user = MutableStateFlow<User?>(null)
    val user: StateFlow<User?> get() = _user.asStateFlow()

    private val _bills = MutableStateFlow<List<Bill>?>(null)
    val bills: StateFlow<List<Bill>?> get() = _bills.asStateFlow()

    private val _billDetails = MutableStateFlow<Bill?>(null)
    val billDetails: StateFlow<Bill?> get() = _billDetails.asStateFlow()

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading get() = _isLoading.asStateFlow()

    fun getUserByEmail(email: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            repo.getUserByEmail(email).onEach {
                when (it) {
                    is DataState.Loading -> {
                        _isLoading.value = true
                    }

                    is DataState.Success -> {
                        _user.value = it.data
                        onSuccess()
                    }

                    is DataState.Error -> {
                        _isLoading.value = false
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun getBillsByUserId(id: Long, onSuccess: () -> Unit) {
        viewModelScope.launch {
            repo.getBillsByUserId(id).onEach {
                when (it) {
                    is DataState.Loading -> {
                        _isLoading.value = true
                    }

                    is DataState.Success -> {
                        _bills.value = it.data
                        onSuccess()
                    }

                    is DataState.Error -> {
                        _isLoading.value = false
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun getBillById(billId: Long, citizenId: Long, onSuccess: (bill: Bill) -> Unit) {
        viewModelScope.launch {
            repo.getBillById(billId, citizenId).onEach {
                when (it) {
                    is DataState.Loading -> {
                        _isLoading.value = true
                    }

                    is DataState.Success -> {
                        _billDetails.value = it.data
                        onSuccess(it.data)
                    }

                    is DataState.Error -> {
                        _isLoading.value = false
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun vote(billId: Long, citizenId: Long, rating: Int, feedback: String, onSuccess: (isVoted: Boolean) -> Unit) {
        viewModelScope.launch {
            repo.voteBill(billId = billId, citizenId = citizenId, rating = rating, feedback = feedback).onEach {
                when (it) {
                    is DataState.Loading -> {
                        _isLoading.value = true
                    }

                    is DataState.Success -> {
                        val voted = it.data
                        onSuccess(voted)
                    }

                    is DataState.Error -> {
                        _isLoading.value = false
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun summarizeText(longText: String, onSuccess: (summary: String) -> Unit) {
        viewModelScope.launch {
            val text = summarizeHtml(longText)
            onSuccess(text ?: "Can not summarize")
        }
    }

    fun saveSentiment(billId: Long, citizenId: Long, rating: Int, feedback: String, onSuccess: (isSaved: Boolean) -> Unit) {
        viewModelScope.launch {
            repo.saveSentiment(billId, citizenId, rating, feedback).onEach {
                when (it) {
                    is DataState.Loading -> {
                        _isLoading.value = true
                    }

                    is DataState.Success -> {
                        val saved = it.data
                        onSuccess(saved)
                    }

                    is DataState.Error -> {
                        _isLoading.value = false
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun getBillTextByNreg(nreg: String, onSuccess: (text: String) -> Unit) {
        viewModelScope.launch {
            val text = fetchHtml("https://data.rada.gov.ua/laws/show/$nreg.txt")
            onSuccess(text ?: "")
        }
    }

    private fun fetchHtml(url: String): String? = runBlocking {
        withContext(Dispatchers.IO) {
            val client = OkHttpClient()

            val request = Request.Builder()
                .url(url)
                .addHeader("User-Agent", "OpenData")
                .build()

            try {
                val response: Response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    response.body?.string()
                } else {
                    null
                }
            } catch (e: IOException) {
                e.printStackTrace()
                null
            }

        }
    }

    private fun summarizeHtml(longText: String): String? = runBlocking {
        withContext(Dispatchers.IO) {
            val client = OkHttpClient()
            val mediaType = "text/plain".toMediaTypeOrNull()
            val body = RequestBody.create(mediaType, longText)
            val request = Request.Builder()
                .url("http://10.0.2.2:8080/analyze/summarize")
                .post(body)
                .addHeader("User-Agent", "OpenData")
                .addHeader("Authorization", "Bearer ${tokenManager.getToken()}")
                .build()

            try {
                val response: Response = client.newCall(request).execute()
                if (response.isSuccessful) {
                    response.body?.string()
                } else {
                    "Response code: " + response.code
                }
            } catch (e: IOException) {
                e.printStackTrace()
                null
            }

        }
    }
}