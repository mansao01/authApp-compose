package com.example.authcomposeapp.network.request

import com.google.gson.annotations.SerializedName

data class LogoutRequest(
    @field:SerializedName("refreshToken")
    val refreshToken:String
)
