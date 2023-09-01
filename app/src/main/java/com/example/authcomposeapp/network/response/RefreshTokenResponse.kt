package com.example.authcomposeapp.network.response

import com.google.gson.annotations.SerializedName

data class RefreshTokenResponse(

	@field:SerializedName("accessToken")
	val accessToken: String
)
