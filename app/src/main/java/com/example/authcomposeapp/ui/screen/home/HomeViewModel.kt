package com.example.authcomposeapp.ui.screen.home

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import androidx.lifecycle.viewmodel.initializer
import androidx.lifecycle.viewmodel.viewModelFactory
import com.example.authcomposeapp.AuthAppApplication
import com.example.authcomposeapp.data.AuthRepository
import com.example.authcomposeapp.preferences.AuthTokenManager
import com.example.authcomposeapp.ui.common.HomeUiState
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class HomeViewModel(
    private val authRepository: AuthRepository,
    private val authTokenManager: AuthTokenManager
) : ViewModel() {

    var uiState: HomeUiState by mutableStateOf(HomeUiState.Loading)
        private set


    fun getUsersAndProfile(token: String) {
        viewModelScope.launch {
            uiState = HomeUiState.Loading
            uiState = try {
                val result = authRepository.getAllUser("Bearer $token")
                val resultProfile = authRepository.profile("Bearer $token")
                val localToken  = authTokenManager.getAccessToken()
                HomeUiState.Success(result.data, resultProfile, localToken !!)
            } catch (e: Exception) {
                val errorMessage = when (e) {
                    is IOException -> "Network error occurred"
                    is HttpException -> {
                        when (e.code()) {
                            400 -> e.response()?.errorBody()?.string().toString()
                            // Add more cases for specific HTTP error codes if needed
                            else -> "HTTP error: ${e.code()}"
                        }
                    }

                    else -> "An unexpected error occurred"
                }
                HomeUiState.Error(errorMessage)
            }
        }
    }


    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AuthAppApplication)
                val authRepository = application.container.authRepository
                val authTokenManager = application.authTokenManager
                HomeViewModel(authRepository = authRepository, authTokenManager = authTokenManager)
            }
        }
    }
}