package com.example.authcomposeapp.network.response

import com.google.gson.annotations.SerializedName

data class LogoutResponse(

	@field:SerializedName("msg")
	val msg: String
)
