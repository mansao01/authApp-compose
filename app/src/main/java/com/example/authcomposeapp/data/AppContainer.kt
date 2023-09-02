package com.example.authcomposeapp.data

import com.example.authcomposeapp.network.ApiService
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

interface AppContainer {
    val authRepository: AuthRepository
}

class DefaultAppContainer : AppContainer {
    private val baseUrl = "http://192.168.43.194:8000/"

    private val retrofit = Retrofit.Builder()
        .addConverterFactory(GsonConverterFactory.create())
        .baseUrl(baseUrl)
        .build()

    private val retrofitService: ApiService by lazy {
        retrofit.create(ApiService::class.java)
    }
    override val authRepository: AuthRepository by lazy {
        NetworkAuthRepository(retrofitService)
    }
}