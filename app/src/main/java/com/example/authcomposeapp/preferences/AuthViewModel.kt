package com.example.authcomposeapp.preferences

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.authcomposeapp.AuthAppApplication
import kotlinx.coroutines.launch

class AuthViewModel(
    private val authTokenManager: AuthTokenManager

) : ViewModel() {

    private val _accessToken = mutableStateOf("")
    val accessToken: State<String?> = _accessToken

    suspend fun getAccessToken(){
        authTokenManager.getAccessTokenFlow().collect { token ->
            _accessToken.value = token
        }
    }

    fun removeAccessToken(){
        viewModelScope.launch {
            authTokenManager.clearTokens()
        }
    }
    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AuthAppApplication)
                val authTokenManager = application.authTokenManager
                AuthViewModel(authTokenManager = authTokenManager)
            }
        }
    }
}
