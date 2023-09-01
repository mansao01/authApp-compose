package com.example.authcomposeapp.data

import com.example.authcomposeapp.network.ApiService
import com.example.authcomposeapp.network.request.LoginRequest
import com.example.authcomposeapp.network.request.LogoutRequest
import com.example.authcomposeapp.network.request.RefreshTokenRequest
import com.example.authcomposeapp.network.request.RegisterRequest
import com.example.authcomposeapp.network.response.GetProfileResponse
import com.example.authcomposeapp.network.response.GetUsersResponse
import com.example.authcomposeapp.network.response.LoginResponse
import com.example.authcomposeapp.network.response.LogoutResponse
import com.example.authcomposeapp.network.response.RefreshTokenResponse
import com.example.authcomposeapp.network.response.RegisterResponse

interface AuthRepository {

    suspend fun getAllUser(token: String): GetUsersResponse
    suspend fun profile(token: String): GetProfileResponse
    suspend fun register(registerRequest: RegisterRequest): RegisterResponse
    suspend fun login(loginRequest: LoginRequest): LoginResponse
    suspend fun logout(token: LogoutRequest): LogoutResponse
    suspend fun refreshToken(token: RefreshTokenRequest): RefreshTokenResponse
}

class NetworkAuthRepository(
    private val apiService: ApiService
) : AuthRepository {
    override suspend fun getAllUser(token: String): GetUsersResponse = apiService.getUsers(token)

    override suspend fun profile(token: String): GetProfileResponse = apiService.profile(token)

    override suspend fun register(registerRequest: RegisterRequest): RegisterResponse =
        apiService.register(registerRequest)

    override suspend fun login(loginRequest: LoginRequest): LoginResponse =
        apiService.login(loginRequest)

    override suspend fun logout(token: LogoutRequest): LogoutResponse = apiService.logout (token)

    override suspend fun refreshToken(token: RefreshTokenRequest): RefreshTokenResponse =
        apiService.refreshToken(token)
}