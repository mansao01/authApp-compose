package com.example.authcomposeapp.ui.screen.register

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
import com.example.authcomposeapp.network.request.RegisterRequest
import com.example.authcomposeapp.ui.common.RegisterUiState
import kotlinx.coroutines.launch
import retrofit2.HttpException
import java.io.IOException

class RegisterViewModel(
    private val authRepository: AuthRepository
) : ViewModel() {
    var uiState: RegisterUiState by mutableStateOf(RegisterUiState.StandBy)
        private set

    fun getUiState(){
        uiState = RegisterUiState.StandBy
    }
    fun register(registerRequest: RegisterRequest) {
        viewModelScope.launch {
            uiState = RegisterUiState.Loading
            uiState = try {

                val result = authRepository.register(registerRequest)
                RegisterUiState.Success(result)
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
                 RegisterUiState.Error(errorMessage)
            }

        }
    }

    companion object {
        val Factory: ViewModelProvider.Factory = viewModelFactory {
            initializer {
                val application =
                    (this[ViewModelProvider.AndroidViewModelFactory.APPLICATION_KEY] as AuthAppApplication)
                val authRepository = application.container.authRepository
                RegisterViewModel(authRepository = authRepository)
            }
        }
    }
}