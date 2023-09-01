package com.example.authcomposeapp.network

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
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.POST

interface ApiService {

    @GET("v1/users")
    suspend fun getUsers(
        @Header("Authorization")
        token:String //access token
    ):GetUsersResponse

    @GET("v1/profile")
    suspend fun profile(
        @Header("Authorization")
        token:String //access token
    ):GetProfileResponse

    @POST("v1/register")
    suspend fun register(
        @Body register:RegisterRequest
    ):RegisterResponse

    @POST("v1/login")
    suspend fun login(
        @Body login:LoginRequest
    ):LoginResponse

    @POST("v1/logout")
    suspend fun logout(
        @Body token:LogoutRequest
    ):LogoutResponse


    @POST("v1/token")
    suspend fun refreshToken(
        @Body token:RefreshTokenRequest
    ):RefreshTokenResponse
}