package com.example.authcomposeapp.ui.common

import com.example.authcomposeapp.network.response.RegisterResponse

sealed interface RegisterUiState {
    object StandBy : RegisterUiState
    object Loading : RegisterUiState
    data class Success(val registerResponse: RegisterResponse) : RegisterUiState
    data class Error(val msg:String) : RegisterUiState
}