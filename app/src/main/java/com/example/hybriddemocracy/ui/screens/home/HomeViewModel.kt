package com.example.hybriddemocracy.ui.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hybriddemocracy.data.model.Bill
import com.example.hybriddemocracy.data.model.User
import com.example.hybriddemocracy.data.repository.RepositoryImpl
import com.example.hybriddemocracy.utils.network.DataState
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
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.Response
import java.io.IOException
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(private val repo: RepositoryImpl) : ViewModel() {

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

    fun getBillById(id: Long, onSuccess: (bill: Bill) -> Unit) {
        viewModelScope.launch {
            repo.getBillById(id).onEach {
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

    fun getBillTextByNreg(nreg: String, onSuccess: (text: String) -> Unit) {
        viewModelScope.launch {
//            repo.getBillTextByNreg(nreg).onEach {
//                when (it) {
//                    is DataState.Loading -> {
//                        _isLoading.value = true
//                    }
//
//                    is DataState.Success -> {
//                        onSuccess(it.data)
//                    }
//
//                    is DataState.Error -> {
//                        _isLoading.value = false
//                    }
//                }
//            }.launchIn(viewModelScope)
            val text = fetchHtml("https://data.rada.gov.ua/laws/show/$nreg.txt")
            onSuccess(text ?: "")
        }
    }

    fun fetchHtml(url: String): String? = runBlocking {
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
}