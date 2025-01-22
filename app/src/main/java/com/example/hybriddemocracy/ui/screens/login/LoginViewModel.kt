package com.example.hybriddemocracy.ui.screens.login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.hybriddemocracy.data.repository.Repository
import com.example.hybriddemocracy.data.repository.RepositoryImpl
import com.example.hybriddemocracy.utils.network.DataState
import com.example.hybriddemocracy.utils.security.TokenManager
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class LoginViewModel @Inject constructor(private val tokenManager: TokenManager, private val repo: RepositoryImpl) : ViewModel() {
    private val _token = MutableStateFlow<String?>(null)
    val token: StateFlow<String?> get() = _token.asStateFlow()

    private val _noSuchUser = MutableStateFlow<String?>(null)
    val noSuchUser: StateFlow<String?> get() = _noSuchUser.asStateFlow()

    private val _isLoading = MutableStateFlow<Boolean>(false)
    val isLoading get() = _isLoading.asStateFlow()

    fun authorize(email: String, password: String, onSuccess: () -> Unit) {
        viewModelScope.launch {
            repo.authenticate(email, password).onEach {
                when (it) {
                    is DataState.Loading -> {
                        _isLoading.value = true
                    }

                    is DataState.Success -> {
                        val token = it.data.jwt
                        tokenManager.saveToken(token)
                        _isLoading.value = false
                        onSuccess()
                    }

                    is DataState.Error -> {
                        _isLoading.value = false
                        _noSuchUser.value = "No such user ${email}"
                    }
                }
            }.launchIn(viewModelScope)
        }
    }

    fun logout() {
        tokenManager.clearToken()
    }
}