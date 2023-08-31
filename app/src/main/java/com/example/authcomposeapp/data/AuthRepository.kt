package com.example.authcomposeapp.data

import com.example.authcomposeapp.network.ApiService

interface AuthRepository {

}

class NetworkAuthRepository(
    private val apiService: ApiService
):AuthRepository{

}