package com.example.authcomposeapp.network.request

import com.google.gson.annotations.SerializedName

data class RefreshTokenRequest(
    @field:SerializedName("refreshToken")
    val refreshToken:String
)
