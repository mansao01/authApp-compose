package com.example.authcomposeapp.ui.common

import com.example.authcomposeapp.network.response.GetProfileResponse
import com.example.authcomposeapp.network.response.LoginResponse
import com.example.authcomposeapp.network.response.RegisterResponse
import com.example.authcomposeapp.network.response.UserDataItem

sealed interface RegisterUiState {
    object StandBy : RegisterUiState
    object Loading : RegisterUiState
    data class Success(val registerResponse: RegisterResponse) : RegisterUiState
    data class Error(val msg:String) : RegisterUiState
}

sealed interface LoginUiState{
    object StandBy : LoginUiState
    object Loading : LoginUiState
    data class Success(val loginResponse: LoginResponse) : LoginUiState
    data class Error(val msg:String) : LoginUiState
}

sealed interface HomeUiState{
    object Loading : HomeUiState
    data class Success(val userDataItem: List<UserDataItem>,val getProfileResponse: GetProfileResponse) : HomeUiState
    data class Error(val msg:String) : HomeUiState
}